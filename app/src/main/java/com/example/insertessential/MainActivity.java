package com.example.insertessential;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText areaName , categoryName , shopName , shopAddress , shopContact , shopImage;
    Button submit;

    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        areaName = findViewById(R.id.areaName);
        categoryName = findViewById(R.id.categoryName);
        shopName = findViewById(R.id.shopName);
        shopAddress = findViewById(R.id.shopAddress);
        shopContact = findViewById(R.id.shopContact);
        shopImage = findViewById(R.id.shopImage);

        firebaseDatabase = FirebaseDatabase.getInstance();

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String areaNameStr = areaName.getText().toString();
                String categoryNameStr = categoryName.getText().toString();
                String shopNameStr = shopName.getText().toString();
                final String shopAddressStr = shopAddress.getText().toString();
                String shopContactStr = shopContact.getText().toString();
                String shopImageStr = shopImage.getText().toString();

                if(!areaNameStr.isEmpty())
                {
                    if(!categoryNameStr.isEmpty())
                    {
                        if(!shopNameStr.isEmpty())
                        {
                            if(!shopAddressStr.isEmpty())
                            {
                                if(!shopContactStr.isEmpty())
                                {
                                    databaseReference = firebaseDatabase.getReference().child(areaNameStr).child(categoryNameStr);

                                    shopDetails details = new shopDetails(shopNameStr , shopContactStr , shopAddressStr , shopImageStr);

                                    databaseReference.child(shopNameStr).setValue(details).addOnCompleteListener(task -> {
                                        Toast.makeText(MainActivity.this , "Record Added " , Toast.LENGTH_LONG).show();
                                        shopName.setText("");
                                        shopAddress.setText("");
                                        shopContact.setText("");
                                        shopImage.setText("");
                                    }).addOnFailureListener(e-> Toast.makeText(MainActivity.this , "Record Not Added " , Toast.LENGTH_LONG).show());
                                }
                            }else
                            {
                                shopContact.setError("Null Address");
                            }
                        }else
                        {
                            shopContact.setError("Null Name");
                        }
                    }else
                    {
                        shopContact.setError("Null Category");
                    }
                }else {
                    shopContact.setError("Null Area Name");
                }
            }
        });
    }
}