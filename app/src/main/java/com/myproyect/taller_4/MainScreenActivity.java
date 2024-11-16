package com.myproyect.taller_4;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.myproyect.taller_4.Fragments.Item;
import com.myproyect.taller_4.Fragments.ItemDetailFragment;
import com.myproyect.taller_4.Fragments.ItemListFragment;
import com.myproyect.taller_4.ShoppingList.ShoppingListManager;
import com.myproyect.taller_4.ShoppingList.ShoppingListWidget;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity implements ItemListFragment.OnItemSelectedListener, SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShoppingListManager shoppingListManager;
    private static final float SHAKE_THRESHOLD = 10.0f; // Más sensible
    private long lastShakeTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Inicializar el gestor de la lista de la compra
        shoppingListManager = new ShoppingListManager(this);

        // Cargar el fragmento de lista
        if (savedInstanceState == null) {
            ItemListFragment listFragment = new ItemListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, listFragment)
                    .commit();
        }

        // Configurar el sensor de acelerómetro
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onItemSelected(Item item) {
        // Mostrar los detalles del artículo seleccionado
        ItemDetailFragment detailFragment = ItemDetailFragment.newInstance(item);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calcular la magnitud del movimiento
            float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

            // Agregar logs para verificar valores del sensor
           
            if (magnitude > SHAKE_THRESHOLD) {
                long currentTime = System.currentTimeMillis();

                // Prevenir detecciones repetidas
                if ((currentTime - lastShakeTime) > 1000) {
                    lastShakeTime = currentTime;
                    Log.d("ShakeDetector", "Movimiento detectado. Mostrando diálogo de confirmación.");
                    showDeleteConfirmationDialog();
                }
            }
        }


}

    private void showDeleteConfirmationDialog() {
        // Mostrar un diálogo de confirmación
        new AlertDialog.Builder(this)
                .setTitle("Eliminar todos los artículos")
                .setMessage("¿Estás seguro de que deseas borrar toda la lista de la compra?")
                .setPositiveButton("Sí", (dialog, which) -> clearShoppingList())
                .setNegativeButton("No", null)
                .show();
    }

    private void clearShoppingList() {
        // Limpiar la lista de la compra
        shoppingListManager.saveShoppingList(new ArrayList<>());

        // Notificar al fragmento que actualice la lista
        ItemListFragment listFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (listFragment != null) {
            listFragment.clearList();
        }

        // Mostrar un mensaje al usuario
        Toast.makeText(this, "Lista de la compra eliminada", Toast.LENGTH_SHORT).show();

        // Notificar al widget para que se actualice
        notifyWidgetUpdate();
    }

    private void notifyWidgetUpdate() {
        Intent intent = new Intent(this, ShoppingListWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(new ComponentName(this, ShoppingListWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se necesita manejar cambios de precisión
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desregistrar el sensor para ahorrar batería
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}
