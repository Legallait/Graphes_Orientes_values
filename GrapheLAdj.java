package graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheLAdj implements IGraphe {
	private final Map<String, List<Arc>> ladj;
	
	public GrapheLAdj(String A) {
		ladj = new HashMap<>();
		this.peupler(A);
	}

	public GrapheLAdj() {
		ladj = new HashMap<>();
	}

	@Override
	public void ajouterSommet(String noeud) {
		if(!contientSommet(noeud))
			ladj.put(noeud,new ArrayList<>());
	}

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		if (contientArc(source, destination))
			throw new IllegalArgumentException("arc deja existant");
		ajouterSommet(source);
		ajouterSommet(destination);
		ladj.get(source).add(new Arc(source,destination,valeur));
	}

	@Override
	public void oterSommet(String noeud) {
		if(contientSommet(noeud))
			ladj.remove(noeud);
	}

	@Override
	public void oterArc(String source, String destination) {
		if(!contientArc(source, destination))
			throw new IllegalArgumentException("arc inexistant");
		ladj.get(source).removeIf(arc -> arc.getDestination().equals(destination));
	}

	@Override
	public List<String> getSommets() {
		return new ArrayList<>(ladj.keySet());
	}

	@Override
	public List<String> getSucc(String sommet) {
		List<String> listeSucc = new ArrayList<>();
		for (Arc arc : ladj.get(sommet))
			listeSucc.add(arc.getDestination());
		return listeSucc;
	}

	@Override
	public int getValuation(String src, String dest) {
		if (!contientArc(src,dest))
			throw new IllegalArgumentException("arc inexistant");
		for (Arc arc : ladj.get(src))
			if (arc.getDestination().equals(dest))
				return arc.getValuation();
		return 0;
	}

	@Override
	public boolean contientSommet(String sommet) {
		return ladj.containsKey(sommet);
	}

	@Override
	public boolean contientArc(String src, String dest) {
		if (contientSommet(src))
			for (Arc arc : ladj.get(src))
				if (arc.getDestination().equals(dest))
					return true;
		return false;
	}

	@Override
	public String toString(){
		ArrayList<String> arcsString = new ArrayList<>();
		for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
			if (entry.getValue().isEmpty())
				arcsString.add(entry.getKey() + ":");
			else
				for (Arc arc : entry.getValue())
					arcsString.add(arc.toString());
		}
		arcsString.sort(String::compareTo);
		return  arcsString.toString().substring(1, arcsString.toString().length() - 1);
	}
}
