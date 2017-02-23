
package com.mygdx.game.egret.property;

import com.badlogic.gdx.utils.ObjectMap;

public abstract class BaseProperty {
    public String id = null;
    public boolean isPopUp = false;
    public Object owner = null;
    public boolean updateCompletePendingFlag = false;
    public boolean initialized = false;
    public int nestLeveler = 0;
    public boolean enabled = true;
    public String width;
    public String height;
    public int minWidth = 0;
    public int maxWidth = 10000;
    public int minHeight = 0;
    public int maxHeight = 10000;
    public int measuredWidth = 0;
    public int measuredHeight = 0;
    public float left = -1;
    public float right = -1;
    public float top = -1;
    public float bottom = -1;
    public float horizontalCenter = -1;
    public float verticalCenter = -1;
    public float percentWidth = -1;
    public float percentHeight = -1;
    public boolean includeInLayout = true;

    public boolean touchEnabled = true;
    public boolean visible = true;

    public float x = 0;
    public float y = 0;
    public float scaleX = 1;
    public float scaleY = 1;
    public float anchorX = 0;
    public float anchorY = 0;
    public int rotation = 0;
    public float alpha = 1;

    public BaseProperty(ObjectMap<String, String> attributes) {

        if (attributes.containsKey("x")) this.x = Float.valueOf(attributes.get("x"));

        if (attributes.containsKey("y")) this.y = Float.valueOf(attributes.get("y"));

        if (attributes.containsKey("width")) {
            this.width = attributes.get("width");
        }

        if (attributes.containsKey("height"))
            this.height = attributes.get("height");

        if (attributes.containsKey("id")) this.id = attributes.get("id");

        if (attributes.containsKey("horizontalCenter"))
            this.horizontalCenter = Float.valueOf(attributes.get("horizontalCenter"));

        if (attributes.containsKey("verticalCenter"))
            this.verticalCenter = Float.valueOf(attributes.get("verticalCenter"));

        if (attributes.containsKey("left")) this.left = Float.valueOf(attributes.get("left"));

        if (attributes.containsKey("top")) this.top = Float.valueOf(attributes.get("top"));

        if (attributes.containsKey("right")) this.right = Float.valueOf(attributes.get("right"));

        if (attributes.containsKey("bottom")) this.bottom = Float.valueOf(attributes.get("bottom"));

        if (attributes.containsKey("touchEnabled"))
            this.touchEnabled = Boolean.valueOf(attributes.get("touchEnabled"));

        if (attributes.containsKey("scaleX")) this.scaleX = Float.valueOf(attributes.get("scaleX"));

        if (attributes.containsKey("scaleY")) this.scaleY = Float.valueOf(attributes.get("scaleY"));

        if (attributes.containsKey("visible"))
            this.visible = Boolean.valueOf(attributes.get("visible"));
    }
}
