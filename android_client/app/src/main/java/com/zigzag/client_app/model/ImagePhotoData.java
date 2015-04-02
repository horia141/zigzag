package com.zigzag.client_app.model;

import java.util.ArrayList;
import java.util.List;

public class ImagePhotoData extends PhotoData {
    private final TileData fullImageDesc;
    private final List<TileData> tilesDesc;

    public ImagePhotoData(TileData fullImageDesc, List<TileData> tilesDesc) {
        this.fullImageDesc = fullImageDesc;
        this.tilesDesc = tilesDesc;
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
}
