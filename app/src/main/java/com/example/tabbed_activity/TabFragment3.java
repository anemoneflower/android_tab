package com.example.tabbed_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

import top.defaults.colorpicker.ColorPickerPopup;

import static android.content.Context.MODE_PRIVATE;

public class TabFragment3 extends Fragment {
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    View view;

    ArrayList<String> appNameList;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> appPackageNameList;
    List<ResolveInfo> pkgAppsList;
    PackageManager pm;

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

        appNameList.add("퀵메뉴에 지정할 어플리케이션을 선택하세요");
        appPackageNameList.add(null);
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
        spinner_initialize();

        view.findViewById(R.id.overlay_on_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    Intent intent = new Intent(getContext(), FloatingViewService.class);
//                    intent.putExtra("bgcolor", expanded_bgcolor);
                    getActivity().startService(intent);
                    getActivity().finish();
                } else if (Settings.canDrawOverlays(getActivity())) {
                    Intent intent = new Intent(getContext(), FloatingViewService.class);
//                    intent.putExtra("bgcolor", expanded_bgcolor);
                    getActivity().startService(intent);
                    getActivity().finish();
                }
                else {
                    askPermission();
                    Toast.makeText(getActivity(), "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.overlay_on_button_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int initial_color = pref.getInt("bg_color", -1);
                if(initial_color == -1) initial_color = Color.parseColor("#F5F5ABC3");
                new ColorPickerPopup.Builder(getContext())
                        .initialColor(initial_color) // Set initial color
                        .enableBrightness(true) // Enable brightness slider or not
                        .enableAlpha(true) // Enable alpha slider or not
                        .okTitle("Choose")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(view, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {
                                editor.putInt("bg_color", color);
                                editor.apply();
                            }

                        });

            }
        });
        return view;
    }

    public void spinner_initialize(){
        ArrayList<Spinner> spinner_list = new ArrayList<>();
        spinner_list.add((Spinner) view.findViewById(R.id.spinner0));
        spinner_list.add((Spinner) view.findViewById(R.id.spinner1));
        spinner_list.add((Spinner) view.findViewById(R.id.spinner2));
        spinner_list.add((Spinner) view.findViewById(R.id.spinner3));
        spinner_list.add((Spinner) view.findViewById(R.id.spinner4));
        spinner_list.add((Spinner) view.findViewById(R.id.spinner5));
        spinner_list.add((Spinner) view.findViewById(R.id.spinner6));
        spinner_list.add((Spinner) view.findViewById(R.id.spinner7));
        spinner_list.add((Spinner) view.findViewById(R.id.spinner8));

        for(Spinner spinner : spinner_list){
            final int index = spinner_list.indexOf(spinner)+1;
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putString( Integer.toString(index), appPackageNameList.get(i));
                    editor.putInt(index+"_pos", i);
                    editor.putString(index+"_name", appNameList.get(i));
                    editor.apply();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            String prev = pref.getString(Integer.toString(index),null);
            if(prev != null) {
                spinner.setSelection(pref.getInt(index+"_pos", -1));
            }
        }
    }

    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

}
