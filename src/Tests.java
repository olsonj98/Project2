import java.util.ArrayList;


public class Tests {
	public static void main(String[] args) throws Exception {
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("length");
		topics.add("height");
		topics.add("width");
		WikiCrawler w = new WikiCrawler("/wiki/Size", 10, topics, "WikiISU.txt");
		w.crawl();
	}
}
