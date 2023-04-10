package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class BSTMap<K extends Comparable,V extends Comparable> implements Map61B<K,V> {
    private int size;
    BSTNode root;
    public BSTMap(){
        root=null;
        size=0;

    }
    @Override
    public void clear() {
        root=null;;
        size=0;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root,key);
    }
    private boolean containsKey(BSTNode node,K key){
            if(root==null){
                return false;
            }
        int cmp=key.compareTo(node.key);
        if(cmp>0){
            return containsKey(node.right,key);
        }
        else if(cmp<0){
            return containsKey(node.left,key);
        }
            return true;
    }

    @Override
    public V get(K key) {
        return get(root,key);
    }

    private V get(BSTNode node,K key){
        if(node==null){
            return null;
        }
        int cmp=key.compareTo(node.key);
        if(cmp>0){
            return get(node.right,key);
        }
        else if(cmp<0){
            return get(node.left,key);
        }
        return node.val;
    }
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        root=put(root,key,value);
        ++size;
    }
     private BSTNode put(BSTNode node,K key ,V value){
        if(node==null){
            return new BSTNode(key,value);
        }
        int cmp=key.compareTo(node.key);
        if(cmp>0){
            node.right=put(node.right,key,value);
        }
        else if(cmp<0){
            node.left=put(node.left,key,value);
        }
        else {
            node.val=value;
        }
        return node;
     }


    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
    throw new UnsupportedOperationException();
    }

    private class BSTNode {
         K key;
         V val;
         BSTNode left;
         BSTNode right;

         public BSTNode(){}
        public BSTNode(K key, V val) {
            this.key = key;
            this.val = val;
            left=right=null;
        }

        //insert the node with key and val to the tree
        public BSTNode get(K key){
            if(key==null){
                return null;
            }
            if(key.equals(this.key)){
                return this;
            }
            if(left==null&&right==null){
                return null;
            }
            else if(key.compareTo(this.key)>0&&this.left!=null){
                return left.get(key);
            }
            else if(key.compareTo(this.key)<0&&right!=null){
                return right.get(key);
            }
            return null;
        }
        public  BSTNode delete(BSTNode node,K key){
                 if(node==null){
                     return null;
                 }
                 if(node.key.compareTo(key)>0){
                     node.left=delete(node.left,key);
                 }
                 else if(node.key.compareTo(key)<0){
                     node.right=delete(node.right,key);
                     return node;
                 }
                 if(node.left==null&&node.right==null){
                     node=null;
                     return node;
                 }
                 if(node.left!=null&&node.right==null){
                     node=node.left;
                 }
                 if(node.right!=null&&node.left==null){
                     node=node.right;
                 }
                 else{
                     BSTNode leftMaxNode=findMaxvalinleft(node.left);
                     node.key=leftMaxNode.key;
                     node.val=leftMaxNode.val;
                     leftMaxNode=null;
                 }
                 return node;
        }
        private BSTNode findMaxvalinleft(BSTNode node){
            if(node.right==null){
                return node;
            }
            return node.right;
        }
    }

}
