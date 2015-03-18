package com.zigzag.client_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.model.EntityId;
import com.zigzag.client_app.model.ImageDescription;

import java.util.ArrayList;
import java.util.List;

public class MediaCarouselFragment extends Fragment
        implements Controller.ArtifactListener, GestureDetector.OnGestureListener {
    private static class ImageInfo {
        ImageDescription imageDescription;
        // One of these two is non-null, the other is null.
        @Nullable List<Bitmap> tilesBitmaps;
        @Nullable List<Bitmap> framesBitmaps;
        TilesBitmapListAdapter tilesBitmapListAdapter;

        public ImageInfo(ImageDescription imageDescription, @Nullable List<Bitmap> tilesBitmaps, @Nullable List<Bitmap> framesBitmaps, Context context) {
            this.imageDescription = imageDescription;
            this.tilesBitmaps = tilesBitmaps;
            this.framesBitmaps = framesBitmaps;
            this.tilesBitmapListAdapter = new TilesBitmapListAdapter(context, this.tilesBitmaps);
        }
    }

    private static class TilesBitmapListAdapter extends ArrayAdapter<Bitmap> {
        private static class ViewHolder {
            ProgressBar progressBar;
            ImageView imageView;
        }

        private final List<Bitmap> tilesBitmapList;

        public TilesBitmapListAdapter(Context context, List<Bitmap> tilesBitmapList) {
            super(context, R.layout.fragment_media_carousel_one_image_tile, tilesBitmapList);
            this.tilesBitmapList = tilesBitmapList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = convertView;
            ViewHolder rowViewHolder;

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
            } else {
                rowViewHolder.progressBar.setVisibility(View.GONE);
                rowViewHolder.imageView.setVisibility(View.VISIBLE);
                rowViewHolder.imageView.setImageBitmap(bitmap);
            }

            return rowView;
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

    @Nullable private EntityId currentImageId;
    private final List<ImageInfo> imagesDescriptionBitmapList;
    @Nullable private ImagesDescriptionBitmapListAdapter imagesDescriptionBitmapListAdapter;
    @Nullable private GestureDetectorCompat gestureDetector;
    @Nullable private String currentTitle;
    @Nullable private String currentPageUrl;

    public MediaCarouselFragment() {
        this.currentImageId = null;
        this.imagesDescriptionBitmapList = new ArrayList<ImageInfo>();
        this.imagesDescriptionBitmapListAdapter = null;
        this.gestureDetector = null;
        this.currentTitle = null;
        this.currentPageUrl = null;
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
                if (currentTitle == null || currentPageUrl == null) {
                    return;
                }

                String subject = String.format("%s via %s", currentTitle, getActivity().getString(R.string.app_name));
                String text = String.format("%s via %s %s", currentTitle, getActivity().getString(R.string.app_name), currentPageUrl);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(i, getActivity().getString(R.string.share_title)));
            }
        });

        // Setup gesture handling.
        gestureDetector = new GestureDetectorCompat(getActivity(), this);
        final MediaCarouselFragment thisForClosure = this;
        imageListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (thisForClosure.gestureDetector == null) {
                    return false;
                }

                imageListView.onTouchEvent(e);
                return thisForClosure.gestureDetector.onTouchEvent(e);
            }
        });
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (thisForClosure.gestureDetector == null) {
                    return false;
                }

                rootView.onTouchEvent(e);
                return thisForClosure.gestureDetector.onTouchEvent(e);
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
    public void onInitialArtifactData(EntityId id, String title, String pageUrl, String sourceName, int numberOfImages) {
        Log.i("ZigZag", String.format("Got %s %s - %d", title, sourceName, numberOfImages));
        View rootView = getView();

        // Sometimes this gets called when there is no view - like when an orientation change
        // happens, or the parent fragment has stopped.
        if (rootView == null) {
            return;
        }

        // Make the master waiting progress bar invisible, since there'll be individual image
        // progress bars.
        rootView.findViewById(R.id.waiting).setVisibility(View.GONE);

        // Setup title for artifact.
        TextView titleView = (TextView)rootView.findViewById(R.id.title);
        titleView.setText(String.format("%s - %s", title, sourceName));

        // Setup list view with all the images in the artifact. Reconstruct the list of bitmaps
        // to contain only nulls and the associated adapter and associate them with the image
        // list view.
        currentImageId = id;
        for (int ii = 0; ii < imagesDescriptionBitmapList.size(); ii++) {
            ImageInfo info = imagesDescriptionBitmapList.get(ii);
            if (info == null) {
                continue;
            }
            // TODO(horia141): proper recycling here.
            // info.bitmap.recycle();
        }
        imagesDescriptionBitmapList.clear();
        for (int ii = 0; ii < numberOfImages; ii++) {
            imagesDescriptionBitmapList.add(null);
        }
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();

        // Update global state about the image.
        currentTitle = title;
        currentPageUrl = pageUrl;
    }

    @Override
    public void onImageForArtifact(EntityId id, int imageIdx, ImageDescription imageDescription, Bitmap image) {
        Log.i("ZigZag", String.format("Got %d", imageIdx));

        if (imageIdx < 0) {
            return;
        } else if (imageIdx >= imagesDescriptionBitmapList.size()) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got an image for list index %d, but list" +
                    " has size %d", imageIdx, imagesDescriptionBitmapList.size()));
            return;
        }

        if (id != currentImageId) {
            Log.e("ZigZag/MediaCarouselFragment", "Got out of sync image update");
            return;
        }

        // Update the list of bitmaps and notify the adapter about it.
        List<Bitmap> bitmapsList = new ArrayList<Bitmap>();
        bitmapsList.add(image);
        ImageInfo info = new ImageInfo(imageDescription, bitmapsList, null, getActivity());
        imagesDescriptionBitmapList.set(imageIdx, info);
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorDescription) {
        Log.i("ZigZag", String.format("Error %s", errorDescription));
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d("ZigZag/MediaCarouselFragment","onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        Log.d("ZigZag/MediaCarouselFragment", "onFling: " + event1.toString()+event2.toString());

        float diffY = event2.getY() - event1.getY();
        float diffX = event2.getX() - event1.getX();
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    // Swiping to the right.
                    Controller.getInstance(getActivity()).getPrevArtifact(this);
                } else {
                    // Swiping to the left.
                    Controller.getInstance(getActivity()).getNextArtifact(this);
                }
            }
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d("ZigZag/MediaCarouselFragment", "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("ZigZag/MediaCarouselFragment", "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d("ZigZag/MediaCarouselFragment", "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d("ZigZag/MediaCarouselFragment", "onSingleTapUp: " + event.toString());
        return true;
    }
}
