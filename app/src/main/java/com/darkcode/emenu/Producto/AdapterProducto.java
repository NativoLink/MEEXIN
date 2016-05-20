package com.darkcode.emenu.Producto;

import android.content.Context;

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

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 7/1/16.
 */
public class AdapterProducto extends ArrayAdapter<Producto> {
    private int id_cliente;

    private List<Producto> productos;
    public AdapterProducto(Context context, List<Producto> products,String id_cliente) {
        super(context, R.layout.list_productos ,products);
        productos=products;
        this.id_cliente= Integer.parseInt(id_cliente);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView  = inflater.inflate(R.layout.list_productos, parent, false);

//        String stringItem= getItem(position);
        final TextView marca = (TextView) customView.findViewById(R.id.marca);
        final TextView modelo = (TextView) customView.findViewById(R.id.modelo);
        final TextView precio = (TextView) customView.findViewById(R.id.precio);
        final TextView descripcion = (TextView) customView.findViewById(R.id.descripcion);
        final TextView cant_disponible = (TextView) customView.findViewById(R.id.cant_disponible);
        ImageView img_producto = (ImageView) customView.findViewById(R.id.img_producto);
        Button agregar = (Button)customView.findViewById(R.id.add_pedido);


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
                ProductoService servicio = restadpter.create(ProductoService.class);

                servicio.postRegPedido(id_cliente, productos.get(position).getId_producto(), 1, new Callback<Producto>() {
                    @Override
                    public void success(Producto producto, Response response) {
                        Toast.makeText(getContext(),"Agregado a pedidos pendientes", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }
        });

//        String photo = clientes.get(position).getPhoto();
        marca.setText(productos.get(position).getMarca());
        modelo.setText(productos.get(position).getModelo());
        descripcion.setText(productos.get(position).getDescripcion());

        int cant = productos.get(position).getCant_disponible();
        String canti = Integer.toString(cant);
        cant_disponible.setText(canti);

        int precio1 = productos.get(position).getPrecio();
        String num = Integer.toString(precio1);
        precio.setText(num);

        Picasso.with(getContext()).load(productos.get(position).getFoto()).into(img_producto);

        return  customView;

    }
}
