package com.zigzag.client_app.model;

import java.util.ArrayList;
import java.util.List;

public class ImageSetImageData extends ImageData {
    private final TileData fullImageDesc;
    private final List<TileData> tilesDesc;
    private final int totalHeight;

    public ImageSetImageData(TileData fullImageDesc, List<TileData> tilesDesc) {
        int totalHeight = 0;

        for (TileData tileData : tilesDesc) {
            totalHeight += tileData.getHeight();
        }

        this.fullImageDesc = fullImageDesc;
        this.tilesDesc = tilesDesc;
        this.totalHeight = totalHeight;
    }

    public TileData getFullImageDesc() {
        return fullImageDesc;
    }

    public List<TileData> getTilesDesc() {
        return tilesDesc;
    }

    public List<String> getUriPathsToFetch() {
        List<String> uriPaths = new ArrayList<>(tilesDesc.size());

        for (TileData tileData : tilesDesc) {
            uriPaths.add(tileData.getUriPath());
        }

        return uriPaths;
    }

    public int getTotalHeight() {
        return this.totalHeight;
    }
}
