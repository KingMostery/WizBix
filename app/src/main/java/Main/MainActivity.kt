package Main

import Configuracion.ConfiguracionActivity
import Inventario.InventarioActivity
import Ventas.VentasActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mostery.wizbix.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickVentas(view: View) {
        val intent = Intent(this, VentasActivity::class.java)
        startActivity(intent)
    }

    fun onClickInventario(view: View) {
        val intent = Intent(this, InventarioActivity::class.java)
        startActivity(intent)
    }

    fun onClickConfiguracion(view: View) {
        val intent = Intent(this, ConfiguracionActivity::class.java)
        startActivity(intent)
    }
}
