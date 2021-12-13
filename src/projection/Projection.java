/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projection;

import utility.Point2D;
import utility.Point3D;
import utility.Ray;
import utility.Vector3D;

/**
 *
 * @author MAURICIO
 */
public abstract class Projection {
    public Point3D eye;
    public Point3D lookat;
    public Double distancia;
    public Vector3D u,v,w; 
    public abstract Ray createRay(Point2D point);
    
    public void compute_uvw(){
        w = eye.sub_vec(lookat);
        w.normalize();
        
        Vector3D up = new Vector3D(0.00424,1.0,0.00764);
        u = up.cross(w); //este cross no aparece en el vector3D
        u.normalize();
        
        v = w.cross(u);
        v.normalize();
    }
}
