package com.jeff.game.models.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.common.base.Preconditions;


/**
 * RendererComponent component container bag.
 */
public final class RendererComponent implements Component {

    public static final class TextureInfo {

        public TextureRegion textureRegion;
        public float width, height, oX, oY;
        public Color color;

        /**
         * Constructor for this texture info.
         *
         * @param textureRegion The texture region.
         * @param width         The width
         * @param height        The height
         * @param oX            The x offset.
         * @param oY            The y offset.
         * @param color         The color for this group.
         */
        public TextureInfo(TextureRegion textureRegion, float width, float height, float oX, float oY, Color color) {
            this.textureRegion = textureRegion;
            this.width = width;
            this.height = height;
            this.oX = oX;
            this.oY = oY;
            this.color = color;
        }

        @Override
        public String toString() {
            return String.format("Region:%s, Width:%s, Height:%s," +
                            "OX:%s, OY:%s, Color:%s", textureRegion, width, height, oX, oY,
                    color);
        }
    }

    public ImmutableArray<TextureInfo> textureInfos;
    public Color color;
    public boolean enabled = true;

    /**
     * Simple constructor that defaults all other values.
     *
     * @param textureInfos The info group.
     */
    public RendererComponent(ImmutableArray<TextureInfo> textureInfos) {
        this(textureInfos, Color.WHITE);
    }

    /**
     * Constructor.
     *
     * @param textureInfos The info group.
     * @param color        The color.
     */
    public RendererComponent(ImmutableArray<TextureInfo> textureInfos, Color color) {
        this.textureInfos = Preconditions.checkNotNull(textureInfos);
        this.color = Preconditions.checkNotNull(color);
    }
}
