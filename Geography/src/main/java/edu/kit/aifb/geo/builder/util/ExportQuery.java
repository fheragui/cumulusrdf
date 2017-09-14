package edu.kit.aifb.geo.builder.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.jena.query.Query;
import org.apache.jena.query.SortCondition;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;

/**
 *
 * @author paul
 */
public class ExportQuery {

    private final Query q;
    private final FragmentQuery fq;
    private final List<Components> geoComp;
    private final List<Var> geoVars;
    private final List<Var> newVars;
    private final List<Components> listComponents;
    private final List<ComponentSelect> componentes_select;

    public ExportQuery() {
        this.q = null;
        this.fq = null;
        this.geoComp = null;
        this.geoVars = null;
        this.newVars = null;
        this.listComponents = null;
        this.componentes_select = null;
    }

    public ExportQuery(String query) {
        this.q = new Query();
        this.fq = new FragmentQuery(query);
        this.geoComp = new ArrayList<>();
        this.geoVars = new ArrayList<>();
        this.newVars = new ArrayList<>();
        this.listComponents = new ArrayList<>();
        this.componentes_select = new ArrayList<>();
    }

    public Query buildSimpleQuery() {
        setQueryType();
        setQueryConditions();
        setQueryVars();
        if (!fq.isOrderBy()) {
            setQueryOrderBy();
        }
        //setQueryLimit();
        setQueryGeoVars();
        setGeoQueryVars();
        setSelectGeoVars();
        return q;
    }

    public List<Components> getGeoComp() {
        return geoComp;
    }

    public void setQueryVars() {
        for (Var v : fq.getVars()) {
            q.addResultVar(v);
        }
    }

    public List<String> getQueryVars() {
        List<String> s = null;
        for (Var v : fq.getVars()) {
            s.add(v.getVarName());
        }
        return s;
    }

    public void setGeoQueryVars() {
        for (Var v : newVars) {
            q.addResultVar(v);
        }
    }

    /**
     * Lista de componentes para para las operaciones
     *
     * @return
     */
    public List<Components> getListComponents() {
        return listComponents;
    }

    public void setQueryGeoVars() {
        for (Element element : fq.getGeoQueryConditions().getElements()) {
            List<Var> gvars = getGeoVars(element);
            addUniqueVar(gvars);
            listComponents.add(new Components(element, fq.getTypeFunction(element.toString()),
                    gvars.get(0).toString().replace("?", ""),
                    gvars.get(1).toString().replace("?", "")));
        }

    }

    public List<ComponentSelect> getComponentes_select() {
        return componentes_select;
    }

    public void setSelectGeoVars() {
        Map<Var, Expr> mp = fq.getSelectGeoVars();
        for (Map.Entry<Var, Expr> entry : mp.entrySet()) {
            List<String> gvars = getGeoVarsSelect(entry.getValue().toString());
            System.out.println("clave=1" + entry.getKey() + ", valor=" + entry.getValue().toString());
            componentes_select.add(new ComponentSelect(fq.getTypeFunction(entry.getValue().toString()),
                    gvars.get(0).replace("?", ""),
                    gvars.get(1).replace("?", ""), entry.getKey().getVarName()));
        }
    }

    private void addUniqueVar(List<Var> e) {
        for (Var var : e) {
            if (!newVars.contains(var)) {
                newVars.add(var);
            }
        }
    }

    public List<String> getGeoVarsSelect(String e) {
        Pattern p = Pattern.compile("(\\?[a-zA-Z0-9]+)");
        Matcher mat = p.matcher(e);
        List<String> gv = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            while (mat.find()) {
                gv.add(mat.group(i).replace("?", ""));
            }
        }
        return gv;
    }

    public List<Var> getGeoVars(Element e) {
        Pattern p = Pattern.compile("(\\?[a-zA-Z0-9]+)");
        Matcher mat = p.matcher(e.toString());
        List<Var> gv = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            while (mat.find()) {
                gv.add(Var.alloc(mat.group(i).replace("?", "")));
            }
        }
        return gv;
    }

    public List<Var> getGeoQueryVars() {
        return fq.getGeoVars();
    }

    public ElementGroup getGeoQueryConditions() {
        return fq.getGeoQueryConditions();
    }

    public void setQueryConditions() {
        ElementGroup pattern = fq.getQueryConditions();
        q.setQueryPattern(pattern);
    }

    public void setQueryLimit() {
        q.setLimit(fq.getLimit());
    }

    public long getLimit() {
        return fq.getLimit();
    }

    public void setQueryType() {
        switch (fq.getQueryType()) {
            case 111:
                q.setQuerySelectType();
                break;
            case 222:
                q.setQueryConstructType();
                break;
            case 333:
                q.setQueryDescribeType();
                break;
            case 444:
                q.setQueryAskType();
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return q.toString();
    }

    public void setQueryOrderBy() {
        for (SortCondition sc : fq.getOrderBy()) {
            q.addOrderBy(sc);
        }
    }

}
