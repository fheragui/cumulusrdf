/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kit.aifb.geo.op.features;

import com.vividsolutions.jts.geom.Geometry;
import edu.kit.aifb.geo.op.IOperations;

/**
 *
 * @author paul
 */
public class SfCrosses implements IOperations {

    @Override
    public boolean calculate(Geometry a, Geometry b) {
        return a.crosses(b);
    }

}
