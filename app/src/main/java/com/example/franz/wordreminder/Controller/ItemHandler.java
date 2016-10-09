package com.example.franz.wordreminder.Controller;

import android.util.Log;

import com.example.franz.wordreminder.Model.EnumCaster;
import com.example.franz.wordreminder.Model.Word;
import com.example.franz.wordreminder.Model.WordLanguage;
import com.example.franz.wordreminder.Model.WordType;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by franz on 20.09.16.
 */

public class ItemHandler extends DefaultHandler {

    private static final String LOG_TAG = ItemHandler.class.getSimpleName();

    private ArrayList<Word> words;
    private WordLanguage wordLanguage;

    public ItemHandler(){
        words = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase("root")){
            Log.e(LOG_TAG, "lang found");
            wordLanguage = EnumCaster.castLanguage(attributes.getValue("lang"));
        }
        if(qName.equalsIgnoreCase("item")){
            Log.e(LOG_TAG, "item found");
            WordType type = EnumCaster.castWordType(attributes.getValue("type"));
            String nativeLang = attributes.getValue("key");
            String foreignLang = attributes.getValue("value");
            int times = Integer.parseInt(attributes.getValue("times"));
            Word word = new Word(nativeLang, foreignLang, type, times);
            Log.w(LOG_TAG, word.toString());
            words.add(word);
        }
    }

    public Collection<Word> getWords(){
        return words;
    }

    public WordLanguage getWordLanguage(){
        return wordLanguage;
    }

}
