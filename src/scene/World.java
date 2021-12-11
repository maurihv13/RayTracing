/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import geometry.GeometricObject;
import geometry.Sphere;
import java.util.ArrayList;
import utility.Color;
import utility.Point3D;

/**
 *
 * @author MAURICIO
 */
public class World {
    public ViewPlane viewplane;
    public ArrayList<GeometricObject> objects;
    public Color background;
    
    public World(int width, int height, double size){
        viewplane = new ViewPlane(width, height, size);
        background = new Color(0.0F, 0.0F, 0.0F);
    
        objects = new ArrayList<GeometricObject>();
        objects.add(new Sphere(new Point3D(0.0, 0.0, 0.0),50, new Color(1.0F, 0.0F, 0.0F)));
        
    }
    
}
