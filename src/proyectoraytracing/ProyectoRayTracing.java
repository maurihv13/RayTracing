/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoraytracing;

import geometry.Plane;
import geometry.Sphere;
import geometry.Object;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import utility.Camera;
import utility.Color;
import utility.Light;
import utility.Ray;
import utility.Source;
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
        double ambientlight = 0.2;
        double accuracy = 0.000001;
        
        BufferedImage buffer;
        File image;
        Color color = new Color(0.0F, 0.0F, 0.0F, 0.0F); //color tiene un 
                                                      //atributo special 
        String filename = "Image.png";
        image = new File(filename);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Vector3D O=new Vector3D(0, 0, 0);
        Vector3D X=new Vector3D(1, 0, 0);
        Vector3D Y=new Vector3D(0, 1, 0);
        Vector3D Z=new Vector3D(0, 0, 1);
        Vector3D new_sphere_location = new Vector3D(1.75, 0, 0);
        
        Vector3D campos = new Vector3D(3, 1.5, -4); 
        
        Vector3D lookAt = new Vector3D(0,0,0);
        Vector3D diff_btw = new Vector3D(campos.x-lookAt.x,campos.y-lookAt.y,campos.z-lookAt.z);
        
        
        Vector3D camdir = diff_btw.negative().normalize();
        Vector3D camright = Y.cross(camdir).normalize();
        Vector3D camdown = camright.cross(camdir);
        
        Camera scene_cam = new Camera(campos, camdir, camright, camdown);
        
        Color white_light= new Color(1.0F, 1.0F, 1.0F, 0);
        Color pretty_green=new Color(0.5F, 1.0F, 0.5F, 0.3F);
        Color maroon = new Color(0.5F, 0.25F, 0.25F, 0);
        Color tile_floor = new Color(1,1,1,2);
        Color gray = new Color(0.5F, 0.5F, 0.5F, 0);
        Color black = new Color(0.0F,0.0F,0.0F,0);
        
        Vector3D light_pos = new Vector3D(-9, 10, -10);  
        Light scene_light = new Light(light_pos, white_light);
        ArrayList<Source> light_sources = new ArrayList<>(); //Guarda luces de escena
        light_sources.add(scene_light);
        
        //scene objects
        Sphere scene_sphere = new Sphere(O, 1, pretty_green);
        Sphere scene_sphere2 = new Sphere(new_sphere_location, 0.5, maroon);
        Plane scene_plane = new Plane(Y, -1,tile_floor);
        
        ArrayList<Object> scene_objects = new ArrayList<>();
        scene_objects.add(scene_sphere);
        scene_objects.add(scene_sphere2);
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
                    Object o = scene_objects.get(index);
                    intersections.add(o.findIntersection(cam_ray)); //Necesario importar clase para que funcione
                    
                }
                
                int index_of_winning_object = winningObjectIndex(intersections);
                
                if(index_of_winning_object == -1){
                    color = new Color(0, 0, 0, 0); //Dibuja background, Color Negro definido
                }else{
                    if(intersections.get(index_of_winning_object) > accuracy){
                        //Controla un cierto nivel de error con accuracy
                        Vector3D intersection_position = cam_ray_origin.add(cam_ray_direction.mult(intersections.get(index_of_winning_object)));//Metodos con diferentes nombres
                        Vector3D intersecting_ray_direction = cam_ray_direction;
                        
                        color = getColorAt(intersection_position, intersecting_ray_direction, scene_objects, index_of_winning_object, light_sources, accuracy, ambientlight);
                    }
                    
                }
                y = height - y; // Parche: Para corregir el sentido en imagen dibujada
                
                if(y >= 0 && y < height)buffer.setRGB(x, y, color.toInteger()); //Dibuja en buffer, pregunta si y no excede limite (por parche)
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
        
        /*
        delete pixels;
        t2 = clock();
        float diff ((float)t2-(float)t1)/1000;
        */
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
    
    public static Color getColorAt(Vector3D intersection_position, Vector3D intersecting_ray_direction, ArrayList<Object> scene_objects, int index_of_winning_object, ArrayList<Source> light_sources, double accuracy, double ambientlight){
        //Es la forma de implementar sombras y efectos de luz.
        Color winning_object_color = scene_objects.get(index_of_winning_object).getColor();
        Vector3D winning_object_normal = scene_objects.get(index_of_winning_object).getNormalAt(intersection_position);
        
        if(winning_object_color.getSpecial()==2){
            //checkored/title floor pattern
            int square = (int) Math.floor(intersection_position.x)+(int) Math.floor(intersection_position.z);
            if ((square % 2)==0){
                //cuadro negro
                winning_object_color.setColorRed(0);
                winning_object_color.setColorGreen(0);
                winning_object_color.setColorBlue(0);
            }else{
                //cuadro blanco
                winning_object_color.setColorRed(1);
                winning_object_color.setColorGreen(1);
                winning_object_color.setColorBlue(1);
            }
        }else{}
        
        Color final_color = winning_object_color.colorScalar(ambientlight);
        
        if(winning_object_color.getSpecial()>0 && winning_object_color.getSpecial()<=1){
            //reflexion de objetos con intensidad specular
            double dot1=winning_object_normal.dot(intersecting_ray_direction.negative());
            
            Vector3D scalar1= winning_object_normal.mult(dot1);
            Vector3D add1 =scalar1.add(intersecting_ray_direction);
            Vector3D scalar2 = add1.mult(2);
            Vector3D add2 = intersecting_ray_direction.negative().add(scalar2);
            Vector3D reflection_direction = add2.normalize();
            
            Ray reflection_ray = new Ray(intersection_position, reflection_direction);
            //determina que rayo intersecta primero
            ArrayList<Double> reflection_intersections=new ArrayList<>();
            for(int reflection_index=0;reflection_index<scene_objects.size();reflection_index++){
                reflection_intersections.add(scene_objects.get(reflection_index).findIntersection(reflection_ray));
            }
            
            int index_of_winning_object_with_reflection = winningObjectIndex(reflection_intersections);
            
            if(index_of_winning_object_with_reflection !=-1){
                //el rayo de reflexion se perdio
                if(reflection_intersections.get(index_of_winning_object_with_reflection)>accuracy){
                    //determina la posicion y direccion en el punto de interseccion
                    //el rayo solo afecta el color si este refleja algo
                    Vector3D reflection_intersection_position = intersection_position.add(reflection_direction.mult(reflection_intersections.get(index_of_winning_object_with_reflection)));
                    Vector3D reflection_intersection_ray_direction = reflection_direction;
                    
                    Color reflection_intersection_color = getColorAt(reflection_intersection_position, reflection_intersection_ray_direction, scene_objects, index_of_winning_object_with_reflection, light_sources, accuracy, ambientlight);
                    
                    final_color = final_color.colorAdd(reflection_intersection_color.colorScalar(winning_object_color.getSpecial()));
                }else{}
            }else{}
        }else{}
        
        for(int light_index = 0; light_index < light_sources.size(); light_index++){
            Vector3D light_direction = light_sources.get(light_index).getLigthPosition().add(intersection_position.negative()).normalize();
            
            float cosine_angle = (float)winning_object_normal.dot(light_direction);
            
            if(cosine_angle > 0){
                boolean shadowed = false;
                
                Vector3D distance_to_light = light_sources.get(light_index).getLigthPosition().add(intersection_position.negative()).normalize();
                float distance_to_light_magnitude = (float)distance_to_light.magnitude();
                
                Ray shadow_ray = new Ray(intersection_position, light_sources.get(light_index).getLigthPosition().add(intersection_position.negative()).normalize());
            
                ArrayList<Double> secondary_intersections = new ArrayList<>();
                
                for(int object_index = 0; object_index < scene_objects.size() && !shadowed ; object_index++){
                    secondary_intersections.add(scene_objects.get(object_index).findIntersection(shadow_ray));
                }
                
                for(int c = 0; c < secondary_intersections.size(); c++){
                    if(secondary_intersections.get(c) > accuracy){
                        if(secondary_intersections.get(c) <= distance_to_light_magnitude){
                            shadowed = true;
                        }
                    }
                    break;
                }
                if(!shadowed){
                    final_color = final_color.colorAdd(winning_object_color.colorMultiply(light_sources.get(light_index).getLightColor().colorScalar(cosine_angle)));
                    
                    if(winning_object_color.special > 0 && winning_object_color.special <= 1){
                        //special entre rango 0-1
                        double dot1 =  winning_object_normal.dot(intersecting_ray_direction.negative());
                        Vector3D scalar1 = winning_object_normal.mult(dot1);
                        Vector3D add1 = scalar1.add(intersecting_ray_direction);
                        Vector3D scalar2 = add1.mult(2);
                        Vector3D add2 = intersecting_ray_direction.negative().add(scalar2); 
                        Vector3D reflection_direction = add2.normalize();
                        
                        double specular = reflection_direction.dot(light_direction);
                        if(specular > 0){
                            specular = Math.pow(specular, 10);
                            final_color = final_color.colorAdd(light_sources.get(light_index).getLightColor().colorScalar(specular*winning_object_color.special));
                        }
                    }
                }
            }
        }
        return final_color.clip();
    }
                        
}
