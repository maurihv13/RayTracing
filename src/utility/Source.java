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
public class Source {
    public Source(){
        
    }
    
    public Vector3D getLigthPosition(){
        return new Vector3D(0, 0, 0);
    }
    
    public Color getColor(){
        return new Color(1, 1, 1, 0);
    }
    
    public Color getLightColor(){
        return new Color(1, 1, 1, 0);
    }
}
