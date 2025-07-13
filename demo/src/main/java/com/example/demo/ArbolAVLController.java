package com.example.demo;

import estructuras.ArbolAVL;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/arbol-avl")
@CrossOrigin // Permite llamadas desde tu frontend local
public class ArbolAVLController {
    private final ArbolAVL arbol = new ArbolAVL();

    @PostMapping("/insertar")
    public Map<String, Object> insertar(@RequestBody Map<String, Integer> body) {
        arbol.explicacion = new StringBuilder();
        int valor = body.get("valor");
        arbol.raiz = arbol.insertar(arbol.raiz, valor);
        return getEstado();
    }

    @PostMapping("/eliminar")
    public Map<String, Object> eliminar(@RequestBody Map<String, Integer> body) {
        arbol.explicacion = new StringBuilder();
        int valor = body.get("valor");
        arbol.raiz = arbol.eliminar(arbol.raiz, valor);
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
