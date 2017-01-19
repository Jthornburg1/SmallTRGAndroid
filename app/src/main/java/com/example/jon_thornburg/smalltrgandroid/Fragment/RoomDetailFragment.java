package com.example.jon_thornburg.smalltrgandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon_thornburg.smalltrgandroid.Models.RichardsRoom;
import com.example.jon_thornburg.smalltrgandroid.R;
import com.example.jon_thornburg.smalltrgandroid.Utils.PicassoManager;

/**
 * Created by jon_thornburg on 1/19/17.
 */

public class RoomDetailFragment extends Fragment {

    public final static String TAG = RoomDetailFragment.class.getSimpleName();
    private final static String EXTRA_ROOM = "extra_richards_room";

    private TextView mRoomName;
    private ImageView mRoomImage;
    private TextView mTypeText;
    private View mView;
    private RichardsRoom mRoom;

    public static RoomDetailFragment newInstance(RichardsRoom room) {
        RoomDetailFragment fragment = new RoomDetailFragment();
        Bundle args= new Bundle();
        args.putSerializable(EXTRA_ROOM, room);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_room_detail,container, false);
        mRoomName = (TextView) mView.findViewById(R.id.room_detail_label);
        mRoomImage = (ImageView) mView.findViewById(R.id.detail_room_image);
        mTypeText = (TextView) mView.findViewById(R.id.room_type_text);
        if (getArguments() != null) {
            mRoom = (RichardsRoom) getArguments().getSerializable(EXTRA_ROOM);
            mRoomName.setText(mRoom.getName());
            mTypeText.setText(mRoom.getType());
            String imageUrl = mRoom.getImageUrl();
            PicassoManager manager = new PicassoManager();
            manager.loadRoomPicasso(mRoomImage, imageUrl, getContext());
        }
        return mView;
    }
}
