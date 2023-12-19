package Configuracion

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mostery.wizbix.R


class ConfiguracionActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val listaCategorias: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        sharedPreferences = getSharedPreferences("Categorias", MODE_PRIVATE)
        // Agregar categorías predeterminadas
        listaCategorias.add("Electrónica")
        listaCategorias.add("Ropa")
        listaCategorias.add("Hogar y jardín")
        listaCategorias.add("Alimentos y bebidas")
        listaCategorias.add("Juguetes y juegos")
        listaCategorias.add("Salud y belleza")
        listaCategorias.add("Libros y revistas")
        cargarCategoriasGuardadas()
    }

    fun mostrarDialogoCrearCategoria(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Crear Categoría")

        val input = EditText(this)
        input.hint = "Nombre de la categoría"
        builder.setView(input)

        builder.setPositiveButton("Guardar") { dialog: DialogInterface, which: Int ->
            val nombreCategoria = input.text.toString()
            guardarCategoria(nombreCategoria)
        }

        builder.setNegativeButton("Cancelar") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun mostrarDialogoVerCategorias(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Categorías creadas")

        val categoriasView = LinearLayout(this) // Crear un nuevo LinearLayout
        categoriasView.orientation = LinearLayout.VERTICAL // Establecer orientación vertical

        for (i in listaCategorias.indices) {
            val itemView = layoutInflater.inflate(R.layout.item_categoria, null)
            val textCategoria = itemView.findViewById<TextView>(R.id.textCategoria)
            val btnBorrarCategoria = itemView.findViewById<ImageButton>(R.id.btnBorrarCategoria)

            textCategoria.text = listaCategorias[i]
            btnBorrarCategoria.setOnClickListener {
                borrarCategoria(i)
                categoriasView.removeView(itemView) // Remover el elemento de la vista
            }

            categoriasView.addView(itemView)
        }

        builder.setView(categoriasView)
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }



    private fun guardarCategoria(nombreCategoria: String) {
        listaCategorias.add(nombreCategoria)
        guardarCategoriasEnSharedPreferences()
        mostrarMensaje("Categoría guardada: $nombreCategoria")
    }

    private fun cargarCategoriasGuardadas() {
        val categoriasGuardadas = sharedPreferences.getStringSet("categorias", null)
        categoriasGuardadas?.let {
            listaCategorias.addAll(it)
        }
    }

    private fun guardarCategoriasEnSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putStringSet("categorias", listaCategorias.toSet())
        editor.apply()
    }

    private fun borrarCategoria(position: Int) {
        listaCategorias.removeAt(position)
        guardarCategoriasEnSharedPreferences()
        mostrarMensaje("Categoría eliminada")
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}



