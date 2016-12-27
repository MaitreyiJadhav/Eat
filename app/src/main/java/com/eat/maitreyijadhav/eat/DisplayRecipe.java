package com.eat.maitreyijadhav.eat;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
//displays recipe on the activity
public class DisplayRecipe extends AppCompatActivity {

    ImageView imageToDisplay;
    TextView originType, discription;
    String objectArray[];
    String foodType;
    int activityToLaunch = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout to the display
        setContentView(R.layout.activity_display_recipe);

        //provides navigation icon on the action bar
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        //recieves intent from main activity navigation for recipe array
        Intent intent = getIntent();
        objectArray = intent.getStringArrayExtra("RecipeArray");
        foodType = objectArray[4];

        //check what type of food recipe to be displayed- starters, desserts, main course
        if(foodType.equals("Starter")){
            activityToLaunch = 1;
        }else if(foodType.equals("Dessert")){
            activityToLaunch = 2;
        }
        else if(foodType.equals("Main Course")){
            activityToLaunch = 3;
        }

        //initiates views with its ID
        imageToDisplay = (ImageView) findViewById(R.id.displayImage);
        originType = (TextView)findViewById(R.id.originType);
        discription = (TextView) findViewById(R.id.displayRecipe);
        String data = objectArray[0] + "\n"+"Origin: "+objectArray[3] + "\n"+"Type: "+objectArray[4];
        originType.setText(data);
        discription.setText(objectArray[1]);

        //Assigns image view with image url and loads it with the help of picasso library
        try {
            Picasso.with(this).load(objectArray[2]).into(imageToDisplay);
        }catch (Exception e){
            imageToDisplay.setBackgroundResource(R.mipmap.foodieslogo);
        }

    }

    //when user clicks on navigation icon it will take useer back to appropriate recipe fragment

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ListViewForRecipes.class);
                intent.putExtra("typeOfFood", objectArray[4]);
                intent.putExtra("CousinName", objectArray[3]);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
