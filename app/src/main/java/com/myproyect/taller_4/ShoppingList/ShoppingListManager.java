package com.myproyect.taller_4.ShoppingList;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myproyect.taller_4.Fragments.Item;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListManager {

    private static final String PREFS_NAME = "shopping_list_prefs"; // Nombre del archivo de preferencias
    private static final String KEY_SHOPPING_LIST = "shopping_list"; // Clave para almacenar la lista

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public ShoppingListManager(Context context) {
        // Inicializar SharedPreferences y Gson
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    /**
     * Obtiene la lista de la compra almacenada en SharedPreferences.
     *
     * @return Una lista de objetos de tipo Item.
     */
    public List<Item> getShoppingList() {
        String json = sharedPreferences.getString(KEY_SHOPPING_LIST, null);
        if (json == null) {
            // Si no hay datos almacenados, devuelve una lista vacía
            return new ArrayList<>();
        }
        // Deserializa el JSON a una lista de objetos Item
        Type type = new TypeToken<List<Item>>() {}.getType();
        return gson.fromJson(json, type);
    }

    /**
     * Guarda la lista de la compra en SharedPreferences.
     *
     * @param shoppingList La lista de la compra a guardar.
     */
    public void saveShoppingList(List<Item> shoppingList) {
        // Serializa la lista a JSON
        String json = gson.toJson(shoppingList);
        sharedPreferences.edit().putString(KEY_SHOPPING_LIST, json).apply();
    }
}
