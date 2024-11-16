package com.myproyect.taller_4.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myproyect.taller_4.R;

public class ItemDetailFragment extends Fragment {

    private static final String ARG_ITEM_NAME = "item_name";
    private static final String ARG_ITEM_QUANTITY = "item_quantity";
    private static final String ARG_ITEM_BRAND = "item_brand";
    private static final String ARG_ITEM_PRICE = "item_price";

    public static ItemDetailFragment newInstance(Item item) {
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_NAME, item.getName());
        args.putInt(ARG_ITEM_QUANTITY, item.getQuantity());
        args.putString(ARG_ITEM_BRAND, item.getBrand());
        args.putDouble(ARG_ITEM_PRICE, item.getEstimatedPrice());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

        TextView nameTextView = view.findViewById(R.id.item_name_text);
        TextView quantityTextView = view.findViewById(R.id.item_quantity_text);
        TextView brandTextView = view.findViewById(R.id.item_brand_text);
        TextView priceTextView = view.findViewById(R.id.item_price_text);

        if (getArguments() != null) {
            nameTextView.setText(getArguments().getString(ARG_ITEM_NAME));
            quantityTextView.setText("Cantidad: " + getArguments().getInt(ARG_ITEM_QUANTITY));
            brandTextView.setText("Marca: " + getArguments().getString(ARG_ITEM_BRAND));
            priceTextView.setText("Precio Estimado: $" + getArguments().getDouble(ARG_ITEM_PRICE));
        }

        return view;
    }
}
