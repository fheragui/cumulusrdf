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
