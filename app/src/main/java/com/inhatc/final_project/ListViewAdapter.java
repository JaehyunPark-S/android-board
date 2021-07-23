package com.inhatc.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ImageView iconImageView;
    private TextView title;
    private TextView content;
    private TextView writer;
    private TextView regdate;

    private ArrayList<SingleItem> listViewItemList = new ArrayList<SingleItem>();

    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        title = (TextView)convertView.findViewById(R.id.b_title);
        content = (TextView)convertView.findViewById(R.id.b_content);
        writer = (TextView)convertView.findViewById(R.id.b_writer);
        regdate = (TextView)convertView.findViewById(R.id.b_regdate);
        iconImageView = (ImageView)convertView.findViewById(R.id.b_image);

        SingleItem singleItem = listViewItemList.get(position);

        title.setText(singleItem.getTitle());
        content.setText(singleItem.getContent());
        writer.setText(singleItem.getWriter());
        regdate.setText(singleItem.getRegdate().toString());
        iconImageView.setImageResource(R.drawable.and);

        return convertView;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public Object getItem(int position){
        return listViewItemList.get(position);
    }

    public void addItem(String title, String content, String writer, String regdate){
        SingleItem item = new SingleItem();

        item.setTitle(title);
        item.setContent(content);
        item.setWriter(writer);
        item.setRegdate(regdate);

        listViewItemList.add(item);
    }
}
