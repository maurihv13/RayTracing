/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import proyectoraytracing.main.Driver;
import utility.Color;
import utility.Point2D;
import utility.Ray;

/**
 *
 * @author MAURICIO
 */
public class Tracer {
    public void trace(int x, int y){
        Color color = new Color(0.0F, 0.0F, 0.0F);
        boolean hit = false;
        
        for(int row = 0; row < Driver.sampler.samples; row++){
            for(int col = 0; col < Driver.sampler.samples; col++){
                Point2D point = Driver.sampler.sample(row, col, x, y);
                Ray ray = Driver.projection.createRay(point);
            
                double min = Double.MAX_VALUE;
                Color tempColor = Driver.world.background;
                
                for(int i = 0; i < Driver.world.objects.size(); i++){
                    double temp = Driver.world.objects.get(i).hit(ray);
                    
                    if(temp != 0.0 && temp < min){
                        tempColor = Driver.world.objects.get(i).color;
                        min = temp; //?
                        hit = true;
                    }
                }
                color.add(tempColor);
            }
        }
        
        color.divide(Driver.sampler.samples*Driver.sampler.samples);
        
        Driver.image.buffer.setRGB(x, Driver.world.viewplane.height-y-1, color.toInteger());
        /*if(hit){
            Driver.image.buffer.setRGB(x, Driver.world.viewplane.height-y-1, color.toInteger());
        }else{
            Driver.image.buffer.setRGB(x, Driver.world.viewplane.height-y-1, Driver.world.background.toInteger());
        }*/
        
        
        
        
    }
}
