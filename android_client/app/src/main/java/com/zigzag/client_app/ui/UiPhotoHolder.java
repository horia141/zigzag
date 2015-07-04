package com.zigzag.client_app.ui;

/**
 * Describes UI elements which can hold on to a photo and which need to be manually cleared.
 *
 * <p>Due to a bug in either the application or Android, bitmap memory leaks. If enough artifacts
 * are viewed, an OOM situation will occur. Releasing the memory in the fragment shutdown callbacks
 * does not seem to work. The only thing that does work is releasing the resources while the
 * fragment is still active. I presume that when it becomes inactive, and its state is saved by
 * the {@link ViewPager} and {@link com.zigzag.client_app.ArtifactCarouselFragment.ArtifactsAdapter}
 * somehow the bitmaps set to images become saved, and then they are kept forever by the adapter.
 * In any case, there is a mechanism to release these resources earlier. Any UI element which
 * handles resources related to photos should implement this interface and release the resources
 * they and their children have.</p>
 */
public interface UiPhotoHolder {
    void clearPhotoResources();
}
