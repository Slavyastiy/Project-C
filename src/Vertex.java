import java.util.ArrayList;
import java.util.Iterator;

public class Vertex {
	
	private int discoverTime;
	private int finishTime;
	private String name;
	private boolean visited;
	private boolean visitedForSCC;
	

	ArrayList <Edge> edges = new ArrayList<Edge>();
	ArrayList <Edge> reverseEdges = new ArrayList<Edge>();
	
	
	
	public Vertex(String name) {
		this.name=name;
		this.finishTime=0;
		this.discoverTime=0;
		this.visited=false;
		this.visitedForSCC=false;
		
		
	}

	public boolean isVisitedForSCC() {
		return visitedForSCC;
	}

	public void setVisitedForSCC(boolean visitedForSCC) {
		this.visitedForSCC = visitedForSCC;
	}

	/**
	 * 
	 * @return
	 */
	public Iterator getEdgeIterator() {
		return edges.iterator();
	}
	
	public Iterator getReverseEdgeIterator() {
		return reverseEdges.iterator();
	}
	
	public Vertex getMinEdgeEndNode() {
		Vertex minEdge=null;
		Integer minWeight=Integer.MAX_VALUE;
		Iterator iter=this.getEdgeIterator();
		while(iter.hasNext()) {
			 Edge edge=(Edge) iter.next();
			 if (!(edge.getEnd().isVisited())){
				 if((edge.getWeight()<minWeight)) {
					 minEdge=edge.getEnd();
						minWeight=edge.getWeight();
				 }
			 }
			
			
		}
		 if(minEdge !=null) {
			 Iterator iter1=this.getEdgeIterator();
			 while(iter1.hasNext()) {
				 Edge edge=(Edge)iter1.next();
				 if(edge.getEnd().equals(minEdge)) {
					 edge.setType("T");
				 }
			 }
		 }
			
		return minEdge;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vertex getSccVertex() {
		Vertex sccVertex=null;
		Iterator iter=this.getReverseEdgeIterator();
		while(iter.hasNext()) {
			Edge edge=(Edge)iter.next();
			if(!(edge.getEnd().isVisitedForSCC())) {
				sccVertex=edge.getEnd();
				return sccVertex;
			}
		}
		return sccVertex;
	}

	/**
	 * 
	 */
	public void setEdgeType() {
		Iterator iter=this.getEdgeIterator();
		while(iter.hasNext()) {
			Edge edge=(Edge)iter.next();
			if (edge.getType() == null) {
					if((edge.getStart().getDiscoverTime()<edge.getEnd().getDiscoverTime())&&(edge.getStart().getFinishTime()>edge.getEnd().getFinishTime())) {
						edge.setType("F");
					}else if ((edge.getStart().getDiscoverTime()>edge.getEnd().getDiscoverTime())&&(edge.getStart().getFinishTime()<edge.getEnd().getFinishTime())) {
						edge.setType("B");
					}else if((edge.getEnd().getDiscoverTime()<edge.getEnd().getFinishTime())&&(edge.getEnd().getFinishTime()<edge.getStart().getDiscoverTime())&&(edge.getStart().getDiscoverTime()<edge.getStart().getFinishTime())) {
						edge.setType("C");
				}
				
			}
			
		    }
		
		
	}
	
	public int getDiscoverTime() {
		return discoverTime;
	}



	public void setDiscoverTime(int discoverTime) {
		this.discoverTime = discoverTime;
	}



	public int getFinishTime() {
		return finishTime;
	}



	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;

	
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public boolean isVisited() {
		return visited;
	}



	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public String toString() {
		//String outputString= " The vertex " + this.name  + " "  + edges.toString();
		String outputString=this.name + "  " + this.getDiscoverTime()  + "  " + this.getFinishTime();
		
		return outputString;
		
		
	}
	
}
