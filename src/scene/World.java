/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import geometry.GeometricObject;
import geometry.Plane;
import geometry.Sphere;
import java.util.ArrayList;
import utility.Color;
import utility.Normal;
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
        objects.add(new Sphere(new Point3D(0.0, 0.0, 0.0),70, new Color(1.0F, 0.0F, 0.0F)));
        objects.add(new Sphere(new Point3D(150.0, 0.0, 0.0),70, new Color(0.0F, 1.0F, 0.0F)));
        objects.add(new Sphere(new Point3D(-150.0, 0.0, 0.0),70, new Color(0.0F, 0.0F, 1.0F)));
        objects.add(new Plane(new Point3D(0.0, 0.0, 0.0), new Normal(0.0, 1.0, 0.0), new Color(1.0F, 1.0F, 0.0F)));
    
    }
    
}
