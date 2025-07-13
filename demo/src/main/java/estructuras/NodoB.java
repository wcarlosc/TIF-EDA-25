package estructuras;

import java.util.*;

public class NodoB {
    public List<Integer> claves = new ArrayList<>();
    public List<NodoB> hijos = new ArrayList<>();
    public boolean hoja;

    public NodoB(boolean hoja) {
        this.hoja = hoja;
    }
}
