package Inventario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mostery.wizbix.R

class ProductosActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        databaseReference = FirebaseDatabase.getInstance().getReference("productos")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProductos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtener datos de Firebase
        obtenerProductosDesdeFirebase(recyclerView)
    }

    private fun obtenerProductosDesdeFirebase(recyclerView: RecyclerView) {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listaProductos: MutableList<Producto> = mutableListOf()

                for (productoSnapshot in dataSnapshot.children) {
                    val nombre = productoSnapshot.child("nombre").getValue(String::class.java)
                    val precio = productoSnapshot.child("precio").getValue(Double::class.java)
                    val cantidad = productoSnapshot.child("cantidad").getValue(Int::class.java)

                    if (nombre != null && precio != null && cantidad != null) {
                        val producto = Producto(nombre, precio, cantidad)
                        listaProductos.add(producto)
                    }
                }

                mostrarProductosEnRecyclerView(recyclerView, listaProductos)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores al obtener datos de Firebase
            }
        })
    }

    private fun mostrarProductosEnRecyclerView(
        recyclerView: RecyclerView,
        listaProductos: List<Producto>

    ) {
        val adaptador = ProductoAdapter(listaProductos)
        recyclerView.adapter = adaptador
    }

}
