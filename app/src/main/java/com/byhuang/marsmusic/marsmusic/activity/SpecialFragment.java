package com.byhuang.marsmusic.marsmusic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.byhuang.marsmusic.marsmusic.R;
import com.byhuang.marsmusic.marsmusic.adapter.MusicAdapter;
import com.byhuang.marsmusic.marsmusic.model.Music;
import com.byhuang.marsmusic.marsmusic.util.MusicPlayer;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 64088 on 2017/6/21.
 */

public class SpecialFragment extends Fragment{
    private ListView lv;
    private MusicAdapter adapter;
    private List<Music> musicList;
    MainActivity mainActivity;
    MusicPlayer musicPlayer=MusicPlayer.getMusicPlayer();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.special_music_page,container,false);
        initData();
        lv=(ListView)view.findViewById(R.id.special_lv);
        adapter=new MusicAdapter(getContext(),R.layout.local_list_item,musicList);
        lv.setAdapter(adapter);
        return view;
    }

    private List<Music> initData(){
        musicList= DataSupport.where("love=?","1").find(Music.class);
        if(musicList.size()==0){
            musicList=new ArrayList<>();
        }
        return musicList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mainActivity=(MainActivity)getActivity();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                mainActivity.currentPlay=true;

                musicPlayer.play(getContext(),musicList,i);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.currentName.setText(musicList.get(i).getTitle());
                        mainActivity.currentSinger.setText("歌手:"+musicList.get(i).getSinger());
                        mainActivity.playStop.setBackgroundResource(R.mipmap.stop);
                    }
                });
            }
        });

        super.onActivityCreated(savedInstanceState);
    }
}
