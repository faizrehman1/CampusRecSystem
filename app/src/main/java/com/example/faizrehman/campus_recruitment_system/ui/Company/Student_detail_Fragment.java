package com.example.faizrehman.campus_recruitment_system.ui.Company;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.faizrehman.campus_recruitment_system.Adapter.Student_Detail_Adapter;
import com.example.faizrehman.campus_recruitment_system.Model.Profile_Model;
import com.example.faizrehman.campus_recruitment_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by faiz on 1/26/2017.
 */

public class Student_detail_Fragment extends android.support.v4.app.Fragment {

    ArrayList<Profile_Model> profile_models;
    Student_Detail_Adapter adapter ;
    ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String checkUser;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_profiles,null);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        profile_models = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        checkUser = sharedPreferences.getString("TAG","");
        editor = sharedPreferences.edit();
        listView = (ListView)view.findViewById(R.id.profile_list);
        adapter = new Student_Detail_Adapter(profile_models,getActivity());
        listView.setAdapter(adapter);


        myRef.child("Std-Profiles").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                  Profile_Model profile_modelObject = dataSnapshot1.getValue(Profile_Model.class);
                    profile_models.add(new Profile_Model(profile_modelObject.getFname(),profile_modelObject.getLname(),profile_modelObject.getEmail(),profile_modelObject.getContactno(),profile_modelObject.getAddress(),profile_modelObject.getSsc(),profile_modelObject.getFsc(),profile_modelObject.getUniversity(),profile_modelObject.getGender(),profile_modelObject.getSscyear(),profile_modelObject.getHscyear(),profile_modelObject.getDpt()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(checkUser.matches("admin")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Remove");
                    builder.setMessage("You want tO Delete..?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myRef.child("Std-Profiles").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int i = 0;
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        if (i == position) {
                                            DatabaseReference ref = dataSnapshot1.getRef();
                                            ref.removeValue();
                                            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                            profile_models.remove(position);
                                            adapter.notifyDataSetChanged();
                                        }
                                        i++;
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });
                        }
                    });
                    builder.setNegativeButton("Back", null);
                    builder.show();

                }
                }

            });

        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
