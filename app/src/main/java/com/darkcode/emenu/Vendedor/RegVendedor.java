package com.darkcode.emenu.Vendedor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darkcode.emenu.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 5/4/16.
 */
public class RegVendedor  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_vendedor);

        getSupportActionBar().setTitle("Meexin - Registrar Vendedor");

        final EditText nombre   = (EditText)findViewById(R.id.etNombreV);
        final EditText apellido = (EditText)findViewById(R.id.etApellidoV);
        final EditText cedula   = (EditText)findViewById(R.id.etCedulaV);
        final EditText clave    = (EditText)findViewById(R.id.etClaveV);
        final EditText correo   = (EditText)findViewById(R.id.etCorreoV);
        final EditText telefono = (EditText)findViewById(R.id.etTelefonoV);

        Button btnRegV = (Button)findViewById(R.id.btnRegV);
        btnRegV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nombre.getText().toString();
                String ape = apellido.getText().toString();
                String ced = cedula.getText().toString();
                String tel = telefono.getText().toString();
                String cor = correo.getText().toString();
                String cla = clave.getText().toString();

                if(!nom.equals("") && !ape.equals("") && !cla.equals("") && !cor.equals("") && !tel.equals("")  && !ced.equals("")){
                    Toast.makeText(RegVendedor.this, "Llenos", Toast.LENGTH_LONG).show();
                    RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
                    VendedorService servicio = restadpter.create(VendedorService.class);

                    long ce = Long.valueOf(ced);
                    long te = Long.valueOf(tel);
                    servicio.RegVendedor(nom, ape, ce, te, cor, cla, new Callback<String>() {
                        @Override
                        public void success(String s, Response response) {
                            Toast.makeText(RegVendedor.this, s, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(RegVendedor.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(RegVendedor.this, "Vacios", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
