package com.eat.maitreyijadhav.eat;

/**
 * Created by maitreyijadhav on 27/12/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<Bitmap> bitmapArray;
    private final ArrayList<String> origin;


    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<String> origin, ArrayList<Bitmap> imgid) {
        super(context, R.layout.custom_list_view, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.bitmapArray=imgid;
        this.origin = origin;
        //Log.d("Debug","CustomListAdapter");

    }

    @Override
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_list_view, null,true);
        ImageView image = (ImageView)  rowView.findViewById(R.id.setBackground);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname.get(position));
        imageView.setImageBitmap(bitmapArray.get(position));
        image.setImageBitmap(bitmapArray.get(position));
        extratxt.setText("Origin: "+origin.get(position));
        //Log.d("Debug","Item name of the "+itemname.get(position) + "The origin of the recepie is "+"Origin: "+origin.get(position));
        return rowView;

    }
}
