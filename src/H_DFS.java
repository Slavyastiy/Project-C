/**
 * This program make heuristic depth-first-search (H-DFS) and
 * print information about the result
 * 1. Prints the order how the Vertexes has been visited
 * 2. Prints the Vertexes name, discovery time and finish time
 * 3. Prints topological order
 * 4. Prints Strong Connected Components
 * 
 *  @author Vyacheslav Makharovich
 *  
 *  for this program I use different material from the Internet
 *  that I modified in order to fit for this program's goal
 *  it is hard to identify any source because there is no particular
 *  source. I just read a lot of  different articles, web sites such as 
 *  stackoverflow.com, discussed implementation with my tutor Richard Ketelsen.
 *  All those things together helped me to create my own code.
 * 
 * 
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class H_DFS {
	
	static String point= ".";// use it when reading output file
	static int counter=1;// set discovery and finish time
	

   private static String chooseFile() {

      JFileChooser chooser;
      String fileName;
      
      FileNameExtensionFilter filter;
      int r;
      fileName = null;
      chooser = new JFileChooser();
      filter = new FileNameExtensionFilter("Text Files", "dat", "txt");
      chooser.setFileFilter(filter);
      chooser.setCurrentDirectory(new File("."));
      r = chooser.showOpenDialog(null);
      if (r == JFileChooser.APPROVE_OPTION)
         fileName = chooser.getSelectedFile().getAbsolutePath();

      return (fileName);

   }
/**
 * This method read input text file and store the input value
 * 
 * @param iFile name of input text file
 * @param oFile name for output text file 
 */
   private static void processFile(String iFile, String oFile) {
      /**
       *  instance variables
       */
	  BufferedReader iBuffer;
      FileReader iReader;
      int lineNumber;
      String [] values;
      int pointer=0;// use it for adjacency array
      int j=0; // use it for adjacency array
      Integer number;
      Stack <Vertex> vertexStack=new Stack<Vertex>();//use this stack as argument for DFS method
      Stack <Vertex> reverseStack=new Stack<Vertex>();// use this stack in DFS and in SCC methods to get an order how nodes are discovered
      Stack <Vertex> topologicalStack = new Stack <Vertex>();// use this stack in DFS method to get topological order
      ArrayList <Vertex> vertexesList = new ArrayList <Vertex>();// list of all Vertexes
      ArrayList<ArrayList<String>> strongCC= new ArrayList <>();// holds array Lists of Strong Connected Components
      ArrayList <Vertex> howWasVistited= new ArrayList <Vertex>();// holds the order in which Vertexes have been visited
      
      lineNumber = 0;
      
      
      try {
         iReader = new FileReader(iFile);
         iBuffer = new BufferedReader(iReader);
		
         FileWriter oWriter = new FileWriter(oFile);
         BufferedWriter oBuffer = new BufferedWriter(oWriter);
         int intLength = 0;
        
        /**
         *  read first line to get number of vertexes
         */
        values = iBuffer.readLine().trim().split(" +");
        intLength=values.length;

        /**
         *  create array of vertexes and adjacency array of weight of edges
         */
        String [] vertexes = new String [intLength-1];
        Integer [][] weight = new Integer [intLength-1][intLength-1];
        String line=null;
        
       /**
        * read input text file and store its value to 
        * array vertexes,  and store weight
        * between those vertexes in two dimensions array 
        */
        while((line = iBuffer.readLine())!=null){
        	values=line.trim().split(" +");
        	vertexes[j]= values[0];//add first token to city array
            for(int b=1;b<values.length;b++){
               if (!(values[b].equals(point)) ) {
            	   number=Integer.parseInt(values[b]);
               	weight[j][pointer]= number;//add numbers from second token to the b<values.length
                   pointer++;  
               }else {
            	   weight[j][pointer]= -1;
            	   pointer++;
               }
            	
               }
            j++;
            pointer=0;
           
        	}
        
       
      
        iBuffer.close();
        
        
        /**
         * gets name from vertexes array
         * and create Vertexes
         */
        for (int y=0; y < vertexes.length;y++) {
        	String name=vertexes[y];
        	Vertex vert= new Vertex(name);
        	vertexesList.add(vert);
        	
        }
        	
       
        /**
         * creates edges and assign its weight
         * adds edges to Vertexe's edges List and
         * to reverse Vertexe's List that is used 
         * to find Strong Connected Components
         */
        
        for(int l =0; l<vertexesList.size();l++) {
        	for(int v=0; v<weight.length;v++) {
        		if(weight[l][v]>0) {
        			Vertex start=vertexesList.get(l);
        			Vertex end=vertexesList.get(v);
        			Edge edge=new Edge(start,end);
        			Edge reverseEdge=new Edge(end,start);
        			edge.setWeight(weight[l][v]);
        			start.edges.add(edge);
        			end.reverseEdges.add(reverseEdge);
        		}
        	}
        }
        
     	
        /**
         * 
         * calls NFFS method to get Depth First Search  
         */
        
        H_DFS.NDFS(vertexStack, reverseStack, topologicalStack, vertexesList.get(0), howWasVistited);
      
        /**
         * prints and writes the order in which vertexes
         * have been visited
         */
        for (int k =0; k<howWasVistited.size();k++) {
        	String name=howWasVistited.get(k).getName();
        	oBuffer.write(name);
        }
        oBuffer.newLine();
        oBuffer.newLine();
      
        /**
         * prints Vertexes and its discovery and finish time
         */
        
        Iterator <Vertex> iter1= vertexesList.iterator();
        while(iter1.hasNext()) {
        	Vertex vertex= iter1.next();
        	System.out.println(vertex);
        	String name = vertex.getName();
        	int discTime= vertex.getDiscoverTime();
        	int finishTime= vertex.getFinishTime();
        	oBuffer.write(name +" ");
        	oBuffer.write(discTime + " ");
        	oBuffer.write(finishTime + " ");
        	oBuffer.newLine();
        	
        }
       oBuffer.newLine();
       System.out.println();
 	  
        
        /**
         * prints and writes my edges and its type
         */
        Iterator <Vertex> iter2= vertexesList.iterator();
        while(iter2.hasNext()) {
        	Vertex vertex=iter2.next();
        	vertex.setEdgeType();
        	Iterator iter3=vertex.getEdgeIterator();
        	while(iter3.hasNext()) {
        		Edge edge=(Edge)iter3.next();
        		oBuffer.write(edge.getStart().getName() + "" + edge.getEnd().getName() + "  " + edge.getType());
        		oBuffer.newLine();
        		System.out.println( edge.getStart().getName() + "" + edge.getEnd().getName() + "  " + edge.getType() );
        	}
        	
         }
         oBuffer.newLine();
         System.out.println();
        
        
       /**
        * prints and writes topological order of the vertexes
        */
         while(!(topologicalStack.isEmpty()) ) {
        	Vertex vertex = topologicalStack.pop();
        	oBuffer.write(vertex.getName());
        	System.out.print(vertex.getName());
         }
         oBuffer.newLine();
         oBuffer.newLine();
        /**
         * calling method to find strong connected components 
         */
         H_DFS.SCC(reverseStack,strongCC );
         
        
        System.out.println();
        System.out.println();
        
       /**
        * sort all strong connected components in alphabetical order
        * prints and writes all strong connected components 
        */
       for (int i =0; i<strongCC.size();i++) {
    	  ArrayList <String> StrConComp=strongCC.get(i);
    	  H_DFS.SortArrayList(StrConComp);
    	  for(int h=0;h<StrConComp.size();h++ ) {
    		   String name=StrConComp.get(h);
    		   System.out.print(name);
    		   oBuffer.write(name);
    		   
    	   }
    	   oBuffer.newLine();
    	   System.out.println();
    	   }
        
        
        oBuffer.close();
      } catch (Exception e) {
          System.err.println("Reading failed at line " + lineNumber);
          
          e.printStackTrace(System.err);                
       }
   }
   
   
   
  /**
   * this method produce heuristic depth-first-search 
   * @param stack
   * @param reverseStack
   * @param topolStack
   * @param startNode
   * @param visitVertex tracks the order in which vertexes have been visited
   */
   
   public static void NDFS(Stack <Vertex> stack, Stack <Vertex> reverseStack, Stack <Vertex> topolStack, Vertex startNode, 
		   ArrayList <Vertex> visitVertex ) {
	 
	   Vertex startVertex;
	   stack.push(startNode);
	   while(! (stack.isEmpty())){
		   
	   startVertex=stack.pop();
		 
	   if (! startVertex.isVisited()) {
		   startVertex.setDiscoverTime(counter);
		   counter++;
		   startVertex.setVisited(true);
		   visitVertex.add(startVertex);
		   System.out.print(startVertex.getName());
			  
		  }
		
	   Vertex nextVertex=startVertex.getMinEdgeEndNode();
	   if(nextVertex!=null) {
		   stack.push(startVertex);
		   stack.push(nextVertex);
	   }else {
		   startVertex.setFinishTime(counter);
		   reverseStack.push(startVertex);
		   topolStack.push(startVertex);
		   counter++;
		  }   
	   }
	   System.out.println();
	   System.out.println();
	   
	}
   
		
   
   /**
    * This method finds Strong Connected Components 
    * @param revStack topological order
    * @param sCC gets all strong connected components as array list
    */
   public static void SCC(Stack <Vertex> revStack, ArrayList <ArrayList<String>> sCC) {
	   Stack <Vertex> stack=new Stack<Vertex>();
	   while(!(revStack.isEmpty())){
		   ArrayList <String> strConectedComp= new ArrayList <String> ();
		   Vertex start = revStack.pop();
		   if(!(start.isVisitedForSCC())){
			   stack.push(start);
			   while(!(stack.isEmpty())) {
				   Vertex newStart=stack.pop();
				   newStart.setVisitedForSCC(true);
				   Vertex nextVertex = newStart.getSccVertex();
				   if(nextVertex != null) {
					   stack.push(newStart);
					   stack.push(nextVertex);
				   }else {
					   strConectedComp.add(newStart.getName());
					 
				 }
			 
			   }
			
			 sCC.add(strConectedComp);
		 }
		   
		   
	   }
	   
	   
   }
   /**
    * sorts all input in alphabetical Order
    * @param array
    */
   public static void SortArrayList(ArrayList <String> array) {
	   int size=array.size();
	   String temp=null;
	   for(int i=0;i<size;i++ ) {
		   for(int j=1;j<(size-i); j++) {
			   if(array.get(j-1).compareTo(array.get(j))>0) {
				   temp = array.get(j-1);  
                   array.set((j-1), array.get(j));  
                   array.set(j, temp);  
			   }
			   
		   }
	   }
   }
		  
	  
	  
	 
	  
		 
 	   
   
   
   public static void main(String[] args) {
	   int i;
	      String iFile;
	      String oFile;

	      iFile = chooseFile();
	      if (iFile != null) {
	         i = iFile.lastIndexOf('.');
	         if (i >= 0)
	            oFile = iFile.substring(0, i) + "_out" + iFile.substring(i);
	         else
	            oFile = iFile + "_out";
	         processFile(iFile, oFile);
	      }

	   }
 

}
