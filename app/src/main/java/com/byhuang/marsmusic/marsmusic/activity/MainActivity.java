package com.byhuang.marsmusic.marsmusic.activity;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.byhuang.marsmusic.marsmusic.Listener.MyTabListener;
import com.byhuang.marsmusic.marsmusic.R;
import com.byhuang.marsmusic.marsmusic.model.Music;
import com.byhuang.marsmusic.marsmusic.util.MusicLoader;
import com.byhuang.marsmusic.marsmusic.util.MusicPlayer;
import com.github.ybq.android.spinkit.style.Wave;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ResideMenu resideMenu;
//    private Button testBtn;
//    private ListView testLv;
    private MusicLoader musicLoader;
    private List<Music> musicList;
    private ArrayList<String> titleList;
    private ContentResolver contentResolver;
//    private ArrayAdapter<String> adapter;
    private ProgressBar progressBar;
    private SharedPreferences pf;
    private SharedPreferences.Editor editor;
    private LocalFragment localFragment;
    private SpecialFragment specialFragment;
    public MusicPlayer musicPlayer=MusicPlayer.getMusicPlayer();


    //下方播放小界面控件
    public ImageView currentPic;
    public TextView currentName;
    public TextView currentSinger;
    public boolean currentPlay=false;
    public Button playStop;
    public Button playNext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Connector.getDatabase();

        currentPic=(ImageView)findViewById(R.id.play_pic_main);
        currentName=(TextView)findViewById(R.id.play_title_main);
        currentSinger=(TextView)findViewById(R.id.play_singer_main);
        playStop=(Button)findViewById(R.id.play_stop_main);
        playNext=(Button)findViewById(R.id.play_next_main);
        init();

    }

    private void init(){
        android.support.v7.app.ActionBar bar=getSupportActionBar();
        //设置为Tab模式
        bar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        //bar.setDisplayShowTitleEnabled(true);
        bar.setDisplayShowTitleEnabled(false);
        //新建2个Tab

        android.support.v7.app.ActionBar.Tab tabA = bar.newTab().setText("本地音乐");
        android.support.v7.app.ActionBar.Tab tabB = bar.newTab().setText("我的歌单");
        //绑定到Fragment
        localFragment = new LocalFragment();
        specialFragment = new SpecialFragment();
        tabA.setTabListener(new MyTabListener(localFragment));
        tabB.setTabListener(new MyTabListener(specialFragment));
        bar.addTab(tabA);
        bar.addTab(tabB);



        //滑动菜单的实现
        resideMenu=new ResideMenu(this);
        resideMenu.setBackgroundResource(R.drawable.gakki);
        resideMenu.attachToActivity(this);

        String[] strings={"扫描本地","我的歌单","系统设置","退出"};
        int[] icons={R.mipmap.search_menu,R.mipmap.music_menu,R.mipmap.setting_menu,R.mipmap.exit_menu};

        for(int i=0;i<strings.length;i++){
            ResideMenuItem item=new ResideMenuItem(this,icons[i],strings[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item,ResideMenu.DIRECTION_LEFT);
        }

//        testBtn=(Button)findViewById(R.id.test_btn);
//        testLv=(ListView)findViewById(R.id.test_lv);
        progressBar=(ProgressBar)findViewById(R.id.pro_bar);
        Wave wave=new Wave();
        wave.setColor(R.color.colorPrimary);
        progressBar.setIndeterminateDrawable(wave);


        //监听事件
        playStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentPlay==false) {
                    musicPlayer.start();
                }else {
                    musicPlayer.pause();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(currentPlay==false){
                            currentPlay=true;
                            playStop.setBackgroundResource(R.mipmap.stop);
                        }else{
                            currentPlay=false;
                            playStop.setBackgroundResource(R.mipmap.play);
                        }
                    }
                });
            }
        });
        playNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Music music=musicPlayer.next();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentName.setText(music.getTitle());
                        currentSinger.setText("歌手:"+music.getSinger());
                        playStop.setBackgroundResource(R.mipmap.stop);
                    }
                });
            }
        });


    }

    private void showProgressBar(){
         progressBar.setVisibility(View.VISIBLE);
        Log.i("data","show");
    }

    private void closeProgressBar(){
        if(progressBar!=null){
            progressBar.setVisibility(View.GONE);
            Log.i("data","close");
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            default:
                 break;
        }
        if(view==resideMenu.getMenuItems().get(0)){
            showProgressBar();

            if(localFragment==null){
                localFragment=new LocalFragment();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    localFragment.changeList();
                }
            });
//            if (musicLoader!=null) {
//                musicList.clear();
//            }else{
//                musicLoader=new MusicLoader(contentResolver);
//            }
//            musicList=musicLoader.getMusicList();
//            titleList.clear();
//            editor.putInt("list_title_size",musicList.size());
//            for(int i=0;i<musicList.size();i++){
//                String name=musicList.get(i).getName();
//                titleList.add(name);
//                editor.remove("title_"+i);
//                editor.putString("title_"+i,name);
//                //Log.i("data","new title is "+name);
//            }
//            editor.apply();
//            adapter.notifyDataSetChanged();
            resideMenu.closeMenu();

           new Thread(new Runnable() {
               @Override
               public void run() {
                   try{
                       Thread.sleep(3000);
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               closeProgressBar();
                           }
                       });


                   }catch (InterruptedException e){
                       e.printStackTrace();
                   }
               }
           }).start();

        }else if(view==resideMenu.getMenuItems().get(1)){
            resideMenu.closeMenu();
            Music music=new Music();
            music.setLove(1);
            int i=music.updateAll("id=?","6");
            Log.i("com.mars","update is "+i);

        }else if(view==resideMenu.getMenuItems().get(2)){

        }else if(view==resideMenu.getMenuItems().get(3)){

        }
    }
}
