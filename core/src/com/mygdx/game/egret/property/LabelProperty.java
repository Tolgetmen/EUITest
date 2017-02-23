
package com.mygdx.game.egret.property;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * 文本标签
 *
 * @author haocao
 */
public class LabelProperty extends BaseProperty {
    public String text;
    public String verticalAlign;
    public String textAlign;
    public String textColor;
    public int size;
    public boolean multi = true;

    public LabelProperty(ObjectMap<String, String> attributes) {
        super(attributes);

        text = attributes.get("text");

        verticalAlign = attributes.get("verticalAlign");
        textAlign = attributes.get("textAlign");
        // 文字垂直方向：verticalAlign ： top middle bottom
        // 文字水平方向：textAlign： left middle right
        // textColor="0xFF0000"
        if (attributes.containsKey("textColor")) {
            // 去除开头0x
            textColor = attributes.get("textColor").substring(2);
        } else {
            // 默认黑色
            textColor = "FFFFFF";
        }

        if (attributes.containsKey("size")) {
            size = Integer.valueOf(attributes.get("size"));
        } else {
            size = 30;
        }

        if (attributes.containsKey("multiline")) {
            multi = attributes.get("multiline").equals("true");
        }
    }

}
