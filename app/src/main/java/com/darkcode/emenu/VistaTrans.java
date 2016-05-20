package com.darkcode.emenu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.darkcode.emenu.Facturar.AdapterTransacciones;
import com.darkcode.emenu.Facturar.PedidoService;
import com.darkcode.emenu.Facturar.Transaccion;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 1/3/16.
 */
public class VistaTrans extends Fragment {

    private OnFragmentInteractionListener mListener;

    public VistaTrans() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.content_list_trans, container, false);


        final String cliente = this.getArguments().getString("id_cliente");
        final int id_cliente = Integer.parseInt(cliente);
        final ListView lvresult;
        lvresult = (ListView)view.findViewById(R.id.lvTrans);

        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
        PedidoService servicio = restadpter.create(PedidoService.class);

        servicio.getTransacciones(id_cliente, new Callback<List<Transaccion>>() {
            @Override
            public void success(List<Transaccion> transaccions, Response response) {
                ListAdapter listAdapter = new AdapterTransacciones(getContext(), transaccions);
                lvresult.setAdapter(listAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Error", error.getMessage());
            }
        });
        return view;



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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