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
public abstract class Object { //es una superClase de Sphere (falta transformar
    public Color color;
    /*public Color getColor(){
        return new Color(0.0F, 0.0F, 0.0F, 0);
    }*/
    
    public abstract double findIntersection(Ray ray);
    
    
}
