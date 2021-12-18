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
        center= new Vector3D(0, 0, 0);
        radius=1.0;
        color=new Color(0.5F, 0.5F, 0.5F, 0);
    }

    public Sphere(Vector3D center, double radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public double findIntersection(Ray Ray){
        return 0.0;
    }
    
    public Vector3D getCenter() {
        return center;
    }

    public Color getColor() {
        return color;
    }

    public double getRadius() {
        return radius;
    }
    
    
            
}
