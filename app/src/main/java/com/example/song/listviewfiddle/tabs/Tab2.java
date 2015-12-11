package com.example.song.listviewfiddle.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.song.listviewfiddle.MainActivity;
import com.example.song.listviewfiddle.R;

/**
 * Created by Song on 2015/12/11.
 */
public class Tab2 extends Fragment {

    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        final ListView listView = (ListView) mainActivity.findViewById(R.id.contentMainListView);
        listView.setAdapter(mainActivity.getRestaurantCursorAdapter());
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                Log.d(getClass().getName(), "onCreateActionMode");
                MenuInflater inflater = mainActivity.getMenuInflater();
                inflater.inflate(R.menu.menu_main_contexual, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                Log.d(getClass().getName(), "onPrepareActionMode");
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                Log.d(getClass().getName(), "onActionItemClicked");
                switch (item.getItemId()) {
                    case R.id.menu_main_contexual_delete:
                        Log.d(getClass().getName(), "Deleteing...");
                        long[] ids = listView.getCheckedItemIds();
                        mainActivity.getDbAdapter().deleteRestaurants(ids);
                        mainActivity.refreshRestaurantList();
                        mode.finish();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                Log.d(getClass().getName(), "onDestroyActionMode");
            }

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                ListView listView = (ListView) getActivity().findViewById(R.id.contentMainListView);
                int firstVisiblePosition = listView.getFirstVisiblePosition();
                int checkedPosition = position - firstVisiblePosition;
                Log.d(getClass().getName(), "id=" + id + ", checked=" + checked + ", position=" +
                        position + ", firstVisiblePosition=" + firstVisiblePosition + ", checkedPosition="
                        + checkedPosition);
                Log.d(getClass().getName(), "onItemCheckedStateChanged: " + listView.getCheckedItemPositions());
            }
        });
    }
}
