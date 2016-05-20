package com.darkcode.emenu.Producto;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by NativoLink on 23/1/16.
 */
public interface ProductoService {
    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Productos/list_productos.php")
    void getProducto(Callback<List<Producto>> callback);
//    void getFeed(@Path("user") String user,Callback<Cliente> response);

    // ======== LISTADO PARA EL VENDEDOR  =========
    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Productos/list_productos_v.php")
    void getProductoV(Callback<List<Producto>> callback);

    @FormUrlEncoded
    @POST("/WebSites/Meexin/Productos/Registrar.php")
    public void postRegProducto(@Field("marca") String marc,
                              @Field("modelo") String model,
                              @Field("descripcion") String descripcion,
                              @Field("precio") int precio,
                              @Field("cant_disponible") int cant_disponible,
                              @Field("src_photo") String src_photo,
                              Callback<String> callback);

    @FormUrlEncoded
    @POST("/WebSites/Meexin/Productos/Editar.php")
    public void postEditProducto(@Field("id_producto") int id_producto,
                                @Field("marca") String marc,
                                @Field("modelo") String model,
                                @Field("descripcion") String descripcion,
                                @Field("precio") int precio,
                                @Field("cant_disponible") int cant_disponible,
                                @Field("src_photo") String src_photo,
                                Callback<String> callback);

    @FormUrlEncoded
    @POST("/WebSites/Meexin/Productos/RegistrarPedido.php")
    public void postRegPedido(@Field("id_cliente") int id_cliente,
                              @Field("id_producto") int id_producto,
                              @Field("cantidad") int cantidad,
                              Callback<Producto> callback);

    @FormUrlEncoded
    @POST("/WebSites/Meexin/Productos/DesactivarProducto.php")
    public void postDesactivar(@Field("id_producto") int id_producto,
                              Callback<String> callback);
}
