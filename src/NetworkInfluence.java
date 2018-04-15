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
		// implementation

		// replace this:
		return null;
	}

	public int distance(String u, String v)
	{
		// implementation:

		// replace this:
		return -1;
	}

	public int distance(ArrayList<String> s, String v)
	{
		// implementation

		// replace this:
		return -1;
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
