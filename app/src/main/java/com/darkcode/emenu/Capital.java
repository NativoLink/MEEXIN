package com.darkcode.emenu;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.darkcode.emenu.Cliente.Cliente;
import com.darkcode.emenu.Cliente.ClienteService;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VistaProductos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Capital extends Fragment {

    private OnFragmentInteractionListener mListener;
    public Capital() {

    }
    private  TextView tv_capital;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.capital, container, false);
        final String id_cliente = this.getArguments().getString("id_cliente");
        final int idCliente = Integer.valueOf(id_cliente);
        String nombre = this.getArguments().getString("nombre");
        String apellido = this.getArguments().getString("apellido");
        String correo = this.getArguments().getString("correo");
        String capital = this.getArguments().getString("saldo");
        tv_capital = (TextView) view.findViewById(R.id.tvSaldo);

        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
        ClienteService servicio = restadpter.create(ClienteService.class);
        servicio.postSaldo(idCliente, new Callback<Cliente>() {
            @Override
            public void success(Cliente cliente, Response response) {
                int saldo = cliente.getSaldo();
                String parsear = String.valueOf(saldo);
                tv_capital.setText(parsear);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        final Button actualizarCapital;
        actualizarCapital = (Button)view.findViewById(R.id.btnActualizarCapital);
        actualizarCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
                ClienteService servicio = restadpter.create(ClienteService.class);
                servicio.postSaldo(idCliente, new Callback<Cliente>() {
                    @Override
                    public void success(Cliente cliente, Response response) {
                        int saldo = cliente.getSaldo();
                        String parsear = String.valueOf(saldo);
                        tv_capital.setText(parsear);

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
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
