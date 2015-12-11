package com.example.song.listviewfiddle.tabs;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.song.listviewfiddle.MainActivity;
import com.example.song.listviewfiddle.R;
import com.example.song.listviewfiddle.wheel.RestaurantWheelAdapter;
import com.example.song.listviewfiddle.wheel.WheelView;

/**
 * Created by Song on 2015/12/11.
 */
public class Tab1 extends Fragment {

    private static final int VISIBLE_ITEMS = 5;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        WheelView picker = (WheelView) getActivity().findViewById(R.id.restaurant_wheelpicker);
        picker.setViewAdapter(mainActivity.getRestaurantWheelAdapter());
        picker.setVisibleItems(VISIBLE_ITEMS);
        picker.setCyclic(true);
    }
}
