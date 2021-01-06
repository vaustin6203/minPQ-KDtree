package bearmaps;

import org.junit.Test;
import edu.princeton.cs.algs4.Stopwatch;

import static org.junit.Assert.assertEquals;
import java.util.Random;

public class ArrayHeapMinPQTest {

    @Test
    public void basicAddTest() {
        ArrayHeapMinPQ<String> mine = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> theirs = new NaiveMinPQ<>();
        Random randKey = new Random(1000000);
        Random randValue = new Random(1234091);
        for (int i = 0; i < 10000; i += 1) {
            String key = Integer.toString(randKey.nextInt());
            double value = randValue.nextDouble();
            mine.add(key, value);
            theirs.add(key, value);
        }
        assertEquals(theirs.getSmallest(), mine.getSmallest());
    }

    @Test
    public void basicRemoveSmallestTest() {
        ArrayHeapMinPQ<String> mine = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> theirs = new NaiveMinPQ<>();
        Random randKey = new Random(10000000);
        Random randValue = new Random(12340910);
        for (int i = 0; i < 100000; i += 1) {
            String key = Integer.toString(randKey.nextInt());
            double value = randValue.nextDouble();
            mine.add(key, value);
            theirs.add(key, value);
        }
        assertEquals(theirs.removeSmallest(), mine.removeSmallest());
        assertEquals(theirs.getSmallest(), mine.getSmallest());
    }

    @Test
    public void basicChangePriorityTest() {
        ArrayHeapMinPQ<String> mine = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> theirs = new NaiveMinPQ<>();
        Random randKey = new Random(10000000);
        Random randValue = new Random(12340910);
        for (int i = 0; i < 100000; i += 1) {
            String key = Integer.toString(randKey.nextInt());
            double value = randValue.nextDouble();
            mine.add(key, value);
            theirs.add(key, value);
        }
        mine.changePriority(mine.getSmallest(), 0.99167);
        theirs.changePriority(theirs.getSmallest(), 0.99167);
        assertEquals(theirs.removeSmallest(), mine.removeSmallest());
        mine.changePriority(mine.getSmallest(), 0.64210);
        theirs.changePriority(theirs.getSmallest(), 0.64210);
        assertEquals(theirs.getSmallest(), mine.getSmallest());
    }

    @Test
    public void removeSmallestTimeTest() {
        ArrayHeapMinPQ<String> mine = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> theirs = new NaiveMinPQ<>();
        Random randKey = new Random(234510000);
        Random randValue = new Random(145089000);
        for (int i = 0; i < 100000; i += 1) {
            String key = Integer.toString(randKey.nextInt());
            double value = randValue.nextDouble();
            mine.add(key, value);
            theirs.add(key, value);
        }
        Stopwatch mysw = new Stopwatch();
        for (int i = 0; i < 700; i += 1) {
            mine.removeSmallest();
        }
        double mytime = mysw.elapsedTime();
        Stopwatch theirsw = new Stopwatch();
        for (int i = 0; i < 700; i += 1) {
            theirs.removeSmallest();
        }
        double theirtime = theirsw.elapsedTime();
        System.out.println("my time:" + mytime + ", their time:" + theirtime);
    }

    @Test
    public void changePriorityTimeTest() {
        ArrayHeapMinPQ<String> mine = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> theirs = new NaiveMinPQ<>();
        Random randKey = new Random(879012000);
        Random randValue = new Random(532012100);
        String[] keys = new String[1000];
        for (int i = 0; i < 1000; i += 1) {
            String key = Integer.toString(randKey.nextInt());
            double value = randValue.nextDouble();
            mine.add(key, value);
            theirs.add(key, value);
            keys[i] = key;
        }
        double[] vals = new double[700];
        for (int i = 0; i < 700; i += 1) {
            vals[i] = randValue.nextDouble();
        }
        Stopwatch mysw = new Stopwatch();
        for (int i = 0; i < 700; i += 1) {
            mine.changePriority(keys[i], vals[i]);
        }
        double mytime = mysw.elapsedTime();
        Stopwatch theirsw = new Stopwatch();
        for (int i = 0; i < 700; i += 1) {
            theirs.changePriority(keys[i], vals[i]);
        }
        double theirtime = theirsw.elapsedTime();
        System.out.println("my time:" + mytime + ", their time:" + theirtime);
    }

    @Test
    public void addTimeTest() {
        ArrayHeapMinPQ<String> mine = new ArrayHeapMinPQ<>();
        Random randKey = new Random(999956012);
        Random randValue = new Random(632179974);
        for (int i = 0; i < 10000; i += 1) {
            String key = Integer.toString(randKey.nextInt());
            double value = randValue.nextDouble();
            mine.add(key, value);
        }
        String[] newkeys = new String[700];
        double[] newvals = new double[700];
        for (int i = 0; i < 700; i += 1) {
            newkeys[i] = Integer.toString(randKey.nextInt());
            newvals[i] = randValue.nextDouble();
        }
        Stopwatch mysw = new Stopwatch();
        for (int i = 0; i < 700; i += 1) {
            mine.add(newkeys[i], newvals[i]);
        }
        double mytime = mysw.elapsedTime();
        System.out.println("my time:" + mytime);
    }

    @Test
    public void overallPerformanceTest() {
        ArrayHeapMinPQ<String> mine = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> theirs = new NaiveMinPQ<>();
        Random randKey = new Random(999976412);
        Random randValue = new Random(996979434);
        for (int i = 0; i < 74300; i += 1) {
            String key = Integer.toString(randKey.nextInt());
            double value = randValue.nextDouble();
            mine.add(key, value);
            theirs.add(key, value);
        }
        Random newkeys = new Random(965348188);
        Random newvals = new Random(945321890);
        double[] storm = new double[1001];
        String[] tori = new String[1001];
        for (int i = 0; i < 1001; i += 1) {
            double v = newvals.nextDouble();
            String k = Integer.toString(newkeys.nextInt());
            storm[i] = v;
            tori[i] = k;
        }
        Stopwatch mysw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            mine.changePriority(mine.getSmallest(), storm[i]);
            mine.removeSmallest();
        }
        double mytime = mysw.elapsedTime();
        Stopwatch theirsw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            theirs.changePriority(theirs.getSmallest(), storm[i]);
            theirs.removeSmallest();
        }
        double theirtime = theirsw.elapsedTime();
        System.out.println("my time:" + mytime + ", their time:" + theirtime);
        assertEquals(mine.getSmallest(), theirs.getSmallest());
    }
}
