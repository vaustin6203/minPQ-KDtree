package bearmaps;
import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point newPoint = new Point(x, y);
        double minDist = 100.0;
        Point nearestPoint = points.get(0);
        int i = 0;
        for (Point p : points) {
            double d = Point.distance(p, newPoint);
            if (i == 0) {
                minDist = d;
            } else if (d < minDist) {
                minDist = d;
                nearestPoint = p;
            }
            i += 1;
        }
        return nearestPoint;
    }
}
