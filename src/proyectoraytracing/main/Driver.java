/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoraytracing.main;

import geometry.Sphere;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
import projection.Orthographic;
import projection.Perspective;
import projection.Projection;
import sampling.RegularSample;
import sampling.Sampler;
import scene.Tracer;
import scene.World;
import utility.Color;
import utility.Image;
import utility.Point3D;
import utility.Ray;
import utility.Vector3D;

/**
 *
 * @author MAURICIO
 */
public class Driver {
    public static World world;
    public static Image image;
    public static Tracer tracer;
    public static Sampler sampler;
    public static Projection projection;
    
    public static void main(String[] args){
        
        long start = System.nanoTime();
        
        world = new World(1600, 900, 1.0);
        image = new Image("Image.png");
        tracer = new Tracer();
        sampler = new RegularSample(4);
        projection = new Orthographic();
        projection = new Perspective(new Point3D(100.0,100.0,200.0), new Point3D(0.0,0.0,0.0), 150.0);
        for(int y=0;y < world.viewplane.height ; y++){
            for(int x=0; x < world.viewplane.width ; x++){
                tracer.trace(x, y);
            }
        }
        
        image.write("PNG");
        
        
        long end = System.nanoTime();
        System.out.println("Loop Time: "+ (end-start)/1000000000.0F);
    }
}
