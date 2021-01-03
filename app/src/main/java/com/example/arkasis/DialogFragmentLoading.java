package com.example.arkasis;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogFragmentLoading#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFragmentLoading extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mensaje";

    // TODO: Rename and change types of parameters
    private String strMensaje;

    //Componentes
    View view;
    TextView tvMensaje;

    public DialogFragmentLoading() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param strMensaje Parameter 1.
     * @return A new instance of fragment DialogFragmentLoading.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogFragmentLoading newInstance(String strMensaje) {
        DialogFragmentLoading fragment = new DialogFragmentLoading();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, strMensaje);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert);
        if (getArguments() != null) {
            strMensaje = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog_loading, container, false);

        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tvMensaje = view.findViewById(R.id.tvMensaje);
        tvMensaje.setText(strMensaje);

        return view;
    }

    public void actualizarMensaje(String strMensaje) {
        /*if(tvMensaje != null) {
            this.strMensaje = strMensaje;
            tvMensaje.setText(strMensaje);
        }*/
        new TaskUpdateMensajeLoading().execute(strMensaje, "", "");
    }

    private class TaskUpdateMensajeLoading extends AsyncTask<String, String, String> {
        protected String doInBackground(String... mensajes) {
            if(tvMensaje != null) {
                strMensaje = mensajes[0];
                tvMensaje.setText(mensajes[0]);
            }
            publishProgress();
            return mensajes[0];
        }

        protected void onProgressUpdate(String... progress) {

        }

        protected void onPostExecute(String result) {

        }
    }
}