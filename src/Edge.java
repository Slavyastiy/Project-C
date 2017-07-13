
public class Edge {

	private Vertex start;
	private Vertex  end;
	private String type;
	private int weight;
	
	public Edge(Vertex start, Vertex end) {
		this.start=start;
		this.end=end;
		this.type=null;
		this.weight=-1;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Vertex getStart() {
		return start;
	}

	public void setStart(Vertex start) {
		this.start = start;
	}

	public Vertex getEnd() {
		return end;
	}

	public void setEnd(Vertex end) {
		this.end = end;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		String outputString= "\nThe start vetrex " + start.getName()  + " The end vertex "  + end.getName();
		outputString+=" The weight  is: " + this.weight;
		return outputString;
		
}
}
