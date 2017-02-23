
package com.mygdx.game.egret.property;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.utils.LogHelper;

public class CheckBoxProperty extends BaseProperty {
    public boolean selected = false;
    public String selectedData;

    public String di;
    public String checked;

    public CheckBoxProperty(ObjectMap<String, String> attributes) {
        super(attributes);

        String selectedStr = attributes.get("selected");

        selected = selectedStr.equals("true");
        if (selectedStr.contains("{") && selectedStr.contains("}")) {
            selectedData = selectedStr.replace("{data.", "").replace("}", "");
        }
    }

    public void parseChild(XmlReader.Element root) {
        /*<e:CheckBox id="cb" x="383" y="23" width="29" height="30" selected="{data.checked}" >
        <e:skinName>
        <e:Skin states="up,down,disabled,upAndSelected,downAndSelected,disabledAndSelected">
        <e:Group width="100%" height="100%">
        <e:Image fillMode="scale" alpha="1" alpha.disabled="0.5" alpha.down="0.7" source="checkNo_png" source.upAndSelected="checked_png" source.downAndSelected="checked_png" source.disabledAndSelected="checked_png" bottom="0" left="0" width="36"/>
        </e:Group>
        </e:Skin>
        </e:skinName>
        </e:CheckBox>*/
        XmlReader.Element element = root.getChild(0).getChild(0).getChild(0).getChild(0);
        ObjectMap<String, String> objectMap = element.getAttributes();
        di = objectMap.get("source");
        checked = objectMap.get("source.upAndSelected");
    }
}
