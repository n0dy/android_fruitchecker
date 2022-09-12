package com.example.fruitchecker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;


public class ByvalueFragment extends Fragment {

    Spinner nutrtitionSpinner;
    Button btnSubmit;
    TextView textDisplay;
    EditText editTextMin, editTextMax;
    ListView listView;


    //currency fragment constructor
    public static ByvalueFragment newInstance(String param1, String param2)
    {
        ByvalueFragment fragment = new ByvalueFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_byvalue, container, false);
        //initialize values
        nutrtitionSpinner = view.findViewById(R.id.nutrtitionSpinner); //drop down menu
        btnSubmit = view.findViewById(R.id.btnSubmit);
        textDisplay = view.findViewById(R.id.textDisplay);
        editTextMax =  view.findViewById(R.id.editTextMax);
        editTextMin = view.findViewById(R.id.editTextMin);
        listView = view.findViewById(R.id.listView);
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        //set drop down menu
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(), R.array.nutrition, android.R.layout.simple_spinner_item);
        nutrtitionSpinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        final String[] base = {""};
        nutrtitionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                base[0] = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            //on click event
            public void onClick(View v)
            {
                String s = editTextMax.getText().toString();
                String url = "https://www.fruityvice.com/api/fruit/" + base[0] + "?min=" + editTextMin.getText() + "&max=" + editTextMax.getText();
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>()
                {
                    public void onResponse(JSONArray response)
                    {
                        try
                        {

                            List<FruitModel> allFruits = new ArrayList<>();

                            //loop for each fruit in the response
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
                    //handle errors
                    public void onErrorResponse(VolleyError error)
                    {
                        listView.setAdapter(null);
                        textDisplay.setText("Error loading data. Chek min and max values." );
                    }
                });
                queue.add(request);
            }
        });
        return view;
    }
}