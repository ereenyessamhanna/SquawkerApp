package com.example.ereenyessam.squawker;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ereenyessam.squawker.provider.SquawkContract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ereeny Essam on 7/16/2017.
 */
public class SquawkAdapter extends RecyclerView.Adapter<SquawkAdapter.SquawkViewHolder>{

    private Cursor mCursor;
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("dd MMM");


    private static final long mMin = 1000 * 60;
    private static final long mHour = 60 * mMin;
    private static final long mDay = 24 * mHour;


    @Override
    public SquawkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_squwak_list,parent,false);

        SquawkViewHolder squawkViewHolder = new SquawkViewHolder(view);

        return squawkViewHolder;
    }

    @Override
    public void onBindViewHolder(SquawkViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String message = mCursor.getString(MainActivity.COL_NUM_MESSAGE);
        String author = mCursor.getString(MainActivity.COL_NUM_AUTHOR);
        String authorKey = mCursor.getString(MainActivity.COL_NUM_AUTHOR_KEY);

        long dateMillis = mCursor.getLong(MainActivity.COL_NUM_DATE);
        String date ="";
        long now = System.currentTimeMillis();

        if (now - dateMillis < (mDay)) {
            if (now - dateMillis < (mHour)) {
                long minutes = Math.round((now - dateMillis) / mMin);
                date = String.valueOf(minutes) + "m";
            } else {
                long minutes = Math.round((now - dateMillis) / mHour);
                date = String.valueOf(minutes) + "h";
            }
        } else {
            Date dateDate = new Date(dateMillis);
            date = sDateFormat.format(dateDate);
        }

        date = "\u2022 " + date;

        holder.messageTextView.setText(message);
        holder.authorTextView.setText(author);
        holder.dateTextView.setText(date);

        switch (authorKey) {
            case SquawkContract.ASSER_KEY:
                holder.authorImageView.setImageResource(R.drawable.ereeny);
                break;
            case SquawkContract.CEZANNE_KEY:
                holder.authorImageView.setImageResource(R.drawable.ereeny);
                break;
            case SquawkContract.JLIN_KEY:
                holder.authorImageView.setImageResource(R.drawable.ereeny);
                break;
            case SquawkContract.LYLA_KEY:
                holder.authorImageView.setImageResource(R.drawable.ereeny);
                break;
            case SquawkContract.NIKITA_KEY:
                holder.authorImageView.setImageResource(R.drawable.ereeny);
                break;
            default:
                holder.authorImageView.setImageResource(R.drawable.ereeny);
        }





    }

    @Override
    public int getItemCount() {
        if (mCursor == null)return 0;
        return mCursor.getCount();
    }
    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public class SquawkViewHolder extends RecyclerView.ViewHolder {

        final TextView authorTextView;
        final TextView messageTextView;
        final TextView dateTextView;
        final ImageView authorImageView;

        public SquawkViewHolder(View itemView) {
            super(itemView);
            authorTextView = (TextView) itemView.findViewById(R.id.author_text_view);
            messageTextView = (TextView) itemView.findViewById(R.id.message_text_view);
            dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);
            authorImageView = (ImageView) itemView.findViewById(
                    R.id.author_image_view);
        }
    }
}
