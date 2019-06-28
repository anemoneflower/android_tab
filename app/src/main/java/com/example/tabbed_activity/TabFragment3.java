package com.example.tabbed_activity;

import android.content.Intent;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TabFragment3 extends Fragment {
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_3, container, false);
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

        view.findViewById(R.id.setting_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> pkgAppsList = getContext().getPackageManager().queryIntentActivities( mainIntent, 0);
//                try
//                {
//                    Drawable icon = getActivity().getPackageManager().getApplicationIcon("com.example.testnotification");
//                    imageView.setImageDrawable(icon);
//                }
//                catch (PackageManager.NameNotFoundException e)
//                {
//                    e.printStackTrace();
//                }


                for (ResolveInfo resolveInfo : pkgAppsList) {
                    Log.d("DEBUG DEBUG DEBUG", "Installed package :" + resolveInfo.activityInfo.packageName);

                    //int stringId = resolveInfo.activityInfo.labelRes;
                    //Log.d("DEBUG DEBUG DEBUG", "Installed appname :" +
                    //        (stringId==0 ?  getActivity().getApplicationInfo().nonLocalizedLabel.toString(): getActivity().getString(stringId))
                    //);

                    if(resolveInfo.activityInfo.packageName.equals("com.google.android.music")){
                        Log.d("MUSIC", "music access");
                        Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("com.google.android.music");
                        startActivity(intent);
                    }
                    //Log.d(TAG, "Source dir : " + packageInfo.sourceDir);
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
