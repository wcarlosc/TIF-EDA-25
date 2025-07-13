package estructuras;

public class TestArbolBinario {
    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();

        // Insertar valores
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(60);
        arbol.insertar(80);

        System.out.println("Árbol después de inserciones:");
        System.out.println(arbol.toJson());

        // Eliminar un valor
        arbol.eliminar(20);
        System.out.println("Árbol después de eliminar 20:");
        System.out.println(arbol.toJson());

        arbol.eliminar(30);
        System.out.println("Árbol después de eliminar 30:");
        System.out.println(arbol.toJson());

        arbol.eliminar(50);
        System.out.println("Árbol después de eliminar 50:");
        System.out.println(arbol.toJson());
    }
}
