// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may only include libraries of the form java.*)

/**
 * @author Jeff, Kevin, Tyler
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.*;

public class WikiCrawler {
	// static final String BASE_URL = "https://en.wikipedia.org";
	static final String BASE_URL = "http://web.cs.iastate.edu/~pavan";
	String fileWriteName, seed;
	int numPages;
	ArrayList<String> topic;
	Graph graph;
	int sleepCount = 0;
	
	//Graph
	public ArrayList<String> vertices;
	public ArrayList<String> edges;

	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics,
			String fileName) {
		seed = seedUrl;
		numPages = max;
		fileWriteName = fileName;
		topic = topics;
		graph = new Graph();
		vertices = new ArrayList<String>();
		vertices.add(seedUrl);
	}

	public void crawl() {
		Queue<Vertex> Q = new LinkedList<Vertex>();
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<String> urls = new ArrayList<String>();
		ArrayList<String> distinct = new ArrayList<String>();

		Vertex root = new Vertex(seed);
		Q.add(root);
		distinct.add(seed);

		while (!Q.isEmpty()) {
			Vertex currentPage = Q.poll();
			if (sleepCount % 25 == 0) {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					System.out.println("Sleep function failed.");
				}
			}
			urls = urlList(currentPage.data);

			sleepCount++;
			if (urls.size() != 0) {
				graph.vertices.add(currentPage);
				for (String link : urls) {
					Vertex u = new Vertex(link, currentPage);
					if(!distinct.contains(link)){ 
						if(distinct.size() == numPages){
							break;
						}
							else{
								distinct.add(link);
							}
						}
					ArrayList<String> usUrls = urlList(link);
					if (usUrls.size() != 0 && !visited.contains(currentPage.data + " " + link)) {		// essentially using strings as edges here cause they're easier to check
						visited.add(currentPage.data + " " + link);		// checking each link's urlList really doesn't slow it down all that much
						Q.add(u);
					}
				}
			}
		}

		if (!fileWriteName.contains(".txt")) {
			fileWriteName += ".txt";
		}
		try {
			PrintWriter writer = new PrintWriter(fileWriteName, "UTF-8");
			writer.print(numPages);
			for (int i = 0; i < visited.size(); i++) {
				writer.print("\n" + visited.get(i));
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("File writing has failed.");
		}
	}

	// Version 3 uses regex
	public ArrayList<String> extractLinks(String doc) { // TODO
		ArrayList<String> result = new ArrayList<String>();
		String[] l = doc.split("\r");
		for (int i = 0; i < l.length; i++) {
			String regex = "href=\"/wiki/.*?\"";
			Pattern string = Pattern.compile(regex);
			Matcher m = string.matcher(l[i]);
			while (m.find()) {
				String hrefLink = m.group().substring(
						m.group().indexOf('"') + 1, m.group().length() - 1);

				if (!hrefLink.contains("#") && !hrefLink.contains(":")
						&& !result.contains(hrefLink)) {
					result.add(hrefLink);
				}
			}
		}
		return result;
	}

	private ArrayList<String> urlList(String link) {
		// array of duplicate topics
		ArrayList<String> duplicates = new ArrayList<String>(topic);
		ArrayList<String> validUrls = new ArrayList<String>();
		String line;
		boolean pSection = false;

		try {
			URL url = new URL(BASE_URL + link);
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null)// while the line isnt null
			{
				if (line.contains("<p>") || pSection)// if the line is one we
														// look at
				{
					// check if this line contains an ending p tag so we stop
					// looking through
					pSection = !line.contains("</p>");

					// traverse through each topic
					for (int i = 0; i < topic.size(); i++) {
						// if the topic is in the line
						if (line.toLowerCase().contains(
								topic.get(i).toLowerCase())) {
							// remove it from duplicate array
							duplicates.remove(topic.get(i));
						}
					}

					ArrayList<String> extractedLinks = extractLinks(line);
					for (String str : extractedLinks) {
						if (!validUrls.contains(link)) {
							validUrls.add(str);
						}
					}
				}
			}

			// if nothing in duplicates, that means that we found the topic
			if (duplicates.size() != 0) {
				return new ArrayList<String>();
			} else {
				return validUrls;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new ArrayList<String>();
		}
	}
}
