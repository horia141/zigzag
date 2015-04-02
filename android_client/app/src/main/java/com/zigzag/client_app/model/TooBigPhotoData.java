package com.zigzag.client_app.model;

import java.util.ArrayList;
import java.util.List;

public class TooBigPhotoData extends PhotoData {
    public List<String> getUriPathsToFetch() {
        return new ArrayList<>();
    }
}
