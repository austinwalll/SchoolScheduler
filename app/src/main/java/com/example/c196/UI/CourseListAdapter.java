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

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder> {

    public CourseListAdapter(Context context, LayoutInflater mInflater) {
        this.context = context;
        this.mInflater = mInflater;
    }

    class CourseListViewHolder extends RecyclerView.ViewHolder {

        private final TextView CourseListItemView;

        private CourseListViewHolder(View itemView) {
            super(itemView);
            CourseListItemView = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position=getAbsoluteAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, DetailedCourseView.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getStartDate());
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
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.class_list_item, parent, false);
        return new CourseListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListViewHolder holder, int position) {
        if (mCourses != null && !mCourses.isEmpty()) {
            Course current = mCourses.get(position);
            String courseName = current.getCourseName();
            holder.CourseListItemView.setText(courseName);
        }
        else{
            holder.CourseListItemView.setText("No Course Name Available");
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCourses(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }

}
