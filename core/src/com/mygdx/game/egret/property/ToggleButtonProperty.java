
package com.mygdx.game.egret.property;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

/**
 * 
 * 按钮
 * @author haocao
 *
 */
public class ToggleButtonProperty extends BaseProperty {
    public String name;

	public ToggleButtonProperty(ObjectMap<String, String> attributes) {
		super(attributes);
        name = attributes.get("name");
	}

    public ImageProperty imageProperty[];
    public void parseChild(XmlReader.Element child) {
        XmlReader.Element element = child.getChildByName("e:Skin");
        minWidth = Integer.parseInt(element.getAttribute("minHeight"));
        minHeight = Integer.parseInt(element.getAttribute("minWidth"));

        imageProperty = new ImageProperty[element.getChildCount()];
        for (int i = 0; i < element.getChildCount(); i++) {
            imageProperty[i] = new ImageProperty(element.getChild(i).getAttributes());
        }
    }
}
