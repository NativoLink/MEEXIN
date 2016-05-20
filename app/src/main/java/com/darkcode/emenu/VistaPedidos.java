package com.darkcode.emenu;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.darkcode.emenu.Facturar.AdapterAbonados;
import com.darkcode.emenu.Facturar.AdapterPendientes;
import com.darkcode.emenu.Facturar.AdapterRealizados;
import com.darkcode.emenu.Facturar.DetallePedido;
import com.darkcode.emenu.Facturar.PedidoService;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VistaPedidos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class VistaPedidos extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Button regProducto;
    private LayoutInflater inflarte;
    private ViewGroup containt;

    public VistaPedidos() {

    }

    public View principal(LayoutInflater inflater, ViewGroup container){
        inflarte = inflater;
        containt = container;
        final View view = inflater.inflate(R.layout.content_list, container, false);
        Resources res = getResources();

        TabHost tabs=(TabHost)view.findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Realizados",
                res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Pendientes",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Abonados",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(1);


//        ============| LISTADO DE PEDIDOS REALIZADOS |==============
        final ListView lvresult;
        lvresult = (ListView)view.findViewById(R.id.lvRealizados);

        final String id_client= this.getArguments().getString("id_cliente");
        final int id_cliente = Integer.parseInt(id_client);

        final String capital= this.getArguments().getString("saldo");
        final int saldo = Integer.parseInt(capital);

        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
        PedidoService servicio = restadpter.create(PedidoService.class);

        servicio.getPedidosRealizados(id_cliente, new Callback<List<DetallePedido>>() {
            @Override
            public void success(List<DetallePedido> productos, Response response) {

                ListAdapter listAdapter = new AdapterRealizados(getContext(), productos);
                lvresult.setAdapter(listAdapter);
                //Toast.makeText(getContext(), "CONECTADO", Toast.LENGTH_LONG).show();

            }

            @Override
            public void failure(RetrofitError error) {
//                resultado.setText(error.getMessage());
                Log.e("Error", error.getMessage());
                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

//        ============| LISTADO DE PEDIDOS PENDIENTE |==============
        final ListView lvresult2;
        lvresult2 = (ListView)view.findViewById(R.id.lvPendientes);

        servicio.getPedidosPendientes(id_cliente, new Callback<List<DetallePedido>>() {
            @Override
            public void success(List<DetallePedido> productos, Response response) {
                ListAdapter listAdapter2 = new AdapterPendientes(getContext(), productos, id_client,saldo,id_cliente);
                    lvresult2.setAdapter(listAdapter2);
                    //Toast.makeText(getContext(), "CONECTADO", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
//                resultado.setText(error.getMessage());
                Log.e("Error", error.getMessage());
               // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


//        ============| LISTADO DE PEDIDOS ABONADOS |==============
        final ListView lvresult3;
        lvresult3 = (ListView)view.findViewById(R.id.lvAbonados);


        servicio.getPedidosAbonados(id_cliente, new Callback<List<DetallePedido>>() {
            @Override
            public void success(List<DetallePedido> productos, Response response) {

                ListAdapter listAdapter = new AdapterAbonados(getContext(), productos);
                lvresult3.setAdapter(listAdapter);
                //Toast.makeText(getContext(), "CONECTADO", Toast.LENGTH_LONG).show();

            }

            @Override
            public void failure(RetrofitError error) {
//                resultado.setText(error.getMessage());
                Log.e("Error", error.getMessage());
                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return   principal(inflater,container);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


//        principal(inflarte,containt);



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
