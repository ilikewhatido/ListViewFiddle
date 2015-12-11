package com.example.song.listviewfiddle;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.song.listviewfiddle.db.DbAdapter;
import com.example.song.listviewfiddle.db.RestaurantCursorAdapter;
import com.example.song.listviewfiddle.tabs.SlidingTabLayout;
import com.example.song.listviewfiddle.tabs.Tab1;
import com.example.song.listviewfiddle.tabs.Tab2;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements MainActivityActionManager {

    private DbAdapter mDbAdapter;
    private RestaurantCursorAdapter mRestaurantCursorAdapter;

    private SlidingTabLayout mTabs;
    private ViewPager mPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mPager);
        /*
        mDbAdapter = new DbAdapter(this);
        mRestaurantCursorAdapter = new RestaurantCursorAdapter(this, mDbAdapter.getRestaurants());

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

        // Set up ListView
        final ListView listView = (ListView) findViewById(R.id.contentMainListView);
        listView.setAdapter(mRestaurantCursorAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                Log.d(getClass().getName(), "onCreateActionMode");
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_main_contexual, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                Log.d(getClass().getName(), "onPrepareActionMode");
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                Log.d(getClass().getName(), "onActionItemClicked");
                switch (item.getItemId()) {
                    case R.id.menu_main_contexual_delete:
                        Log.d(getClass().getName(), "Deleteing...");
                        long[] ids = listView.getCheckedItemIds();
                        mDbAdapter.deleteRestaurants(ids);
                        mode.finish();
                        refreshRestaurantList();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                Log.d(getClass().getName(), "onDestroyActionMode");
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                ListView listView = (ListView) findViewById(R.id.contentMainListView);
                int firstVisiblePosition = listView.getFirstVisiblePosition();
                int checkedPosition = position - firstVisiblePosition;
                Log.d(getClass().getName(), "id=" + id + ", checked=" + checked + ", position=" +
                        position + ", firstVisiblePosition=" + firstVisiblePosition + ", checkedPosition="
                        + checkedPosition);
                Log.d(getClass().getName(), "onItemCheckedStateChanged: " + listView.getCheckedItemPositions());
            }
        });
        */
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    /**
     * Refresh the UI after the underlying data set changes.
     */
    private void refreshRestaurantList() {
        mRestaurantCursorAdapter.changeCursor(mDbAdapter.getRestaurants());
        mRestaurantCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.song.listviewfiddle/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.song.listviewfiddle/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        String[] titles = {"TAB1", "TAB2"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
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
