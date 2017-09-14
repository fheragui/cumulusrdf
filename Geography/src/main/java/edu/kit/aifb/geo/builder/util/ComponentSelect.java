package edu.kit.aifb.geo.builder.util;

/**
 *
 * @author paul
 */
public class ComponentSelect {
    private String funcion;
    private String operador1;
    private String operador2;
    private String nombreRes;

    public ComponentSelect(String funcion, String operador1, String operador2, String nombreRes) {
        this.funcion = funcion;
        this.operador1 = operador1;
        this.operador2 = operador2;
        this.nombreRes = nombreRes;
    }

    public String getNombreRes() {
        return nombreRes;
    }

    public void setNombreRes(String nombreRes) {
        this.nombreRes = nombreRes;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getOperador1() {
        return operador1;
    }

    public void setOperador1(String operador1) {
        this.operador1 = operador1;
    }

    public String getOperador2() {
        return operador2;
    }

    public void setOperador2(String operador2) {
        this.operador2 = operador2;
    }
    
}
