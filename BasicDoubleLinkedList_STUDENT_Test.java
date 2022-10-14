import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A JUnit test for the methods of the BasicDoubleLinkedList class
 * @author Christopher Perez Lebron
 *
 */
class BasicDoubleLinkedList_STUDENT_Test {
	BasicDoubleLinkedList<Integer> intList; 
	Integer num1= 33, num2 = 32, num3 = 64, num4 = 71, num5= 45, num6 = 39, num7 =46;  
	IntegerComparator intComparator = new IntegerComparator();
	@BeforeEach
	void setUp() throws Exception {
		intList = new BasicDoubleLinkedList<>(); 
	}

	@AfterEach
	void tearDown() throws Exception {
		intList = null;
	}


	@Test
	void testClear() {
		intList.addToEnd(num6); 
		intList.addToFront(num1);
		
		intList.clear(); 
		
		assertTrue(intList.getSize() == 0); 
		assertTrue(intList.getFirst() == null);
		assertTrue(intList.getLast() == null);
	}

	@Test
	void testAddToEnd() {
		intList.addToEnd(num2); //32 
		intList.addToEnd(num4); //32, 71, 
		intList.addToEnd(num7); //32, 71, 46
		
		ArrayList<Integer> aList= intList.toArrayList(); 
		
		assertTrue(aList.get(0) == 32);
		assertTrue(aList.get(1) == 71);
		assertTrue(aList.get(2) == 46);
	}

	@Test
	void testAddToFront() {
		intList.addToFront(num5); //45
		intList.addToFront(num3); //64, 45
		
		intList.addToFront(num2); //32, 64, 45
		
		ArrayList<Integer> aList= intList.toArrayList(); 
		
		assertTrue(aList.get(0) == 32);
		assertTrue(aList.get(1) == 64);
		assertTrue(aList.get(2) == 45);
	}

	@Test
	void testIsEmpty() {
		assertTrue(intList.isEmpty()); 
		
		intList.addToFront(num7);
		assertTrue(!intList.isEmpty()); 
		
		intList.retrieveFirstElement(); 
		assertTrue(intList.isEmpty()); 
		
		intList.addToFront(num6);
		intList.addToFront(num4);
		intList.retrieveLastElement(); 
		intList.retrieveLastElement(); 
		assertTrue(intList.isEmpty());
	}

	@Test
	void testGetSize() {
		assertTrue(intList.getSize() == 0);
		
		intList.addToFront(num1);
		assertTrue(intList.getSize() == 1);
		
		intList.addToFront(num6);
		assertTrue(intList.getSize() == 2);
		
		intList.clear();
		assertTrue(intList.getSize() == 0);
		
	}

	@Test
	void testGetFirst() {
		assertTrue(intList.getFirst() == null);
		
		intList.addToEnd(num3);
		assertTrue(intList.getFirst() == num3);
		
		
		intList.addToFront(num4);
		assertTrue(intList.getFirst() == num4);
		
		intList.addToEnd(num7);
		assertTrue(intList.getFirst() == num4);
		
	}

	@Test
	void testGetLast() {
		intList.addToEnd(num4);
		assertTrue(intList.getLast() == num4);
		
		
		intList.addToFront(num7);
		assertTrue(intList.getLast() == num4);
		
		intList.addToEnd(num2);
		assertTrue(intList.getLast() == num2);
	}

	@Test
	void testToArrayList() {
		intList.addToFront(num6); //num6
		intList.addToFront(num7); //num7, num6
		intList.addToEnd(num2); //num7, num6, num2
		
		ArrayList<Integer> aList= intList.toArrayList(); 
		
		assertTrue(aList.get(0) == num7);
		assertTrue(aList.get(1) == num6);
		assertTrue(aList.get(2) == num2);
	}

	@Test
	void testRemove() {
		intList.addToFront(num3); //num3
		intList.addToFront(num1); //num1, num3
		intList.addToEnd(num7); //num1, num3, num7
		intList.addToFront(num2); //num2, num1, num3, num7
		intList.addToFront(num5); //num5, num2, num1, num3, num7
		intList.addToEnd(num4);  //num5, num2, num1, num3, num7, num4
		intList.addToEnd(num6); //num5, num2, num1, num3, num7, num4, num6
		
		intList.remove(num6, intComparator); //num5, num2, num1, num3, num7, num4
		assertTrue(intList.getLast() == num4); 
		
		intList.remove(num3, intComparator); //num5, num2, num1, num7, num4
		assertTrue(intList.getSize() == 5);
		
		intList.remove(num1, intComparator); //num5, num2, num7, num4
		assertTrue(intList.getSize() == 4);
		
		intList.remove(num5, intComparator); // num2, num7, num4
		assertTrue(intList.getFirst() == num2);
		
		intList.remove(num4, intComparator); // num2, num7
		assertTrue(intList.getLast() == num7);
		
		intList.remove(num7, intComparator); // num2
		assertTrue(intList.getLast() == num2);
	}

	@Test
	void testRetrieveFirstElement() {
		intList.addToFront(num3); //num3
		intList.addToFront(num1); //num1, num3
		intList.addToEnd(num7); //num1, num3, num7
		intList.addToFront(num2); //num2, num1, num3, num7
		intList.addToFront(num5); //num5, num2, num1, num3, num7
		intList.addToEnd(num4);  //num5, num2, num1, num3, num7, num4
		intList.addToEnd(num6); //num5, num2, num1, num3, num7, num4, num6
		
		assertTrue(intList.retrieveFirstElement() == num5);
		assertTrue(intList.retrieveFirstElement() == num2);
		assertTrue(intList.retrieveFirstElement() == num1);
		assertTrue(intList.retrieveFirstElement() == num3);
		assertTrue(intList.retrieveFirstElement() == num7);
		assertTrue(intList.retrieveFirstElement() == num4);
		assertTrue(intList.retrieveFirstElement() == num6);
		assertTrue(intList.retrieveFirstElement() == null);
	}

	@Test
	void testRetrieveLastElement() {
		intList.addToFront(num3); //num3
		intList.addToFront(num1); //num1, num3
		intList.addToEnd(num7); //num1, num3, num7
		intList.addToFront(num2); //num2, num1, num3, num7
		intList.addToFront(num5); //num5, num2, num1, num3, num7
		intList.addToEnd(num4);  //num5, num2, num1, num3, num7, num4
		intList.addToEnd(num6); //num5, num2, num1, num3, num7, num4, num6
		
		assertTrue(intList.retrieveLastElement() == num6);
		assertTrue(intList.retrieveLastElement() == num4);
		assertTrue(intList.retrieveLastElement() == num7);
		assertTrue(intList.retrieveLastElement() == num3);
		assertTrue(intList.retrieveLastElement() == num1);
		assertTrue(intList.retrieveLastElement() == num2);
		assertTrue(intList.retrieveLastElement() == num5);
	}

	@Test
	void testIterator() {
		intList.addToFront(num3); //num3
		intList.addToFront(num1); //num1, num3
		intList.addToEnd(num7); //num1, num3, num7
		intList.addToFront(num2); //num2, num1, num3, num7
		intList.addToFront(num5); //num5, num2, num1, num3, num7
		intList.addToEnd(num4);  //num5, num2, num1, num3, num7, num4
		intList.addToEnd(num6); //num5, num2, num1, num3, num7, num4, num6
		
		ListIterator<Integer> intIterator = intList.iterator(); 
		
		assertFalse(intIterator.hasPrevious());
		
		try{
			intIterator.previous();
			assertTrue("Did not throw a NoSuchElementException",false);
		}
		catch (NoSuchElementException e)
		{
			assertTrue("Successfully threw a NoSuchElementException",true);
		}
		catch (Exception e)
		{
			assertTrue("Threw an exception other than the NoSuchElementException", false);
		}
		
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == num5); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == num2); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == num1); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == num3); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == num7); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == num4); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == num6); 
		assertTrue(intIterator.hasPrevious());
		
		assertFalse(intIterator.hasNext());
		assertTrue(intIterator.hasPrevious());
		
		
		try{
			intIterator.next();
			assertTrue("Did not throw a NoSuchElementException",false);
		}
		catch (NoSuchElementException e)
		{
			assertTrue("Successfully threw a NoSuchElementException",true);
		}
		catch (Exception e)
		{
			assertTrue("Threw an exception other than the NoSuchElementException", false);
		}
		
		
		
		
		
		try{
		
			intIterator.remove();
			assertTrue("Did not throw a UnsupportedOperationException",false);
		}
		catch (UnsupportedOperationException e)
		{
			assertTrue("Successfully threw a UnsupportedOperationException",true);
		}
		catch (Exception e)
		{
			assertTrue("Threw an exception other than the UnsupportedOperationException", false);
		}
		
		try{
			intIterator.set(num7);
			assertTrue("Did not throw a UnsupportedOperationException",false);
		}
		catch (UnsupportedOperationException e)
		{
			assertTrue("Successfully threw a UnsupportedOperationException",true);
		}
		catch (Exception e)
		{
			assertTrue("Threw an exception other than the UnsupportedOperationException", false);
		}
		
		try{
			
			intIterator.add(num1);
			assertTrue("Did not throw a UnsupportedOperationException",false);
		}
		catch (UnsupportedOperationException e)
		{
			assertTrue("Successfully threw a UnsupportedOperationException",true);
		}
		catch (Exception e)
		{
			assertTrue("Threw an exception other than the UnsupportedOperationException", false);
		}
		
		try{
			intIterator.nextIndex();
			assertTrue("Did not throw a UnsupportedOperationException",false);
		}
		catch (UnsupportedOperationException e)
		{
			assertTrue("Successfully threw a UnsupportedOperationException",true);
		}
		catch (Exception e)
		{
			assertTrue("Threw an exception other than the UnsupportedOperationException", false);
		}
		
		try{
			intIterator.previousIndex();
			assertTrue("Did not throw a UnsupportedOperationException",false);
		}
		catch (UnsupportedOperationException e)
		{
			assertTrue("Successfully threw a UnsupportedOperationException",true);
		}
		catch (Exception e)
		{
			assertTrue("Threw an exception other than the UnsupportedOperationException", false);
		}
	}
	
	private class IntegerComparator implements Comparator<Integer> {
		public int compare(Integer int1, Integer int2) {
			return int1.compareTo(int2);
		}
	}

}
