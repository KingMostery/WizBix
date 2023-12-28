package Inventario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(private val listaProductos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ProductoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(producto: Producto) {
            val nombreTextView = itemView.findViewById<TextView>(android.R.id.text1)
            val precioTextView = itemView.findViewById<TextView>(android.R.id.text2)

            nombreTextView.text = producto.nombre
            precioTextView.text = "Precio: ${producto.precio}"
        }
    }
}

