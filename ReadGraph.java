import java.io.*;
import java.util.*;
		class ColEdge{
			int u;
			int v;
		}
		
public class ReadGraph{
		public final static boolean DEBUG = true;
		public final static String COMMENT = "//";
		public static void main(String args[]){
			ChromaticNubers();

			if( args.length < 1 ){ //How many arguments we passed
				System.out.println("Error! No filename specified.");
				System.exit(0);
			}
			String inputfile = args[0];
			boolean seen[] = null;
			//! n is the number of vertices in the graph
			int n = -1;
			//! m is the number of edges in the graph
			int m = -1;
			//! e will contain the edges of the graph
			ColEdge e[] = null;
			
			try{ 
			    FileReader fr = new FileReader(inputfile);
			    BufferedReader br = new BufferedReader(fr);
			    String record = new String();	
				//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
				//! These comments are only allowed at the top of the file.
				//! -----------------------------------------
			    while ((record = br.readLine()) != null){
					if( record.startsWith("//") ) continue;
					break; // Saw a line that did not start with a comment -- time to start reading the data in!
				}
	
				if( record.startsWith("VERTICES = ")){
					n = Integer.parseInt(record.substring(11) );					
					if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
				}
				seen = new boolean[n+1];			
				record = br.readLine();	
				if(record.startsWith("EDGES = ")){
					m = Integer.parseInt( record.substring(8) );					
					if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
				}
				e = new ColEdge[m];							
				for( int d=0; d<m; d++){
					if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
						record = br.readLine();
					String data[] = record.split(" ");
					if(data.length != 2){
						System.out.println("Error! Malformed edge line: "+record);
						System.exit(0);
					}
					e[d] = new ColEdge();	

					e[d].u = Integer.parseInt(data[0]);
					e[d].v = Integer.parseInt(data[1]);

					seen[ e[d].u ] = true;
					seen[ e[d].v ] = true;
	
					if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
				}					
				String surplus = br.readLine();
				if( surplus != null ){
					if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
				}
			}catch(IOException ex){ 
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+inputfile);
				System.exit(0);
			}
			for(int x=1; x<=n; x++){
				if(seen[x] == false){
					if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
				}
			}

			//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
			//! e[1] will be the second edge...
			//! (and so on)
			//! e[m-1] will be the last edge
			//! 
			//! there will be n vertices in the graph, numbered 1 to n

			//! INSERT YOUR CODE HERE!
			Graph g = new Graph(n);
			for(int i = 0; i < e.length; i++){
				g.AddEdge(e[i].u-1, e[i].v-1);
			}
			
		}
			
		public static void ChromaticNubers(){
			int[] ChromaticNumberArray = {0,1,1,1,1,0};
			ArrayList<Integer> ValuesToAdd = new ArrayList<Integer>();
			for(int i=0; i<ChromaticNumberArray.length; i++){
				ValuesToAdd.add(i);
			}
			int FindChromaticNumber = ValuesToAdd.size();
			System.out.println("Number of Chroamtic numbers are " + FindChromaticNumber);
		}

		public static int GetUpperBound(int n, int m){      
			int upper = n;
			int e = 2*m;
			//upper bound
			for(double i = 0; i < n; i++){
				if(e > (i * (i-1) ))
					upper = (int)i;
			}
			return upper;
		}
		// n is the number of vertices. m is the number of edges.
		public static int GetLowerBound(int n, int m){
			int low = 0;
			//lower bound
			if(m > 1)
				low = 2; //if there is an edge exits, lower bound must equal or more than 2
			if(n % 2 == 0){
				if(m > ((n/2) - 1) * n || m > (((n-1)/2) * (n-1)))
					low = n/2;
			}    
			return low;
		}
}

// Creat Graph
class Graph{
    private int vertex;
    private List<Integer>[] edge;
	//private int[][] edge;

    //basic method for retrieving the number of vertex
    public int CheckVertex(int v){
        return this.vertex = v;
    }

	//add edge to graph
    public void AddEdge(int u, int v){
        edge[u].add(v);
        edge[v].add(u);
		// edge[a][b] = 1;
    }

	//boolean method to check if edge exist betweent two vetices
    public boolean CheckEdge(int u, int v){
		return edge[u].contains(v);
		//return edge[i][j];
	}

    //return list with the vertex has edge
    public List<Integer> HasEdge(int v) {
		return edge[v];
	}

    //Constructor
    public Graph(int v){
        this.vertex = v;
        edge = (List<Integer>[])new List[v];  
        for (int i = 0; i < v; i++){
            edge[i] = new ArrayList<Integer>();
			// edge = new int[i][i]
        }			
    }

}

