package me.jellysquid.mods.sodium.client.render.chunk.format.ultra;

import me.jellysquid.mods.sodium.client.model.vertex.buffer.VertexBufferView;
import me.jellysquid.mods.sodium.client.model.vertex.buffer.VertexBufferWriterUnsafe;
import me.jellysquid.mods.sodium.client.render.chunk.format.DefaultModelVertexFormats;
import me.jellysquid.mods.sodium.client.render.chunk.format.ModelVertexSink;
import me.jellysquid.mods.sodium.client.render.chunk.format.ModelVertexUtil;

public class UltraModelVertexBufferWriterUnsafe extends VertexBufferWriterUnsafe implements ModelVertexSink {
    public UltraModelVertexBufferWriterUnsafe(VertexBufferView backingBuffer) {
        super(backingBuffer, DefaultModelVertexFormats.MODEL_VERTEX_ULTRA);
    }

    @Override
    public void writeQuad(float x, float y, float z, int color, float u, float v, int light) {
        this.writeQuadInternal(
                ModelVertexUtil.adf(x, y, z),
                color,
                ModelVertexUtil.denormalizeVertexTextureFloatAsShort(u),
                ModelVertexUtil.denormalizeVertexTextureFloatAsShort(v),
                ModelVertexUtil.encodeLightMapTexCoord(light)
        );
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void writeQuadInternal(int pos, int color, short u, short v, int light) {
        long i = this.writePointer;

        UNSAFE.putInt(i, pos);
        UNSAFE.putInt(i + 4, color);
        UNSAFE.putShort(i + 8, u);
        UNSAFE.putShort(i + 10, v);
        UNSAFE.putInt(i + 12, light);

        this.advance();
    }

}
