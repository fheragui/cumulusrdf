package edu.kit.aifb.geo.builder;

import com.vividsolutions.jts.io.ParseException;
import edu.kit.aifb.geo.op.IOperations;
import edu.kit.aifb.geo.op.Operations;
import edu.kit.aifb.geo.op.features.SfContains;
import edu.kit.aifb.geo.op.features.SfCrosses;
import edu.kit.aifb.geo.op.features.SfDisjoint;
import edu.kit.aifb.geo.op.features.SfEquals;

/**
 *
 * @author paul
 */
public class EjectCalculus {

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
                option = new SfEquals();
                break;
            default:
                break;
        }
        Operations operations = new Operations(option);
        return operations.calculate(a, b);
    }
}
