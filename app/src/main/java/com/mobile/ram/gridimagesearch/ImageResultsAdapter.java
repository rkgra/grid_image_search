package com.mobile.ram.gridimagesearch;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rgauta01 on 9/25/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        //Get the data item for this postion

        ImageResult imageResult = getItem(position);

        // check if we are using a recycled view ,if not we need to inflate

        if (convertView == null) {
            //create view

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);

        }

        // Lookup the view for populating tha data.

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        ImageView ivImageView = (ImageView) convertView.findViewById(R.id.ivImage);

        ivImageView.setImageResource(0);


        // Insert the model data into each of the view items

        tvTitle.setText(Html.fromHtml( imageResult.title));

        Picasso.with(getContext()).load(imageResult.thumbUrl).into(ivImageView);

        return convertView;

    }
}