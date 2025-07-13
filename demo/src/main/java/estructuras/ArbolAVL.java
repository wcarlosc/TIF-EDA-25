package estructuras;
import java.util.*;

public class ArbolAVL {
    public NodoAVL raiz;
    public StringBuilder explicacion = new StringBuilder();

    public int altura(NodoAVL n) {
        return (n == null) ? 0 : n.altura;
    }

    public int balance(NodoAVL n) {
        return (n == null) ? 0 : altura(n.izquierdo) - altura(n.derecho);
    }

    public NodoAVL insertar(NodoAVL nodo, int valor) {
        if (nodo == null) {
            explicacion.append("Insertado el valor ").append(valor).append("\\n");
            return new NodoAVL(valor);
        }
        if (valor < nodo.valor) {
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertar(nodo.derecho, valor);
        } else {
            explicacion.append("El valor ya existe: ").append(valor).append("\\n");
            return nodo;
        }
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
        int balance = balance(nodo);

        // Rotaciones
        // Left Left
        if (balance > 1 && valor < nodo.izquierdo.valor) {
            explicacion.append("Rotación simple a la derecha en ").append(nodo.valor).append("\\n");
            return rotacionDerecha(nodo);
        }
        // Right Right
        if (balance < -1 && valor > nodo.derecho.valor) {
            explicacion.append("Rotación simple a la izquierda en ").append(nodo.valor).append("\\n");
            return rotacionIzquierda(nodo);
        }
        // Left Right
        if (balance > 1 && valor > nodo.izquierdo.valor) {
            explicacion.append("Rotación doble izquierda-derecha en ").append(nodo.valor).append("\\n");
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }
        // Right Left
        if (balance < -1 && valor < nodo.derecho.valor) {
            explicacion.append("Rotación doble derecha-izquierda en ").append(nodo.valor).append("\\n");
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }
        return nodo;
    }

    public NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;
        x.derecho = y;
        y.izquierdo = T2;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        return x;
    }

    public NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;
        y.izquierdo = x;
        x.derecho = T2;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        return y;
    }

    public NodoAVL eliminar(NodoAVL nodo, int valor) {
        if (nodo == null) {
            explicacion.append("Valor no encontrado: ").append(valor).append("\\n");
            return null;
        }
        if (valor < nodo.valor) {
            nodo.izquierdo = eliminar(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = eliminar(nodo.derecho, valor);
        } else {
            explicacion.append("Eliminado el valor ").append(valor).append("\\n");
            if (nodo.izquierdo == null || nodo.derecho == null) {
                NodoAVL temp = (nodo.izquierdo != null) ? nodo.izquierdo : nodo.derecho;
                if (temp == null)
                    return null;
                else
                    nodo = temp;
            } else {
                NodoAVL temp = minValorNodo(nodo.derecho);
                nodo.valor = temp.valor;
                nodo.derecho = eliminar(nodo.derecho, temp.valor);
            }
        }
        nodo.altura = Math.max(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;
        int balance = balance(nodo);
        // Rotaciones
        if (balance > 1 && balance(nodo.izquierdo) >= 0)
            return rotacionDerecha(nodo);
        if (balance > 1 && balance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && balance(nodo.derecho) <= 0)
            return rotacionIzquierda(nodo);
        if (balance < -1 && balance(nodo.derecho) > 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }
        return nodo;
    }

    public NodoAVL minValorNodo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izquierdo != null)
            actual = actual.izquierdo;
        return actual;
    }

    // Métodos para exponer el árbol como JSON
    public Map<String, Object> toMap(NodoAVL n) {
        if (n == null) return null;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("valor", n.valor);
        map.put("izquierdo", toMap(n.izquierdo));
        map.put("derecho", toMap(n.derecho));
        return map;
    }
}
