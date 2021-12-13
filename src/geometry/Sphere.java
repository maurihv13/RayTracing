/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import utility.Color;
import utility.Point3D;
import utility.Ray;

/**
 *
 * @author MAURICIO
 */
public class Sphere extends GeometricObject{
    public Point3D center;  //en ahi usan vector en vez de punto
    public double radius;
    
    public Sphere(Point3D center, double radius, Color color){
        this.center = new Point3D(center);
        this.radius = radius;
        this.color = color;
    }

    public double hit(Ray ray) {
        double a = ray.direction.dot(ray.direction);
        double b = 2*ray.origin.sub(center).dot(ray.direction);
        double c = ray.origin.sub(center).dot(ray.origin.sub(center))-radius*radius;
        double discriminant = b*b-4*a*c;
        
        if(discriminant<0.0){
            return 0.0;
        }else{
            double t = (-b - Math.sqrt(discriminant/(2*a)));
            
            if(t > 10E-9){
                return t;
            }else{
                return 0.0;
            }
        }
    }
    /*
    public Point3D getNormal(Point3D pi) {//implementare suma resta divicion en vector
        return new Point3D(center-pi/radius);//o seria en punto?
    }
    */
}
