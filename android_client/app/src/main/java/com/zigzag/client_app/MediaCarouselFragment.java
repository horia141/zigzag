package com.zigzag.client_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.model.AnimationSetImageData;
import com.zigzag.client_app.model.Artifact;
import com.zigzag.client_app.model.ImageData;
import com.zigzag.client_app.model.ImageDescription;
import com.zigzag.client_app.model.ImageSetImageData;
import com.zigzag.client_app.model.TooBigImageData;

import java.util.ArrayList;
import java.util.List;

public class MediaCarouselFragment extends Fragment implements Controller.ArtifactListener {
    private static class ImageInfo {
        final ImageDescription imageDescription;
        final ImageData imageData;
        // One of these two is non-null, the other is null.
        final List<Bitmap> tilesBitmaps;
        final List<Bitmap> framesBitmaps;
        final TilesBitmapListAdapter tilesBitmapListAdapter;
        // TODO(horia141): fugly.
        final boolean isAnimation;
        @Nullable final AnimationSetImageData animationSetImageData;
        final int framesCount;
        @Nullable GifAnimationTask gifAnimationTask;

        public ImageInfo(ImageDescription imageDescription, ImageData imageData, Context context) {
            this.imageDescription = imageDescription;
            this.imageData = imageData;
            this.tilesBitmaps = new ArrayList<>();
            this.framesBitmaps = new ArrayList<>();
            this.tilesBitmapListAdapter = new TilesBitmapListAdapter(context, this.tilesBitmaps, this);
            this.isAnimation = imageData instanceof AnimationSetImageData;
            this.animationSetImageData = this.isAnimation ? (AnimationSetImageData) imageData : null;
            this.framesCount = this.isAnimation ? this.animationSetImageData.getFramesDesc().size() : 0;
            this.gifAnimationTask = null;
        }

        public boolean allFramesLoaded() {
            for (Bitmap bitmap : framesBitmaps) {
                if (bitmap == null) {
                    return false;
                }
            }

            return true;
        }
    }

    private static class GifAnimationTask extends AsyncTask<Boolean, Integer, Boolean> {
        final ImageInfo imageInfo;
        TilesBitmapListAdapter.ViewHolder viewHolder;
        final long timeBetweenFrames;
        final int framesCount;

        public GifAnimationTask(ImageInfo imageInfo, TilesBitmapListAdapter.ViewHolder viewHolder) {
            this.imageInfo = imageInfo;
            this.viewHolder = viewHolder;
            this.timeBetweenFrames = imageInfo.animationSetImageData.getTimeBetweenFrames();
            this.framesCount = imageInfo.framesCount;
        }

        @Override
        protected Boolean doInBackground(Boolean... unused) {
            int framesCounter = 0;

            while (true) {
                try {
                    Thread.sleep(timeBetweenFrames);

                    // We should somehow protect access to allFramesLoaded, or rather the set of
                    // bitmaps it scans with a lock. I don't think the complexity is warranted.
                    // This method is read only and if it doesn't read the latest state for a
                    // bitmap, it'll get it next time.
                    if (!imageInfo.allFramesLoaded()) {
                        publishProgress(-1);
                    } else {
                        publishProgress(framesCounter);
                        framesCounter = (framesCounter + 1) % framesCount;
                    }

                    if (isCancelled()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... framesCounterParams) {
            int framesCounter = framesCounterParams[0];
            if (framesCounter == -1) {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                viewHolder.imageView.setVisibility(View.GONE);
            } else {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.imageView.setImageBitmap(imageInfo.framesBitmaps.get(framesCounter));
                viewHolder.imageView.setVisibility(View.VISIBLE);
            }
        }
    }

    private static class TilesBitmapListAdapter extends ArrayAdapter<Bitmap> {
        static class ViewHolder {
            ProgressBar progressBar;
            ImageView imageView;
        }

        private final List<Bitmap> tilesBitmapList;
        private final ImageInfo owner;

        public TilesBitmapListAdapter(Context context, List<Bitmap> tilesBitmapList, ImageInfo owner) {
            super(context, R.layout.fragment_media_carousel_one_image_tile, tilesBitmapList);
            this.tilesBitmapList = tilesBitmapList;
            this.owner = owner;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = convertView;
            ViewHolder rowViewHolder;

            if (tilesBitmapList.size() == 2) {
                Log.i("ZigZag/Trap", "trap");
            }

            // Build the basic view of the element, and cache it for later use.
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.fragment_media_carousel_one_image_tile, parent, false);
                rowViewHolder = new ViewHolder();

                rowViewHolder.progressBar = (ProgressBar) rowView.findViewById(R.id.waiting);
                rowViewHolder.imageView = (ImageView) rowView.findViewById(R.id.image);

                rowView.setTag(rowViewHolder);
            } else {
                rowViewHolder = (ViewHolder)rowView.getTag();
            }

            Bitmap bitmap = tilesBitmapList.get(position);

            if (bitmap == null) {
                rowViewHolder.progressBar.setVisibility(View.VISIBLE);
                rowViewHolder.imageView.setVisibility(View.GONE);
            } else if (!owner.isAnimation) {
                rowViewHolder.progressBar.setVisibility(View.GONE);
                rowViewHolder.imageView.setVisibility(View.VISIBLE);
                rowViewHolder.imageView.setImageBitmap(bitmap);
            } else {
                rowViewHolder.progressBar.setVisibility(View.VISIBLE);
                rowViewHolder.imageView.setVisibility(View.GONE);
                if (owner.gifAnimationTask == null) {
                    owner.gifAnimationTask = new GifAnimationTask(owner, rowViewHolder);
                    owner.gifAnimationTask.execute(true);
                } else {
                    owner.gifAnimationTask.viewHolder = rowViewHolder;
                }
            }

            return rowView;
        }

        @Override
        public int getCount() {
            return tilesBitmapList.size();
        }
    }

    private static class ImagesDescriptionBitmapListAdapter extends ArrayAdapter<ImageInfo> {
        private static class ViewHolder {
            TextView subtitleTextView;
            ListView tilesListView;
            TextView descriptionTextView;
        }

        private final List<ImageInfo> imagesDescriptionBitmapList;

        public ImagesDescriptionBitmapListAdapter(Context context, List<ImageInfo> imagesDescriptionBitmapList) {
            super(context, R.layout.fragment_media_carousel_one_image, imagesDescriptionBitmapList);
            this.imagesDescriptionBitmapList = imagesDescriptionBitmapList;
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
                rowViewHolder.tilesListView = (ListView) rowView.findViewById(R.id.tiles_list);
                rowViewHolder.descriptionTextView = (TextView) rowView.findViewById(R.id.description);

                rowView.setTag(rowViewHolder);
            } else {
                rowViewHolder = (ViewHolder)rowView.getTag();
            }

            ImageInfo info = imagesDescriptionBitmapList.get(position);


            if (info == null) {
                rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                rowViewHolder.tilesListView.setVisibility(View.GONE);
                rowViewHolder.descriptionTextView.setVisibility(View.GONE);
            } else {
                if (!info.imageDescription.getSubtitle().equals("")) {
                    rowViewHolder.subtitleTextView.setText(info.imageDescription.getSubtitle());
                    rowViewHolder.subtitleTextView.setVisibility(View.VISIBLE);
                } else {
                    rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                }

                rowViewHolder.tilesListView.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rowViewHolder.tilesListView.getLayoutParams();
                layoutParams.height = info.imageData.getTotalHeight();
                rowViewHolder.tilesListView.setLayoutParams(layoutParams);
                rowViewHolder.tilesListView.setAdapter(info.tilesBitmapListAdapter);


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

    private static final int SWIPE_THRESHOLD = 50;
    private static final int SWIPE_VELOCITY_THRESHOLD = 25;

    @Nullable private Artifact currentArtifact;
    private final List<ImageInfo> imagesDescriptionBitmapList;
    @Nullable private ImagesDescriptionBitmapListAdapter imagesDescriptionBitmapListAdapter;

    public MediaCarouselFragment() {
        this.currentArtifact = null;
        this.imagesDescriptionBitmapList = new ArrayList<ImageInfo>();
        this.imagesDescriptionBitmapListAdapter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarouselFragment", "Creating view");

        final View rootView = inflater.inflate(R.layout.fragment_media_carousel, container, false);

        ProgressBar waitingView = (ProgressBar)rootView.findViewById(R.id.waiting);

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
                if (currentArtifact == null) {
                    return;
                }

                String subject = String.format("%s via %s", currentArtifact.getTitle(), getActivity().getString(R.string.app_name));
                String text = String.format("%s via %s %s", currentArtifact.getTitle(), getActivity().getString(R.string.app_name), currentArtifact.getPageUrl());
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(i, getActivity().getString(R.string.share_title)));
            }
        });

        Controller.getInstance(getActivity()).getNextArtifact(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ZigZag/MediaCarouselFragment", "Resuming");
        Controller.getInstance(getActivity()).setListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("ZigZag/MediaCarouselFragment", "Pausing");
        Controller.getInstance(getActivity()).stopEverything();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onInitialArtifactData(Artifact artifact) {
        View rootView = getView();

        // Sometimes this gets called when there is no view - like when an orientation change
        // happens, or the parent fragment has stopped.
        if (rootView == null) {
            return;
        }

        currentArtifact = artifact;

        // Clear the current data structures and interface. Dispose of the associated bitmaps and
        // clear the tiles list.
        for (int ii = 0; ii < imagesDescriptionBitmapList.size(); ii++) {
            ImageInfo info = imagesDescriptionBitmapList.get(ii);
            info.tilesBitmaps.clear();
            info.framesBitmaps.clear();
            if (info.gifAnimationTask != null) {
                info.gifAnimationTask.cancel(true);
            }
            info.tilesBitmapListAdapter.notifyDataSetChanged();
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
            ImageInfo info = new ImageInfo(imageDescription, imageData, getActivity());

            if (imageData instanceof TooBigImageData) {
                continue;
            } else if (imageData instanceof ImageSetImageData) {
                ImageSetImageData imageSetImageData = (ImageSetImageData) imageData;
                for (int jj = 0; jj < imageSetImageData.getTilesDesc().size(); jj++) {
                    info.tilesBitmaps.add(null);
                }
            } else if (imageData instanceof AnimationSetImageData) {
                AnimationSetImageData animationSetImageData = (AnimationSetImageData) imageData;
                info.tilesBitmaps.add(null);
                for (int jj = 0; jj < animationSetImageData.getFramesDesc().size(); jj++) {
                    info.framesBitmaps.add(null);
                }
            }
            info.tilesBitmapListAdapter.notifyDataSetChanged();
            imagesDescriptionBitmapList.add(info);
        }
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImageForArtifact(Artifact artifact, int imageIdx, int tileOrFrameIdx, Bitmap image) {
        Log.i("ZigZag", String.format("Got %d/%d", imageIdx, tileOrFrameIdx));

        if (artifact != currentArtifact) {
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
            } else if (tileOrFrameIdx >= imageDescription.framesBitmaps.size()) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Got a frame image for list index %d, with tile %d but it" +
                        " has size %d", imageIdx, tileOrFrameIdx, imageDescription.tilesBitmaps.size()));
                return;
            }

            if (tileOrFrameIdx == 0) {
                imageDescription.tilesBitmaps.set(0, image);
            }
            imageDescription.framesBitmaps.set(tileOrFrameIdx, image);
        }

        imageDescription.tilesBitmapListAdapter.notifyDataSetChanged();
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorDescription) {
        Log.i("ZigZag", String.format("Error %s", errorDescription));
    }
}
