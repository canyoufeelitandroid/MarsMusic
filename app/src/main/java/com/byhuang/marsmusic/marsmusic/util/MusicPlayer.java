package com.byhuang.marsmusic.marsmusic.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.byhuang.marsmusic.marsmusic.model.Music;

import java.util.List;

/**
 * Created by 64088 on 2017/6/22.
 */

public class MusicPlayer {
    private static MediaPlayer mediaPlayer;

    private static final MusicPlayer musicPlayer=new MusicPlayer();
    private List<Music> list;
    private Context context;
    private int position=0;

    private MusicPlayer(){

    }

    public static MusicPlayer getMusicPlayer(){
        return musicPlayer;
    }

    public Music play(Context context1,List<Music> musicList,int position1){
        if(mediaPlayer!=null){
            //重置mediaPlayer
            mediaPlayer.reset();
        }
        this.list=musicList;
        this.position=position1;
        this.context=context1;
        //每次播放新的文件时要用create重新定义
        mediaPlayer=MediaPlayer.create(context, Uri.parse(list.get(position).getAddress()));
        try{
            mediaPlayer.start();
            //mediaPlayer.start();
            Log.i("data","play is start");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  musicList.get(position);
    }

    public void start(){
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    //停止播放
    public void stop(){
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
            Log.i("data","fuck you");
        }
    }

    //暂停播放
    public void pause(){
            mediaPlayer.pause();
    }

    public Music next(){
        Music music=null;
        if(list.size()<1){
           this.destroy();
        }else{
            mediaPlayer.reset();
            position=(position+1)%list.size();
            play(context,list,position);
            music=list.get(position);
        }
        return music;
    }

    public void destroy(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
    }
}
