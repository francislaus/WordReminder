package com.example.franz.wordreminder.Model;

/**
 * Created by franz on 20.09.16.
 */

public class EnumCaster {

    //this class casts from string to the corresponding enum tpye

    public static WordType castWordType(String type){

        //only to be sure
        type = type.toUpperCase();

        switch (type){
            case "NOUN":
                return WordType.NOUN;
            case "VERB":
                return WordType.VERB;
            case "ADJECTIV":
                return WordType.ADJECTIVE;
            case "ADVERB":
                return WordType.ADVERB;
            default:
                return WordType.UNKNOWN;
        }
    }

    public static WordLanguage castLanguage(String lang){

        //only to be sure
        lang = lang.toUpperCase();

        switch (lang){
            case "ENGGER":
                return WordLanguage.ENGGER;
            case "ESPGER":
                return WordLanguage.ESPGER;
            default:
                return WordLanguage.UNKNOWN;
        }
    }

}
