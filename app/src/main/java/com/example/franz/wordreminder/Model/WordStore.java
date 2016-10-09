package com.example.franz.wordreminder.Model;

import android.util.Log;

import com.example.franz.wordreminder.Controller.GraphDataController;
import com.example.franz.wordreminder.MainActivity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by franz on 20.09.16.
 */

public class WordStore {

    private static final String LOG_TAG = WordStore.class.getSimpleName();
    //has to be equal with LAST_WORD_SIZE in WordStore!!!
    public static final int LAST_WORD_SIZE = 3;

    //functionality: store all words for runtime;
    private ArrayList<Word> lastWords;
    //key is always the nativeLang
    private Hashtable<String, Word> hashtable;
    //gets messages from this instance
    private MainActivity mainActivity;
    //updates the graph
    private GraphDataController dataController;

    public WordStore(MainActivity mainActivity){
        //only inits
        lastWords = new ArrayList<>();
        hashtable = new Hashtable<>();
        this.mainActivity = mainActivity;
        //all views should be empty at the beginning
        mainActivity.refreshTextViews(lastWords);
        dataController = new GraphDataController(mainActivity.getApplicationContext());
    }

    //adds a word to the store
    public void addWord(Word word){
        wordOperation();
        addToLast(word);
        addToHashtable(word);
        if(!lastWords.isEmpty()) {
            mainActivity.refreshTextViews(lastWords);
        }
    }

    private void addToLast(Word word){
        //Log.e(LOG_TAG, "word to last added");
        lastWords.add(word);
        if(lastWords.size() > LAST_WORD_SIZE){
            //we have to remove the first word because it is the oldest word
            lastWords.remove(0);
        }
    }

    private void addToHashtable(Word word){
        if(hashtable.contains(word.getNativeLang())){
            //Log.e(LOG_TAG, "only incremeneted");
            //we only have to increment the int value times
            Word w = hashtable.get(word.getNativeLang());
            //delete the old word
            hashtable.remove(word.getNativeLang());
            w.incrementTimes();
            //restore the word
            hashtable.put(w.getNativeLang(), w);
        }
        else {
            //simply store the word
            if(word != null){
                hashtable.put(word.getNativeLang(), word);
                //Log.e(LOG_TAG, "word stored: "+word.toString());
            }
        }
    }

    public Word findWord(String nativeLang){
        //returns the corresponding word to nativeLand if it exists
        //otherwise null
        //printHashtable();
        if(hashtable.containsKey(nativeLang)){
            //Log.e(LOG_TAG, "contains");
            return hashtable.get(nativeLang);
        }
        return null;
    }

    public boolean deleteWord(String nativeLang){
        //returns true if the word has been deleted, otherwise false as return value
        //has to refresh the views
        if(hashtable.containsKey(nativeLang)){
            Word w = hashtable.remove(nativeLang);
            if(lastWords.contains(w)){
                //we have to refresh the views
                lastWords.remove(w);
                mainActivity.refreshTextViews(lastWords);
            }
            //printHashtable();
            wordOperation();
            return true;
        }
        return false;
    }

    private void wordOperation(){
        //if and only if a deletion or addition occurs
        Log.e(LOG_TAG, "wordOperation");
        dataController.addNewWord();
    }

    private void printHashtable(){
        Collection<Word> collection = hashtable.values();
        Iterator<Word> iterator = collection.iterator();
        Log.w(LOG_TAG, "Hashtable");
        while(iterator.hasNext()){
            Log.w(LOG_TAG, iterator.next().toString());
        }
    }

    public Collection<Word> getWords(){
        return hashtable.values();
    }

}
