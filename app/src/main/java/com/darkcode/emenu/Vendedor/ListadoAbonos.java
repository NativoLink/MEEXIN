package com.darkcode.emenu.Vendedor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.darkcode.emenu.Cliente.ClienteService;
import com.darkcode.emenu.Facturar.Abono;
import com.darkcode.emenu.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 29/2/16.
 */
public class ListadoAbonos extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list_trans);

        proceso();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        proceso();
    }

    private void proceso(){
        getSupportActionBar().setTitle("Meexin - Abonos");

        final ListView lvresult2;
        lvresult2 = (ListView)findViewById(R.id.lvTrans);
        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
        ClienteService servicio = restadpter.create(ClienteService.class);

        servicio.getAbonosV(new Callback<List<Abono>>() {
            @Override
            public void success(List<Abono> abonos, Response response) {
                ListAdapter listAdapter = new AdapterAbonosV(getApplicationContext(), abonos);
                lvresult2.setAdapter(listAdapter);
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
