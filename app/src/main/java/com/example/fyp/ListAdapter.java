package com.example.fyp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListVH> {

        List<String> resultList;
private ListAdapter adapter;

public ListAdapter(ArrayList<Result> resultList) {
        this.adapter = adapter;
        this.resultList = this.resultList;
        }



@NonNull
@Override
public ListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.activity_result_data, parent, false);
        return new ListVH(view).linkAdapter(this);
        }

@Override
public void onBindViewHolder(@NonNull ListVH holder, int position) {
        holder.textView.setText((CharSequence) resultList.get(position));

        }

@Override
public int getItemCount() {
        if(resultList ==null) return 0;
        return resultList.size();
        }

public void retrieveTasks_firebase() {
        DatabaseReference fireDBTask = FirebaseDatabase.getInstance().getReference("Results");

        fireDBTask.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot taskSnapShot : snapshot.getChildren()) {
        String resultObj = taskSnapShot.getValue(String.class);
        resultList.add(resultObj);
        adapter.notifyItemInserted(resultList.size());
        }
        }

@Override
public void onCancelled(@NonNull DatabaseError error) {
        Log.w("DBError", "Cancel Access DB");
        }
        });
        }

private void notifyItemInserted() {
        adapter.notifyItemInserted();
        }

public void addItemtoEnd(String results1){
        resultList.add(results1);
        notifyItemInserted();

        }

public void remove(int position){
        resultList.remove(position);
        notifyItemRemoved(position);
        }

public void update(String newResult, int position){
        resultList.set(position, newResult);
        notifyItemChanged(position);
        }
        }


class ListVH extends RecyclerView.ViewHolder {

    TextView textView;
    private ListAdapter adapter;
    //TextView tags, descs, dates, menu;


    public ListVH(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.text);
        itemView.findViewById(R.id.delete).setOnClickListener(view -> {
            adapter.resultList.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());

        });
    }

    public ListVH linkAdapter(ListAdapter adapter) {
        this.adapter = adapter;
        return this;
    }
}

