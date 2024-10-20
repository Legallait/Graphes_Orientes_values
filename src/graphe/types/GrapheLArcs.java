package graphe.types;

import graphe.Arc;
import graphe.IGraphe;

import java.util.ArrayList;
import java.util.List;

public class GrapheLArcs implements IGraphe {
    private final List<Arc> arcs;

    public GrapheLArcs (String chaine){
        arcs = new ArrayList<>();
        this.peupler(chaine);
        }

    public GrapheLArcs (){
        arcs = new ArrayList<>();
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
        for(Arc arc: arcs){
            String source = arc.getSource();
            String destination = arc.getDestination();
            if(source.equals(src) && dest.equals(destination))
                return arc.getValuation();
        }
        return -1;
    }
    @Override
    public boolean contientSommet(String sommet) {
        List<String> sommets = getSommets();
        return sommets.contains(sommet);
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


    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)){
            Arc arc = new Arc(noeud);
            arcs.add(arc);
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination))
            throw new IllegalArgumentException("arc deja present");
        Arc arc = new Arc(source, destination, valeur);
        arcs.add(arc);
        supprimerArcNull(source);
    }

    private void supprimerArcNull(String source){
        for (int i = 0; i < arcs.size(); i++) {
            if(arcs.get(i).getSource().equals(source) && arcs.get(i).getDestination().length() == 0){
                arcs.remove(i);
                break;
            }
        }
    }

    @Override
    public void oterSommet(String noeud) {
        if(contientSommet(noeud)){
            for (int i = 0; i < arcs.size(); i++) {
                Arc arc = arcs.get(i);
                String source = arc.getSource();
                String destination = arc.getDestination();
                if(source.equals(noeud) || destination.equals(noeud)){
                    arcs.remove(i);
                    i--;
                }
            }
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if(!contientArc(source, destination))
            throw new IllegalArgumentException("arc non present");
        else{
            for (int i = 0; i < arcs.size(); i++) {
                Arc arc = arcs.get(i);
                String src = arc.getSource();
                String dest = arc.getDestination();
                if(src.equals(source) && dest.equals(destination)){
                    arcs.remove(i);
                    i--;
                }
            }
        }
    }
    public String toString(){
        return toAString();
    }
}
