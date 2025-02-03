import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

// **Árbol AVL

// **Árbol B

// **Árbol B+

//Bùsqueda B+

class NodoBPlus {
    List<Integer> claves;
    List<NodoBPlus> hijos;
    boolean esHoja;

    public NodoBPlus(int grado) {
        this.claves = new ArrayList<>(grado - 1);
        this.hijos = new ArrayList<>(grado);
        this.esHoja = true;
    }
}

class ArbolBPlus {
    private NodoBPlus raiz;
    private int grado;

    public ArbolBPlus(int grado) {
        this.grado = grado;
        this.raiz = new NodoBPlus(grado);
    }

    public void insertar(int clave) {
        NodoBPlus nodo = raiz;
        if (nodo.claves.size() == grado - 1) {
            NodoBPlus nuevoNodo = new NodoBPlus(grado);
            raiz = nuevoNodo;
            nuevoNodo.esHoja = false;
            nuevoNodo.hijos.add(nodo);
            dividir(nuevoNodo, nodo, 0);
            insertarEnNodo(nuevoNodo, clave);
        } else {
            insertarEnNodo(nodo, clave);
        }
    }

    private void insertarEnNodo(NodoBPlus nodo, int clave) {
        int i = nodo.claves.size() - 1;
        if (nodo.esHoja) {
            while (i >= 0 && clave < nodo.claves.get(i)) {
                i--;
            }
            nodo.claves.add(i + 1, clave);
        } else {
            while (i >= 0 && clave < nodo.claves.get(i)) {
                i--;
            }
            i++;
            NodoBPlus hijo = nodo.hijos.get(i);
            if (hijo.claves.size() == grado - 1) {
                dividir(nodo, hijo, i);
                if (clave > nodo.claves.get(i)) {
                    i++;
                }
            }
            insertarEnNodo(nodo.hijos.get(i), clave);
        }
    }

    private void dividir(NodoBPlus padre, NodoBPlus hijo, int indice) {
        NodoBPlus nuevoHijo = new NodoBPlus(grado);
        int mid = grado / 2;
        padre.claves.add(indice, hijo.claves.get(mid));
        padre.hijos.add(indice + 1, nuevoHijo);
        nuevoHijo.esHoja = hijo.esHoja;

        for (int i = mid + 1; i < hijo.claves.size(); i++) {
            nuevoHijo.claves.add(hijo.claves.get(i));
        }
        hijo.claves.subList(mid, hijo.claves.size()).clear();

        if (!hijo.esHoja) {
            for (int i = mid + 1; i < hijo.hijos.size(); i++) {
                nuevoHijo.hijos.add(hijo.hijos.get(i));
            }
            hijo.hijos.subList(mid + 1, hijo.hijos.size()).clear();
        }
    }

    public void buscar(int clave) {
        buscarEnNodo(raiz, clave, "");
    }

    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String RESET = "\u001B[0m";

    private void buscarEnNodo(NodoBPlus nodo, int clave, String camino) {
        if (nodo == null) {
            System.out.println("No se encontró la clave.");
            return;
        }
    
        int i = 0;
        while (i < nodo.claves.size() && clave > nodo.claves.get(i)) {
            i++;
        }
    
        // Imprimir el árbol resaltando la clave que se está verificando
        System.out.println(CYAN + "----------------------------------------------------------------------------------------------------------" + RESET);
        System.out.println(VERDE + "Recorrido actual del árbol" + RESET);
        imprimirArbol(clave, i); // Pasar la clave buscada y el índice
    
        // Mostrar el mensaje de verificación después de imprimir el árbol
        if (i < nodo.claves.size()) {
            System.out.println("Verificando clave " + AMARILLO + "[" + nodo.claves.get(i) + "]" + RESET + ": dirección " + (clave > nodo.claves.get(i) ? "derecha" : "izquierda"));
        } else {
            System.out.println("Verificando clave " + AMARILLO + "[Ninguna]" + RESET + ": dirección izquierda");
        }
    
        // Verificar si se encontró la clave
        if (i < nodo.claves.size() && clave == nodo.claves.get(i)) {
            camino += "Clave encontrada: " + clave + "\n";
            System.out.println(camino);
            return;
        }
    
        if (nodo.esHoja) {
            camino += "No se encontró la clave, el nodo más cercano es: " + (i > 0 ? nodo.claves.get(i - 1) : "Ninguna") + "\n";
            System.out.println(camino);
            return;
        }
    
        // Continuar la búsqueda en el hijo correspondiente
        buscarEnNodo(nodo.hijos.get(i), clave, camino);
    }
    
    public void imprimirArbol(int claveBuscada, int indiceVerificacion) {
        Queue<NodoBPlus> queue = new LinkedList<>();
        queue.add(raiz);
        int nivel = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            // Imprimir espacios en blanco según el nivel
            if (nivel == 0) {
                System.out.print("                 "); // 5 espacios para el nivel 0
            } else if (nivel == 1) {
                System.out.print("              "); // 3 espacios para el nivel 1
            } else if (nivel == 2) {
                System.out.print("          "); // 3 espacios para el nivel 1
            } else if (nivel == 3) {
                System.out.print(" "); // 3 espacios para el nivel 1
            }
    
            for (int i = 0; i < levelSize; i++) {
                NodoBPlus nodo = queue.poll();
                for (int j = 0; j < nodo.claves.size(); j++) {
                    Integer clave = nodo.claves.get(j);
                    // Resaltar las claves que se están verificando
                    if (j == indiceVerificacion) {
                        System.out.print(VERDE + "[" + clave + "]" + RESET + " "); // Colorear la clave que se está verificando
                    } else {
                        System.out.print("[" + clave + "] "); // Agregar corchetes a las claves
                    }
                }
                if (!nodo.esHoja) {
                    queue.addAll(nodo.hijos);
                }
            }
            System.out.println();
            nivel++;
        }
    }
    
}    

//FIN Bùsqueda B+
public class ArbolesDemo {
    
    public static final String CYAN = "\u001B[36m";
    public static final String ROJO = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        ArbolBPlus arbol = new ArbolBPlus(4);
        System.out.println(ROJO + "Proyecto Segundo Bimestre");
        System.out.println("Bienvenido al programa de árboles" + RESET);
        
        while (true) {
            try {
                System.out.println(CYAN +"\nSeleccione el tipo de árbol:" + RESET);
                System.out.println("1. Árbol AVL (no implementado)");
                System.out.println("2. Árbol B (no implementado)");
                System.out.println("3. Árbol B+");
                System.out.println("0. Salir");
                System.out.print("Ingrese el número del tipo de árbol: ");
                int tipoArbol = Integer.parseInt(scanner.nextLine());

                if (tipoArbol == 0) {
                    break;
                } else if (tipoArbol == 3) {
                    while (true) {
                        System.out.println(CYAN + "\nMenu:" + RESET);
                        System.out.println("1. Búsqueda de la clave de un nodo");
                        System.out.println("2. Inserción de un nuevo nodo en el arbol(no implementado)");
                        System.out.println("3. Eliminación de un nodo en el arbol(no implementado)");
                        System.out.println("4. Recorrido del arbol(no implementado)");
                        System.out.println("0. Volver al menú principal");
                        System.out.print("Ingrese el número de la operación: ");
                        int operacion = Integer.parseInt(scanner.nextLine());

                        if (operacion == 0) {
                            break;
                        } else if (operacion == 1) {
                            System.out.println(CYAN + "----------------------------------------------------------------------------------------------------------" + RESET);
                            for (int i = 0; i < 15; i++) {
                                arbol.insertar((int) (Math.random() * 100));
                            }
                            System.out.println("Arbol B+ generado");
                            arbol.imprimirArbol(0, -1);
                            System.out.print("Ingrese la clave a buscar: ");
                            int claveBuscada = Integer.parseInt(scanner.nextLine());
                            arbol.buscar(claveBuscada);
                            System.out.println(CYAN + "----------------------------------------------------------------------------------------------------------" + RESET);
                            
                        } else if (operacion == 2) {
                            System.out.print("Ingrese la clave a insertar: ");
                            // Aquí iría la lógica de inserción
                        } else if (operacion == 4) {
                            // Aquí iría la lógica de recorrido
                        } else {
                            System.out.println(ROJO + "Opción no válida." + RESET);
                        }
                    }
                } else {
                    System.out.println(ROJO + "Opción no válida." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada no válida. Por favor, ingrese un número." + RESET);
            }
        }

        scanner.close();
    }
}

