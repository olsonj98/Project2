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
import java.util.Scanner;

public class NetworkInfluence
{
	public ArrayList<Vertex> graph = new ArrayList<Vertex>();
	public int numVertices;
	
	// NOTE: graphData is an absolute file path that contains graph data, NOT the raw graph data itself
	public NetworkInfluence(String graphData)
	{
		File file = new File(graphData);
		Scanner sc;
		boolean firstFound, secondFound;
		Vertex first = new Vertex();
		Vertex second = new Vertex();
		try {
			sc = new Scanner(file);
			if(sc.hasNextLine()) numVertices = sc.nextInt();
			
			while(sc.hasNextLine()){
				String[] edge = {sc.next(), sc.next()};
				
				firstFound = false;
				secondFound = false;
				for(Vertex v : graph){
					if(v.data.equals(edge[0])){
						firstFound = true;
						first = v;
					}
					if(v.data.equals(edge[1])){
						secondFound = true;
						second = v;
					}
				}
				
				if(!firstFound){
					first = new Vertex(edge[0]);
					graph.add(first);
				}
				
				if(!secondFound){
					second = new Vertex(edge[1]);
					graph.add(second);
				}
				
				first.to.add(second);
				second.from.add(first);
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int outDegree(String v)
	{
		for(Vertex vertex : graph){
			if(vertex.data.equals(v)){
				return vertex.to.size();
			}
		}

		return -1;
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

		ArrayList<String> arr = new ArrayList<String>();
		Queue<Vertex> Q = new LinkedList<Vertex>();
		LinkedList<Vertex> visited = new LinkedList<Vertex>();

		Vertex root;
		for (Vertex ver : graph) {
			if (ver.data.equals(u)) {
				root = ver;
				Q.add(root);
				visited.add(root);
				break;
			}
		}

		while (!Q.isEmpty()) {
			Vertex temp = Q.poll();
			for (Vertex edge : temp.to) {
				if (!visited.contains(edge)) {
					Q.add(edge);
					visited.add(edge);
				}
			}
		}

		for (Vertex point : visited) {
			arr.add(point.data);
		}

		return arr;
	}

	public int distance(String u, String v)
	{
		ArrayList<String> path = shortestPath(u,v);
		return path.size();
	}

	public int distance(ArrayList<String> s, String v)
	{
		int shortest = Integer.MAX_VALUE;
		for(String str : s){
			int result = distance(str, v);
			if(result < shortest && result != 0) shortest = result;
		}
		return shortest;
	}

	public float influence(String u)
	{
		// implementation

		// replace this:
		return -1f;
	}

	public float influence(ArrayList<String> s)
	{
		// implementation

		// replace this:
		return -1f;
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
	
	private class Vertex{
		ArrayList<Vertex> to = new ArrayList<Vertex>();
		ArrayList<Vertex> from = new ArrayList<Vertex>();
		String data;
		
		public Vertex(){
			data = "";
		}
		
		public Vertex(String str){
			data = str;
		}
	}
}
