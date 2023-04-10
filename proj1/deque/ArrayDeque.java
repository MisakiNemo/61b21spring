package deque;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class ArrayDeque<T> implements Deque<T>,Iterable<T> {
   @SuppressWarnings("unchecked")
    private T[] items=(T[]) new Object[8];
    private  int NextFirst;
    private int NextLast;
    private int size;

    public ArrayDeque() {
        size = 0;
        NextFirst=3;
        NextLast=4;
    }
    public ArrayDeque(T item){
       items[3]=item;
       NextFirst=2;
       NextLast=4;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        if(NextFirst==-1)
        {
            System.arraycopy(items, NextFirst+1, newItems,(capacity-size())/2, size());
            NextFirst=(capacity-size())/2-1;
            NextLast=NextFirst+1+size();
        }
        else{
            System.arraycopy(items, NextFirst+1, newItems,NextFirst+1, size());
        }
        items=newItems;
    }


    @Override
    public void addFirst(T item) {
        items[NextFirst]=item;
        size+=1;
        NextFirst-=1;
        if(NextFirst==-1){
            resize(items.length*2);
        }
    }

    @Override
    public void addLast(T item) {
        items[NextLast]=item;
        NextLast+=1;
        size+=1;
        if(NextLast==items.length){
            resize(items.length*2);
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        System.out.println(String.join(" ", Arrays.stream(items).filter(Objects::nonNull).map(T::toString).toArray(String[]::new)));
    }

    @Override
    public T removeFirst() {
        if(isEmpty())
        {
            return null;
        }
        NextFirst+=1;
        T item=items[NextFirst];
        items[NextFirst]=null;
        size-=1;
        ShrinkSize();
        return item;
    }
    public void  ShrinkSize(){
        if(isEmpty())
        {
            resize(8);
        }
        else if(items.length>size()*4&&size>=4){
            resize(size*2);
        }
    }
    @Override
    public T removeLast() {
        if(isEmpty())
        {
            return null;
        }
        NextLast-=1;
        T item=items[NextLast];
        items[NextLast]=null;
        ShrinkSize();
        return item;
    }

    @Override
    public T get(int index) {
        if(isEmpty()||index<0||index>size-1)
        {
            return null;
        }
        return items[index+1+NextFirst];
    }

    public class ArrayDequeIterator implements Iterator<T>{
        private  int  index;
        public  ArrayDequeIterator(){
            index=NextFirst;
        }
        @Override
        public boolean hasNext() {
            if(items[index+1]!=null)
            {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            if(hasNext())
            {
                return items[++index];
            }
            return null;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if(o==null)
        {
            return false;
        }
        if(this==o)
        {
            return true;
        }
        if(!(o instanceof ArrayDeque)){
            return false;
        }
        ArrayDeque<?> ad=(ArrayDeque<?>) o;
        if(ad.size()!=this.size()){
            return false;
        }
        for (int i = 0; i < size(); i++) {
           if(ad.get(i)!=this.get(i)){
               return false;
           }
        }
           return true;
    }
    @Override
    public boolean isEmpty(){
        return size()==0;
    }
}
