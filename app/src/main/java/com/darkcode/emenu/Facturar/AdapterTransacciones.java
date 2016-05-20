package com.darkcode.emenu.Facturar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.darkcode.emenu.R;

import java.util.List;

/**
 * Created by NativoLink on 1/3/16.
 */
public class AdapterTransacciones extends ArrayAdapter<Transaccion> {

    private List<Transaccion> transaccions;

    public AdapterTransacciones(Context context, List<Transaccion> trans) {
        super(context, R.layout.list_transacciones, trans);
        transaccions = trans;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_transacciones, parent, false);

        TextView id_producto = (TextView) customView.findViewById(R.id.tvidProducto);
        TextView pedido = (TextView) customView.findViewById(R.id.idPedido);
        TextView vendedor = (TextView) customView.findViewById(R.id.idVendedorTrans);
        TextView producto = (TextView) customView.findViewById(R.id.tvProducto);
        TextView abonado = (TextView) customView.findViewById(R.id.tvAbono);
        TextView restante = (TextView) customView.findViewById(R.id.tvRestante);
        TextView total = (TextView) customView.findViewById(R.id.tvTotal);
        TextView fecha = (TextView) customView.findViewById(R.id.tvFecha);

        final TextView txtidproducto = (TextView) customView.findViewById(R.id.txtidProducto);
        final TextView txtpedido= (TextView) customView.findViewById(R.id.txtPedido);
        final TextView txtproducto= (TextView) customView.findViewById(R.id.txtProducto);
        final TextView txtabonado= (TextView) customView.findViewById(R.id.txtAbono);
//        final TextView txtvendedor= (TextView) customView.findViewById(R.id.txtVendedor);
        final TextView txtrestante = (TextView) customView.findViewById(R.id.txtRestante);
        final TextView txttotal = (TextView) customView.findViewById(R.id.txtTotal);
        final TextView txtfecha= (TextView) customView.findViewById(R.id.txtFecha);


//        String toV = Integer.toString(transaccions.get(position).getId_vendedor());
//        factura.setText(toV);

        int idpedid = transaccions.get(position).getId_pedido();
        if (idpedid == 0) {
//            factura.setVisibility(View.INVISIBLE);
            pedido.destroyDrawingCache();
            vendedor.setVisibility(View.INVISIBLE);
            pedido.setVisibility(View.INVISIBLE);
            abonado.setVisibility(View.INVISIBLE);
            id_producto.setVisibility(View.INVISIBLE);
            producto.setVisibility(View.INVISIBLE);
            restante.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);
            fecha.setVisibility(View.INVISIBLE);
            txtproducto.setVisibility(View.INVISIBLE);
            txtabonado.setVisibility(View.INVISIBLE);
//            txtvendedor.setVisibility(View.INVISIBLE);
            txtrestante.setVisibility(View.INVISIBLE);
            txttotal.setVisibility(View.INVISIBLE);
            txtidproducto.setVisibility(View.INVISIBLE);
            txtfecha.setVisibility(View.INVISIBLE);
            txtpedido.setText("No tiene Transacciones realizadas");

        }else {
//            String toF = Integer.toString(transaccions.get(position).getId_factura());
//            factura.setText(toF);

            producto.setText(transaccions.get(position).getProducto());

            String toiP = Integer.toString(transaccions.get(position).getId_producto());
            id_producto.setText(toiP);

            String toP = Integer.toString(transaccions.get(position).getId_pedido());
            pedido.setText(toP);

            String toA = Integer.toString(transaccions.get(position).getAbono());
            abonado.setText(toA);

            String toR = Integer.toString(transaccions.get(position).getRestante());
            restante.setText(toR);

            String toT = Integer.toString(transaccions.get(position).getTotal());
            total.setText(toT);

            fecha.setText(transaccions.get(position).getFecha_reg());
        }
        return customView;

    }
}
