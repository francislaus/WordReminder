package com.example.franz.wordreminder.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.franz.wordreminder.Model.WordStore;
import com.example.franz.wordreminder.R;

/**
 * Created by franz on 24.09.16.
 */

public class DeleteWordDialog extends DialogFragment {

    private static final String LOG_TAG = DeleteWordDialog.class.getSimpleName();

    private WordStore wordStore;
    private Context context;

    public void initDialog(WordStore wordStore, Context context){
        this.wordStore = wordStore;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete_word, null);
        builder.setView(view);
        builder.setTitle(getResources().getString(R.string.layout_main_button_delete_word));
        final EditText editText = (EditText) view.findViewById(R.id.dialog_delete_word_edittext);
        builder.setNegativeButton(android.R.string.cancel, null);
        //todo: refresh views!!!
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nativeLang = editText.getText().toString();
                if(wordStore.deleteWord(nativeLang)){
                    //successfully deleted
                    Toast toast = Toast.makeText(context, getResources().getString(R.string.layout_delete_word_dialog_deleted_true), Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    //not successfully deleted
                    Toast toast = Toast.makeText(context, getResources().getString(R.string.layout_delete_word_dialog_deleted_false), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        return builder.create();
    }

}
