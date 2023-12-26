package com.trodev.scanhub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trodev.scanhub.R;
import com.trodev.scanhub.adapters.EmailAdapter;
import com.trodev.scanhub.adapters.LocationAdapter;
import com.trodev.scanhub.models.EmailModel;
import com.trodev.scanhub.models.LocationModel;

import java.util.ArrayList;


public class LocationFragment extends Fragment {
    private RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<LocationModel> list;
    LocationAdapter adapter;
    LottieAnimationView animationView;

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_location, container, false);
        reference = FirebaseDatabase.getInstance().getReference("QR_DB");

        /*init views*/
        recyclerView = view.findViewById(R.id.locRv);
        animationView = view.findViewById(R.id.animationView);
        animationView.loop(true);

        /*create methods*/
        load_data();

        return view;

    }
    private void load_data() {

        Query query = reference.child("location_qr").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    animationView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    animationView.setVisibility(View.GONE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        LocationModel data = snapshot.getValue(LocationModel.class);
                        list.add(0, data);

                    }
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    LocationAdapter  adapter = new LocationAdapter(getContext(), list, "location_qr");
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getContext(), "location data found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}