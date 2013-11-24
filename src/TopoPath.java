import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * James Van Gaasbeck. PID: J2686979. COP 3503 - Computer Science II. Assignment
 * #4 - TopoPath.java. Professor Sean Szumlanski. Due: Ocotober 25th, 2013
 */

// There may not be as many comments in this class because my TopoSort.java
// class has all of the comments because the two classes are so similar, this
// class still contains sufficient comments though.
public class TopoPath {

	// super sexy class to give structure to our data and let us hold meta data
	// about the graph
	static class Vertex {

		static Integer totalNumVertices = null;
		// which vertex are we talking about.
		int vertexNumber;
		// the number of adjacent vertices to the vertex at hand.
		int numberOfAdjacentVertices;
		// the number of incoming edges
		int incomingEdgeCount;
		// flag to show that we have processed this vertex
		boolean processed = false;
		// a list of the adjacent vertices
		ArrayList<Integer> theAdjacentVertices;
		public HashSet<Integer> inEdges;
		public HashSet<Integer> outEdges;

		Vertex(int vNum, int numberOf) {
			vertexNumber = vNum;
			numberOfAdjacentVertices = numberOf;
			incomingEdgeCount = 0;
			theAdjacentVertices = new ArrayList<Integer>();
			inEdges = new HashSet<>();
			outEdges = new HashSet<>();
		}

		@Override
		public String toString() {
			return "The current vertex is :" + this.vertexNumber
					+ "\nThe Number Of Adjacent Vertices are:"
					+ this.numberOfAdjacentVertices + "\n"
					+ this.printAdjacentVertices();
		}

		// method only called by the toString() method to make printing the
		// entire Vertex.class easier.
		private StringBuilder printAdjacentVertices() {
			StringBuilder adjVertexString = new StringBuilder(
					"The adjacent vertices are:");
			for (int i = 0; i < this.theAdjacentVertices.size(); i++) {
				adjVertexString.append(" " + this.theAdjacentVertices.get(i));
			}
			return adjVertexString;
		}
	} // close static class Vertex

	// Start the actual TopoPath Class.
	//
	static ArrayList<Vertex> adjacencyLists;

	// method called by the grader
	public static boolean hasTopoPath(String filename) {
		readTheAdjacencyListFile(filename);
		if (doesHaveTopoPath()) {
			return true;
		} else {
			return false;
		}
	}

	private static void readTheAdjacencyListFile(String filename) {
		try {
			Scanner sin = new Scanner(new File(filename));
			int numberOfVertexes = Integer.parseInt(sin.nextLine());
			Vertex.totalNumVertices = numberOfVertexes;
			// System.out.println(numberOfVertexes);
			adjacencyLists = new ArrayList<Vertex>(numberOfVertexes);

			for (int i = 0; i < numberOfVertexes; i++) {
				adjacencyLists.add(new Vertex(i + 1, sin.nextInt()));
				for (int j = 0; j < adjacencyLists.get(i).numberOfAdjacentVertices; j++) {
					int adjVertex = sin.nextInt();
					Vertex currentVertex = adjacencyLists.get(i);
					currentVertex.theAdjacentVertices.add(adjVertex);
					currentVertex.outEdges.add(adjVertex);
				}
			}

			for (int i = 0; i < adjacencyLists.size(); i++) {
				for (Integer out : adjacencyLists.get(i).outEdges) {
					adjacencyLists.get(out - 1).inEdges.add(i + 1);
				}
			}

		} catch (FileNotFoundException e) {
			System.out
					.println("There must of been a problem reading the file.");
			e.printStackTrace();
		}
	}

	private static boolean doesHaveTopoPath() {

		// reset the number of incoming vertices each time this method is
		// called,
		// or else it gets jacked up and messes everything up
		for (int i = 0; i < Vertex.totalNumVertices; i++) {
			adjacencyLists.get(i).incomingEdgeCount = adjacencyLists.get(i).inEdges
					.size();
		}

		int pathCount = 0;
		int count = 0;
		// q to hold all the vertices with no incoming edges
		ArrayDeque<Vertex> q = new ArrayDeque<Vertex>();

		// add vertices with no incoming edges to the queue
		for (int i = 0; i < Vertex.totalNumVertices; i++) {
			if (adjacencyLists.get(i).incomingEdgeCount == 0) {
				q.add(adjacencyLists.get(i));
			}
		}

		while (!q.isEmpty()) {

			// pull out a Vertex, add it to the topological Sort (print it)
			Vertex vert = q.remove();
			// System.out.println("Removed: " + vert.vertexNumber);

			// if what we just removed from the queue does not have a path
			// between what will be pulled next then there is no topopath
			
			if ((!q.isEmpty() && !vert.outEdges.contains(q.getFirst())) || (q.size() > 1)) {
				return false;
			}

			count++;

			// need to decrement incoming edge count and if it hits zero add it
			// to the queue
			for (int i = 0; i < Vertex.totalNumVertices; i++) {
				if (adjacencyLists.get(i).inEdges.contains(vert.vertexNumber)
						&& --adjacencyLists.get(i).incomingEdgeCount == 0) {
					q.add(adjacencyLists.get(i));
				}
			}
		}
		if (count != Vertex.totalNumVertices) {
			// System.out.println("Error: There was a cycle");
			return false;
		}
		//System.out.println("Count: " + count);
		return true;
	}

	public static double difficultyRating() {
		return 2.7;
	}

	public static double hoursSpent() {
		return 5;
	}
}
