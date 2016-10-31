package com.siziksu.va.ui.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siziksu.va.R;
import com.siziksu.va.common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.LocalViewHolder> {

    private final List<String> strings;
    private final Context context;
    private final ClickListener listener;

    public ProductsAdapter(Context context, ClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.strings = new ArrayList<>();
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
        return new LocalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalViewHolder holder, int position) {
        holder.itemText.setText(strings.get(position));
        holder.itemTextDescription.setText(R.string.product_description);
        holder.itemTextNumber.setText(String.format(Locale.getDefault(), Constants.DOLLAR, String.valueOf(position + 1)));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public void showProducts(List<String> products) {
        strings.clear();
        strings.addAll(products);
        notifyDataSetChanged();
    }

    final class LocalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout productItemRow;
        TextView itemText;
        TextView itemTextNumber;
        TextView itemTextDescription;

        LocalViewHolder(View view) {
            super(view);
            productItemRow = (RelativeLayout) view.findViewById(R.id.productItemRow);
            itemText = (TextView) view.findViewById(R.id.productItemNameTextView);
            itemTextDescription = (TextView) view.findViewById(R.id.productItemDescriptionTextView);
            itemTextNumber = (TextView) view.findViewById(R.id.productItemPriceTextView);
            productItemRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onClick(view, getAdapterPosition());
            }
        }
    }

    public interface ClickListener {

        void onClick(View view, int position);
    }
}