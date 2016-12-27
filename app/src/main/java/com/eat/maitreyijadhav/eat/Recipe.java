package com.eat.maitreyijadhav.eat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Nero on 26/11/2016.
 */
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
