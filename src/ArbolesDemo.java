import java.util.*;

// **Árbol AVL

// **Árbol B

// **Árbol B+

// Bùsqueda B+

class NodoBPlus {
    List<Integer> clavesBmas;
    List<NodoBPlus> hijosBmas;
    boolean esHojaBmas;

    public NodoBPlus(int gradoBmas) {
        this.clavesBmas = new ArrayList<>(gradoBmas - 1);
        this.hijosBmas = new ArrayList<>(gradoBmas);
        this.esHojaBmas = true;
    }
}

class ArbolBPlus {
    NodoBPlus raizBmas;
    private int gradoBmas;

    public ArbolBPlus(int gradoBmas) {
        this.gradoBmas = gradoBmas;
        this.raizBmas = new NodoBPlus(gradoBmas);
    }

    // Inicio Insercion B+
    public static final String ROJOBmas = "\u001B[31m";
    public void insertarBmas(int claveBmas) {
        // Verificar si la clave está dentro del rango permitido
        if (claveBmas < 1 || claveBmas > 100) {
            System.out.println(ROJOBmas + "La clave debe estar entre 1 y 100." + RESETBmas);
            return;
        }

        // Verificar la altura del árbol
        if (altura(raizBmas) >= 4) { // Cambiar a 4 para permitir altura máxima de 4
            System.out.println(ROJOBmas + "No se puede insertar más claves, el árbol ha alcanzado la altura máxima de 4." + RESETBmas);
            return;
        }

        NodoBPlus nodoBmas = raizBmas;
        if (nodoBmas.clavesBmas.size() == gradoBmas - 1) {
            NodoBPlus nuevoNodoBmas = new NodoBPlus(gradoBmas);
            raizBmas = nuevoNodoBmas;
            nuevoNodoBmas.esHojaBmas = false;
            nuevoNodoBmas.hijosBmas.add(nodoBmas);
            dividirBmas(nuevoNodoBmas, nodoBmas, 0);
            insertarEnNodoBmas(nuevoNodoBmas, claveBmas);
        } else {
            insertarEnNodoBmas(nodoBmas, claveBmas);
        }
    }

    private void insertarEnNodoBmas(NodoBPlus nodoBmas, int claveBmas) {
        int iBmas = nodoBmas.clavesBmas.size() - 1;
        if (nodoBmas.esHojaBmas) {
            while (iBmas >= 0 && claveBmas < nodoBmas.clavesBmas.get(iBmas)) {
                iBmas--;
            }
            nodoBmas.clavesBmas.add(iBmas + 1, claveBmas);
            System.out.println("Clave " + AMARILLOBmas + claveBmas + RESETBmas + " insertada en el nodo hoja.");
            imprimirArbolBmas(0, -1); // Mostrar el árbol después de la inserción
        } else {
            while (iBmas >= 0 && claveBmas < nodoBmas.clavesBmas.get(iBmas)) {
                iBmas--;
            }
            iBmas++;
            NodoBPlus hijoBmas = nodoBmas.hijosBmas.get(iBmas);
            if (hijoBmas.clavesBmas.size() == gradoBmas - 1) {
                dividirBmas(nodoBmas, hijoBmas, iBmas);
                if (claveBmas > nodoBmas.clavesBmas.get(iBmas)) {
                    iBmas++;
                }
            }
            insertarEnNodoBmas(nodoBmas.hijosBmas.get(iBmas), claveBmas);
        }
    }

    private void dividirBmas(NodoBPlus padreBmas, NodoBPlus hijoBmas, int indiceBmas) {
        NodoBPlus nuevoHijoBmas = new NodoBPlus(gradoBmas);
        int midBmas = gradoBmas / 2;
        padreBmas.clavesBmas.add(indiceBmas, hijoBmas.clavesBmas.get(midBmas));
        padreBmas.hijosBmas.add(indiceBmas + 1, nuevoHijoBmas);
        nuevoHijoBmas.esHojaBmas = hijoBmas.esHojaBmas;

        for (int iBmas = midBmas + 1; iBmas < hijoBmas.clavesBmas.size(); iBmas++) {
            nuevoHijoBmas.clavesBmas.add(hijoBmas.clavesBmas.get(iBmas));
        }
        hijoBmas.clavesBmas.subList(midBmas, hijoBmas.clavesBmas.size()).clear();

        if (!hijoBmas.esHojaBmas) {
            for (int iBmas = midBmas + 1; iBmas < hijoBmas.hijosBmas.size(); iBmas++) {
                nuevoHijoBmas.hijosBmas.add(hijoBmas.hijosBmas.get(iBmas));
            }
            hijoBmas.hijosBmas.subList(midBmas + 1, hijoBmas.hijosBmas.size()).clear();
        }
    }
    // Fin Insercion B+
    // Inicio Bùsqueda B+
    public void buscarBmas(int claveBmas) {
        buscarEnNodoBmas(raizBmas, claveBmas, "");
    }

    public static final String VERDEBmas = "\u001B[32m";
    public static final String CYANBmas = "\u001B[36m";
    public static final String AMARILLOBmas = "\u001B[33m";
    public static final String RESETBmas = "\u001B[0m";

    private void buscarEnNodoBmas(NodoBPlus nodoBmas, int claveBmas, String caminoBmas) {
        if (nodoBmas == null) {
            System.out.println("No se encontró la clave.");
            return;
        }

        int iBmas = 0;
        while (iBmas < nodoBmas.clavesBmas.size() && claveBmas > nodoBmas.clavesBmas.get(iBmas)) {
            iBmas++;
        }

        // Imprimir el árbol resaltando la clave que se está verificando
        System.out.println(CYANBmas + "---------------------------------------------------------------------------------------------------------" + RESETBmas);
        System.out.println(VERDEBmas + "Recorrido actual del árbol" + RESETBmas);
        imprimirArbolBmas(claveBmas, iBmas); // Pasar la clave buscada y el índice

        // Mostrar el mensaje de verificación después de imprimir el árbol
        if (iBmas < nodoBmas.clavesBmas.size()) {
            System.out.println("Verificando clave " + AMARILLOBmas + "[" + nodoBmas.clavesBmas.get(iBmas) + "]" + RESETBmas + ": dirección " + (claveBmas > nodoBmas.clavesBmas.get(iBmas) ? "derecha" : "izquierda"));
        } else {
            System.out.println("Verificando clave " + AMARILLOBmas + "[Ninguno]" + RESETBmas + ": dirección izquierda");
        }

        // Verificar si se encontró la clave
        if (iBmas < nodoBmas.clavesBmas.size() && claveBmas == nodoBmas.clavesBmas.get(iBmas)) {
            caminoBmas += "Clave encontrada: " + claveBmas + "\n";
            System.out.println(caminoBmas);
            return;
        }

        if (nodoBmas.esHojaBmas) {
            caminoBmas += "No se encontró la clave, el nodo más cercano es: " + (iBmas > 0 ? nodoBmas.clavesBmas.get(iBmas - 1) : "Ninguna") + "\n";
            System.out.println(caminoBmas);
            return;
        }

        // Continuar la búsqueda en el hijo correspondiente
        buscarEnNodoBmas(nodoBmas.hijosBmas.get(iBmas), claveBmas, caminoBmas);
    }

    public void imprimirArbolBmas(int claveBuscadaBmas, int indiceVerificacionBmas) {
        Queue<NodoBPlus> queueBmas = new LinkedList<>();
        queueBmas.add(raizBmas);
        int nivelBmas = 0;
        while (!queueBmas.isEmpty()) {
            int levelSizeBmas = queueBmas.size();

            // Imprimir espacios en blanco según el nivel
            if (nivelBmas == 0) {
                System.out.print("                 "); 
            } else if (nivelBmas == 1) {
                System.out.print("              "); 
            } else if (nivelBmas == 2) {
                System.out.print("          "); 
            } else if (nivelBmas == 3) {
                System.out.print(" "); 
            }

            for (int iBmas = 0; iBmas < levelSizeBmas; iBmas++) {
                NodoBPlus nodoBmas = queueBmas.poll();
                if (nodoBmas.clavesBmas.isEmpty()) {
                    System.out.print("[ ] "); // Imprimir espacio vacío si no hay claves
                } else {
                    for (int jBmas = 0; jBmas < nodoBmas.clavesBmas.size(); jBmas++) {
                        Integer claveBmas = nodoBmas.clavesBmas.get(jBmas);
                        // Resaltar las claves que se están verificando
                        if (jBmas == indiceVerificacionBmas) {
                            System.out.print(VERDEBmas + "[" + claveBmas + "]" + RESETBmas + " "); // Colorear la clave que se está verificando
                        } else {
                            System.out.print("[" + claveBmas + "] "); // Agregar corchetes a las claves
                        }
                    }
                }
                if (!nodoBmas.esHojaBmas) {
                    queueBmas.addAll(nodoBmas.hijosBmas);
                }
            }
            System.out.println();
            nivelBmas++;
        }
    }

    // Métodos de recorrido
    public String recorridoPreorden(NodoBPlus nodo) {
        if (nodo == null) return "";
        StringBuilder resultado = new StringBuilder();
        resultado.append(nodo.clavesBmas).append(" "); // Imprimir la clave del nodo
        for (NodoBPlus hijo : nodo.hijosBmas) {
            String recorridoHijo = recorridoPreorden(hijo);
            if (!recorridoHijo.isEmpty()) {
                resultado.append(recorridoHijo); // Recorrer los hijos
            }
        }
        return resultado.toString();
    }

    public String recorridoInorden(NodoBPlus nodo) {
        if (nodo == null) return "";
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < nodo.clavesBmas.size(); i++) {
            if (!nodo.esHojaBmas) {
                String recorridoHijoIzquierdo = recorridoInorden(nodo.hijosBmas.get(i));
                if (!recorridoHijoIzquierdo.isEmpty()) {
                    resultado.append(recorridoHijoIzquierdo); // Recorrer el hijo izquierdo
                }
            }
            resultado.append(nodo.clavesBmas.get(i)).append(" "); // Imprimir la clave
        }
        if (!nodo.esHojaBmas) {
            String recorridoHijoDerecho = recorridoInorden(nodo.hijosBmas.get(nodo.hijosBmas.size() - 1)); // Recorrer el hijo derecho
            if (!recorridoHijoDerecho.isEmpty()) {
                resultado.append(recorridoHijoDerecho);
            }
        }
        return resultado.toString();
    }

    public String recorridoPostorden(NodoBPlus nodo) {
        if (nodo == null) return "";
        StringBuilder resultado = new StringBuilder();
        for (NodoBPlus hijo : nodo.hijosBmas) {
            String recorridoHijo = recorridoPostorden(hijo); // Recorrer los hijos
            if (!recorridoHijo.isEmpty()) {
                resultado.append(recorridoHijo);
            }
        }
        resultado.append(nodo.clavesBmas).append(" "); // Imprimir la clave del nodo
        return resultado.toString();
    }

    public String recorridoAnchura() {
        Queue<NodoBPlus> queue = new LinkedList<>();
        StringBuilder resultado = new StringBuilder();
        queue.add(raizBmas);
        while (!queue.isEmpty()) {
            NodoBPlus nodo = queue.poll();
            resultado.append(nodo.clavesBmas).append(" "); // Imprimir la clave del nodo
            for (NodoBPlus hijo : nodo.hijosBmas) {
                queue.add(hijo); // Agregar los hijos a la cola
            }
        }
        return resultado.toString();
    }

    // Método para calcular la altura del árbol
    public int altura(NodoBPlus nodo) {
        if (nodo == null) {
            return 0;
        }
        int alturaMaxima = 0;
        for (NodoBPlus hijo : nodo.hijosBmas) {
            alturaMaxima = Math.max(alturaMaxima, altura(hijo));
        }
        return 1 + alturaMaxima; // Contar el nodo actual
    }
}

// Fin Bùsqueda B+
// Inicio Eliminación B+

// Fin Eliminación B+

public class ArbolesDemo {
    public static final String CYANBmas = "\u001B[36m";
    public static final String ROJOBmas = "\u001B[31m";
    public static final String RESETBmas = "\u001B[0m";

    public static void main(String[] args) {
        Scanner scannerBmas = new Scanner(System.in);

        int opcionOperacion, opcionRecorrido;

        ArbolBPlus arbolBmas = new ArbolBPlus(4);
        System.out.println(ROJOBmas + "Proyecto Segundo Bimestre");
        System.out.println("Bienvenido al programa de árboles" + RESETBmas);

        // Variable para controlar si el árbol ya ha sido inicializado
        boolean arbolInicializado = false;
        boolean salir = false;

        while (!salir) {
            try {
                System.out.println(CYANBmas + "\nSeleccione el tipo de árbol:" + RESETBmas);
                System.out.println("1. Árbol AVL (no implementado)");
                System.out.println("2. Árbol B ");
                System.out.println("3. Árbol B+");
                System.out.println("0. Salir");
                System.out.print("Ingrese el número del tipo de árbol: ");
                int tipoArbolBmas = Integer.parseInt(scannerBmas.nextLine());

                if (tipoArbolBmas == 0) {
                    salir = true;
                    continue;
                }
    
                if (tipoArbolBmas == 2) {
                    ArbolB arbol = new ArbolB();
                    arbol.generarArbolAutomático();
                    System.out.println("\nÁrbol B generado automáticamente:");
                    arbol.mostrarArbol();
    
                    while (true) {
                        System.out.println("\n--- Operaciones ---");
                        System.out.println("1. Búsqueda de la clave de un nodo");
                        System.out.println("2. Inserción de un nuevo nodo en el árbol");
                        System.out.println("3. Eliminación de un nodo del árbol");
                        System.out.println("4. Recorrido del árbol");
                        System.out.println("5. Volver al menú principal");
                        System.out.print("Seleccione una operación: ");
                        opcionOperacion = obtenerEnteroEnRango(scannerBmas, 1, 5);
    
                        if (opcionOperacion == 5) {
                            break;
                        }
    
                        switch (opcionOperacion ) {
                            case 1:
                                System.out.print("Ingrese la clave a buscar (1-100): ");
                                int claveBuscar = obtenerEnteroEnRango(scannerBmas, 1, 100);
                                arbol.buscarConColor(claveBuscar);
                                break;
                            case 2:
                                System.out.print("Ingrese la clave a insertar (1-100): ");
                                int claveInsertar = obtenerEnteroEnRango(scannerBmas, 1, 100);
                                arbol.insertarConExplicacion(claveInsertar);
                                break;
                            case 3:
                                System.out.print("Ingrese la clave a eliminar (1-100): ");
                                int claveEliminar = obtenerEnteroEnRango(scannerBmas, 1, 100);
                                arbol.eliminarConExplicacion(claveEliminar);
                                break;
                            case 4:
                                System.out.println("\n--- Recorridos ---");
                                System.out.println("1. Recorrido In-order");
                                System.out.println("2. Recorrido Pre-order");
                                System.out.println("3. Recorrido Post-order");
                                System.out.println("4. Recorrido Level-order");
                                System.out.print("Seleccione un recorrido: ");
                                opcionRecorrido = obtenerEnteroEnRango(scannerBmas, 1, 4);
                                arbol.recorrerConColor(opcionRecorrido);
                                break;
                            default:
                                System.out.println("Opción no válida.");
                        }
    
                        System.out.println("\nÁrbol B actualizado:");
                        arbol.mostrarArbol();
                    }
                } else {
                    System.out.println("Opción no implementada. Solo el Árbol B está disponible.");
                }

                if (tipoArbolBmas == 0) {
                    break;
                } else if (tipoArbolBmas == 3) {
                    while (true) {
                        System.out.println(CYANBmas + "\nMenu:" + RESETBmas);
                        System.out.println("1. Búsqueda de la clave de un nodo");
                        System.out.println("2. Inserción de un nuevo nodo en el árbol");
                        System.out.println("3. Eliminación de un nodo en el árbol (no implementado)");
                        System.out.println("4. Recorrido del árbol");
                        System.out.println("0. Volver al menú principal");
                        System.out.print("Ingrese el número de la operación: ");
                        int operacionBmas = Integer.parseInt(scannerBmas.nextLine());

                        if (operacionBmas == 0) {
                            break;
                        } else if (operacionBmas == 1) {
                            if (!arbolInicializado) {
                                // Generar claves aleatorias solo una vez
                                for (int iBmas = 0; iBmas < 15; iBmas++) {
                                    arbolBmas.insertarBmas((int) (Math.random() * 100));
                                }
                                System.out.println("Árbol B+ generado");
                                arbolBmas.imprimirArbolBmas(0, -1);
                                arbolInicializado = true; // Marcar el árbol como inicializado
                            }
                            System.out.print("Ingrese la clave a buscar: ");
                            int claveBuscadaBmas = Integer.parseInt(scannerBmas.nextLine());
                            arbolBmas.buscarBmas(claveBuscadaBmas);
                            System.out.println(CYANBmas + "---------------------------------------------------------------------------------------------------------" + RESETBmas);
                        } else if (operacionBmas == 2) {
                            System.out.println(CYANBmas + "---------------------------------------------------------------------------------------------------------" + RESETBmas);
                            System.out.print("Ingrese la clave a insertar: ");
                            int claveInsertarBmas = Integer.parseInt(scannerBmas.nextLine());
                            arbolBmas.insertarBmas(claveInsertarBmas);
                        } else if (operacionBmas == 3) {
                            System.out.println(CYANBmas + "---------------------------------------------------------------------------------------------------------" + RESETBmas);
                            // Lógica de eliminación no implementada
                        } else if (operacionBmas == 4) {
                            System.out.println(CYANBmas + "---------------------------------------------------------------------------------------------------------" + RESETBmas);
                            Scanner scanner1 = new Scanner(System.in);
                            int opcion = 0;

                            while (true) {
                                System.out.println(CYANBmas + "\nMenú de Recorridos" + RESETBmas);
                                System.out.println("1. Recorrido en preorden");
                                System.out.println("2. Recorrido en inorden");
                                System.out.println("3. Recorrido en postorden");
                                System.out.println("4. Recorrido en anchura");
                                System.out.println("5. Salir");
                                System.out.print("Seleccione una opción: ");

                                try {
                                    opcion = Integer.parseInt(scanner1.nextLine());

                                    switch (opcion) {
                                        case 1:
                                            System.out.println("Ejecutando recorrido en preorden...");
                                            System.out.println(arbolBmas.recorridoPreorden(arbolBmas.raizBmas));
                                            break;
                                        case 2:
                                            System.out.println("Ejecutando recorrido en inorden...");
                                            System.out.println(arbolBmas.recorridoInorden(arbolBmas.raizBmas));
                                            break;
                                        case 3:
                                            System.out.println("Ejecutando recorrido en postorden...");
                                            System.out.println(arbolBmas.recorridoPostorden(arbolBmas.raizBmas));
                                            break;
                                        case 4:
                                            System.out.println("Ejecutando recorrido en anchura...");
                                            System.out.println(arbolBmas.recorridoAnchura());
                                            break;
                                        case 5:
                                            System.out.println("Saliendo del menú de recorridos...");
                                            return;
                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Ingrese un número válido.");
                                } catch (Exception e) {
                                    System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println(ROJOBmas + "Opción no válida." + RESETBmas);
                        }
                    }
                } else {
                    System.out.println(ROJOBmas + "Opción no válida." + RESETBmas);
                }
            } catch (NumberFormatException e) {
                System.out.println(ROJOBmas + "Entrada no válida. Por favor, ingrese un número." + RESETBmas);
            }
        }

        scannerBmas.close();
    }
    private static int obtenerEnteroEnRango(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int valor = scanner.nextInt();
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.print("Por favor, ingrese un número entre " + min + " y " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Entrada no válida. Por favor, ingrese un número: ");
                scanner.next(); // Limpiar el buffer del scanner
            }
        }
    }
}

class ArbolB implements Arbol {
    private List<Integer> claves = new ArrayList<>();
    private Random random = new Random();

    public void generarArbolAutomático() {
        int numClaves = random.nextInt(9) + 7; // Entre 7 y 15 claves
        System.out.println("\nGenerando árbol automáticamente con " + numClaves + " claves...");
        for (int i = 0; i < numClaves; i++) {
            int clave = random.nextInt(100) + 1;
            insertar(clave);
        }
    }

    @Override
    public void insertar(int clave) {
        claves.add(clave);
        Collections.sort(claves);
    }

    public void insertarConExplicacion(int clave) {
        System.out.println("\nIniciando inserción de la clave " + clave + "...");
        System.out.println("Paso 1: Verificar si la clave ya existe en el árbol.");
        if (claves.contains(clave)) {
            System.out.println("La clave " + clave + " ya existe en el árbol. No se insertará.");
            return;
        }

        System.out.println("Paso 2: Insertar la clave " + clave + " en el árbol.");
        System.out.println("Árbol antes de la inserción:");
        mostrarArbol();

        claves.add(clave);
        Collections.sort(claves);

        System.out.println("\nPaso 3: Reorganizar el árbol para mantener el orden.");
        System.out.println("Árbol después de la inserción:");
        mostrarArbol();

        System.out.println("Clave " + clave + " insertada correctamente.");
    }

    @Override
    public boolean buscar(int clave) {
        for (int i = 0; i < claves.size(); i++) {
            System.out.println("Comparando con la clave: " + claves.get(i));
            if (claves.get(i).equals(clave)) {
                pintarNodoEnArbol(i); // Resaltar el nodo encontrado
                return true;
            }
            pintarNodoEnArbol(i); // Resaltar el nodo actual
        }
        return false;
    }

    public void buscarConColor(int clave) {
        System.out.println("\nIniciando búsqueda de la clave " + clave + "...");
        boolean encontrado = buscar(clave);
        if (encontrado) {
            System.out.println("La clave " + clave + " está presente en el árbol.");
        } else {
            System.out.println("No existe en el árbol, un nodo con la clave " + clave + ".");
        }
    }

    @Override
    public boolean eliminar(int clave) {
        if (!claves.contains(clave)) {
            System.out.println("No existe en el árbol, un nodo con la clave " + clave + ".");
            return false;
        }

        System.out.println("Eliminando clave " + clave + " del árbol B.");
        eliminarClave(clave);
        //verificarCondiciones();
        mostrarArbol();
        return true;
    }

    public void eliminarConExplicacion(int clave) {
        System.out.println("\nIniciando eliminación de la clave " + clave + "...");
        System.out.println("Paso 1: Verificar si la clave existe en el árbol.");
        if (!claves.contains(clave)) {
            System.out.println("La clave " + clave + " no existe en el árbol. No se eliminará.");
            return;
        }

        System.out.println("Paso 2: Eliminar la clave " + clave + " del árbol.");
        System.out.println("Árbol antes de la eliminación:");
        mostrarArbol();

        claves.remove(Integer.valueOf(clave));
        Collections.sort(claves);

        System.out.println("\nPaso 3: Reorganizar el árbol para mantener el orden.");
        System.out.println("Árbol después de la eliminación:");
        mostrarArbol();

        System.out.println("Clave " + clave + " eliminada correctamente.");
        System.out.println("Generando matriz de adyacencia...");
        generarMatrizAdyacencia();
    }

    private void eliminarClave(int clave) {
        claves.remove(Integer.valueOf(clave));
        Collections.sort(claves);
        System.out.println("Clave " + clave + " eliminada.");
    }

    private void generarMatrizAdyacencia() {
        int n = claves.size();
        int[][] matriz = new int[n][n];

        for (int i = 0; i < n; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;

            if (leftChild < n) {
                matriz[i][leftChild] = 1;
            }
            if (rightChild < n) {
                matriz[i][rightChild] = 1;
            }
        }

        System.out.println("\nMatriz de adyacencia:");
        System.out.println("Nota: Los '1's indican una conexión entre nodos.");
        System.out.print("     ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-5d", claves.get(i));
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf("%-5d", claves.get(i));
            for (int j = 0; j < n; j++) {
                System.out.printf("%-5d", matriz[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public void recorrerConColor(int tipoRecorrido) {
        List<Integer> recorrido = new ArrayList<>();
        switch (tipoRecorrido) {
            case 1:
                System.out.println("\nRecorrido In-order:");
                recorrerInOrder(0, recorrido);
                break;
            case 2:
                System.out.println("\nRecorrido Pre-order:");
                recorrerPreOrder(0, recorrido);
                break;
            case 3:
                System.out.println("\nRecorrido Post-order:");
                recorrerPostOrder(0, recorrido);
                break;
            case 4:
                System.out.println("\nRecorrido Level-order:");
                recorrerLevelOrder(recorrido);
                break;
            default:
                System.out.println("Opción de recorrido no válida.");
                return;
        }
        System.out.println("Recorrido completado: " + recorrido);
    }

    private void recorrerInOrder(int indice, List<Integer> recorrido) {
        if (indice < claves.size()) {
            recorrerInOrder(2 * indice + 1, recorrido);
            pintarNodoEnArbol(indice);
            recorrido.add(claves.get(indice));
            recorrerInOrder(2 * indice + 2, recorrido);
        }
    }

    private void recorrerPreOrder(int indice, List<Integer> recorrido) {
        if (indice < claves.size()) {
            pintarNodoEnArbol(indice);
            recorrido.add(claves.get(indice));
            recorrerPreOrder(2 * indice + 1, recorrido);
            recorrerPreOrder(2 * indice + 2, recorrido);
        }
    }

    private void recorrerPostOrder(int indice, List<Integer> recorrido) {
        if (indice < claves.size()) {
            recorrerPostOrder(2 * indice + 1, recorrido);
            recorrerPostOrder(2 * indice + 2, recorrido);
            pintarNodoEnArbol(indice);
            recorrido.add(claves.get(indice));
        }
    }

    private void recorrerLevelOrder(List<Integer> recorrido) {
        for (int i = 0; i < claves.size(); i++) {
            pintarNodoEnArbol(i);
            recorrido.add(claves.get(i));
        }
    }

    private void pintarNodoEnArbol(int indice) {
        mostrarArbol(indice);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mostrarArbol() {
        mostrarArbol(-1);
    }

    public void mostrarArbol(int nodoResaltado) {
        if (claves.isEmpty()) {
            System.out.println("Árbol vacío.");
            return;
        }

        System.out.println("\nÁrbol B con altura 3:");

        int anchoMaximo = 57;
        int espaciadoNivel1 = anchoMaximo / 2;
        int espaciadoNivel2 = anchoMaximo / 3;
        int espaciadoNivel3 = anchoMaximo / 5;

        // Raíz
        imprimirNodo(nodoResaltado, 0, espaciadoNivel1);
        System.out.println();
        System.out.printf("%" + espaciadoNivel1 / 2 + "s%s\n", " ", "    /            \\");

        for (int i = 1; i <= 2; i++) {
            imprimirNodo(nodoResaltado, i, espaciadoNivel2);
        }
        System.out.println();
        System.out.println("            /    \\               /   \\");

        for (int i = 3; i <= 6; i++) {
            imprimirNodo(nodoResaltado, i, espaciadoNivel3);
        }
        System.out.println();
        System.out.println("       / \\        / \\        / \\        / \\");

        for (int i = 7; i < 15 && i < claves.size(); i++) {
            imprimirNodo(nodoResaltado, i, 5);
        }
        System.out.println();
    }

    private void imprimirNodo(int nodoResaltado, int indice, int espaciado) {
        if (indice < claves.size()) {
            if (indice == nodoResaltado) {
                System.out.printf("%" + espaciado + "s", "\u001B[31m| " + claves.get(indice) + " |\u001B[0m"); // Resaltar en rojo
            } else {
                System.out.printf("%" + espaciado + "s", "| " + claves.get(indice) + " |");
            }
        }
    }
}

interface Arbol {
    void insertar(int clave);
    boolean buscar(int clave);
    void buscarConColor(int clave);
    boolean eliminar(int clave);
    void recorrerConColor(int tipoRecorrido);
    void mostrarArbol();
}

