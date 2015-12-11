package com.example.song.listviewfiddle;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.song.listviewfiddle.db.DbAdapter;
import com.example.song.listviewfiddle.db.RestaurantCursorAdapter;
import com.example.song.listviewfiddle.tabs.SlidingTabLayout;
import com.example.song.listviewfiddle.tabs.Tab1;
import com.example.song.listviewfiddle.tabs.Tab2;
import com.example.song.listviewfiddle.wheel.RestaurantWheelAdapter;
import com.example.song.listviewfiddle.wheel.WheelView;

public class MainActivity extends AppCompatActivity implements MainActivityActionManager {

    private DbAdapter mDbAdapter;
    private RestaurantCursorAdapter mRestaurantCursorAdapter;
    private RestaurantWheelAdapter mRestaurantWheelAdapter;

    private SlidingTabLayout mTabs;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbAdapter = new DbAdapter(this);
        Cursor cursor = mDbAdapter.getRestaurants();
        mRestaurantCursorAdapter = new RestaurantCursorAdapter(this, cursor);
        mRestaurantWheelAdapter = new RestaurantWheelAdapter(this, cursor);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mPager);

        // Floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(getClass().getName(), "floating button clicked");
                AddRestaurantDialog dialog = new AddRestaurantDialog();
                dialog.show(getFragmentManager(), "ADD_RESTAURANT");
            }
        });
    }

    public void go(View view) {
        WheelView wheel = (WheelView) findViewById(R.id.restaurant_wheelpicker);
        int count = mRestaurantWheelAdapter.getItemsCount();
        wheel.scroll(25 + (int)(Math.random() * count), 4000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void addRestaurant(String name, String number) {
        mDbAdapter.addRestaurant(name, number);
        refreshRestaurantList();
    }

    public RestaurantWheelAdapter getRestaurantWheelAdapter() {
        return mRestaurantWheelAdapter;
    }

    public RestaurantCursorAdapter getRestaurantCursorAdapter() {
        return mRestaurantCursorAdapter;
    }

    public DbAdapter getDbAdapter() {
        return mDbAdapter;
    }

    /**
     * Refresh the UI after the underlying data set changes.
     */
    public void refreshRestaurantList() {
        Cursor cursor = mDbAdapter.getRestaurants();
        mRestaurantCursorAdapter.changeCursor(cursor);
        mRestaurantCursorAdapter.notifyDataSetChanged();
        mRestaurantWheelAdapter.changeCursor(cursor);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        String[] titles = {"拉霸機", "餐廳"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new Tab1();
            } else {
                return new Tab2();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
