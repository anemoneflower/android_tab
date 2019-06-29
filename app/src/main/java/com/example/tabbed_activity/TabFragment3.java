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

    ArrayList<String> appNameList;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> appPackageNameList;
    List<ResolveInfo> pkgAppsList;
    PackageManager pm;

    private Spinner spinner0;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Shared preferences
        pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        //App List
        appNameList = new ArrayList<>();
        appPackageNameList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, appNameList);
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        pkgAppsList = getContext().getPackageManager().queryIntentActivities( mainIntent, 0);
        pm = getContext().getPackageManager();

        for (ResolveInfo resolveInfo : pkgAppsList) {
            //package name
            String PackageName = resolveInfo.activityInfo.packageName;
            try {
                String appname = pm.getApplicationLabel(pm.getApplicationInfo(resolveInfo.activityInfo.packageName, PackageManager.GET_META_DATA)).toString();
                appNameList.add(appname);
                appPackageNameList.add(PackageName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_3, container, false);

        spinner0 = (Spinner) view.findViewById(R.id.spinner0);
        spinner0.setAdapter(arrayAdapter);
        spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putString("1", appPackageNameList.get(i));
                editor.putInt("1_pos", i);
                editor.putString("1_name", appNameList.get(i));
                editor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        String prev = pref.getString("1",null);
        if(prev != null){
            spinner0.setSelection(pref.getInt("1_pos", -1));
        }

        view.findViewById(R.id.overlay_on_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    getActivity().startService(new Intent(getContext(), FloatingViewService.class));
                    getActivity().finish();
                } else if (Settings.canDrawOverlays(getActivity())) {
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
