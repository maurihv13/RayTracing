/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoraytracing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author MAURICIO
 */
public class ProyectoRayTracing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long start = System.nanoTime();
        
        int height, width;
        height = 900;
        width = 1600;
        
        BufferedImage buffer;
        File image;
        
        String filename = "Image.png";
        image = new File(filename);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
        
        for(int y=0;y < height ; y++){
            for(int x=0; x < width ; x++){
                
            }
        }
        
        //Escribe la imagen
        try{
            ImageIO.write(buffer, "PNG", image);
        }catch(IOException e){
            System.out.println("Could not write image");
            System.exit(1);
        }
        
        
        long end = System.nanoTime();
        System.out.println("Loop Time: "+ (end-start)/1000000000.0F);
    }
    
}
