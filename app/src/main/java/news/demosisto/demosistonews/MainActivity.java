package news.demosisto.demosistonews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtEdit;
    public SharedPreferences dataSet;
    private Spinner catSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtEdit = (EditText) findViewById(R.id.txtSearch);
        dataSet = getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor dataSetEditor = dataSet.edit();
        dataSetEditor.putString(getString(R.string.pref_str),getResources().getString(R.string.url) + "/feed/");
        dataSetEditor.commit();
        Log.v("pref_first_push",dataSet.getString(getString(R.string.pref_str),"Error!"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        catSpinner = (Spinner) findViewById(R.id.catSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.catIdArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(adapter);

        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {

            addRssFragment();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Fetching......", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                doSearch();

                InputMethodManager ime = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                ime.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void addRssFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RssFragment fragment = new RssFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doSearch(){

        String forCompare = txtEdit.getText().toString();
        forCompare.trim();
        txtEdit.setText(forCompare);
        SharedPreferences.Editor dataSetEditor = dataSet.edit();


        String srcURL = getResources().getString(R.string.url);

        //convert catName to catPath
        int intCatId = catSpinner.getSelectedItemPosition();

        String catId = catNameToIdConverter(intCatId);

        Log.v("catId_Before_Converted", "catId: " + catId);
        if(catId == "none") {
            srcURL = srcURL + "/feed";
        }else{
            srcURL = srcURL + "/category/" + catId + "/feed";
        }

        //final push
        if(forCompare.indexOf("=") >= 0){
            dataSetEditor.putString(getString(R.string.pref_str),srcURL + "/?" + forCompare);
            dataSetEditor.commit();
            addRssFragment();
        }else{
            dataSetEditor.putString(getString(R.string.pref_str),srcURL
                    +getString(R.string.feed_header) + forCompare);
            dataSetEditor.commit();
            addRssFragment();
        }
        txtEdit.setText("");

    }


    public String catNameToIdConverter(int intIn){
        String result = "none";
        if(intIn == 0){result ="none";}
        if (intIn == 1){result = "local";}
        if (intIn == 2){result = "global";}
        if (intIn == 3){result = "acgn";}
        if (intIn == 4){result = "tech";}
        if (intIn == 5){result = "lifestyle";}
        if (intIn == 6){result =  "funny";}

        return result;



    }
}
