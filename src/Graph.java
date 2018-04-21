import java.util.ArrayList;


public class Graph {

	public ArrayList<Vertex> vertices;
	public ArrayList<Edge> edges;
	
	public Graph(ArrayList<Vertex> V, ArrayList<Edge> E){
		vertices = V;
		edges = E;
	}
	
	public Graph(){
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}
	
}
