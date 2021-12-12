/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author MAURICIO
 */
public class Ray {
    public Point3D origin;
    public Vector3D direction;
    
    public Ray(){
        origin = new Point3D();
        direction = new Vector3D();
    }
    
    public Ray(Point3D origin, Vector3D direction){
        this.origin = new Point3D(origin);
        this.direction = new Vector3D(direction);
    }
}
