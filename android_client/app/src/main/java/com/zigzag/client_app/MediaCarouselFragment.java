package com.zigzag.client_app;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.model.EntityId;
import com.zigzag.client_app.model.ImageDescription;

import java.util.ArrayList;
import java.util.List;

public class MediaCarouselFragment extends Fragment implements Controller.NextArtifactListener {
    private static class ImagesDescriptionBitmapListAdapter extends ArrayAdapter<Pair<ImageDescription, Bitmap>> {
        private static class ViewHolder {
            ProgressBar progressBar;
            TextView subtitleTextView;
            ResizableImageView imageView;
            TextView descriptionTextView;
        }

        private final List<Pair<ImageDescription, Bitmap>> imagesDescriptionBitmapList;

        public ImagesDescriptionBitmapListAdapter(Context context, List<Pair<ImageDescription, Bitmap>> imagesDescriptionBitmapList) {
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

                rowViewHolder.progressBar = (ProgressBar) rowView.findViewById(R.id.waiting);
                rowViewHolder.subtitleTextView = (TextView) rowView.findViewById(R.id.subtitle);
                rowViewHolder.imageView = (ResizableImageView) rowView.findViewById(R.id.image);
                rowViewHolder.descriptionTextView = (TextView) rowView.findViewById(R.id.description);

                rowView.setTag(rowViewHolder);
            } else {
                rowViewHolder = (ViewHolder)rowView.getTag();
            }

            Pair<ImageDescription, Bitmap> pair = imagesDescriptionBitmapList.get(position);

            if (pair == null) {
                rowViewHolder.progressBar.setVisibility(View.VISIBLE);
                rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                rowViewHolder.imageView.setVisibility(View.GONE);
                rowViewHolder.descriptionTextView.setVisibility(View.GONE);
            } else {
                ImageDescription imageDescription = pair.first;
                Bitmap bitmap = pair.second;

                rowViewHolder.progressBar.setVisibility(View.GONE);

                if (!imageDescription.getSubtitle().equals("")) {
                    rowViewHolder.subtitleTextView.setText(imageDescription.getSubtitle());
                    rowViewHolder.subtitleTextView.setVisibility(View.VISIBLE);
                } else {
                    rowViewHolder.subtitleTextView.setVisibility(View.GONE);
                }

                rowViewHolder.imageView.setVisibility(View.VISIBLE);
                rowViewHolder.imageView.setImageBitmap(bitmap);

                if (!imageDescription.getDescription().equals("")) {
                    rowViewHolder.descriptionTextView.setText(imageDescription.getDescription());
                    rowViewHolder.descriptionTextView.setVisibility(View.VISIBLE);
                } else {
                    rowViewHolder.descriptionTextView.setVisibility(View.GONE);
                }
            }

            return rowView;
        }
    }

    private EntityId currentImageId;
    private final List<Pair<ImageDescription, Bitmap>> imagesDescriptionBitmapList;
    @Nullable private ImagesDescriptionBitmapListAdapter imagesDescriptionBitmapListAdapter;

    public MediaCarouselFragment() {
        this.currentImageId = null;
        this.imagesDescriptionBitmapList = new ArrayList<Pair<ImageDescription, Bitmap>>();
        this.imagesDescriptionBitmapListAdapter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarouselFragment", "Creating view");

        View rootView = inflater.inflate(R.layout.fragment_media_carousel, container, false);

        ProgressBar waitingView = (ProgressBar)rootView.findViewById(R.id.waiting);

        imagesDescriptionBitmapListAdapter = new ImagesDescriptionBitmapListAdapter(getActivity(), this.imagesDescriptionBitmapList);

        ListView imageListView = (ListView)rootView.findViewById(R.id.image_list);
        imageListView.setEmptyView(waitingView);
        imageListView.setAdapter(imagesDescriptionBitmapListAdapter);

        Button saveButton = (Button)rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View saveButton) {
            }
        });

        Button shareButton = (Button)rootView.findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View shareButton) {
            }
        });

        Button nextButton = (Button)rootView.findViewById(R.id.next_button);
        final MediaCarouselFragment thisForClosure = this;
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View nextButton) {
            Controller.getInstance(getActivity()).getNextArtifact(thisForClosure);
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
    public void onInitialArtifactData(EntityId id, String title, String sourceName, int numberOfImages) {
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
        imagesDescriptionBitmapList.clear();
        for (int ii = 0; ii < numberOfImages; ii++) {
            imagesDescriptionBitmapList.add(null);
        }
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();
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
        imagesDescriptionBitmapList.set(imageIdx, new Pair(imageDescription, image));
        imagesDescriptionBitmapListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorDescription) {
        Log.i("ZigZag", String.format("Error %s", errorDescription));
    }
}
