package com.example.customerlist;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.customerlist.Adapter.CustomerListAdapter;
import com.example.customerlist.Model.CustomerDetails;
import com.example.customerlist.Room.CustomerInformation;
import com.example.customerlist.Room.DatabaseClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static String url = "https://api.birdeye.com/resources/v1/customer/all?businessId=154297123507363&api_key=uu7qrdki2HkQ7H7yf3diI92dkQ6uy7Hd";
    private RecyclerView recyclerView;
    ImageView img_add_customer;
    private CustomerListAdapter customerListAdapter;
    RequestQueue requestQueue;
    List<CustomerDetails> customerDetailsList;
    private ArrayList<CustomerDetails> customerDetailsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_imageview, null);



        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddCustomer.class);
                startActivity(intent);
            }
        });

        actionBar.setCustomView(v);

        recyclerView = findViewById(R.id.recyclerView);
        customerDetailsArrayList = new ArrayList<>();
        customerListAdapter = new CustomerListAdapter(this,customerDetailsArrayList);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(customerListAdapter);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            fetchFromServer();
        } else {

            fetchFromRoom();

        }

    }

    private void fetchFromRoom() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<CustomerInformation> customerList = DatabaseClient.getInstance(MainActivity.this).getAppDatabase().customerDao().getAll();
                customerDetailsArrayList.clear();

                for (CustomerInformation customerInformation : customerList) {
                    CustomerDetails customerDetails_locally = new CustomerDetails(customerInformation.getId(), customerInformation.getFirst_name()
                            , customerInformation.getLast_name()
                            , customerInformation.getEmail_Id()
                            , customerInformation.getPhone_number());
                    customerDetailsArrayList.add(customerDetails_locally);
                }
                // refreshing recycler view
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customerListAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
        thread.start();
    }

    public void fetchFromServer() {
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        customerDetailsList = new Gson().fromJson(response.toString(), new TypeToken<List<CustomerDetails>>() {
                        }.getType());

                        // adding data to  list
                        customerDetailsArrayList.clear();
                        customerDetailsArrayList.addAll(customerDetailsList);


                        // refreshing recycler view
                        customerListAdapter.notifyDataSetChanged();


                        saveTask();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<String, String>();
                //   HashMap headers = new HashMap();
                params.put("Postman-Token","93246e9e-1ab1-4bc2-ab49-dbae1504798d,f15d8cb8-6711-463a-bf40-8caead536dea");
                params.put("Content-Type","application/json");
                params.put("Host","api.birdeye.com");
                params.put("Accept","application/json");
                params.put("Cache-Control","no-cache");
                params.put("Connection","keep-alive");
                return params;
            }
        };

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        request.setShouldCache(false);

        requestQueue.add(request);
    }

    public void  saveTask(){

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                for (int i = 0; i < customerDetailsList.size(); i++) {
                    CustomerInformation customerInformation= new CustomerInformation();
                    customerInformation.setFirst_name(customerDetailsList.get(i).getFirstName());
                    customerInformation.setFirst_name(customerDetailsList.get(i).getLastName());
                    customerInformation.setEmail_Id(customerDetailsList.get(i).getEmailId());
                    customerInformation.setPhone_number(customerDetailsList.get(i).getNumber());
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().customerDao().insert(customerInformation);
                }
                return null;
            }

            @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        }
    }

    SaveTask st = new SaveTask();
        st.execute();
}

    }





