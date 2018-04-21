public class Vertex {
	public String data;
	public Vertex parent;
	
	public Vertex(){
		this.data = "";
		this.parent = null;
	}
	
	public Vertex(String url){
		this.data = url;
		this.parent = null;
	}
	
	public Vertex(String url, Vertex p){
		this.data = url;
		this.parent = p;
	}
	
	public boolean same(Vertex v) {
		if (this.data.equals(v.data))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
}
