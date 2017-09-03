package edu.kit.aifb.geo.builder.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.SortCondition;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.*;

/**
 *
 * @author paul
 */
public class FragmentQuery {

    private final Query q;
    private final ElementGroup conditions = new ElementGroup();
    private final ElementGroup geoConditions = new ElementGroup();
    private List<Var> vars = new ArrayList<>();
    private final List<Var> geoVars = new ArrayList<>();

    public FragmentQuery(String query) {
        q = QueryFactory.create(query);
    }

    public int getQueryType() {
        return q.getQueryType();
    }

    public ElementGroup getQueryConditions() {
        ElementGroup e = (ElementGroup) q.getQueryPattern();
        getVars();
        ArrayList<Element> elementsConditions = (ArrayList<Element>) e.getElements();
        elementsConditions.forEach((element) -> {
            if (getTypeFunction(element.toString()) != null) {
                if (element instanceof ElementBind) {
                    ElementBind eb = (ElementBind) element;
                    vars.remove(eb.getVar());
                    geoVars.add(eb.getVar());
                }
                geoConditions.addElement(element);
            } else {
                conditions.addElement(element);
            }
        });
        return conditions;
    }

    public List<Var> getVars() {
        vars = q.getProjectVars();
        return vars;
    }

    public long getLimit() {
        return q.getLimit();
    }

    public boolean isOrderBy() {
        return q.getOrderBy() == null;
    }

    public List<SortCondition> getOrderBy() {
        return q.getOrderBy();
    }

    public List<Var> getGeoVars() {
        return geoVars;
    }

    public ElementGroup getGeoQueryConditions() {
        return geoConditions;
    }

    /**
     *
     * @param s String
     * @return Function that contains the String
     */
    public String getTypeFunction(String s) {
        for (String f : getFunctions()) {
            if (s.contains(f)) {
                return f;
            }
        }
        return null;
    }

    /**
     *
     * @return list of functions
     */
    private String[] getFunctions() {
        String[] geof = {"sfEquals", "sfDisjoint", "sfIntersects",
            "sfTouches", "sfWithin", "sfContains", "sfOverlaps", "sfCrosses", "sfDistance"};
        return geof;
    }
}
