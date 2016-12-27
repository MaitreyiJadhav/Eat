package com.eat.maitreyijadhav.eat;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class HomePage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Random random = new Random();
    private ArrayList<String> itemName;
    private ArrayList<String> origin;
    ArrayList<String> typeOfRecipe;
    ArrayList<String> discription;
    ArrayList<String> urlOfRecipe;
    private ImageView imageView;
    TextView originRecipe, description, nameTodisplay;
    Firebase fireBaseReference;
    long randomNumber;
    int numberToDisplay;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        Firebase.setAndroidContext(getActivity());
        typeOfRecipe = new ArrayList<String>();
        discription = new ArrayList<String>();
        urlOfRecipe = new ArrayList<String>();
        itemName = new ArrayList<String>();
        origin = new ArrayList<String>();
        imageView = (ImageView) view.findViewById(R.id.imageToDispaly);
        nameTodisplay = (TextView) view.findViewById(R.id.nameOfRecipe);
        originRecipe = (TextView) view.findViewById(R.id.typrOrigin);
        description = (TextView) view.findViewById(R.id.discriptionOfREcipe);
        fireBaseReference = new Firebase("https://foodies-2f4d9.firebaseio.com/Recipes");
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                originRecipe.setText(origin.get(numberToDisplay));
                nameTodisplay.setText(itemName.get(numberToDisplay));
                description.setText(discription.get(numberToDisplay));
                try {
                    Picasso.with(getActivity()).load(urlOfRecipe.get(numberToDisplay)).into(imageView);
                }catch (Exception e){
                    imageView.setBackgroundResource(R.mipmap.foodieslogo);
                }

            }

            @Override
            protected Void doInBackground(Void... params) {

                fireBaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int i = 0;
                        randomNumber = snapshot.getChildrenCount();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Recipe recipe = postSnapshot.getValue(Recipe.class);
                            typeOfRecipe.add(recipe.getTypeOfRecipe());
                            discription.add(recipe.getDescription());
                            urlOfRecipe.add(recipe.getImageURL());
                            itemName.add(recipe.getNameOfRecipe());
                            origin.add(recipe.getOriginOfRecipe());

                        }

                        numberToDisplay = random.nextInt(((int)randomNumber ));
                       publishProgress(0);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
                return null;
            }
        }.execute();
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

    class MyAsyncTask extends AsyncTask<String, String, String> {


        protected String doInBackground(String... params) {

            return null;
        }
    }
}
