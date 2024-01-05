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
import com.example.c196.entities.Term;


import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermListViewHolder> {

    public TermListAdapter(Context context, LayoutInflater mInflater) {
        this.context = context;
        this.mInflater = mInflater;
    }

    class TermListViewHolder extends RecyclerView.ViewHolder {

        private final TextView TermListItemView;

        private TermListViewHolder(View itemView) {
            super(itemView);
            TermListItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position=getAbsoluteAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, DetailedTermView.class);
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termTitle", current.getTermTitle());
                    intent.putExtra("startDate", current.getTermStartDate());
                    intent.putExtra("endDate", current.getTermEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;
    public TermListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public TermListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermListViewHolder holder, int position) {
        if (mTerms != null) {
            Term current = mTerms.get(position);
            String termTitle = current.getTermTitle();
            holder.TermListItemView.setText(termTitle);
        }
        else{
            holder.TermListItemView.setText("No Term Title Available");
        }
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }

}