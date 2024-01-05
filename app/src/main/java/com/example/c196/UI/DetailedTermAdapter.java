package com.example.c196.UI;

import android.content.Intent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.entities.Course;

import java.util.List;

public class DetailedTermAdapter extends RecyclerView.Adapter<DetailedTermAdapter.DetailedTermViewHolder> {

    public DetailedTermAdapter(Context context, LayoutInflater mInflater) {
        this.context = context;
        this.mInflater = mInflater;
    }

    class DetailedTermViewHolder extends RecyclerView.ViewHolder {

        private final TextView DetailedTermItemView;

        private DetailedTermViewHolder(View itemView) {
            super(itemView);
            DetailedTermItemView = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position=getAbsoluteAdapterPosition();
                    final Course current = mAssociatedCourses.get(position);
                    Intent intent = new Intent(context, DetailedCourseView.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhoneNumber", current.getInstructorPhoneNumber());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("termID", current.getTerm());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mAssociatedCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public DetailedTermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public DetailedTermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.class_list_item, parent, false);
        return new DetailedTermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailedTermViewHolder holder, int position) {
        if (mAssociatedCourses != null && !mAssociatedCourses.isEmpty()) {
            Course current = mAssociatedCourses.get(position);
            String courseName = current.getCourseName();
            holder.DetailedTermItemView.setText(courseName);
        } else {
            holder.DetailedTermItemView.setText("No Course Name Available");
        }
    }


    @Override
    public int getItemCount() {
        return mAssociatedCourses.size();
    }

    public void setAssociatedCourses(List<Course> courses){
        mAssociatedCourses=courses;
        notifyDataSetChanged();
    }

}
