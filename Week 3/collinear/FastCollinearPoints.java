/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lines;

    public FastCollinearPoints(Point[] points) {
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

        if (points.length < 4) {
            lines = new LineSegment[0];
            return;
        }

        // Make a copy to sort and play around with
        Point[] copy = new Point[points.length];
        for (int k = 0; k < points.length; k++) {
            copy[k] = points[k];
        }

        // Init local var
        int size = 1;
        lines = new LineSegment[size];
        int numOfLines = 0;

        // Iterate through all points making each the pivot
        for (int i = 0; i < points.length; i++) {
            Point pivot = points[i];

            // Sorting by location using compareTo
            sort(copy);
            Point[] sortByLoc = new Point[copy.length];
            for (int k = 0; k < copy.length; k++) {
                sortByLoc[k] = copy[k];
            }

            // Sorting by slope order relative to pivot
            Arrays.sort(copy, pivot.slopeOrder());

            int counter = 0;
            double prevSlope = Double.NEGATIVE_INFINITY;
            int first = 1;
            int j;

            // Check for 3 or more consecutive slopes that are the same
            for (j = 1; j < points.length; j++) {
                if (pivot.slopeTo(copy[j]) == prevSlope) {
                    counter++;
                } else {
                    // If 3 or more consecutive slopes found, then create a new segment
                    if (counter >= 3 && pivot.compareTo(copy[first]) < 0) {
                        LineSegment line = new LineSegment(pivot, copy[j-1]);

                        if (numOfLines == size) {
                            resize(size * 2, numOfLines);
                            size *= 2;
                        }

                        lines[numOfLines] = line;
                        numOfLines += 1;
                    }

                    first = j;
                    counter = 1;
                    prevSlope = pivot.slopeTo(copy[j]);
                }
            }

            // Cleaning up with any possible extra segments
            if (counter >= 3 && pivot.compareTo(copy[first]) < 0) {
                LineSegment line = new LineSegment(pivot, copy[j-1]);

                if (numOfLines == size) {
                    resize(size * 2, numOfLines);
                    size *= 2;
                }

                lines[numOfLines] = line;
                numOfLines += 1;
            }

        }
        resize(numOfLines, numOfLines);
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

    private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++)
        {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    private static void sort(Point[] a, Point[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void sort(Point[] a)
    {
        Point[] aux = new Point[a.length];
        sort(a, aux, 0, a.length - 1);
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
