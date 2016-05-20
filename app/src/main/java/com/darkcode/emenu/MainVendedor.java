package com.darkcode.emenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.darkcode.emenu.Vendedor.ListadoAbonos;
import com.darkcode.emenu.Vendedor.ListadoClientes;
import com.darkcode.emenu.Vendedor.ListadoProductos;
import com.darkcode.emenu.Vendedor.ListadoTrans;
import com.darkcode.emenu.Vendedor.ListadoVendedores;
import com.darkcode.emenu.Vendedor.RegProducto;
import com.darkcode.emenu.Vendedor.RegVendedor;

/**
 * Created by NativoLink on 27/2/16.
 */
public class MainVendedor extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_vendedor);

        Button listP;
        Button listU;
        Button listT;
        Button listV;
        Button listA;
        Button regP;
        Button regV;

        final String id_vendedor = getIntent().getStringExtra("id_vendedor");
//        Toast.makeText(MainVendedor.this,id_vendedor+": ID VENDEDOR", Toast.LENGTH_LONG).show();

        listP = (Button)findViewById(R.id.ListaProductoV);
        listU = (Button)findViewById(R.id.ListaUsuariosV);
        listT = (Button)findViewById(R.id.TransaccionesV);
        listV = (Button)findViewById(R.id.ListaVendedores);
        listA = (Button)findViewById(R.id.ListadoAbonosV);
        regP = (Button)findViewById(R.id.RegProductoV);
        regV = (Button)findViewById(R.id.RegVendedor);
        if(!id_vendedor.equals("1")){
            regV.setVisibility(View.INVISIBLE);
        }

        listP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainVendedor.this, ListadoProductos.class);
                startActivity(intent);
            }
        });
        listU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainVendedor.this, ListadoClientes.class);
                intent.putExtra("id_vendedor",id_vendedor);
                startActivity(intent);
            }
        });
        listT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainVendedor.this, ListadoTrans.class);
                intent.putExtra("id_vendedor",id_vendedor);
                startActivity(intent);
            }
        });
        listA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainVendedor.this, ListadoAbonos.class);
                startActivity(intent);
            }
        });
        regP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainVendedor.this, RegProducto.class);
//                intent.putExtra("id_vendedor",id_vendedor);
                startActivity(intent);
            }
        });
        regV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainVendedor.this, RegVendedor.class);
//                intent.putExtra("id_vendedor",id_vendedor);
                startActivity(intent);
            }
        });
        listV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainVendedor.this, ListadoVendedores.class);
                startActivity(intent);
            }
        });




        
    }




}
