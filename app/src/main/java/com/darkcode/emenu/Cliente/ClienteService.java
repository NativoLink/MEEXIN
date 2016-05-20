package com.darkcode.emenu.Cliente;

import com.darkcode.emenu.Facturar.Abono;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by NativoLink on 15/12/15.
 */
public interface ClienteService {
    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Cliente/ListCliente.php")
    void getClientes(Callback<List<Cliente>> callback);
//    void getFeed(@Path("user") String user,Callback<Cliente> response);

    @FormUrlEncoded
    @POST("/WebSites/Meexin/Login/login.php")
    public void postLogin(@Field("username") String username, @Field("password") String password, Callback<Cliente> callback);

    @FormUrlEncoded
    @POST("/WebSites/Meexin/Cliente/GET_SALDO.php")
    public void postSaldo(
            @Field("id_cliente") int id_cliente, Callback<Cliente> callback);

    @Headers("Cache-Control: max-age=1")
    @FormUrlEncoded
    @POST("/WebSites/Meexin/Cliente/ActualizarCuenta.php")
    public void ActualizarCuenta(@Field("id_cliente") int id_cliente,
                                 @Field("clave_nueva") String clave_nueva,
                                 @Field("clave_vieja") String clave_vieja,Callback<String> callback);

    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Cliente/ListAbono.php")
    void getAbonos(@Query("id_cliente") int id_cliente,Callback<List<Abono>> callback);

    @Headers("Cache-Control: max-age=1")
    @GET("/WebSites/Meexin/Cliente/ListAbonoV.php")
    void getAbonosV(Callback<List<Abono>> callback);


//    @GET("/WebSites/Meexin/Login/login.php")
//    Cliente postLogin(@Query("username") String username, @Query("password") String password, Callback<Cliente> callback);
}
