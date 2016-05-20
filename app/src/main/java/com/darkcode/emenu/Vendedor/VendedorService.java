package com.darkcode.emenu.Vendedor;

import com.darkcode.emenu.Cliente.Cliente;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by NativoLink on 15/3/16.
 */
public interface VendedorService {

    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Vendedor/ListVendedor.php")
    void getVendedores(Callback<List<Cliente>> callback);
    //    void getFeed(@Path("user") String user,Callback<Cliente> response);

    @FormUrlEncoded
    @POST("/WebSites/Meexin/Vendedor/Registrar.php")
    public void RegVendedor(@Field("nombre") String nombre,
                                 @Field("apellido") String apellido,
                                 @Field("cedula") long cedula,
                                 @Field("telefono") long telefono,
                                 @Field("correo") String correo,
                                 @Field("clave") String clave,
                                 Callback<String> callback);

    @FormUrlEncoded
    @POST("/WebSites/Meexin/Cliente/RegAbono.php")
    public void postRegAbono(@Field("id_cliente") int id_cliente,@Field("id_vendedor") int id_vendedor,@Field("monto")int monto, Callback<String> callback);
}
