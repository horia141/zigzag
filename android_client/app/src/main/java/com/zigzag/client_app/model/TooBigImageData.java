package com.zigzag.client_app.model;

import java.util.ArrayList;
import java.util.List;

public class TooBigImageData extends ImageData {
    public List<String> getUriPathsToFetch() {
        return new ArrayList<>();
    }

    public int getTotalHeight() {
        return 0;
    }
}
