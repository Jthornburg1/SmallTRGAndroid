package com.example.jon_thornburg.smalltrgandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon_thornburg.smalltrgandroid.Models.Employee;
import com.example.jon_thornburg.smalltrgandroid.R;
import com.example.jon_thornburg.smalltrgandroid.Utils.PicassoManager;

/**
 * Created by jon_thornburg on 1/19/17.
 */

public class EmployeeDetailFragment extends Fragment {

    public final static String TAG = EmployeeDetailFragment.class.getSimpleName();
    private final static String EXTRA_EMPLOYEE = "extra_employee";

    private Employee mEmployee;
    private TextView mNameLabel;
    private ImageView mImage;
    private TextView mDiscipline;
    private View mView;

    public static EmployeeDetailFragment newInstance(Employee employee) {
        EmployeeDetailFragment frag = new EmployeeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_EMPLOYEE, employee);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_employee_detail, container, false);
        mNameLabel = (TextView) mView.findViewById(R.id.employee_detail_text);
        mDiscipline = (TextView) mView.findViewById(R.id.employee_detail_discipline);
        mImage = (ImageView) mView.findViewById(R.id.employee_detail_image);
        if (getArguments() != null) {
            mEmployee = (Employee) getArguments().getSerializable(EXTRA_EMPLOYEE);
            mNameLabel.setText(mEmployee.getFullName());
            mDiscipline.setText(mEmployee.getDiscipline());
            String urlString = mEmployee.getLargeImageUrlString();
            PicassoManager manager = new PicassoManager();
            manager.loadEmployeePicasso(mImage, urlString, getContext());
        }
        return mView;
    }
}
