package com.example.fruitchecker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BynameFragment extends Fragment
{
    Spinner baseSpinner;
    Button btnSubmit;
    TextView textDisplay;


    public static BynameFragment newInstance(String param1, String param2)
    {
        BynameFragment fragment = new BynameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_byname, container, false); //view - prozor

        //initialize values
        baseSpinner = view.findViewById(R.id.baseSpinner); //drop down menu
        btnSubmit = view.findViewById(R.id.btnSubmit);
        textDisplay = view.findViewById(R.id.textDisplay);
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        //set drop down menu
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(getContext(), R.array.fruits, android.R.layout.simple_spinner_item);
        baseSpinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        final String[] base = {""};
        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                base[0] = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            //on click event
            public void onClick(View v)
            {
                String url = "https://www.fruityvice.com/api/fruit/" + base[0];
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
                {
                    public void onResponse(JSONObject response) //sta se desava na response od api-a
                    {
                        try
                        {

                            FruitModel fruitModel = new FruitModel();
                            //get name
                            fruitModel.setId(response.getString("name"));

                            //get nutrition values
                            String nutritions  = response.getString("nutritions");
                            JSONObject object1 = new JSONObject(nutritions);
                            fruitModel.setCalories(object1.getString("calories"));
                            fruitModel.setCarbohydrates(object1.getString("carbohydrates"));
                            fruitModel.setProtein(object1.getString("protein"));
                            fruitModel.setFat(object1.getString("fat"));
                            fruitModel.setSugar(object1.getString("sugar"));


                            //display text
                            textDisplay.setText(fruitModel.toString());

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
                {
                    //handle errors
                    public void onErrorResponse(VolleyError error)
                    {
                        textDisplay.setText("Error loading data. " + error.toString());
                    }
                });
                queue.add(request);
            }
        });
        return view;
    }
}