package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.egret.BaseSkinUI;
import com.mygdx.game.utils.LogHelper;

/**
 * Created by Administrator on 2017/2/22.
 */

public class AboutGroup extends BaseSkinUI {
    public AboutGroup(AssetManager assetManager, String exmlPath) {
        super(assetManager, exmlPath);
        LogHelper.log(getClass(), "----->AboutGroup:" + exmlPath);
    }
}
