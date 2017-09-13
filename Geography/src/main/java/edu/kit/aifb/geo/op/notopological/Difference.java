/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kit.aifb.geo.op.notopological;

import com.vividsolutions.jts.geom.Geometry;
import edu.kit.aifb.geo.op.IOperationsGeometry;

/**
 *
 * @author paul
 */
public class Difference implements IOperationsGeometry{

    @Override
    public Geometry calculate(Geometry a, Geometry b) {
        return a.difference(b);
    }
    
}
