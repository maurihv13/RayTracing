/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projection;

import utility.Point2D;
import utility.Ray;

/**
 *
 * @author MAURICIO
 */
public abstract class Projection {
    public abstract Ray createRay(Point2D point);
}
