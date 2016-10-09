package com.example.franz.wordreminder.Controller;

import com.example.franz.wordreminder.Model.Word;

import java.util.Collection;

/**
 * Created by franz on 20.09.16.
 */

public interface StoreInterface {

    //specifies a interface to store words in the app

    void store(Collection<Word> words, String language);

}
