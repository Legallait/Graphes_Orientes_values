package main;

import java.util.Map;
import java.util.HashMap;

import algo.DijkstraTools;
import graphe.IGraphe;
import graphe.ihm.CheminATrouver;
import graphe.ihm.GraphDirectoryImporter;
import graphe.types.*;


public class Main {
    public static final String GRAPHES_REP = "graphes/barabasi";
    public static final String REPONSES_REP = "reponses/barabasi";
	private static final IGraphe[] prototypes = {
			new GrapheLArcs(),
            new GrapheLAdj(),
			new GrapheMAdj(),
            new GrapheHHAdj()
	};

	public static void main(String[] args) {
        long duree;
		for (IGraphe g : prototypes) {
			System.out.print(g.getClass().getSimpleName() + " : ");
			//duree = mesurer(g, "graphes", "reponses");
            duree = chronoTousLesGraphesDeType(g);
            System.out.println(duree + " millisecondes" );
		}
	}

    private static long chronoTousLesGraphesDeType(IGraphe g) {
        long dureeTotale = 0;
        GraphDirectoryImporter importer = new GraphDirectoryImporter(GRAPHES_REP, REPONSES_REP, false, g);
        for (CheminATrouver cat : importer) {
            dureeTotale += chrono(cat.getGraph(), cat.getSD_arc().getSource()
            );
        }
        return dureeTotale;
    }

    private static long chrono(IGraphe g, String source) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        return DijkstraTools.time(g, source, dist, prev);
    }
}
