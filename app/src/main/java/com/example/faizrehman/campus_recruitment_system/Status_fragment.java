package com.example.faizrehman.campus_recruitment_system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by faizrehman on 1/25/17.
 */

public class Status_fragment extends android.support.v4.app.Fragment {

    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_view,null);
        super.onCreateView(inflater, container, savedInstanceState);
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.add_app);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
