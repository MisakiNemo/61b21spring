package deque;

import jdk.jshell.execution.Util;
import net.sf.saxon.functions.Reverse;
import org.apache.commons.math3.util.OpenIntToFieldHashMap;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class ArrayDequeTest {
    @Test
    public void addLastTest(){
        ArrayDeque<Double> d=new ArrayDeque<Double>();
        Double[] expect=new Double[]{1.14,3.14,4.54,4.4,5.5,5.4,6.6,7.8};
        Double[] output=new Double[8];
        for (int i = 0; i < expect.length; i++) {
           d.addLast(expect[i]);
        }
        for (int i = 0; i < d.size(); i++) {
           output[i]=d.get(i);
        }
       org.junit.Assert.assertEquals(expect,output);

    }
    @Test
    public void  addFirstTest(){
        ArrayDeque<Double> d=new ArrayDeque<Double>();
        Double[] expect=new Double[]{1.14,3.14,4.54,4.4,5.5,5.4,6.6,7.8};
        Double[] output=new Double[8];
        for (int i = 0; i < expect.length; i++) {
            d.addFirst(expect[expect.length-i-1]);
        }
        for (int i = 0; i < d.size(); i++) {
            output[i]=d.get(i);
        }
        org.junit.Assert.assertEquals(expect,output);

    }
    @Test
    public void  isEmptyTest(){
        ArrayDeque<Double> d=new ArrayDeque<Double>();
        if(d.isEmpty()){
            System.out.println("true");
        }
        Double[] expect=new Double[]{1.14,3.14,4.54,4.4,5.5,5.4,6.6,7.8};
        Double[] output=new Double[8];
        for (int i = 0; i < expect.length; i++) {
            d.addFirst(expect[expect.length-i-1]);
        }
        for (int i = 0; i < d.size(); i++) {
            output[i]=d.get(i);
        }
        if(!d.isEmpty()){
            System.out.println("true");
        }
}
    @Test
    public void  IteratorTest() {
        ArrayDeque<Double> d = new ArrayDeque<Double>();
        Double[] expect = new Double[]{1.14, 3.14, 4.54, 4.4, 5.5, 5.4, 6.6, 7.8};
        Double[] output = new Double[8];
        for (int i = 0; i < expect.length; i++) {
            d.addLast(expect[i]);
        }
         Iterator<Double> it =  d.iterator();
        int i=0;
        for(Double item: d){
            output[i++]=item;
        }
        org.junit.Assert.assertArrayEquals(expect,output);
    }
    @Test
    public void RemoveFirstTest(){
        ArrayDeque<Double> d = new ArrayDeque<Double>();
        Double[] array = new Double[]{1.14, 3.14, 4.54, 4.4, 5.5, 5.4, 6.6, 7.8};
        Double[] output = new Double[7];
        for (int i = 0; i < array.length; i++) {
            d.addLast(array[i]);
        }
        Iterator<Double> it =  d.iterator();
        Double t=d.removeFirst();
        Double[] expected=new Double[7];
        System.arraycopy(array,1,expected,0,7);
        int i=0;
        for(Double item:d){
            output[i++]=item;
        }
        org.junit.Assert.assertArrayEquals(expected,output);
        org.junit.Assert.assertEquals((Double) 1.14,t);

    }
    @Test
    public void RemoveLastTest(){
        ArrayDeque<Double> d = new ArrayDeque<Double>();
        Double[] array = new Double[]{1.14, 3.14, 4.54, 4.4, 5.5, 5.4, 6.6, 7.8};
        Double[] output = new Double[7];
        for (int i = 0; i < array.length; i++) {
            d.addLast(array[i]);
        }
        Iterator<Double> it =  d.iterator();
        Double t=d.removeLast();
        Double[] expected=new Double[7];
        System.arraycopy(array,0,expected,0,7);
        int i=0;
        for(Double item:d){
            output[i++]=item;
        }
        org.junit.Assert.assertArrayEquals(expected,output);
       org.junit.Assert.assertEquals((Double) 7.8,t);
    }


}
