package com.example.demo;

import estructuras.ArbolB;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/arbol-b")
@CrossOrigin // Permite pruebas locales
public class ArbolBController {
    private final ArbolB arbol = new ArbolB();

    @PostMapping("/insertar")
    public Map<String, Object> insertar(@RequestBody Map<String, Integer> body) {
        arbol.explicacion = new StringBuilder();
        int valor = body.get("valor");
        arbol.insertar(valor);
        return getEstado();
    }

    @PostMapping("/eliminar")
    public Map<String, Object> eliminar(@RequestBody Map<String, Integer> body) {
        arbol.explicacion = new StringBuilder();
        int valor = body.get("valor");
        arbol.eliminar(valor);
        return getEstado();
    }

    @GetMapping
    public Map<String, Object> getEstado() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("arbol", arbol.toMap(arbol.raiz));
        response.put("explicacion", arbol.explicacion.toString().replace("\\n", "\n"));
        return response;
    }
}
