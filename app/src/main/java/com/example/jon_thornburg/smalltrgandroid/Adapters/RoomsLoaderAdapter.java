package com.example.jon_thornburg.smalltrgandroid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jon_thornburg.smalltrgandroid.Constants;
import com.example.jon_thornburg.smalltrgandroid.Models.RichardsRoom;
import com.example.jon_thornburg.smalltrgandroid.R;
import com.example.jon_thornburg.smalltrgandroid.Utils.BusyCheck;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by jon_thornburg on 1/12/17.
 */

public class RoomsLoaderAdapter extends RecyclerView.Adapter<RoomsLoaderAdapter.RoomsViewHolder> {

    public final static String TAG = RoomsLoaderAdapter.class.getSimpleName();

    private Context mContext;
    private List<RichardsRoom> mRooms = new ArrayList<>();
    private int rowLayout;
    private final OnItemClickListener<RichardsRoom> listener;


    public RoomsLoaderAdapter(Context mContext, int rowLayout, OnItemClickListener<RichardsRoom> listener) {
        this.mContext = mContext;
        this.rowLayout = rowLayout;
        this.listener = listener;
    }

    public void swapLists(List<RichardsRoom> newRooms) {
        mRooms.clear();
        if (newRooms != null) {
            mRooms.addAll(newRooms);
        }
    }

    @Override
    public RoomsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomsViewHolder holder, final int position) {
        holder.mRobinObject = mRooms.get(position);
        holder.roomNameText.setText(mRooms.get(position).getName());
        holder.bind(mRooms.get(position), listener);
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (listener != null) {

                    RichardsRoom room = mRooms.get(position);
                    listener.onItemClick(room);
                    Log.d(TAG, room.getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return mRooms.size();
    }



    public class RoomsViewHolder extends RecyclerView.ViewHolder {

        TextView roomNameText;
        RelativeLayout roomsLayout;
        TextView label1;
        TextView label2;
        TextView label3;
        TextView label4;
        TextView label5;
        RichardsRoom mRobinObject;
        TimeSlot[] slots;
        TextView[] textViews;

        public RoomsViewHolder(View itemView) {
            super(itemView);
            roomsLayout = (RelativeLayout) itemView.findViewById(R.id.room_item);
            roomNameText = (TextView) itemView.findViewById(R.id.room_name_textview);
            label1 = (TextView) itemView.findViewById(R.id.label_1);
            label2 = (TextView) itemView.findViewById(R.id.label_2);
            label3 = (TextView) itemView.findViewById(R.id.label_3);
            label4 = (TextView) itemView.findViewById(R.id.label_4);
            label5 = (TextView) itemView.findViewById(R.id.label_5);

            textViews = new TextView[5];


            textViews[0] = label1;
            textViews[1] = label2;
            textViews[2] = label3;
            textViews[3] = label4;
            textViews[4] = label5;

        }

        public void bind(final RichardsRoom room, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(room);
                    Log.d(TAG, room.getName());
                }
            });
            setTimeSlots();
            setTextViews();
            for (int i = 0; i < textViews.length; i ++) {
                textViews[i].setText(slots[i].startTimeString);
            }
        }

        private void setTimeSlots() {
            slots = new TimeSlot[5];
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            if (date.getMinutes() < 30) {
                cal = setCalendar(0);
            } else {
                cal = setCalendar(30);
            }
            int halfHour = 1800000;

            long startTime = cal.getTimeInMillis();
            long endTime = flattenLast3Digits(cal.getTimeInMillis()) + halfHour;
            for (int i = 0; i < 5; i++) {

                if (i != 0) {
                    startTime = startTime + halfHour;
                }

                String smallDate = dateFromTimestamp(startTime);

                if (i != 0) {
                    endTime = endTime + halfHour;
                }

                TimeSlot sl = new TimeSlot(smallDate, startTime, endTime);
                slots[i] = sl;
            }
        }

        private String dateFromTimestamp(long ts) {
            try {
                Calendar calendar = Calendar.getInstance();
                TimeZone tz = TimeZone.getTimeZone("CST");
                calendar.setTimeInMillis(ts);
                calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date currentDate = (Date) calendar.getTime();
                return format.format(currentDate);
            } catch (Exception e) {

            }
            return "";
        }

        private String longDateFromTS(long ts) {
            try {
                Calendar calendar = Calendar.getInstance();
                TimeZone tz = TimeZone.getTimeZone("CST");
                calendar.setTimeInMillis(ts);
                calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
                SimpleDateFormat format = new SimpleDateFormat(Constants.ISO_DATE_FORMAT);
                Date currentDate = (Date) calendar.getTime();
                return format.format(currentDate);
            } catch (Exception e) {

            }
            return "";
        }

        private void setTextViews() {
            //set default

            if (mRobinObject.getBusyTimes().size() > 0) {

                for (int i = 0; i < mRobinObject.getBusyTimes().size(); i++) {
                    for (int x = 0; x < slots.length; x++) {
                        if (x == 3) {
                            Log.d(TAG, mRobinObject.getName());
                        }
                        if (BusyCheck.isBusyForTimeInterval(slots[x], mRobinObject.getBusyTimes().get(i))) {
                            slots[x].setToBusy(true);
                            configureTextView(textViews[x], true);
                        } else {
                            if (!slots[x].isBusy()) {
                                configureTextView(textViews[x], false);
                            }

                        }
                    }
                }
            } else {
                for (int x = 0; x < slots.length; x++) {
                    configureTextView(textViews[x], false);
                }
            }

        }

    }

    private long flattenLast3Digits(long ts) {
        double decimalable = (double) ts;
        decimalable = decimalable / 1000;
        long flatLong = (long) decimalable;
        flatLong = flatLong * 1000;
        return flatLong;
    }

    private void configureTextView(TextView textv, boolean busy) {
        textv.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        textv.setBackgroundColor(mContext.getResources().getColor(R.color.colorGrey));
        if (!busy) {
            textv.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
            textv.setTextColor(mContext.getResources().getColor(R.color.colorGrey));
            textv.setText(mContext.getResources().getString(R.string.room_busy));
        }
     }

    private static Calendar setCalendar(int minutes) {

        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        cal.set(year, month, day, hour, minutes, 0);
        return cal;
    }

    public class TimeSlot {
        String startTimeString;
        long startTime;
        long endTime;
        boolean busy = false;

        TimeSlot(String start, long startTime, long endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.startTimeString = start;

        }

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setToBusy(boolean busy) {
            this.busy = busy;
        }

        public boolean isBusy() {
            return busy;
        }
    }

}
