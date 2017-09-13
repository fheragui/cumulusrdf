package edu.kit.aifb.geo.builder;

import com.vividsolutions.jts.io.ParseException;

import edu.kit.aifb.geo.op.IOperations;
import edu.kit.aifb.geo.op.Operations;
import edu.kit.aifb.geo.op.features.SfContains;
import edu.kit.aifb.geo.op.features.SfCrosses;
import edu.kit.aifb.geo.op.features.SfDisjoint;
import edu.kit.aifb.geo.op.features.SfEquals;
import edu.kit.aifb.geo.op.features.SfIntersects;
import edu.kit.aifb.geo.op.features.SfOverlaps;
import edu.kit.aifb.geo.op.features.SfTouches;
import edu.kit.aifb.geo.op.features.SfWithin;

import edu.kit.aifb.geo.op.IOperationsGeometry;
import edu.kit.aifb.geo.op.OperationsGeometry;

import edu.kit.aifb.geo.op.notopological.Difference;
import edu.kit.aifb.geo.op.notopological.Intersection;
import edu.kit.aifb.geo.op.notopological.Union;


/**
 *
 * @author paul
 */
public class EjectCalculus {

    /**
     * Devuelve true or false si dos geometrias cumplen con la operacion
     * seleccionada
     *
     * @param op
     * @param a
     * @param b
     * @return
     * @throws ParseException
     */
    public boolean topologicalRelations(String op, String a, String b) throws ParseException {
        IOperations option = null;
        switch (op) {
            case "sfContains":
                option = new SfContains();
                break;
            case "sfCrosses":
                option = new SfCrosses();
                break;
            case "sfDisjoint":
                option = new SfDisjoint();
                break;
            case "sfEquals":
                option = new SfEquals();
                break;
            case "sfIntersects":
                option = new SfIntersects();
                break;
            case "sfOverlaps":
                option = new SfOverlaps();
                break;
            case "fTouches":
                option = new SfTouches();
                break;
            case "sfWithin":
                option = new SfWithin();
                break;
            default:
                break;
        }
        Operations operations = new Operations(option);
        return operations.calculate(a, b);
    }

    /**
     * Devuelve como resultado una geometria
     *
     * @param op
     * @param a
     * @param b
     * @return
     * @throws ParseException
     */
    public String geometry(String op, String a, String b) throws ParseException {
        IOperationsGeometry option = null;
        switch (op) {
            case "difference":
                option = new Difference();
                break;
            case "intersection":
                option = new Intersection();
                break;
            case "union":
                option = new Union();
                break;
            default:
                break;
        }
        OperationsGeometry opg = new OperationsGeometry(option);
        return opg.calculate(a, b);
    }
}
