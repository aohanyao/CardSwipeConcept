package com.aohanyao.ue.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aohanyao.ue.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {


    public ContentFragment() {
    }

    public static ContentFragment newInstance() {

        Bundle args = new Bundle();

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

}
