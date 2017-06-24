package com.byhuang.marsmusic.marsmusic.activity;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.byhuang.marsmusic.marsmusic.R;
import com.byhuang.marsmusic.marsmusic.adapter.MusicAdapter;
import com.byhuang.marsmusic.marsmusic.model.Music;
import com.byhuang.marsmusic.marsmusic.util.MusicLoader;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 64088 on 2017/6/21.
 */

public class LocalFragment extends Fragment {
    private ListView lv;

    private MusicAdapter adapter;
    private SharedPreferences pf;
    private SharedPreferences.Editor editor;
    //private ArrayList<ListSong> songList;
    private List<Music> musicList;
    private ContentResolver contentResolver;

    MainActivity mainActivity;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.local_music_page,container,false);
        mainActivity=(MainActivity)getActivity();
        pf= PreferenceManager.getDefaultSharedPreferences(getContext());
        editor=pf.edit();
        String namePf=pf.getString("title_0",null);
        int titleSize=pf.getInt("list_title_size",0);
        Log.i("data","size is "+titleSize);
        contentResolver=getActivity().getContentResolver();
        //songList=new ArrayList<ListSong>();


//        if(namePf==null||titleSize==0) {
//            songList=MusicLoader.getTitleList(getContext(),contentResolver);
//        }else{
//            songList.clear();
//            for(int i=0;i<titleSize;i++){
//                String title=pf.getString("title_"+i,null);
//                String singer=pf.getString("singer_"+i,null);
//                ListSong listSong=new ListSong(title,singer);
//                songList.add(listSong);
//            }
//        }

        init();

        lv=(ListView)view.findViewById(R.id.local_lv);
        adapter=new MusicAdapter(getContext(),R.layout.local_list_item,musicList);
        lv.setAdapter(adapter);
        return view;
    }


    private void init(){

        musicList= DataSupport.findAll(Music.class);
        if(musicList.size()==0){
            Log.i("com.mars","music litepal is null");
            musicList=MusicLoader.musicLoad(contentResolver);
        }
    }

    public void changeList(){
        musicList.clear();
        //songList=MusicLoader.getTitleList(getContext(),contentResolver);
        musicList=MusicLoader.musicLoad(contentResolver);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


//        mainActivity.playStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(mainActivity.currentPlay==false) {
//                   mainActivity.musicPlayer.start();
//                }else {
//                    mainActivity.musicPlayer.pause();
//                }
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(mainActivity.currentPlay==false){
//                            mainActivity.currentPlay=true;
//                            mainActivity.playStop.setBackgroundResource(R.mipmap.stop);
//                        }else{
//                            mainActivity.currentPlay=false;
//                            mainActivity.playStop.setBackgroundResource(R.mipmap.play);
//                        }
//                    }
//                });
//            }
//        });
//        mainActivity.playNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Music music=mainActivity.musicPlayer.next();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mainActivity.currentName.setText(music.getTitle());
//                        mainActivity.currentSinger.setText("歌手:"+music.getSinger());
//                        mainActivity.playStop.setBackgroundResource(R.mipmap.stop);
//                    }
//                });
//            }
//        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                Log.i("com.mars","context is "+getContext());
                mainActivity.currentPlay=true;

                mainActivity.musicPlayer.play(getContext(),musicList,i);

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
