# Taller 4

## Descripción

Esta aplicación de Android permite gestionar una lista de la compra con funcionalidades avanzadas como:

- Ver y editar artículos en una lista.
- Detallar cada artículo con atributos como cantidad, marca y precio estimado.
- Borrar toda la lista agitando el dispositivo.
- Sincronización con un widget que muestra la lista de la compra.

## Características

1. **Pantalla de Inicio**
   - Muestra un saludo personalizado según la hora del día.
   - Botón para ir a la pantalla principal.

2. **Pantalla Principal**
   - Fragmento con una lista de la compra.
   - Fragmento para ver detalles del artículo seleccionado.
   - Funcionalidad para añadir y eliminar artículos.

3. **Widget**
   - Muestra la lista de la compra.
   - Botón para actualizar la información.

4. **Sensores**
   - Detecta movimientos bruscos (agitar el dispositivo) para borrar toda la lista.

## Estructura de Clases

### MainActivity
- Muestra un saludo personalizado en la pantalla de inicio.
- Navega a la pantalla principal.

### MainScreenActivity
- Administra fragmentos (lista y detalles).
- Maneja el sensor de acelerómetro para detectar movimientos bruscos.
- Borra la lista de la compra al agitar el dispositivo.

### ItemListFragment
- Muestra la lista de la compra.
- Permite añadir y eliminar artículos.

### ItemDetailFragment
- Muestra los detalles de un artículo seleccionado.

### ShoppingListManager
- Gestiona la persistencia de datos mediante `SharedPreferences`.
- Serializa y deserializa la lista de la compra.

### ShoppingListWidget
- Lee la lista desde `ShoppingListManager`.
- Actualiza la información mostrada en el widget.

## Cómo Probar

1. **Simular Acelerómetro (emulador):**
   - Usa los controles de `Virtual Sensors` en Android Studio.
   - IMPORTANTE: Usar función MOVE y no funcion rotate. 

2. **Probar en un dispositivo físico:**
   - Agita el dispositivo para borrar la lista.

