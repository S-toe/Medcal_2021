package com.androidrion.navigationdrawer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class FragmentHome extends Fragment {


    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ImageView imgview;
        Button btnTP;
        Button btnScn;
        Button btnSetAl;
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity().getApplication(), NewMedsActivity.class);
                startActivity(intent);

                /*
                Fragment frag = new FragmentNewMeds();
                FragmentTransaction Ft = getFragmentManager().beginTransaction();

                Ft.replace(R.id.nav_home, frag);
                Ft.addToBackStack(null);

                Ft.commit();

                 */

            }
        });




        /*
        imgview = view.findViewById(R.id.hasilfoto);
        btnTP = view.findViewById(R.id.btntakepic);
        btnScn = view.findViewById(R.id.btnscan);
        btnSetAl = view.findViewById(R.id.btnset);

        if (ContextCompat.checkSelfPermission(FragmentHome.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FragmentHome.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

         */
        // return v;


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }
}