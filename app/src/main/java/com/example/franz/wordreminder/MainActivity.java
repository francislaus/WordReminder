package com.example.franz.wordreminder;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franz.wordreminder.Controller.GraphDataController;
import com.example.franz.wordreminder.Controller.ReadInterface;
import com.example.franz.wordreminder.Controller.StoreInterface;
import com.example.franz.wordreminder.Controller.XMLReadImplementor;
import com.example.franz.wordreminder.Controller.XMLStoreImplementor;
import com.example.franz.wordreminder.Model.InvalidSourceFileException;
import com.example.franz.wordreminder.Model.Word;
import com.example.franz.wordreminder.Model.WordStore;
import com.example.franz.wordreminder.View.Activity.AbstractActivity;
import com.example.franz.wordreminder.View.Activity.UsageActivity;
import com.example.franz.wordreminder.View.Dialog.DeleteWordDialog;
import com.example.franz.wordreminder.View.Dialog.SelectWordTypeDialog;
import com.example.franz.wordreminder.View.Dialog.ShowWordDialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AbstractActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Button storeButton;
    private StoreInterface storeInterface;
    private ReadInterface readInterface;
    private WordStore wordStore;
    private ScheduledExecutorService timeExecutorService;

    //textviews for the last three words

    private ArrayList<TextView> nativeTextViews;
    private ArrayList<TextView> foreignTextViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        //todo: go on

        storeButton = (Button) findViewById(R.id.activity_main_store_button);

        storeInterface = new XMLStoreImplementor(getApplicationContext());

        readInterface = new XMLReadImplementor(getApplicationContext());
        Collection<Word> words = readInterface.read();
        Iterator<Word> iterator = words.iterator();
        while(iterator.hasNext()){
            Log.w(LOG_TAG, iterator.next().toString());
        }

        //instanciate all text-views
        TextView nativeFirst = (TextView) findViewById(R.id.text_view_lang_native_first);
        TextView foreignFirst = (TextView) findViewById(R.id.text_view_lang_foreign_first);
        TextView nativeSecond = (TextView) findViewById(R.id.text_view_lang_native_second);
        TextView foreignSecond = (TextView) findViewById(R.id.text_view_lang_foreign_second);
        TextView nativeThird = (TextView) findViewById(R.id.text_view_lang_native_third);
        TextView foreignThird = (TextView) findViewById(R.id.text_view_lang_foreign_third);
        nativeTextViews = new ArrayList<>();
        nativeTextViews.add(nativeFirst);
        nativeTextViews.add(nativeSecond);
        nativeTextViews.add(nativeThird);
        foreignTextViews = new ArrayList<>();
        foreignTextViews.add(foreignFirst);
        foreignTextViews.add(foreignSecond);
        foreignTextViews.add(foreignThird);

        //has to be the last init, because of back-reference
        wordStore = new WordStore(this);

        if((WordStore.LAST_WORD_SIZE != nativeTextViews.size()) ||  (WordStore.LAST_WORD_SIZE != foreignTextViews.size())){
            throw new InvalidSourceFileException();
        }

        timeExecutorService = Executors.newSingleThreadScheduledExecutor();
        timeExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //Log.e(LOG_TAG, "Every 30 seconds");
                storeInterface.store(wordStore.getWords(), "ENGGER");
            }
        }, 0, 30000, TimeUnit.MILLISECONDS);
    }

    //click listener for the add button
    public void onAdd(View v){
        //Log.e(LOG_TAG, "add word");
        SelectWordTypeDialog selectWordTypeDialog = new SelectWordTypeDialog();
        selectWordTypeDialog.initDialog(wordStore);
        selectWordTypeDialog.show(getFragmentManager(), "SelectWordTypeDialog");
    }

    //click listener for the delete button
    public void onDelete(View v){
        /*DeleteWordDialog deleteWordDialog = new DeleteWordDialog();
        deleteWordDialog.initDialog(wordStore, getApplicationContext());
        deleteWordDialog.show(getFragmentManager(), "DeleteWordDialog");*/
        /*GraphDataController dataController = new GraphDataController(getApplicationContext());
        if(!dataController.thisAvailable()){
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.layout_usage_not_data_available), Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            switchActivity(UsageActivity.class);
        }*/
    }

    //on-click methods for all views

    public void onFirstView(View v){
        String nativeLang = (String)nativeTextViews.get(0).getText();
        Log.e(LOG_TAG, nativeLang);
        Word word = wordStore.findWord(nativeLang);
        if(word != null) {
            ShowWordDialog showWordDialog = new ShowWordDialog();
            showWordDialog.initDialog(word);
            showWordDialog.show(getFragmentManager(), "ShowWordDialog");
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.layout_main_word_not_found), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onSecondView(View v){
        String nativeLang = (String)nativeTextViews.get(1).getText();
        Word word = wordStore.findWord(nativeLang);
        if(word != null) {
            ShowWordDialog showWordDialog = new ShowWordDialog();
            showWordDialog.initDialog(word);
            showWordDialog.show(getFragmentManager(), "ShowWordDialog");
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.layout_main_word_not_found), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onThirdView(View v){
        String nativeLang = (String)nativeTextViews.get(2).getText();
        Word word = wordStore.findWord(nativeLang);
        if(word != null) {
            ShowWordDialog showWordDialog = new ShowWordDialog();
            showWordDialog.initDialog(word);
            showWordDialog.show(getFragmentManager(), "ShowWordDialog");
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.layout_main_word_not_found), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void refreshTextViews(ArrayList<Word> list){
        int i = 0;
        for(; i < list.size(); i++){
            Word w = list.get(i);
            nativeTextViews.get(i).setText(w.getNativeLang());
            foreignTextViews.get(i).setText(w.getForeignLang());
        }

        for(; i < WordStore.LAST_WORD_SIZE; i++){
            nativeTextViews.get(i).setText("");
            foreignTextViews.get(i).setText("");
        }
    }


    @Override
    public void onPause(){
        super.onPause();
        //we have to do all things that have to be done before leaving the app
        timeExecutorService.shutdown();
    }

}
