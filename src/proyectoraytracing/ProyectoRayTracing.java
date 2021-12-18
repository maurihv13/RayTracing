/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoraytracing;

import geometry.Plane;
import geometry.Sphere;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import utility.Camera;
import utility.Color;
import utility.Light;
import utility.Ray;
import utility.Vector3D;

/**
 *
 * @author MAURICIO
 */
public class ProyectoRayTracing {
    //public int 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long start = System.nanoTime();
        
        int height, width;
        height = 480;
        width = 640;
        double aspectratio = (double)width/ (double)height; 
        BufferedImage buffer;
        File image;
        Color color = new Color(0.0F, 0.0F, 0.0F,0.0F); //color tiene un 
                                                      //atributo special 
        String filename = "Image.png";
        image = new File(filename);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Vector3D O=new Vector3D(0, 0, 0);
        Vector3D X=new Vector3D(1, 0, 0);
        Vector3D Y=new Vector3D(0, 1, 0);
        Vector3D Z=new Vector3D(0, 0, 1);
        
        Vector3D campos = new Vector3D(3, 1.5, -4);
        
        Vector3D lookAt=new Vector3D(0,0,0);
        Vector3D diff_btw=new Vector3D(campos.x-lookAt.x,campos.y-lookAt.y,campos.z-lookAt.z);
        
        //crea mas verctores no entiendo para que minuto 30 aprox
        Vector3D camdir=diff_btw.negative().normalize();
        Vector3D camright=Y.cross(camdir).normalize();
        Vector3D camdown = camright.cross(camdir);
        
        Camera scene_cam = new Camera(campos, camdir, camright, camdown);
        
        Color white_light= new Color(1.0F,1.0F,1.0F,0);
        Color pretty_green=new Color(0.5F,1.0F,0.5F,0.3F);
        Color maroon = new Color(0.5F, 0.25F, 0.25F, 0);
        Color gray = new Color(0.5F, 0.5F, 0.5F, 0);
        Color black = new Color(0.0F,0.0F,0.0F,0);
        
        Vector3D light_pos= new Vector3D(-7, 10, -10);
        Light scene_light=new Light(light_pos, white_light);
        
        //scene objects
        Sphere scene_sphere=new Sphere(O, 1, pretty_green);
        Plane scene_plane= new Plane(Y, -1, maroon);
        
        ArrayList<Object> scene_objects=new ArrayList<>();
        scene_objects.add(scene_sphere);
        scene_objects.add(scene_plane);
        
        
        double xamnt,yamnt;
        
        for(int y=0;y < height ; y++){
            for(int x=0; x < width ; x++){
                //start with no anti-aliasing
                if (width>height){
                    //the image is wider than it is tall
                    xamnt = ((x+0.5)/width)*aspectratio-(((width-height)/(double)height)/2);
                    yamnt = ((height-y)+0.5)/height;
                }else{
                    if (height>width){
                        //the image is taller than it is wide
                        xamnt = (x + 0.5)/width;
                        yamnt = (((height-y) + 0.5)/height)/aspectratio - (((height-width)/(double)width)/2);
                        
                    }else{
                        //the imagen is square
                        xamnt = (x + 0.5)/width;
                        yamnt = ((height-y)+0.5)/height;
                    }
                }
                
                Vector3D cam_ray_origin = scene_cam.getCameraPos();
                Vector3D cam_ray_direction= camdir.add(camright.mult(xamnt-0.5).add(camdown.mult(yamnt-0.5))).normalize();
                
                Ray cam_ray = new Ray(cam_ray_origin,cam_ray_direction);
                
                ArrayList<Double> intersections= new ArrayList();
                for(int index=0;index<scene_objects.size();index++){ //aplicar polimorfismo aqui
                    if(scene_objects.get(index) instanceof Plane){
                        Plane p=(Plane) scene_objects.get(index);
                        intersections.add(p.findIntersection(cam_ray));
                    }else{
                        if(scene_objects.get(index) instanceof Sphere){
                            Sphere s=(Sphere) scene_objects.get(index);
                            intersections.add(s.findIntersection(cam_ray));
                        }
                    }
                    
                }
                
                if((x > 200 && x < 440) && (y > 200 && y < 280)){
                    color = new Color(23, 222, 10,00);
                }else{
                    color = new Color(0, 0, 0,0);
                }
                buffer.setRGB(x, y, color.toInteger());
            }
        }
        
        //Escribe la imagen
        try{
            ImageIO.write(buffer, "PNG", image);
        }catch(IOException e){
            System.out.println("Could not write image");
            System.exit(1);
        }
        
        
        long end = System.nanoTime();
        System.out.println("Loop Time: "+ (end-start)/1000000000.0F);
    }
    
}
