package com.example.Marcel.oop;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;

public class DateInputMask implements TextWatcher, View.OnFocusChangeListener {


    private EditText input;
    TextInputLayout textInputLayout;

    public int countChar(String str, char c)
    {
        int count = 0;
        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }
        return count;
    }

    public DateInputMask(EditText input, TextInputLayout textInputLayout) {
        this.input = input;
        this.input.addTextChangedListener(this);
        this.textInputLayout=textInputLayout;
        this.input.setOnFocusChangeListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String val = input.getText().toString();

        int count = countChar(val,'/');
        if((val.length()==2||val.length()==5)&&count <2){
            input.append("/");
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(b){
            textInputLayout.setHint("DD/MM/RRRR");}
        else{
            textInputLayout.setHint(" ");
        }

    }
}