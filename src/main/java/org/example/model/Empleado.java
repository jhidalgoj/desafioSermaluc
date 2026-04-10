package org.example.model;

/**
 * Clase modelo que representa un empleado. Esta clase es un simple POJO (Plain Old Java Object)
 * que contiene los atributos de un empleado y sus respectivos getters y setters.
 * No contiene lógica de negocio ni validaciones, solo se encarga de almacenar los datos del empleado.
 */
public class Empleado {

    private int id;
    private String nombre;
    private String apellido;
    private String rut;
    private String cargo;
    private double salario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }


}
