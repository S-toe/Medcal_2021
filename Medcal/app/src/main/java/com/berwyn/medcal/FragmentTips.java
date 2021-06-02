package com.berwyn.medcal;

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

public class FragmentTips extends Fragment {


    public FragmentTips() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        WebView tipsWebView;

        View v = inflater.inflate(R.layout.fragment_tips, container,false);
        tipsWebView = (WebView) v.findViewById(R.id.tipswv);
        tipsWebView.loadUrl("https://www.who.int/philippines/news/feature-stories/detail/20-health-tips-for-2020");

        WebSettings webSett = tipsWebView.getSettings();
        webSett.setJavaScriptEnabled(true);

        tipsWebView.setWebViewClient(new WebViewClient());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tips");
    }
}

