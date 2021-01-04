package com.example.top10downloader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private String feedURL="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private int feedLimit =10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =(ListView)findViewById(R.id.xmlListView);
        downloadURL(String.format(feedURL,feedLimit));
    }

    @Override
    public void supportInvalidateOptionsMenu() {
        super.supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //String feedURL="";
        switch (id){
            case R.id.freeApps:
                feedURL="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                break;
            case R.id.songs:
                feedURL="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
                break;
            case R.id.paidApps:
                feedURL="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                break;
            case R.id.top10:
            case R.id.top25:
                if(!item.isChecked()){
                    item.setChecked(true);
                    feedLimit = 35-feedLimit;
                }else {
                    Log.d(TAG, "onOptionsItemSelected: unchanged");
                }
                break;
             default:
                return super.onOptionsItemSelected(item);
        }
        Log.d(TAG, "onOptionsItemSelected: "+ feedURL);
        downloadURL(String.format(feedURL,feedLimit));
        return true;
    }
     private void downloadURL(String x){
         Log.d(TAG, "onCreate: Asynk Task started");
         DownloadClass downloadClass = new DownloadClass();
         downloadClass.execute(x);
         Log.d(TAG, "onCreate: Finished");
     }
    private class DownloadClass extends AsyncTask<String, Void, String>{
        private static final String TAG = "DownloadClass";
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: "+s);
            super.onPostExecute(s);
            parseApp p = new parseApp();
            p.parse(s);
            FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this,R.layout.list_record,p.getApplications());
            listView.setAdapter(feedAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            String rssFeed= downloadXML(strings[0]);
            if(rssFeed==null){
                Log.e(TAG, "doInBackground: Error in Downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String x){
            StringBuilder xmldata = new StringBuilder();
            try{
                URL url = new URL(x);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                int responsecode = connection.getResponseCode();
                Log.d(TAG, "Response Code : "+ responsecode);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int charsRead=0;
                char[] inputBuffer = new char[500];
                while(true){
                  charsRead = reader.read(inputBuffer);
                  if(charsRead<0)break;
                  if(charsRead>0) xmldata.append(String.copyValueOf(inputBuffer,0,charsRead));
                }
                reader.close();
                Log.d(TAG, "downloadXML: here123");
                return xmldata.toString();
            }catch(MalformedURLException e){
                Log.e(TAG, "downloadXML: Error in Downloading"+ e.getMessage());
            }catch (IOException e){
                Log.e(TAG, "downloadXML: Error in input "+ e.getMessage());
            }catch(SecurityException e){
                Log.e(TAG, "downloadXML: Security Execption. Needs permission?" + e.getMessage() );
            }
            return null;
        }
    }
}
