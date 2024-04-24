/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] lines;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }

            for (int j = 0; j < points.length && j != i; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = 0; j < points.length - i - 1; j++) {
                if (points[j].compareTo(points[j + 1]) > 0) {
                    Point temp = points[j];
                    points[j] = points[j + 1];
                    points[j + 1] = temp;
                }
            }
        }

        int counter = 0;
        int size = 1;
        lines = new LineSegment[size];

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        double m1 = points[i].slopeTo(points[j]);
                        double m2 = points[i].slopeTo(points[k]);
                        double m3 = points[i].slopeTo(points[m]);

                        if (m1 == m2 && m2 == m3) {
                            LineSegment line = new LineSegment(points[i], points[m]);

                            if (counter == size) {
                                resize(size * 2, counter);
                                size *= 2;
                            }

                            lines[counter] = line;
                            counter += 1;
                        }
                    }
                }
            }
        }

        this.resize(counter, counter);
    }

    public int numberOfSegments() {
        return lines.length;
    }

    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[lines.length];

        for (int i = 0; i < lines.length; i++) {
            copy[i] = lines[i];
        }

        return copy;
    }


    private void resize(int newSize, int oldSize) {
        LineSegment[] temp = new LineSegment[newSize];

        for (int i = 0; i < oldSize; i++) {
            temp[i] = lines[i];
        }

        lines = temp;
    }

    public static void main(String[] args) {
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
