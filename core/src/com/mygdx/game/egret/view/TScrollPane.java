package com.mygdx.game.egret.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.TGame;
import com.mygdx.game.egret.BaseSkinUI;
import com.mygdx.game.egret.Egret;
import com.mygdx.game.egret.property.BaseProperty;
import com.mygdx.game.egret.utils.EXMLReader;
import com.mygdx.game.utils.LogHelper;

import net.mwplay.nativefont.NativeLabel;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/23.
 */

public class TScrollPane extends ScrollPane {
    Group layout;
    public Array<Map<String, String>> dataProvider;
    public String itemRenderXml;

    public TScrollPane(Actor widget) {
        super(widget);
        layout = (Group) widget;
    }

    public TScrollPane(Actor widget, Skin skin) {
        super(widget, skin);
        layout = (Group) widget;
    }

    public TScrollPane(Actor widget, Skin skin, String styleName) {
        super(widget, skin, styleName);
        layout = (Group) widget;
    }

    public TScrollPane(Actor widget, ScrollPaneStyle style) {
        super(widget, style);
        layout = (Group) widget;
    }

    public void setData(Array<Map<String, String>> dataProvider) {
        this.dataProvider = dataProvider;

        if (itemRenderXml != null) {
            parseItemRender(itemRenderXml);
        }
    }

    Array<BaseProperty> itemPropertys;

    private void parseItemRender(String itemRenderXml) {
        if (itemPropertys == null)
            itemPropertys = Egret.loadExmlProperty("custom_skins/" + itemRenderXml);

        /*<e:Skin class="profileSkillListIRSkin" width="87" height="120" xmlns:e="http://ns.egret.com/eui">
        <e:Image height="88" width="87" x="1" y="5" source="skillItemFrame_png"/>
        <e:Image height="71" width="65" x="11" y="14" source="{data.icon}"/>
        <e:Label x="7" y="96" width="76" size="18" textColor="0xFFAC1C" text="{data.name}"
        stroke="1" strokeColor="0" fontFamily="微软雅黑" textAlign="center" verticalAlign="middle"/>
        </e:Skin>*/

        for (int i = 0; i < dataProvider.size; i++) {
            Map<String, String> data = dataProvider.get(i);
            BaseSkinUI baseSkinUI = new BaseSkinUI(TGame.getInstance().assetManager, itemPropertys);
            for (int j = 0; j < data.size(); j++) {
                Actor actor = baseSkinUI.findItem(data.keySet().toArray()[j].toString());
                if (actor instanceof My2CheckBox){
                    LogHelper.log(getClass(), Boolean.parseBoolean(data.values().toArray()[j].toString()));
                    ((My2CheckBox) actor).setChecked(Boolean.parseBoolean(data.values().toArray()[j].toString()));
                }else if (actor instanceof Image) {
                    ((Image) actor).setDrawable(TGame.getInstance().findDrawable(data.values().toArray()[j].toString()));
                }else if (actor instanceof NativeLabel){
                    ((NativeLabel) actor).setText(data.values().toArray()[j].toString());
                }
            }

            layout.addActor(baseSkinUI);
        }
    }
}
