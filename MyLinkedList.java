package project4;

/**
 * Created with IntelliJ IDEA.
 * User: fergusor
 * Date: 3/14/13
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;

public class MyLinkedList<E> implements Serializable
{
    private DNode<E> top;
    private DNode<E> tail;
    public int size;

    public MyLinkedList() {
        top = null;
        tail = null;
        size = 0;
    }
    
    public void clear(){
    	for(int i =0; i< this.size(); i++)
    		this.remove(i);
    	
    	this.size = 0;
    }
    
    public void add(E s){
    	if(this.size() == 0){
    		tail = new DNode<E>();
    		top = new DNode<E>(s, tail, null);
    	}
    	
    	else if(this.size() == 1){
    		tail.setData(s);
    		tail.setPrev(top);
    	}
    	else{
    		DNode<E> temp = new DNode<E>(tail.getData(), tail, tail.getPrev());
    		tail.getPrev().setNext(temp);
    		tail.setData(s);
    		tail.setPrev(temp);
    		tail.setNext(null);
    	}
    		
    		
    	size++;
    }
    
    public void addFirst(E s){
    	DNode<E> temp = new DNode<E>(top.getData(), top.getNext(), top.getPrev());
		top.setData(s);
		top.setNext(temp);
		top.setPrev(null);

		
    	size++;
    }
    
    public E remove(int index){
    	if(top == null)
    		throw new IllegalArgumentException();
    	
    	if(size == 0 || index < 0 || index >= size)
    		throw new IllegalArgumentException();
    	
    	if(index == size -1){
    		return removeLast();
    	}else if(index == 0){
    		return removeFirst();
    	}
    	
    	DNode<E> previous = null;
    	DNode<E> temp = top;
    	
    	while(index >0){
    		previous = temp;
    		temp = temp.getNext();
    		index--;
    	}
    	
    	previous.setNext(temp.getNext());
    	temp.getNext().setPrev(previous);
    	size--;
    	
    	return temp.getData();
    	
    }
    
    private E removeLast(){
    	DNode<E> temp = tail;
    	tail = tail.getPrev();
    	if(tail == null)
    		top = null;
    	else
    		tail.setNext(null);
    	
    	size--;
    	return temp.getData();
    }
    
    private E removeFirst(){
    	DNode<E> temp = top;
    	top = top.getNext();
    	if(top != null)
    		top.setPrev(null);
    	else
    		tail = null; 
    	
    	temp.setNext(null);
    	size--;
    	return temp.getData();
    }
    
    @SuppressWarnings("unchecked")
	public E get(int index){
    	if(this.size() < 1)
    		return null;
    	if(index < 0)
    		return null;
    	if(index >= this.size())
    		return null;
    	
    	DNode<E> temp = new DNode<E>(top.getData(), top.getNext());
    	while(index > 0){
    		temp = temp.getNext();
    		index--;
    	}
    	
    	return temp.getData();
    }
    
    public boolean removeAll(E s){
//    	if(top == null || this.size() < 1)
//    		return false;
//    	else if(top == tail && top.getData().equals(s)){
//    		top = tail = null;
//    		return true;
//    	}
//    	else if(top.getData().equals(top))
//    		top = top.getNext();
//    		top.setPrev(null);
//    		return true;
    	DNode<E> temp = top;
    	while(temp != null && !temp.getData().equals(s)){
    		temp = temp.getNext();
    	}
    	if(temp != null){
    		if(temp.getPrev() != null)
    			temp.getPrev().setNext(temp.getNext());
    		else
    			top = temp.getNext();
    		if(temp.getNext() != null)
    			temp.getNext().setPrev(temp.getPrev());
    		else
    			tail = temp.getPrev();
    		size--;
    		return true;
    	}
    	
    	return false;
    		
    }
    
    public int find(E s){
    	int count = 0;
    	
    	DNode<E> temp = top;
    	
    	while(temp != null && !temp.getData().equals(s)){
    		temp = temp.getNext();
    		count ++;
    	}
    	
    	// temp points to value, i is index
    	if(temp == null){
    		return -1;
    	// value found, return index	
    	}else{
    		return count;
    	}
    		
    }
    
    
    
    public int size(){
    	return size;
    }
    
    
    

   
}
