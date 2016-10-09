package com.example.franz.wordreminder.View.Dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.franz.wordreminder.Model.EnumCaster;
import com.example.franz.wordreminder.Model.WordStore;
import com.example.franz.wordreminder.Model.WordType;
import com.example.franz.wordreminder.R;

/**
 * Created by franz on 21.09.16.
 */

public class SelectWordTypeDialog extends DialogFragment {

    private static final String LOG_TAG = SelectWordTypeDialog.class.getSimpleName();

    private WordStore wordStore;

    public void initDialog(WordStore wordStore){
        this.wordStore = wordStore;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //we could choose a dialog with checkable items here too...task for later!
        builder.setTitle("Select part of speech");
        final String[] items = new String[4];
        items[0] = getString(R.string.layout_select_word_type_dialog_noun);
        items[1] = getString(R.string.layout_select_word_type_dialog_verb);
        items[2] = getString(R.string.layout_select_word_type_dialog_adjective);
        items[3] = getString(R.string.layout_select_word_type_dialog_adverb);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.e(LOG_TAG, "i: "+i);
                WordType wordType = EnumCaster.castWordType(items[i]);
                //Log.e(LOG_TAG, wordType.toString());
                AddWordDialog addWordDialog = new AddWordDialog();
                addWordDialog.initDialog(wordStore, wordType);
                addWordDialog.show(getFragmentManager(), "AddWordDialog");
            }
        });
        return builder.create();
    }

}
