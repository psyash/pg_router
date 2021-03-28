package com.example.minor2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class RegisterPage extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.addFragment(new Tab1Fragment(),"REGISTER");
        sectionsPageAdapter.addFragment(new Tab2Fragment(),"UPLOAD");
        viewPager.setAdapter(sectionsPageAdapter);
    }
}
