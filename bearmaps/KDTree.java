package bearmaps;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;

public class KDTree implements PointSet {
    private KDNode root;
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private HashSet<Point> noDups;

    private class KDNode {
        Point point;
        boolean orientation;
        KDNode less;     //left child or down child
        KDNode greater; //right child or up child

        private KDNode(Point p, boolean b, KDNode l, KDNode g) {
            point = p;
            orientation = b;
            less = l;
            greater = g;
        }
    }

    private class KDPair {
        KDNode node;
        double dist;

        private KDPair(KDNode n, double distance) {
            node = n;
            dist = distance;
        }
    }

    /** KDTree Constructor, adds points in a balanced way by comparing
     * Point values to ensure efficient search for nearest method.
     * @param points
     * @source
     * * Used source to learn how to iterate through a HashSet.
     * https://beginnersbook.com/2014/08/how-to-iterate-over-a-sethashset/
     */
    public KDTree(List<Point> points) {
        noDups = new HashSet<>(points);
        Iterator<Point> it = noDups.iterator();
        while (it.hasNext()) {
            if (root == null) {
                root = new KDNode(it.next(), HORIZONTAL, null, null);
            } else {
                comparePoints(root, it.next());
            }
        }
    }


    /** Compares either the X or Y coordinate of the given
     * KDnode's point and the given point to be added based
     * on the orientation of the KDNode. Makes a recursive call
     * to each KDNodes children until reaches the leaf of a branch.
     * Once a leaf is hit, adds the point to either the less or
     * greater branch of leaf based on point value comparisons.
     * @param p2
     */
    private void comparePoints(KDNode p1, Point p2) {
        int comp = 0;
        boolean opp = VERTICAL;
        if (p1.orientation == HORIZONTAL) {
            comp = Double.compare(p1.point.getX(), p2.getX());
        } else {
            comp = Double.compare(p1.point.getY(), p2.getY());
            opp = HORIZONTAL;
        }
        if (comp > 0) {
            if (p1.less == null) {
                p1.less = add(p2, opp);
            } else {
                comparePoints(p1.less, p2);
            }
        } else {
            if (p1.greater == null) {
                p1.greater = add(p2, opp);
            } else {
                comparePoints(p1.greater, p2);
            }
        }
    }

    /** Creates a new KDNode to add to this KDTree given
     * a Point and an orientation.
     * @param p
     * @param orient
     * @return
     */
    private KDNode add(Point p, boolean orient) {
        return new KDNode(p, orient, null, null);
    }

    private int compareSides(KDNode n, Point p) {
        if (n.orientation == HORIZONTAL) {
            return Double.compare(n.point.getX(), p.getX());
        } else {
            return Double.compare(n.point.getY(), p.getY());
        }
    }

    private boolean checkBadSide(KDPair best, KDNode checked, Point p) {
        if (checked == null) {
            return false;
        }
        if (checked.orientation == VERTICAL) {
            double hypDist = Math.pow(p.getY() - checked.point.getY(), 2.0);
            if (hypDist < best.dist) {
                return true;
            }
        } else {
            double hypDist = Math.pow(p.getX() - checked.point.getX(), 2.0);
            if (hypDist < best.dist) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        if (root.less == null && root.greater == null) {
            return root.point;
        } else if (noDups.contains(p)) {
            return p;
        }
        noDups.clear();
        KDPair min = new KDPair(root, Point.distance(root.point, p));
        KDNode good = findGoodSide(root, p);
        KDNode bad = root.greater;
        if (good == bad) {
            bad = root.less;
        }
        min = nearestHelper(min, good, p);
        if (checkBadSide(min, root, p)) {
            min = nearestHelper(min, bad, p);
        }
        return min.node.point;
    }

    private KDNode findGoodSide(KDNode node, Point p) {
        int comp = compareSides(node, p);
        if (comp > 0) {
            return node.less;
        } else if (comp < 0) {
            return node.greater;
        } else {
            double diff = node.point.getX() - p.getX();
            if (node.orientation == HORIZONTAL) {
                diff = node.point.getY() - p.getY();
            }
            if (diff < 0) {
                return node.greater;
            } else if (diff > 0) {
                return node.less;
            }
        }
        return node;
    }

    private KDPair nearestHelper(KDPair best, KDNode gchild, Point p) {
        if (gchild == null) {
            return best;
        }
        KDPair check = new KDPair(gchild, Point.distance(gchild.point, p));
        KDNode good = gchild;
        KDNode bad = best.node;
        if (check.dist < best.dist) {
            best = check;
            good = findGoodSide(best.node, p);
            if (good == best.node.less) {
                bad = best.node.greater;
            } else {
                bad = best.node.less;
            }
        } else {
            good = findGoodSide(gchild, p);
            if (good == gchild.less) {
                bad = gchild.greater;
            } else if (good == gchild.greater) {
                bad = gchild.less;
            }
        }
        best = nearestHelper(best, good, p);
        if (checkBadSide(best, gchild, p)) {
            best = nearestHelper(best, bad, p);
        }
        return best;
    }
}
