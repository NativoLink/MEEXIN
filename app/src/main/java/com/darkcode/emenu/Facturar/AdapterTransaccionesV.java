package com.darkcode.emenu.Facturar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.darkcode.emenu.MainActivity;
import com.darkcode.emenu.R;
import com.darkcode.emenu.Vendedor.RegAbono;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 1/3/16.
 */
public class AdapterTransaccionesV extends ArrayAdapter<Transaccion> {

    private List<Transaccion> transaccions;
    private Context contexto;
    private int idVendedor;
    private String sidVendedor;

    public AdapterTransaccionesV(Context context, List<Transaccion> trans,String id_vendedor) {
        super(context, R.layout.list_transacciones_v, trans);
        transaccions = trans;
        idVendedor = Integer.valueOf(id_vendedor);
        sidVendedor=id_vendedor;
        contexto=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_transacciones_v, parent, false);

        TextView factura = (TextView) customView.findViewById(R.id.idFacturaTrans);
//        final TextView pedido = (TextView) customView.findViewById(R.id.idPedido);
        final TextView vendedor = (TextView) customView.findViewById(R.id.idVendedorTrans);
        TextView abonado = (TextView) customView.findViewById(R.id.tvAbono);
        TextView restante = (TextView) customView.findViewById(R.id.tvRestante);
        TextView total = (TextView) customView.findViewById(R.id.tvTotal);
        TextView fecha = (TextView) customView.findViewById(R.id.tvFecha);

        Button imprimir  = (Button)customView.findViewById(R.id.btnImprimir);
        imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, ImprimirFactura.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                String id_pedido = String.valueOf(transaccions.get(position).getId_pedido());
                intent.putExtra("id_pedido", id_pedido);

                String precio = String.valueOf(transaccions.get(position).getPrecio());
                intent.putExtra("precio", precio);

                String id_factura = String.valueOf(transaccions.get(position).getId_factura());
                intent.putExtra("id_factura",id_factura );

                String id_cliente = String.valueOf(transaccions.get(position).getId_cliente());
                intent.putExtra("id_cliente",id_cliente);

                String cliente = String.valueOf(transaccions.get(position).getCliente());
                intent.putExtra("cliente",cliente);

                String id_transaccion = String.valueOf(transaccions.get(position).getId_transaccion());
                intent.putExtra("id_transaccion",id_transaccion);

                String abono = String.valueOf(transaccions.get(position).getAbono());
                intent.putExtra("abono", abono);

                String total = String.valueOf(transaccions.get(position).getTotal());
                intent.putExtra("total",total);

                String cant_pedida = String.valueOf(transaccions.get(position).getCant_pedida());
                intent.putExtra("cant_pedida",cant_pedida);



                intent.putExtra("id_vendedor",sidVendedor);
                Toast.makeText(getContext(),"aaaa"+transaccions.get(position).getCant_pedida(), Toast.LENGTH_LONG).show();
                intent.putExtra("estado",transaccions.get(position).getEstado());
                intent.putExtra("producto",transaccions.get(position).getProducto());
                contexto.startActivity(intent);

            }
        });

        if(transaccions.get(position).getRestante()==0){
            imprimir.setVisibility(View.INVISIBLE);
        }


        String toV = Integer.toString(transaccions.get(position).getId_vendedor());
        vendedor.setText(toV);

        String toF = Integer.toString(transaccions.get(position).getId_factura());
        factura.setText(toF);

//        String toP = Integer.toString(transaccions.get(position).getId_pedido());
//        pedido.setText(toP);

        String toA = Integer.toString(transaccions.get(position).getAbono());
        abonado.setText(toA);

        String toR = Integer.toString(transaccions.get(position).getRestante());
        restante.setText(toR);

        String toT = Integer.toString(transaccions.get(position).getTotal());
        total.setText(toT);

        fecha.setText(transaccions.get(position).getFecha_reg());

        return customView;

    }


}
