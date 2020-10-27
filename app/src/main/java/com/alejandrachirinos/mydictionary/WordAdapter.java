package com.alejandrachirinos.mydictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends BaseAdapter {
    private Context context;
    private List<modelWords> items;

    public WordAdapter(Context context, List<modelWords> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public modelWords getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.items.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.word_model_layout, null);

            viewHolder.parentLinearLayout = view.findViewById(R.id.parentLinearLayout);
            viewHolder.nameTextView = view.findViewById(R.id.nameTextView);
            viewHolder.detailsTextView= view.findViewById(R.id.detailsTextView);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        modelWords words = this.items.get(position);
        viewHolder.nameTextView.setText(words.getName());
        viewHolder.detailsTextView.setText(words.getDescription());

        return view;
    }

    static  class ViewHolder{
        LinearLayout parentLinearLayout;
        TextView nameTextView;
        TextView detailsTextView;
    }
}
