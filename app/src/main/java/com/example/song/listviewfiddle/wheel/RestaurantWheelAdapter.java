package com.example.song.listviewfiddle.wheel;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import com.example.song.listviewfiddle.R;
import com.example.song.listviewfiddle.db.DbAdapter;

/**
 * Created by Song on 2015/12/11.
 */
public class RestaurantWheelAdapter extends AbstractWheelTextAdapter {

    private Cursor cursor;

    public RestaurantWheelAdapter(Context context, Cursor cursor) {
        super(context, R.layout.restaurant_wheelpick_layout, NO_RESOURCE);
        this.cursor = cursor;
        setItemTextResource(R.id.wheelpicker_layout_restaurant);
    }

    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
        return super.getItem(index, cachedView, parent);
    }

    @Override
    protected CharSequence getItemText(int index) {
        if(cursor.moveToPosition(index)) {
            return cursor.getString(cursor.getColumnIndex(DbAdapter.RESTAURANT_NAME));
        }
        return null;
    }

    @Override
    public int getItemsCount() {
       return cursor.getCount();
    }

    public void changeCursor(Cursor newCursor) {
        if(cursor != null) {
            cursor.close();
            cursor = newCursor;
            if(newCursor.getCount() == 0) {
                this.notifyDataInvalidatedEvent();
            } else {
                this.notifyDataChangedEvent();
            }

        }
    }
}
