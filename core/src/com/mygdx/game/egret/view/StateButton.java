package com.mygdx.game.egret.view;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pools;

/**
 * Created by Administrator on 2017/2/22.
 */

public class StateButton extends Image{
    private boolean isChecked;
    private ClickListener clickListener;
    private Drawable imageUp, imageDown, imageChecked;
    public StateButton(TextureRegion imageUp, TextureRegion imageDown, TextureRegion imageChecked) {
        super();
        this.imageUp = new TextureRegionDrawable(imageUp);
        this.imageDown = new TextureRegionDrawable(imageDown);
        this.imageChecked = (imageChecked == null) ? null : new TextureRegionDrawable(imageChecked);
        setSize(imageUp.getRegionWidth(), imageUp.getRegionHeight());
        setDrawable(this.imageUp);
        setTouchable(Touchable.enabled);
        addListener(clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setChecked(!isChecked);
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
   /* public StateButton(Sound buttonSound, TextureRegion imageUp, TextureRegion imageDown) {
        this(buttonSound, imageUp, imageDown, null);
    }
    public StateButton(TextureRegion imageUp, TextureRegion imageDown, TextureRegion imageChecked) {
        this(null, imageUp, imageDown, imageChecked);
    }
    public StateButton(TextureRegion imageUp, TextureRegion imageDown) {
        this(null, imageUp, imageDown, null);
    }*/

    public void setChecked(boolean isChecked) {
        if (this.isChecked == isChecked)
            return;
        this.isChecked = isChecked;
        ChangeListener.ChangeEvent changeEvent = Pools.obtain(ChangeListener.ChangeEvent.class);
        if (fire(changeEvent))
            this.isChecked = !isChecked;
        Pools.free(changeEvent);
    }
    public boolean isChecked() {
        return isChecked;
    }
    public boolean isPressed() {
        return clickListener.isVisualPressed();
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        updateImage();
    }
    private void updateImage() {
        Drawable drawable = null;
        if (isPressed() && imageDown != null)
            drawable = imageDown;
        else if (isChecked && imageChecked != null)
            drawable = imageChecked;
        else if (imageUp != null)
            drawable = imageUp;
        setDrawable(drawable);
    }

}
