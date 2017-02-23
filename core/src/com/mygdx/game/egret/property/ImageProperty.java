package com.mygdx.game.egret.property;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Administrator on 2017/2/22.
 */

public class ImageProperty extends BaseProperty {

    public int[] ninePatch;
    public String source, sourceDown, sourceDisabledAndSelected;

    public ImageProperty(ObjectMap<String, String> attributes) {
        super(attributes);
        //String skinInfo = attributes.get("skinName");
        source = attributes.get("source");
        if (attributes.containsKey("source.down")) {
            sourceDown = attributes.get("source.down");
        }

        if (attributes.containsKey("source.disabledAndSelected")) {
            sourceDisabledAndSelected = attributes.get("source.disabledAndSelected");
        }

        try {
            String[] info = attributes.get("scale9Grid").split(",");
            ninePatch = new int[4];
            // x
            ninePatch[0] = Integer.valueOf(info[0]);
            // y
            ninePatch[1] = Integer.valueOf(info[1]);
            // w
            ninePatch[2] = Integer.valueOf(info[2]);
            // h
            ninePatch[3] = Integer.valueOf(info[3]);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
