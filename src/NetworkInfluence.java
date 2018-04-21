// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may only include libraries of the form java.*)

/**
* @authors Jeff, Kevin, Tyler
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class NetworkInfluence
{
	public Graph graph = new Graph();
	public int numVertices;
	
	// NOTE: graphData is an absolute file path that contains graph data, NOT the raw graph data itself
	public NetworkInfluence(String graphData)
	{
		File file = new File(graphData);
		Scanner sc;
		boolean fromFound, toFound;
		try {
			sc = new Scanner(file);
			if(sc.hasNextLine()) numVertices = sc.nextInt();
			
			while(sc.hasNextLine()){
				Vertex from = new Vertex(sc.next());
				Vertex to = new Vertex(sc.next());
				Edge edge = new Edge(from, to);
				graph.edges.add(edge);
				
				fromFound = false;
				toFound = false;
				for(Vertex v : graph.vertices){
					if(v.same(from)) {
						fromFound = true;
					}
					if(v.same(to)){
						toFound = true;
					}
				}
				
				if(!fromFound){
					graph.vertices.add(from);
				}
				
				if(!toFound){
					graph.vertices.add(to);
				}
				
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		printV(graph.vertices);
		printE(graph.edges);
		printArrayList(shortestPath("Ames", "Chicago"));
	}

	public int outDegree(String v)
	{
		ArrayList<String> neighbors = neighbors(v, graph.edges);
		return neighbors.size();
	}

	public ArrayList<String> shortestPath(String u, String v)
	{
		/*
		 * 1. Input: Directed Graph G = (V, E), and root <in> V . 2. Initialize
		 * a Queue Q and a list visited. 3. Place root in Q and visited. 4.
		 * while Q is not empty Do (a) Let v be the first element of Q. (b) For
		 * every edge <v, u> <in> E DO If u <in> visited add u to the end of Q,
		 * and add u to visited. If you output the vertices in visited, that
		 * will be BFS traversal of the input graph
		 */

		ArrayList<String> path = new ArrayList<String>();
		Queue<String> Q = new LinkedList<String>();
		LinkedList<String> visited = new LinkedList<String>();
		HashMap<String, String> parents = new HashMap<String, String>();
		HashMap<String, Integer> distances = new HashMap<String, Integer>();
		
		Q.add(u);
		visited.add(u);
		Vertex k = null;
		
		for (int i = 0; i < graph.vertices.size(); i++) {
			k = graph.vertices.get(i);
			distances.put(k.data, Integer.MAX_VALUE);
			parents.put(k.data, null);
		}

		distances.put(u, 0);
		
		while (!Q.isEmpty()) {
			String next = Q.poll();
			if (next.equals(v)) {
				break; // path found from u to v
			} else {
				ArrayList<String> adj = neighbors(next, graph.edges);
				 if (adj != null) {
					 // adjacent vertices exist
					 for (int i = 0; i < adj.size(); i++) {
						 String e = adj.get(i);
						 if (distances.get(e) > distances.get(next) + 1) {
							 distances.put(e, distances.get(next) + 1);
							 parents.put(e, next);
						 }
						 if (!visited.contains(e)) {
							 Q.add(e);
							 visited.add(e);
						 }
					 }
				 }
			}
			
			
		}
		path.add(v);
		String temp = parents.get(v);
		if (temp == null) {
			path.add(v);
			return path;
		}
		while (!temp.equals(u)) {
			path.add(temp);
			temp = parents.get(temp);
		}
		path.add(u);
		Collections.reverse(path);

		return path;
	}
	
	// finds neighboring Vertices to v
	public static ArrayList<String> neighbors(String v, ArrayList<Edge> es) {
		ArrayList<String> adj = new ArrayList<String>();
		for (Edge e : es) {
			if (e.from.data.equals(v)) {
				adj.add(e.to.data);
			}
		}
		return adj;
	}

	public int distance(String u, String v)
	{
		ArrayList<String> path = shortestPath(u,v);
		return path.size() - 1;
	}

	public int distance(ArrayList<String> s, String v)
	{
		int shortest = Integer.MAX_VALUE;
		for (String str : s) {
			int temp = distance(str, v);
			if (temp < shortest)
				shortest = temp;
		}
		return shortest;
	}

	public float influence(String u)
	{
		float first;
		float value;
		float sum = 0;
		int n = graph.vertices.size();
		for (int i = 0; i < n; i++) {
			ArrayList<String> ys = new ArrayList<String>();
			for (int j = 0; j < n; j++) {
				String y = graph.vertices.get(j).data;
				if (distance(u, y) == i) {
					ys.add(y);
				}
				
			}
			first = (float) (1 / (Math.pow(2, i)));
			value = first * ys.size();
			sum += value;
		}

		return sum;
	}

	public float influence(ArrayList<String> s)
	{
		float first;
		float value;
		float sum = 0;
		int n = graph.vertices.size();
		for (int i = 0; i < n; i++) {
			ArrayList<String> ys = new ArrayList<String>();
			for (int j = 0; j < n; j++) {
				String y = graph.vertices.get(j).data;
				if (distance(s, y) == i) {
					ys.add(y);
				}
				
			}
			first = (float) (1 / (Math.pow(2, i)));
			value = first * ys.size();
			sum += value;
		}

		return sum;
	}

	public ArrayList<String> mostInfluentialDegree(int k)
	{
		// implementation

		// replace this:
		return null;
	}

	public ArrayList<String> mostInfluentialModular(int k)
	{
		// implementation

		// replace this:
		return null;
	}

	public ArrayList<String> mostInfluentialSubModular(int k)
	{
		// implementation

		// replace this:
		return null;
	}
	
	/*
	 * prints out the array for testing
	 */
	public static void printArrayList(ArrayList<String> arr) {
		System.out.print("{");
		for (int j = 0; j < arr.size(); j++)
			if (j != arr.size() - 1)
				System.out.print(arr.get(j) + ", ");
			else
				System.out.println(arr.get(j) + "}");
	}
	
	/*
	 * prints out the vertices for testing
	 */
	public static void printV(ArrayList<Vertex> arr) {
		System.out.print("{");
		for (int j = 0; j < arr.size(); j++)
			if (j != arr.size() - 1)
				System.out.print(arr.get(j).data + ", ");
			else
				System.out.println(arr.get(j).data + "}");
	}
	
	/*
	 * prints out the edges for testing
	 */
	public static void printE(ArrayList<Edge> arr) {
		System.out.print("{");
		for (int j = 0; j < arr.size(); j++)
			if (j != arr.size() - 1)
				System.out.print(arr.get(j).from.data + " -> " + arr.get(j).to.data + ", ");
			else
				System.out.println(arr.get(j).from.data + " -> " + arr.get(j).to.data + "}");
	}
}
