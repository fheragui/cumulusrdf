package edu.kit.aifb.geo.op.notopological;

import com.vividsolutions.jts.geom.Geometry;
import edu.kit.aifb.geo.op.IOperationsGeometry;

/**
 *
 * @author paul
 */
public class Union implements IOperationsGeometry {

    @Override
    public Geometry calculate(Geometry a, Geometry b) {
        return a.union(b);
    }

}
