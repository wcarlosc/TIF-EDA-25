package com.example.demo;

import estructuras.ArbolBinario;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/arbol")
public class ArbolController {
    // Creamos una instancia global del árbol binario
    private ArbolBinario arbol = new ArbolBinario();

    @PostMapping("/insertar")
    public void insertar(@RequestBody Map<String, Integer> body) {
        arbol.insertar(body.get("valor"));
    }

    @PostMapping("/eliminar")
    public void eliminar(@RequestBody Map<String, Integer> body) {
        arbol.eliminar(body.get("valor"));
    }

    @GetMapping
    public Map<String, Object> obtenerArbol() {
        Map<String, Object> res = new HashMap<>();
        res.put("arbol", arbol.toJson());
        res.put("explicacion", arbol.getUltimaOperacion());
        return res;
    }
}
