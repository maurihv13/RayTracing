/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projection;

import proyectoraytracing.main.Driver;
import utility.Point2D;
import utility.Point3D;
import utility.Ray;

/**
 *
 * @author Fred
 */
public class Perspective extends Projection{

    public Perspective(Point3D eye,Point3D lookat,double FOV) {
        this.eye=new Point3D(eye);
        this.lookat = new Point3D(lookat);
        this.distancia=Driver.world.viewplane.height/2/Math.tan(Math.toRadians(FOV));
        
    }
    @Override
    public Ray createRay(Point2D point){
        Ray ray=new Ray(new Point3D(eye), u.mult(point.x).add(v.mult(point.y).sub(w.mult(-distancia))));
        ray.direction.normalize();
        return ray;
    }
    
}
