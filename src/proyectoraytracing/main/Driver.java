/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoraytracing.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

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
        
        for(int y=0;y < height ; y++){
            for(int x=0; x < width ; x++){
                buffer.setRGB(x, y, random.nextInt());
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
