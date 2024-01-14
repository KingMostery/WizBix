package Inventario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mostery.wizbix.R

class ProductoAdapter(private val listaProductos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
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
            val nombreTextView = itemView.findViewById<TextView>(R.id.textViewNombreProducto)
            val precioTextView = itemView.findViewById<TextView>(R.id.textViewPrecioProducto)
            val cantidadTextView = itemView.findViewById<TextView>(R.id.textViewCantidadProducto)

            nombreTextView.text = "Nombre: ${producto.nombre}"
            precioTextView.text = "Precio: ${producto.precio}"
            cantidadTextView.text = "Cantidad: ${producto.cantidad}"
            //nombreTextView.text = producto.nombre
            //precioTextView.text = "Precio: ${producto.precio}"
        }
    }
}

