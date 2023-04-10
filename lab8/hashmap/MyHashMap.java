package hashmap;

import java.security.Key;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void clear() {

    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(key,getIndex(key));
    }
    private boolean containsKey(K key,int index)
    {
        return get(key,index)==null;
    }

    @Override
    public V get(K key) {
        return get(key,getIndex(key));
    }
    private V get(K key,int bucketIndex)
    {
          for(Node node:buckets[bucketIndex])
          {
              if(node.key==key)
              {
                  return node.value;
              }
          }
          return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int bucketIndex=getIndex(key);
        Node node=getNode(key,bucketIndex);
        if(node!=null)
        {
            node.value=value;
            return;
        }

        buckets[bucketIndex].add(createNode(key,value));
        ++size;
        if(biggerThanmaxLoadFactor())
        {
            resize(buckets.length*2);
        }
        return;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V remove(K key) {
        int bucketIndex=getIndex(key);
        Node node=getNode(key);
        if(node==null)
        {
            return null;
        }
        V val= node.value;
        buckets[bucketIndex].remove(node);
        --size;
        return val;
    }

    @Override
    public V remove(K key, V value) {
        if(get(key)==value)
        {
            remove(key);
            --size;
            return value;
        }
        else{
            return get(key);
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }
    private class MyHashMapIterator implements Iterator<K>{
        private final Iterator<Node> nodeIterator= new MyHashMapNodeIterator();
        @Override
        public boolean hasNext() {
            return nodeIterator.hasNext();
        }

        @Override
        public K next() {
            return nodeIterator.next().key;
        }
    }
    private  class  MyHashMapNodeIterator implements Iterator<Node>{
        private  final Iterator<Collection<Node>> bucketsIterator= Arrays.stream(buckets).iterator();
        private  Iterator<Node> curentBucketItorator;
        private int nodeLeft=size();
        @Override
        public boolean hasNext() {
               return nodeLeft>0;
        }

        @Override
        public Node next() {
            if(curentBucketItorator==null||bucketsIterator.hasNext())
            {
                Collection<Node> currentBucket=bucketsIterator.next();
                while(currentBucket.size()==0)
                {
                    currentBucket=bucketsIterator.next();
                }
                curentBucketItorator=currentBucket.iterator();
            }
             --nodeLeft;
            return curentBucketItorator.next();
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }

    }

    /* Instance Variables */
    private static  final int DEFAULT_INITIAL_SIZE=16;
    private static  final double DEFAULT_MAX_LOAD_FACTOR=0.75;
    private final double maxLoadFactor;
    private Collection<Node>[] buckets;
    private int size=0;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INITIAL_SIZE,DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize,DEFAULT_MAX_LOAD_FACTOR);
    }


    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets=createTable(initialSize);
        maxLoadFactor=maxLoad;

    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return null;
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table= (Collection[]) new Object[tableSize];
        for (int i = 0; i < tableSize; i++) {
           table[i]=createBucket();
        }
        return table;
    }
    private int getIndex(K  key)
    {
        return getIndex(key,buckets);
    }
    private int getIndex(K key, Collection<Node>[] table)
    {
        return key.hashCode()%table.length;
    }
    private Node getNode(K key)
    {
        return getNode(key,getIndex(key));
    }
    private  Node getNode(K key,int bucketIndex)
    {
        for(Node node:buckets[bucketIndex])
        {
            if(node.key==key)
            {
                return node;
            }
        }
        return null;
    }
    private boolean biggerThanmaxLoadFactor()
    {
        return (double)(size()/buckets.length)>maxLoadFactor;
    }
    private void  resize(int capacity)
    {
         Collection<Node>[] newbuckets=createTable(capacity);
         Iterator<Node> nodeIterator=new MyHashMapNodeIterator();
         while(nodeIterator.hasNext())
         {
             Node node=nodeIterator.next();
             int nodeIndex=getIndex(node.key);
             newbuckets[nodeIndex].add(node);
         }
         buckets=newbuckets;
    }
    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

}
