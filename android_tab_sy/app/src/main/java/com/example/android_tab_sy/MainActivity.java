package com.example.android_tab_sy;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    static final int PERMISSION_REQUEST_CODE = 100;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // get the reference of FrameLayout and TabLayout
//        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
//        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
//// Create a new Tab named "First"
//        TabLayout.Tab firstTab = tabLayout.newTab();
//        firstTab.setText("First"); // set the Text for the first Tab
//        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
//// Create a new Tab named "Second"
//        TabLayout.Tab secondTab = tabLayout.newTab();
//        secondTab.setText("Second"); // set the Text for the second Tab
//        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
//// Create a new Tab named "Third"
//        TabLayout.Tab thirdTab = tabLayout.newTab();
//        thirdTab.setText("Third"); // set the Text for the first Tab
//        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout

//        TelephonyManager systemService = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        String PhoneNumber = systemService.getLine1Number();
//        PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-10, PhoneNumber.length());
//        PhoneNumber = "0" + PhoneNumber;
//
//        PhoneNumber = PhoneNumberUtils.formatNumber(PhoneNumber);
    }
}
