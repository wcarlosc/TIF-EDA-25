package estructuras;

import java.util.*;

public class ArbolBinario {
    private Nodo raiz;
    private String ultimaOperacion = "";

    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
        ultimaOperacion = "Insertado el valor " + valor;
    }

    private Nodo insertarRec(Nodo nodo, int valor) {
        if (nodo == null) return new Nodo(valor);
        if (valor < nodo.valor) nodo.izquierdo = insertarRec(nodo.izquierdo, valor);
        else if (valor > nodo.valor) nodo.derecho = insertarRec(nodo.derecho, valor);
        return nodo;
    }

    public void eliminar(int valor) {
        raiz = eliminarRec(raiz, valor);
        ultimaOperacion = "Eliminado el valor " + valor;
    }

    private Nodo eliminarRec(Nodo nodo, int valor) {
        if (nodo == null) return null;
        if (valor < nodo.valor) nodo.izquierdo = eliminarRec(nodo.izquierdo, valor);
        else if (valor > nodo.valor) nodo.derecho = eliminarRec(nodo.derecho, valor);
        else {
            if (nodo.izquierdo == null) return nodo.derecho;
            else if (nodo.derecho == null) return nodo.izquierdo;
            nodo.valor = minValor(nodo.derecho);
            nodo.derecho = eliminarRec(nodo.derecho, nodo.valor);
        }
        return nodo;
    }
    public boolean buscar(int valor) {
    boolean encontrado = buscarRec(raiz, valor);
        if (encontrado) {
            ultimaOperacion = "Valor " + valor + " encontrado en el árbol.";
        } else {
            ultimaOperacion = "Valor " + valor + " no encontrado.";
        }
        return encontrado;
    }

    private boolean buscarRec(Nodo nodo, int valor) {
        if (nodo == null) return false;
        if (valor == nodo.valor) return true;
        if (valor < nodo.valor) return buscarRec(nodo.izquierdo, valor);
        else return buscarRec(nodo.derecho, valor);
    }


    private int minValor(Nodo nodo) {
        int minv = nodo.valor;
        while (nodo.izquierdo != null) {
            minv = nodo.izquierdo.valor;
            nodo = nodo.izquierdo;
        }
        return minv;
    }

    // Serialización simple para el frontend
    public Map<String, Object> toJson() {
        return nodoToJson(raiz);
    }

    private Map<String, Object> nodoToJson(Nodo nodo) {
        if (nodo == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("valor", nodo.valor);
        map.put("izquierdo", nodoToJson(nodo.izquierdo));
        map.put("derecho", nodoToJson(nodo.derecho));
        return map;
    }

    public String getUltimaOperacion() {
        return ultimaOperacion;
    }
}
