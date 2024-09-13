package CS.Registro.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class Excepcion extends RuntimeException {
    public Excepcion(String mensaje){
        super(mensaje);
    }
}
