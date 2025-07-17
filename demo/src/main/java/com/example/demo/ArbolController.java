package com.example.demo;

import estructuras.ArbolAVL;
import estructuras.ArbolBinario;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
    
    @GetMapping("/buscar/{valor}")
    public Map<String, Object> buscar(@PathVariable int valor) {
        boolean encontrado = arbol.buscar(valor);
        Map<String, Object> res = new HashMap<>();
        res.put("encontrado", encontrado);
        res.put("explicacion", arbol.getUltimaOperacion());
        return res;
    }

    @PostMapping("/reiniciar")
    public Map<String, Object> reiniciar() {
        arbol = new ArbolBinario(); 
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Árbol AVL reiniciado correctamente");
        response.put("arbol", null);
        response.put("explicacion", "El árbol ha sido limpiado.");
        return response;
    }

    

    @GetMapping
    public Map<String, Object> obtenerArbol() {
        Map<String, Object> res = new HashMap<>();
        res.put("arbol", arbol.toJson());
        res.put("explicacion", arbol.getUltimaOperacion());
        return res;
    }
}
