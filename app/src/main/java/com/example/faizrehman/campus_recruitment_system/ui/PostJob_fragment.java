package com.example.faizrehman.campus_recruitment_system.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faizrehman.campus_recruitment_system.R;

/**
 * Created by faiz on 1/26/2017.
 */

public class PostJob_fragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.postjob_view,null);

        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
