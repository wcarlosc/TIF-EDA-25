package estructuras;

public class NodoAVL {
    public int valor;
    public NodoAVL izquierdo, derecho;
    public int altura;

    public NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}
