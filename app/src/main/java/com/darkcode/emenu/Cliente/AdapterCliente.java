package com.darkcode.emenu.Cliente;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.darkcode.emenu.R;
import com.darkcode.emenu.Vendedor.RegAbono;

import java.util.List;

/**
 * Created by NativoLink on 7/1/16.
 */
public class AdapterCliente extends ArrayAdapter<Cliente>{
    private Context contexto;
    private List<Cliente> clientes;
    private String id_vendedor;
    public AdapterCliente(Context context, List<Cliente> clients,String id_vendedor) {
        super(context, R.layout.list_clientes, clients);
        clientes=clients;
        this.id_vendedor = id_vendedor;
        contexto=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView  = inflater.inflate(R.layout.list_clientes, parent, false);

//        String stringItem= getItem(position);
        final TextView name = (TextView) customView.findViewById(R.id.nombre);
        final TextView id_cliente = (TextView) customView.findViewById(R.id.idCliente);
        TextView telefono = (TextView) customView.findViewById(R.id.telefono);
        TextView capital = (TextView) customView.findViewById(R.id.capital);
        TextView email = (TextView) customView.findViewById(R.id.email);
        Button btnAbonar = (Button) customView.findViewById(R.id.btnAbonar);


        btnAbonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, RegAbono.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("id_cliente", id_cliente.getText().toString());
                intent.putExtra("id_vendedor",id_vendedor);
                intent.putExtra("nombre", name.getText().toString());
                contexto.startActivity(intent);
            }
        });

        name.setText(clientes.get(position).getNombre());
        email.setText(clientes.get(position).getCorreo());

        int saldo = clientes.get(position).getSaldo();
        String capi = Integer.toString(saldo);
        capital.setText(capi);

        int id = clientes.get(position).getId_cliente();
        String idc = Integer.toString(id);
        id_cliente.setText(idc);

        int te = clientes.get(position).getTelefono();
        String tel = Integer.toString(te);
        telefono.setText(tel);



        return  customView;

    }

}
