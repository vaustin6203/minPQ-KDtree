package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import edu.princeton.cs.algs4.Stopwatch;

public class KDTreeTest {

    @Test
    public void testNearestTime() {
        List<Point> randPoints = new ArrayList<>();
        Random randX = new Random(987654998);
        Random randY = new Random(984537289);
        for (int i = 0; i < 100000; i += 1) {
            Point p = new Point(randX.nextInt(), randY.nextInt());
            randPoints.add(p);
        }
        KDTree kd = new KDTree(randPoints);
        NaivePointSet np = new NaivePointSet(randPoints);
        ArrayList<Point> searches = new ArrayList<>();
        for (int i = 0; i < 10000; i += 1) {
            Point p = new Point(randX.nextInt(), randY.nextInt());
            searches.add(p);
        }
        Stopwatch npWatch = new Stopwatch();
        for (Point p: searches) {
            np.nearest(p.getX(), p.getY());
        }
        double npTime = npWatch.elapsedTime();
        Stopwatch kdWatch = new Stopwatch();
        for (Point p : searches) {
            kd.nearest(p.getX(), p.getY());
        }
        double kdTime = kdWatch.elapsedTime();
        System.out.println("their's: " + npTime + " mine: " + kdTime);
    }

    @Test
    public void testMainNearest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        List<Point> points = List.of(p1, p2, p3, p4, p5, p6, p7);
        KDTree kd = new KDTree(points);
        NaivePointSet np = new NaivePointSet(points);
        Point expected = np.nearest(0.0, 7.0);
        Point actual = kd.nearest(0.0, 7.0);
        assertEquals(expected, actual);
    }

    @Test
    public void testNearest() {
        List<Point> randPoints = new ArrayList<>();
        Random randX = new Random(987674309);
        Random randY = new Random(988954328);
        for (int i = 0; i < 100000; i += 1) {
            Point p = new Point(randX.nextInt(), randY.nextInt());
            randPoints.add(p);
        }
        KDTree kd = new KDTree(randPoints);
        NaivePointSet np = new NaivePointSet(randPoints);
        ArrayList<Point> searches = new ArrayList<>();
        for (int i = 0; i < 1000; i += 1) {
            Point search = new Point(randX.nextInt(), randY.nextInt());
            searches.add(search);
        }
        for (Point p: searches) {
            Point mine = kd.nearest(p.getX(), p.getY());
            Point theirs = np.nearest(p.getX(), p.getY());
            assertEquals(theirs, mine);
        }
    }
}
