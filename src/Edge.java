/**
 * this class represents edges from directed graph 
 * @author Vyacheslav
 *
 */
public class Edge {
	/**
	 * instance variables
	 */
	private Vertex start;
	private Vertex  end;
	private String type;
	private int weight;
	
	/**
	 * Constructor
	 * @param start start vertex of the edge
	 * @param end end vertex of the edge
	 */
	public Edge(Vertex start, Vertex end) {
		this.start=start;
		this.end=end;
		this.type=null;
		this.weight=-1;
	}

	
	/**
	 * 
	 * 
	 */
	
	public int getWeight() {
		return weight;
	}
	
	/**
	 * 
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Vertex getStart() {
		return start;
	}
	
	/**
	 * 
	 * @param start
	 */
	public void setStart(Vertex start) {
		this.start = start;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vertex getEnd() {
		return end;
	}
	
	
	/**
	 * 
	 * @param end
	 */
	public void setEnd(Vertex end) {
		this.end = end;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	/**
	 * 
	 * prints all information about edge
	 * 
	 */
	public String toString() {
		String outputString= "\nThe start vetrex " + start.getName()  + " The end vertex "  + end.getName();
		outputString+=" The weight  is: " + this.weight;
		return outputString;
		
}
}
