package com.programs.lala.myschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programs.lala.myschool.R;
import com.programs.lala.myschool.interfaces.Communication;
import com.programs.lala.myschool.modeld.PostModel;

import java.util.List;

/**
 * Created by melde on 6/6/2017.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<PostModel> list;

    private AdapterOnClickHandler mClickHandler;

    public interface AdapterOnClickHandler {
        void onClick(int i);
    }


    public PostsAdapter(Context c, List<PostModel> postsList) {

        this.context = c;
        this.list = postsList;
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        PostsAdapter.ViewHolder holder = new PostsAdapter.ViewHolder(row);
        return holder;

    }

    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitile());
        holder.description.setText(list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView description;
        Communication con;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            con = (Communication) context;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            PostModel model = list.get(adapterPosition);
            con.onClickPost(model);
        }
    }
}