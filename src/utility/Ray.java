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
    public Vector3D origin;
    public Vector3D direction;
    
    public Ray(){
        origin = new Vector3D(0, 0, 0);
        direction = new Vector3D(1, 0, 0);
    }
    
    public Ray(Vector3D origin, Vector3D direction){
        this.origin = new Vector3D(origin);
        this.direction = new Vector3D(direction);
    }
}
