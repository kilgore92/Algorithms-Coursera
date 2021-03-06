import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    
    private Point[] points;
    private LineSegment[] lines;
    private List<LineSegment> linesList;
    private Point[] sortedPoints;
    
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("Points array is null");
        }
        this.points = points;
        checkConditions();
        this.linesList = new ArrayList<LineSegment>();
        // Sort the points
        sortedPoints = this.points.clone();
        Arrays.sort(sortedPoints);
        findLines();
        lines = new LineSegment[linesList.size()];
        lines = linesList.toArray(lines);
    
    } // finds all line segments containing 4 points
    
    public int numberOfSegments() {
        return lines.length;
    
    } // the number of line segments
    
    public LineSegment[] segments() {
        return lines;
    
    } // the line segments
    
    private boolean isLine(Point p1, Point p2,Point p3) {
        return (p2.slopeTo(p1) == p3.slopeTo(p2));
    }
    
    private void findLines() {
        
        int count = 0;
        for (int i = 0; i < this.sortedPoints.length; i++) {
            count = 0;
            Point p1 = this.sortedPoints[i];
            for (int j = i+1; j < this.sortedPoints.length; j++) {
                Point p2 = this.sortedPoints[j];
                for (int k = j+1; k < this.sortedPoints.length;k++) {
                    Point p3 = this.sortedPoints[k];
                    if (isLine(p1,p2,p3)) {
                        for (int l = k+1; l < this.sortedPoints.length; l++) {
                            Point p4 = this.sortedPoints[l];
                            if (isLine(p2,p3,p4)) {
                                linesList.add(new LineSegment(p1,p4));
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void checkConditions() {
        // Check if any point is null
        for (int i = 0; i < this.points.length; i++) {
            if (this.points[i] == null)
                throw new java.lang.IllegalArgumentException();
        }
        
        for (int i = 0; i < this.points.length; i++) {
            for (int j = i+1; j < this.points.length;j++ ) {
                if (this.points[i].compareTo(this.points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();          
            }
        }
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}