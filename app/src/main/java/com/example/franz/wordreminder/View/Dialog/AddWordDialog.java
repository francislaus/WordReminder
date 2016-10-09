package com.example.franz.wordreminder.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.franz.wordreminder.Model.Word;
import com.example.franz.wordreminder.Model.WordStore;
import com.example.franz.wordreminder.Model.WordType;
import com.example.franz.wordreminder.R;

/**
 * Created by franz on 21.09.16.
 */

public class AddWordDialog extends DialogFragment {

    private static final String LOG_TAG = AddWordDialog.class.getSimpleName();

    private WordStore wordStore;
    private WordType wordType;

    public void initDialog(WordStore wordStore, WordType wordType){
        this.wordStore = wordStore;
        this.wordType = wordType;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_word, null);
        builder.setView(view);
        final EditText nativeEditText = (EditText) view.findViewById(R.id.dialog_add_word_edittext_native);
        final EditText foreignEditText = (EditText) view.findViewById(R.id.dialog_add_word_edittext_foreign);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.e(LOG_TAG, "yes button pressed");
                String nativeString = nativeEditText.getText().toString();
                String foreignString = foreignEditText.getText().toString();
                if(nativeString.length() > 0 && foreignString.length() > 0){
                    Word word = new Word(nativeString, foreignString, wordType, 0);
                    wordStore.addWord(word);
                }
                else{
                    //inform the user about his error
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please insert something", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        return builder.create();
    }

}
