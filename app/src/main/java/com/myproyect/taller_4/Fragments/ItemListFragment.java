package com.myproyect.taller_4.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.myproyect.taller_4.R;
import com.myproyect.taller_4.ShoppingList.ShoppingListManager;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment {

    private OnItemSelectedListener callback;
    private ShoppingListManager shoppingListManager;
    private List<Item> shoppingList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemNames;

    public interface OnItemSelectedListener {
        void onItemSelected(@NonNull Item item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            callback = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " debe implementar OnItemSelectedListener");
        }
        shoppingListManager = new ShoppingListManager(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        ListView listView = view.findViewById(R.id.item_list);
        Button addButton = view.findViewById(R.id.add_button);
        EditText itemNameInput = view.findViewById(R.id.item_name_input);
        EditText itemQuantityInput = view.findViewById(R.id.item_quantity_input);
        EditText itemBrandInput = view.findViewById(R.id.item_brand_input);
        EditText itemPriceInput = view.findViewById(R.id.item_price_input);

        // Cargar la lista de la compra desde SharedPreferences
        shoppingList = shoppingListManager.getShoppingList();

        // Crear una lista de nombres de artículos
        itemNames = new ArrayList<>();
        for (Item item : shoppingList) {
            itemNames.add(item.getName());
        }

        // Configurar el adaptador
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(adapter);

        // Manejar la selección de un artículo
        listView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            Item selectedItem = shoppingList.get(position);
            callback.onItemSelected(selectedItem);
        });

        // Manejar la eliminación de un artículo al mantener pulsado
        listView.setOnItemLongClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            shoppingList.remove(position);
            itemNames.remove(position);
            adapter.notifyDataSetChanged();
            saveShoppingList();
            Toast.makeText(requireContext(), "Artículo eliminado", Toast.LENGTH_SHORT).show();
            return true;
        });

        // Añadir un nuevo artículo
        addButton.setOnClickListener(v -> {
            String name = itemNameInput.getText().toString();
            String quantityText = itemQuantityInput.getText().toString();
            String brand = itemBrandInput.getText().toString();
            String priceText = itemPriceInput.getText().toString();

            if (name.isEmpty() || quantityText.isEmpty() || brand.isEmpty() || priceText.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);
            Item newItem = new Item(name, quantity, brand, price);

            shoppingList.add(newItem);
            itemNames.add(newItem.getName());
            adapter.notifyDataSetChanged();

            // Guardar la lista actualizada
            saveShoppingList();

            // Limpiar los campos
            itemNameInput.setText("");
            itemQuantityInput.setText("");
            itemBrandInput.setText("");
            itemPriceInput.setText("");

            Toast.makeText(requireContext(), "Artículo añadido a la lista", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    public void clearList() {
        shoppingList.clear();
        itemNames.clear();
        adapter.notifyDataSetChanged();
    }


    /**
     * Guarda la lista de la compra en SharedPreferences.
     */
    private void saveShoppingList() {
        shoppingListManager.saveShoppingList(shoppingList);
    }
}
