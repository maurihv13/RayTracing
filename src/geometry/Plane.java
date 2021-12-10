/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import utility.Color;
import utility.Normal;
import utility.Point3D;
import utility.Ray;

/**
 *
 * @author MAURICIO
 */
public class Plane extends GeometricObject{
    public Point3D point;
    public Normal normal;
    
    public Plane(Point3D point, Normal normal, Color color){
        this.point = new Point3D(point);
        this.normal = new Normal(normal);
        this.color = new Color(color);
    }

    public double hit(Ray ray) {
        double t = point.sub(ray.origin).dot(normal)/ray.direction.dot(normal);
        
        if(t > 10E-9){
            return t;
        }else{
            return 0.0;
        }
    }
}
