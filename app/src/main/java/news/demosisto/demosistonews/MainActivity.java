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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtEdit;
    public SharedPreferences dataSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtEdit = (EditText) findViewById(R.id.txtSearch);
        dataSet = getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor dataSetEditor = dataSet.edit();
        dataSetEditor.putString(getString(R.string.pref_str),getResources().getString(R.string.url));
        dataSetEditor.commit();
        Log.v("pref_first_push",dataSet.getString(getString(R.string.pref_str),"Error!"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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
                String forCompare = txtEdit.getText().toString();
                forCompare.trim();
                txtEdit.setText(forCompare);
                SharedPreferences.Editor dataSetEditor = dataSet.edit();
                if(forCompare != "" || forCompare != null){
                    dataSetEditor.putString(getString(R.string.pref_str),getResources().getString(R.string.url)
                            +getString(R.string.feed_header) + forCompare);
                    dataSetEditor.commit();
                    addRssFragment();
                }else{
                    dataSetEditor.putString(getString(R.string.pref_str),getResources().getString(R.string.url));
                    dataSetEditor.commit();
                }
                txtEdit.setText("");
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
}
