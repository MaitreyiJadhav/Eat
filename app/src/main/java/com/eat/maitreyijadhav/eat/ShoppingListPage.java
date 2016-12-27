package com.eat.maitreyijadhav.eat;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ShoppingListPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnAdd;
    private Button deleteItems;
    private EditText et;
    private ListView lv;
    String filename = "mysecondfile";
    String outputString = "";
    ArrayList<String> list = new ArrayList<String>();
    static ArrayAdapter<String> adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list_page, container, false);
        btnAdd = (Button) view.findViewById(R.id.addTaskBtn);
        et = (EditText)view.findViewById(R.id.editText);
        deleteItems = (Button) view.findViewById(R.id.deleteButton);

        if(!checkIfFileExist(filename)){
            try {
                FileOutputStream outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(outputString.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                FileInputStream inputStream = getActivity().openFileInput(filename);
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    list.add(line);
                }
                r.close();
                inputStream.close();
                Log.d("File", "File contents: " + total);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_expandable_list_item_1, list);

        // set the lv variable to your list in the xml
        lv=(ListView)view.findViewById(R.id.list);
        lv.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = et.getText().toString();
                outputString += input+"\n";
                if(input.length() > 0)
                {
                    // add string to the adapter, not the listview
                    adapter.add(input);
                    // no need to call adapter.notifyDataSetChanged(); as it is done by the adapter.add() method
                    try {
                        FileOutputStream outputStream = getActivity().openFileOutput(filename, Context.MODE_APPEND);
                        outputStream.write(outputString.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    et.setText("");
                    et.getText().clear();
                }
            }
        });
        deleteItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                String empty = "";
                try {
                    FileOutputStream outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(empty.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    /*
   *  Check if the file exists
   * */
    public boolean checkIfFileExist(String fileName) {
        File file = getActivity().getFileStreamPath(fileName);
        return file.exists();
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
