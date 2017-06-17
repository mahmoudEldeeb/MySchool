package com.programs.lala.myschool.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.programs.lala.myschool.R;
import com.programs.lala.myschool.modeld.ChatModel;

import java.util.List;

/**
 * Created by melde on 6/5/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<ChatModel> chatModelList;

    public ChatAdapter(Context c, List<ChatModel> l) {

        this.context = c;
        this.chatModelList = l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = null;
        if (viewType == 0) {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_message, parent, false);
            return new ViewHolderSend(row);

        } else {

            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recive_message, parent, false);
            return new ViewHolderRecive(row);
        }
        /*int i=0;
        while (i<chatModelList.size()) {
            if (chatModelList.get(viewType).getType() == 1) {

                row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_message, parent, false);

            } else {
                row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recive_message, parent, false);

            }
            i++;
        }*/

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final int itemType = getItemViewType(position);
        if (itemType == 0) {
            ViewHolderSend viewholder = (ViewHolderSend) holder;
            viewholder.sendMessage.setText(chatModelList.get(position).getMessage());
        } else {
            ViewHolderRecive viewholder = (ViewHolderRecive) holder;
            viewholder.recieveMessage.setText(chatModelList.get(position).getMessage());


        }
    }

    /* @Override
     public void onBindViewHolder(ViewHolder holder, int position) {
    if(chatModelList.get(position).getType()==1){
        Log.v("bb",chatModelList.get(position).getMessage());
        holder.sendMessage.setText(chatModelList.get(position).getMessage());
    }
 else{

        holder.recieveMessage.setText(chatModelList.get(position).getMessage());}
     }
 */
    @Override
    public int getItemCount() {
        return chatModelList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return chatModelList.get(position).getType();
    }


    class ViewHolderSend extends RecyclerView.ViewHolder {

        TextView sendMessage;

        public ViewHolderSend(View itemView) {
            super(itemView);
            sendMessage = (TextView) itemView.findViewById(R.id.sendMessage1);


        }

    }

    class ViewHolderRecive extends RecyclerView.ViewHolder {

        TextView recieveMessage;

        public ViewHolderRecive(View itemView) {
            super(itemView);
            recieveMessage = (TextView) itemView.findViewById(R.id.recieveMessage);


        }

    }
}
