package estructuras;

import java.util.*;

public class ArbolSplay {
    public NodoSplay raiz;
    public StringBuilder explicacion = new StringBuilder();

    // Splay operation
    private NodoSplay splay(NodoSplay root, int key) {
        if (root == null || root.valor == key)
            return root;

        // Key in left subtree
        if (key < root.valor) {
            if (root.izquierdo == null) return root;

            // Zig-Zig (Left Left)
            if (key < root.izquierdo.valor) {
                root.izquierdo.izquierdo = splay(root.izquierdo.izquierdo, key);
                root = rotacionDerecha(root);
                explicacion.append("Rotación derecha (zig-zig) en ").append(root.valor).append("\\n");
            }
            // Zig-Zag (Left Right)
            else if (key > root.izquierdo.valor) {
                root.izquierdo.derecho = splay(root.izquierdo.derecho, key);
                if (root.izquierdo.derecho != null)
                    root.izquierdo = rotacionIzquierda(root.izquierdo);
            }
            return (root.izquierdo == null) ? root : rotacionDerecha(root);
        }
        // Key in right subtree
        else {
            if (root.derecho == null) return root;

            // Zag-Zig (Right Left)
            if (key < root.derecho.valor) {
                root.derecho.izquierdo = splay(root.derecho.izquierdo, key);
                if (root.derecho.izquierdo != null)
                    root.derecho = rotacionDerecha(root.derecho);
            }
            // Zag-Zag (Right Right)
            else if (key > root.derecho.valor) {
                root.derecho.derecho = splay(root.derecho.derecho, key);
                root = rotacionIzquierda(root);
                explicacion.append("Rotación izquierda (zag-zag) en ").append(root.valor).append("\\n");
            }
            return (root.derecho == null) ? root : rotacionIzquierda(root);
        }
    }

    private NodoSplay rotacionDerecha(NodoSplay x) {
        NodoSplay y = x.izquierdo;
        x.izquierdo = y.derecho;
        y.derecho = x;
        return y;
    }

    private NodoSplay rotacionIzquierda(NodoSplay x) {
        NodoSplay y = x.derecho;
        x.derecho = y.izquierdo;
        y.izquierdo = x;
        return y;
    }

    // Insertar valor
    public NodoSplay insertar(NodoSplay root, int key) {
        if (root == null) {
            explicacion.append("Insertado el valor ").append(key).append("\\n");
            return new NodoSplay(key);
        }
        root = splay(root, key);
        if (root.valor == key) {
            explicacion.append("El valor ya existe: ").append(key).append("\\n");
            return root;
        }
        NodoSplay nuevo = new NodoSplay(key);
        if (key < root.valor) {
            nuevo.derecho = root;
            nuevo.izquierdo = root.izquierdo;
            root.izquierdo = null;
        } else {
            nuevo.izquierdo = root;
            nuevo.derecho = root.derecho;
            root.derecho = null;
        }
        explicacion.append("Insertado el valor ").append(key).append("\\n");
        return nuevo;
    }

    // Eliminar valor
    public NodoSplay eliminar(NodoSplay root, int key) {
        if (root == null) {
            explicacion.append("Valor no encontrado: ").append(key).append("\\n");
            return null;
        }
        root = splay(root, key);
        if (root.valor != key) {
            explicacion.append("Valor no encontrado: ").append(key).append("\\n");
            return root;
        }
        if (root.izquierdo == null) {
            explicacion.append("Eliminado el valor ").append(key).append("\\n");
            return root.derecho;
        } else {
            NodoSplay temp = splay(root.izquierdo, key);
            temp.derecho = root.derecho;
            explicacion.append("Eliminado el valor ").append(key).append("\\n");
            return temp;
        }
    }

    // Convertir árbol a Map para JSON
    public Map<String, Object> toMap(NodoSplay n) {
        if (n == null) return null;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("valor", n.valor);
        map.put("izquierdo", toMap(n.izquierdo));
        map.put("derecho", toMap(n.derecho));
        return map;
    }
}
