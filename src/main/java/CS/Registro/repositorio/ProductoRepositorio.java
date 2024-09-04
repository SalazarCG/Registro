package CS.Registro.repositorio;

import CS.Registro.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository <Producto, Integer> {
}
