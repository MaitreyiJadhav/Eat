package com.eat.maitreyijadhav.eat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class StartersPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Bitmap bitmap;
    String IMAGE_URL = "";
    //FirebaseDatabase ref = new FirebaseDatabase();
    Firebase fireBaseReference;
    private ListView list;
    private ArrayList<String> itemName;
    private ArrayList<String> origin;
    ArrayList<Bitmap> bitmapArray;
    String cousingTypes[];
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_starters_page, container, false);
        list = (ListView) view.findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.differentcousins));
        //CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemName, origin, bitmapArray);
        list.setAdapter(adapter);
        cousingTypes = getResources().getStringArray(R.array.differentcousins);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), ListViewForRecipes.class);
                Log.d("I am inside starter ", cousingTypes[position]);
                intent.putExtra("CousinName", cousingTypes[position]);
                intent.putExtra("typeOfFood", "Starter");
                startActivity(intent);
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }

}
