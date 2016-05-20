package com.darkcode.emenu.Vendedor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.darkcode.emenu.Producto.AdapterProducto;
import com.darkcode.emenu.Producto.AdapterProductoV;
import com.darkcode.emenu.Producto.Producto;
import com.darkcode.emenu.Producto.ProductoService;
import com.darkcode.emenu.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 27/2/16.
 */
public class ListadoProductos extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list_producto_v);

        proceso();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        proceso();
    }

    private void proceso(){
        getSupportActionBar().setTitle("Meexin - Productos");
        final ListView lvresult2;
        lvresult2 = (ListView)findViewById(R.id.lvProductosV);
        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
        ProductoService servicio = restadpter.create(ProductoService.class);

        servicio.getProductoV(new Callback<List<Producto>>() {
            @Override
            public void success(List<Producto> productos, Response response) {
                ListAdapter listAdapter = new AdapterProductoV(getApplicationContext(), productos);
                lvresult2.setAdapter(listAdapter);
//                proceso();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        proceso();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        proceso();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        proceso();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        proceso();
    }
}
