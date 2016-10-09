package com.example.franz.wordreminder.Model;

/**
 * Created by franz on 20.09.16.
 */

public class Word {

    //a word that can be stored and displayed in the app
    //entity object

    //key in the xml-storage
    private String nativeLang;
    //value in the xml-storage
    private String foreignLang;
    //type in the xml-storage
    private WordType type;
    //how many times the word was searched
    private int times;

    public Word(String nativeLang, String foreignLang, WordType type, int times){
        //setting the words is only available with the constructor
        this.nativeLang = nativeLang;
        this.foreignLang = foreignLang;
        this.type = type;
        this.times = times;
    }

    public void incrementTimes(){
        //called when a word is called more than one time
        times++;
    }

    public String getNativeLang(){
        return nativeLang;
    }

    public String getForeignLang(){
        return foreignLang;
    }

    public WordType getType(){
        return type;
    }

    public int getTimes(){
        return times;
    }

    @Override
    public String toString(){
        return nativeLang+" - "+foreignLang;
    }

}
