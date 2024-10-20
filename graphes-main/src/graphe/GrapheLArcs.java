package graphe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GrapheLArcs implements IGrapheConst {
    private List<Arc> arcs;

    public GrapheLArcs (String chaine){
        arcs = new ArrayList<>();
        String[] arcsString = chaine.split(", ");
        for (String arcString: arcsString) {
            if(arcString.length() == 2){
                Arc arc = new Arc(arcString.substring(0, 1), "", 0);
                arcs.add(arc);
            }
            else{
                String[] data = arcString.split("-|\\(|\\)");
                String source = data[0];
                String destination = data[1];
                int valeur = Integer.parseInt(data[2]);
                Arc arc = new Arc(source, destination, valeur);
                arcs.add(arc);
            }
        }

        // chaine :
        // A-B(5), A-C(10), B-C(3), C-D(8), E:

        // on crée un tableau arcsString :
        //  [A-B(5); A-C(10); B-C(3); C-D(8); E:]

        // on le parcourt pour récupérer les valeurs, créer les arcs et les ajouter à notre liste:
        // A-B(5)
        // A-C(10)
        // B-C(3)
        // C-D(8)
        // E:

    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        for(Arc arc: arcs){
            String source = arc.getSource();
            String destination = arc.getDestination();
            if(!sommets.contains(source))
                sommets.add(source);
            if(!sommets.contains(destination) && destination.length() != 0)
                sommets.add(destination);
        }
        Collections.sort(sommets);
        return sommets;
    }
    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        for(Arc arc: arcs){
            String source = arc.getSource();
            String destination = arc.getDestination();
            if(source.equals(sommet) && destination.length() != 0) {
                if (!successeurs.contains(destination))
                    successeurs.add(destination);
            }
        }
        return successeurs;
    }
    @Override
    public int getValuation(String src, String dest) {
        int valuation;
        for(Arc arc: arcs){
            String source = arc.getSource();
            String destination = arc.getDestination();
            if(source.equals(src) && dest.equals(destination))
                return arc.getValeur();
        }
        return 0;
    }
    @Override
    public boolean contientSommet(String sommet) {
        List<String> sommets = getSommets();
        if(sommets.contains(sommet))
            return true;
        return false;
    }
    @Override
    public boolean contientArc(String src, String dest) {
        for(Arc arc: arcs){
            String source = arc.getSource();
            String destination = arc.getDestination();
            if(source.equals(src) && dest.equals(destination))
                return true;
        }
        return false;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < arcs.size() - 1; i++) {
            s.append(arcs.get(i) + ", ");
        }
        s.append(arcs.get(arcs.size()-1));
        return s.toString();
    }
}
