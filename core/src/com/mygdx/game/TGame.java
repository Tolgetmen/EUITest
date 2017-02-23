package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import net.mwplay.nativefont.NativeFont;
import net.mwplay.nativefont.NativeFontPaint;
import net.mwplay.nativefont.NativeLabel;

import java.util.HashMap;
import java.util.Map;

public class TGame extends Game {
    private static TGame instance = null;
    public static Map<String, NativeFont> fonts = new HashMap<>();
    public static final String DEFAULT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*";

    public AssetManager assetManager;

    public TGame(){
        super();
    }

	@Override
	public void create () {
        instance = this;

        NativeFont font = new NativeFont(new NativeFontPaint(30));
        font.appendText(DEFAULT_CHARS);
        this.fonts.put("font", font);
        NativeFont font20 = new NativeFont(new NativeFontPaint(20));
        font20.appendText(DEFAULT_CHARS);
        fonts.put("font20", font20);

        setScreen(new LoadingScreen());
	}

    public static TGame getInstance() {
        if (instance == null){
            return new TGame();
        }
        return instance;
    }

    public static NativeFont getDefaultFont() {
        return fonts.get("font");
    }
    public static NativeFont getSmallFont() {
        return fonts.get("font20");
    }

    public Map<String, Texture> textures = new HashMap<String, Texture>();

	@Override
	public void dispose () {
        assetManager.dispose();
	}


    public NinePatch getNinePatch(Texture texture, int left, int right, int up, int down) {
        return new NinePatch(texture, left, right, up, down);
    }

    public NinePatchDrawable getNineDrawable(Texture texture, int left, int right, int up, int down) {
        return new NinePatchDrawable(new NinePatch(texture, left, right, up, down));
    }

    public NativeLabel getNativeLabel(CharSequence defaultStr, NativeFont font) {
        NativeLabel nativeLabel = new NativeLabel(defaultStr, font);
        nativeLabel.setColor(Color.WHITE);

        return nativeLabel;
    }

    public NativeLabel getNativeLabel(CharSequence defaultStr, int size) {
        if (fonts.get("" + size) == null){
            NativeFont font = new NativeFont(new NativeFontPaint(size));
            font.appendText(DEFAULT_CHARS);
            fonts.put(size + "", font);
        }

        return new NativeLabel(defaultStr, fonts.get("" + size), Color.WHITE);
    }

    public NativeLabel getNativeLabel(Object defaultStr) {
        NativeLabel nativeLabel = new NativeLabel(defaultStr.toString(), getDefaultFont());
        nativeLabel.setColor(Color.WHITE);

        return nativeLabel;
    }

    public TextureRegion findRegion(String name){
        try{
            return new TextureRegion(assetManager.get(name, Texture.class));
        }catch (Exception e){
            return new TextureRegion(TGame.getInstance().textures.get(name));
        }
    }

    public Drawable findDrawable(String name){
        return new TextureRegionDrawable(findRegion(name));
    }
}
