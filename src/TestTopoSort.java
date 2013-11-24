//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Scanner;
//
///**
// * James Van Gaasbeck. PID: J2686979. COP 3503 - Computer Science II. Assignment
// * #5 - TopoSort.java. Professor Sean Szumlanski. Due: Ocotober 25th, 2013
// */
//public class TestTopoSort {
//
//	ArrayList<Vertex> adjacencyLists;
//
//	// constructor
//	public TestTopoSort(String filename) {
//		readFile(filename);
//	}
//
//	// method to be called to see if there is a toposort. This method calls a
//	// different method that i made to do all the work.
//	public boolean hasTopoSort(int x, int y) {
//		//make sure bad inputs don't break our program.
//		if (x > Vertex.totalNumVertices || y > Vertex.totalNumVertices
//				|| x <= 0 || y <= 0 || (x == y)) {
//			return false;
//		}
//		//the worker method returns true, return true.
//		if (printTopoSort(x - 1, y - 1)) {
//			return true;
//		} else {
//			//...otherwise return false.
//			return false;
//		}
//
//	}
//
//	// This code is very similar to Sean's Toposort method for adjacency
//	// matrices, so props to him. It was actually a task within itself just to
//	// make this work with adjacency lists, so props to me.
//	private boolean printTopoSort(int x, int y) {
//
//		// set the number of incoming vertices each time this method is called,
//		// or else it gets jacked up and messes everything up
//		for (int i = 0; i < Vertex.totalNumVertices; i++) {
//			adjacencyLists.get(i).incomingEdgeCount = adjacencyLists.get(i).inEdges
//					.size();
//			// set the processed variable back to false each time this method is
//			// first called
//			adjacencyLists.get(i).processed = false;
//		}
//
//		// count variable to make sure there isn't a loop in the graph, if there
//		// is a loop there is no topological sort
//		int count = 0;
//		// flag variable to indicate if there is a toposort where X is before Y.
//		boolean flag = false;
//		// q to hold all the vertices with no incoming edges
//		ArrayDeque<Vertex> q = new ArrayDeque<Vertex>();
//		// list to hold the topological sort
//		ArrayList<Vertex> topoList = new ArrayList<Vertex>();
//
//		// add vertices with no incoming edges to the queue
//		for (int i = 0; i < Vertex.totalNumVertices; i++) {
//			if (adjacencyLists.get(i).inEdges.size() == 0) {
//				q.add(adjacencyLists.get(i));
//			}
//			// System.out.println(adjacencyLists.get(i).toString());
//		}
//
//		//
//		//
//		// This is a two pass algorithm, it makes one pass to make sure there
//		// isn't a cycle in the graph, it does this by doing a topological sort.
//		while (!q.isEmpty()) {
//
//			// pull out a Vertex, add it to the topological Sort
//			Vertex vert = q.remove();
//
//			count++;
//
//			// need to decrement incoming edge count and if it hits zero add it
//			// to the queue
//			for (int i = 0; i < Vertex.totalNumVertices; i++) {
//				if (adjacencyLists.get(i).inEdges.contains(vert.vertexNumber)
//						&& --adjacencyLists.get(i).incomingEdgeCount == 0) {
//					q.add(adjacencyLists.get(i));
//				}
//			}
//		} // close loop to check for a cycle
//
//		// check if a cycle
//		if (count != Vertex.totalNumVertices) {
//			// System.out.println("Count: " + count);
//			System.out.print("Cycle: ");
//			return false;
//		}
//
//		// since this is a 2pass algorithm we need to reset all our variables to
//		// their initial values.
//		for (int i = 0; i < Vertex.totalNumVertices; i++) {
//			adjacencyLists.get(i).incomingEdgeCount = adjacencyLists.get(i).inEdges
//					.size();
//			// add vertices with no incoming edges to the queue
//			if (adjacencyLists.get(i).inEdges.size() == 0) {
//				q.add(adjacencyLists.get(i));
//			}
//			// set the processed flag back to false
//			adjacencyLists.get(i).processed = false;
//		}
//
//		// this is the loop that tells us if X comes before Y.
//		while (!q.isEmpty()) {
//			Vertex vert = null;
//
//			// if Y is in the Q, and it's not the only one, then don't grab it
//			if (q.contains(adjacencyLists.get(y)) && q.size() > 1) {
//				// always try to grab X if we can.
//				boolean removed = q.remove(adjacencyLists.get(x));
//				if (removed) {
//					// if we actually grabbed X then return true becuse we have
//					// grabbed X before Y.
//					return true;
//				} else {
//					// if we didn't get X, we need to try our hardest to not
//					// grab Y, so if Y is the head of the queue grab from the
//					// back
//					if (q.getFirst() == adjacencyLists.get(y)) {
//						vert = q.removeLast();
//					} else {
//						// otherwise just grab from the front, then check if
//						// what we just removed was X, if it was X, then we can
//						// return true.
//						vert = q.remove();
//						if (vert.vertexNumber == (x + 1)) {
//							return true;
//						}
//					}
//				}
//			} else {
//				// just remove from the Q
//				vert = q.remove();
//				// if we got an X return true
//				if (vert.vertexNumber == (x + 1)) {
//					return true;
//				} else if (vert.vertexNumber == (y + 1)) {
//					// if we got a Y return false because that means Y always
//					// comes before X.
//					return false;
//				}
//			}
//
//			vert.processed = true;
//			topoList.add(vert);
//
//			count++;
//
//			// need to decrement incoming edge count and if it hits zero add it
//			// to the queue
//			for (int i = 0; i < Vertex.totalNumVertices; i++) {
//				if (adjacencyLists.get(i).inEdges.contains(vert.vertexNumber)
//						&& --adjacencyLists.get(i).incomingEdgeCount == 0) {
//					q.add(adjacencyLists.get(i));
//				}
//			}
//		} // close loop to topologically sort that graph there
//
//		// check if a cycle
//		if ((count != Vertex.totalNumVertices)) {
//			// System.out.println("Count: " + count);
//			System.out.print("Cycle: ");
//			return false;
//		}
//		return true;
//
//	}
//
//	// method to open up and read the file
//	private void readFile(String filename) {
//		try {
//			// open up file
//			Scanner sin = new Scanner(new File(filename));
//			// get the number of vertices in the graph
//			int numberOfVertexes = Integer.parseInt(sin.nextLine());
//			Vertex.totalNumVertices = numberOfVertexes;
//			adjacencyLists = new ArrayList<Vertex>();
//
//			// driver loop
//			for (int i = 0; i < Vertex.totalNumVertices; i++) {
//				// create a new Vertex object and add it to the list
//				adjacencyLists.add(new Vertex(i + 1, sin.nextInt()));
//				for (int j = 0; j < adjacencyLists.get(i).numberOfAdjacentVertices; j++) {
//					// get the next number
//					int adjVertex = sin.nextInt();
//					// add that number to our list of adjacent vertices
//					adjacencyLists.get(i).theAdjacentVertices.add(adjVertex);
//					// add that number to our set of outEdges for this vertex
//					adjacencyLists.get(i).outEdges.add(adjVertex);
//				}
//			}
//
//			// now we need to fill up our hash set of inComing edges, we have to
//			// do this in a separate for() loop because all of the edges must be
//			// instantiated in order for this to work.
//			for (int i = 0; i < Vertex.totalNumVertices; i++) {
//				// if an edge has an outEdge then go to the vertex the outEdge
//				// is going to and give it an incoming edge from this edge (hard
//				// to explain but simple reflexive logic?)
//				for (Integer out : adjacencyLists.get(i).outEdges) {
//					adjacencyLists.get(out - 1).inEdges.add(i + 1);
//				}
//			}
//		} catch (FileNotFoundException e) {
//			// check to make sure u spelt everythign correctly
//			e.printStackTrace();
//		}
//	} // closes readFile()
//
//	public static double difficultyRating() {
//		// this one was hard, safe to say you can round up to 4...
//		return 3.9999;
//	}
//
//	public static double hoursSpent() {
//		// too many....
//		return 15;
//	}
//
//	public static void main(String[] args) {
//		// TopoSort sort = new TopoSort(
//		// "/Users/jjvg/Google Drive/Fall 2013/CS_2/Assignment 4/g9.txt");
//		// System.out.println(sort.hasTopoSort(1,3));
//
//		TestTopoSort t2 = new TestTopoSort(
//				"/Users/jjvg/Documents/workspace/g14.txt");
//
//		System.out.print("Test Case #1: ");
//		System.out.println((t2.hasTopoSort(4, 1) == false) ? "PASS" : "FAIL");
//
//		System.out.print("Test Case #2: ");
//		System.out.println((t2.hasTopoSort(8, 3) == true) ? "PASS" : "FAIL");
//
//		System.out.print("Test Case #3: ");
//		System.out.println((t2.hasTopoSort(14, 11) == true) ? "PASS" : "FAIL");
//
//		System.out.print("Test Case #4: ");
//		System.out.println((t2.hasTopoSort(6, 12) == true) ? "PASS" : "FAIL");
//
//		System.out.print("Test Case #5: ");
//		System.out.println((t2.hasTopoSort(12, 6) == false) ? "PASS" : "FAIL");
//
//		System.out.print("Test Case #6: ");
//		System.out.println((t2.hasTopoSort(12, 4) == true) ? "PASS" : "FAIL");
//		System.out.println(TestTopoSort.difficultyRating());
//		System.out.println(TestTopoSort.hoursSpent());
//	}
//
//} // closes class TopoSort.java
//
//// super sexy class to give structure to our data and let us hold meta data
//// about the graph
////class Vertex {
//
//	// total number of vertexes in the graph aka that first line in the
//	// textFile.txt
//	static Integer totalNumVertices = null;
//	// which vertex it is.
//	int vertexNumber;
//	// the number of adjacent vertices to the vertex at hand aka the number of
//	// outGoing Edges this vertex has
//	int numberOfAdjacentVertices;
//	// the number of incoming edges
//	int incomingEdgeCount;
//	// flag to show that we have processed this vertex, don't really think i
//	// ended up using it but i think I'm going to leave it in here
//	boolean processed = false;
//	// a list of the adjacent vertices, not really necessary but it's there cuz
//	// it is.
//	ArrayList<Integer> theAdjacentVertices;
//	// set of the incoming edges to this node telling us where the inComing edge
//	// came from.
//	public HashSet<Integer> inEdges;
//	// set of outGoing edges to this node telling us where the outEdge is going
//	// to.
//	public HashSet<Integer> outEdges;
//
//	// constructor
//	Vertex(int vNum, int numberOf) {
//		vertexNumber = vNum;
//		numberOfAdjacentVertices = numberOf;
//		incomingEdgeCount = 0;
//		theAdjacentVertices = new ArrayList<Integer>();
//		inEdges = new HashSet<>();
//		outEdges = new HashSet<>();
//	}
//
//	// super sexy intricate toString() method to let the user see what we're
//	// working with and for debugging purposes.
//	@Override
//	public String toString() {
//		return "The current vertex is :" + this.vertexNumber
//				+ "\nThe Number Of Adjacent Vertices are:"
//				+ this.numberOfAdjacentVertices + "\n"
//				+ this.printAdjacentVertices();
//	}
//
//	// method only called by the toString() method to make printing the
//	// entire Vertex.class easier. It prints the adjacent vertices.
//	private StringBuilder printAdjacentVertices() {
//		StringBuilder adjVertexString = new StringBuilder(
//				"The adjacent vertices are:");
//		for (int i = 0; i < this.theAdjacentVertices.size(); i++) {
//			adjVertexString.append(" " + this.theAdjacentVertices.get(i));
//		}
//		return adjVertexString;
//	}
//
//} // close the Vertex class
//
