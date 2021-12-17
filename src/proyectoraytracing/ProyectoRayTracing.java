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
import utility.Color;
import utility.Vector3D;

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
        height = 480;
        width = 640;
        
        BufferedImage buffer;
        File image;
        Color color = new Color(0.0F, 0.0F, 0.0F,0.0F); //color tiene un 
                                                      //atributo special  
        String filename = "Image.png";
        image = new File(filename);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
        Vector3D lookAt=new Vector3D(0,0,0);
        Vector3D diff_btw=new Vector3D();
        
        for(int y=0;y < height ; y++){
            for(int x=0; x < width ; x++){
                if((x > 200 && x < 440) && (y > 200 && y < 280)){
                    color = new Color(23, 222, 10,00);
                }else{
                    color = new Color(0, 0, 0,0);
                }
                buffer.setRGB(x, y, color.toInteger());
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
