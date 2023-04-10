package deque;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.util.Iterator;
import java.util.Objects;

public class LinkedListDeque<T> implements Deque<T> {
    private IntNode sentinel,FirstNode,LastNode;
    private int size;
    public class IntNode {
        public T item;
        public IntNode next;
        public IntNode prev;
        public IntNode(T item,IntNode prev,IntNode next)
        {
            this.item=item;
            this.next=next;
            this.prev=prev;
        }
    }
    public LinkedListDeque(){
        sentinel=FirstNode=LastNode=new IntNode(null,null,null);
        size=0;
    }
    @Override
     public  void addFirst(T item)
    {
             sentinel.next=new IntNode(item,sentinel,sentinel.next);
             FirstNode=sentinel.next;
             if(LastNode==sentinel)
             {
                 LastNode=FirstNode;
             }
             ++size;
    }
    @Override
    public  void addLast(T item)
    {
        LastNode.next=new IntNode(item,LastNode,null);
        LastNode=LastNode.next;
        if(FirstNode==sentinel)
        {
            FirstNode=LastNode;
        }
        ++size;
    }
    @Override
    public int size()
    {
        return this.size;
    }
    @Override
    public void printDeque()
    {
        if(isEmpty())
        {
            return;
        }
        IntNode cur=sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.printf(cur.item+" ");
            cur=cur.next;
        }
        System.out.println();
    }
    @Override
    public  T removeFirst()
    {
        if(isEmpty())
        {
            return null;
        }
        T tmp=FirstNode.item;
        sentinel.next=FirstNode.next;
        FirstNode=sentinel.next;
        --size;
        if(isEmpty())
        {
            LastNode=sentinel;
            FirstNode=sentinel;
        }
        return tmp;
    }
    @Override
    public  T removeLast()
    {
        if(isEmpty())
        {
            return null;
        }
        T tmp=LastNode.item;
        LastNode=LastNode.prev;
        LastNode.next=null;
        --size;
        if(isEmpty())
        {
            LastNode=sentinel;
            FirstNode=sentinel;
        }
        return tmp;
    }
    @Override
    public  T get(int index)
    {
        if(isEmpty()||index>=size||index<0)
        {
            return null;
        }
        IntNode cur=sentinel.next;
        while((index--)!=0)
        {
                cur=cur.next;

        }
        return cur.item;
    }
    private class LinkedisDequeIterator implements Iterator<T>{
        IntNode node;
        public LinkedisDequeIterator(){
            node=sentinel;
        }
        @Override
        public boolean hasNext() {
            if(node.next!=null)
                return true;
            return false;
        }
        @Override
        public T next() {
            if(hasNext())
            {
                node=node.next;
                return node.item;
            }
            return null;
        }
    }
    @Override
    public Iterator<T> iterator()
    {
        return new LinkedisDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof LinkedListDeque) {
            if (this.size() == ((LinkedListDeque<?>) o).size()) {
                for (int i = 0; i < size(); i++) {
                    if (this.get(i) != ((Deque<?>) o).get(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


    public T getRecursive(int index,IntNode t){
        if(isEmpty()||index>size()||index<0){
            return null;
        }
        if(index==0){
            return t.item;
        }
        return getRecursive(index-1,t.next);
    }
    public T getRecursive(int index){
        if(isEmpty()||index>size()||index<0){
            return null;
        }
        IntNode t=sentinel.next;
        if(index==0){
            return t.item;
        }
        return getRecursive(index-1,t.next);
    }
    /*public T getRecursiveHelper(IntNode cur,int index){
        if(index==0)
        {
            return cur.item;
        }
        return getRecursiveHelper(cur.next,index-1);
    }*/
}
