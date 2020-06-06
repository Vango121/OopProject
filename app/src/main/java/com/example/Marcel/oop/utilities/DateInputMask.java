package com.example.Marcel.oop.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Class that format text in edittext to date format
 */
public class DateInputMask implements TextWatcher, View.OnFocusChangeListener {


    private EditText input;
    TextInputLayout textInputLayout;

    /**
     * counting chars
     * @param str String in which method count char
     * @param c char to count
     * @return number of chars in string
     */
    public int countChar(String str, char c)
    {
        int count = 0;
        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }
        return count;
    }

    /**
     * default constructor
     * @param input text written
     * @param textInputLayout Material textInput
     */
    public DateInputMask(EditText input, TextInputLayout textInputLayout) {
        this.input = input;
        this.input.addTextChangedListener(this);
        this.textInputLayout=textInputLayout;
        this.input.setOnFocusChangeListener(this);
    }

    /**
     * Method before editText is changed
     * @param charSequence
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * Method triggered by textchange in edittext
     * @param charSequence text in editext
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String val = input.getText().toString();

        int count = countChar(val,'/');
        if((val.length()==2||val.length()==5)&&count <2){
            input.append("/");
        }

    }

    /**
     * method triggered when text is already changed
     * @param editable
     */
    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * check if editext has focus and set or hide hint
     * @param view
     * @param b focus boolean if true= there is focus false no focus
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        if(b){
            textInputLayout.setHint("DD/MM/RRRR");}
        else{
            textInputLayout.setHint(" ");
        }

    }
}