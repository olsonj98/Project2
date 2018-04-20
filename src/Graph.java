import java.util.ArrayList;


public class Graph {

	public ArrayList<Vertex> vertexes;
	public ArrayList<Edge> edges;
	
	public Graph(ArrayList<Vertex> V, ArrayList<Edge> E){
		vertexes = V;
		edges = E;
	}
	
	public Graph(){
		vertexes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}
	
}
