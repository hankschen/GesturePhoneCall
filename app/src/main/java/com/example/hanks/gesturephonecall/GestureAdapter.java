package com.example.hanks.gesturephonecall;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by student on 2015/7/11.
 */
public class GestureAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,Object>> list;

    public GestureAdapter(Context ctxt,ArrayList<HashMap<String,Object>> list){
        context=ctxt;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewTag tag=new ViewTag();
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.list_item, null);
            tag.iv=(ImageView)convertView.findViewById(R.id.imageView);
            tag.tv=(TextView)convertView.findViewById(R.id.textView4);
            tag.tv1=(TextView)convertView.findViewById(R.id.textView5);
            convertView.setTag(tag);
        }else{
            tag=(ViewTag)convertView.getTag();
        }

        tag.iv.setImageBitmap((Bitmap)list.get(position).get("pic"));
        tag.tv.setText((String) list.get(position).get("name"));
        tag.tv1.setText((String)list.get(position).get("phone"));

        return convertView;
    }

    class ViewTag{
        ImageView iv;
        TextView tv;
        TextView tv1;
    }

}
