package com.example.demo;

import estructuras.ArbolAVL;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/arbol-avl")

@CrossOrigin
public class ArbolAVLController {
    private ArbolAVL arbol = new ArbolAVL();

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
    @GetMapping("/buscar/{valor}")
    public Map<String, Object> buscar(@PathVariable int valor) {
        arbol.explicacion = new StringBuilder();
        boolean encontrado = arbol.buscar(arbol.raiz, valor);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("encontrado", encontrado);
        response.put("explicacion", arbol.getExplicacion().replace("\\n", "\n"));
        return response;
    }

    @PostMapping("/reiniciar")
    public Map<String, Object> reiniciar() {
        arbol = new ArbolAVL(); 
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Árbol AVL reiniciado correctamente");
        response.put("arbol", null);
        response.put("explicacion", "El árbol ha sido limpiado.");
        return response;
    }



}
