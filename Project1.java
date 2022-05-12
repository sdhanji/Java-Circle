/*
 * PROJECT I: Project1.java
 *
 * As in project 0, this file - and the others you downloaded - form a
 * template which should be modified to be fully functional.
 *
 * This file is the *last* file you should implement, as it depends on both
 * Point and Circle. Thus your tasks are to:
 *
 * 1) Make sure you have carefully read the project formulation. It contains
 *    the descriptions of all of the functions and variables below.
 * 2) Write the class Point.
 * 3) Write the class Circle
 * 4) Write this class, Project1. The results() method will perform the tasks
 *    laid out in the project formulation.
 * 
 * NAME:
 * UNIVERSITY ID:
 * DEPARTMENT:
 */

import java.util.*;

import javax.print.PrintServiceLookup;
import javax.sql.rowset.spi.XmlReader;

import java.io.*;
import java.sql.Array;

public class Project1 {
    // -----------------------------------------------------------------------
    // Do not modify the names or types of the instance variables below! This 
    // is where you will store the results generated in the Results() function.
    // -----------------------------------------------------------------------
    public int      circleCounter; // Number of non-singular circles in the file.
    public double[] aabb;          // The bounding rectangle for the first and 
                                   // last circles
    public double   Smax;          // Area of the largest circle (by area).
    public double   Smin;          // Area of the smallest circle (by area).
    public double   areaAverage;   // Average area of the circles.
    public double   areaSD;        // Standard deviation of area of the circles.
    public double   areaMedian;    // Median of the area.
    public int      stamp = 220209;
    // -----------------------------------------------------------------------
    // You should implement - but *not* change the types, names or parameters of
    // the variables and functions below.
    // -----------------------------------------------------------------------

    /**
     * Default constructor for Project1. You should leave it empty.
     */
    public Project1() {
        // This method is complete.
    }

    /**
     * Results function. It should open the file called fileName (using
     * Scanner), and from it generate the statistics outlined in the project
     * formulation. These are then placed in the instance variables above.
     *
     * @param fileName  The name of the file containing the circle data.
     */
    public void results(String fileName){
    double x, y, rad;
    ArrayList<Circle> dynamicCircleList = new ArrayList<Circle>();
    
    try (
      Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))
      ) {
    
      while(scanner.hasNext()) {
      
        //Read the three values on each line, make ArrayList of circles
        x = scanner.nextDouble();
        y = scanner.nextDouble();
        rad = scanner.nextDouble();
        dynamicCircleList.add(new Circle(x, y, rad));  

    }

    
    //convert array list to array (so it can be passed into functions)
    Circle[] staticCircleList = dynamicCircleList.toArray(new Circle[dynamicCircleList.size()]);




    //Calculate all results needed with functions below
    circleCounter = count(staticCircleList);
    Smax = CalculateSMax(staticCircleList);
    Smin = CalculateSMin(staticCircleList);
    areaAverage = averageCircleArea(staticCircleList);
    areaSD = areaStandardDeviation(staticCircleList);
    areaMedian = medianArea(staticCircleList);


    //to calculate aabb, we need to make an array with 10th&20th non singular circles, to pass into calculateAABB() function
    Circle[] circles1020 = new Circle[2];
    staticCircleList = removeSingularCircles(staticCircleList);
    if (staticCircleList.length >= 20) {
      circles1020[0] = staticCircleList[9];
      circles1020[1] = staticCircleList[19];
    }
    aabb = calculateAABB(circles1020);

      
    } catch(Exception e) {
      System.err.println("An error has occured. See below for details");
      e.printStackTrace();
    } 
    }

    /**
     * A function to remove all singular circles in the array provided.
     *
     * @param circles  An array of Circles
     */
    public Circle[] removeSingularCircles(Circle[] circles){

      ArrayList<Circle> dynamicList = new ArrayList<Circle>();

      //if circle non-singular, add it to arraylist
      for (int i = 0; i < circles.length; i++) {
        if (circles[i].getRadius() > Point.GEOMTOL) {
          dynamicList.add(circles[i]);
        }
      }
      //array list now has all non-singular circles, so convert back to array
      circles = dynamicList.toArray(new Circle[dynamicList.size()]);
      return circles;
    }





    /**
     * A function to count the number of non-singular circles in the array provided. 
     *
     * @param circles  An array of Circles
     */
    public int count(Circle[] circles){
      circles = removeSingularCircles(circles);
      return circles.length;
    }






    /**
     * A function to calculate the maximum area of circles in the array provided. 
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     */
    public double CalculateSMax(Circle[] circles){
      circles = removeSingularCircles(circles);
      if (circles.length > 0) {
        double MAX = Double.MIN_VALUE;

        //foreach circle in array, if area > MAX, set MAX = area. Will end up with maximum area
        for (int i = 0; i < circles.length; i++) {
          if ((Math.PI * Math.pow(circles[i].getRadius(), 2)) > MAX) {
            MAX = Math.PI * Math.pow(circles[i].getRadius(), 2);
          }
        }
        return MAX;
      }
      return 0.0;
    }




    /**
     * A function to calculate the minimum area of circles in the array provided. 
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     */
    public double CalculateSMin(Circle[] circles){
      circles = removeSingularCircles(circles);
      if (circles.length > 0) {
        double MIN = Double.MAX_VALUE;

        //foreach circle in array, if area < MIN, set MIN = area. Will end up with minimum area
        for (int i = 0; i < circles.length; i++) {
          if ((Math.PI * Math.pow(circles[i].getRadius(), 2)) < MIN) {
            MIN = Math.PI * Math.pow(circles[i].getRadius(), 2);
          }
        }
        return MIN;
      }
      return 0.0;
    }




    /**
     * A function to calculate the median area of circles in the array provided. 
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     */
    public double medianArea(Circle[] circles){
      double median;
      circles = removeSingularCircles(circles);

      if (circles.length > 0) {
        //make array of radii of (non-singular) circles
      double radii[] = new double[circles.length];
      for (int i = 0; i < radii.length; i++) {
        radii[i] = circles[i].getRadius();
      }

      //sort radii in ascending order
      Arrays.sort(radii);

      double areas[] = new double[radii.length];
      for (int i = 0; i < areas.length; i++) {
        areas[i] = Math.PI * Math.pow(radii[i], 2);
      }

      //median index is different depending on if number of circles is even or odd
      if (areas.length % 2 == 1) {
        median = areas[(int)(0.5 * (areas.length - 1))];
      }
      else{
        median = 0.5 * (areas[(int)(areas.length / 2)] + areas[(int)((areas.length / 2) - 1)]);
      }
      return median;
      }
      return 0.0;
    }
    




    /**
     * A function to calculate the avarage area of circles in the array provided. 
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     */
    public double averageCircleArea(Circle[] circles) {
      circles = removeSingularCircles(circles);
      if (circles.length > 0) {
        double sum = 0;

        //sum all the areas
        for (int i = 0; i < circles.length; i++) {
          sum += Math.PI * Math.pow(circles[i].getRadius(), 2);
        }
  
        //return sum/number of circles (= mean)
        return sum/circles.length;
      }
      return 0.0;
    }
    
    /**
     * A function to calculate the standard deviation of areas in the circles in the array provided. 
     * This array may contain 0 or more circles.
     * 
     * @param circles  An array of Circles
     */
    public double areaStandardDeviation(Circle[] circles) {
      circles = removeSingularCircles(circles);
      if (circles.length > 0) {
        double mean = averageCircleArea(circles);
        double sumSquared = 0;
        
        //sum the square of all the areas
        for (int i = 0; i < circles.length; i++) {
          sumSquared += Math.pow(Math.PI * Math.pow(circles[i].getRadius(), 2), 2);
        }
  
        //calculate sd
        double sd = Math.sqrt((sumSquared / circles.length) - (Math.pow(mean, 2)));
        return sd;
      }
      return 0.0;
    }
    
    /**
     * Returns 4 values in an array [X1,Y1,X2,Y2] that define the rectangle
     * that surrounds the array of circles given.
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     * @return An array of doubles [X1,Y1,X2,Y2] that define the bounding rectangle with
     *         the origin (bottom left) at [X1,Y1] and opposite corner (top right)
     *         at [X2,Y2]
     */
    public double[] calculateAABB(Circle[] circles)
    {
        circles = removeSingularCircles(circles);
        if ((circles.length > 0) && (circles[0] != null)) {
        //(x1, y1) and (x2, y2) are the coordinates that bound the rectangle (to be calculated)
         //xMin = array of left-most positions of all circles = x-coordinate of centre - radius. (minimum of these = x1)
         //yMin = array of lowest positions of all circles = y-coordinate of centre - radius (minimum of these = y1)
         //xMax = array of right-most positions of all circles = x-coordinate of centre + radius (maximum of these = x2)
         //yMax = array of highest positions of all circles = y-coordinate of centre + radius (maximum of these = y2)

         double x1, y1, x2, y2;
         double[] xMin, yMin, xMax, yMax;
         x1 = Double.MAX_VALUE;
         y1 = Double.MAX_VALUE;
         x2 = -Double.MAX_VALUE;
         y2 = -Double.MAX_VALUE;
         xMin = new double[circles.length];
         yMin = new double[circles.length];
         xMax = new double[circles.length];
         yMax = new double[circles.length];


         //fills arrays with relevant values
         for (int i = 0; i < circles.length; i++) {
           xMin[i] = circles[i].getCentre().getX() - circles[i].getRadius();
           yMin[i] = circles[i].getCentre().getY() - circles[i].getRadius();
           xMax[i] = circles[i].getCentre().getX() + circles[i].getRadius();
           yMax[i] = circles[i].getCentre().getY() + circles[i].getRadius();
         }


         for (int i = 0; i < circles.length; i++) {
           if (xMin[i] < x1) {
             x1 = xMin[i];
           }
           if (yMin[i] < y1) {
            y1 = yMin[i];
          }
          if (xMax[i] > x2) {
            x2 = xMax[i];
          }
          if (yMax[i] > y2) {
            y2 = yMax[i];
          }
         }


        return new double[]{x1, y1, x2, y2};
        }
        else{

        
        return new double[]{0,0,0,0};
        }
         
    }

  
    // =======================================================
    // Tester - tests methods defined in this class
    // =======================================================

    /**
     * Your tester function should go here (see week 14 lecture notes if
     * you're confused). It is not tested by BOSS, but you will receive extra
     * credit if it is implemented in a sensible fashion.
     */
    public static void main(String args[]){
        // You can use this method for testing.
        Project1 project = new Project1();
        project.results("test.data");
        System.out.println("Number of Circles = " + project.circleCounter);
        System.out.println("Maximum Area = " + project.Smax);
        System.out.println("Minimum Area = " + project.Smin);
        System.out.println("Average Area= " + project.areaAverage);
        System.out.println("SD of Areas = " + project.areaSD);
        System.out.println("Median of Areas = " + project.areaMedian);
        System.out.println("(x1, y1) = (" + project.aabb[0] + ", " + project.aabb[1] + ") and (x2, y2) = (" + project.aabb[2] + ", " + project.aabb[3] + "). If these are both (0.0,0.0) it is because there are less than 20 non-singular circles");

    }
}
