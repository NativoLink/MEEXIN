package com.darkcode.emenu;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.darkcode.emenu.Cliente.ClienteService;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 1/3/16.
 */
public class VistaCuenta extends Fragment {

    private OnFragmentInteractionListener mListener;

    public VistaCuenta() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.configuraciones, container, false);
        final String id_cliente= this.getArguments().getString("id_cliente");
        final String nombre= this.getArguments().getString("nombre");
        final  String tel = this.getArguments().getString("telefono");
//        Toast.makeText(getContext(),nombre, Toast.LENGTH_LONG).show();
        TextView tv_user = (TextView)view.findViewById(R.id.tvUser);
        tv_user.setText(id_cliente);
        TextView tv_nombre = (TextView)view.findViewById(R.id.tvNombre);
        tv_nombre.setText(nombre);


        Button actualizar = (Button)view.findViewById(R.id.btnActualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText clave_a = (EditText)view.findViewById(R.id.etClaveA);
                EditText clave_n = (EditText)view.findViewById(R.id.etClaveN);
                EditText clave_c = (EditText)view.findViewById(R.id.etClaveC);
                if(clave_a != null  && clave_n != null  && clave_c != null && (clave_a.toString() !=""  && clave_n.toString() !=""  && clave_c.toString() !="")) {
                    if (clave_n.getText().toString().equals(clave_c.getText().toString())) {
                        Toast.makeText(getContext(),clave_n.getText().toString(), Toast.LENGTH_LONG).show();

                        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
                        ClienteService servicio = restadpter.create(ClienteService.class);
                        int IdCliente = Integer.valueOf(id_cliente);
                        servicio.ActualizarCuenta(IdCliente, clave_n.getText().toString(), clave_a.getText().toString(), new Callback<String>() {
                            @Override
                            public void success(String s, Response response) {
                                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getContext(), error.getMessage()+"asdfghjkl;", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });



//        Toast.makeText(getContext(), tel, Toast.LENGTH_LONG).show();
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