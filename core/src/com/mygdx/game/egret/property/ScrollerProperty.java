
package com.mygdx.game.egret.property;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

/**
 * 
 * 面板
 * 
 * @author haocao
 *
 */
public class ScrollerProperty extends BaseProperty {
    public String itemRenderXml;
    public float itemHeight = 0;
    public boolean isVertical = true;
    public String scrollbar;

	public ScrollerProperty(ObjectMap<String, String> attributes) {
		super(attributes);
	}

	public void parseChild(XmlReader.Element root) {
        /*<e:viewport>
        <e:List id="listGoods" height="110">
        <e:layout>
        <e:VerticalLayout/>
        </e:layout>
        </e:List>
        </e:viewport>
        <e:Skin>
        <e:VScrollBar id="verticalScrollBar" width="8" minHeight="100%" height="100%" right="0">
        <e:Skin>
        <e:Image id="thumb" width="8" source="hScrollBar02_png"  scale9Grid="1,1,4,4"/>
        </e:Skin>
        </e:VScrollBar>
        </e:Skin>*/
        for (int i = 0; i < root.getChildCount(); i++){
            if (root.getChild(i).getName().equals("e:viewport")){
                parseViewport(root.getChild(i).getChild(0));
            }else if (root.getChild(i).getName().equals("e:Skin")){
                parseSkin(root.getChild(i));
            }
        }
	}

    private void parseSkin(XmlReader.Element element){
        XmlReader.Element sb = element.getChild(0).getChild(0).getChild(0);
        ObjectMap<String, String> objectMap = sb.getAttributes();
        scrollbar = objectMap.get("source");
    }

    private void parseViewport(XmlReader.Element element){
        ObjectMap<String, String> objectMap = element.getAttributes();
        if (objectMap.containsKey("itemRendererSkinName")){
            itemRenderXml = objectMap.get("itemRendererSkinName");
        }

        itemHeight = Float.parseFloat(objectMap.get("height"));
        XmlReader.Element layout = element.getChildByName("e:layout").getChildByName("e:HorizontalLayout");
        if (layout != null){
            isVertical = false;
        }
    }

}
