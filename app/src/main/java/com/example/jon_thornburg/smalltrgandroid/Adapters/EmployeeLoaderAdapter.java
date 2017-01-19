package com.example.jon_thornburg.smalltrgandroid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon_thornburg.smalltrgandroid.Models.Employee;
import com.example.jon_thornburg.smalltrgandroid.R;
import com.example.jon_thornburg.smalltrgandroid.Utils.PicassoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon_thornburg on 1/11/17.
 */

public class EmployeeLoaderAdapter extends RecyclerView.Adapter<EmployeeLoaderAdapter.EmployeeViewHolder> {

    public final static String TAG = EmployeeLoaderAdapter.class.getSimpleName();

    private Context context;
    private List<Employee> employees = new ArrayList<>();
    private int rowLayout;
    private final OnItemClickListener<Employee> listener;

    public EmployeeLoaderAdapter(Context context, int rowLayout, OnItemClickListener<Employee> listener) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.listener = listener;
    }

    public void swapList(List<Employee> newList) {
        employees.clear();
        if (newList != null) {
            employees.addAll(newList);
        }
    }

    @Override
    public EmployeeLoaderAdapter.EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, final int position) {
        holder.fullName.setText(employees.get(position).getFullName());
        holder.discipline.setText(employees.get(position).getDiscipline());
        String displn = employees.get(position).getDiscipline();
        PicassoManager manager = new PicassoManager();
        holder.bind(employees.get(position), listener);
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    Employee employee = employees.get(position);
                    listener.onItemClick(employee);
                    Log.d(TAG, "An Employee: " + employee.getFullName());
                }
            }
        });
        manager.loadEmployeePicasso(holder.empImg, employees.get(position).getSmallImageUrlString(), context);
    }


    @Override
    public int getItemCount() {
        return employees.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        ImageView empImg;
        TextView fullName;
        TextView discipline;

        public EmployeeViewHolder(View itemView) {
            super(itemView);

            empImg = (ImageView) itemView.findViewById(R.id.item_image);
            fullName = (TextView) itemView.findViewById(R.id.name_label);
            discipline = (TextView) itemView.findViewById(R.id.discipline_label);
        }

        public void bind(final Employee employee, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    listener.onItemClick(employee);
                }
            });
        }
    }
}
