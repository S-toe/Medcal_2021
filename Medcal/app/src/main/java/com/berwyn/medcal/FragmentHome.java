package com.berwyn.medcal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class FragmentHome extends Fragment {

    ImageView imgview;
    Button btnTP;
    Button btnScn;
    Button btnSetAl;
    Uri imageUri;
    static int PreqCode = 1;
    final int galery = 100;
    public static final int RESULT_OK = -1;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


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


        btnTP = (Button) view.findViewById(R.id.btntakepic);
        imgview = (ImageView) view.findViewById(R.id.hasilfoto);

        btnTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tp = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(tp, galery);
            }
        });

        /*
        imgview = view.findViewById(R.id.hasilfoto);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galery && resultCode == RESULT_OK){
            imageUri = data.getData();
            imgview.setImageURI(imageUri);
        }
    }
}