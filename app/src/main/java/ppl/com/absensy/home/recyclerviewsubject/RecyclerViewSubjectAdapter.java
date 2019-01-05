package ppl.com.absensy.home.recyclerviewsubject;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ppl.com.absensy.R;
import ppl.com.absensy.base.RecyclerViewHelper;
import ppl.com.absensy.model.Subject;

public class RecyclerViewSubjectAdapter
        extends RecyclerView.Adapter<RecyclerViewSubjectAdapter.RecyclerViewSubjectViewHolder>
        implements RecyclerViewHelper<Subject> {

    private List<Subject> subjectList = new ArrayList<>();
    private Listener listener;

    public RecyclerViewSubjectAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subject, viewGroup, false);
        return new RecyclerViewSubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSubjectViewHolder recyclerViewSubjectViewHolder, int i) {
        Subject subject = subjectList.get(i);

        recyclerViewSubjectViewHolder.bind(subject);

        recyclerViewSubjectViewHolder.tvSubjectName.setText(subject.getName());
        recyclerViewSubjectViewHolder.tvSubjectAbsenceAmount.setText(String.format("Kosong : %s", String.valueOf(subject.getAbsenceAmount())));
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    @Override
    public void updateData(List<Subject> data) {
        this.subjectList.clear();
        this.subjectList.addAll(data);
        notifyDataSetChanged();
    }

    public interface Listener {
        void OnItemClick(Subject subject);
    }

    class RecyclerViewSubjectViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout parentItem;
        private TextView tvSubjectName;
        private TextView tvSubjectAbsenceAmount;

        public RecyclerViewSubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            parentItem = itemView.findViewById(R.id.parentItem);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvSubjectAbsenceAmount = itemView.findViewById(R.id.tvSubjectAbsenceAmount);
        }

        void bind(final Subject subject) {
            parentItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(subject);
                }
            });
        }
    }
}
