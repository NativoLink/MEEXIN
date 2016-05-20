package com.darkcode.emenu.Facturar;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.darkcode.emenu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by NativoLink on 20/2/16.
 */
public class AdapterRealizados extends ArrayAdapter<DetallePedido> {

    private List<DetallePedido> pedidos;

    public AdapterRealizados(Context context, List<DetallePedido> pedids) {
        super(context, R.layout.list_realizados, pedids);
        pedidos = pedids;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_realizados, parent, false);

//        String stringItem= getItem(position);
        final TextView num_pedido = (TextView) customView.findViewById(R.id.tvNoPedidoR);
        final TextView marca = (TextView) customView.findViewById(R.id.tvMarcaPedidoR);
        TextView modelo = (TextView) customView.findViewById(R.id.tvModeloPedidoR);
        TextView precio = (TextView) customView.findViewById(R.id.tvPrecioPedidoR);
        TextView descripcion = (TextView) customView.findViewById(R.id.tvDescripcionPedidoR);
        TextView cant_disponible = (TextView) customView.findViewById(R.id.tvCantidadPedidoR);
        TextView total = (TextView) customView.findViewById(R.id.tvTotalPedidoR);
        ImageView img_producto = (ImageView) customView.findViewById(R.id.imgProductoPedidoR);

        final TextView txtpedido = (TextView) customView.findViewById(R.id.NoP);
        final TextView txtmarca = (TextView) customView.findViewById(R.id.tvMarcaR);
        final TextView tvNoPedido = (TextView) customView.findViewById(R.id.tvNoPedidoR);
        final TextView txtmodelo = (TextView) customView.findViewById(R.id.tvModeloR);
        final TextView txtprecio = (TextView) customView.findViewById(R.id.tvPrecioR);
        final TextView txtdescripcion = (TextView) customView.findViewById(R.id.tvDescripcionR);
        final TextView txtcant_disponible = (TextView) customView.findViewById(R.id.tvCantidadR);
        final TextView txttotal = (TextView) customView.findViewById(R.id.tvTotalR);


////        String photo = clientes.get(position).getPhoto();
        int idpedid = pedidos.get(position).getId_pedido();
        if (idpedid == 0) {
            marca.setVisibility(View.INVISIBLE);
            marca.destroyDrawingCache();
            modelo.setVisibility(View.INVISIBLE);
            precio.setVisibility(View.INVISIBLE);
            descripcion.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);
            img_producto.setVisibility(View.INVISIBLE);
            num_pedido.setVisibility(View.INVISIBLE);
            cant_disponible.setVisibility(View.INVISIBLE);
            txtpedido.setText("No tiene pedidos Realizados");

            txtmarca.setVisibility(View.INVISIBLE);
            txtmodelo.setVisibility(View.INVISIBLE);
            txtprecio.setVisibility(View.INVISIBLE);
            txtdescripcion.setVisibility(View.INVISIBLE);
            txtcant_disponible.setVisibility(View.INVISIBLE);
            txttotal.setVisibility(View.INVISIBLE);
        }else {
            String idpedido = Integer.toString(idpedid);
            num_pedido.setText(idpedido);
            marca.setText(pedidos.get(position).getMarca());
            modelo.setText(pedidos.get(position).getModelo());
            descripcion.setText(pedidos.get(position).getNota());

            int cant = pedidos.get(position).getCantidad();
            String canti = Integer.toString(cant);
            cant_disponible.setText(canti);

            int to = pedidos.get(position).getTotal();
            String rTotal = Integer.toString(to);
            total.setText(rTotal);

            int precio1 = pedidos.get(position).getPrecio();
            String num = Integer.toString(precio1);
            precio.setText(num);

            Picasso.with(getContext()).load(pedidos.get(position).getFoto()).into(img_producto);
//        img_producto.setImageResource(R.drawable.pacman2);

        }
        return customView;

    }
}
