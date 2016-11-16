package com.gonuvem.gorent;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by orlandoamorim on 15/11/16.
 */

public class FireControl {

    public FireControl() {}

    private static final String TAG = "FireControl";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("car");

    public void writeNewCar(Car car){
        myRef = database.getReference("message");
        String key = myRef.child("carros").push().getKey();
        Map<String, Object> carValues = car.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/carros/" + key, carValues);
        myRef.updateChildren(childUpdates);
    }

    public ArrayList<Car> retrive_all_cars(){
        myRef = database.getReference("message/carros/");
        final ArrayList<Car> list_car = new ArrayList<Car>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot carroSnap : dataSnapshot.getChildren()){
                    Car car = carroSnap.getValue(Car.class);
                    list_car.add(car);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        });

        return list_car;
    }

}
