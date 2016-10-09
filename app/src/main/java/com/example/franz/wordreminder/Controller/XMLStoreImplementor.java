package com.example.franz.wordreminder.Controller;

import android.content.Context;
import android.util.Log;

import com.example.franz.wordreminder.Model.Word;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by franz on 20.09.16.
 */

public class XMLStoreImplementor implements StoreInterface {

    //stores values in a xml file

    private static final String LOG_TAG = XMLStoreImplementor.class.getSimpleName();

    private Context context;

    public XMLStoreImplementor(Context context){
        this.context = context;
    }

    @Override
    public void store(Collection<Word> words, String language) {
        //throw new UnsupportedOperationException("XMLStoreImplementor is not implemented!");
        //at the beginning we have to create the root-tag
        String filename = "example.xml";
        String data = "";

        data += "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        data += "<root lang=\""+language+"\">\n";
        for(Word w : words){
            data += "    <item type=\""+w.getType()+"\" key=\""+w.getNativeLang()+"\" value=\""+w.getForeignLang()+"\" times=\""+w.getTimes()+"\"/>\n";
        }
        data += "</root>";

        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
            Log.e(LOG_TAG, "stored");
        }
        catch (IOException ioe){
            Log.e(LOG_TAG, "File was not created in a valid way");
            ioe.printStackTrace();
        }
    }

}
