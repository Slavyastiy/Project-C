/**
 * this class represents a Vertex in a graph
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Vertex {
	/**
	 * instance variables
	 */
	private int discoverTime;
	private int finishTime;
	private String name;
	private boolean visited;
	private boolean visitedForSCC;
	

	ArrayList <Edge> edges = new ArrayList<Edge>();// use it array list to produce Depth First Search
	ArrayList <Edge> reverseEdges = new ArrayList<Edge>();// use this array to find Strong Connected Components
	
	
	/**
	 * Constructor
	 * @param name gives name for the Vertex
	 */
	public Vertex(String name) {
		this.name=name;
		this.finishTime=0;
		this.discoverTime=0;
		this.visited=false;
		this.visitedForSCC=false;
		
		
	}
	
	
	/**
	 * 
	 * @return if it was visited while program 
	 * is looking for Strong Connected Components
	 */
	public boolean isVisitedForSCC() {
		return visitedForSCC;
	}
	
	
	
	/**
	 * 
	 * @param visitedForSCC set boolean variable as visited
	 * during Strong Connected Components search
	 */
	public void setVisitedForSCC(boolean visitedForSCC) {
		this.visitedForSCC = visitedForSCC;
	}

	
	
	/**
	 * 
	 * @return iterator for edges array list
	 */
	public Iterator getEdgeIterator() {
		return edges.iterator();
	}
	
	
	
	/**
	 * 
	 * @return iterator for reverseEddges list
	 */
	public Iterator getReverseEdgeIterator() {
		return reverseEdges.iterator();
	}
	
	
	
	
	/**
	 * finds the minimum weight edge 
	 * @return the end vertex of minimum weighted edge or null
	 */
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
	 * check for edges from vertex while checking 
	 * for Strong Connected Components
	 * @return the end Vertex of edge or null
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
	 * this method determine type of each edge in the 
	 * vertex edges list
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
	
	
	/**
	 * 
	 * @return discover time
	 */
	public int getDiscoverTime() {
		return discoverTime;
	}


	/**
	 * 
	 * @param discoverTime
	 */
	public void setDiscoverTime(int discoverTime) {
		this.discoverTime = discoverTime;
	}


	/**
	 * 
	 * @return
	 */
	public int getFinishTime() {
		return finishTime;
	}

	/**
	 * 
	 * @param finishTime
	 */

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;

	
	}


	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}


	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * 
	 * @return
	 */
	public boolean isVisited() {
		return visited;
	}


	/**
	 * 
	 * @param visited
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	/**
	 * prints all information about vertex
	 */
	public String toString() {
		//String outputString= " The vertex " + this.name  + " "  + edges.toString();
		String outputString=this.name + "  " + this.getDiscoverTime()  + "  " + this.getFinishTime();
		
		return outputString;
		
		
	}
	
}
