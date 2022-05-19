//package com.smproject.yourcityradio;
//
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//
//public class WebViewFragment extends Fragment {
//
//    private WebView mWebView;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable  ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view  = inflater.inflate(R.layout.fragment_web_view, container, false);
//        mWebView = (WebView) view.findViewById(R.id.webview);
//        mWebView.setWebViewClient(new WebChromeClient());
//        String webUrl = "https://c34.radioboss.fm:18234/stream";
//
//
//
//        mWebView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
//        mWebView.loadUrl(webUrl);
//        return view;
//    }
//}