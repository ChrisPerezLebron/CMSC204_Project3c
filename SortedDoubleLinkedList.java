import java.util.Comparator;
import java.util.ListIterator;

/**
 * A basic implementation of a sorted doubly linked list. This class inherits from 
 * the BasicDoubleLinkedList class
 * @author Christopher Perez Lebron
 *
 * @param <T> a generic type representing items in the collection 
 */
public class SortedDoubleLinkedList<T> extends BasicDoubleLinkedList<T>{
	
	private Comparator<T> comparator; 
	
	/**
	 * default constructor which calls the super class constructor and sets the comparator field
	 * @param comparatorObj the comparator object which will be used for comparisons
	 */
	public SortedDoubleLinkedList(Comparator<T> comparatorObj) {
		super();
		comparator = comparatorObj; 
	}
	
	/**
	 * determines where data should be added based on the the comparison results derived 
	 * from the comparator object. The data is then added accordingly. In other words, this 
	 * method adds data in the current location to maintain ascending order. 
	 * @param data the entry to be added to the collection
	 */
	public void add(T data) {
		Node currentNode = firstNode;
		boolean found = false;
		
		/*
		 * this should stop just after the insertion location
		 */
		while(currentNode != null && !found) { 
			found = comparator.compare(data, currentNode.data) < 1; 
			if(!found)
				currentNode = currentNode.next; 
		}
		
		Node newNode = new Node(data);
		
		
		if(isEmpty()) { //if chain is empty 
			firstNode = newNode;
			lastNode = newNode; 
		}
		else if(currentNode == null) { //adding to end of non-empty chain
			lastNode.next = newNode; 
			newNode.previous = lastNode; 
			lastNode = newNode;
		}
		
		else if(currentNode.previous == null) { //if adding to beginning of non-empty chain
			currentNode.previous = newNode;
			newNode.next = currentNode; 
			firstNode = newNode; 
		}
		else if(currentNode.previous != null) { //adding between two existing nodes 
			Node nodeBefore = currentNode.previous; 
			nodeBefore.next = newNode; 
			newNode.previous = nodeBefore;
			currentNode.previous = newNode;
			newNode.next = currentNode; 
			//no need to update head or tail reference
		}	
		numberOfEntries++;
	}
	
	/**
	 * This operation is not supported for sorted list
	 * @throws UnsupportedOperationException on every call
	 */
	@Override
	public void addToFront(T data) {
		throw new UnsupportedOperationException("Invalid operation for sorted list"); 
	}
	
	/**
	 * This operation is not supported for sorted list
	 * @throws UnsupportedOperationException on every call
	 */
	@Override
	public void addToEnd(T data) {
		throw new UnsupportedOperationException("Invalid operation for sorted list"); 
	}
	
	/**
	 * instantiates and returns a listiterator for this collection
	 * @return a ListIterator for this collection
	 */
	@Override
	public ListIterator<T> iterator() {
		return super.iterator(); 
	}
	
	/**
	 * removes a particular entry from the collection
	 * @param data the entry to be removed
	 * @param comparator an object used to determine equality between 
	 * two instances of the generic type
	 * @return the node containing the removed entry (the node is also removed) 
	 */
	@Override
	public Node remove(T data, Comparator<T> comparator) {
		return super.remove(data, comparator); 
	}
}
