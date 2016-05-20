package com.darkcode.emenu.Facturar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.darkcode.emenu.MainActivity;
import com.darkcode.emenu.R;
import com.darkcode.emenu.Vendedor.RegAbono;
import com.darkcode.emenu.VistaPedidos;
import com.darkcode.emenu.VistaProductos;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 7/1/16.
 */
public class AdapterPendientes extends ArrayAdapter<DetallePedido> {
    private List<DetallePedido> pedidos;
    private int id_pedido;
    private int saldo;
    private int id_client;
    private Context contexto;

    public AdapterPendientes(Context context, List<DetallePedido> pedids, String id_pedido, int capital, int id_cliente) {
        super(context, R.layout.list_pendientes, pedids);
        this.id_pedido = Integer.parseInt(id_pedido);
        pedidos = pedids;
        saldo = capital;
        id_client = id_cliente;
        contexto=context;

    }

    View proceso(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_pendientes, parent, false);
        final TextView num_pedido = (TextView) customView.findViewById(R.id.tvNoPedido);
        final TextView marca = (TextView) customView.findViewById(R.id.tvMarcaPedido);
        final TextView modelo = (TextView) customView.findViewById(R.id.tvModeloPedido);
        final TextView precio = (TextView) customView.findViewById(R.id.tvPrecioPedido);
        final TextView descripcion = (TextView) customView.findViewById(R.id.tvDescripcionPedido);
        final TextView total = (TextView) customView.findViewById(R.id.tvTotalPedido);
        final ImageView img_producto = (ImageView) customView.findViewById(R.id.imgProductoPedido);
        final Button ConfirmarP = (Button) customView.findViewById(R.id.btnConfirmarP);
        final Button EliminarP = (Button) customView.findViewById(R.id.btnEliminarP);

        final TextView txtpedido = (TextView) customView.findViewById(R.id.NoP);
        final TextView txtmarca = (TextView) customView.findViewById(R.id.tvMarca);
        final TextView tvNoPedido = (TextView) customView.findViewById(R.id.tvNoPedido);
        final TextView txtmodelo = (TextView) customView.findViewById(R.id.tvModelo);
        final TextView txtprecio = (TextView) customView.findViewById(R.id.tvPrecio);
        final TextView txtdescripcion = (TextView) customView.findViewById(R.id.tvDescripcion);
        final TextView txtcant_disponible = (TextView) customView.findViewById(R.id.tvCantidad);
        final TextView txttotal = (TextView) customView.findViewById(R.id.tvTotal);
        final int[] cant_disp = {1, 2, 3};
        final String[] cant_dispo = {"1", "2", "3"};

        final Spinner opciones = (Spinner) customView.findViewById(R.id.tvCantidadPedido);
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, cant_dispo);
        opciones.setAdapter(adapter);
        final int preci = pedidos.get(position).getPrecio();
        opciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                Toast.makeText(getContext(),opciones.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                String selec = opciones.getSelectedItem().toString();
                int cant_selec = Integer.valueOf(selec);
                int cal = cant_selec * preci;
                String cal_txt = String.valueOf(cal);
                total.setText(cal_txt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        FragmentActivity activity = (new FragmentActivity());
        final FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();


        ConfirmarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selec = opciones.getSelectedItem().toString();
                int cant_selec = Integer.valueOf(selec);
                int cal = cant_selec * preci;
                if (saldo >= cal) {
//                    Toast.makeText(getContext(),saldo, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

                    alertDialog.setTitle("Confirmar Pedido ");
                    alertDialog.setMessage("Desea confirmar el pago? ");
                    alertDialog.setNegativeButton(
                            "Si",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
                                    PedidoService servicio = restadpter.create(PedidoService.class);

                                    String selec = opciones.getSelectedItem().toString();
                                    final int cant_selec = Integer.valueOf(selec);

                                    String precio_f = precio.getText().toString();
                                    int precio_pro = Integer.valueOf(precio_f);

                                    servicio.postConfirmarPedido(id_client, pedidos.get(position).getId_pedido(), cant_selec, precio_pro, new Callback<DetallePedido>() {
                                        @Override
                                        public void success(DetallePedido detallePedido, Response response) {
                                            Toast.makeText(getContext(), "Confirmado" + pedidos.get(position).getId_pedido(), Toast.LENGTH_LONG).show();
                                            FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            VistaPedidos vista = new VistaPedidos();
                                            Bundle bundle = new Bundle();
                                            //PARAMS PARA ENVIAR A FRAGMENTS
                                            String idCliente = String.valueOf(id_client);
                                            String Saldo = String.valueOf(saldo);
                                            bundle.putString("id_cliente", idCliente);
//                                            bundle.putString("nombre", nombre);
//                                            bundle.putString("apellido", apellido);
//                                            bundle.putString("telefono", tel);
//                                            bundle.putString("correo", correo);
                                            bundle.putString("saldo", Saldo);
                                            vista.setArguments(bundle);
                                            fragmentTransaction.replace(R.id.f_main, vista);
                                            fragmentTransaction.commit();
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Toast.makeText(getContext(), error.getMessage() + id_pedido, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                    );
                    alertDialog.setPositiveButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }
                    );
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

                    alertDialog.setTitle("Alerta");
                    alertDialog.setMessage("Capital insuficiente");
                    alertDialog.show();
                }

            }
        });
        EliminarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
                PedidoService servicio = restadpter.create(PedidoService.class);

                servicio.postEliminarPedido(pedidos.get(position).getId_pedido(), new Callback<String>() {
                    @Override
                    public void success(String detallePedido, Response response) {
                        Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_LONG).show();
//                        Toast.makeText(getContext(), "Confirmado" + pedidos.get(position).getId_pedido(), Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        VistaPedidos vista = new VistaPedidos();
                        Bundle bundle = new Bundle();
                        //PARAMS PARA ENVIAR A FRAGMENTS
                        String idCliente = String.valueOf(id_client);
                        String Saldo = String.valueOf(saldo);
                        bundle.putString("id_cliente", idCliente);
                        bundle.putString("saldo", Saldo);
                        vista.setArguments(bundle);
                        fragmentTransaction.replace(R.id.f_main, vista);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getContext(), error.getMessage() + id_pedido, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

////        String photo = clientes.get(position).getPhoto();
        int idpedid = pedidos.get(position).getId_pedido();
        if (idpedid == 0) {
            marca.setVisibility(View.INVISIBLE);
            marca.destroyDrawingCache();
            modelo.setVisibility(View.INVISIBLE);
            precio.setVisibility(View.INVISIBLE);
            descripcion.setVisibility(View.INVISIBLE);
            opciones.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);
            img_producto.setVisibility(View.INVISIBLE);
            ConfirmarP.setVisibility(View.INVISIBLE);
            EliminarP.setVisibility(View.INVISIBLE);
            num_pedido.setVisibility(View.INVISIBLE);
            txtpedido.setText("No tiene Pedidos Pendientes");

            txtmarca.setVisibility(View.INVISIBLE);
            txtmodelo.setVisibility(View.INVISIBLE);
            txtprecio.setVisibility(View.INVISIBLE);
            txtdescripcion.setVisibility(View.INVISIBLE);
            txtcant_disponible.setVisibility(View.INVISIBLE);
            txttotal.setVisibility(View.INVISIBLE);
        } else {
            String idpedido = Integer.toString(idpedid);
            num_pedido.setText(idpedido);
            marca.setText(pedidos.get(position).getMarca());
            modelo.setText(pedidos.get(position).getModelo());
            descripcion.setText(pedidos.get(position).getNota());


            String canti = Integer.toString(preci);
//            opciones.setText(canti);

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


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        return proceso(position, convertView, parent);

    }
}
