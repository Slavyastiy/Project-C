
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileIO {
	
	static Integer  minDistance = 0;
	static int indexForPath=0;
	static int n=0;
	static String point= ".";
	static String name;
	static int counter=1;
	

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
 * to array cities, startArray, and 2d distance array
 * @param iFile name of input text file
 */
   private static void processFile(String iFile) {
      /**
       *  instance variables
       */
	  BufferedReader iBuffer;
      FileReader iReader;
      int lineNumber;
      String [] values;
      int pointer=0;
      int j=0; 
      Integer number;
      Stack <Vertex> vertexStack=new Stack<Vertex>();
      Stack <Vertex> reverseStack=new Stack<Vertex>();
     ArrayList <Vertex> vertexesList = new ArrayList <Vertex>();
      
      lineNumber = 0;
      try {
         iReader = new FileReader(iFile);
         iBuffer = new BufferedReader(iReader);
        // oWriter = new FileWriter(oFile);
        // oBuffer = new BufferedWriter(oWriter);
         int intLength = 0;
        
        /**
         *  read first line to get number of cities 
         */
        values = iBuffer.readLine().trim().split(" +");
        intLength=values.length;

        /**
         *  create array of cities and adjacency array of distances
         */
        String [] nodes = new String [intLength-1];
        Integer [][] distance = new Integer [intLength-1][intLength-1];
        String line=null;
        
       /**
        * read input text file and store its value to 
        * array cities, citiesStart, and store distance
        * between those cities in two dimensions array 
        */
        while((line = iBuffer.readLine())!=null){
        	values=line.trim().split(" +");
        	nodes[j]= values[0];//add first token to city array
            for(int b=1;b<values.length;b++){
               if (!(values[b].equals(point)) ) {
            	   number=Integer.parseInt(values[b]);
               	distance[j][pointer]= number;//add numbers from second token to the b<values.length
                   pointer++;  
               }else {
            	   distance[j][pointer]= -1;
            	   pointer++;
               }
            	
               }
            j++;
            pointer=0;
           
        	}
        
        /**
         * Print list of cities
         */
        
        System.out.print( "The Nodes are:  ");
           for (int k=0; k< nodes.length; k++){
        	   System.out.print(nodes[k] + " ");
           }
        System.out.println("");
        
        /**
         * prints matrix array
         */
        for(int i=0; i<distance.length; i++) {
        	for(int h =0; h< distance.length;h++) {
        		System.out.print(distance[i][h] + "  ");
        		
        	}
        	System.out.println();
        }
        iBuffer.close();
        
        
        
        for (int y=0; y < nodes.length;y++) {
        	name=nodes[y];
        	Vertex vert= new Vertex(name);
        	vertexesList.add(vert);
        	
        }
        	
        Iterator <Vertex> iter= vertexesList.iterator();
        while(iter.hasNext()) {
        	System.out.println(iter.next().getName());
        }
        
        
        for(int l =0; l<vertexesList.size();l++) {
        	for(int v=0; v<distance.length;v++) {
        		if(distance[l][v]>0) {
        			Vertex start=vertexesList.get(l);
        			Vertex end=vertexesList.get(v);
        			Edge edge=new Edge(start,end);
        			Edge reverseEdge=new Edge(end,start);
        			edge.setWeight(distance[l][v]);
        			start.edges.add(edge);
        			end.reverseEdges.add(reverseEdge);
        		}
        	}
        }
        
     	
        
        
        FileIO.NDFS(vertexStack, reverseStack, vertexesList.get(0), vertexesList);
       
        
        Iterator <Vertex> iter1= vertexesList.iterator();
        while(iter1.hasNext()) {
        	System.out.println(iter1.next());
        }

        Iterator <Vertex> iter2= vertexesList.iterator();
        while(iter2.hasNext()) {
        	Vertex vertex=iter2.next();
        	vertex.setEdgeType();
        	Iterator iter3=vertex.getEdgeIterator();
        	while(iter3.hasNext()) {
        		Edge edge=(Edge)iter3.next();
        		System.out.println( edge.getStart().getName() + "" + edge.getEnd().getName() + "  " + edge.getType() );
        	}
        	
        }
        
        System.out.println();
        
        FileIO.SCC(reverseStack, vertexesList);
        
        
        //oBuffer.close();
      } catch (Exception e) {
          System.err.println("Reading failed at line " + lineNumber);
          
          e.printStackTrace(System.err);                
       }
   }
   
   public static void NDFS(Stack <Vertex> stack, Stack <Vertex> reverseStack,  Vertex startNode,  ArrayList <Vertex> vertList ) {
	 
	   Vertex startVertex;
	   stack.push(startNode);
	   while(! (stack.isEmpty())){
		   
		   startVertex=stack.pop();
		   
		   if (! startVertex.isVisited()) {
			  startVertex.setDiscoverTime(counter);
			  counter++;
			  startVertex.setVisited(true);
			  
		  }
		   Vertex nextVertex=startVertex.getMinEdgeEndNode();
		   if(nextVertex!=null) {
				  stack.push(startVertex);
				  stack.push(nextVertex);
		   }else {
				  startVertex.setFinishTime(counter);
				  reverseStack.push(startVertex);
				  counter++;
		  }   
	   }
	}
		
   
   /**
    * 
    * @param revStack
    * @param vertList
    */
   public static void SCC(Stack <Vertex> revStack, ArrayList <Vertex> vertList ) {
	   Stack <Vertex> stack=new Stack<Vertex>();
	   while(!(revStack.isEmpty())){
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
					   System.out.print("" + newStart.getName());
				 
			   }
			 
			   }
			   System.out.println();
			 
		 }
		   
		   
	   }
	   
	   
   }
		  
	  
	  
	 
	  
		 
 	   
   
   
   public static void main(String[] args) {
	   String iFile;
	   iFile = chooseFile();
       if (iFile != null) {
    	   processFile(iFile);
      }
   }
 
}
