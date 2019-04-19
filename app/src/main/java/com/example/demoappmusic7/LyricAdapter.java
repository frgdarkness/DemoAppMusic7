package com.example.demoappmusic7;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LyricAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<RowLyric> listRowLyric;

    public LyricAdapter(Context mContext, int layout, List<RowLyric> list){
        this.context = mContext;
        this.layout = layout;
        this.listRowLyric = list;

    }

    @Override
    public int getCount() {
        return listRowLyric.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtRow;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder.txtRow = (TextView) convertView.findViewById(R.id.textRowLyric);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(listRowLyric.get(position).isCheckShow()){
            viewHolder.txtRow.setTextColor(Color.RED);
            viewHolder.txtRow.setTextSize(30);
        }else{
            viewHolder.txtRow.setTextColor(Color.WHITE);
            viewHolder.txtRow.setTextSize(24);
        }
        RowLyric row = listRowLyric.get(position);
        viewHolder.txtRow.setText(row.getStringCau());
        return convertView;
    }
}
