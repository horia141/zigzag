package com.zigzag.client_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.model.AnimationSetImageData;
import com.zigzag.client_app.model.Artifact;
import com.zigzag.client_app.model.EntityId;
import com.zigzag.client_app.model.ImageData;
import com.zigzag.client_app.model.ImageDescription;
import com.zigzag.client_app.model.ImageSetImageData;
import com.zigzag.client_app.model.TooBigImageData;
import com.zigzag.client_app.ui.BitmapSetAdapter;
import com.zigzag.client_app.ui.GifImageView;
import com.zigzag.client_app.ui.TileImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaCarouselFragment extends Fragment implements Controller.ArtifactResourcesListener {
    private static class ImageInfo {
        final ImageDescription imageDescription;
        final ImageData imageData;
        // One of these two is non-null, the other is null.
        final List<Bitmap> tilesBitmaps;
        final TilesBitmapAdapter tilesBitmapAdapter;

        public ImageInfo(ImageDescription imageDescription, ImageData imageData) {
            this.imageDescription = imageDescription;
            this.imageData = imageData;
            this.tilesBitmaps = new ArrayList<>();
            this.tilesBitmapAdapter = new TilesBitmapAdapter(this.tilesBitmaps);
        }
    }

    private static class TilesBitmapAdapter extends BitmapSetAdapter<TileImageView> {
        private final List<Bitmap> tilesBitmaps;

        public TilesBitmapAdapter(List<Bitmap> tilesBitmaps) {
            super();
            this.tilesBitmaps = tilesBitmaps;
        }

        @Override
        @Nullable
        public Bitmap getBitmap(int position) {
            return tilesBitmaps.get(position);
        }

        @Override
        public int getCount() {
            return tilesBitmaps.size();
        }
    }

    private static class ImagesDescriptionBitmapListAdapter extends ArrayAdapter<ImageInfo> {
        private static class ViewHolder {
            TextView subtitleTextView;
            GifImageView gifImageView;
            TileImageView tileImageView;
            TextView descriptionTextView;
        }

        private final List<ImageInfo> imagesDescriptionBitmapList;
        private final Map<Integer, GifImageView> gifImages;

        public ImagesDescriptionBitmapListAdapter(Context context, List<ImageInfo> imagesDescriptionBitmapList, Map<Integer, GifImageView> gifImages) {
            super(context, R.layout.fragment_media_carousel_one_image, imagesDescriptionBitmapList);
            this.imagesDescriptionBitmapList = imagesDescriptionBitmapList;
            this.gifImages = gifImages;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = convertView;
            ViewHolder rowViewHolder;

            // Build the basic view of the element, and cache it for later use.
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.fragment_media_carousel_one_image, parent, false);
                rowViewHolder = new ViewHolder();

                rowViewHolder.subtitleTextView = (TextView) rowView.findViewById(R.id.subtitle);
                rowViewHolder.gifImageView = (GifImageView) rowView.findViewById(R.id.image_gif);
                rowViewHolder.tileImageView = (TileImageView) rowView.findViewById(R.id.image_tile);
                rowViewHolder.descriptionTextView = (TextView) rowView.findViewById(R.id.description);

                rowView.setTag(rowViewHolder);
            } else {
                rowViewHolder = (ViewHolder)rowView.getTag();
            }

            ImageInfo info = imagesDescriptionBitmapList.get(position);

            if (info == null) {
                rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                rowViewHolder.gifImageView.setVisibility(View.GONE);
                rowViewHolder.tileImageView.setVisibility(View.GONE);
                rowViewHolder.descriptionTextView.setVisibility(View.GONE);
            } else {
                if (!info.imageDescription.getSubtitle().equals("")) {
                    rowViewHolder.subtitleTextView.setText(info.imageDescription.getSubtitle());
                    rowViewHolder.subtitleTextView.setVisibility(View.VISIBLE);
                } else {
                    rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                }

                if (info.imageData instanceof TooBigImageData) {
                    rowViewHolder.gifImageView.setVisibility(View.GONE);
                    rowViewHolder.tileImageView.setVisibility(View.GONE);
                } else if (info.imageData instanceof ImageSetImageData) {
                    rowViewHolder.gifImageView.setVisibility(View.GONE);

                    rowViewHolder.tileImageView.setVisibility(View.VISIBLE);
                    rowViewHolder.tileImageView.setAdapter(info.tilesBitmapAdapter);
                } else if (info.imageData instanceof AnimationSetImageData) {
                    GifImageView gifImageView = gifImages.get(position);
                    if (gifImageView != null && gifImageView != rowViewHolder.gifImageView) {
                        // gifImageView.stopAnimation();
                    }
                    gifImages.put(position, rowViewHolder.gifImageView);
                    rowViewHolder.gifImageView.setVisibility(View.VISIBLE);
                    rowViewHolder.gifImageView.setImageData((AnimationSetImageData) info.imageData, info.tilesBitmaps);

                    rowViewHolder.tileImageView.setVisibility(View.GONE);
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
    private final Map<Integer, GifImageView> gifImages = new HashMap<>();

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

        imagesDescriptionBitmapListAdapter = new ImagesDescriptionBitmapListAdapter(getActivity(), this.imagesDescriptionBitmapList, gifImages);

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
            ImageData imageData = imageDescription.getBestMatchingImageData();
            ImageInfo info = new ImageInfo(imageDescription, imageData);

            if (imageData instanceof TooBigImageData) {
                continue;
            } else if (imageData instanceof ImageSetImageData) {
                ImageSetImageData imageSetImageData = (ImageSetImageData) imageData;
                for (int jj = 0; jj < imageSetImageData.getTilesDesc().size(); jj++) {
                    info.tilesBitmaps.add(null);
                }
            } else if (imageData instanceof AnimationSetImageData) {
                AnimationSetImageData animationSetImageData = (AnimationSetImageData) imageData;
                for (int jj = 0; jj < animationSetImageData.getFramesDesc().size(); jj++) {
                    info.tilesBitmaps.add(null);
                }
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

        if (imageDescription.imageData instanceof TooBigImageData) {
            Log.e("ZigZag/MediaCarouselFragment", "Received image data for a very large image");
            return;
        } else if (imageDescription.imageData instanceof ImageSetImageData) {
            if (tileOrFrameIdx < 0) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Got a negative tile index %d", tileOrFrameIdx));
                return;
            } else if (tileOrFrameIdx >= imageDescription.tilesBitmaps.size()) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Got a tile image for list index %d, with tile %d but it" +
                        " has size %d", imageIdx, tileOrFrameIdx, imageDescription.tilesBitmaps.size()));
                return;
            }

            imageDescription.tilesBitmaps.set(tileOrFrameIdx, image);
        } else if (imageDescription.imageData instanceof AnimationSetImageData) {
            if (tileOrFrameIdx < 0) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Got a negative frame index %d", tileOrFrameIdx));
                return;
            } else if (tileOrFrameIdx >= imageDescription.tilesBitmaps.size()) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Got a frame image for list index %d, with tile %d but it" +
                        " has size %d", imageIdx, tileOrFrameIdx, imageDescription.tilesBitmaps.size()));
                return;
            }

            imageDescription.tilesBitmaps.set(tileOrFrameIdx, image);
            GifImageView gifImage = gifImages.get(tileOrFrameIdx);
            if (gifImage != null) {
                gifImage.setFrameBitmap(tileOrFrameIdx, image);
            }
        }

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
