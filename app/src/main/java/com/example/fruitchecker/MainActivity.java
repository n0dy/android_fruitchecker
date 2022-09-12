package com.example.fruitchecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
{

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FragmentAdapter adapter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);


        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new FragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("By name"));
        tabLayout.addTab(tabLayout.newTab().setText("By value"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}