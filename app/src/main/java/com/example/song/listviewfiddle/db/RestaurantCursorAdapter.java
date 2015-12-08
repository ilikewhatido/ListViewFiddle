package com.example.song.listviewfiddle.db;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.song.listviewfiddle.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by songhan on 2015/12/7.
 */
public class RestaurantCursorAdapter extends CursorAdapter {

    private Set<Long> mCheckedItemIds;

    public RestaurantCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER);
        mCheckedItemIds = new HashSet();

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listRow = inflater.inflate(R.layout.content_main_listview_row, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView)listRow.findViewById(R.id.contentMainListViewRowName);
        listRow.setTag(holder);
        return listRow;
    }

    @Override
    public void bindView(View listRow, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)listRow.getTag();
        String name = getCursor().getString(cursor.getColumnIndex(DbAdapter.RESTAURANT_NAME));
        holder.name.setText(name);
    }

    /**
     * Maintain the checked item list
     * @param id - the id of the restaurant
     * @param checked - is the restaurant checked or not?
     */
    public void setChecked(Long id, boolean checked) {
        if(checked) {
            mCheckedItemIds.add(id);
        } else {
            mCheckedItemIds.remove(id);
        }
    }

    private static class ViewHolder {
        public TextView name;
    }
}
