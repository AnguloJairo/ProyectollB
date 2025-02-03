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
    private NodoBPlus raizBmas;
    private int gradoBmas;

    public ArbolBPlus(int gradoBmas) {
        this.gradoBmas = gradoBmas;
        this.raizBmas = new NodoBPlus(gradoBmas);
    }
    public static final String ROJOBmas = "\u001B[31m";
    public void insertarBmas(int claveBmas) {
        // Verificar si la clave está dentro del rango permitido
        if (claveBmas < 1 || claveBmas > 100) {
            System.out.println(ROJOBmas + "La clave debe estar entre 1 y 100." + RESETBmas);
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
            System.out.println("Clave " + claveBmas + " insertada en el nodo hoja.");
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

    public void buscarBmas(int claveBmas) {
        buscarEnNodoBmas(raizBmas, claveBmas, "");
    }

    public static final String VERDEBmas = "\u001B[32m";
    public static final String AMARILLOBmas = "\u001B[33m";
    public static final String CYANBmas = "\u001B[36m";
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
        System.out.println(CYANBmas + "----------------------------------------------------------------------------------------------------------" + RESETBmas);
        System.out.println(VERDEBmas + "Recorrido actual del árbol" + RESETBmas);
        imprimirArbolBmas(claveBmas, iBmas); // Pasar la clave buscada y el índice
    
        // Mostrar el mensaje de verificación después de imprimir el árbol
        if (iBmas < nodoBmas.clavesBmas.size()) {
            System.out.println("Verificando clave " + AMARILLOBmas + "[" + nodoBmas.clavesBmas.get(iBmas) + "]" + RESETBmas + ": dirección " + (claveBmas > nodoBmas.clavesBmas.get(iBmas) ? "derecha" : "izquierda"));
        } else {
            System.out.println("Verificando clave " + AMARILLOBmas + "[Ninguna]" + RESETBmas + ": dirección izquierda");
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
                System.out.print("                 "); // 5 espacios para el nivel 0
            } else if (nivelBmas == 1) {
                System.out.print("              "); // 3 espacios para el nivel 1
            } else if (nivelBmas == 2) {
                System.out.print("          "); // 3 espacios para el nivel 1
            } else if (nivelBmas == 3) {
                System.out.print(" "); // 3 espacios para el nivel 1
            }
    
            for (int iBmas = 0; iBmas < levelSizeBmas; iBmas++) {
                NodoBPlus nodoBmas = queueBmas.poll();
                for (int jBmas = 0; jBmas < nodoBmas.clavesBmas.size(); jBmas++) {
                    Integer claveBmas = nodoBmas.clavesBmas.get(jBmas);
                    // Resaltar las claves que se están verificando
                    if (jBmas == indiceVerificacionBmas) {
                        System.out.print(VERDEBmas + "[" + claveBmas + "]" + RESETBmas + " "); // Colorear la clave que se está verificando
                    } else {
                        System.out.print("[" + claveBmas + "] "); // Agregar corchetes a las claves
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
    
}    

//FIN Bùsqueda B+

public class ArbolesDemo {
    
    public static final String CYANBmas = "\u001B[36m";
    public static final String ROJOBmas = "\u001B[31m";
    public static final String RESETBmas = "\u001B[0m";
    
    public static void main(String[] args) {
        Scanner scannerBmas = new Scanner(System.in);

        ArbolBPlus arbolBmas = new ArbolBPlus(4);
        System.out.println(ROJOBmas + "Proyecto Segundo Bimestre");
        System.out.println("Bienvenido al programa de árboles" + RESETBmas);
        
        while (true) {
            try {
                System.out.println(CYANBmas +"\nSeleccione el tipo de árbol:" + RESETBmas);
                System.out.println("1. Árbol AVL (no implementado)");
                System.out.println("2. Árbol B (no implementado)");
                System.out.println("3. Árbol B+");
                System.out.println("0. Salir");
                System.out.print("Ingrese el número del tipo de árbol: ");
                int tipoArbolBmas = Integer.parseInt(scannerBmas.nextLine());

                if (tipoArbolBmas == 0) {
                    break;
                } else if (tipoArbolBmas == 3) {
                    while (true) {
                        System.out.println(CYANBmas + "\nMenu:" + RESETBmas);
                        System.out.println("1. Búsqueda de la clave de un nodo");
                        System.out.println("2. Inserción de un nuevo nodo en el arbol");
                        System.out.println("3. Eliminación de un nodo en el arbol (no implementado)");
                        System.out.println("4. Recorrido del arbol (no implementado)");
                        System.out.println("0. Volver al menú principal");
                        System.out.print("Ingrese el número de la operación: ");
                        int operacionBmas = Integer.parseInt(scannerBmas.nextLine());

                        if (operacionBmas == 0) {
                            break;
                        } else if (operacionBmas == 1) {
                            System.out.println(CYANBmas + "----------------------------------------------------------------------------------------------------------" + RESETBmas);
                            for (int iBmas = 0; iBmas < 15; iBmas++) {
                                arbolBmas.insertarBmas((int) (Math.random() * 100));
                            }
                            System.out.println("Arbol B+ generado");
                            arbolBmas.imprimirArbolBmas(0, -1);
                            System.out.print("Ingrese la clave a buscar: ");
                            int claveBuscadaBmas = Integer.parseInt(scannerBmas.nextLine());
                            arbolBmas.buscarBmas(claveBuscadaBmas);
                            System.out.println(CYANBmas + "----------------------------------------------------------------------------------------------------------" + RESETBmas);
                            

                        } else if (operacionBmas == 2) {
                            for (int iBmas = 0; iBmas < 15; iBmas++) {
                                arbolBmas.insertarBmas((int) (Math.random() * 100));
                            }
                            System.out.println("Arbol B+ generado");
                            arbolBmas.imprimirArbolBmas(0, -1);
                            System.out.print("Ingrese la clave a insertar: ");
                            int claveInsertarBmas = Integer.parseInt(scannerBmas.nextLine());
                            arbolBmas.insertarBmas(claveInsertarBmas);
                        } else if (operacionBmas == 4) {
                            System.out.println(CYANBmas + "----------------------------------------------" + RESETBmas);
                            while (true) {
                                System.out.println(CYANBmas + "\nMenú de Recorridos: " + RESETBmas);
                                System.out.println("1. Recorrido In-order");
                                System.out.println("2. Recorrido Pre-order");
                                System.out.println("3. Recorrido Post-order");
                                System.out.println("4. Recorrido Level-order");
                                System.out.println("5. Volver al menú principal");
                                System.out.print("Seleccione un recorrido: ");
                                
                                try {
                                    int recorridoBmas = scannerBmas.nextInt();
                                    
                                    switch (recorridoBmas) {
                                        case 1:
                                            break;
                                        case 2:
                                            break;
                                        case 3:
                                            break;
                                        case 4:
                                            break;
                                        case 5:
                                            System.out.println("Volviendo al menú principal...");
                                            break;
                                        default:
                                            System.out.println("Opción no válida.");
                                    }
                                } catch (Exception e) {
                                    System.out.println(ROJOBmas + "Entrada no válida. Por favor, ingrese un número." + RESETBmas);
                                    scannerBmas.next(); // Limpiar el buffer
                                }
                                //if (recorridoBmas == 5) break;
                            }
                            //break;
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
}
