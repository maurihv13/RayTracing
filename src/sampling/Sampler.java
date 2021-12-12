/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampling;

import utility.Point2D;

/**
 *
 * @author MAURICIO
 */
public abstract class Sampler {
    public int samples;
    
    public abstract Point2D sample(int row, int col, int x, int y);
    
}
