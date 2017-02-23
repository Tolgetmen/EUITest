package com.mygdx.game.egret;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.TGame;
import com.mygdx.game.egret.property.BaseProperty;
import com.mygdx.game.egret.property.ButtonProperty;
import com.mygdx.game.egret.property.CheckBoxProperty;
import com.mygdx.game.egret.property.GroupProperty;
import com.mygdx.game.egret.property.ImageProperty;
import com.mygdx.game.egret.property.LabelProperty;
import com.mygdx.game.egret.property.PanelProperty;
import com.mygdx.game.egret.property.ScrollerProperty;
import com.mygdx.game.egret.property.ToggleButtonProperty;
import com.mygdx.game.egret.view.My2CheckBox;
import com.mygdx.game.egret.view.StateButton;
import com.mygdx.game.egret.view.TScrollPane;
import com.mygdx.game.egret.view.ToggleButton;
import com.mygdx.game.utils.LogHelper;

import net.mwplay.nativefont.NativeLabel;

/**
 * Created by Administrator on 2017/2/22.
 */

public class BaseSkinUI extends Group {
    public AssetManager assetManager;
    String exmlPath;

    public BaseSkinUI(AssetManager assetManager, String exmlPath) {
        this.assetManager = assetManager;
        this.exmlPath = exmlPath;
        init(Egret.loadExmlProperty(exmlPath));
    }

    public BaseSkinUI(AssetManager assetManager, Array<BaseProperty> propertys) {
        this.assetManager = assetManager;
        init(propertys);
    }

    private void init(Array<BaseProperty> propertys) {
        for (BaseProperty property : propertys) {
            if (property instanceof PanelProperty) {
                Group group = new Group();
                this.addActor(group);
            } else if (property instanceof GroupProperty) {
                parseBaseProperty(this, property);
            } else if (property instanceof ImageProperty) {
                Image actor = new Image();
                this.addActor(actor);
                parseImageProperty(actor, (ImageProperty) property);
                parseBaseProperty(actor, property);
            } else if (property instanceof LabelProperty) {
                LabelProperty labelProperty = (LabelProperty) property;
                // 文本
                NativeLabel label0 = TGame.getInstance().getNativeLabel("", labelProperty.size);
                label0.setText(labelProperty.text);
                label0.setWrap(labelProperty.multi);
                parseLabelProperty(label0, labelProperty);
                parseBaseProperty(label0, property);
                label0.setText(label0.getText().replace("\\n", ""));
                this.addActor(label0);
            } else if (property instanceof ButtonProperty) {
                StateButton stateButton = parseButtonProperty((ButtonProperty) property);
                this.addActor(stateButton);
                parseBaseProperty(stateButton, property);

            } else if (property instanceof ToggleButtonProperty) {
                ToggleButton toggleButton = parseToggleButtonProperty((ToggleButtonProperty) property);
                toggleButton.setName(((ToggleButtonProperty) property).name);
                parseBaseProperty(toggleButton, property);
                this.addActor(toggleButton);
            } else if (property instanceof ScrollerProperty) {
                TScrollPane scrollPane = null;
                ScrollerProperty scrollerProperty = (ScrollerProperty) property;
                if (scrollerProperty.isVertical) {
                    VerticalGroup verticalGroup = new VerticalGroup();
                    scrollPane = new TScrollPane(verticalGroup);
                } else {
                    HorizontalGroup verticalGroup = new HorizontalGroup();
                    scrollPane = new TScrollPane(verticalGroup);
                }

                addActor(scrollPane);
                parseScrollerProperty(scrollPane, scrollerProperty);
                parseBaseProperty(scrollPane, property);
            } else if (property instanceof CheckBoxProperty) {
                My2CheckBox my2CheckBox = parse2CheckBoxProperty((CheckBoxProperty) property);
                addActor(my2CheckBox);
                parseBaseProperty(my2CheckBox, property);
            }
        }
    }

    private My2CheckBox parse2CheckBoxProperty(CheckBoxProperty property) {
        TextureRegion di = TGame.getInstance().findRegion(property.di);
        TextureRegion check = TGame.getInstance().findRegion(property.checked);

        My2CheckBox my2CheckBox = new My2CheckBox(di, check);
        if (property.selectedData != null) {
            my2CheckBox.setUserObject(property.selectedData);
        } else {
            my2CheckBox.setChecked(property.selected);
        }

        return my2CheckBox;
    }

    public <T extends Actor> T findItem(String name) {
        Array<Actor> children = this.getChildren();
        for (int i = 0, n = children.size; i < n; i++) {
            LogHelper.log(getClass(), name + " --- " + children.get(i).getUserObject());
            if (name.equals(children.get(i).getUserObject())) {
                return (T) children.get(i);
            }
        }
        return null;
    }

    private void parseScrollerProperty(TScrollPane scrollPane, ScrollerProperty scrollerProperty) {
        if (scrollerProperty.itemRenderXml != null) {
            scrollPane.itemRenderXml = scrollerProperty.itemRenderXml;
        }

        if (scrollerProperty.scrollbar != null){
            if (scrollerProperty.isVertical){
                scrollPane.getStyle().vScrollKnob = TGame.getInstance().findDrawable(scrollerProperty.scrollbar);
            }else {
                scrollPane.getStyle().hScrollKnob = TGame.getInstance().findDrawable(scrollerProperty.scrollbar);
            }
        }
    }

    private ToggleButton parseToggleButtonProperty(ToggleButtonProperty property) {
        ToggleButton toggleButton = new ToggleButton(property.imageProperty[1], property.imageProperty[0]);
        // toggleButton.setSize(property.minWidth, property.minHeight);
        return toggleButton;
    }

    private StateButton parseButtonProperty(final ButtonProperty property) {
        Texture upTexture = TGame.getInstance().textures.get(property.upSource);
        Texture downTexture = TGame.getInstance().textures.get(property.downSource);
        TextureRegion up = new TextureRegion((upTexture));
        TextureRegion down = new TextureRegion((downTexture));

        final StateButton stateButton = new StateButton(up, down, null);
        stateButton.setOrigin(Align.center);

        stateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                LogHelper.log("...");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttonID) {
                stateButton.setScale(property.oldW, property.oldH);
                return super.touchDown(event, x, y, pointer, buttonID);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int buttonID) {
                super.touchUp(event, x, y, pointer, buttonID);
                stateButton.setScale(property.newW, property.newH);
            }
        });

        return stateButton;
    }

    private void parseLabelProperty(Label label, LabelProperty labelUIProperty) {
        if (labelUIProperty.text.contains("{") && labelUIProperty.text.contains("}")) {
            label.setUserObject(labelUIProperty.text.replace("{data.", "").replace("}", ""));
        }

        Color color = Color.valueOf((labelUIProperty).textColor);
        label.setColor(color);

        String align = labelUIProperty.textAlign;
        // 文字垂直方向：verticalAlign ： top middle bottom
        // 文字水平方向：textAlign： left middle right

        int gdxAlign_1 = Integer.MAX_VALUE;
        int gdxAlign_2 = Integer.MAX_VALUE;
        if ("left".equals(align)) {
            gdxAlign_1 = Align.left;
        } else if ("center".equals(align)) {
            gdxAlign_1 = Align.center;
        } else if ("right".equals(align)) {
            gdxAlign_1 = Align.right;
        }

        align = labelUIProperty.verticalAlign;
        if ("top".equals(align)) {
            gdxAlign_2 = Align.top;
        } else if ("middle".equals(align)) {
            gdxAlign_2 = Align.center;
        } else if ("bottom".equals(align)) {
            gdxAlign_2 = Align.bottom;
        }

        if (gdxAlign_1 < Integer.MAX_VALUE) {
            label.setAlignment(gdxAlign_1 | gdxAlign_2);
        } else if (gdxAlign_2 < Integer.MAX_VALUE) {
            label.setAlignment(gdxAlign_2);
        }
    }

    private void parseImageProperty(Image actor, ImageProperty property) {
        if (property.source != null && property.source.contains("{") && property.source.contains("}")) {
            actor.setUserObject(property.source.replace("{data.", "").replace("}", ""));
        } else {
            Texture texture = TGame.getInstance().textures.get(property.source);
            if (texture != null) {
                Drawable drawable;
                if (property.ninePatch != null) {
                    int left = property.ninePatch[0];
                    int top = property.ninePatch[1];
                    int right = texture.getWidth() - left - property.ninePatch[2];
                    int bottom = texture.getHeight() - top - property.ninePatch[3];
                    drawable = TGame.getInstance().getNineDrawable(texture, left, right, top, bottom);
                } else {
                    drawable = new TextureRegionDrawable(new TextureRegion(texture));
                }

                actor.setDrawable(drawable);
            }
        }

        actor.setScaling(Scaling.stretch);
        actor.setAlign(Align.center);
        actor.setSize(actor.getPrefWidth(), actor.getPrefHeight());
    }

    private void parseBaseProperty(Actor actor, BaseProperty property) {
        if (actor instanceof Group) {
            actor.setTouchable(property.touchEnabled ? Touchable.enabled : Touchable.childrenOnly);
        } else {
            actor.setTouchable(property.touchEnabled ? Touchable.enabled : Touchable.disabled);
        }
        // 锚点默认中心
        actor.setOrigin(Align.center);
        // 名称
        if (property.id != null) {
            actor.setName(property.id);
        }

        if (property.width != null) {
            if (property.width.contains("%")) {
                int percent = Integer.parseInt(property.width.replace("%", ""));
                actor.setWidth(actor.getParent().getWidth() * percent / 100f);
            } else {
                int width = Integer.parseInt(property.width);
                actor.setWidth(width);
            }
        }

        if (property.height != null) {
            if (property.height.contains("%")) {
                int percent = Integer.parseInt(property.width.replace("%", ""));
                actor.setHeight(actor.getParent().getHeight() * percent / 100f);
            } else {
                int height = Integer.parseInt(property.height);
                actor.setHeight(height);
            }
        }

        if (property.left != -1) {

        }

        if (property.right != -1) {

        }

        if (property.top != -1) {

        }

        if (property.bottom != -1) {

        }

        // 坐标转换，egret以左上角，libgdx以左下角
        float x = property.x;
        float y = getHeight() - property.y - actor.getHeight();
        if (property.left != -1) {
            x += property.left;
        }
        if (property.top != -1) {
            y -= property.top;
        }

        // 坐标
        actor.setPosition(x, y);

        // 垂直居中
        if (property.verticalCenter != -1) {
            actor.setY(getHeight() / 2 - actor.getHeight() / 2 - property.verticalCenter);
        }
        // 水平居中
        if (property.horizontalCenter != -1) {
            actor.setX(getWidth() / 2 - actor.getWidth() / 2 + property.horizontalCenter);
        }

        // 缩放
        actor.setScale(property.scaleX, property.scaleY);
        /*if (actor instanceof Label) {
            ((Label)actor).setFontScale(baseUIProperty._scaleX, baseUIProperty._scaleY);
        }*/

        // 旋转
        if (property.rotation != 0) {
            actor.rotateBy(property.rotation);
        }

        // 可见性
        actor.setVisible(property.visible);
    }
}
