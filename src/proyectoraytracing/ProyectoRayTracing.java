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
        height = 640;
        width = 840;
        int aadepth = 4;
        double aspectratio = (double)width/ (double)height; 
        double ambientlight = 0.2;
        double accuracy = 0.000001;
        
        BufferedImage buffer;
        File image;
        Color color = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        String filename;
        if(aadepth == 1){
            filename = "Image_sin_antialising.png";
        }else{
            filename = "Image.png";
        }
        image = new File(filename); 
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        Vector3D O = new Vector3D(0, 0, 0);
        Vector3D Y = new Vector3D(0, 1, 0);
        
        Vector3D new_sphere_location = new Vector3D(1.5, -0.5, 0);
        Vector3D big_sphere_location = new Vector3D(12, 3, 15);
        
        Vector3D campos = new Vector3D(-3, 1.5, -4); 
        
        Vector3D lookAt = new Vector3D(0,0,0);
        Vector3D diff_btw = new Vector3D(campos.x-lookAt.x,campos.y-lookAt.y,campos.z-lookAt.z);
        
        
        Vector3D camdir = diff_btw.negative().normalize();
        Vector3D camright = Y.cross(camdir).normalize();
        Vector3D camdown = camright.cross(camdir);
        
        Camera scene_cam = new Camera(campos, camdir, camright, camdown);
        
        Color white_light = new Color(1.0F, 1.0F, 1.0F, 0);
        Color pretty_red = new Color(0.79607F, 0.0F, 0.0F, 0.3F);
        Color cyan = new Color(0.09411F, 0.54509F, 0.82745F, 0F);
        Color green = new Color(0.08627F, 0.82745F, 0.09804F, 0.15F);
        Color tile_floor = new Color(0.28627F, 0.12941F, 0.015686F,2F);
        Color gray = new Color(0.5F, 0.5F, 0.5F, 0);
        
        Vector3D light_pos = new Vector3D(-9, 10, -10);  
        Vector3D light_pos2 = new Vector3D(-4.5, 5, 10);
        Light scene_light = new Light(light_pos, white_light);
        Light scene_light2 = new Light(light_pos2, gray);
        ArrayList<Source> light_sources = new ArrayList<>(); //Guarda luces de escena
        light_sources.add(scene_light);
        light_sources.add(scene_light2);
        
        //Objetos de la escena
        Sphere scene_sphere = new Sphere(O, 1, pretty_red);
        Sphere scene_sphere2 = new Sphere(new_sphere_location, 0.5, cyan);
        Sphere big_sphere = new Sphere(big_sphere_location, 4, green);
        Plane scene_plane = new Plane(Y, -1,tile_floor);
        
        ArrayList<Object> scene_objects = new ArrayList<>();
        scene_objects.add(scene_sphere);
        scene_objects.add(scene_sphere2);
        scene_objects.add(big_sphere);
        scene_objects.add(scene_plane);
        
        int aa_index;
        double xamnt,yamnt;
        double[] tempRed, tempGreen, tempBlue;
        
        for(int x = 0; x < width ; x++){
            for(int y = 0;y < height ; y++){
                
                tempRed = new double[aadepth*aadepth];
                tempGreen = new double[aadepth*aadepth];
                tempBlue = new double[aadepth*aadepth];
                
                for(int aax = 0 ; aax < aadepth ; aax++){
                    for(int aay = 0 ; aay < aadepth ; aay++){
                        aa_index = aay*aadepth + aax;
                        if(true){
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
                        }

                        Vector3D cam_ray_origin = scene_cam.getCameraPos();
                        Vector3D cam_ray_direction = camdir.add(camright.mult(xamnt-0.5).add(camdown.mult(yamnt-0.5))).normalize();

                        Ray cam_ray = new Ray(cam_ray_origin,cam_ray_direction);

                        ArrayList<Double> intersections = new ArrayList();
                        for(int index = 0 ; index < scene_objects.size(); index++){
                            Object o = scene_objects.get(index);
                            intersections.add(o.findIntersection(cam_ray));
                        }

                        int index_of_winning_object = winningObjectIndex(intersections);

                        if(index_of_winning_object == -1){
                            //Dibuja background, Color Negro definido, por defecto
                            tempRed[aa_index] = 0;
                            tempGreen[aa_index] = 0;
                            tempBlue[aa_index] = 0;
                        }else{
                            if(intersections.get(index_of_winning_object) > accuracy){
                                //Controla un cierto nivel de error con accuracy
                                Vector3D intersection_position = cam_ray_origin.add(cam_ray_direction.mult(intersections.get(index_of_winning_object)));
                                Vector3D intersecting_ray_direction = cam_ray_direction;

                                color = getColorAt(intersection_position, intersecting_ray_direction, scene_objects, index_of_winning_object, light_sources, accuracy, ambientlight);
                                
                                tempRed[aa_index] = color.r;
                                tempGreen[aa_index] = color.g;
                                tempBlue[aa_index] = color.b;
                            }

                        }
                    }
                }
                
                //promediar el color para antialising
                float totalRed = 0;
                float totalGreen = 0;
                float totalBlue = 0;
                        
                for(int iRed = 0; iRed < aadepth * aadepth ;iRed++){
                    totalRed += tempRed[iRed]; 
                }
                
                for(int iGreen = 0; iGreen < aadepth * aadepth ;iGreen++){
                    totalGreen += tempGreen[iGreen]; 
                }
                
                for(int iBlue = 0; iBlue < aadepth * aadepth ;iBlue++){
                    totalBlue += tempBlue[iBlue]; 
                }
                        
                float avgRed = totalRed/(aadepth*aadepth);
                float avgGreen = totalGreen/(aadepth*aadepth);
                float avgBlue = totalBlue/(aadepth*aadepth);
                        
                color.r = avgRed; color.g = avgGreen; color.b = avgBlue;
                        
                int ny = height - y; // Parche: Para corregir el sentido en imagen dibujada

                if(ny >= 0 && ny < height)buffer.setRGB(x, ny, color.toInteger()); //Dibuja en buffer, pregunta si y no excede limite (por parche)
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
        int index_of_minimum_value = 0;
        if(object_intersections.isEmpty()){
            return -1;
        }else{
            if(object_intersections.size()==1){
                if(object_intersections.get(0) > 0){
                    return 0;
                }else{
                    return -1;
                }
            }else{
                double max =0;
                for (int algo = 0; algo<object_intersections.size() ; algo++){
                    if(max<object_intersections.get(algo)){
                        max = object_intersections.get(algo);
                    }
                }
                if (max >0){
                    for(int indice = 0; indice<object_intersections.size() ; indice++){
                        if (object_intersections.get(indice) > 0 && object_intersections.get(indice) <= max){
                            max = object_intersections.get(indice);
                            index_of_minimum_value= indice;
                        }
                    }
                    return index_of_minimum_value;
                }else{
                    return -1;
                }
            }
        }
    }
    
    public static Color getColorAt(Vector3D intersection_position, Vector3D intersecting_ray_direction, ArrayList<Object> scene_objects, int index_of_winning_object, ArrayList<Source> light_sources, double accuracy, double ambientlight){
        //Es la forma de implementar sombras y efectos de luz.
        Color winning_object_color = new Color(scene_objects.get(index_of_winning_object).getColor());
        Vector3D winning_object_normal = scene_objects.get(index_of_winning_object).getNormalAt(intersection_position);
        
        if(winning_object_color.getSpecial()==2){
            //checkored/title floor pattern
            int square = (int) Math.floor(intersection_position.x)+(int) Math.floor(intersection_position.z);
            if ((square % 2)==0){
                //conserva su color
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
            Vector3D light_direction = light_sources.get(light_index).getLightPosition().add(intersection_position.negative()).normalize();
            
            float cosine_angle = (float)winning_object_normal.dot(light_direction);
            
            if(cosine_angle > 0){
                boolean shadowed = false;
                
                Vector3D distance_to_light = light_sources.get(light_index).getLightPosition().add(intersection_position.negative()).normalize();
                float distance_to_light_magnitude = (float)distance_to_light.magnitude();
                
                Ray shadow_ray = new Ray(intersection_position, light_sources.get(light_index).getLightPosition().add(intersection_position.negative()).normalize());
            
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
                    //break; Necesario?
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
