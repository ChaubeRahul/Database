package com.example.rahul.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    Button button;
    RecyclerView recyclerView;
    AdapterDemo adapterDemo;
    List<String> list=new ArrayList<>();
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         recyclerView= (RecyclerView) findViewById(R.id.recyclerviewDemo);
        textView= (TextView) findViewById(R.id.textview);
        editText= (EditText) findViewById(R.id.editText);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");
        DatabaseReference msg=myRef.child("my");
        final DatabaseReference timeStamp=msg.child("timestamp");
       final DatabaseReference child= msg.child("xyz");

        timeStamp.setValue(new Date().toString());
        button= (Button) findViewById(R.id.btnSend);
         adapterDemo =new AdapterDemo(list);

         final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
         layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setAdapter(adapterDemo);
         recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
         recyclerView.setHasFixedSize(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                child.push().setValue(editText.getText().toString());
                timeStamp.setValue(System.currentTimeMillis());
                editText.setText("");
            }
        });

     // Read from the database
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data :
                        dataSnapshot.getChildren()) {
                    Log.d("_)_)", (String) data.getValue());
                    textView.setText((String) data.getValue());
                    list.add((String) data.getValue());
                }
                adapterDemo.setFilter(list);
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                adapterDemo.notifyDataSetChanged();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("********************", "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("********************", "Failed to read value.", error.toException());
            }
        });
    }
}
