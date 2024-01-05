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
import com.example.c196.entities.Assessment;


import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentListViewHolder> {

    public AssessmentListAdapter(Context context, LayoutInflater mInflater) {
        this.context = context;
        this.mInflater = mInflater;
    }

    class AssessmentListViewHolder extends RecyclerView.ViewHolder {

        private final TextView AssessmentListItemView;

        private AssessmentListViewHolder(View itemView) {
            super(itemView);
            AssessmentListItemView = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position=getAbsoluteAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, DetailedAssessmentView.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("assessmentTitle", current.getAssessmentTitle());
                    intent.putExtra("assessmentType", current.isAssessmentType());
                    intent.putExtra("course", current.getCourse());
                    intent.putExtra("dueDate", current.getDueDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public AssessmentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String assessmentTitle = current.getAssessmentTitle();
            holder.AssessmentListItemView.setText(assessmentTitle);
        }
        else{
            holder.AssessmentListItemView.setText("No Assessment Name Available");
        }
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
    }

}
