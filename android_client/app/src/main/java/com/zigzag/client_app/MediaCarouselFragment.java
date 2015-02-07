package com.zigzag.client_app;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.model.EntityId;
import com.zigzag.client_app.model.ImageDescription;

import java.util.ArrayList;
import java.util.List;

public class MediaCarouselFragment extends Fragment implements Controller.NextArtifactListener {
    private static class BitmapListAdapter extends ArrayAdapter<Pair<ImageDescription, Bitmap>> {
        private final List<Pair<ImageDescription, Bitmap>> bitmapList;

        public BitmapListAdapter(Context context, List<Pair<ImageDescription, Bitmap>> bitmapList) {
            super(context, R.layout.fragment_media_carousel_one_image, bitmapList);
            this.bitmapList = bitmapList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Build the basic view of the element.
            View rowView = inflater.inflate(R.layout.fragment_media_carousel_one_image, parent, false);

            ProgressBar progressBar = (ProgressBar)rowView.findViewById(R.id.waiting);
            TextView subtitleTextView = (TextView)rowView.findViewById(R.id.subtitle);
            ResizableImageView imageView = (ResizableImageView)rowView.findViewById(R.id.image);
            TextView descriptionTextView = (TextView)rowView.findViewById(R.id.description);

            Pair<ImageDescription, Bitmap> pair = bitmapList.get(position);

            if (pair == null) {
                progressBar.setVisibility(View.VISIBLE);
                subtitleTextView.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                descriptionTextView.setVisibility(View.GONE);
            } else {
                ImageDescription imageDescription = pair.first;
                Bitmap bitmap = pair.second;

                progressBar.setVisibility(View.GONE);

                if (!imageDescription.getSubtitle().equals("")) {
                    subtitleTextView.setText(imageDescription.getSubtitle());
                    subtitleTextView.setVisibility(View.VISIBLE);
                } else {
                    subtitleTextView.setVisibility(View.GONE);
                }

                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);

                if (!imageDescription.getDescription().equals("")) {
                    descriptionTextView.setText(imageDescription.getDescription());
                    descriptionTextView.setVisibility(View.VISIBLE);
                } else {
                    descriptionTextView.setVisibility(View.GONE);
                }
            }

            return rowView;
        }
    }

    private EntityId currentImageId;
    private final List<Pair<ImageDescription, Bitmap>> bitmapList;
    @Nullable private BitmapListAdapter bitmapListAdapter;

    public MediaCarouselFragment() {
        this.currentImageId = null;
        this.bitmapList = new ArrayList<Pair<ImageDescription, Bitmap>>();
        this.bitmapListAdapter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarouselFragment", "Creating view");

        View rootView = inflater.inflate(R.layout.fragment_media_carousel, container, false);

        ProgressBar waitingView = (ProgressBar)rootView.findViewById(R.id.waiting);

        bitmapListAdapter = new BitmapListAdapter(getActivity(), this.bitmapList);

        ListView imageListView = (ListView)rootView.findViewById(R.id.image_list);
        imageListView.setEmptyView(waitingView);
        imageListView.setAdapter(bitmapListAdapter);

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
        bitmapList.clear();
        for (int ii = 0; ii < numberOfImages; ii++) {
            bitmapList.add(null);
        }
        bitmapListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImageForArtifact(EntityId id, int imageIdx, ImageDescription imageDescription, Bitmap image) {
        Log.i("ZigZag", String.format("Got %d", imageIdx));

        if (imageIdx < 0) {
            return;
        } else if (imageIdx >= bitmapList.size()) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got an image for list index %d, but list" +
                    " has size %d", imageIdx, bitmapList.size()));
            return;
        }

        if (id != currentImageId) {
            Log.e("ZigZag/MediaCarouselFragment", "Got out of sync image update");
            return;
        }

        // Update the list of bitmaps and notify the adapter about it.
        bitmapList.set(imageIdx, new Pair(imageDescription, image));
        bitmapListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorDescription) {
        Log.i("ZigZag", String.format("Error %s", errorDescription));
    }
}
