/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import proyectoraytracing.main.Driver;

/**
 *
 * @author MAURICIO
 */
public class Image {
    public BufferedImage buffer;
    public File image;
    
    public Image(String filename){
        image = new File(filename);
        buffer = new BufferedImage(Driver.world.viewplane.width, Driver.world.viewplane.height, BufferedImage.TYPE_INT_RGB);
    }
    
    public void write(String filetype){
        try{
            ImageIO.write(buffer, filetype, image);
        }catch(IOException e){
            System.out.println("Could not write image");
            System.exit(1);
        }
    }
}
