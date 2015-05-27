package com.zigzag.client_app;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.ui.BitmapSetAdapter;
import com.zigzag.client_app.ui.ImagePhotoView;
import com.zigzag.client_app.ui.ImagePhotoView2;
import com.zigzag.client_app.ui.VideoPhotoView;
import com.zigzag.common.model.Artifact;
import com.zigzag.common.model.ArtifactSource;
import com.zigzag.common.model.ImagePhotoData;
import com.zigzag.common.model.PhotoData;
import com.zigzag.common.model.PhotoDescription;
import com.zigzag.common.model.VideoPhotoData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MediaCarouselFragment extends Fragment implements Controller.ArtifactResourcesListener {
    private static class ImageInfo {
        final PhotoDescription photoDescription;
        final PhotoData photoData;
        final List<Bitmap> tilesBitmaps;
        final TilesBitmapAdapter tilesBitmapAdapter;
        String localPathToVideo;
        int viewTreeObserverHash;

        public ImageInfo(PhotoDescription photoDescription, PhotoData photoData) {
            this.photoDescription = photoDescription;
            this.photoData = photoData;
            this.tilesBitmaps = new ArrayList<>();
            this.tilesBitmapAdapter = new TilesBitmapAdapter(photoData, this.tilesBitmaps);
            this.localPathToVideo = null;
            this.viewTreeObserverHash = -1;
        }
    }

    private static class TilesBitmapAdapter extends BitmapSetAdapter {
        private final PhotoData photoData;
        private final List<Bitmap> tilesBitmaps;

        public TilesBitmapAdapter(PhotoData photoData, List<Bitmap> tilesBitmaps) {
            super();
            this.photoData = photoData;
            this.tilesBitmaps = tilesBitmaps;
        }

        @Override
        @Nullable
        public TileInfo getTileInfo(int position) {
            if (photoData.isSetImage_photo_data()) {
                ImagePhotoData imageSetImageData = photoData.getImage_photo_data();

                return new TileInfo(tilesBitmaps.get(position),
                        imageSetImageData.getTiles().get(position).getWidth(),
                        imageSetImageData.getTiles().get(position).getHeight());
            } else if (photoData.isSetVideo_photo_data()) {
                // assert position == 0
                VideoPhotoData videoPhotoData = photoData.getVideo_photo_data();

                return new TileInfo(tilesBitmaps.get(position),
                        videoPhotoData.getFirst_frame().getWidth(),
                        videoPhotoData.getFirst_frame().getHeight());
            } else {
                throw new IllegalArgumentException("This codepath should not be reached");
            }
        }

        @Override
        public int getCount() {
            return tilesBitmaps.size();
        }
    }

    private static class ImagesDescriptionBitmapListAdapter extends ArrayAdapter<ImageInfo> {
        private static class ViewHolder {
            ImagePhotoView2 tileImageView;
            VideoPhotoView videoPhotoView;
            TextView subtitleTextView;
            TextView descriptionTextView;
        }

        private final List<ImageInfo> imagesDescriptionBitmapList;

        public ImagesDescriptionBitmapListAdapter(Context context, List<ImageInfo> imagesDescriptionBitmapList) {
            super(context, R.layout.fragment_media_carousel_one_photo, imagesDescriptionBitmapList);
            this.imagesDescriptionBitmapList = imagesDescriptionBitmapList;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = convertView;
            ViewHolder rowViewHolder;

            // Build the basic view of the element, and cache it for later use.
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.fragment_media_carousel_one_photo, parent, false);
                rowViewHolder = new ViewHolder();

                rowViewHolder.tileImageView = (ImagePhotoView2) rowView.findViewById(R.id.image_photo);
                rowViewHolder.videoPhotoView = (VideoPhotoView) rowView.findViewById(R.id.video_photo);
                rowViewHolder.subtitleTextView = (TextView) rowView.findViewById(R.id.subtitle);
                rowViewHolder.descriptionTextView = (TextView) rowView.findViewById(R.id.description);

                rowView.setTag(rowViewHolder);
            } else {
                rowViewHolder = (ViewHolder)rowView.getTag();
            }

            ImageInfo info = imagesDescriptionBitmapList.get(position);

            if (info == null) {
                rowViewHolder.tileImageView.setVisibility(View.GONE);
                rowViewHolder.videoPhotoView.setVisibility(View.GONE);
                rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                rowViewHolder.descriptionTextView.setVisibility(View.GONE);
            } else {
                if (info.photoData.isSetToo_big_photo_data()) {
                    rowViewHolder.tileImageView.setVisibility(View.GONE);
                    rowViewHolder.videoPhotoView.setVisibility(View.GONE);
                } else if (info.photoData.isSetImage_photo_data()) {
                    rowViewHolder.tileImageView.setVisibility(View.VISIBLE);
                    rowViewHolder.tileImageView.setImagePhotoData(info.photoData.getImage_photo_data());
                    for (int ii = 0; ii < info.tilesBitmaps.size(); ii++) {
                        Bitmap tileBitmap = info.tilesBitmaps.get(ii);
                        if (tileBitmap == null) {
                            continue;
                        }
                        rowViewHolder.tileImageView.setBitmapForTile(ii, tileBitmap);
                    }
                    rowViewHolder.videoPhotoView.setVisibility(View.GONE);
                } else if (info.photoData.isSetVideo_photo_data()) {
                    rowViewHolder.tileImageView.setVisibility(View.GONE);
                    rowViewHolder.videoPhotoView.setVisibility(View.VISIBLE);
                    rowViewHolder.videoPhotoView.setAdapter(info.tilesBitmapAdapter, info.photoData.getVideo_photo_data(), info.localPathToVideo);

                    final ViewHolder localRowViewHolder = rowViewHolder;
                    ViewTreeObserver viewTreeObserver = rowView.getViewTreeObserver();

                    if (info.viewTreeObserverHash != Math.abs(viewTreeObserver.hashCode())) {
                        info.viewTreeObserverHash = Math.abs(viewTreeObserver.hashCode());
                        rowView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                            @Override
                            public void onScrollChanged() {
                                Rect scrollBounds = new Rect();
                                parent.getHitRect(scrollBounds);
                                if (localRowViewHolder.videoPhotoView.getLocalVisibleRect(scrollBounds)) {
                                    localRowViewHolder.videoPhotoView.enableVideo();
                                } else {
                                    localRowViewHolder.videoPhotoView.disableVideo();
                                }
                            }
                        });
                    }
                }

                if (!info.photoDescription.getSubtitle().equals("")) {
                    rowViewHolder.subtitleTextView.setText(info.photoDescription.getSubtitle());
                    rowViewHolder.subtitleTextView.setVisibility(View.VISIBLE);
                } else {
                    rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                }

                if (!info.photoDescription.getDescription().equals("")) {
                    rowViewHolder.descriptionTextView.setText(info.photoDescription.getDescription());
                    rowViewHolder.descriptionTextView.setVisibility(View.VISIBLE);
                } else {
                    rowViewHolder.descriptionTextView.setVisibility(View.GONE);
                }
            }

            return rowView;
        }
    }

    @Nullable private Artifact artifact;
    private final List<ImageInfo> imagesDescriptionBitmapList;
    @Nullable private ImagesDescriptionBitmapListAdapter imagesDescriptionBitmapListAdapter;
    private final SimpleDateFormat dateAddedFormatter;

    public MediaCarouselFragment() {
        this.artifact = null;
        this.imagesDescriptionBitmapList = new ArrayList<>();
        this.imagesDescriptionBitmapListAdapter = null;
        this.dateAddedFormatter = new SimpleDateFormat("d MMM yyyy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarouselFragment", "Creating view");

        Bundle args = getArguments();
        String artifactPageUri = args.getString("artifact_id");
        artifact = Controller.getInstance(getActivity()).getArtifactByPageUri(artifactPageUri);

        final View rootView = inflater.inflate(R.layout.fragment_media_carousel, container, false);

        imagesDescriptionBitmapListAdapter = new ImagesDescriptionBitmapListAdapter(getActivity(), this.imagesDescriptionBitmapList);

        final ListView imageListView = (ListView)rootView.findViewById(R.id.image_list);
        imageListView.setAdapter(imagesDescriptionBitmapListAdapter);

        // Clear the current data structures and interface. Dispose of the associated bitmaps and
        // clear the tiles list.
        for (int ii = 0; ii < imagesDescriptionBitmapList.size(); ii++) {
            ImageInfo info = imagesDescriptionBitmapList.get(ii);
            info.tilesBitmaps.clear();
            info.tilesBitmapAdapter.notifyDataSetChanged();
        }
        imagesDescriptionBitmapList.clear();
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();

        // Setup title for artifact.
        TextView titleView = (TextView)rootView.findViewById(R.id.title);
        titleView.setText(String.format("%s", artifact.getTitle()));

        // Setup source name for the artifact.
        TextView sourceNameView = (TextView)rootView.findViewById(R.id.source_name);
        ArtifactSource artifactSource = Controller.getInstance(getActivity()).getSourceForArtifact(artifact);
        sourceNameView.setText(artifactSource.getArtifact_title_name());

        // Setup date for the artifact.
        TextView dateAddedView = (TextView)rootView.findViewById(R.id.date_added);
        Date dateForArtifact = Controller.getInstance(getActivity()).getDateForArtifact(artifact);
        dateAddedView.setText(dateAddedFormatter.format(dateForArtifact));

        // Setup list view with all the images in the artifact. Reconstruct the list of bitmaps
        // to contain only nulls and the associated adapter and associate them with the image
        // list view.
        for (PhotoDescription photoDescription : artifact.getPhoto_descriptions()) {
            PhotoData photoData = photoDescription.getPhoto_data();
            ImageInfo info = new ImageInfo(photoDescription, photoData);

            if (photoData.isSetToo_big_photo_data()) {
                continue;
            } else if (photoData.isSetImage_photo_data()) {
                ImagePhotoData imageSetImageData = photoData.getImage_photo_data();
                for (int jj = 0; jj < imageSetImageData.getTilesSize(); jj++) {
                    info.tilesBitmaps.add(null);
                }
            } else if (photoData.isSetVideo_photo_data()) {
                // Add a single placeholder tile info to tilesBitmap, corresponding to the first
                // frame of the image.
                info.tilesBitmaps.add(null);
            }
            info.tilesBitmapAdapter.notifyDataSetChanged();
            imagesDescriptionBitmapList.add(info);
        }
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Controller.getInstance(getActivity()).fetchArtifactResources(artifact, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Controller.getInstance(getActivity()).deregisterArtifactResources(artifact, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResourcesForArtifact(Artifact artifact, int imageIdx, int tileOrFrameIdx, Bitmap image) {
        Log.i("ZigZag", String.format("Got %d/%d", imageIdx, tileOrFrameIdx));

        if (artifact != this.artifact) {
            Log.e("ZigZag/MediaCarouselFragment", "Got out of sync image update");
            return;
        }

        if (imageIdx < 0) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got a negative image index %d", imageIdx));
            return;
        } else if (imageIdx >= imagesDescriptionBitmapList.size()) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got an image for list index %d, but list" +
                    " has size %d", imageIdx, imagesDescriptionBitmapList.size()));
            return;
        }

        ImageInfo imageDescription = imagesDescriptionBitmapList.get(imageIdx);

        if (imageDescription.photoData.isSetToo_big_photo_data()) {
            Log.e("ZigZag/MediaCarouselFragment", "Received image data for a very large image");
            return;
        } else {
            if (tileOrFrameIdx < 0) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Got a negative tile index %d", tileOrFrameIdx));
                return;
            } else if (tileOrFrameIdx >= imageDescription.tilesBitmaps.size()) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Got a tile image for list index %d, with tile %d but it" +
                        " has size %d", imageIdx, tileOrFrameIdx, imageDescription.tilesBitmaps.size()));
                return;
            }

            imageDescription.tilesBitmaps.set(tileOrFrameIdx, image);
        }

        imageDescription.tilesBitmapAdapter.notifyDataSetChanged();
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();

        // TODO(horia141): the hackiest of hacks.
        ListView imageListView = (ListView) getView().findViewById(R.id.image_list);
        View something = imageListView.getChildAt(imageIdx);
        if (something == null) {
            return;
        }
        ImagePhotoView2 imagePhotoView = (ImagePhotoView2) something.findViewById(R.id.image_photo);
        imagePhotoView.setBitmapForTile(tileOrFrameIdx, image);
    }

    @Override
    public void onVideoResourcesForArtifact(Artifact artifact, int imageIdx, String localPathToVideo) {
        Log.i("ZigZag", String.format("Got Video %d", imageIdx));

        if (artifact != this.artifact) {
            Log.e("ZigZag/MediaCarouselFragment", "Got out of sync video update");
            return;
        }

        if (imageIdx < 0) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got a negative image index %d", imageIdx));
            return;
        } else if (imageIdx >= imagesDescriptionBitmapList.size()) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got an image for list index %d, but list" +
                    " has size %d", imageIdx, imagesDescriptionBitmapList.size()));
            return;
        }

        ImageInfo imageDescription = imagesDescriptionBitmapList.get(imageIdx);

        if (!imageDescription.photoData.isSetVideo_photo_data()) {
            Log.e("ZigZag/MediaCarouselFragment", "Received video update for something which is not a video");
            return;
        }

        imageDescription.localPathToVideo = localPathToVideo;
        imageDescription.tilesBitmapAdapter.notifyDataSetChanged();
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorDescription) {
        Log.i("ZigZag", String.format("Error %s", errorDescription));
    }

    public static MediaCarouselFragment newInstance(Artifact artifact) {
        MediaCarouselFragment fragment = new MediaCarouselFragment();
        Bundle args = new Bundle();
        args.putString("artifact_id", artifact.getPage_uri());
        fragment.setArguments(args);

        return fragment;
    }
}
