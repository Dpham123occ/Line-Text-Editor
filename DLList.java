//=======================================
// Java templated doubly linked list
//=======================================

//=======================================
// Imports
//=======================================

import java.util.*;

//=======================================
// DLList Class
//=======================================
class DLList<T> {

    //internal DLList node class
    private class DLListNode<T> {
        //data member
        public T data;
        public DLListNode<T> previous;
        public DLListNode<T> next;

        //member function

        //overloaded constructor
        DLListNode(T value) {
            data = value;
            previous = null;
            next = null;
        }
    }

    //data member
    private DLListNode<T> front;
    private DLListNode<T> back;
    private DLListNode<T> current;
    int size;
    int index;

    //member functions
    
    //default constructor
    public DLList() {
        clear();
    }

    //copy constructor (deep copy)
    public DLList(DLList<T> other) {
        this.front = other.front;
        this.back = other.back;
        this.current = other.current;
        this.size = other.getSize();
        this.index = other.getIndex();
        /*DLListNode<T> cur = other.front;
        while (cur!=null) {
        	insertLast(other.getData());
        	System.out.println("Copied last element");
        	cur = cur.next;
        }*/
    }

    //clear list method
    public void clear() {
        front = null;
        back = null;
        current = null;
        size = 0;
        index = -1;
    }
    
    //get size method
    public int getSize() {
        return size;
    }

    //get index method 
    public int getIndex() {
        return index;
    }

    //is empty 
    public boolean isEmpty() {
        return (getSize() == 0);
    }

    //is at first
    public boolean atFirst() {
        return (getIndex() == 0);
    }

    //is at last
    public boolean atLast() {
        return  (getIndex() == getSize() -1);
    }

    //get data at current 
    public T getData() {
        if(!isEmpty()) 
            return current.data;
        else 
            return null;
    }

    //set Data
    public T setData(T x) {
        if(!isEmpty()) {
            current.data = x;
            return x;
        } else {
            return null;
        }
    }

    // seek to first node 
    public boolean first() {
        return seek(0);
    }

    //seek to the next node
    public boolean next() {
        if (current.next == null ) 
        	return false;
        else 
        	return seek(getIndex()+1);
    }

    //seek to the previous node
    public boolean previous() {
        if (current.previous == null)
        	return false;
        else
        	return seek(getIndex()-1);
    }

    //seek to the last node
    public boolean last() {
        return seek(getSize()-1);
    }

    //seek method
    public boolean seek(int loc) {
        //local var
        boolean retval = false;
        //is the list empty
        if (isEmpty()) {
            retval = false;
        }
        //is location in range
        else if (loc < 0 || loc > getSize() - 1) {
            retval = false;
        }
        //is loc == 0
        else if (loc == 0) {
            current = front;
            index = 0;
            retval = true;
        }
        //is loc == last index
        if (loc == getSize() -1) {
            current = back;
            index = getSize() -1;
            retval = true;
        }
        //is loc < current index
        else if (loc < getIndex()) {
            for (; getIndex() != loc; index--) 
                current = current.previous;
            retval = true; 
        }
        //is loc > current index
        if (loc > getIndex()) {
            for(; getIndex() != loc; index++) 
                current = current.next;
            retval = true;
    
        }
        //else loc is at the current index ... nothing to do
        else 
            retval = true;

        return retval;
    }

    //insert first method
    public boolean insertFirst(T item) {
    	DLListNode<T> newnode;
        newnode = new DLListNode<T>(item);
        boolean retval = false;
    	//case emptyList
    	 if (isEmpty()) {
         	 front = newnode;
             back = newnode;
             current = newnode;
             size = 1;
             index = 0;
             retval = true;
         }
    	 //case more than 1 item
    	 else if(getSize() >= 1) {
    		 seek(0);
    		 current.previous = newnode;
    		 newnode.next = current;
    		 front = newnode;
    		 newnode.previous = null;
    		 current = newnode;
    		 size++;
    	 }
    	return retval;
    	
    }

    //insert at current method
    public boolean insertAt(T item) {
        //create an node
        DLListNode<T> newnode;
        newnode = new DLListNode<T>(item);
        boolean retval = false;
        if (atFirst()) {
        	return (insertFirst(item));
        }
        else {	
        	newnode.next = current;
        	current.previous.next = newnode;
        	newnode.previous = current.previous;
        	current.previous = newnode;
        	current = newnode;
        	size++;
        	retval = true;
        }
        return retval;
    }
    
    public boolean insertAfter(T item) {
        //create an node
        DLListNode<T> newnode;
        newnode = new DLListNode<T>(item);
        boolean retval = false;
        if (atLast()) {
        	return (insertLast(item));
        }
        else {	
        	newnode.previous = current;
        	current.next.previous = newnode;
        	newnode.next = current.next;
        	current.next = newnode;
        	current = newnode;
        	size++;
        	retval = true;
        }
        return retval;
    }
    
    //insert last method
    public boolean insertLast(T item) {
        
    	//create a new node, copy item into the list
        DLListNode<T> newnode;
        newnode = new DLListNode<T>(item);
        boolean retval = false;
        
        //if empty list
        if (isEmpty()) {
            front = newnode;
            back = newnode;
            current = newnode;
            size = 1;
            index = 0;
            retval = true;
        }

        //case list has more than 1 item
        else if (getSize() >= 1) {
            back.next = newnode;
            newnode.previous = back;
            back = newnode;
            current = newnode;
            size++;
            index++;
            retval = true;
        } 
        
        //back.next = newnode
        //newnode.previous = back
        //back = newnode
        //current = newnode
        //size++; index++
  
        //same with list 1 item
        return retval;
    }

    //delete first 
    public boolean deleteFirst() {
       boolean retval = false;
       
       //case empty 
       if (isEmpty()) {
    	   retval = false;
       }
       //case 1 item
       else if(getSize() == 1) {
    	   clear();
    	   retval = true;
       }
       //case more than 1 
       else if (getSize() > 1) {
    	   seek(0);
    	   current = current.next;
    	   current.previous.next = null;
    	   current.previous = null;
    	   front = current;
    	   size--;
    	   retval = true;
       }
       return retval;
    }

    //delete at 
    public boolean deleteAt() {
        //if list is empty
        boolean retval = false;
        if(isEmpty()) {
            retval = false;
        }
        else if(getSize() == 1) {
        	clear();
        	retval = true;
        } 
        else if (atFirst()) {
        	return deleteFirst();
        } else if (atLast()) {
        	return deleteLast();
        }
        else {
        	current.previous.next = current.next;
        	current.next.previous = current.previous;
        	current = current.previous;
        	size--;
        	index --;
        	retval = true;
        }
        
        return retval;
    }

    //delete last
    public boolean deleteLast() {
    	boolean retval = false;
    	//case empty 
        if (isEmpty()) {
     	   retval = false;
        }
        //case 1 item
        if(getSize() == 1) {
     	   clear();
     	   retval = true;
        }
        //case more than 1
        
        if(getSize() > 1) {
        	seek(getSize()-1);
        	current = current.previous;
        	current.next.previous = null;
        	current.next = null;
        	back = current;
        	index--;
        	size--;
        	retval = true;
        }
        return retval;
    }

}
