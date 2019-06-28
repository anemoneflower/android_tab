package com.example.tabbed_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TabFragment3 extends Fragment {
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_3, container, false);
        view.findViewById(R.id.buttonCreateWidget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUGDEBUGDEBUG", "DEBUGDEBUGDEBUG");
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    Log.d("DEBUGDEBUGDEBUG", "IF");
                    getActivity().startService(new Intent(getContext(), FloatingViewService.class));
                    getActivity().finish();
                } else if (Settings.canDrawOverlays(getActivity())) {
                    Log.d("DEBUGDEBUGDEBUG", "ELSEIF");
                    getActivity().startService(new Intent(getContext(), FloatingViewService.class));
                    getActivity().finish();
                }
                else {
                    askPermission();
                    Toast.makeText(getActivity(), "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

}
