package com.example.Marcel.oop;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewDialog implements AdapterView.OnItemClickListener {
    ListView listView;
    String recieveValue;
    Button ok;
    private Context context;
    public ListViewDialog(Button ok, ListView listView,Context context){
        this.listView=listView;
        this.ok=ok;
        this.context=context;
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Dialog dialog =new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Ustaw");
        final EditText edtInput=dialog.findViewById(R.id.input);
        ok= dialog.findViewById(R.id.buttonOK);
        Button cancel= dialog.findViewById(R.id.buttonAnuluj);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recieveValue=edtInput.getText().toString();
                Toast.makeText(context, recieveValue, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public String getRecieveValue() {
        return recieveValue;
    }
}
