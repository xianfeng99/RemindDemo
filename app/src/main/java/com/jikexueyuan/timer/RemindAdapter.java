package com.jikexueyuan.timer;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lixianfeng on 16/3/14.
 */
public class RemindAdapter extends BaseAdapter {

    private ArrayList<RemindBean> list = new ArrayList<>();
    private Context mContext;

    public RemindAdapter(Context context){
        mContext = context;
    }

    public RemindAdapter(Context context, ArrayList<RemindBean> list){
        this.list = list;
    }

    public void addRemindList(ArrayList<RemindBean> list){
        this.list = list;
    }

    public void addRemind(RemindBean remindBean){
        list.add(remindBean);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RemindBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RemindBean remindBean = getItem(position);
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.remind_item, null);
            holder = new ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.remind_time);
            holder.content = (TextView) convertView.findViewById(R.id.remind_content);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.time.setText("" + remindBean.getTime());
        holder.content.setText(remindBean.getContent());

        return convertView;
    }

    class ViewHolder{
        TextView time;
        TextView content;
    }

}
