/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampling;

import proyectoraytracing.main.Driver;
import utility.Point2D;

/**
 *
 * @author MAURICIO
 */
public class RegularSample extends Sampler{
    public RegularSample(int samples){
        this.samples = samples;
    }

    @Override
    public Point2D sample(int row, int col, int x, int y) {
        Point2D point = new Point2D();
        
        point.x = x-Driver.world.viewplane.width/2+(col+0.5)/samples;
        point.y = y-Driver.world.viewplane.height/2+(row+0.5)/samples;
    
        return point;
    }
}
