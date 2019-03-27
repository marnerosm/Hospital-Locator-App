package com1032.cw2.mm01632.mm01632cw2com1032;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by marne on 19/05/2018.
 */

public class CustomAdapter extends ArrayAdapter<Hospital> {

    /**
     * A Custom Adapter that is used to display a custom listview in the main activity,
     * containing an image and three textviews.
     */
    private Context mContext;
    private int mResource;

    public CustomAdapter(Context context, int resource, ArrayList<Hospital> hospitals) {
        super(context, resource, hospitals);
        this.mContext = context;
        this.mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String city = getItem(position).getCity();
        String type = getItem(position).getType();

        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        convertView = inflater.inflate(this.mResource, parent, false);

        ImageView mImage= (ImageView)convertView.findViewById(R.id.imageView);
        TextView tvName = (TextView) convertView.findViewById(R.id.hospital_name);
        TextView tvCity = (TextView) convertView.findViewById(R.id.hospital_city);
        TextView tvType = (TextView) convertView.findViewById(R.id.hospital_type);


        tvName.setText(name);
        tvCity.setText(city);
        tvType.setText(type);
        return convertView;


    }

}
