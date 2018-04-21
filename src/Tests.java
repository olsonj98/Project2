import java.util.ArrayList;


public class Tests {
	public static void main(String[] args) throws Exception {
		testWikiCrawler();
		testNetworkInfluence();
	}
	
	private static void testWikiCrawler(){
		ArrayList<String> topics = new ArrayList<String>();
		int numResults = 100;
//		String seed = "/wiki/Size";
//		topics.add("length");
//		topics.add("height");
//		topics.add("width");
		long startTime = System.currentTimeMillis();

		 //String seed = "/wiki/Iowa_State_University";
		 //topics.add("Iowa State");
		 //topics.add("Cyclones");
		
		String seed = "/wiki/Computer_Science";
		
			
		WikiCrawler w = new WikiCrawler(seed, numResults, topics, "WikiCS.txt");
		w.crawl();
		
		System.out.println("DONE.....................");
		System.out.println("Finished in: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	private static void testNetworkInfluence(){
		NetworkInfluence graph = new NetworkInfluence("test.txt");
//		String u = "/wiki/Complexity_theory";
//		String v = "/wiki/System";
//		String inf = "/wiki/Complexity";
		String u = "Ames";
		String v = "Chicago";
		String inf = "Omaha";
		System.out.println("Distance between  " + u + " and " + v + ": " + graph.distance(u, v));
		System.out.println("Influence of " + inf + ": " + graph.influence(inf));
	}
}
