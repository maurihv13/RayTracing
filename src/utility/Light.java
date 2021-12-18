/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author Fred
 */
public class Light extends Source{
    public Vector3D position;
    public Color    color;
    
    public Light() {
        position=new Vector3D(0, 0, 0);
        color = new Color(1, 1, 1, 0);
    }
    
    public Light(Vector3D position,Color color){
        this.position=position;
        this.color=color;
    }

    public Vector3D getPosition() {
        return position;
    }

    @Override
    public Color getColor() {
        return color;
    }
    
    @Override
    public Color getLightColor(){
        return color;
    }
    
}
