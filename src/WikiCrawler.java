// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may only include libraries of the form java.*)

/**
 * @author Hugh Potter
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
	ArrayList<Vertex> verticies;

	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics,
			String fileName) {
		seed = seedUrl;
		numPages = max;
		fileWriteName = fileName;
		topic = topics;
		verticies = new ArrayList<Vertex>();
	}

	public void crawl() {
		try {
			boolean result = checkSeed();
			if (result) {
				Vertex currentPage = new Vertex();
				ArrayList<String> results;

				Vertex s = new Vertex();
				s.data = seed;

				Queue<Vertex> Q = new LinkedList<Vertex>();
				Q.add(s);

				LinkedList<String> visited = new LinkedList<String>();
				visited.add(s.data);

				while (Q.peek() != null && visited.size() < numPages)// limits
																		// the
																		// pages
																		// from
																		// keeping
																		// on
																		// searching
				{
					currentPage = Q.poll();
					boolean check = checkPage(currentPage);
					if (check) {
						URL url = new URL(BASE_URL + currentPage.data);
						InputStream is = url.openStream();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(is));

						String temp = builder(br);
						results = extractLinks(temp);

						for (String r : results) {
							if (!visited.contains(r)) {// make sure it actually
														// can go through the
														// new vertex in queue
								Vertex v = new Vertex();
								v.data = r;
								Q.add(v);
								visited.add(v.data);
								currentPage.to.add(v);
								verticies.add(v);
							}
						}

					}
				}
				// write the results // TODO //right now its using visited, when
				// visited should be every node, not just the ones that worked
				if (!fileWriteName.contains(".txt"))
					fileWriteName += ".txt";
				PrintWriter writer = new PrintWriter(fileWriteName, "UTF-8");
				for (String vis : visited) {
					// System.out.println(vis);
					writer.println(vis);
				}
				writer.close();
			}
		} catch (Exception e) {
			System.out.println("it done broke");
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

	private String builder(BufferedReader br) throws Exception {
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
