package com.mobile.ram.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SettingActivity extends Activity {

    private EditText etImageSize;

    private EditText etColorFilter;

    private EditText etImageType;

    private EditText etSiteFilter;

    private SearchSetting querySearchSetting;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        showView();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);




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

    private void showView() {

        etImageSize= (EditText) findViewById(R.id.etImageSize);
        etColorFilter= (EditText) findViewById(R.id.etColorFilter);
        etImageType= (EditText) findViewById(R.id.etImageType);
        etSiteFilter= (EditText) findViewById(R.id.etSiteFilter);
        querySearchSetting = (SearchSetting) getIntent().getSerializableExtra("querySearchSetting");

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String colorFilter = pref.getString("colorFilter", "");

        Log.i("DEBUG", "PreferenceManager==>colorFilter:"+colorFilter);

//
//        edit.putString("colorFilter", querySearchSetting.colorFilter );
//        edit.putString("imageSize", querySearchSetting.imageSize );
//        edit.putString("imageType", querySearchSetting.imageType );
//        edit.putString("siteFilter", querySearchSetting.siteFilter );

        // Show Selected item in text box



            etImageSize.setText( pref.getString("imageSize", ""));



            etColorFilter.setText( pref.getString("colorFilter", ""));


            etImageType.setText(pref.getString("imageType", ""));


            etSiteFilter.setText( pref.getString("siteFilter", ""));




    }

    public void saveSetting(View view) {

        SearchSetting searchSetting=new SearchSetting();

        searchSetting.imageSize=etImageSize.getText().toString();

        searchSetting.colorFilter=etColorFilter.getText().toString();

        searchSetting.imageType =etImageType.getText().toString();

        searchSetting.siteFilter=etSiteFilter.getText().toString();


        Intent data = new Intent();

        data.putExtra("searchSetting",  searchSetting);

        data.putExtra("code", 200);

        setResult(RESULT_OK, data);

        Log.i("DEBUG", searchSetting.toString());

        this.finish();



    }





    public void cancelSetting(View view) {

       this.finish();

    }
}
