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

import com.example.jon_thornburg.smalltrgandroid.Adapters.EmployeeLoaderAdapter;
import com.example.jon_thornburg.smalltrgandroid.Adapters.OnItemClickListener;
import com.example.jon_thornburg.smalltrgandroid.Models.Employee;
import com.example.jon_thornburg.smalltrgandroid.R;
import com.example.jon_thornburg.smalltrgandroid.Utils.HttpClient;
import com.example.jon_thornburg.smalltrgandroid.Utils.NetworkInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by jon_thornburg on 1/11/17.
 */

public class PeopleFragment extends Fragment implements OnItemClickListener<Employee> {

    public final static String TAG = PeopleFragment.class.getSimpleName();

    private View mView;
    private RecyclerView mRecyclerView;
    private EmployeeLoaderAdapter mAdapter;
    private NavigationListener mNavigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (context instanceof NavigationListener) {
                mNavigationListener = (NavigationListener) context;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_people, container, false);

        mAdapter = new EmployeeLoaderAdapter(getActivity(), R.layout.employee_list_item, this);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview_employees);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reload(true);

    }

    private void reload(boolean init) {
        if (init) {
            getLoaderManager().initLoader(ALL_EMPLOYEES_LOADER, null, mLoaderCallback);
        } else {
            getLoaderManager().restartLoader(ALL_EMPLOYEES_LOADER, null, mLoaderCallback);
        }

    }

    public static PeopleFragment createNew() {
        PeopleFragment fragment = new PeopleFragment();
        return fragment;
    }



    @Override
    public void onItemClick(Employee item) {
        mNavigationListener.itemBubbler(item);
    }

    protected static final int ALL_EMPLOYEES_LOADER = 0;
    private LoaderManager.LoaderCallbacks<List<Employee>> mLoaderCallback = new LoaderManager.LoaderCallbacks<List<Employee>>() {

        @Override
        public Loader<List<Employee>> onCreateLoader(int id, Bundle args) {
            switch (id) {
                case ALL_EMPLOYEES_LOADER:
                    PersonListLoader listLoader = new PersonListLoader(getActivity());
                     return listLoader;
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<List<Employee>> loader, List<Employee> data) {
            List<Employee> employees = data;
            mAdapter.swapList(employees);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<Employee>> loader) {
            mAdapter.swapList(null);
        }
    };

    public static class PersonListLoader extends AsyncTaskLoader<List<Employee>> {

        List<Employee> mItems;
        public PersonListLoader(Context context) {
            super(context);
        }

        /**
         * This is where the bulk of our work is done.  This function is
         * called in a background thread and should generate a new set of
         * data to be published by the loader.
         */
        @Override public List<Employee> loadInBackground() {

            NetworkInterface mInterface =HttpClient.getClient().create(NetworkInterface.class);

            Call<List<Employee>> call = mInterface.getEmployees();
            List<Employee> items = new ArrayList<>();
            try {
                items = call.execute().body();
            } catch (IOException exception) {
                Log.e(TAG, "Failed", exception);
            }


            // Done!
            return items;
        }

        /**
         * Called when there is new data to deliver to the client.  The
         * super class will take care of delivering it; the implementation
         * here just adds a little more logic.
         */
        @Override public void deliverResult(List<Employee> apps) {
            if (isReset()) {
                // An async query came in while the loader is stopped.  We
                // don't need the result.
                if (apps != null) {
                    onReleaseResources(apps);
                }
            }
            List<Employee> oldApps = mItems;
            mItems = apps;

            if (isStarted()) {
                // If the Loader is currently started, we can immediately
                // deliver its results.
                super.deliverResult(apps);
            }

            // At this point we can release the resources associated with
            // 'oldApps' if needed; now that the new result is delivered we
            // know that it is no longer in use.
            if (oldApps != null) {
                onReleaseResources(oldApps);
            }
        }

        /**
         * Handles a request to start the Loader.
         */
        @Override protected void onStartLoading() {
            if (mItems != null) {
                // If we currently have a result available, deliver it
                // immediately.
                deliverResult(mItems);
            }


            if (takeContentChanged() || mItems == null) {
                // If the data has changed since the last time it was loaded
                // or is not currently available, start a load.
                forceLoad();
            }
        }

        /**
         * Handles a request to stop the Loader.
         */
        @Override protected void onStopLoading() {
            // Attempt to cancel the current load task if possible.
            cancelLoad();
        }

        /**
         * Handles a request to cancel a load.
         */
        @Override public void onCanceled(List<Employee> apps) {
            super.onCanceled(apps);

            // At this point we can release the resources associated with 'apps'
            // if needed.
            onReleaseResources(apps);
        }

        /**
         * Handles a request to completely reset the Loader.
         */
        @Override protected void onReset() {
            super.onReset();

            // Ensure the loader is stopped
            onStopLoading();

        }

        /**
         * Helper function to take care of releasing resources associated
         * with an actively loaded data set.
         */
        protected void onReleaseResources(List<Employee> apps) {
            // For a simple List<> there is nothing to do.  For something
            // like a Cursor, we would close it here.
        }
    }
}
