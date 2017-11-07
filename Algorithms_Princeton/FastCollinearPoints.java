import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by xiaojing on 4/9/17.
 */
public class FastCollinearPoints {
    private ArrayList<LineSegment> segmentList = new ArrayList<>();
    private int segmentsNumber = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("input should not be null");
        Arrays.sort(points);
        int N = points.length;
        Point[] slopes = new Point[N - 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0, k = 0; j < N; j++) {
                if (j != i) slopes[k++] = points[j];
            }
            Arrays.sort(slopes, points[i].slopeOrder());
            int start = 0, end = 1;
            while (end < N - 1) {
                boolean forward = points[i].slopeTo(slopes[start]) == points[i].slopeTo(slopes[end]);
                if (forward) {
                    end++;
                }
                if (!forward || end == N - 1) {
                    if (end - start >= 3 && points[i].compareTo(slopes[start]) < 0) {
                        LineSegment segment = new LineSegment(points[i], slopes[end - 1]);
                        segmentList.add(segment);
                        segmentsNumber++;
                    }
                    start = end;
                    end = start + 1;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
