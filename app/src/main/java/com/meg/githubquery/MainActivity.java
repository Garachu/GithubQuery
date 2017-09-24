package com.meg.githubquery;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_lIST_ITEMS = 100;

    //Create and initialize MainAdapter variable
    private MainAdapter mMainAdapter;

    //For the Query
    EditText searchEditText;

    //To display the Searched URL
    TextView searchTextView;

    //To display the query result
    TextView resultTextView;

    //To display the error message
    TextView errorMessageTextView;

    //To display Results Loading progress
    ProgressBar loadingIndicator;

    //References to RecyclerView
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Referencing  resource (the activity_main.xml) file to use as the layout of MainActivity
        setContentView(R.layout.activity_main);

        //Get the entered search String
        searchEditText = (EditText) findViewById(R.id.searchEditText);

        //Get the TextBox for displaying entered search Query
        searchTextView = (TextView) findViewById(R.id.searchTextBox);

        //Get the TextBox for displaying the results
        //resultTextView = (TextView) findViewById(R.id.resultTextBox);

        //Get the TextBox for displaying the error message
        errorMessageTextView = (TextView) findViewById(R.id.error_message_display);

        //Get a reference to the ProgressBar using findViewById
        loadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_Indicator);

        //Get reference to our RecyclerView Using findViewById
        //We'll use this reference to assign a LayoutManager
        recyclerView = (RecyclerView) findViewById(R.id.rv_numbers);

        //Create and set LayoutManager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(Boolean.TRUE);

        mMainAdapter = new MainAdapter(NUM_lIST_ITEMS);

        //Set the MainAdapter on the RecyclerView
        recyclerView.setAdapter(mMainAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //call getMenuInfaltor to inflate our menu resource into our passed in menu object.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //MenuItem are identified by integer IDs, grab the selected ID
        int itemId = item.getItemId();

        //Log
        Log.i("itemId: === " , String.valueOf(itemId));
        Log.i("R.id.action_search:   " , String.valueOf(R.id.action_search));

        if(itemId == R.id.action_search){
            Context context = MainActivity.this;

            //A Toast is used to provide simple feedback about an operation in a small popup
            //A Toast only fills the amount of space required for the messge, and the current activity remains visible and interactive.
            //Toast automatically disappears after a certain timeout.
            Toast.makeText(context, "Search Clicked", Toast.LENGTH_LONG).show();

            //Get the search String on EditText
            String s = searchEditText.getText().toString();

            //Set entered search String to TextView
            searchTextView.setText(s);

            //Call the makeGithubSearchQuery method when Search is selected
            makeGithubSearchQuery(s);

        }
        return super.onOptionsItemSelected(item);
    }

    public void makeGithubSearchQuery(String searchString){
        URL url = NetworkUtils.buildUrl(searchString);
        new GithubQueryTask().execute(url);

    }

    private void showJsonDataView(){
        errorMessageTextView.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        errorMessageTextView.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        /*
        * Override doInBackground to query Github and return string
        * AsyncTask is a generic class, Meaning that it takes parameterized types in its constuctor
        * Each one of these generic parameters is to find as a java variable argument with the 3 dots
        * Which means it's technically passed as an Array*/
        protected String doInBackground(URL... urls) {

            URL uri = urls[0];

            String githubSearchResults = null;

            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return githubSearchResults;
        }

        //Override onPostExecute to set our githubSearchResults TextView
        @Override
        protected void onPostExecute(String s) {
            loadingIndicator.setVisibility(View.INVISIBLE);
            if(s != null && !s.equals("")){
                showJsonDataView();
                resultTextView.setText(s);
            }else {
                showErrorMessage();
            }
        }
    }
}
