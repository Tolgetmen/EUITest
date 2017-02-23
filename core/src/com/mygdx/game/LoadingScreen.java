package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.egret.view.TImage;
import com.mygdx.game.utils.LogHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/22.
 */

public class LoadingScreen implements Screen {
    Stage stage;
    public AssetManager assetManager;
    RES res;
    private Map<String, List<String>> groups = new HashMap<String, List<String>>();
    @Override
    public void show() {
        stage = new Stage(new StretchViewport(480, 800));

        assetManager = new AssetManager();
        TGame.getInstance().assetManager = assetManager;
        loadUI();
    }

    TImage loading = null;
    private void loadUI() {
        TImage.loadInerImg("art/loading/loading.jpg").size(480, 800).addTo(stage);
        TImage.loadInerImg("art/loading/toast-bg.png").addTo(stage).toStageCenter(stage);
        loading = TImage.loadInerImg("art/loading/loading2.png").addTo(stage).toStageCenter(stage);

        Json json = new Json();
        res = json.fromJson(RES.class, Gdx.files.internal("default.res.json"));

        for (GroupRes groupRes: res.groups){
            List<String> item = Arrays.asList(groupRes.keys.split(","));
            groups.put(groupRes.name, item);
        }

        for (ResourcesRes resourceData: res.resources){
            if (resourceData.type.equals("image")){
                assetManager.load(resourceData.url, Texture.class);
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        if (assetManager.update()){
            parse();
            TGame.getInstance().setScreen(new MainScreen());
        }else {
            loading.rotateBy(5);
            System.out.println("----------->" + assetManager.getProgress());
        }
    }

    public void parse(){
        for (ResourcesRes resourceData: res.resources){
            if (resourceData.type.equals("image")){
                TGame.getInstance().textures.put(resourceData.name, assetManager.get(resourceData.url, Texture.class));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
