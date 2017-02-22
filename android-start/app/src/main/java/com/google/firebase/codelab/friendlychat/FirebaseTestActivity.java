package com.google.firebase.codelab.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.codelab.friendlychat.dto.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseTestActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabaseReference;

    public static final String SOMETHINGS = "test/somethings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference dr = mFirebaseDatabaseReference.child(SOMETHINGS);

        Product product = new Product();
        product.setId("a");
        product.setName("apple");
        product.setPrice(1000);
        product.setType("fruits");

        dr.setValue(product);

    }
}
