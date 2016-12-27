package com.eat.maitreyijadhav.eat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//Fragment to hold main course recipes
public class MainCoursePage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String cousingTypes[];
    private ListView list;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_starters_page, container, false);
        list = (ListView) view.findViewById(R.id.list);
        //creating Adapter for a list of origin names

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.differentcousins));
        //CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemName, origin, bitmapArray);
        list.setAdapter(adapter);
        cousingTypes = getResources().getStringArray(R.array.differentcousins);
        //set on item click listener on the list

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), ListViewForRecipes.class);
                intent.putExtra("CousinName", cousingTypes[position]);
                intent.putExtra("typeOfFood", "Main Course");
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
    class MyAsyncTask extends AsyncTask<String, String, String> {


        protected String doInBackground(String... params) {

            return null;
        }
    }

}
