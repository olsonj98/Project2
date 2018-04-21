import java.util.ArrayList;


public class Tests {
	public static void main(String[] args) throws Exception {
		//testWikiCrawler();
		testNetworkInfluence();
	}
	
	private static void testWikiCrawler(){
		ArrayList<String> topics = new ArrayList<String>();
		int numResults = 100;
		long startTime = System.currentTimeMillis();
		String seed = "/wiki/Computer_Science";
			
		WikiCrawler w = new WikiCrawler(seed, numResults, topics, "WikiCS.txt");
		w.crawl();
		
		System.out.println("DONE.....................");
		System.out.println("Finished in: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	private static void testNetworkInfluence(){
		NetworkInfluence graph = new NetworkInfluence("test.txt");
		
		String u = "Ames";
		String v = "Chicago";
		String inf = "Omaha";
		System.out.println("\n");
		System.out.println("Distance between  " + u + " and " + v + ": " + graph.distance(u, v));
		System.out.println("Influence of " + inf + ": " + graph.influence(inf));
		
		ArrayList<String> influential = graph.mostInfluentialDegree(2);
		System.out.println("Most influential 2 degree: " + influential.get(0) + ", " + influential.get(1));
		influential = graph.mostInfluentialModular(2);
		System.out.println("Most influential 2 modular: " + influential.get(0) + ", " + influential.get(1));
		influential = graph.mostInfluentialSubModular(2);
//		System.out.println("Most influential 2 submodular: " + influential.get(0) + ", " + influential.get(1));
	}
}
