package com.byhuang.marsmusic.marsmusic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.byhuang.marsmusic.marsmusic.R;
import com.byhuang.marsmusic.marsmusic.model.Music;

import java.util.List;

/**
 * Created by 64088 on 2017/6/21.
 */

public class MusicAdapter extends ArrayAdapter<Music>{
    private int resourceId;


    public MusicAdapter(Context context, int resource, List<Music> objects) {

        super(context, resource, objects);
        this.resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Music musicList=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
           view = LayoutInflater.from(getContext()).inflate(R.layout.local_list_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.title=(TextView)view.findViewById(R.id.music_title);
            viewHolder.singer=(TextView)view.findViewById(R.id.music_singer);
            view.setTag(viewHolder);

        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.title.setText(musicList.getTitle());
        viewHolder.singer.setText(musicList.getSinger()+"-"+musicList.getAlbum() );

        return view;
    }

    class ViewHolder{
        TextView title;
        TextView singer;
    }

}
