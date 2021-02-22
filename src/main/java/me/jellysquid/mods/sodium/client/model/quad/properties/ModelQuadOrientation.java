package me.jellysquid.mods.sodium.client.model.quad.properties;

/**
 * Defines the orientation of vertices in a model quad. This information be used to re-orient the quad's vertices to a
 * consistent order, eliminating a number of shading issues caused by anisotropy problems.
 */
public enum ModelQuadOrientation {
    NORMAL(new int[] { 0, 1, 2, 3 }),
    FLIP(new int[] { 1, 2, 3, 0 });

    private final int[] indices;

    ModelQuadOrientation(int[] indices) {
        this.indices = indices;
    }

    /**
     * @return The re-oriented index of the vertex {@param idx}
     */
    public int getVertexIndex(int idx) {
        return this.indices[idx];
    }

    /**
     * Determines the orientation of the vertices in the quad.
     */
    public static ModelQuadOrientation orient(float[] brightnesses, int[] lightmaps) {
        // If one side of the quad is brighter, flip the sides

        // Prioritize ambient occlusion over block/sky light
        if (brightnesses[0] + brightnesses[2] > brightnesses[1] + brightnesses[3]) {
            return NORMAL;
        } else if (brightnesses[0] + brightnesses[2] < brightnesses[1] + brightnesses[3]) {
            return FLIP;
        }

        // Use only the brightest of block or sky light for each vertex
        int[] worldLight = new int[4];
        for (int i = 0; i < 4; i++) {
            worldLight[i] = Math.max(lightmaps[i] & 255, lightmaps[i] >> 16);
        }

        // Check which diagonal has the brightest vertex
        if (Math.max(worldLight[0], worldLight[2]) < Math.max(worldLight[1], worldLight[3])) {
            return FLIP;
        } else if (Math.max(worldLight[0], worldLight[2]) > Math.max(worldLight[1], worldLight[3])) {
            return NORMAL;
        }

        // Check if three vertices have identical light levels
        if (worldLight[0] == worldLight[1] ^ worldLight[2] == worldLight[3]) {
            if (worldLight[0] == worldLight[2]) {
                return FLIP;
            } else if (worldLight[1] == worldLight[3]) {
                return NORMAL;
            }
        }

        // Same as the ambient occlusion check, but returns the opposite orientation
        if (worldLight[0] + worldLight[2] > worldLight[1] + worldLight[3]) {
            return FLIP;
        } else if (worldLight[0] + worldLight[2] < worldLight[1] + worldLight[3]) {
            return NORMAL;
        }

        // if all previous checks fail, return the default orientation
        return NORMAL;
    }
}
