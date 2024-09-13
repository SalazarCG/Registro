package CS.Registro.controlador;

import CS.Registro.excepciones.Excepcion;
import CS.Registro.modelo.Producto;
import CS.Registro.servicio.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

//localhost:8080/registro-app
@RestController
@RequestMapping("registro-app")//ContexPath
@CrossOrigin(value = "http://localhost:4200") //Para hacer peticiones desde Angular
public class ProductoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    @Autowired
    private ProductoServicio productoServicio;

    //Ruta final
    //http://localhost:8080/registro-app/productos
    @GetMapping("/productos")//Uri
    public List<Producto> obtenerProductos(){
        List<Producto> productos = this.productoServicio.listarProductos();
        logger.info("Productos obtenidos");
        productos.forEach((producto -> logger.info(producto.toString())));
        return productos;
    }

    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto){
        logger.info("Producto agregar: " + producto);
        return this.productoServicio.guardarProducto(producto);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoId(
            @PathVariable int id
    ){
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if (producto != null)
            return ResponseEntity.ok(producto);
        else
            throw new Excepcion("No se encontro el Id " + producto);

    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable int id,
            @RequestBody Producto productoRecibido
    ){
        Producto producto = this.productoServicio.buscarProductoPorId(id); //buscamos en la base de datos el id del producto
        if (producto == null)
            throw new Excepcion("No se encontro el id " + id);
        producto.setDescripcion(productoRecibido.getDescripcion());
        producto.setPrecio(productoRecibido.getPrecio());
        producto.setExistencia(productoRecibido.getExistencia());
        this.productoServicio.guardarProducto(producto);
        return ResponseEntity.ok(producto);
    }
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>>
        eliminarProducto(@PathVariable int id){
        Producto producto = productoServicio.buscarProductoPorId(id);
        if (producto == null)
            throw new Excepcion("No se encontro el id " + id);
        this.productoServicio.eliminarProductoPorId(producto.getIdProducto());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }


}
