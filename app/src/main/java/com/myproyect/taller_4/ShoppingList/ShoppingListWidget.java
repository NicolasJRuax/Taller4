package com.myproyect.taller_4.ShoppingList;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.myproyect.taller_4.Fragments.Item;
import com.myproyect.taller_4.R;

import java.util.List;

public class ShoppingListWidget extends AppWidgetProvider {

    private static final String ACTION_UPDATE_WIDGET = "com.myproyect.taller_4.ACTION_UPDATE_WIDGET";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        ShoppingListManager shoppingListManager = new ShoppingListManager(context);
        List<Item> shoppingList = shoppingListManager.getShoppingList();

        // Construir el texto de la lista de la compra
        StringBuilder shoppingListText = new StringBuilder();
        for (Item item : shoppingList) {
            shoppingListText.append("- ").append(item.getName()).append("\n");
        }

        // Configurar las vistas del widget
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.widget_list_text, shoppingListText.toString().isEmpty() ? "Lista vacía" : shoppingListText.toString());

        // Configurar el botón de actualizar
        Intent intentUpdate = new Intent(context, ShoppingListWidget.class);
        intentUpdate.setAction(ACTION_UPDATE_WIDGET);
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context, 0, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_update_button, pendingUpdate);

        // Actualizar el widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_UPDATE_WIDGET.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ShoppingListWidget.class));
            for (int appWidgetId : appWidgetIds) {
                updateWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }
}
