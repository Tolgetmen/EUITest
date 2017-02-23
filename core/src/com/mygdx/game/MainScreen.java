package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.egret.view.TImage;
import com.mygdx.game.egret.view.TScrollPane;
import com.mygdx.game.egret.view.ToggleButton;
import com.mygdx.game.egret.view.ToggleButtonGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MainScreen implements Screen {
    Stage stage;
    Group home, profile, hero, good, about;
    ToggleButton mbtnProfile ;
    ToggleButton mbtnHeros ;
    ToggleButton mbtnGoods ;
    ToggleButton mbtnAbout ;

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(480, 800));
        Gdx.input.setInputProcessor(stage);

        stage.addActor(new TImage(TGame.getInstance().findRegion("commonBg_jpg")));

        addHome();
    }

    Image homeBg =  null;
    private void addHome() {
        if (home != null){
            home.remove();
            home = null;
        }

        Group homeGroup = new Group();
        stage.addActor(homeGroup);

        home = new AboutGroup(TGame.getInstance().assetManager, "custom_skins/homeUISkin.exml");
        homeGroup.addActor(home);

        homeBg = home.findActor("imgBg");

        mbtnProfile = home.findActor("mbtnProfile");
        mbtnHeros = home.findActor("mbtnHeros");
        mbtnGoods = home.findActor("mbtnGoods");
        mbtnAbout = home.findActor("mbtnAbout");

        mbtnProfile.setOnCheckedListener(new ToggleButton.OnCheckedListener() {
            @Override
            public void onChecked(boolean checked) {
                if (checked){
                    addProfile();
                }
            }
        });

        mbtnHeros.setOnCheckedListener(new ToggleButton.OnCheckedListener() {
            @Override
            public void onChecked(boolean checked) {
                if (checked){
                    addHeros();
                }
            }

        });

        mbtnGoods.setOnCheckedListener(new ToggleButton.OnCheckedListener() {
            @Override
            public void onChecked(boolean checked) {
                if (checked){
                    addGoods();
                }
            }
        });

        mbtnAbout.setOnCheckedListener(new ToggleButton.OnCheckedListener() {
            @Override
            public void onChecked(boolean checked) {
                if (checked){
                    addAbout();
                }
            }
        });

        ToggleButtonGroup.make(mbtnProfile, mbtnHeros, mbtnGoods, mbtnAbout);
    }

    private void addGoods() {
        if (good == null){
            good = new AboutGroup(TGame.getInstance().assetManager, "custom_skins/goodsUISkin.exml");

            TScrollPane scrollPane = good.findActor("scrListGoods");
            scrollPane.itemRenderXml = "goodsListIRSkin.exml";

            String[][] datas = {
                    {"goods01_png", "魔法石", "法力加成 +3"}
                    , {"goods02_png", "诅咒娃娃", "咒术加成 +3"}
                    , {"goods03_png", "万圣戒指", "敏捷加成 +3"}
                    , {"goods04_png", "斗篷", "耐力加成 +3"}
                    , {"goods05_png", "鹅毛笔", "精神加成 +3"}
                    , {"goods06_png", "血滴子", "嗜血加成 +3"}
                    , {"goods07_png", "屠龙刀", "力量加成 +5"}
            };
            Array<Map<String, String>> dataProvider = new Array<>();
            for (int i = 0; i < datas.length; i++) {
                Map<String, String> item = new HashMap<>();
                item.put("icon", datas[i][0]);
                item.put("goodsName", datas[i][1]);
                item.put("comment", datas[i][2]);

                dataProvider.add(item);
            }

            scrollPane.setData(dataProvider);
        }

        if (selectedGroup != null){
            selectedGroup.remove();
        }

        selectedGroup = good;
        home.addActorAfter(homeBg, good);

        homeBg.setDrawable(TGame.getInstance().findDrawable("bgListPage_jpg"));
    }

    private void addAbout() {
        if (about == null){
            about = new AboutGroup(TGame.getInstance().assetManager, "custom_skins/aboutUISkin.exml");
            about.findActor("btnClose").addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    mbtnProfile.setChecked(true);
                }
            });
        }

        if (selectedGroup != null){
            selectedGroup.remove();
        }

        selectedGroup = about;
        home.addActorAfter(homeBg, about);
        homeBg.setDrawable(TGame.getInstance().findDrawable("commonBg_jpg"));
    }

    private void addHeros() {
        if (hero == null){
            hero = new AboutGroup(TGame.getInstance().assetManager, "custom_skins/herosUISkin.exml");

            TScrollPane scrollPane = hero.findActor("scrListHeros");
            scrollPane.itemRenderXml = "herosListIRSkin.exml";

            String[][] datas = {
                    {"heros01_png", "伊文捷琳", "评价：樱桃小丸子", "false"}
                    , {"heros02_png", "亚特伍德", "评价：离了我你不行的", "true"}
                    , {"heros03_png", "伊妮德", "评价：猴子请来的逗比", "false"}
                    , {"heros04_png", "鲁宾", "评价：我勒个去", "false"}
                    , {"heros05_png", "威弗列德", "评价：这货碉堡了", "false"}
                    , {"heros06_png", "史帝文", "评价：咖啡不加糖", "false"}
                    , {"heros07_png", "哈瑞斯", "评价：猪一样的队友", "false"}
            };

            Array<Map<String, String>> dataProvider = new Array<>();
            for (int i = 0; i < datas.length; i++) {
                Map<String, String> item = new HashMap<>();
                item.put("icon", datas[i][0]);
                item.put("heroName", datas[i][1]);
                item.put("comment", datas[i][2]);
                item.put("checked", datas[i][3]);

                dataProvider.add(item);
            }

            scrollPane.setData(dataProvider);
        }

        if (selectedGroup != null){
            selectedGroup.remove();
        }

        selectedGroup = hero;
        home.addActorAfter(homeBg, hero);

        homeBg.setDrawable(TGame.getInstance().findDrawable("bgListPage_jpg"));
    }

    Group selectedGroup = null;
    private void addProfile() {
        if (profile == null){
            profile = new AboutGroup(TGame.getInstance().assetManager, "custom_skins/profileUISkin.exml");

            TScrollPane scrollPane = profile.findActor("scrListSkills");
            scrollPane.itemRenderXml = "profileSkillListIRSkin.exml";

            String[][] datas = {
                    {"skillIcon01_png", "旋龙幻杀"}
                    , {"skillIcon02_png", "魔魂天咒"}
                    , {"skillIcon03_png", "天魔舞"}
                    , {"skillIcon04_png", "痴情咒"}
                    , {"skillIcon05_png", "无间寂"}
                    , {"skillIcon06_png", "霸天戮杀"}
                    , {"skillIcon07_png", "灭魂狂飙"}
            };
            Array<Map<String, String>> dataProvider = new Array<>();
            for (int i = 0; i < datas.length; i++) {
                Map<String, String> item = new HashMap<>();
                item.put("icon", datas[i][0]);
                item.put("name", datas[i][1]);

                dataProvider.add(item);
            }

            scrollPane.setData(dataProvider);
        }

        if (selectedGroup != null){
            selectedGroup.remove();
        }

        selectedGroup = profile;
        home.addActorAfter(homeBg, profile);
        homeBg.setDrawable(TGame.getInstance().findDrawable("commonBg_jpg"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
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
