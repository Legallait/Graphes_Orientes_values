package graphe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class GrapheMAdj implements IGraphe {

	private int[][] matrice;
	private Map<String, Integer> indices;
	
	public GrapheMAdj(String chaine){
		indices = new TreeMap<>();
		this.peupler(chaine);
	}
	public GrapheMAdj() {
		indices = new TreeMap<>();
	}

	@Override
	public List<String> getSommets() {
		List<String> sommets = new ArrayList<>();
		for (String entree : indices.keySet()) {
			sommets.add(entree);
		}
		return sommets;
	}

	@Override
	public List<String> getSucc(String sommet) {
		if (!contientSommet(sommet))
			throw new IllegalArgumentException("sommet non existant");
		List<String> successeurs = new ArrayList<>();
		for (int j = 0; j < matrice[0].length; j++) {
			if (matrice[indices.get(sommet)][j] >= 0) {
				for (String entree : indices.keySet()) {
					if (indices.get(entree) == j)
						successeurs.add(entree);
				}
			}
		}

		return successeurs;
	}

	@Override
	public int getValuation(String src, String dest) {
		if (!contientArc(src,dest))
			throw new IllegalArgumentException("arc non existant");
		return matrice[indices.get(src)][indices.get(dest)];
	}

	@Override
	public boolean contientSommet(String sommet) {
		return indices.containsKey(sommet);
	}

	@Override
	public boolean contientArc(String src, String dest) {
		//On teste d'abord si les deux sommets donnés existent
		if (contientSommet(src) && contientSommet(dest))
			return (matrice[indices.get(src)][indices.get(dest)] >= 0);
		else
			return false;
	}

	@Override
	public void ajouterSommet(String noeud) {
		//On ajoute le sommet seulement il n'existe pas déjà
		if (!contientSommet(noeud)) {
			//On commence par l'indexer dans la map d'indices
			int indice = 0;
			for (String sommet : indices.keySet()) {
				if (noeud.compareTo(sommet)>0) 
					indice += 1;
				else
					indices.replace(sommet, indices.get(sommet)+1);
			}
			indices.put(noeud, indice);
			
			//On l'ajoute ensuite dans la matrice
			
			//Cas si la matrice possède déjà un sommet
			if (matrice != null) {
				//On cree un nouveau tableau à 2 dimensions
				int[][] tableau = new int[matrice.length + 1][matrice[0].length + 1];
				//On remplit le debut du tableau avec les valeurs de la matrice
				for (int i = 0; i < indice ; i++) {
					//colonnes des sommets précédant le nouveau
					for (int j = 0; j < indice; j++) {
						tableau[i][j] = matrice[i][j];
					}
					//colonne du nouveau sommet
					tableau[i][indice] = -1;
					//colonnes des sommets suivants le nouveau
					for (int j = indice; j < matrice.length; j++) {
						tableau[i][j+1] = matrice[i][j];
					}
				}
				
				//ligne des successeurs du nouveau sommet
				for (int j = 0; j <= matrice.length; j++) {
					tableau[indice][j] = -1;
				}
				
				//On remplit la fin du tableau avec les valeurs de la matrice
				for (int i = indice ; i < matrice.length ; i++) {
					//colonnes des sommets précédant le nouveau
					for (int j = 0; j<indice; j++) {
						tableau[i+1][j] = matrice[i][j];
					}
					//colonne du nouveau sommet
					tableau[i+1][indice] = -1;
					//colonnes des sommets suivants le nouveau
					for (int j = indice; j < matrice.length; j++) {
						tableau[i+1][j+1] = matrice[i][j];
					}
				}
				//On remplace notre matrice par le tableau nouvellement créé
				matrice = tableau.clone();
			}
			
			//Cas si c'est le premier sommet de la matrice
			else {
				matrice = new int[1][1];
				matrice[0][0] = -1;
			}
		}
	}

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		//On teste que l'arc n'existe pas déjà
		if (contientArc(source, destination)){
            throw new IllegalArgumentException("arc deja present");
        }
		if (valeur < 0)
            throw new IllegalArgumentException("valuation negative");
		ajouterSommet(source);
		ajouterSommet(destination);
		//On recupère les indices des sommets
		int indice_ligne = indices.get(source);
		int indice_colonne = indices.get(destination);
		matrice[indice_ligne][indice_colonne] = valeur;
		
	}

	@Override
	public void oterSommet(String noeud) {
		if (contientSommet(noeud)) {
			int indice = indices.get(noeud);
			
			//On supprime le sommet de la map et on décrémente l'indice de tous les sommets suivants
			indices.remove(noeud);
			for (String entree : indices.keySet()) {
				if (indices.get(entree) > indice)
					indices.replace(entree, indices.get(entree)-1);
			}

			//On recréé une matrice en retirant tous les arcs correspondant au sommet retiré
			int[][] tableau = new int [matrice.length-1][matrice[0].length-1];
			//On commence par les sommets précédent le sommet retiré
			for (int i = 0; i < indice; i++) {
				for (int j = 0; j < indice; j++) {
					tableau[i][j] = matrice[i][j];
				}
			}
			//On termine par les sommets suivants le sommet retiré
			for (int i = indice+1; i < matrice.length; i++) {
				for (int j = indice +1; j < matrice[0].length; j++) {
					tableau[i-1][j-1] = matrice[i][j];
				}
			}
			//Une fois le tableau créé, on le définit comme la nouvelle matrice
			matrice = tableau.clone();
		}
		
	}

	@Override
	public void oterArc(String source, String destination) {
		if(!contientArc(source, destination))
            throw new IllegalArgumentException("arc non present");
		matrice[indices.get(source)][indices.get(destination)] = -1;

	}

	@Override
	public String toString() {
		String s = "";
		for (String noeud : getSommets()) {
			if (s.length() > 1)
				s += ", ";
			s += noeud;
			if (getSucc(noeud).isEmpty()) 
				s += ":";
			else {
				boolean firstsucc = true;
				for (String succ : getSucc(noeud)) {
					if (!firstsucc)
						s += ", " + noeud;
					s += "-" + succ + "(" + getValuation(noeud,succ) + ")";
					firstsucc = false;
				}
			}
		}
		return s;
	}
}
