package com.mygdx.game.egret.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.TGame;
import com.mygdx.game.egret.property.ImageProperty;

/**
 * Created by Administrator on 2017/2/22.
 */

public class ToggleButton extends Image {
    private OnCheckedListener onCheckedListener;
    public ToggleButtonGroup buttonGroup;

    public boolean isChecked = false;
    public boolean enable = true;

    public Drawable di, real, checked;
    public Vector2 offsetUp = Vector2.Zero, offsetDown = Vector2.Zero;

    public ToggleButton(ImageProperty up, ImageProperty down) {
        di = getNinePathDrawable(down, down.source);
        checked = getNinePathDrawable(down, down.sourceDisabledAndSelected);
        real = getNinePathDrawable(up, up.source);

        //offsetDown.set(down.x, down.y);
        //offsetUp.set(up.x, up.y);

        setSize(real.getMinWidth(), real.getMinHeight());
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (enable) {
                    setChecked(!isChecked());
                }
            }
        });
    }

    public void setOnCheckedListener(OnCheckedListener clickListener) {
        this.onCheckedListener = clickListener;
    }

    public boolean isChecked() {
        return isChecked;
    }

    /*public void setChecked(boolean checked) {
        isChecked = checked;
    }*/

    public void setChecked(boolean checked) {
        if (this.isChecked == checked) return;
        if (buttonGroup != null && !buttonGroup.canCheck(this, checked)) return;

        this.isChecked = checked;

        if (onCheckedListener != null) {
            onCheckedListener.onChecked(isChecked());
        }
    }

    public ToggleButtonGroup getButtonGroup() {
        return buttonGroup;
    }


    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public Drawable getNinePathDrawable(ImageProperty imageProperty, String name) {
        Texture texture = TGame.getInstance().textures.get(name);
        if (texture != null) {
            if (imageProperty.ninePatch != null) {
                int left = imageProperty.ninePatch[0];
                int top = imageProperty.ninePatch[1];
                int right = texture.getWidth() - left - imageProperty.ninePatch[2];
                int bottom = texture.getHeight() - top - imageProperty.ninePatch[3];
                return TGame.getInstance().getNineDrawable(texture, left, right, top, bottom);
            } else {
                return new TextureRegionDrawable(new TextureRegion(texture));
            }
        }

        return null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (di instanceof NinePatchDrawable) {
            ((NinePatchDrawable) di).draw(batch, getX() + offsetDown.x, getY() + offsetDown.x, getOriginX(), getOriginY(), di.getMinWidth(), di.getMinHeight(), getScaleX(), getScaleY(), getRotation());
        }

        if (isChecked && checked instanceof NinePatchDrawable) {
            ((NinePatchDrawable) checked).draw(batch, getX() + offsetDown.x, getY() + offsetDown.x, getOriginX(), getOriginY(), checked.getMinWidth(), checked.getMinHeight(), getScaleX(), getScaleY(), getRotation());
        }

        if (real instanceof TextureRegionDrawable) {
            ((TextureRegionDrawable) real).draw(batch, getX() + offsetUp.x, getY() + offsetUp.x, getOriginX(), getOriginY(), real.getMinWidth(), real.getMinHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    public interface OnCheckedListener {
        public void onChecked(boolean checked);
    }
}
