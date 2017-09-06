package edu.kit.aifb.geo.builder.util;

import org.apache.jena.sparql.syntax.Element;

/**
 *
 * @author paul
 */
public class Components {

    private Element element;
    private String op;
    private String x;
    private String y;

    public Components(Element element, String op, String x, String y) {
        this.element = element;
        this.op = op;
        this.x = x;
        this.y = y;
    }
    
    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

   

}
