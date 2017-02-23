package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by tian on 2016/9/18.
 */
public class LogHelper {
    public static final boolean debug = true;

    public final static void log(Object msg){
        if (debug)
        Gdx.app.log("xxxx", msg.toString());
    }

    public final static void log(boolean yes, Object msg){
        if (debug && yes)
            Gdx.app.log("xxxx", msg.toString());
    }

    public static void log(Class<?> mClass, Object str) {
        if (debug) {
            Gdx.app.log(mClass.getName(), str + "");
        }
    }
}
