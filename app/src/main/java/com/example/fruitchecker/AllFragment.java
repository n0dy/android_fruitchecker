package com.example.fruitchecker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class AllFragment extends Fragment
{
    Button btnSubmit;
    TextView textDisplay;
    ListView listView;

    public AllFragment()
    {

    }

    public static AllFragment newInstance(String param1, String param2) {
        AllFragment fragment = new AllFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        btnSubmit = view.findViewById(R.id.btnSubmit);
        listView = view.findViewById(R.id.listView);
        textDisplay = view.findViewById(R.id.textDisplay);

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "https://www.fruityvice.com/api/fruit/all";
                textDisplay.setText(" ");

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    public void onResponse(JSONArray response)
                    {
                        try
                        {
                            List<FruitModel> allFruits = new ArrayList<>();
                            for(int i = 0; i< response.length();i++){
                                FruitModel fruitModel = new FruitModel();
                                JSONObject object  = response.getJSONObject(i);
                                fruitModel.setId(object.getString("name"));

                                String nutritions  = object.getString("nutritions");
                                JSONObject object1 = new JSONObject(nutritions);
                                fruitModel.setCalories(object1.getString("calories"));
                                fruitModel.setCarbohydrates(object1.getString("carbohydrates"));
                                fruitModel.setProtein(object1.getString("protein"));
                                fruitModel.setFat(object1.getString("fat"));
                                fruitModel.setSugar(object1.getString("sugar"));

                                allFruits.add(fruitModel);
                            }

                            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, allFruits);
                            listView.setAdapter(arrayAdapter);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
                {
                    public void onErrorResponse(VolleyError error) {
                        listView.setAdapter(null);
                        textDisplay.setText("Error loading data." + error.toString());
                    }
                });

                queue.add(request);
            }
        });

        return view;
    }
}