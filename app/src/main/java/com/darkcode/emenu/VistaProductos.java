package com.darkcode.emenu;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.darkcode.emenu.Producto.AdapterProducto;
import com.darkcode.emenu.Producto.Producto;
import com.darkcode.emenu.Producto.ProductoService;


import java.util.List;


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
public class VistaProductos extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Button regProducto;

    public VistaProductos() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.content_list_producto, container, false);


        final String id_cliente = this.getArguments().getString("id_cliente");
        final ListView lvresult;
        lvresult = (ListView)view.findViewById(R.id.lvResultado);

        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
        ProductoService servicio = restadpter.create(ProductoService.class);

        servicio.getProducto(new Callback<List<Producto>>() {
            @Override
            public void success(List<Producto> productos, Response response) {

                ListAdapter listAdapter = new AdapterProducto(getContext(), productos,id_cliente);
                lvresult.setAdapter(listAdapter);
//                Toast.makeText(getContext(),"CONECTADO", Toast.LENGTH_LONG).show();

            }

            @Override
            public void failure(RetrofitError error) {
//                resultado.setText(error.getMessage());
                Log.e("Error", error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

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
