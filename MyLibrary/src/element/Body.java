package element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Body<E> implements Collection<E>{
	
	private ArrayList<E> elements;
	
	public Body(){
		elements = new ArrayList<E>();
	}
	public boolean add(E arg0) {
		return elements.add(arg0);
	}

	public boolean addAll(Collection<? extends E> arg0) {
		return elements.addAll(arg0);
	}

	public void clear() {
		elements.clear();
	}

	public boolean contains(Object arg0) {
		return elements.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return elements.containsAll(arg0);
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public Iterator<E> iterator() {
		return elements.iterator();
	}

	public boolean remove(Object arg0) {
		return elements.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return elements.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return elements.retainAll(arg0);
	}

	public int size() {
		return elements.size();
	}

	public Object[] toArray() {
		return elements.toArray();
	}

	public <T> T[] toArray(T[] arg0) {
		return elements.toArray(arg0);
	}
	
	public E get(int index){
		return elements.get(index);
	}
}
