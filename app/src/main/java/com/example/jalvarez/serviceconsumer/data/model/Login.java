package com.example.jalvarez.serviceconsumer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    private final String LOGIN_FUNCTION = "login";
    @SerializedName("funcion")
    @Expose
    private String funcion;
    @SerializedName("Fecha")
    @Expose
    private String fecha;
    @SerializedName("Usuario")
    @Expose
    private long usuario;
    @SerializedName("celular")
    @Expose
    private long celular;
    @SerializedName("ciudad")
    @Expose
    private String ciudad;
    @SerializedName("departamento")
    @Expose
    private String departamento;
    @SerializedName("finca")
    @Expose
    private String finca;

    public Login() {
        this.funcion = LOGIN_FUNCTION;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getUsuario() {
        return usuario;
    }

    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }

    public long getCelular() {
        return celular;
    }

    public void setCelular(long celular) {
        this.celular = celular;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getFinca() {
        return finca;
    }

    public void setFinca(String finca) {
        this.finca = finca;
    }

    @Override
    public String toString() {
        return "Login{" +
                "funcion='" + funcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", usuario=" + usuario +
                ", celular=" + celular +
                ", ciudad='" + ciudad + '\'' +
                ", departamento='" + departamento + '\'' +
                ", finca='" + finca + '\'' +
                '}';
    }
}