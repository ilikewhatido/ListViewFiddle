package com.example.song.listviewfiddle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class MainActivity extends AppCompatActivity implements MainActivityActionManager {

    private DbAdapter mDbAdapter;
    private RestaurantCursorAdapter mRestaurantCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Log.d("wawawa", "onActionItemClicked");
                switch (item.getItemId()) {
                    case R.id.menu_main_contexual_delete:
                        long[] ids = listView.getCheckedItemIds();
                        StringBuffer buffer = new StringBuffer();
                        for (long i : ids) {
                            buffer.append(i).append(", ");
                        }
                        Log.d("wawawa", "listView.getCheckedItemIds()=" + buffer.toString());
                        mode.finish();
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
}
