package estructuras;

import java.util.*;

public class ArbolB {
    public NodoB raiz;
    public int t = 2; // Mínimo grado (orden). t=2 es B-tree de orden 2 (máximo 3 claves por nodo).
    public StringBuilder explicacion = new StringBuilder();

    public ArbolB() {
        raiz = null;
    }

    public void insertar(int k) {
        if (raiz == null) {
            raiz = new NodoB(true);
            raiz.claves.add(k);
            explicacion.append("Insertado el valor ").append(k).append("\\n");
            return;
        }
        if (raiz.claves.size() == 2 * t - 1) {
            NodoB s = new NodoB(false);
            s.hijos.add(raiz);
            dividirHijo(s, 0, raiz);
            insertarNoLleno(s, k);
            raiz = s;
        } else {
            insertarNoLleno(raiz, k);
        }
    }

    private void insertarNoLleno(NodoB x, int k) {
        int i = x.claves.size() - 1;
        if (x.hoja) {
            while (i >= 0 && k < x.claves.get(i)) i--;
            if (i >= 0 && k == x.claves.get(i)) {
                explicacion.append("El valor ya existe: ").append(k).append("\\n");
                return;
            }
            x.claves.add(i + 1, k);
            explicacion.append("Insertado el valor ").append(k).append("\\n");
        } else {
            while (i >= 0 && k < x.claves.get(i)) i--;
            i++;
            if (x.hijos.get(i).claves.size() == 2 * t - 1) {
                dividirHijo(x, i, x.hijos.get(i));
                if (k > x.claves.get(i)) i++;
            }
            insertarNoLleno(x.hijos.get(i), k);
        }
    }

    private void dividirHijo(NodoB padre, int i, NodoB y) {
        NodoB z = new NodoB(y.hoja);
        for (int j = 0; j < t - 1; j++)
            z.claves.add(y.claves.remove(t));
        if (!y.hoja) {
            for (int j = 0; j < t; j++)
                z.hijos.add(y.hijos.remove(t));
        }
        padre.claves.add(i, y.claves.remove(t - 1));
        padre.hijos.add(i + 1, z);
        explicacion.append("División de nodo (split)\\n");
    }

    public void eliminar(int k) {
        if (raiz == null) return;
        eliminarRec(raiz, k);
        if (raiz.claves.isEmpty() && !raiz.hoja) {
            raiz = raiz.hijos.get(0);
        }
    }

    private void eliminarRec(NodoB nodo, int k) {
        int idx = 0;
        while (idx < nodo.claves.size() && k > nodo.claves.get(idx)) idx++;
        if (idx < nodo.claves.size() && k == nodo.claves.get(idx)) {
            if (nodo.hoja) {
                nodo.claves.remove(idx);
                explicacion.append("Eliminado el valor ").append(k).append("\\n");
            } else {
                if (nodo.hijos.get(idx).claves.size() >= t) {
                    int pred = getPredecesor(nodo.hijos.get(idx));
                    nodo.claves.set(idx, pred);
                    eliminarRec(nodo.hijos.get(idx), pred);
                } else if (nodo.hijos.get(idx + 1).claves.size() >= t) {
                    int succ = getSucesor(nodo.hijos.get(idx + 1));
                    nodo.claves.set(idx, succ);
                    eliminarRec(nodo.hijos.get(idx + 1), succ);
                } else {
                    fusionar(nodo, idx);
                    eliminarRec(nodo.hijos.get(idx), k);
                }
                explicacion.append("Eliminado el valor ").append(k).append("\\n");
            }
        } else if (!nodo.hoja) {
            boolean flag = (idx == nodo.claves.size());
            if (nodo.hijos.get(idx).claves.size() < t) {
                rellenar(nodo, idx);
            }
            if (flag && idx > nodo.claves.size()) {
                eliminarRec(nodo.hijos.get(idx - 1), k);
            } else {
                eliminarRec(nodo.hijos.get(idx), k);
            }
        } else {
            explicacion.append("Valor no encontrado: ").append(k).append("\\n");
        }
    }

    private int getPredecesor(NodoB nodo) {
        while (!nodo.hoja)
            nodo = nodo.hijos.get(nodo.claves.size());
        return nodo.claves.get(nodo.claves.size() - 1);
    }

    private int getSucesor(NodoB nodo) {
        while (!nodo.hoja)
            nodo = nodo.hijos.get(0);
        return nodo.claves.get(0);
    }

    private void fusionar(NodoB nodo, int idx) {
        NodoB hijo = nodo.hijos.get(idx);
        NodoB hermano = nodo.hijos.get(idx + 1);
        hijo.claves.add(nodo.claves.remove(idx));
        hijo.claves.addAll(hermano.claves);
        if (!hijo.hoja) hijo.hijos.addAll(hermano.hijos);
        nodo.hijos.remove(idx + 1);
        explicacion.append("Fusión de nodos (merge)\\n");
    }

    private void rellenar(NodoB nodo, int idx) {
        if (idx != 0 && nodo.hijos.get(idx - 1).claves.size() >= t) {
            tomarPrestadoIzq(nodo, idx);
        } else if (idx != nodo.claves.size() && nodo.hijos.get(idx + 1).claves.size() >= t) {
            tomarPrestadoDer(nodo, idx);
        } else {
            if (idx != nodo.claves.size())
                fusionar(nodo, idx);
            else
                fusionar(nodo, idx - 1);
        }
    }

    private void tomarPrestadoIzq(NodoB nodo, int idx) {
        NodoB hijo = nodo.hijos.get(idx);
        NodoB hermano = nodo.hijos.get(idx - 1);
        hijo.claves.add(0, nodo.claves.get(idx - 1));
        nodo.claves.set(idx - 1, hermano.claves.remove(hermano.claves.size() - 1));
        if (!hermano.hoja)
            hijo.hijos.add(0, hermano.hijos.remove(hermano.hijos.size() - 1));
        explicacion.append("Préstamo desde la izquierda\\n");
    }

    private void tomarPrestadoDer(NodoB nodo, int idx) {
        NodoB hijo = nodo.hijos.get(idx);
        NodoB hermano = nodo.hijos.get(idx + 1);
        hijo.claves.add(nodo.claves.get(idx));
        nodo.claves.set(idx, hermano.claves.remove(0));
        if (!hermano.hoja)
            hijo.hijos.add(hermano.hijos.remove(0));
        explicacion.append("Préstamo desde la derecha\\n");
    }

    // Convertir árbol a Map para JSON
    public Map<String, Object> toMap(NodoB n) {
        if (n == null) return null;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("claves", new ArrayList<>(n.claves));
        map.put("hoja", n.hoja);
        List<Object> hijos = new ArrayList<>();
        for (NodoB h : n.hijos) {
            hijos.add(toMap(h));
        }
        map.put("hijos", hijos);
        return map;
    }
}
