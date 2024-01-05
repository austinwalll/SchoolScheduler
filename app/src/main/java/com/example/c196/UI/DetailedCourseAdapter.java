package com.example.c196.UI;

import android.content.Intent;
import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.entities.Assessment;



import java.util.List;

public class DetailedCourseAdapter extends RecyclerView.Adapter<DetailedCourseAdapter.DetailedCourseViewHolder> {

    public DetailedCourseAdapter(Context context, LayoutInflater mInflater) {
        this.context = context;
        this.mInflater = mInflater;
    }

    class DetailedCourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView DetailedCourseItemView;

        private DetailedCourseViewHolder(View itemView) {
            super(itemView);
            DetailedCourseItemView = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position=getAbsoluteAdapterPosition();
                    final Assessment current = mAssociatedAssessment.get(position);
                    Intent intent = new Intent(context, DetailedAssessmentView.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("assessmentTitle", current.getAssessmentTitle());
                    intent.putExtra("course", current.getCourse());
                    intent.putExtra("dueDate", current.getDueDate());
                    intent.putExtra("assessmentType", current.isAssessmentType());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssociatedAssessment;
    private final Context context;
    private final LayoutInflater mInflater;
    public DetailedCourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public DetailedCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new DetailedCourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailedCourseViewHolder holder, int position) {
        if (mAssociatedAssessment != null) {
            Assessment current = mAssociatedAssessment.get(position);
            String assessmentTitle = current.getAssessmentTitle();
            holder.DetailedCourseItemView.setText(assessmentTitle);
        }
        else{
            holder.DetailedCourseItemView.setText("No Assessment Title Available");
        }
    }

    @Override
    public int getItemCount() {
        return mAssociatedAssessment.size();
    }

    public void setAssociatedAssessment(List<Assessment> assessments){
        mAssociatedAssessment=assessments;
        notifyDataSetChanged();
    }

}