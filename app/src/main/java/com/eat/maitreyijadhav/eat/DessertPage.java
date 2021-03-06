package com.eat.maitreyijadhav.eat;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//fragment to hold Desserts Recipes
public class DessertPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    String cuisineTypes[];
    private ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_starters_page, container, false);
        list = (ListView) view.findViewById(R.id.list);

        //creating Adapter for a list of origin names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, getResources().
                getStringArray(R.array.differentcuisines));
        list.setAdapter(adapter);
        cuisineTypes = getResources().getStringArray(R.array.differentcuisines);
        //set on item click listener on the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), ListViewForRecipes.class);
                intent.putExtra("CousinName", cuisineTypes[position]);
                intent.putExtra("typeOfFood", "Dessert");
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
