package com.mygdx.game.egret;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.egret.property.BaseProperty;
import com.mygdx.game.egret.utils.EXMLReader;

/**
 * Created by Administrator on 2017/2/22.
 */

public class Egret {

    public static Array<BaseProperty> loadExmlProperty (String exmlPath) {
        EXMLReader exmlReader = new EXMLReader();
        exmlReader.parseExml(exmlPath);
        return exmlReader.getUILayerProperty();
    }

    /** 自动load资源
     *
     * @param pathName 资源所在目录路径 */
    /*public static void autoLoadRes (String pathName) {
        FileHandle[] handles = CHFileHelper.getFileHandles(pathName);
        for (FileHandle fileHandle : handles) {
            ERes.loadRes(fileHandle.name().replace(".", "_"), fileHandle.path());
        }
    }*/
}
