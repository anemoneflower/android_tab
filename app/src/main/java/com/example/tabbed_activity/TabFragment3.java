package com.example.tabbed_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TabFragment3 extends Fragment {
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    View view;
    public ArrayList<String> appNameList;
    ArrayAdapter<String> arrayAdapter;
    private Spinner spinner0;
    List<ResolveInfo> pkgAppsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref= getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("1","com.google.android.music");
        editor.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_3, container, false);

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        pkgAppsList = getContext().getPackageManager().queryIntentActivities( mainIntent, 0);
        PackageManager pm = getContext().getPackageManager();

        appNameList = new ArrayList<>();

        for (ResolveInfo resolveInfo : pkgAppsList) {
            //package name
            Log.d("DEBUG DEBUG DEBUG", "Installed package :" + resolveInfo.activityInfo.packageName);

            try {
                String appname = pm.getApplicationLabel(pm.getApplicationInfo(resolveInfo.activityInfo.packageName, PackageManager.GET_META_DATA)).toString();
                Log.d("DEBUGDEBUGDEBUG",appname);
                appNameList.add(appname);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
               appNameList);

        spinner0 = (Spinner) view.findViewById(R.id.spinner0);
        spinner0.setAdapter(arrayAdapter);

        spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),appNameList.get(i)+"가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        view.findViewById(R.id.overlay_on_button).setOnClickListener(new View.OnClickListener() {
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
