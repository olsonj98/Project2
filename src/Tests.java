import java.util.ArrayList;


public class Tests {
	public static void main(String[] args) throws Exception {
		//testWikiCrawler();
		testNetworkInfluence();
	}
	
	private static void testWikiCrawler(){
		ArrayList<String> topics = new ArrayList<String>();
		int numResults = 10;
		String seed = "/wiki/Size";
		topics.add("length");
		topics.add("height");
		topics.add("width");

		// String seed = "/wiki/Iowa_State_University";
		// topics.add("Iowa");
		// topics.add("Cyclones");

		WikiCrawler w = new WikiCrawler(seed, numResults, topics, "WikiISU.txt");
		try {
			w.crawl();
		} catch (Exception e) {
			int temp = 0;
		}
	}
	
	private static void testNetworkInfluence(){
		NetworkInfluence graph = new NetworkInfluence("wikiCC.txt");
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
