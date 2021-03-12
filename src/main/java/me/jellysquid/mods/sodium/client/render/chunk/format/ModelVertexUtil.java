package me.jellysquid.mods.sodium.client.render.chunk.format;

public class ModelVertexUtil {
    /**
     * Converts a floating point vertex position in range 0..32 to a de-normalized unsigned short.
     * @param value The float in range 0..32
     * @return The resulting de-normalized unsigned short
     */
    public static short denormalizeVertexPositionFloatAsShort(float value) {
        // Since we're skipping the scaling formerly done in ChunkModelVertexTransformer to preserve precision, this
        // method actually takes input unnormalized within the range 0..32, and expands that to 0..65536.
        // TODO: Restructure things to be less arbitrary here
        return (short) Math.round(value * 2048.0f);
    }

    /**
     * Converts a normalized floating point texture coordinate to a de-normalized unsigned short.
     * @param value The normalized float
     * @return The resulting de-normalized unsigned short
     */
    public static short denormalizeVertexTextureFloatAsShort(float value) {
        return (short) Math.round(value * 32768.0f);
    }

    /**
     * Converts three floating point vertex positions in range 0..32 to de-normalized unsigned 10-bit values and packs them into an int.
     * @param x The X coordinate float in range 0..32
     * @param y The Y coordinate float in range 0..32
     * @param z The Z coordinate float in range 0..32
     * @return The resulting de-normalized unsigned 10-bit values packed into a 32-bit int with the two leading bits unused
     */
    public static int adf(float x, float y, float z) {
        int l = Math.round(x * 32.0f) & 1023;
        l |= (Math.round(y * 32.0f) & 1023) << 10;
        l |= (Math.round(z * 32.0f) & 1023) << 20;
        return l;
    }

    /**
     * This moves some work out the shader code and simplifies things a bit. In vanilla, the game encodes light map
     * texture coordinates as two un-normalized unsigned shorts in the range 0..255. Using the fixed-function pipeline,
     * it then applies a matrix transformation which normalizes these coordinates and applies a centering offset. This
     * operation has non-zero overhead and complicates shader code a bit.
     *
     * To work around the problem, this function instead normalizes these light map texture coordinates and applies the
     * centering offset, allowing it to be baked into the vertex data itself.
     *
     * @param light The light map value
     * @return The light map texture coordinates as two unsigned shorts with a center offset applied
     */
    public static int encodeLightMapTexCoord(int light) {
        int sl = (light >> 16) & 255;
        sl = (sl << 8) + 2048;

        int bl = light & 255;
        bl = (bl << 8) + 2048;

        return (sl << 16) | bl;
    }
}
