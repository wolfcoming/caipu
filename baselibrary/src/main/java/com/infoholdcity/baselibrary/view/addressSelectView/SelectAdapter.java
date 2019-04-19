package com.infoholdcity.baselibrary.view.addressSelectView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.infoholdcity.baselibrary.R;

import java.util.List;


/**
 * Created by dun on 17/2/9.
 */

public class SelectAdapter extends BaseAdapter {
    List<Area> datas;
    int selectedIndex = Selector.INDEX_INVALID;

    public SelectAdapter(List<Area> datas) {
        this.datas = datas;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

            holder = new Holder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Area item = (Area) getItem(position);
        //item.getname结果：昌邑区-全部 只显示全部
        String name = item.getName();

        if (name.contains("-")) {
            name = name.split("-")[1];
        }
        holder.textView.setText(name);

        boolean checked = selectedIndex != Selector.INDEX_INVALID && datas.get(selectedIndex).getId() == item.getId();
        holder.textView.setEnabled(!checked);
        holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

        return convertView;
    }

    class Holder {
        TextView textView;
        ImageView imageViewCheckMark;
    }
}
