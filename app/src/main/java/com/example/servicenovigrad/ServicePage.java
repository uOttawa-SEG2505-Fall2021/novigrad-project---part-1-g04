package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class ServicePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_page);
    }

    private void addService() {
//        //getting the values to save
//        String name = editTextName.getText().toString().trim();
//        double price = Double.parseDouble(editTextPrice.getText().toString());
//
//        // checking if the value is provided
//        if (!TextUtils.isEmpty(name)) {
//
//            //getting a unique id using push().getKey() method
//            //it will create a unique id and we will use it as the Primary key for our Product
//            String id = databaseProducts.push().getKey();
//
//            //creating a Product Object
//            Product product = new Product(id, name, price);
//            // Saving the Product
//            assert id != null;
//            databaseProducts.child(id).setValue(product);
//
//            // setting edittext to blank again
//            editTextName.setText("");
//            editTextPrice.setText("");
//
//            //displaying a success toast
//            Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
//        } else {
//            //if the value is not given displaying a toast
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
//
//        }
    }
}