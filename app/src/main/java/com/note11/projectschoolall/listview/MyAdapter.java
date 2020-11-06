package com.note11.projectschoolall.listview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.note11.projectschoolall.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private ArrayList<MyItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_find_school, parent, false);
        }

        TextView tv_schoolname = (TextView) convertView.findViewById(R.id.txt_memo_row_text);

        MyItem myItem = getItem(position);

        tv_schoolname.setText(myItem.getContents());

        return convertView;
    }

    public void addItem(String sSchoolname) {
        MyItem mItem = new MyItem();

        mItem.setContents(sSchoolname);

        mItems.add(mItem);
    }
}
