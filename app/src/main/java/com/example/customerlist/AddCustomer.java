package com.example.customerlist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.customerlist.Room.CustomerInformation;
import com.example.customerlist.Room.DatabaseClient;

public class AddCustomer extends AppCompatActivity {
    private EditText editTextFirstName,editTextLastName, editTextEmailId, editTextPhoneNumber;
    Button Check_IN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_customer_details);

        editTextFirstName = findViewById(R.id.editText_first_name);
        editTextLastName = findViewById(R.id.editText_last_name);
        editTextEmailId = findViewById(R.id.editText_customer_emailId);
        editTextPhoneNumber = findViewById(R.id.editText_customer_phone_number);

        findViewById(R.id.btn_add_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCustomer();
            }
        });

    }
    private void saveCustomer() {
        final String first_name = editTextFirstName.getText().toString().trim();
        final String last_name = editTextLastName.getText().toString().trim();
        final String email_id = editTextEmailId.getText().toString().trim();
        final String phone_number = editTextPhoneNumber.getText().toString().trim();

        if (first_name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "First Name is Empty", Toast.LENGTH_LONG).show();
            return;
        } else if (last_name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Last Name is Empty", Toast.LENGTH_LONG).show();
            return;
        } else if (email_id.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email Id is Empty", Toast.LENGTH_LONG).show();
            return;
        } else if (phone_number.isEmpty()) {
            Toast.makeText(getApplicationContext(), " Phone Number is Empty", Toast.LENGTH_LONG).show();
            return;
        }


            class SaveTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                        CustomerInformation customerInformation= new CustomerInformation();
                        customerInformation.setFirst_name(first_name);
                        customerInformation.setLast_name(last_name);
                        customerInformation.setEmail_Id(email_id);
                        customerInformation.setPhone_number(phone_number);
                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().customerDao().insert(customerInformation);

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
