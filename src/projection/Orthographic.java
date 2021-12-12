/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projection;

import proyectoraytracing.main.Driver;
import utility.Point2D;
import utility.Ray;

/**
 *
 * @author MAURICIO
 */
public class Orthographic extends Projection{
    public Ray createRay(Point2D point){
        Ray ray = new Ray();
        ray.origin.x = Driver.world.viewplane.size*point.x;
        ray.origin.y = Driver.world.viewplane.size*point.y;
        ray.origin.z = 100;
        
        ray.direction.x = 0.0;
        ray.direction.y = 0.0;
        ray.direction.z = -1.0;
        
        return ray;
    }
}
