package com.darkcode.emenu.Vendedor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darkcode.emenu.Producto.ProductoService;
import com.darkcode.emenu.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by NativoLink on 6/4/16.
 */
public class EditProducto extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_producto);
        getSupportActionBar().setTitle("Meexin - Editar Producto");

        final EditText marca = (EditText)findViewById(R.id.etMarca);
        final EditText modelo = (EditText)findViewById(R.id.etModelo);
        final EditText precio = (EditText)findViewById(R.id.etPrecio);
        final EditText cant_disp = (EditText)findViewById(R.id.etCantDisp);
        final EditText foto = (EditText)findViewById(R.id.etFoto);
        final EditText descrip = (EditText)findViewById(R.id.etDescripcion);



        final String c_id_producto = getIntent().getStringExtra("id_producto");
        final String marca_c = getIntent().getStringExtra("marca");
        final String modelo_c = getIntent().getStringExtra("modelo");
        final String foto_c = getIntent().getStringExtra("foto");
        final String descripcion_c = getIntent().getStringExtra("descripcion");
        final String cant_disponible_c = getIntent().getStringExtra("cant_disponible");
        final String precio_c = getIntent().getStringExtra("precio");

        marca.setText(marca_c);
        modelo.setText(modelo_c);
        precio.setText(precio_c);
        cant_disp.setText(cant_disponible_c);
        foto.setText(foto_c);
        descrip.setText(descripcion_c);




        Button btnRegP = (Button)findViewById(R.id.btnEditProducto);
        btnRegP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String s_marca = marca.getText().toString();
                final String s_modelo = modelo.getText().toString();
                final String s_foto = foto.getText().toString();
                final String s_descrip = descrip.getText().toString();

                String s_precio = precio.getText().toString();
                final int i_precio = Integer.valueOf(s_precio);
                String s_cant = cant_disp.getText().toString();
                final int i_cant = Integer.valueOf(s_cant);

                final int id_producto = Integer.valueOf(c_id_producto);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(descrip, InputMethodManager.SHOW_IMPLICIT);

                if(s_marca!="" && s_modelo!="" && s_foto!="" && s_descrip!="" && s_precio!="" && s_cant!="") {
                    RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
                    ProductoService servicio = restadpter.create(ProductoService.class);
                    Toast.makeText(getApplicationContext(), c_id_producto, Toast.LENGTH_LONG).show();
                    servicio.postEditProducto(id_producto, s_marca, s_modelo, s_descrip, i_precio, i_cant, s_foto, new Callback<String>() {
                        @Override
                        public void success(String respuesta, Response response) {
                            Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}
