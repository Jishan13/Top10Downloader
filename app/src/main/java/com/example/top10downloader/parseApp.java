package com.example.top10downloader;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class parseApp {
    private static final String TAG = "parseApp";
    private ArrayList<Entry>applications;

    public parseApp() {

        this.applications = new ArrayList<>();
    }

    public ArrayList<Entry> getApplications() {
        return applications;
    }
    public boolean parse(String xmlData){
        boolean status = true;
        Entry currentRecord=null;
        boolean inEntry = false;
        String textVal="";
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while(eventType!= XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parse: Starting of "+ tagName);
                        if("entry".equalsIgnoreCase(tagName)){
                            inEntry =true;
                            currentRecord = new Entry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textVal= xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: End Of "+ tagName);
                        if(inEntry){
                            if("entry".equalsIgnoreCase(tagName)){
                                applications.add(currentRecord);
                                inEntry= false;
                            } else if("name".equalsIgnoreCase(tagName)){
                                currentRecord.setName(textVal);
                            } else if("artist".equalsIgnoreCase(tagName)){
                                currentRecord.setArtist(textVal);
                            }else if("summary".equalsIgnoreCase(tagName)){
                                currentRecord.setSummary(textVal);
                            }else if("releaseDate".equalsIgnoreCase(tagName)){
                                currentRecord.setReleaseDate(textVal);
                            }else if("image".equalsIgnoreCase(tagName)){
                                currentRecord.setImageURL(textVal);
                            }

                        }
                        break;
                        default:
                }
                eventType=xpp.next();
            }
            for (Entry a:applications) {
                Log.d(TAG, "**********");
                Log.d(TAG, a.toString());
            }
        }catch (Exception e){
            status =false;
            e.printStackTrace();
        }
        return status;
    }
}
