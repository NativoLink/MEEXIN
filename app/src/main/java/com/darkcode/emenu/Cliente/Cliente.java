package com.darkcode.emenu.Cliente;

/**
 * Created by NativoLink on 15/12/15.
 */
public class Cliente {

    private int id_cliente;
    private String nombre;
    private String apellido;
    private String correo;
    private int cedula;
    private int saldo;
    private int telefono;
    private String clave;
    private String login;
    private int usertype;

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String isLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }



    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id_cliente +
                ", name='" + nombre + '\'' +
                ", username='" + apellido + '\'' +
                ", email='" + correo + '\'' +
                ", website='" + cedula + '\'' +
                ", photo='" + telefono + '\'' +
                ", password='" + clave + '\'' +
                '}';
    }

    public String toString2() {
        return "Cliente{" +
                " username='" + nombre + '\'' +
                ", password='" + clave + '\'' +
                '}';
    }



}
