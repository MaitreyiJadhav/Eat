package com.eat.maitreyijadhav.eat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
//create custom activity that holds custom recyclable list  views

public class ListViewForRecipes extends AppCompatActivity {
    //Declare variable to hold data
    private ArrayList<String> itemName;
    private ArrayList<String> origin;
    ArrayList<Bitmap> bitmapArray;
    ArrayList<String> discription;
    ArrayList<String> urlOfRecipe;
    private ListView list;
    String IMAGE_URL = "";
    String arrayOfRecipeObjects[];
    String cousingTypes[];
    String foodToLaunch;
    static String foodType;
    CustomListAdapter adapter;
    int activityToLaunch;
    private ProgressDialog progressDialog;
    Firebase fireBaseReference;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initiate views arrays and set navigation icon to action bar
        progressDialog = new ProgressDialog(this);
        arrayOfRecipeObjects = new String [5];
        setContentView(R.layout.activity_list_view_for_recipes);
        cousingTypes = getResources().getStringArray(R.array.differentcuisines);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        //check what type of food recipe to be displayed- starters, desserts, main course

        Intent intent = getIntent();
        foodToLaunch = intent.getStringExtra("CousinName");
        foodType = intent.getStringExtra("typeOfFood");
        if(foodType.equals("Starter")){
            activityToLaunch = 1;
        }else if(foodType.equals("Dessert")){
            activityToLaunch = 2;
        }
        else if(foodType.equals("Main Course")){
            activityToLaunch = 3;
        }

        list = (ListView) findViewById(R.id.list);
        Firebase.setAndroidContext(this);
        bitmapArray = new ArrayList<Bitmap>();
        discription = new ArrayList<String>();
        urlOfRecipe = new ArrayList<String>();
        itemName = new ArrayList<String>();
        origin  = new ArrayList<String>();

        //gets the reference of the database in the firebase recipes
        fireBaseReference =  new Firebase("https://foodies-2f4d9.firebaseio.com/Recipes");

        //seperate thread to perform loading of data from database into the array list
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }

            //on progress update sets data to the different views that has been fetched from database

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                adapter = new CustomListAdapter(ListViewForRecipes.this, itemName, origin, bitmapArray);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);

            }
            //data recieved from firebase is parsed into recipe obj and individual elements from the recipe obj are added to desired ArrayList

            @Override
            protected Void doInBackground(Void... params) {

                fireBaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int i  =0;
                        //fetches each child obj from firebase database and parse it into recipe obj

                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            Recipe recipe = postSnapshot.getValue(Recipe.class);
                            String foodTypeholder = recipe.getTypeOfRecipe().toString();
                            String originOfRecipeHolder = recipe.getOriginOfRecipe().toString();

                            //checks recipe type- starters, main, desserts and displays it accordingly
                            if(foodTypeholder.equals(foodType) && originOfRecipeHolder.equals(foodToLaunch)){
                                itemName.add(recipe.getNameOfRecipe());
                                origin.add(recipe.getOriginOfRecipe());
                                discription.add(recipe.getDescription());
                                urlOfRecipe.add(recipe.getImageURL());
                                IMAGE_URL = recipe.getImageURL();
                                try{
                                    publishProgress(0);
                                    URL url = new URL(IMAGE_URL);
                                    //connect http and url and fetch data
                                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                    connection.setDoInput(true);
                                    connection.connect();
                                    InputStream input = connection.getInputStream();
                                    //converts image to bitmap
                                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                                    bitmapArray.add(bitmap);
                                }
                                //if image is not loaded then display default
                                catch(Exception E){
                                    Bitmap bitmap =BitmapFactory.decodeResource(getResources(),R.mipmap.foodieslogo);
                                    bitmapArray.add(bitmap);
                                }
                                publishProgress(0);
                            }
                            publishProgress(0);
                        }
                        progressDialog.dismiss();
                    }

                    //if firebase connection fails throws exception
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
                publishProgress(0);
                return null;
            }
        }.execute();
        adapter = new CustomListAdapter(ListViewForRecipes.this, itemName, origin, bitmapArray);

        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        //when user clicks on the item in the list, the selected item data is saved into array and parsed from one intent to other
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(itemName.size() > 0 && discription.size() >0 && urlOfRecipe.size() > 0 && origin.size() > 0){
                arrayOfRecipeObjects[0] = itemName.get(position);
                arrayOfRecipeObjects[1] = discription.get(position);
                arrayOfRecipeObjects[2] = urlOfRecipe.get(position);
                arrayOfRecipeObjects[3] = origin.get(position);
                arrayOfRecipeObjects[4] = foodType;}

                Intent openDisplayRecipe = new Intent(ListViewForRecipes.this, DisplayRecipe.class);
                openDisplayRecipe.putExtra("RecipeArray", arrayOfRecipeObjects);
                startActivity(openDisplayRecipe);
             }

        });

    }

    //finish activity if app closed
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    //checks from navigation icon in action bar it directs user to appropriate fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivityNavigation.class);
                intent.putExtra("StartFragmentNUmber",activityToLaunch);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
