package com.google.firebase.codelab.friendlychat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.dto.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseTestActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabaseReference;
    private Button sendBtn;
    private Button getBtn;
    private EditText productName;
    private TextView productResult;

    private DatabaseReference dr;

    private DatabaseReference productInfo;
    public static final String SOMETHINGS = "test/somethings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        sendBtn = (Button) findViewById(R.id.sendInfo);
        getBtn = (Button) findViewById(R.id.getInfo);
        productName = (EditText) findViewById(R.id.productInfo);
        productResult = (TextView) findViewById(R.id.productResult);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        dr = mFirebaseDatabaseReference.child(SOMETHINGS);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                product.setId("b");
                product.setName(productName.getText().toString());
                product.setPrice(1000);
                product.setType("fruits");

                DatabaseReference productInfoList = dr.push();
                productInfoList.setValue(product);
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                DatabaseReference productInfoResult = dr.child(productInfo.getKey());
//
//                productInfoResult.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Product product = dataSnapshot.getValue(Product.class);
//                        Log.i("ohdoking",dr.push().getKey() + "!!");
//                        productResult.setText(product.getName());
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        System.out.println("The read failed: " + databaseError.getCode());
//                    }
//                });

            }
        });

        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Product product = dataSnapshot.getValue(Product.class);
                productResult.setText(product.getName());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
