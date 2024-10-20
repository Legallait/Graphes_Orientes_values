package graphe.types;

import graphe.IGraphe;

import java.util.*;

public class GrapheMAdj implements IGraphe {
    private int[][] matrice;
    private final Map<String, Integer> indices;

    public GrapheMAdj(String chaine) {
        this.indices = new HashMap<>();
        this.peupler(chaine);
    }

    public GrapheMAdj() {
        indices = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        //On ajoute le sommet seulement s'il n'existe pas déjà
        if (!contientSommet(noeud)) {
            //On commence par l'indexer dans la map d'indices
            int indice = 0;
            for (String sommet : indices.keySet()) {
                if (noeud.compareTo(sommet) > 0)
                    indice += 1;
                else
                    indices.replace(sommet, indices.get(sommet) + 1);
            }
            indices.put(noeud, indice);

            //On l'ajoute ensuite dans la matrice

            //Cas si la matrice possède déjà un sommet
            if (matrice != null) {
                //On crée un nouveau tableau à 2 dimensions
                int[][] tableau = new int[matrice.length + 1][matrice[0].length + 1];
                //On remplit le debut du tableau avec les valeurs de la matrice
                for (int i = 0; i < indice; i++) {
                    //colonnes des sommets précédant le nouveau
                    System.arraycopy(matrice[i], 0, tableau[i], 0, indice);
                    //colonne du nouveau sommet
                    tableau[i][indice] = -1;
                    //colonnes des sommets suivants le nouveau
                    if (matrice.length - indice >= 0)
                        System.arraycopy(matrice[i], indice, tableau[i], indice + 1, matrice.length - indice);
                }

                //ligne des successeurs du nouveau sommet
                for (int j = 0; j <= matrice.length; j++) {
                    tableau[indice][j] = -1;
                }

                //On remplit la fin du tableau avec les valeurs de la matrice
                for (int i = indice; i < matrice.length; i++) {
                    //colonnes des sommets précédant le nouveau
                    System.arraycopy(matrice[i], 0, tableau[i + 1], 0, indice);
                    //colonne du nouveau sommet
                    tableau[i + 1][indice] = -1;
                    //colonnes des sommets suivants le nouveau
                    System.arraycopy(matrice[i], indice, tableau[i + 1], indice + 1, matrice.length - indice);
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
        if (valeur < 0)
            throw new IllegalArgumentException("valeur negative");
        if (!contientSommet(source))
            ajouterSommet(source);
        if (!contientSommet(destination))
            ajouterSommet(destination);
        if (!contientArc(source, destination))
            matrice[indices.get(source)][indices.get(destination)] = valeur;
        else
            throw new IllegalArgumentException("arc deja existant");
    }

    @Override
    public void oterSommet(String noeud) {
        if (contientSommet(noeud)) {
            int[][] newMatrice = new int[matrice.length - 1][matrice.length - 1];
            for (int i = 0; i < matrice.length; i++) {
                for (int j = 0; j < matrice.length; j++) {
                    if (i != indices.get(noeud) && j != indices.get(noeud)) {
                        if (i < indices.get(noeud) && j < indices.get(noeud)) {
                            newMatrice[i][j] = matrice[i][j];
                        } else if (i < indices.get(noeud) && j > indices.get(noeud)) {
                            newMatrice[i][j - 1] = matrice[i][j];
                        } else if (i > indices.get(noeud) && j < indices.get(noeud)) {
                            newMatrice[i - 1][j] = matrice[i][j];
                        } else if (i > indices.get(noeud) && j > indices.get(noeud)) {
                            newMatrice[i - 1][j - 1] = matrice[i][j];
                        }
                    }
                }
            }
            matrice = newMatrice;
            indices.remove(noeud);
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException("arc inexistant");
        matrice[indices.get(source)][indices.get(destination)] = -1;

    }

    @Override
    public List<String> getSommets() {
        return new ArrayList<>(indices.keySet());
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        for (int j = 0; j < matrice[0].length; j++)
            if (matrice[indices.get(sommet)][j] >= 0)
                for (String entree : indices.keySet())
                    if (indices.get(entree) == j)
                        successeurs.add(entree);
        return successeurs;
    }

    @Override
    public int getValuation(String src, String dest) {
        if (!contientArc(src, dest))
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
    public String toString() {
        return toAString();
    }
}