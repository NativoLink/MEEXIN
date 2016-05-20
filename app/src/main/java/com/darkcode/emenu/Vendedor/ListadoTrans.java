package com.darkcode.emenu.Vendedor;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.darkcode.emenu.Facturar.AdapterTransaccionesV;
import com.darkcode.emenu.Facturar.PedidoService;
import com.darkcode.emenu.Facturar.Transaccion;
import com.darkcode.emenu.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 2/3/16.
 */
public class ListadoTrans extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list_trans);

        proceso();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        proceso();
    }

    private void proceso(){
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        final String cliente = bundle.getString("id_cliente");
//        final int id_cliente = Integer.parseInt(cliente);
        getSupportActionBar().setTitle("Meexin - Transacciones");
        final String id_vendedor = getIntent().getStringExtra("id_vendedor");
//        Toast.makeText(ListadoTrans.this, id_vendedor + ": ID VENDEDOR", Toast.LENGTH_LONG).show();
        final ListView lvresult;
        lvresult = (ListView)findViewById(R.id.lvTrans);
        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
        PedidoService servicio = restadpter.create(PedidoService.class);

        servicio.getAllTransacciones( new Callback<List<Transaccion>>() {
            @Override
            public void success(List<Transaccion> transaccions, Response response) {
                ListAdapter listAdapter = new AdapterTransaccionesV(getApplicationContext(), transaccions,id_vendedor);
                lvresult.setAdapter(listAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
//                resultado.setText(error.getMessage());
                Log.e("Error", error.getMessage());
                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        proceso();
    }

    @Override
    protected void onStart() {
        super.onStart();
        proceso();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        proceso();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        proceso();
    }
}
