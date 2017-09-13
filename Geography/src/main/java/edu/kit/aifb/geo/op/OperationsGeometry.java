package edu.kit.aifb.geo.op;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.geometry.jts.JTSFactoryFinder;

/**
 *
 * @author paul
 */
public class OperationsGeometry {

    private IOperationsGeometry operationsgeo;

    public OperationsGeometry(IOperationsGeometry operationsgeo) {
        this.operationsgeo = operationsgeo;
    }

    public String calculate(String wkt1, String wkt2) throws ParseException {
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
        WKTReader reader = new WKTReader(geometryFactory);
        Geometry a = reader.read(getWkt(wkt1));
        Geometry b = reader.read(getWkt(wkt2));
        return operationsgeo.calculate(a, b).toString();
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

    public IOperationsGeometry getOperationsgeo() {
        return operationsgeo;
    }

    public void setOperationsgeo(IOperationsGeometry operationsgeo) {
        this.operationsgeo = operationsgeo;
    }
}
