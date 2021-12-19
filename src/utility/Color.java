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
public class Color {
    public float r, g, b, special;
    
    public Color(){
        r = 0.0F;
        g = 0.0F;
        b = 0.0F;
        special = 0.0F;
    }
    
    public Color(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
        special = 0.0F;
    }
    
    public Color(float r, float g, float b,float spec){
        this.r = r;
        this.g = g;
        this.b = b;
        special = spec;
    }
    
    public Color(Color color){
        r = color.r;
        g = color.g;
        b = color.b;
        special=color.special;
    }
    
    /*public void add(Color color){
        r += color.r;
        g += color.g;
        b += color.b;
        //special += color.special;
    }*/
    
    public void divide(int scalar){
        r /= scalar;
        g /= scalar;
        b /= scalar;
    }
    
    public int toInteger(){
        return (int)(r*255)<<16|(int)(g*255)<<8|(int)(b*255);
    }
    
    public void setColorRed(float redValue){
        r = redValue;
    }
    
    public void setColorGreen(float greenValue){
        g = greenValue;
    }
    public void setColorBlue(float blueValue){
        b = blueValue;
    }
    
    public void setColorSpecial(float specialValue){
        special = specialValue;
    }
    
    public double brightness(){
        return (r + b + g)/3;
    }
    
    public Color colorScalar(double scalar){
        return new Color((float)(r*scalar), (float)(g*scalar), (float)(b*scalar), special);
    }
    
    public Color colorAdd(Color color){
        return new Color(r+color.r, g+color.g, b+color.b, special); 
    }
    
    public Color colorMultiply(Color color){
        return new Color(r*color.r, g*color.g, b*color.b, special);
    }
    
    public Color colorAverage(Color color){
        return new Color((r+color.r)/2, (g+color.g)/2, (b+color.b)/2, special);
    }
    
    public Color clip(){
        float alllight = r+g+b;
        float excesslight = alllight -3;
        if (excesslight>0){
            r = r+excesslight*(r/alllight);
            g = g + excesslight*(g/alllight);
            b = b + excesslight*(b/alllight);
        }else
        
        if(r>1){r=1;}    
        if(g>1){g=1;}    
        if(b>1){b=1;}    
        if(r<0){r=0;}    
        if(g<0){g=0;}    
        if(b<0){b=0;}    
        return new Color(r, g, b, special);
    }
}
