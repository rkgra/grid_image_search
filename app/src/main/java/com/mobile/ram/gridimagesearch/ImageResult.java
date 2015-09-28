package com.mobile.ram.gridimagesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by rgauta01 on 9/23/15.
 */
public class ImageResult implements Serializable {

    public String fullUrl;
    public String thumbUrl;
    public String title;

    private static final long serializableVerionID= 100;




    public ImageResult(JSONObject json){

        try {

            this.fullUrl=json.getString("url");
            this.thumbUrl=json.getString("tbUrl");
            this.title=json.getString("title");



        }catch (JSONException e){

            e.printStackTrace();
        }


    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray jsonArray){

             ArrayList<ImageResult> results=new ArrayList<ImageResult>();

        try {
            for(int i=0; i< jsonArray.length();i++){

                results.add(new ImageResult(jsonArray.getJSONObject(i)));


            }

        }catch (JSONException e){

            e.printStackTrace();
        }


        return results;
    }


}
