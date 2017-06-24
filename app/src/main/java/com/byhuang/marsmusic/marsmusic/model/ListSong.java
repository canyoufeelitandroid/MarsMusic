package com.byhuang.marsmusic.marsmusic.model;

/**
 * Created by 64088 on 2017/6/21.
 * this is a bean for adapter and listview,not litepal
 */

public class ListSong {
    private String title;
    private String singer;

    public ListSong(String title1,String singer1){
        this.title=title1;
        this.singer=singer1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }




}
