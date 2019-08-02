package com.example.customerlist.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customerlist.Model.CustomerDetails;
import com.example.customerlist.R;
import com.example.customerlist.Room.CustomerInformation;
import com.example.customerlist.Room.DatabaseClient;

import java.util.ArrayList;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {
    Context context;
    List<CustomerDetails> customerDetail;

    public CustomerListAdapter(Context context, List<CustomerDetails> customerDetail) {
        this.context = context;
        this.customerDetail = customerDetail;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_details, viewGroup, false);
      // MyViewHolder holder = new MyViewHolder(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(  MyViewHolder myViewHolder, int i) {

        CustomerDetails customerDetails = customerDetail.get(i);
        String email = customerDetails.getEmailId();
        String name = email.substring(0,email .indexOf("@"));

        myViewHolder.text_name.setText(name);
        myViewHolder.text_emailId.setText(customerDetails.getEmailId());
        myViewHolder.text_phone_number.setText(customerDetails.getNumber());


    }

    @Override
    public int getItemCount() {
        return customerDetail.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
       public TextView text_name,text_emailId,text_phone_number;


        public MyViewHolder(View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.textView_customer_name_data);
            text_emailId = itemView.findViewById(R.id.textView_emailId_data);
            text_phone_number = itemView.findViewById(R.id.textView_phone_number_data);
        }
    }
}
