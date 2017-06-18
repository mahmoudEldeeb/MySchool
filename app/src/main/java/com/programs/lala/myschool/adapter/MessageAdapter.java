package com.programs.lala.myschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.programs.lala.myschool.R;
import com.programs.lala.myschool.interfaces.Communication;
import com.programs.lala.myschool.modeld.MessageModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by melde on 2/19/2017.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;

    Communication con;

    List<MessageModel> messageModelList;


    public MessageAdapter(Context c, List<MessageModel> list) {

        this.context = c;
        this.messageModelList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(row);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.sendeName.setText(messageModelList.get(position).getName());
        holder.lastMessage.setText(messageModelList.get(position).getMessge());
        try {
            Picasso.with(context)
                    .load(String.valueOf(context.getString(R.string.BAS_URL) + messageModelList.get(position).getImage())).fit()
                    .into(holder.messageImge);
        } catch (Exception E) {
        }
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView sendeName;

        TextView lastMessage;
        ImageView messageImge;

        public ViewHolder(View itemView) {
            super(itemView);


            messageImge = (ImageView) itemView.findViewById(R.id.messageImge);

            lastMessage = (TextView) itemView.findViewById(R.id.lastMessage);
            sendeName = (TextView) itemView.findViewById(R.id.sendeName);
            con = (Communication) context;
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            con.onClickMessage(messageModelList.get(adapterPosition));

        }
    }
}