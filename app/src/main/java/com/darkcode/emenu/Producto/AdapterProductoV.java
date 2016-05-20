package com.darkcode.emenu.Producto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.darkcode.emenu.MainActivity;
import com.darkcode.emenu.R;
import com.darkcode.emenu.Vendedor.EditProducto;
import com.darkcode.emenu.Vendedor.RegAbono;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 27/2/16.
 */
public class AdapterProductoV extends ArrayAdapter<Producto> {
    private Context contexto;
    private List<Producto> productos;

    public AdapterProductoV(Context context, List<Producto> products) {
        super(context, R.layout.list_productos_v, products);
        productos = products;
        contexto = context;
    }

//    private void actBtnText(String ai){
//        if (ai == "a") {
//            activar.setText("desactivar");
//            Toast.makeText(getContext(), ai, Toast.LENGTH_LONG).show();
//        }
//        if (ai == "i") {
//            activar.setText("activar");
//        }
//
//    }

    private View proceso(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_productos_v, parent, false);

//        String stringItem= getItem(position);
        final TextView marca = (TextView) customView.findViewById(R.id.marca);
        final TextView modelo = (TextView) customView.findViewById(R.id.modelo);
        final TextView precio = (TextView) customView.findViewById(R.id.precio);
        final TextView descripcion = (TextView) customView.findViewById(R.id.descripcion);
        final TextView cant_disponible = (TextView) customView.findViewById(R.id.cant_disponible);
        ImageView img_producto = (ImageView) customView.findViewById(R.id.img_producto);
//        Toast.makeText(getContext(), productos.get(position).getEstado()+" <-ESTADO", Toast.LENGTH_LONG).show();

        final Button activar = (Button) customView.findViewById(R.id.activarP);

        String estado = productos.get(position).getEstado();
//


        if (estado.equals("a")) {
            activar.setText("Desactivar");
        }if(estado.equals("i")){
            activar.setText("Activar");
        }



        activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
                ProductoService servicio = restadpter.create(ProductoService.class);

                int id_producto = productos.get(position).getId_producto();
                servicio.postDesactivar(id_producto, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
//                        actBtnText(s);
                        if (s.equals("a")) {
                            activar.setText("Desactivar");
                        }if(s.equals("i")){
                            activar.setText("Activar");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        Button btnEditP = (Button)customView.findViewById(R.id.btnEditar);
        btnEditP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, EditProducto.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                String id_producto = String.valueOf(productos.get(position).getId_producto());
                intent.putExtra("id_producto", id_producto);

                String cant_dispo = String.valueOf(productos.get(position).getCant_disponible());
                intent.putExtra("cant_disponible",cant_dispo);

                String precio = String.valueOf(productos.get(position).getPrecio());
                intent.putExtra("precio",precio);

                intent.putExtra("marca",productos.get(position).getMarca());
                intent.putExtra("modelo",productos.get(position).getModelo());
                intent.putExtra("foto",productos.get(position).getFoto());
                intent.putExtra("estado",productos.get(position).getEstado());
                intent.putExtra("descripcion",productos.get(position).getDescripcion());
                contexto.startActivity(intent);
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

        Picasso.with(getContext()

    ).

    load(productos.get(position).getFoto()).into(img_producto);


        return customView;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        return proceso(position, convertView, parent);
    }



}
