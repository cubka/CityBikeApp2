package com.example.ivana.citybikeapp2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ivana.citybikeapp2.BikeCompanyMap;
import com.example.ivana.citybikeapp2.R;
import com.example.ivana.citybikeapp2.models.Networks;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable {

  private   Context context;
  private   ArrayList<Networks> networks ;
   private ArrayList<Networks> filterNetworks;

   public void setNetworksList (Context context, final ArrayList<Networks> networks) {

       this.context = context;
       if (this.networks == null) {
           this.networks = networks;
           this.filterNetworks = networks;
           notifyItemChanged(0,filterNetworks.size());
       }
       else {
           final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
               @Override
               public int getOldListSize() {
                   return ListAdapter.this.networks.size();
               }

               @Override
               public int getNewListSize() {
                   return networks.size();
               }

               @Override
               public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                   return ListAdapter.this.networks.get(oldItemPosition).getName() == networks.get(newItemPosition).getName();
               }

               @Override
               public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                   Networks newNetworks = ListAdapter.this.networks.get(oldItemPosition);
                   Networks oldNetworks = networks.get(newItemPosition);

                   return newNetworks.getName() == oldNetworks.getName();
               }
           });
       }
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bike_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position ) {

        final Networks networksModel_ = filterNetworks.get(position);
        holder.companyName.setText("Company name:" + networksModel_.getName());
        holder.citY.setText("City:" + networksModel_.getCity());
        holder.CountryCode.setText("Country Code: " + networksModel_.getCountry());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BikeCompanyMap.class);
                i.putExtra("NAME",networksModel_);
                i.putExtra("POS",position);
                context.startActivity(i);
            }
        });


    }

    @Override
        public int getItemCount() {

        if (networks != null)
            return filterNetworks.size();
        else
            return  0;

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filterNetworks = networks;
                } else {
                    ArrayList<Networks> filteredList = new ArrayList<>();
                    for (Networks row : networks) {

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);

                        }
                    }
                    filterNetworks = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterNetworks;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filterNetworks = (ArrayList<Networks>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.company_name)
        TextView companyName;
        @BindView(R.id.city)
        TextView citY;
        @BindView(R.id.country_code)
        TextView CountryCode;
        @BindView(R.id.line1)
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
