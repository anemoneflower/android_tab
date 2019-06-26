package com.example.tabbed_activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TabFragment2 extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerImageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AlbumRecyclerItem> mMyData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.album_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mAdapter = new RecyclerImageAdapter(mMyData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }

    private void initDataset() {
        mMyData = new ArrayList<AlbumRecyclerItem>();
        addPhoto(getResources().getDrawable(R.drawable.sample_image_1)) ;
        addPhoto(getResources().getDrawable(R.drawable.sample_image_2)) ;

    }

    public void addPhoto(Drawable photo) {
        AlbumRecyclerItem item = new AlbumRecyclerItem();

        item.setPhoto(photo);
        //item.setDesc(desc);
        mMyData.add(item);
    }
}
