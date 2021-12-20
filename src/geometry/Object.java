/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;
import utility.*;
/**
 * @author Fred
 */
public abstract class Object { //es una superClase de Sphere 
    public Color color;
    public Color getColor(){
        return this.color; //Devuelve color de objeto
    }
    
    public abstract double findIntersection(Ray ray);
    
    public Vector3D getNormalAt(Vector3D point){
        return new Vector3D(0, 0, 0);
    }
}
