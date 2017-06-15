package com.programs.lala.myschool.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.programs.lala.myschool.R;
import com.programs.lala.myschool.interfaces.Communication;
import com.programs.lala.myschool.modeld.AccountModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by melde on 6/6/2017.
 */

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {
    private Context context;
Communication con;
List<AccountModel>accountList;





    public AccountsAdapter( Context c,List<AccountModel> l ) {

        this.context=c;
        this.accountList=l;

    }

    @Override
    public AccountsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account,parent,false);
        AccountsAdapter.ViewHolder holder = new AccountsAdapter.ViewHolder(row);
        return holder;

    }

    @Override
    public void onBindViewHolder(AccountsAdapter.ViewHolder holder, int position) {
holder.accountName.setText(accountList.get(position).getName());
        holder.accountJob.setText(accountList.get(position).getJob());
       try {
           Picasso.with(context)
                   .load(String.valueOf(context.getString(R.string.BAS_URL) + accountList.get(position).getImage())).fit()
                   .into(holder.personImage);
       }
       catch (Exception E){}
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView accountName;

        TextView accountJob;
        ImageView personImage;


        public ViewHolder(View itemView) {
            super(itemView);
            personImage= (ImageView) itemView.findViewById(R.id.personImage);

            accountName= (TextView) itemView.findViewById(R.id.accountName);
            accountJob= (TextView) itemView.findViewById(R.id.accountJob);
            con= (Communication) context;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
con.onClickAccount(accountList.get(adapterPosition));

        }
    }
}
