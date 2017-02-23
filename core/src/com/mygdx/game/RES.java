package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class RES {
    public List<GroupRes> groups;
    public List<ResourcesRes> resources;

    @Override
    public String toString() {
        return "RES{" +
                "groups=" + groups +
                ", resources=" + resources +
                '}';
    }
}

class GroupRes{
    String keys;
    String name;

    @Override
    public String toString() {
        return "GroupRes{" +
                "keys='" + keys + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

class ResourcesRes{
    String name;
    String type;
    String url;

    @Override
    public String toString() {
        return "ResourcesRes{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}