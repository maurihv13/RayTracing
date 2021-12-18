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
                
                int index_of_winning_object = winningObjectIndex(intersections);
                
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
    public static int winningObjectIndex(ArrayList<Double> object_intersections){
        
        //return the index of the winnig intersection 40:00
        int index_of_minimum_value =0;
        
        //prevent unnessary calculations
        if(object_intersections.size()==0){
            //if there are no intersections
            return -1;
        }else{
            if(object_intersections.size()==1){
                if(object_intersections.get(0)>0){
                    //if that intersection is greater than zero then its our index of minimun value   
                    return 0;
                }else{
                    //otherwise the only intersection value is negative
                    return -1;
                }
            }else{
                //otherwise there is more than intersection
                //first find the maximum value
                
                double max =0;
                for (int algo = 0;algo<object_intersections.size();algo++){
                    if(max<object_intersections.get(algo)){
                        max =object_intersections.get(algo);
                    }else{}
                }
                //then starting from the maximum value find the minimum positive
                if (max >0){
                    //we only want positive intersections
                    for(int indice=0;indice<object_intersections.size();indice++){
                        if (object_intersections.get(indice) > 0 && object_intersections.get(indice)<=max){
                            max = object_intersections.get(indice);
                            index_of_minimum_value= indice;
                        }else{}
                    }
                    return index_of_minimum_value;
                }else{
                    //all the intersections were negative
                    return -1;
                }
                
            }
        }
        
    }
}
