package com.darkcode.emenu.Facturar;

import com.darkcode.emenu.Cliente.Cliente;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by NativoLink on 17/2/16.
 */
public interface PedidoService {
    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Factura/ListPedidosPendientes.php")
    void getPedidosPendientes(@Query("id_cliente") int id_cliente,Callback<List<DetallePedido>> callback);

    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Factura/ListPedidosRealizados.php")
    void getPedidosRealizados(@Query("id_cliente") int id_cliente,Callback<List<DetallePedido>> callback);

    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Factura/ListPedidosAbonados.php")
    void getPedidosAbonados(@Query("id_cliente") int id_cliente,Callback<List<DetallePedido>> callback);

    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Factura/ListTransacciones.php")
    void getTransacciones(@Query("id_cliente") int id_cliente,Callback<List<Transaccion>> callback);

    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Factura/ListTransaccionesV.php")
    void getAllTransacciones(Callback<List<Transaccion>> callback);

    @Headers("Cache-Control: max-age=1")
    @FormUrlEncoded
    @POST("/WebSites/Meexin/Factura/EliminarPedido.php")
    public void postEliminarPedido(
                          @Field("id_pedido") int id_pedido,
                          Callback<String> callback);

    @Headers("Cache-Control: max-age=1")
    @FormUrlEncoded
    @POST("/WebSites/Meexin/Factura/ImprimirFactura.php")
    public void postImprimir(
            @Field("id_pedido") int id_pedido,
            @Field("id_vendedor") int id_vendedor,
            Callback<String> callback);

    @FormUrlEncoded
    @POST("/WebSites/Meexin/Factura/ConfirmarPedido.php")
    public void postConfirmarPedido(
            @Field("id_cliente") int id_cliente,
            @Field("id_pedido") int id_pedido,
            @Field("cantidad") int cantidad,
            @Field("precio") int precio,
            Callback<DetallePedido> callback);

}
