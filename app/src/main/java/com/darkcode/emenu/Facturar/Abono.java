package com.darkcode.emenu.Facturar;

/**
 * Created by NativoLink on 4/4/16.
 */
public class Abono {
    public int getId_abono() {
        return id_abono;
    }

    public void setId_abono(int id_abono) {
        this.id_abono = id_abono;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getSaldo_anterior() {
        return saldo_anterior;
    }

    public void setSaldo_anterior(int saldo_anterior) {
        this.saldo_anterior = saldo_anterior;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getSaldo_nuevo() {
        return saldo_nuevo;
    }

    public void setSaldo_nuevo(int saldo_nuevo) {
        this.saldo_nuevo = saldo_nuevo;
    }

    private int id_abono;
    private int id_cliente;
    private int saldo_anterior;
    private int monto;
    private int saldo_nuevo;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    private String fecha;
    private String cliente;

    public String getCliente() {
        return cliente;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    private String vendedor;
    private int id_vendedor;
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
