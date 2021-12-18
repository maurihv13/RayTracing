/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;
import utility.*;
/**
 *
 * @author Fred
 */
public class Plane extends Object{
    public Vector3D normal;
    public double distance;
    

    public Plane() {
        normal = new Vector3D(1, 0, 0);
        distance=0;
        color= new Color(0.5F, 0.5F, 0.5F, 0);
    }
    
    public Plane(Vector3D normal, double distance, Color color) {
        this.normal = normal;
        this.distance = distance;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public double getDistance() {
        return distance;
    }

    public Vector3D getNormal() {
        return normal;
    }
    
    public Vector3D getNormalAt(Vector3D point){
        return normal; //raro che
    }
    
    @Override
    public double findIntersection(Ray ray){
        Vector3D ray_direction = ray.getDirection();
        double a = ray_direction.dot(normal);
        if(a==0){
            //el rayo el paralelo al plano
            return -1;
        }else{              
            double b= normal.dot(ray.getOrigin().add(normal.mult(distance).negative()));
            return -1*b/a;
        }
    }
}
