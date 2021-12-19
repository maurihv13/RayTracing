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
public class Sphere extends Object{ //debe ser hijo de la clase Object
    public Vector3D center;
    public double radius;
    
    public Sphere() {
        center = new Vector3D(0, 0, 0);
        radius = 1.0;
        color = new Color(0.5F, 0.5F, 0.5F, 0);
    }

    public Sphere(Vector3D center, double radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public double findIntersection(Ray ray){
        Vector3D ray_origin = ray.getOrigin();
        double ray_origin_x = ray_origin.x;
        double ray_origin_y = ray_origin.y;
        double ray_origin_z = ray_origin.z;
        
        Vector3D ray_direction = ray.getDirection();
        double ray_direction_x = ray_direction.x;
        double ray_direction_y = ray_direction.y;
        double ray_direction_z = ray_direction.z;
        
        Vector3D sphere_center = center;
        
        double sphere_center_x = sphere_center.x;
        double sphere_center_y = sphere_center.y;
        double sphere_center_z = sphere_center.z;
        
        double a = 1; //normalized
        double b = (2*(ray_origin_x-sphere_center_x)*ray_direction_x)+(2*(ray_origin_y-sphere_center_y)*ray_direction_y)+(2*(ray_origin_z-sphere_center_z)*ray_direction_z);
        double c = Math.pow(ray_origin_x - sphere_center_x,2)+Math.pow(ray_origin_y - sphere_center_y,2)+Math.pow(ray_origin_z - sphere_center_z,2) - (radius*radius);
        
        double discriminant = b*b - 4*c;
        if (discriminant>0){
            //the ray intersect the sphere
            
            // the first root
            double root_1 = ((-1*b - Math.sqrt(discriminant))/2)-0.000001;
            
            if (root_1 > 0){
                //the first root is the smallest positive root
                return root_1;
            }else{
                //the second root is the smallest positive root
                double root_2 = ((Math.sqrt(discriminant)-b)/2)-0.000001;
                return root_2;
            }
        }else{
            //the ray missed the sphere
            return -1;
        }
    }
    
    @Override
    public Vector3D getNormalAt(Vector3D point){
        Vector3D normal_Vect = point.add(center.add(center.negative()).normalize());
        return normal_Vect;
    }
    
    public Vector3D getCenter() {
        return center;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public double getRadius() {
        return radius;
    }
    
    
            
}
