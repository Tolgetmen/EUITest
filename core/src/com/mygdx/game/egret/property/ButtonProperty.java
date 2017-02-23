
package com.mygdx.game.egret.property;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.utils.LogHelper;

/**
 * 
 * 按钮
 * @author haocao
 *
 */
public class ButtonProperty extends BaseProperty {

	public String downSource, upSource;
    public boolean hasSize = true;

	public ButtonProperty (ObjectMap<String, String> attributes) {
		super(attributes);
	}

    public float oldW;
    public float oldH;
    public float newW;
    public float newH;
    public void parseChild(XmlReader.Element child) {
        XmlReader.Element element = child.getChildByName("e:Skin");
        XmlReader.Element image = element.getChildByName("e:Image");
        upSource = image.getAttribute("source");
        downSource = image.getAttribute("source.down");

        oldW = getValueByPercent(image.get("width"));
        oldH = getValueByPercent(image.get("height"));
        newW = getValueByPercent(image.get("width.down"));
        newH = getValueByPercent(image.get("height.down"));

        if (width == null){
            hasSize = false;
        }
    }

    private float getValueByPercent(String value){
        value = value.replace("%", "");
        return Integer.parseInt(value) / 100f;
    }
}
