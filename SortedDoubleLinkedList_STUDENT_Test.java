import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * A JUnit test for the methods of the SortedDoubleLinkedList class
 * @author Christopher Perez Lebron
 *
 */
class SortedDoubleLinkedList_STUDENT_Test {

	SortedDoubleLinkedList<Integer> sortedIntList; 
	Integer num1 = -66, num2 = 5569, num3 = -8094, num4 = -4287, num5 = 9626, num6 = -7135, num7 = -3164;
	IntegerComparator intComparator = new IntegerComparator();
	@BeforeEach
	void setUp() throws Exception {
		sortedIntList = new SortedDoubleLinkedList(intComparator);
	}

	@AfterEach
	void tearDown() throws Exception {
		sortedIntList = null;
	}

	@Test
	void testAddToEnd() {
		try{
			sortedIntList.addToEnd(num7); 
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

	@Test
	void testAddToFront() {
		try{
			sortedIntList.addToFront(num3); 
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

	@Test
	void testRemove() {
		sortedIntList.add(num4); //-4287
		sortedIntList.add(num3); //-8094, -4287
		sortedIntList.add(num5); //-8094, -4287, 9626
		assertTrue(sortedIntList.getSize() == 3); 
		
		sortedIntList.remove(num4, intComparator);
		assertTrue(sortedIntList.getSize() == 2);
		
		sortedIntList.remove(num5, intComparator);
		assertTrue(sortedIntList.getLast() == -8094);
		
	}

	@Test
	void testIterator() {
		sortedIntList.add(num1); 
		sortedIntList.add(num2); 
		sortedIntList.add(num3); 
		sortedIntList.add(num4);
		sortedIntList.add(num5);
		sortedIntList.add(num6);
		sortedIntList.add(num7); //-8094, -7135, -4287, -3164, -66, 5569, 9626
		
		/*
		 * Note unsupported methods were tested in the other JUnit test I made. 
		 */
		
		ListIterator<Integer> intIterator = sortedIntList.iterator(); 
		
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
		assertTrue(intIterator.next() == -8094); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == -7135); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == -4287); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == -3164); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == -66); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == 5569); 
		assertTrue(intIterator.hasPrevious());
		
		assertTrue(intIterator.hasNext());
		assertTrue(intIterator.next() == 9626); 
		assertTrue(intIterator.hasPrevious());
		
		assertFalse(intIterator.hasNext());
		
		
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

	}

	@Test
	void testAdd() {
		sortedIntList.add(num1); //-66
		assertTrue(sortedIntList.getFirst() == num1);
		
		sortedIntList.add(num2); //-66, 5569
		assertTrue(sortedIntList.getLast() == 5569);
		
		sortedIntList.add(num5);//-66, 5569, 9626
		assertTrue(sortedIntList.getLast() == 9626);
		
		sortedIntList.add(num6);//-7135, -66, 5569, 9626
		assertTrue(sortedIntList.getFirst() == -7135);
		
		sortedIntList.add(num7); //-7135, -3164, -66, 5569. 9626
		assertTrue(sortedIntList.getSize() == 5);
	}

	private class IntegerComparator implements Comparator<Integer> {
		public int compare(Integer int1, Integer int2) {
			return int1.compareTo(int2);
		}
	}
}
