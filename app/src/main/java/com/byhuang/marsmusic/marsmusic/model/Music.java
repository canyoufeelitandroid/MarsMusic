package com.byhuang.marsmusic.marsmusic.model;

import org.litepal.crud.DataSupport;

/**
 * Created by 64088 on 2017/6/19.
 */

public class Music extends DataSupport {

    private int id;


    //文件中的所属id
    private long _id;
    private String name;
    private String singer;
    //媒体的链接地址
    private String address;
    private long time;
    //所属歌单
    private int belong;
    //专辑
    private String album;
    //大小
    private long size;
    //歌曲标题
    private String title;
    //是否是我的收藏歌曲
    private int love;

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getBelong() {
        return belong;
    }

    public void setBelong(int belong) {
        this.belong = belong;
    }

}
