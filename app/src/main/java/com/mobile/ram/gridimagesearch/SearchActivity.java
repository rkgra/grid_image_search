package com.mobile.ram.gridimagesearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    public SearchSetting querySearchSetting;
    private EditText etSearchQuery;
    private GridView gvSearchResult;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //

      Log.i("DEBUG","isNetworkAvailable-->"+isNetworkAvailable());

        isNetworkAvailable();

        setupView();

        imageResults = new ArrayList<ImageResult>();

        aImageResultsAdapter = new ImageResultsAdapter(this, imageResults);

        gvSearchResult.setAdapter(aImageResultsAdapter);


    }

    private void setupView() {

        etSearchQuery = (EditText) findViewById(R.id.etSearchQuery);

        gvSearchResult = (GridView) findViewById(R.id.gvSearchResult);

        gvSearchResult.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {

                // Toast.makeText(this,"onLoadMore" , Toast.LENGTH_SHORT).show();

                customLoadMoreDataFromApi(page);

                return true;
            }
        });




        gvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                  @Override
                                                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                      Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);

                                                      ImageResult result = imageResults.get(position);

                                                      //i.putExtra("url",result.fullUrl);
                                                      i.putExtra("result", result);

                                                      i.putExtra("querySearchSetting", querySearchSetting);


                                                      startActivity(i);


                                                  }
                                              }
        );


    }

    private void customLoadMoreDataFromApi(int page) {

        //Toast.makeText(this, "scroll", Toast.LENGTH_SHORT).show();

        requestImages(page * 8, false);

        Log.i("DEBUG","page====>"+page);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_search, menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    /*
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
*/
    //int page, final Boolean clearResults

    //  public void onclickImageSearch(View view) {
    public void onclickImageSearch(View view) {


        requestImages(8, true);

    }


    public void requestImages(int page, final Boolean clearResults) {

        // String searchQuery= etSearchQuery.getText().toString();

        // Toast.makeText(this,searchQuery,Toast.LENGTH_SHORT ).show();

        AsyncHttpClient client = new AsyncHttpClient();

        // 1-8
        String strSearchUrl = buildSearchString(page);

        Log.i("DEBUG","buildSearchString-->"+strSearchUrl);

        client.addHeader("Accept-Encoding", "identity");

        client.get(strSearchUrl, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // Log.d("DEBUG",response.toString());


                try {

                    JSONArray imageResultJson = null;

                    imageResultJson = response.getJSONObject("responseData").getJSONArray("results");
                    //  imageResults.clear();//CLear existing images

                    if (clearResults) {
                        // Clear out the old data, because this is a different search.
                        aImageResultsAdapter.clear();
                    }


                    //  imageResults.addAll(ImageResult.fromJSONArray(imageResultJson));
                    aImageResultsAdapter.addAll(ImageResult.fromJSONArray(imageResultJson));

                    // aImageResultsAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();


                }


                //   Log.i("DEBUG",imageResults.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // failed to do image search
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.No_internet_connection), Toast.LENGTH_LONG).show();

                //Toast.makeText(this,"testing",Toast.LENGTH_SHORT).show();

                Log.i("DEBUG", "onFailure------statusCode>"+statusCode);


            }
        });

    }

    private String buildSearchString(int start) {


        String searchQuery = etSearchQuery.getText().toString();


        String strSearchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" +
                searchQuery + getSearchsettingQuery() + "&rsz=8" + "&start=" + String.valueOf(start);


        Log.i("DEUG", "buildSearchString-->" + strSearchUrl);

        return strSearchUrl;

    }


    private String getSearchsettingQuery() {

        String searchQuery = "";

        // String strSearchUrl="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+
        //        searchQuery+getSearchsettingQuerry() +"&rsz=8";

        //imgsz=small|medium|large|xlarge
        //searchQuery= "&imgsz=small" +"&imgcolor=blue" + "&imgtype=face" +"&as_sitesearch=photobucket.com";



        if (querySearchSetting != null  && querySearchSetting.imageSize !=null && querySearchSetting.imageSize != "") {

            searchQuery = searchQuery + "&imgsz=" + querySearchSetting.imageSize;

        }
        if (querySearchSetting != null && querySearchSetting.colorFilter != null &&  ! querySearchSetting.colorFilter.isEmpty()) {

            searchQuery = searchQuery + "&imgcolor=" + querySearchSetting.colorFilter;

        }
        if (querySearchSetting != null &&  querySearchSetting.imageType != null && ! querySearchSetting.imageType .isEmpty()) {

            searchQuery = searchQuery + "&imgtype=" + querySearchSetting.imageType;

        }
        if (querySearchSetting != null && querySearchSetting.siteFilter !=null && ! querySearchSetting.siteFilter.isEmpty()) {

            searchQuery = searchQuery + "&as_sitesearch=" + querySearchSetting.imageSize;

        }


        Log.i("DEUG","getSearchsettingQuery-->"+searchQuery);

        return searchQuery;

    }


    public void OnSetting(MenuItem item) {

        //  Toast.makeText(this, "Setting CLICKED", Toast.LENGTH_SHORT).show();

        // create an intent

        Intent intent = new Intent(this, SettingActivity.class);
        //start the new Activity

       // startActivity(intent);

        startActivityForResult(intent, REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (RESULT_OK == resultCode && requestCode == REQUEST_CODE) {


            querySearchSetting = (SearchSetting) data.getSerializableExtra("searchSetting");

            // Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = pref.edit();


            edit.putString("colorFilter", querySearchSetting.colorFilter );
            edit.putString("imageSize", querySearchSetting.imageSize );
            edit.putString("imageType", querySearchSetting.imageType);
            edit.putString("siteFilter", querySearchSetting.siteFilter);

            edit.commit();


            Log.i("DEBUG", "onActivityResult-querySearchSetting->" + querySearchSetting.toString());


            requestImages(8, true);


        }


    }

    private Boolean isNetworkAvailable() {




        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.i("DEBUG", "Internet Connection Not Present");
            return false;
        }
    }

}
