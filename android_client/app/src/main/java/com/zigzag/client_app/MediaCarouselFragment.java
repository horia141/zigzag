package com.zigzag.client_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.model.Artifact;
import com.zigzag.client_app.model.EntityId;
import com.zigzag.client_app.model.ImagePhotoData;
import com.zigzag.client_app.model.PhotoData;
import com.zigzag.client_app.model.ImageDescription;
import com.zigzag.client_app.model.TooBigPhotoData;
import com.zigzag.client_app.model.VideoPhotoData;
import com.zigzag.client_app.ui.BitmapSetAdapter;
import com.zigzag.client_app.ui.ImagePhotoView;
import com.zigzag.client_app.ui.VideoPhotoView;

import java.util.ArrayList;
import java.util.List;

public class MediaCarouselFragment extends Fragment implements Controller.ArtifactResourcesListener {
    private static class ImageInfo {
        final ImageDescription imageDescription;
        final PhotoData photoData;
        final List<Bitmap> tilesBitmaps;
        final TilesBitmapAdapter tilesBitmapAdapter;
        String localPathToVideo;
        int viewTreeObserverHash;

        public ImageInfo(ImageDescription imageDescription, PhotoData photoData) {
            this.imageDescription = imageDescription;
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
            if (photoData instanceof ImagePhotoData) {
                ImagePhotoData imageSetImageData = (ImagePhotoData) photoData;

                return new TileInfo(tilesBitmaps.get(position),
                        imageSetImageData.getTilesDesc().get(position).getWidth(),
                        imageSetImageData.getTilesDesc().get(position).getHeight());
            } else if (photoData instanceof VideoPhotoData) {
                // assert position == 0
                VideoPhotoData videoPhotoData = (VideoPhotoData) photoData;

                return new TileInfo(tilesBitmaps.get(position),
                        videoPhotoData.getFirstFrameDesc().getWidth(),
                        videoPhotoData.getFirstFrameDesc().getHeight());
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
            TextView subtitleTextView;
            ImagePhotoView tileImageView;
            VideoPhotoView videoPhotoView;
            TextView descriptionTextView;
        }

        private final List<ImageInfo> imagesDescriptionBitmapList;

        public ImagesDescriptionBitmapListAdapter(Context context, List<ImageInfo> imagesDescriptionBitmapList) {
            super(context, R.layout.fragment_media_carousel_one_image, imagesDescriptionBitmapList);
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
                rowView = inflater.inflate(R.layout.fragment_media_carousel_one_image, parent, false);
                rowViewHolder = new ViewHolder();

                rowViewHolder.subtitleTextView = (TextView) rowView.findViewById(R.id.subtitle);
                rowViewHolder.tileImageView = (ImagePhotoView) rowView.findViewById(R.id.image_photo);
                rowViewHolder.videoPhotoView = (VideoPhotoView) rowView.findViewById(R.id.video_photo);
                rowViewHolder.descriptionTextView = (TextView) rowView.findViewById(R.id.description);

                rowView.setTag(rowViewHolder);
            } else {
                rowViewHolder = (ViewHolder)rowView.getTag();
            }

            ImageInfo info = imagesDescriptionBitmapList.get(position);

            if (info == null) {
                rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                rowViewHolder.tileImageView.setVisibility(View.GONE);
                rowViewHolder.videoPhotoView.setVisibility(View.GONE);
                rowViewHolder.descriptionTextView.setVisibility(View.GONE);
            } else {
                if (!info.imageDescription.getSubtitle().equals("")) {
                    rowViewHolder.subtitleTextView.setText(info.imageDescription.getSubtitle());
                    rowViewHolder.subtitleTextView.setVisibility(View.VISIBLE);
                } else {
                    rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                }

                if (info.photoData instanceof TooBigPhotoData) {
                    rowViewHolder.tileImageView.setVisibility(View.GONE);
                    rowViewHolder.videoPhotoView.setVisibility(View.GONE);
                } else if (info.photoData instanceof ImagePhotoData) {
                    rowViewHolder.tileImageView.setVisibility(View.VISIBLE);
                    rowViewHolder.tileImageView.setAdapter(info.tilesBitmapAdapter);
                    rowViewHolder.videoPhotoView.setVisibility(View.GONE);
                } else if (info.photoData instanceof VideoPhotoData) {
                    rowViewHolder.tileImageView.setVisibility(View.GONE);
                    rowViewHolder.videoPhotoView.setVisibility(View.VISIBLE);
                    rowViewHolder.videoPhotoView.setAdapter(info.tilesBitmapAdapter, (VideoPhotoData) info.photoData, info.localPathToVideo);

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

                if (!info.imageDescription.getDescription().equals("")) {
                    rowViewHolder.descriptionTextView.setText(info.imageDescription.getDescription());
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

    public MediaCarouselFragment() {
        this.artifact = null;
        this.imagesDescriptionBitmapList = new ArrayList<>();
        this.imagesDescriptionBitmapListAdapter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarouselFragment", "Creating view");

        Bundle args = getArguments();
        String artifactIdStr = args.getString("artifact_id");
        artifact = Controller.getInstance(getActivity()).getArtifactById(new EntityId(artifactIdStr));

        final View rootView = inflater.inflate(R.layout.fragment_media_carousel, container, false);

        ProgressBar waitingView = (ProgressBar) rootView.findViewById(R.id.waiting);

        imagesDescriptionBitmapListAdapter = new ImagesDescriptionBitmapListAdapter(getActivity(), this.imagesDescriptionBitmapList);

        final ListView imageListView = (ListView)rootView.findViewById(R.id.image_list);
        imageListView.setEmptyView(waitingView);
        imageListView.setAdapter(imagesDescriptionBitmapListAdapter);

        ImageButton saveButton = (ImageButton)rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View saveButton) {
            }
        });

        ImageButton shareButton = (ImageButton)rootView.findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View shareButton) {
                if (artifact == null) {
                    return;
                }

                String subject = String.format("%s via %s", artifact.getTitle(), getActivity().getString(R.string.app_name));
                String text = String.format("%s via %s %s", artifact.getTitle(), getActivity().getString(R.string.app_name), artifact.getPageUrl());
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(i, getActivity().getString(R.string.share_title)));
            }
        });

        // Clear the current data structures and interface. Dispose of the associated bitmaps and
        // clear the tiles list.
        for (int ii = 0; ii < imagesDescriptionBitmapList.size(); ii++) {
            ImageInfo info = imagesDescriptionBitmapList.get(ii);
            info.tilesBitmaps.clear();
            info.tilesBitmapAdapter.notifyDataSetChanged();
        }
        imagesDescriptionBitmapList.clear();
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();

        // Make the master waiting progress bar invisible, since there'll be individual image
        // progress bars.
        rootView.findViewById(R.id.waiting).setVisibility(View.GONE);

        // Setup title for artifact.
        TextView titleView = (TextView)rootView.findViewById(R.id.title);
        titleView.setText(String.format("%s - %s", artifact.getTitle(), artifact.getArtifactSource().getName()));

        // Setup list view with all the images in the artifact. Reconstruct the list of bitmaps
        // to contain only nulls and the associated adapter and associate them with the image
        // list view.
        for (int ii = 0; ii < artifact.getImagesDescription().size(); ii++) {
            ImageDescription imageDescription = artifact.getImagesDescription().get(ii);
            PhotoData photoData = imageDescription.getBestMatchingImageData();
            ImageInfo info = new ImageInfo(imageDescription, photoData);

            if (photoData instanceof TooBigPhotoData) {
                continue;
            } else if (photoData instanceof ImagePhotoData) {
                ImagePhotoData imageSetImageData = (ImagePhotoData) photoData;
                for (int jj = 0; jj < imageSetImageData.getTilesDesc().size(); jj++) {
                    info.tilesBitmaps.add(null);
                }
            } else if (photoData instanceof VideoPhotoData) {
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

        if (imageDescription.photoData instanceof TooBigPhotoData) {
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

        if (!(imageDescription.photoData instanceof VideoPhotoData)) {
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
        args.putString("artifact_id", artifact.getId().getId());
        fragment.setArguments(args);

        return fragment;
    }
}
