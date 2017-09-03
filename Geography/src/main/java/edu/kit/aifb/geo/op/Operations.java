package edu.kit.aifb.geo.op;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.geometry.jts.FactoryFinder;

/**
 *
 * @author paul
 */
public class Operations {

    private IOperations operation;

    public Operations(IOperations operation) {
        this.operation = operation;
    }

    public boolean calculate(String wkt1, String wkt2) throws ParseException {
        GeometryFactory geometryFactory = FactoryFinder.getGeometryFactory(null);
        WKTReader reader = new WKTReader(geometryFactory);
        Geometry a = reader.read(getWkt(wkt1).trim());
        Geometry b = reader.read(getWkt(wkt2).trim());
        return operation.calculate(a, b);
    }

    private String getWkt(String s) {
        String[] splitString = (s.split("<|>"));
        String v = null;
        for (String string : splitString) {
            if (!(string.isEmpty() || string.equalsIgnoreCase(" ") || string.contains("http"))) {
                v = string;
            }
        }
        return v;
    }

    public IOperations getOperation() {
        return operation;
    }

    public void setOperation(IOperations operation) {
        this.operation = operation;
    }

}
