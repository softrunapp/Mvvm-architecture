package com.softrunapps.mvvmarchitecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends ListAdapter<User, UserAdapter.ViewHolder> {
    private OnItemClickListener listener;

    protected UserAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUsernsme().equals(newItem.getUsernsme()) &&
                    oldItem.getEmail().equals(newItem.getEmail());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = getItem(position);
        holder.id.setText(String.valueOf(user.getId()));
        holder.username.setText(user.getUsernsme());
        holder.email.setText(user.getEmail());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id, username, email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.text_view_id);
            username = itemView.findViewById(R.id.text_view_username);
            email = itemView.findViewById(R.id.text_view_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
