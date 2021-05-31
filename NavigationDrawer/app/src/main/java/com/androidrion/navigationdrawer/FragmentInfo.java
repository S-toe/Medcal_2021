package com.androidrion.navigationdrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentInfo extends Fragment {


    public FragmentInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        WebView infoWebView;

        View v = inflater.inflate(R.layout.fragment_info, container,false);
        infoWebView = (WebView) v.findViewById(R.id.infowv);
        infoWebView.loadUrl("https://www.alodokter.com/obat-a-z");

        WebSettings webSettings = infoWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        infoWebView.setWebViewClient(new WebViewClient());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Info");
    }
}

