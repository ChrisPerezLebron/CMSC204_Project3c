import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A basic double linked implementation of the ADT List
 * @author Christopher Perez Lebron
 *
 * @param <T> a generic type representing an entry in the collection
 */
public class BasicDoubleLinkedList<T> implements Iterable<T> {

	protected Node firstNode;
	protected Node lastNode;
	protected int numberOfEntries; 
	
	/**
	 * default constructor initializes the data fields
	 */
	public BasicDoubleLinkedList() {
		initializeDataFields();
	}
	
	/**
	 * resets the data fields to their initial state upon instantiation 
	 */
	public void clear( ) {
		initializeDataFields();
	}
	
	/**
	 * set the data fields to their initial state  
	 */
	private void initializeDataFields() {
		firstNode = null;
		lastNode = null;
		numberOfEntries = -1;
	}
	
	/**
	 * adds an entry to the end of the list
	 * @param data an entry to be added 
	 */
	public void addToEnd(T data) {
		add(data);
	}
	
	/**
	 * adds an entry to the front of the list
	 * @param data an entry to be added 
	 */
	public void addToFront(T data) {
		add(0, data);
	}
	
	/**
	 * adds an entry to the end of the list
	 * @param newEntry  an entry to be added 
	 */
	private void add(T newEntry) {
		/*
		 * NOTE: I had already wrote this before I realized that
		 * the only adding methods are to front and to back
		 * 
		 * Also, this add to back could of called the other add method
		 * but I guess I like doing extra work now. 
		 * 
		 */
		Node newNode = new Node(newEntry);
		if(isEmpty())
			firstNode = newNode;
		else { //if NOT empty
			lastNode.next = newNode;
			newNode.previous = lastNode;
		}
		
		/*
		 * regardless of if the chain is empty or not empty
		 * lastNode must be updated/moved to reference newNode
		 */
		lastNode = newNode;
		numberOfEntries++; 
	}
	
	
	/**
	 * adds an entry anywhere in the list 
	 * @param givenPosition the position at which the entry will be inserted
	 * @param newEntry an entry to be inserted
	 */
	private void add(int givenPosition, T newEntry) {
		/*
		 * NOTE: I had already wrote this before I realized that
		 * the only adding methods are to front and to back
		 */
		if(givenPosition >= 0 && givenPosition <= numberOfEntries + 1) {
			Node newNode = new Node(newEntry);
			if(isEmpty()) {
				firstNode = newNode;
				lastNode = newNode; 
			}
			else if (givenPosition == 0 ) { //that is, adding to the front of a non-empty chain
				newNode.next = firstNode; 
				firstNode.previous = newNode;
				firstNode = newNode; 
			}
			else if(givenPosition == numberOfEntries + 1) { //that is, adding to the end of a non-empty chain
				lastNode.next = newNode; 
				newNode.previous = lastNode; 
				lastNode = newNode; 
			}
			else { //addition occurs anywhere else in a non-empty chain
				Node nodeBefore = getNodeAt(givenPosition - 1);
				Node nodeAfter = nodeBefore.next;
				newNode.next = nodeAfter;
				nodeAfter.previous = newNode; 
				nodeBefore.next = newNode; 
				newNode.previous = nodeBefore;
			}
			numberOfEntries++;
		}
		else //if given position is invalid
			throw new IndexOutOfBoundsException("Addition index must be between 0 and "
									+ "just after the last added index");
	}
	
	/**
	 * checks if the list is empty
	 * @return true if empty false otherwise
	 */
	public boolean isEmpty() {
		return numberOfEntries == -1;
	}
	
	private Node getNodeAt(int givenPosition) {
		Node currentNode;
		if(givenPosition == numberOfEntries) //if node requested is the last node
			currentNode = lastNode;
		else { //requested node is somewhere else
			currentNode = firstNode;
			/*
			 * note we only traverse one way because 
			 * traversing conditionally back or forwards 
			 * dependent on the given index wouldn't provide much
			 * benefit but implementing that logic would be 
			 * quiet complicated
			 */
			for(int count = 0; count < givenPosition; count++)
				currentNode = currentNode.next; 
		}
		return currentNode;	
	}
	
	/**
	 * retrieves the number of items in the collection
	 * @return the number of items currently stored in this collection
	 */
	public int getSize() {
		return numberOfEntries + 1;
	}
	
	/**
	 * gets (but does not delete) the first entry
	 * @return the first entry
	 */
	public T getFirst() {
		T result = null;
		if(!isEmpty())
			result = firstNode.data;
		/*
		 * NOTE: null will be returned if chain is 
		 * empty
		 */
		return result; 
	}
	
	/**
	 * gets (but does not delete) the last entry
	 * @return the last entry
	 */
	public T getLast() {
		T result = null;
		if(!isEmpty())
			result = lastNode.data;
		/*
		 * NOTE: null will be returned if chain is 
		 * empty
		 */
		return result; 
	}
	
	/**
	 * converts all the items currently stored in this collection into an 
	 * ArrayList which is then returned to the client
	 * @return An ArrayList of all the objects in this collection
	 */
	public ArrayList<T> toArrayList(){
		
		ArrayList<T> resultList = new ArrayList<>(); 
		Node currentNode = firstNode; 
		while(currentNode != null) {
			resultList.add(currentNode.data);
			currentNode = currentNode.next; 
		}
		
		return resultList; 
	}
	
	/**
	 * Removes a specific entry. This method traverses through the collection 
	 * using the comparator object provided to determine which entry must be 
	 * removed. The node containing the removed entry is then returned to the 
	 * client
	 * @param targetData the entry being searched for
	 * @param comparator an object that can be used to determine if entries are equal
	 * @return the node removed node which contains the removed entry 
	 */
	public Node remove(T targetData, Comparator<T> comparator) {
		Node currentNode = firstNode;
		boolean found = false;
		
		while(currentNode != null && !found) {
			if(0 == comparator.compare(currentNode.data, targetData))
				found = true;
			if(!found) 
				currentNode = currentNode.next; 
		}
		
		if (currentNode != null) { //target node was found 
			Node nodeBefore = currentNode.previous; 
			Node nodeAfter = currentNode.next; 
			
			if(nodeBefore == null && nodeAfter == null) { //found at the only node in the chain
				firstNode = null; 
				lastNode = null; 
			}
			else if(nodeBefore == null & nodeAfter != null) { //found at beginning of the chain
				nodeAfter.previous = null; 
				firstNode = firstNode.next; //firstNode.next should (if my logic is correct) be nodeAfter 
			}
			
			else if(nodeBefore != null & nodeAfter == null) { //found at end of the chain
				nodeBefore.next = null; 
				lastNode = lastNode.previous; //lastNode.previous should (if my logic is correct) be nodeBefore 
			
			}
			
			else if(nodeBefore != null && nodeAfter != null) {//found between two existing nodes in the chain
				nodeBefore.next = nodeAfter; 
				nodeAfter.previous = nodeBefore; 
			}
		}
		numberOfEntries--;
		return currentNode; 
	}
	
	/**
	 * get and delete the first entry
	 * @return the first entry
	 */
	public T retrieveFirstElement() {
		T result = null; 
		if(!isEmpty()) {
			result = firstNode.data;
			firstNode = firstNode.next; 
			numberOfEntries--;
			if(getSize() == 0) {
				lastNode = null;
			}
			else
				firstNode.previous = null;
		}
		
		/*
		 * result will be null if the chain is empty
		 */
		return result;
	}
	
	/**
	 * get and delete the last entry
	 * @return the last entry
	 */
	public T retrieveLastElement() {
		T result = null; 
		if(!isEmpty()) {
			result = lastNode.data;
			lastNode = lastNode.previous; 
			numberOfEntries--;
			if(getSize() == 0) {
				firstNode = null;
			}
			else
				lastNode.next = null; 
		}
		
		/*
		 * result will be null if the chain is empty
		 */
		return result;
	}
	
	/**
	 * instantiates and returns a listiterator for this collection
	 * @return a ListIterator for this collection
	 */
	public ListIterator<T> iterator() {
		return new DoubleLinkedListIterator(); 
	}
	
	
	
	
	/**
	 * Node class for storing the items in the collection. 
	 * @author Christopher Perez Lebron
	 *
	 */
	protected class Node {
		protected Node next; 
		protected T data;
		protected Node previous;
		
		public Node(Node nextField, T dataField, Node previousField) {
			this.next = nextField; 
			this.data = dataField; 
			this.previous = previousField; 
		} 
		
		public Node(T dataField) {
			this.next = null; 
			this.data = dataField; 
			this.previous = null; 
		}
		
	}
	
	/**
	 * A ListIterator for this class. It does not provide any of the 
	 * modification methods. Only, next, hasNext, previous, and hasPrevious
	 * @author Christopher Perez Lebron 
	 *
	 */
	private class DoubleLinkedListIterator implements ListIterator<T> {
		private Node nextNode; 
		private Node previousNode;
		
		public DoubleLinkedListIterator() {
			nextNode = firstNode; 
			previousNode = null;
		}
		
		/**
		 * checks if there is a next entry to return
		 * @return true if there is a next entry. false otherwise
		 */
		public boolean hasNext() {
			return nextNode != null; 
		}
		
		/**
		 * returns the next entry and moves the cursor forwards
		 * @return the next entry in the collection
		 * @throws NoSuchElementException if called when hasNext is false
		 */
		public T next() {
			if(hasNext()) {
				T result = nextNode.data;
				previousNode = nextNode; 
				nextNode = nextNode.next;
				return result;
			}
			else //there is no next element but next was called
				throw new NoSuchElementException("No next element"); 
		}
		
		/**
		 * checks if there is a previous entry to return
		 * @return true if there is a previous entry. false otherwise
		 */
		public boolean hasPrevious() {
			return previousNode != null; 
		}
		
		/**
		 * retrieves the previous entry 
		 * @return the previous entry
		 * @throws NoSuchElementException if called when hasPrevious 
		 * is false
		 */
		public T previous() {
			if(hasPrevious()) {
				T result = previousNode.data;
				nextNode = previousNode; 
				previousNode = previousNode.previous; 
				
				return result;
			}
			else //there is no previous element but next was called
				throw new NoSuchElementException("No previous element");
		}
		
		/**
		 * not implemented so this method simply throws an UnsupportedOperationException
		 * @throws UnsupportedOperationException on every call
		 */
		public void add(T data) {
			throw new UnsupportedOperationException("add not supported");
		}
		
		/**
		 * not implemented so this method simply throws an UnsupportedOperationException
		 * @throws UnsupportedOperationException on every call
		 */
		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
		
		/**
		 * not implemented so this method simply throws an UnsupportedOperationException
		 * @throws UnsupportedOperationException on every call
		 */
		public int nextIndex() {
			throw new UnsupportedOperationException("nextIndex not supported");
		}
		
		/**
		 * not implemented so this method simply throws an UnsupportedOperationException
		 * @throws UnsupportedOperationException on every call
		 */
		public int previousIndex() {
			throw new UnsupportedOperationException("previousIndex not supported");
		}
		
		/**
		 * not implemented so this method simply throws an UnsupportedOperationException
		 * @throws UnsupportedOperationException on every call
		 */
		public void set(T data) {
			throw new UnsupportedOperationException("set not supported");
		}
		
		
		
	}
}
