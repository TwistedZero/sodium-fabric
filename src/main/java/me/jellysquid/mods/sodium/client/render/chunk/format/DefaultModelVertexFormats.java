package me.jellysquid.mods.sodium.client.render.chunk.format;

import me.jellysquid.mods.sodium.client.render.chunk.format.hfp.HFPModelVertexType;
import me.jellysquid.mods.sodium.client.render.chunk.format.sfp.SFPModelVertexType;
import me.jellysquid.mods.sodium.client.render.chunk.format.ultra.UltraModelVertexType;

public class DefaultModelVertexFormats {
    public static final HFPModelVertexType MODEL_VERTEX_HFP = new HFPModelVertexType();
    public static final SFPModelVertexType MODEL_VERTEX_SFP = new SFPModelVertexType();
    public static final UltraModelVertexType MODEL_VERTEX_ULTRA = new UltraModelVertexType();
}
