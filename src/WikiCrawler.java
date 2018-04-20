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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WikiCrawler {
	static final String BASE_URL = "https://en.wikipedia.org";
	String fileWriteName, seed;
	int numPages;
	ArrayList<String> topic;
	Graph graph;
	int sleepCount = 0;

	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics,
			String fileName) {
		seed = seedUrl;
		numPages = max;
		fileWriteName = fileName;
		topic = topics;
		graph = new Graph();
	}

	public void crawl() throws Exception{
		Queue<Vertex> Q = new LinkedList<Vertex>();
		ArrayList<Vertex> visited = new ArrayList<Vertex>();
		ArrayList<String> urls = new ArrayList<String>();
		String page;

		Vertex root = new Vertex(seed);
		Q.add(root);
		visited.add(root);

		while (!Q.isEmpty() && graph.vertexes.size() <= numPages) {
			Vertex currentPage = Q.poll();
			if (checkPage(currentPage)) {
				graph.vertexes.add(currentPage);
				page = builder(currentPage.data);
				urls = extractLinks(page);
				for (String link : urls) {
					Vertex u = new Vertex(link, currentPage);
					if (!visited.contains(u)) {
						visited.add(u);
						Q.add(u);
					}
				}
			}
		}
		
		for(Vertex v : graph.vertexes){
			if(v.parent != null){
				graph.edges.add(new Edge(v.parent, v));
			}
		}

		if (!fileWriteName.contains(".txt")) {
			fileWriteName += ".txt";
		}
		try {
			PrintWriter writer = new PrintWriter(fileWriteName, "UTF-8");
			for (Edge e : graph.edges) {
				writer.println(e.from.data + " " + e.to.data);
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("File writing has failed.");
		}
	}

	// NOTE: extractLinks takes the source HTML code, NOT a URL
	public ArrayList<String> extractLinks(String doc) { // TODO
		ArrayList<String> results = new ArrayList<String>();
		int index = 0;

		while (results.size() < numPages && doc.indexOf("href") != -1) {
			int startIndex = doc.indexOf("href", index);
			index = startIndex + 7;
			int endIndex = doc.indexOf("\"", startIndex + 6);
			int strLen = endIndex - startIndex;
			String possibleLink = doc.substring(startIndex + 6, startIndex
					+ strLen);
			if (!possibleLink.contains("#")
					&& !possibleLink.contains(":")
					&& (possibleLink.length() >= 6 && possibleLink.substring(0,
							6).equals("/wiki/"))) {
				Vertex v = new Vertex();
				v.data = possibleLink;
				try {
					boolean check = checkPage(v);
					if (check) {
						results.add(possibleLink);
					}
				} catch (Exception e) {

				}

			}
		}
		return results;
	}

	private String builder(String link) throws Exception {
		URL url = new URL(BASE_URL + link);
		InputStream is = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder result = new StringBuilder();
		try {
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return result.toString();
	}

	private boolean checkPage(Vertex v) throws Exception {
		URL url = new URL(BASE_URL + v.data);
		InputStream is = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		ArrayList<String> duplicates = new ArrayList<String>(topic);// array of
																	// duplicate
																	// topics
		String line;
		try {
			while ((line = br.readLine()) != null)// while the line isnt null
			{
				if (line.contains("<p>"))// if the line is one we look at
				{
					for (int i = 0; i < topic.size(); i++)// traverse through
															// each topic
					{
						if (line.toLowerCase().contains(
								topic.get(i).toLowerCase()))// if the topic is
															// in the line
						{
							duplicates.remove(topic.get(i));// remove it from
															// duplicate array
						}
					}
				}

			}
			if (duplicates.size() != 0)// if nothing in duplicates, that means
										// that we found the topic
			{
				return false;
			} else {
				return true;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	private boolean checkSeed() throws Exception {
		URL url = new URL(BASE_URL + seed);
		InputStream is = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		ArrayList<String> duplicates = new ArrayList<String>(topic);// array of
																	// duplicate
																	// topics
		String line;
		try {
			while ((line = br.readLine()) != null)// while the line isnt null
			{
				if (line.contains("<p>"))// if the line is one we look at
				{
					for (int i = 0; i < topic.size(); i++)// traverse through
															// each topic
					{
						if (line.toLowerCase().contains(
								topic.get(i).toLowerCase()))// if the topic is
															// in the line
						{
							duplicates.remove(topic.get(i));// remove it from
															// duplicate array
						}
					}
				}

			}
			if (duplicates.size() != 0)// if nothing in duplicates, that means
										// that we found the topic
			{
				return false;
			} else {
				return true;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}
}
