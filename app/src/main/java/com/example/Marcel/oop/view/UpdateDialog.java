package com.example.Marcel.oop.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.Marcel.oop.R;
import com.example.Marcel.oop.utilities.DateInputMask;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * Custom Dialog class to update data in mainactivity
 */

public class UpdateDialog extends AppCompatDialogFragment {
    DialogListener1 listener;
    EditText Nazwa,Kcal,Data,Ilosc;
    TextInputLayout textInputLayout;

    public UpdateDialog(DialogListener1 listener) {
        this.listener = listener;
    }

    /**
     * Dialog lifecycle method
     * @param savedInstanceState
     * @return dialog creator
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_layout,null);
        Nazwa=(EditText)view.findViewById(R.id.updateProductName);
        Kcal=(EditText)view.findViewById(R.id.updateProductKcal);
        Data=(EditText)view.findViewById(R.id.updateProductDate);
        Ilosc=(EditText)view.findViewById(R.id.updateProductCount);
        textInputLayout=view.findViewById(R.id.textFieldDialog);
        DateInputMask dateInputMask=new DateInputMask(Data,textInputLayout);
        builder.setView(view)
                .setTitle("Zmiana parametrów produktu")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
        .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            String name= Nazwa.getText().toString();
            String kcal= Kcal.getText().toString();
            String data=Data.getText().toString();
            String ilosc=Ilosc.getText().toString();
            listener.apply(name,kcal,data,ilosc);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //listener = (DialogListener)context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public interface DialogListener1 {
        void apply(String nazwa,String kcal,String data,String ilosc);
    }
}
