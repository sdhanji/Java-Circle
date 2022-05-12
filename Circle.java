/* 
 * PROJECT I: Circle.java
 *
 * This file contains a template for the class Circle. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Point class.
 *
 * Remember not to change the types, names, parameters or return types of any
 * functions or variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 * 
 * NAME:
 * UNIVERSITY ID:
 * DEPARTMENT:
 */

public class Circle {

    /*
     * Here, YOU should define private variables that represent the radius and
     * centre of this particular Circle. The radius should be of type double,
     * and the centre should be of type Point.
     */

     public double r;
     public Point A;
    

    // =========================
    // Constructors
    // =========================
    /**
     * Default constructor - performs no initialization.
     */
    public Circle() {
        // This method is complete.
    }
    
    /**
     * Alternative constructor, which sets the circle up with x and y
     * co-ordinates representing the centre, and a radius. Remember you should
     * not store these x and y co-ordinates explicitly, but instead create a
     * Point to hold them for you.
     *
     * @param xc   x-coordinate of the centre of the circle
     * @param yc   y-coordinate of the centre of the circle
     * @param rad  radius of the circle
     */
    public Circle(double xc, double yc, double rad) {
        Point point = new Point(xc, yc);
        this.setCentre(point);
        this.setRadius(rad);
    }

    /**
     * Alternative constructor, which sets the circle up with a Point
     * representing the centre, and a radius.
     *
     * @param centre  Point representing the centre
     * @param rad     Radius of the circle
     */
    
    public Circle(Point centre, double rad) {
        this.setCentre(centre);
        this.setRadius(rad);
    }

    // =========================
    // Setters and Getters
    // =========================

    /**
     * Setter - sets the co-ordinates of the centre.
     *
     * @param xc  new x-coordinate of the centre
     * @param yc  new y-coordinate of the centre
     */   
    public void setCentre(double xc, double yc) {
        this.A.setPoint(xc, yc);
    }

    /**
     * Setter - sets the centre of the circle to a new Point.
     *
     * @param C  A Point representing the new centre of the circle.
     */   
    public void setCentre(Point C) {
        this.A = C;
    }
    
    /**
     * Setter - change the radius of this circle.
     *
     * @param rad  New radius for the circle.
     */   
    public void setRadius(double rad) {
        this.r = rad;
    }
    
    /**
     * Getter - returns the centre of this circle.
     *
     * @return The centre of this circle (a Point).
     */   
    public Point getCentre(){

        return new Point(A.getX(), A.getY());
    }

    /**
     * Getter - extract the radius of this circle.
     *
     * @return The radius of this circle.
     */   
    public double getRadius(){

        return this.r;
    }

    // =========================
    // Convertors
    // =========================

    /**
     * Calculates a String representation of the Circle.
     *
     * @return A String of the form: "[Ax,Ay,Radius]" where Ax and Ay are
     *         numerical values of the coordinates, and Radius is a numerical
     *         value of the radius.
     */
    public String toString() {
        // You need to fill in this method.
        return String.format("[%.9f,%.9f,%.9f]",this.getCentre().getX(), this.getCentre().getY(), this.getRadius());
    }
    
    // ==========================
    // Service routines
    // ==========================
    
    /**
     * Similar to the equals() function in Point. Returns true if two Circles
     * are equal. By this we mean:
     * 
     * - They have the same radius (up to the tolerance defined in Point).
     * - They have the same centre (up to the tolerance defined in Point).
     * 
     * Remember that the second test is already done in the Point class!
     * 
     * @param c The circle to compare this with.
     * @return true if the two circles are equal.
     */
    public boolean equals(Circle c) {
        return (Math.abs(this.getRadius() - c.getRadius()) <= Point.GEOMTOL &&
                Math.abs(this.getCentre().getX() - c.getCentre().getX()) <= Point.GEOMTOL && 
                Math.abs(this.getCentre().getY() - c.getCentre().getY()) <= Point.GEOMTOL);
    }
    
    // -----------------------------------------------------------------------
    // Do not change the method below! It is essential for marking the
    // project, and you may lose marks if it is changed.
    // -----------------------------------------------------------------------

    /**
     * Compare this Circle with some Object, using the test above.
     *
     * @param obj  The object to compare this with.
     * @return true if the two objects are equal.
     */
    public boolean equals(Object obj) {
        // This method is complete.
        
        if (obj instanceof Circle) {
            boolean test = false;
            Circle C = (Circle)obj;
            
            test = this.equals(C);

            return test;
        } else {
            return false;
        }
    }

    // ======================================
    // Implementors
    // ======================================
    
    /**
     * Computes and returns the area of the circle.
     *
     * @return  Area of the circle
     */
    public double area() {
        
        return Math.PI * Math.pow(this.getRadius(), 2);
    }

    // =======================================================
    // Tester - test methods defined in this class
    // =======================================================
    
    public static void main(String args[]) {
        // You can use this method for testing.
        Point point = new Point(1, 2);
        Circle circle1 = new Circle(point, 3);
        Circle circle2 = new Circle(10.5, -6, 0.3);
        Circle circle3 = new Circle(1, 2, 3);
        Circle circle4 = new Circle(1, 2, 3);
        System.out.println(circle1.area());
        System.out.println(circle2.area());
        System.out.println(circle1.getCentre());
        System.out.println(circle2.getCentre());
        System.out.println(circle1.getRadius());
        System.out.println(circle2.getRadius());
        System.out.println(circle1.toString());
        System.out.println(circle2.toString());
        System.out.println(circle1.equals(circle2));
        System.out.println(circle1.equals(circle3));
        System.out.println(circle4.equals(circle3));
    }
}
