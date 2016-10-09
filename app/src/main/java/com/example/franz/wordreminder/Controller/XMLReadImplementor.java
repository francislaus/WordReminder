package com.example.franz.wordreminder.Controller;

import android.content.Context;
import android.util.Log;

import com.example.franz.wordreminder.Model.Word;

import java.io.File;
import java.util.Collection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by franz on 20.09.16.
 */

public class XMLReadImplementor implements ReadInterface {

    //reads out the xml file and returns a collection of words

    private static final String LOG_TAG = XMLReadImplementor.class.getSimpleName();

    private Context context;

    public XMLReadImplementor(Context context){
        this.context = context;
    }

    @Override
    public Collection<Word> read() {

        try {
            File file = new File(context.getFilesDir().getAbsoluteFile() + "/example.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            ItemHandler handler = new ItemHandler();
            parser.parse(file, handler);
            return handler.getWords();
        }
        catch (Exception e){
            Log.e(LOG_TAG, "Parsing error");
            e.printStackTrace();
        }

        /*File file = new File(context.getFilesDir().getAbsolutePath()+"/"+filename);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String ret = "";
            String middle = "";
            while((middle = bufferedReader.readLine()) != null){
                ret += middle;
            }
            Log.w(LOG_TAG, ret);
        }
        catch (IOException ioe){
            Log.e(LOG_TAG, "File was not created in a valid way");
            ioe.printStackTrace();
        }*/
        return null;
    }

}
