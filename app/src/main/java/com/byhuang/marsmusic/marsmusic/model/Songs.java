package com.byhuang.marsmusic.marsmusic.model;

import org.litepal.crud.DataSupport;

/**
 * Created by 64088 on 2017/6/19.
 */

public class Songs extends DataSupport {
    private int id;
    private int belongId;
    private String picAddress;
    private String lyric;

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }


}
