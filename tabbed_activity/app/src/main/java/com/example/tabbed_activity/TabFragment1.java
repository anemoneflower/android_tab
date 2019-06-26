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

public class TabFragment1 extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerImageTextAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ContactRecyclerItem> mMyData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mAdapter = new RecyclerImageTextAdapter(mMyData);
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
        mMyData = new ArrayList<ContactRecyclerItem>();
        addContact(getResources().getDrawable(R.drawable.default_icon), "Kim", "01086731742") ;
        addContact(getResources().getDrawable(R.drawable.default_icon), "Son", "01085467389") ;
        addContact(getResources().getDrawable(R.drawable.default_icon), "Jane", "01046885813") ;

    }


    public void addContact(Drawable icon, String name, String phone) {
        ContactRecyclerItem item = new ContactRecyclerItem();

        item.setIcon(icon);
        item.setName(name);
        item.setPhone(phone);

        mMyData.add(item);
    }
}
