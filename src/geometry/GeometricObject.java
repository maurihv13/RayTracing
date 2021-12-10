/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import utility.Color;
import utility.Ray;

/**
 *
 * @author MAURICIO
 */
public abstract class GeometricObject {
    public Color color;
    public abstract double hit(Ray ray); //Retorna si se intersecta con el objeto
    
}
