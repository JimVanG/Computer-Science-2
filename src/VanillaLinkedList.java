//This is just a linked list that holds an int value. Nothing special.



// Basic node class holds a piece of data (int) and a next field.
class VanillaNode {

	int data;
	VanillaNode next;
	
	// constructor; sets this object's 'data' field to 'data'
	VanillaNode(int data) {
		this.data = data;
	}
}

// Basic linked lists.
public class VanillaLinkedList {
	
	// don't jack up my head and tail! (private members)
	private VanillaNode head, tail;

	// insert at the tail of the list
	void insert(int data) {
		// if the list is empty, set 'head' and 'tail' to the new node
		if (head == null) {
			head = tail = new VanillaNode(data);
		}
		// otherwise, append the new node to the end of the list and move the
		// tail reference forward
		else {
			tail.next = new VanillaNode(data);
			tail = tail.next;
		}
	}

	// insert at the head of the list
	void headInsert(int data) {
		// first, create the node to be inserted
		VanillaNode YouCanJustMakeANewNode = new VanillaNode(data);

		// insert it at the beginning of the list
		YouCanJustMakeANewNode.next = head;
		head = YouCanJustMakeANewNode;

		// if the list was empty before adding this node, 'head' AND 'tail'
		// need to reference this new node
		if (tail == null)
			tail = YouCanJustMakeANewNode;
	}

	// print the contents of the linked list
	void printList() {
		for (VanillaNode temp = head; temp != null; temp = temp.next)
			System.out.print(temp.data + " ");
		System.out.println();
	}

	// Remove the head of the list (and return its 'data' value). We're using
	// Integer so that we can return a null reference if the list is empty.
	// Otherwise, the return value can be used in int contexts. This is a bit
	// nicer than returning -1 or Integer.MIN_VALUE, because we might actually
	// want to allow those values in our linked list nodes!
	Integer removeHead() {
		// if the list is empty, signify that by returning null
		if (head == null)
			return null;
		
		// Store the data from the head, then move the head reference forward.
		// Java will take care of the memory management when it realizes there
		// are no references to the old head anymore.
		int temp = head.data;
		head = head.next;
		
		// If the list is now empty (i.e., if the node we just removed was the
		// only node in the list), update the tail reference, too!
		if (head == null)
			tail = null;
		
		// Return the value from the old head node.
		return temp;
	}

	// returns true if the list is empty, false otherwise
	boolean isEmpty() {
		return (head == null);
	}

	public static void main(String [] args) {

		// create a new linked list
		VanillaLinkedList L = new VanillaLinkedList();
		
		for (int i = 0; i < 10; i++)
		{
			// this inserts random values on the range [1, 100]
			int SomeRandomJunk = (int)(Math.random() * 100) + 1;
			System.out.println("Inserting " + SomeRandomJunk);
			L.insert(SomeRandomJunk);
		}

		// print the list to verify everything got in there correctly
		L.printList();
	}
}