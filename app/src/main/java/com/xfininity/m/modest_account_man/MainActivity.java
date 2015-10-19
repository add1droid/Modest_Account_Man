/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xfininity.m.modest_account_man;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private CharSequence mTitle;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        toolbar.setTitle("Overview");

        viewPager.setAdapter(adapter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView, viewPager);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        // tabLayout.setupWithViewPager(viewPager);
    }

    // This method will trigger on item Click of navigation menu


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {

        //   viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView, ViewPager viewPager) {


        final ViewPager vp = viewPager;
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Adapter adapter = new Adapter(getSupportFragmentManager());
                        //Checking if the item is in checked state or not, if not make it in checked state
                        if (menuItem.isChecked()) menuItem.setChecked(false);
                        else menuItem.setChecked(true);

                        //Closing drawer on item click
                        mDrawerLayout.closeDrawers();

                        //Check to see which item was being clicked and perform appropriate action
                        switch (menuItem.getItemId()) {


                            //Replacing the main content with ContentFragment Which is our Inbox View;
                            case R.id.screen:
                                Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();

                                return true;

                            // For rest of the options we just show a toast on click

                            case R.id.nav_overview:
                                toolbar.setTitle("Overview");
                                adapter.addFragment(new DashBoard_Fragment(), "DashBoard");
                                adapter.addFragment(new DashBoard_Fragment(), "Billing");
                                adapter.addFragment(new DashBoard_Fragment(), "Manage");
                                vp.setAdapter(adapter);
                                tabLayout.setupWithViewPager(vp);

                                return true;
                            case R.id.nav_shop:
                                toolbar.setTitle("Shop");
                                adapter.addFragment(new DashBoard_Fragment(), "Category 4");
                                adapter.addFragment(new DashBoard_Fragment(), "Category 5");
                                adapter.addFragment(new DashBoard_Fragment(), "Category 6");
                                vp.setAdapter(adapter);
                                tabLayout.setupWithViewPager(vp);
                                return true;
                            case R.id.nav_support:
                                toolbar.setTitle("Support");
                                Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.nav_account:
                                toolbar.setTitle("Account");
                                Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                                adapter.addFragment(new DashBoard_Fragment(), "DashBoard");
                                adapter.addFragment(new Billing_Fragment(), "Billing");
                                adapter.addFragment(new Manage_Fragment(), "Manage");
                                vp.setAdapter(adapter);
                                tabLayout.setupWithViewPager(vp);
                                return true;
                            case R.id.nav_search:
                                toolbar.setTitle("Search");
                                Toast.makeText(getApplicationContext(), "Trash Selected", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.nav_l_chat:
                                toolbar.setTitle("Live Chat");
                                Toast.makeText(getApplicationContext(), "Spam Selected", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                toolbar.setTitle("My Account");
                                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                                return true;

                        }


                        // return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


}
