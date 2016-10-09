package com.example.franz.wordreminder.Controller;

import com.example.franz.wordreminder.Model.Word;

import java.util.Collection;

/**
 * Created by franz on 20.09.16.
 */

public interface ReadInterface {

    //specifies the interface how to read for all implementors

    //reads the whole file and returns a collection of words
    Collection<Word> read();

}
