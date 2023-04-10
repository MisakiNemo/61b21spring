package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @org.junit.Test
    public void BuggyAListTest(){
        BuggyAList<Integer> L = new BuggyAList<>();
        int N = 100000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);

            }
            else if(operationNumber==2)
            {
                int lastnum=L.getLast();
                L.removeLast();
                System.out.println("lastnum: " + lastnum);
            }
        }
    }
}
