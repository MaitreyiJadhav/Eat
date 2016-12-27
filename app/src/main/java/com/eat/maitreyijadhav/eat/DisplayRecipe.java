package com.eat.maitreyijadhav.eat;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayRecipe extends AppCompatActivity {

    ImageView imageToDisplay;
    TextView originType, discription;
    String objectArray[];
    String foodType;
    int activityToLaunch = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        objectArray = intent.getStringArrayExtra("RecipeArray");
        /*arrayOfRecipeObjects[0] = itemName.get(position);
        arrayOfRecipeObjects[1] = discription.get(position);
        arrayOfRecipeObjects[2] = urlOfRecipe.get(position);
        arrayOfRecipeObjects[3] = origin.get(position);
        arrayOfRecipeObjects[4] = foodType;*/
        foodType = objectArray[4];
        if(foodType.equals("Starter")){
            activityToLaunch = 1;
        }else if(foodType.equals("Dessert")){
            activityToLaunch = 2;
        }
        else if(foodType.equals("Main Course")){
            activityToLaunch = 3;
        }

        imageToDisplay = (ImageView) findViewById(R.id.displayImage);
        originType = (TextView)findViewById(R.id.originType);
        discription = (TextView) findViewById(R.id.displayRecipe);
        String data = objectArray[0] + "\n"+"Origin: "+objectArray[3] + "\n"+"Type: "+objectArray[4];
        originType.setText(data);
        discription.setText(objectArray[1]);
        try {
            Picasso.with(this).load(objectArray[2]).into(imageToDisplay);
        }catch (Exception e){
            imageToDisplay.setBackgroundResource(R.mipmap.foodieslogo);
        }

    }

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
