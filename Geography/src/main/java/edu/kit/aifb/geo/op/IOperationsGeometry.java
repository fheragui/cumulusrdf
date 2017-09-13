package edu.kit.aifb.geo.op;

import com.vividsolutions.jts.geom.Geometry;

/**
 *
 * @author paul
 */
public interface IOperationsGeometry {

    public Geometry calculate(Geometry a, Geometry b);
}
