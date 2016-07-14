package com.sreesha.android.moviebuzz.MovieDataRenderingClasses.MovieDetailTabsView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.sreesha.android.moviebuzz.Animation.CustomAnimator;
import com.sreesha.android.moviebuzz.DataHandlerClasses.CursorRecyclerViewAdapter;
import com.sreesha.android.moviebuzz.MovieDataRenderingClasses.PeopleDisplay.PeopleProfileActivity;
import com.sreesha.android.moviebuzz.MovieDataRenderingClasses.PeopleDisplay.PeopleProfileFragment;
import com.sreesha.android.moviebuzz.Networking.APIUrls;
import com.sreesha.android.moviebuzz.Networking.CrewDataInstance;
import com.sreesha.android.moviebuzz.Networking.PopularPeopleInstance;
import com.sreesha.android.moviebuzz.R;

/**
 * Created by Sreesha on 19-04-2016.
 */
public class CrewDataCursorAdapter
        extends CursorRecyclerViewAdapter<CrewDataCursorAdapter.ViewHolder> {
    Context mContext;

    public CrewDataCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_crew_item, parent, false);
        return new ViewHolder(view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView crewNameTextView;
        public TextView crewDepartmentTextView;
        public TextView crewJobTextView;
        public CardView crewContainerCard;
        public CardView backGroundSheetCard;
        public ImageView profileImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            crewNameTextView = (TextView) itemView.findViewById(R.id.crewNameTextView);
            crewDepartmentTextView = (TextView) itemView.findViewById(R.id.crewDepartmentTextView);
            crewJobTextView = (TextView) itemView.findViewById(R.id.crewJobTextView);
            crewContainerCard = (CardView) itemView.findViewById(R.id.profileCardContainer);
            profileImageView = (ImageView) itemView.findViewById(R.id.crewProfileImageView);
            backGroundSheetCard = (CardView) itemView.findViewById(R.id.backGroundSheetCard);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, Cursor cursor) {
        Log.e("CastCrewDebug", "onBindViewHolder : " + cursor.getCount());
        final CrewDataInstance instance = CrewDataInstance.getCrewDataInstanceFromCursor(cursor);
        holder.crewNameTextView.setText(instance.getNAME().trim());
        holder.crewDepartmentTextView.setText(instance.getDEPARTMENT().trim());
        holder.crewJobTextView.setText(instance.getJOB().trim());
        holder.profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopularPeopleInstance mInstance = new PopularPeopleInstance(
                        instance.getID()
                        , 0
                        , null
                        , null
                        , null
                        , 0
                );
                Intent intent = new Intent(v.getContext(), PeopleProfileActivity.class);
                Bundle args = new Bundle();
                args.putParcelable(PeopleProfileFragment.POPULAR_PERSON_INSTANCE_DATA_KEY, mInstance);
                intent.putExtra(PeopleProfileFragment.POPULAR_PERSON_INSTANCE_DATA_KEY, args);
                v.getContext().startActivity(intent);
            }
        });
        Target imageViewTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (bitmap != null) {
                    holder.profileImageView.setImageBitmap(bitmap);
                    Palette.PaletteAsyncListener asyncListener = new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            holder.crewContainerCard
                                    .setCardBackgroundColor(
                                            palette
                                                    .getDarkMutedColor(
                                                            mContext
                                                                    .getResources()
                                                                    .getColor(R.color.colorPrimary)
                                                    )
                                    );
                            holder.backGroundSheetCard
                                    .setCardBackgroundColor(
                                            palette
                                                    .getLightMutedColor(
                                                            mContext
                                                                    .getResources()
                                                                    .getColor(R.color.colorPrimary)
                                                    )
                                    );
                        }
                    };
                    Palette.from(bitmap).generate(asyncListener);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        String URL = APIUrls.BASE_IMAGE_URL
                + "/" + APIUrls.API_IMG_W_342
                + instance
                .getPROFILE_PATH();
        Picasso.with(mContext)
                .load(URL)
                .placeholder(R.drawable.error_person_avathar)
                .error(R.drawable.error_person_avathar)
                .into(imageViewTarget);
        Log.e("CastCrewDebug : ", URL);

    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        CustomAnimator.slideAnimRecyclerView(holder, true);
    }
}