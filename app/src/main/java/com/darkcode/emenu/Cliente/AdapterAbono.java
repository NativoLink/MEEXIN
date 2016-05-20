package com.darkcode.emenu.Cliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.darkcode.emenu.Facturar.Abono;
import com.darkcode.emenu.R;

import java.util.List;

/**
 * Created by NativoLink on 4/4/16.
 */
public class AdapterAbono extends ArrayAdapter<Abono> {

    private List<Abono> transaccions;
    private Context contexto;

    public AdapterAbono(Context context, List<Abono> trans) {
        super(context, R.layout.list_abonos, trans);
        transaccions = trans;
        contexto=context;
    }
    View proceso(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_abonos, parent, false);

        final TextView id_abono = (TextView) customView.findViewById(R.id.idAbono);
        final TextView id_vendedor = (TextView) customView.findViewById(R.id.idVendedor);
        final TextView vendedor = (TextView) customView.findViewById(R.id.nombreVendedor);
        final TextView saldo_ant = (TextView) customView.findViewById(R.id.saldoAnt);
        final TextView abono = (TextView) customView.findViewById(R.id.abono);
        final TextView saldo_act = (TextView) customView.findViewById(R.id.saldoAct);


        int idA = transaccions.get(position).getId_abono();
        String id_a = Integer.toString(idA);
        id_abono.setText(id_a);


        int idC = transaccions.get(position).getId_vendedor();
        String id_c = Integer.toString(idC);
        id_vendedor.setText(id_c);

        vendedor.setText(transaccions.get(position).getVendedor());

        int sAnt = transaccions.get(position).getSaldo_anterior();
        String s_ant = Integer.toString(sAnt);
        saldo_ant.setText(s_ant);

        int abono1 = transaccions.get(position).getMonto();
        String abo = Integer.toString(abono1);
        abono.setText(abo);

        int sAct = transaccions.get(position).getSaldo_nuevo();
        String s_act = Integer.toString(sAct);
        saldo_act.setText(s_act);

        return customView;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        return proceso(position, convertView, parent);

    }
}
