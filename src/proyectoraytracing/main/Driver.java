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
import utility.Color;
import utility.Point3D;
import utility.Ray;
import utility.Vector3D;

/**
 *
 * @author MAURICIO
 */
public class Driver {
    public static void main(String[] args){
        
        long start = System.nanoTime();
        
        Random random = new Random();
        
        int width = 1600;
        int height = 900;
        
        File image = new File("Image.png");
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        Sphere sphere = new Sphere(new Point3D(0,0,0), 60, new Color(1.0F, 0.0F, 0.0F));
        
        for(int y=0;y < height ; y++){
            for(int x=0; x < width ; x++){
                //buffer.setRGB(x, y, random.nextInt());
                Ray ray = new Ray(new Point3D(x-width/2+.5, y-height/2+.5, 100), new Vector3D(0.0, 0.0, -1.0));
            
                if(sphere.hit(ray) != 0.0){
                    buffer.setRGB(x, y, sphere.color.toInteger());
                }else{
                    buffer.setRGB(x, y, 0);
                }
            }
        }
        
        try{
            ImageIO.write(buffer, "PNG", image);
        }catch(Exception e){
            System.out.println("could not write image");
            System.exit(1);
        }
        
        
        long end = System.nanoTime();
        
        System.out.println("Loop Time: "+ (end-start)/1000000000.0F);
    }
}
