package com.eat.maitreyijadhav.eat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Maitreyi on 12/12/2016.
 */

//Recipe is a helper class that holds parsed objects from database
@JsonIgnoreProperties(ignoreUnknown=true)
public class Recipe {

    public String NameOfRecipe;
    public String TypeOfRecipe;
    public String ImageURL;
    public String OriginOfRecipe;
    public String Description;

    @JsonIgnoreProperties(ignoreUnknown=true)
    protected String getNameOfRecipe() {
        return NameOfRecipe;
    }


    @JsonIgnoreProperties(ignoreUnknown=true)
    protected String getTypeOfRecipe() {
        return TypeOfRecipe;
    }


    @JsonIgnoreProperties(ignoreUnknown=true)
    protected  String getImageURL() {
        return ImageURL;
    }
    @JsonIgnoreProperties(ignoreUnknown=true)
    protected String getOriginOfRecipe() {
        return OriginOfRecipe;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    protected String getDescription() {
        return Description;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public Recipe(String name, String type, String image, String origin, String descr) {
        NameOfRecipe = name;
        TypeOfRecipe = type;
        ImageURL = image;
        OriginOfRecipe = origin;
        Description = descr;
    }

    public Recipe() {
    }
}
