package com.svalero.spaceinvaders.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class ResourceManager {

    private static AssetManager assetManager = new AssetManager();

    public static boolean update() {
        return assetManager.update();
    }

    public static void loadAllResources() {
        assetManager.load("spaceinvaders.atlas", TextureAtlas.class);
    }

    public static TextureRegion getTexture(String name) {
        return assetManager.get("spaceinvaders.atlas", TextureAtlas.class).findRegion(name);
    }

    public static Array<TextureAtlas.AtlasRegion> getAnimation(String name) {
        return assetManager.get("spaceinvaders.atlas", TextureAtlas.class).findRegions(name);
    }


}
