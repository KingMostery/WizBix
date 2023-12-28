package Inventario

import Ventas.VentasActivity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mostery.wizbix.R

class InventarioActivity : AppCompatActivity() {

    private lateinit var categorias: MutableList<String>
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        // Obtener la lista de categorías de ConfiguracionActivity (puedes usar SharedPreferences o intent extras)
        // Por ejemplo, si utilizas SharedPreferences:
        val sharedPreferences = getSharedPreferences("Categorias", MODE_PRIVATE)
        categorias =
            sharedPreferences.getStringSet("categorias", HashSet<String>())?.toMutableList()
                ?: mutableListOf()

        // Inicializar Firebase
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference

    }

    fun mostrarFormularioCrearProducto(view: View) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_crear_producto, null)

        val nombreEditText = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val descripcionEditText = dialogView.findViewById<EditText>(R.id.editTextDescripcion)
        val precioEditText = dialogView.findViewById<EditText>(R.id.editTextPrecio)
        val codigoAlternoEditText = dialogView.findViewById<EditText>(R.id.editTextCodigoAlterno)
        val categoriaSpinner = dialogView.findViewById<Spinner>(R.id.spinnerCategoria)


        // Configurar el Spinner con las categorías disponibles
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoriaSpinner.adapter = adapter

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Crear Producto")
            .setPositiveButton("Guardar") { dialog, _ ->
                // Guardar el producto en la base de datos aquí
                val nombre = nombreEditText.text.toString()
                val descripcion = descripcionEditText.text.toString()
                val precio = precioEditText.text.toString()
                val codigoAlterno = codigoAlternoEditText.text.toString()
                val cantidad = "0"
                val categoriaSeleccionada = categoriaSpinner.selectedItem.toString()


                if (nombre.isNotEmpty() && precio.isNotEmpty() &&
                    codigoAlterno.isNotEmpty()
                ) {
                    // Aquí puedes guardar los datos en Firebase
                    guardarProductoEnFirebase(
                        nombre,
                        descripcion,
                        precio,
                        cantidad,
                        codigoAlterno,
                        categoriaSeleccionada
                    )
                    dialog.dismiss()
                } else {
                    // Mostrar un mensaje de error si algún campo está vacío
                    mostrarMensaje("Por favor, complete todos los campos")
                }

            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun guardarProductoEnFirebase(
        nombre: String,
        descripcion: String,
        precio: String,
        cantidad: String,
        codigoAlterno: String,
        categoria: String
    ) {
        val productosReference =
            databaseReference.child("productos") // Nombre del nodo "productos" en tu base de datos
        val precioNumerico = precio.toDoubleOrNull() ?: 0.0
        val cantidadNumerico = cantidad.toInt()

        val nuevoProducto = HashMap<String, Any>()
        nuevoProducto["nombre"] = nombre
        nuevoProducto["descripcion"] = descripcion
        nuevoProducto["precio"] = precioNumerico
        nuevoProducto["cantidad"] = cantidadNumerico
        nuevoProducto["codigoAlterno"] = codigoAlterno
        nuevoProducto["categoria"] = categoria

        productosReference.push().setValue(nuevoProducto)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mostrarMensaje("Producto guardado correctamente")
                } else {
                    mostrarMensaje("Error al guardar el producto")
                    // Muestra el mensaje de error específico si lo proporciona Firebase
                    task.exception?.message?.let { mostrarMensaje(it) }
                }
            }

    }
   fun onClickverProductos(view: View){
       val intent=Intent(this,ProductosActivity::class.java)
       startActivity(intent)
   }

}//Fin codigo
