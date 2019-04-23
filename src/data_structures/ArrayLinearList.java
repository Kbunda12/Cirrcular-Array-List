/*
Program #1
Kristian Bunda
cssc1458
 */
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class ArrayLinearList<E extends Comparable<E>> implements LinearListADT<E>{
	private int size;
	private int first;
	private int last;
	private E[] array;
	private int modCounter = 0;

	
	
	public ArrayLinearList() {
		this(LinearListADT.DEFAULT_MAX_CAPACITY);
	}
	

	@SuppressWarnings("unchecked")
	public ArrayLinearList(int maxCapacity) {
		this.size = 0;
		this.first = 0;
		this.last = 0;
		if (maxCapacity == 1 || maxCapacity == 0) {
			System.out.println("Incorrect size, setting to default capacity");
			this.array  = (E[]) new Comparable[LinearListADT.DEFAULT_MAX_CAPACITY];
		}
		else {
		this.array = (E[]) new Comparable[maxCapacity];	
		}
	}
	
	@Override
	public void ends() {
		System.out.println("Front " + first + " Rear " + last);
	}

	@Override
	public boolean addFirst(E obj) {
		if (isFull()) {
			return false;
		}
		if (isEmpty()) {
			first = 0;
			array[first] = obj;
			last = 0;
			array[last] = obj;
			size++;
			return true;
		}
		if (this.first -1 == -1)  {	//if element is out of bounds
			this.first = array.length-1;
		}
		
		else{	
		this.first--;		//if there is a valid empty space behind
		}
		array[first] = obj;
		size++;
		modCounter++;
		return true;
	}

	@Override
	public boolean addLast(E obj) {
		if (isFull()) {
			return false;
		}
		if (isEmpty()) {			
			first = 0;
			last = 0;
			array[first] = obj;
			array[last] = obj;
			size++;
			return true;
		}
		if (last + 1 == array.length) {
			last = 0;
			array[last] = obj;		//if there is an empty space at the first index
		}
		else {
			last++;
			array[last] = obj;
		}
		size++;
		modCounter++;
		return true;
	}

	@Override
	public E removeFirst() {
		if (isEmpty()) {
			return null;
		}
		if (this.first -1 == -1) {	//checks if first index is out of bounds
			this.first = array.length-1;
		}
		else {
			this.first--;
		}
		size--;
		modCounter++;
		return array[first];
	}

	@Override
	public E removeLast() {
		if (isEmpty()) {
			return null;
		}
		if (this.last -1 == -1) {
			this.last = array.length -1;
		}
		else {
			this.last--;
		}
		
		size--;
		modCounter++;
		return array[last];
	}

	@Override
	public E remove(E obj) {
		int currentPos = 0;
		if (isEmpty()) {
			return null;
		}
		for (int i = 0; i <= array.length -1; i++) {
			if (it.next().compareTo(obj) == 0) { //iterating through elements until it finds obj element
				E temp = obj;
				return obj;
			}
			currentPos++;
		}
		
		for (int i = currentPos; i <= array.length -1; i++) {	//shifts elements 
			array[i-1] = array[i];
		}
		size--;
		modCounter++;
		return obj;
	}

	@Override
	public E peekFirst() {
		if (isEmpty()) {
			return null;
		}
		return array[first];
	}

	@Override
	public E peekLast() {
		if (isEmpty()) {
			return null;
		}
		return array[last];
	}

	@Override
	public boolean contains(E obj) {
		while (it.hasNext()) {
		if (it.next().compareTo(obj) == 0) {
			return true;
			}
		}
		return false;
	}

	@Override
	public E find(E obj) {
		if (isEmpty()) {
			return null;
		}
		
		while (it.hasNext()) {
			if (it.next().compareTo(obj) == 0) {
				return obj;
			}
		}
		return null;
	}

	@Override
	public void clear() {
		size = 0;
		first = 0;
		last = 0;
		modCounter++;
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean isFull() {
		return size == array.length;
	}

	@Override
	public int size() {
		return size; 
	}

	@Override
	public Iterator<E> iterator() {
		return new iteratorHelper();
	}
	
	Iterator<E> it = iterator();
	
	private class iteratorHelper implements Iterator<E> {
		private int spot;
		private int itCounter;	//iterator counter to keep track of modifications made
		private int countHelper;
		
		public iteratorHelper(){
		this.spot = 0;
		this.countHelper = 0;
		this.itCounter = modCounter;
		}
		@Override
		public boolean hasNext() {
			if(this.itCounter != modCounter) {
				throw new ConcurrentModificationException();	//fail-fast
			}
			return this.countHelper < size;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			if (spot +1 == array.length-1) {
			this.spot = 0;
			}
			else {
				spot++;
			}
			countHelper++;
			return array[spot];
		}
	}
}
