package com.example.franz.wordreminder.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.franz.wordreminder.Model.Word;
import com.example.franz.wordreminder.R;

/**
 * Created by franz on 21.09.16.
 */

public class ShowWordDialog extends DialogFragment {

    private static final String LOG_TAG = ShowWordDialog.class.getSimpleName();

    private Word word;

    public void initDialog(Word word){
        this.word = word;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Log.e(LOG_TAG, "Start building");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_show_word, null);
        TextView nativeView = (TextView) view.findViewById(R.id.dialog_show_word_textview_native);
        TextView foreignView = (TextView) view.findViewById(R.id.dialog_show_word_textview_foreign);
        TextView type = (TextView) view.findViewById(R.id.dialog_show_word_textview_type);
        TextView times = (TextView) view.findViewById(R.id.dialog_show_word_textview_times);
        nativeView.setText(word.getNativeLang());
        foreignView.setText(word.getForeignLang());
        type.setText(word.getType().toString());
        times.setText(String.valueOf(word.getTimes()));
        builder.setView(view);
        return builder.create();
    }

}
