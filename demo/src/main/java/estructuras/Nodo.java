package estructuras;

public class Nodo {
    public int valor;
    public Nodo izquierdo, derecho;

    public Nodo(int valor) {
        this.valor = valor;
        izquierdo = derecho = null;
    }
}
