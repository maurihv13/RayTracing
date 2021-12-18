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
public class Camera {
    public Vector3D campos, camdir, camrigth, camdown;
    
    public Camera(){
        campos = new Vector3D(0, 0, 0);
        camdir = new Vector3D(0, 0, 1);
        camrigth = new Vector3D(0, 0, 0);
        camdown = new Vector3D(0, 0, 0);
    }
    
    public Camera(Vector3D pos, Vector3D dir, Vector3D rigth, Vector3D down){
        campos = pos;
        camdir = dir;
        camrigth = rigth;
        camdown = down;
    }
    //metodos de la camara 
    public Vector3D getCameraPos(){return campos;}
    
    public Vector3D getCameraDirection(){return camdir;}
    
    public Vector3D getCameraRigth(){return camrigth;}
    
    public Vector3D getCameraDown(){return camdown;}
    
}
