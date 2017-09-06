package edu.kit.aifb.geo.op.notopological;

import com.vividsolutions.jts.geom.Geometry;
import edu.kit.aifb.geo.op.IOperations;

/**
 *
 * @author paul
 */
public class Boundary implements IOperations{
    /**
     * This is the opposite of disjoint
     * @param a
     * @param b
     * @return 
     */
    @Override
    public boolean calculate(Geometry a, Geometry b) {
        return !a.disjoint(b);
    }
    
}
