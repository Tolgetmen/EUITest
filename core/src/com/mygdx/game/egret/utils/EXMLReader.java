
package com.mygdx.game.egret.utils;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.mygdx.game.egret.property.BaseProperty;
import com.mygdx.game.egret.property.ButtonProperty;
import com.mygdx.game.egret.property.CheckBoxProperty;
import com.mygdx.game.egret.property.GroupProperty;
import com.mygdx.game.egret.property.ImageProperty;
import com.mygdx.game.egret.property.LabelProperty;
import com.mygdx.game.egret.property.ScrollerProperty;
import com.mygdx.game.egret.property.ToggleButtonProperty;
import com.mygdx.game.utils.LogHelper;

public class EXMLReader {

	// <?xml version='1.0' encoding='utf-8'?>
	// <e:Skin width="480" height="800" xmlns:e="http://ns.egret-labs.org/egret"
	// xmlns:w="http://ns.egret-labs.org/wing">
	// <w:HostComponent name="egret.gui.SkinnableComponent"/>
	// <w:Declarations/>
	// <e:states>
	// <e:State name="normal"/>
	// <e:State name="disabled"/>
	// </e:states>
	// <e:UIAsset source="loading_bg_jpg" left="0" right="0" top="0"
	// bottom="0"/>
	// <e:Button id="btnStartGame" label.disabled=""
	// skinName.disabled="@ButtonSkin('menu_adventure2_png','menu_adventure_png')"
	// skinName="@ButtonSkin('menu_adventure2_png','menu_adventure_png')"
	// horizontalCenter="0" verticalCenter="-14.5"/>
	// <e:Button id="btnExitGame"
	// skinName="@ButtonSkin('menu_quit2_png','menu_quit_png')"
	// horizontalCenter="-3" y="476"/>
	// <e:Button id="btnShop"
	// skinName="@ButtonSkin('shoopicon1_png','shoopicon2_png')" x="355"
	// y="686"/>
	// <e:Button id="btnRank" x="262"
	// skinName="@ButtonSkin('leadboard1_png','leadboard2_png')" y="638"/>
	// <e:UIAsset source="life2_png" x="400" y="35"/>
	// <e:BitmapLabel id="numLife" font="snum_fnt" width="22" height="24" y="38"
	// text="9" verticalAlign="middle" x="371"/>
	// <e:UIAsset source="gold_png" x="227" y="35"/>
	// <e:BitmapLabel id="numCoin" x="113" y="38" font="snum_fnt" text="00001"
	// textAlign="center"/>
	// </e:Skin>

	private Array<BaseProperty> uiLayer = new Array<BaseProperty>();
	/** UIAsset->Image Button->Button BitmapLabel->LabelAtlas */
	public void parseExml (String path) {
		XmlReader xmlReader = new XmlReader();
		try {
			Element element = xmlReader.parse(Gdx.files.internal(path));
			int size = element.getChildCount();
			uiLayer.add(new GroupProperty(element.getAttributes()));
			for (int i = 0; i < size; i++) {
				Element childElement = element.getChild(i);
                LogHelper.log(getClass(), "parse: " + childElement.getName());
                switch (childElement.getName()){
                    case "e:Image":
                        uiLayer.add(new ImageProperty(childElement.getAttributes()));
                        break;
                    case "e:Button":
                        ButtonProperty btn = new ButtonProperty(childElement.getAttributes());
                        if (childElement.getChildCount() > 0){
                            btn.parseChild(childElement.getChild(0));
                        }

                        uiLayer.add(btn);
                        break;
                    case "e:Label":
                        uiLayer.add(new LabelProperty(childElement.getAttributes()));
                        break;
                    case "e:ToggleButton":
                        ToggleButtonProperty togBtn = new ToggleButtonProperty(childElement.getAttributes());
                        if (childElement.getChildCount() > 0){
                            togBtn.parseChild(childElement.getChild(0));
                        }

                        uiLayer.add(togBtn);
                        break;
                    case "e:Scroller":
                        ScrollerProperty scrollerProperty = new ScrollerProperty(childElement.getAttributes());
                        if (childElement.getChildCount() > 0){
                            scrollerProperty.parseChild(childElement);
                        }

                        uiLayer.add(scrollerProperty);
                        break;
                    case "e:CheckBox":
                        CheckBoxProperty checkBoxProperty = new CheckBoxProperty(childElement.getAttributes());
                        if (childElement.getChildCount() > 0){
                            checkBoxProperty.parseChild(childElement);
                        }

                        uiLayer.add(checkBoxProperty);
                        break;
                }
				/*if ("e:UIAsset".equals(childElement.getName())) {
					uiLayer.add(new UIAssetProperty(childElement.getAttributes()));
				} else if ("e:Button".equals(childElement.getName())) {
					uiLayer.add(new ButtonProperty(childElement.getAttributes()));
				} else if ("e:BitmapLabel".equals(childElement.getName())) {
					uiLayer.add(new BitmapLabelProperty(childElement.getAttributes()));
				} else if ("e:Label".equals(childElement.getName())) {
					uiLayer.add(new LabelProperty(childElement.getAttributes()));
				} else if ("e:Panel".equals(childElement.getName())) {
					// 容器
					uiLayer.add(new PanelProperty(childElement.getAttributes()));
				} else if ("e:Rect".equals(childElement.getName())) {
					// 纯色面板
					uiLayer.add(new RectProperty(childElement.getAttributes()));
				} else if ("e:CheckBox".equals(childElement.getName())) {
					// 复选框
					uiLayer.add(new CheckBoxProperty(childElement.getAttributes()));
				} else if ("e:ProgressBar".equals(childElement.getName())) {
					// 进度条
					uiLayer.add(new ProgressBarProperty(childElement.getAttributes()));
				} else if ("e:EditText".equals(childElement.getName())) {
					// 编辑框
					uiLayer.add(new EditTextProperty(childElement.getAttributes()));
				}
				*/
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Gdx.app.error(getClass().getSimpleName(), "parseEXML : " + e.getLocalizedMessage());
		}
	}

	public Array<BaseProperty> getUILayerProperty () {
		return uiLayer;
	}

}
