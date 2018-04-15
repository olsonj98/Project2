import java.util.ArrayList;

public class Vertex {

	public ArrayList<Vertex> to = new ArrayList<Vertex>();
	public String data;
	
	public Vertex(){
		this.data = "";
	}
	
	public Vertex(String url){
		this.data = url;
	}
}
