import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by xiaojing on 4/9/17.
 */
public class BruteCollinearPoints {

    private ArrayList<LineSegment> segmentList = new ArrayList<>();
    private int segmentsNumber = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("input should not be null");
        Point[] copy = new Point[points.length];
        for(int i = 0; i <points.length; i++) copy[i]=points[i];
        Arrays.sort(copy);
        for (int i = 0; i < copy.length; i++)
            for (int j = i + 1; j < copy.length; j++) {
                if (copy[i].compareTo(copy[j]) == 0) throw new IllegalArgumentException("repeated point");
                double slope1 = copy[i].slopeTo(copy[j]);
                for (int k = j + 1; k < copy.length; k++) {
                    double slope2 = copy[i].slopeTo(copy[k]);
                    if (slope1 == slope2) {
                        for (int l = k + 1; l < copy.length; l++) {
                            double slope3 = copy[i].slopeTo(copy[l]);
                            if (slope3 == slope1) {
                                segmentsNumber++;
                                LineSegment segment = new LineSegment(copy[i], copy[l]);
                                segmentList.add(segment);
                            }
                        }
                    }
                }
            }
    }

    public int numberOfSegments() {
        return segmentsNumber;
    }

    public LineSegment[] segments() {
        return segmentList.toArray(new LineSegment[segmentList.size()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}

