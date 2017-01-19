package com.example.jon_thornburg.smalltrgandroid.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jon_thornburg.smalltrgandroid.Adapters.OnItemClickListener;
import com.example.jon_thornburg.smalltrgandroid.Adapters.RoomsLoaderAdapter;
import com.example.jon_thornburg.smalltrgandroid.Models.RichardsRoom;
import com.example.jon_thornburg.smalltrgandroid.Models.RobinBusyTime;
import com.example.jon_thornburg.smalltrgandroid.Models.RobinObject;
import com.example.jon_thornburg.smalltrgandroid.Models.SpaceNode;
import com.example.jon_thornburg.smalltrgandroid.R;
import com.example.jon_thornburg.smalltrgandroid.Utils.HttpClient;
import com.example.jon_thornburg.smalltrgandroid.Utils.Iso8601DateMaker;
import com.example.jon_thornburg.smalltrgandroid.Utils.RoomsInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by jon_thornburg on 1/11/17.
 */

public class PlacesFragment extends Fragment implements OnItemClickListener<RichardsRoom> {

    public final static String TAG = PlacesFragment.class.getSimpleName();

    private View mView;
    private RecyclerView mRecyclerView;
    private RoomsLoaderAdapter mAdapter;
    private NavigationListener mNavigationListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (context instanceof NavigationListener) {
                this.mNavigationListener = (NavigationListener) context;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_places, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rooms_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RoomsLoaderAdapter(getActivity(), R.layout.rooms_list_item, this);
        mRecyclerView.setAdapter(mAdapter);
        reload(false);
        return mView;
    }

    public static PlacesFragment createNew() {
        PlacesFragment fragment = new PlacesFragment();
        return fragment;
    }

    @Override
    public void onItemClick(RichardsRoom item) {
        Log.d(TAG, item.getName() + ": Clicked and in delegate>>>>>>");
        mNavigationListener.itemBubbler(item);
    }

    private void reload(boolean init) {
        if (init) {
            getLoaderManager().initLoader(ALL_ROOMS_LOADER, null, mLoaderCallback);
        } else {
            getLoaderManager().restartLoader(ALL_ROOMS_LOADER, null, mLoaderCallback);
        }
    }
    private final static int ALL_ROOMS_LOADER = 0;
    private LoaderManager.LoaderCallbacks<List<RichardsRoom>> mLoaderCallback = new LoaderManager.LoaderCallbacks<List<RichardsRoom>>() {

        @Override
        public Loader<List<RichardsRoom>> onCreateLoader(int id, Bundle args) {
            switch (id) {
                case ALL_ROOMS_LOADER:
                    RoomsListLoader listLoader = new RoomsListLoader(getActivity());
                    return listLoader;
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<List<RichardsRoom>> loader, List<RichardsRoom> data) {

            List<RichardsRoom> rooms = data;
            mAdapter.swapLists(rooms);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<RichardsRoom>> loader) {
            mAdapter.swapLists(null);
        }
    };

    public static class RoomsListLoader extends AsyncTaskLoader<List<RichardsRoom>> {

        List<RichardsRoom> mRooms;

        public RoomsListLoader(Context context) {
            super(context);
        }

        @Override
        public List<RichardsRoom> loadInBackground() {

            String toFilter = Iso8601DateMaker.robinToFilter();
            String fromFilter = Iso8601DateMaker.robinFromFilter();
            RoomsInterface mInterface = HttpClient.getRoomsClient().create(RoomsInterface.class);

            Call<RobinObject> call = mInterface.getRooms(3771, 1, 100, toFilter, fromFilter);
            List<RichardsRoom> rooms = new ArrayList<>();
            try {
                List<SpaceNode> nodes = call.execute().body().getSpaceNodes();
                for (int i = 1; i < nodes.size(); i++) {
                    List<RobinBusyTime> times = nodes.get(i).getBusy();
                    RichardsRoom room = nodes.get(i).getRichardsRoom();
                    room.setBusyTimes(nodes.get(i).getBusy());
                    rooms.add(room);
                }

            } catch (IOException e) {
                Log.e(TAG, "Failed", e);
            }
            return rooms;
        }

        @Override
        public void deliverResult(List<RichardsRoom> data) {
            super.deliverResult(data);

            List<RichardsRoom> oldData = mRooms;
            mRooms = data;

            if (isStarted()) {
                super.deliverResult(data);
            }
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();

            if (mRooms != null) {
                deliverResult(mRooms);
            }
            if (takeContentChanged() || mRooms == null) {
                forceLoad();
            }
        }
    }
}
