package com.example.demo;

import estructuras.ArbolAVL;
import estructuras.ArbolSplay;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/arbol-splay")
@CrossOrigin // Permite pruebas locales desde tu frontend
public class ArbolSplayController {
    private ArbolSplay arbol = new ArbolSplay();

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
        boolean encontrado = arbol.buscar(valor);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("arbol", arbol.toMap(arbol.raiz)); // El splay mueve el nodo buscado a la raíz
        response.put("explicacion", arbol.explicacion.toString().replace("\\n", "\n"));
        response.put("encontrado", encontrado);
        return response;
    }
    @PostMapping("/reiniciar")
    public Map<String, Object> reiniciar() {
        arbol = new ArbolSplay(); 
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Árbol AVL reiniciado correctamente");
        response.put("arbol", null);
        response.put("explicacion", "El árbol ha sido limpiado.");
        return response;
    }

}
