package com.darkcode.emenu.Vendedor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.darkcode.emenu.Cliente.Cliente;
import com.darkcode.emenu.R;

import java.util.List;

/**
 * Created by NativoLink on 6/4/16.
 */
public class AdapterVendedor extends ArrayAdapter<Cliente> {

    private Context contexto;
    private List<Cliente> vendedores;

    public AdapterVendedor(Context context, List<Cliente> vendedors) {
        super(context, R.layout.list_vendedores, vendedors);
        vendedores=vendedors;
        contexto=context;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView  = inflater.inflate(R.layout.list_vendedores, parent, false);

//        String stringItem= getItem(position);
        TextView id_vendedor = (TextView) customView.findViewById(R.id.idVendedorLV);
        final TextView name = (TextView) customView.findViewById(R.id.tvNombreLV);
        final TextView apellido = (TextView) customView.findViewById(R.id.tvApellidoLV);
        TextView cedula = (TextView) customView.findViewById(R.id.tvCedulaLV);
        TextView telefono = (TextView) customView.findViewById(R.id.tvTelefonoLV);
        TextView email = (TextView) customView.findViewById(R.id.tvCorreoLV);


        name.setText(vendedores.get(position).getNombre());
        apellido.setText(vendedores.get(position).getApellido());
        email.setText(vendedores.get(position).getCorreo());

        int saldo = vendedores.get(position).getCedula();
        String capi = Integer.toString(saldo);
        cedula.setText(capi);

        int id = vendedores.get(position).getId_cliente();
        String idc = Integer.toString(id);
        id_vendedor.setText(idc);

        int te = vendedores.get(position).getTelefono();
        String tel = Integer.toString(te);
        telefono.setText(tel);



        return  customView;

    }
}
