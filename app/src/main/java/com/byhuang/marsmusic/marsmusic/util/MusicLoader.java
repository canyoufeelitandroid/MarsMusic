package com.byhuang.marsmusic.marsmusic.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;

import com.byhuang.marsmusic.marsmusic.model.ListSong;
import com.byhuang.marsmusic.marsmusic.model.Music;

import org.litepal.exceptions.DataSupportException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 64088 on 2017/6/19.
 */

public class MusicLoader {
//    private static List<Music> musicList=new ArrayList<Music>();
//    private static MusicLoader musicLoader;
//    private static ContentResolver contentResolver;





//    public MusicLoader(ContentResolver contentResolver1){
//        if(contentResolver==null){
//            contentResolver=contentResolver1;
//            musicList=musicLoad();
//        }
//    }

    public synchronized static ArrayList<ListSong> getTitleList(Context context,ContentResolver contentResolver){

        List<Music> musicList=MusicLoader.musicLoad(contentResolver);
        ArrayList<ListSong> songList=new ArrayList<ListSong>();

        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt("list_title_size",musicList.size());
        for (int i = 0; i < musicList.size(); i++) {
            String title = musicList.get(i).getTitle();
            String singer=musicList.get(i).getSinger()+"-"+musicList.get(i).getAlbum();
            ListSong listSong=new ListSong(title,singer);
            songList.add(listSong);
            editor.remove("title_" + i);
            editor.putString("title_" + i, title);
            editor.remove("singer_" + i);
            editor.putString("singer_" + i, singer);
        }

        editor.apply();
        return songList;
    }

//    public static MusicLoader instance(){
//
//        return musicLoader;
//    }
    public synchronized static List<Music> musicLoad(ContentResolver contentResolver){
        List<Music> musicList=new ArrayList<Music>();

        //需要查询的地址
        Uri contentUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        //需要查询的字段
        String[] projection={
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.TITLE
        };
        //需要查询的条件
        String where="mime_type in ('audio/mpeg','audio/x-ms-wma') and bucket_display_name <> 'audio' and is_music > 0 ";
        //查询后的排列顺序
        String order= MediaStore.Audio.Media.DATA;

        musicList.clear();
        Cursor cursor=contentResolver.query(contentUri,projection,null,null,order);
        if(cursor==null){
            Log.i("data","Music load is null");
        }else if(!cursor.moveToFirst()){
            Log.i("data","Music load moveryofirst is false");
        }else{
            do{
                String name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String singer=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                long time=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long size=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                String address=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                long _id=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));


                Log.i("data","_id is "+_id+" title is "+title+" album is "+album+" singer is "+singer+" time is "+time+" address is "+address);

                Music music=new Music();
                music.setName(name);
                music.setAlbum(album);
                music.setSinger(singer);
                music.setTime(time);
                music.setSize(size);
                music.setAddress(address);
                music.set_id(_id);
                music.setTitle(title);
                music.setLove(0);

                try {
                    int updateNum = music.updateAll("_id=?", String.valueOf(_id));
                    Log.i("com.mars","updateNum is "+updateNum);
                    if (updateNum == 0) {
                        music.save();
                    }
                }catch (DataSupportException e){
                    e.printStackTrace();
                }
                musicList.add(music);

            }while (cursor.moveToNext());
        }
        return musicList;
    }

//    public List<Music> getMusicList(){
//        return musicLoad();
//    }

    public static Uri getUriById(long _id){
        Uri contentUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri uri= ContentUris.withAppendedId(contentUri,_id);
        return uri;
    }

}
