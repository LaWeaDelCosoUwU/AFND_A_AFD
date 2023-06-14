package laweadelcoso.proyectofinalely;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class ProyectoFinalEly {
    
    public static void main(String[] args) {
        
        // Crear un AFND a partir de la entrada del usuario
        AFND afnd = crearAFNDDesdeEntradaUsuario();
        
        // Convertir el AFND a un AFD
        AFD afd = convertirAFNDaAFD(afnd);
        
        // Imprimir el AFD resultante
        System.out.println("AFD resultante:");
        imprimirAFD(afd);
        
    }

    // Método para crear un AFND a partir de la entrada del usuario
    private static AFND crearAFNDDesdeEntradaUsuario() {
        
        AFND afnd = new AFND();
        Scanner scanner = new Scanner(System.in);

        // Ingreso de estados
        System.out.println("Ingrese los estados (uno por línea, escriba 'fin' para terminar):");
        String estado;
        
        while (!(estado = scanner.nextLine()).equalsIgnoreCase("fin")) {
            afnd.agregarEstado(estado);
        }

        // Ingreso del alfabeto
        System.out.println("Ingrese los símbolos del alfabeto (uno por línea, escriba 'fin' para terminar):");
        String simbolo;
        while (!(simbolo = scanner.nextLine()).equalsIgnoreCase("fin")) {
            
            if (simbolo.length() == 1) {
                afnd.agregarSimboloAlfabeto(simbolo.charAt(0));
            } else {
                System.out.println("Ingrese un único símbolo.");
            }
            
        }

        // Ingreso de transiciones
        System.out.println("Ingrese las transiciones (estadoActual, símbolo, estadoDestino) separadas por comas (una por línea, escriba 'fin' para terminar):");
        String transicion;
        while (!(transicion = scanner.nextLine()).equalsIgnoreCase("fin")) {
            
            String[] partes = transicion.split(",");
            if (partes.length == 3) {
                String estadoActual = partes[0].trim();
                char simb = partes[1].trim().charAt(0);
                String estadoDestino = partes[2].trim();
                afnd.agregarTransicion(estadoActual, simb, estadoDestino);
            } else {
                System.out.println("Ingrese una transición válida.");
            }
            
        }

        // Ingreso de transiciones épsilon
        System.out.println("Ingrese las transiciones épsilon (estadoActual, estadoDestino) separadas por comas (una por línea, escriba 'fin' para terminar):");
        String transicionEpsilon;
        
        while (!(transicionEpsilon = scanner.nextLine()).equalsIgnoreCase("fin")) {
            
            String[] partes = transicionEpsilon.split(",");
            if (partes.length == 2) {
                String estadoActual = partes[0].trim();
                String estadoDestino = partes[1].trim();
                afnd.agregarTransicionEpsilon(estadoActual, estadoDestino);
            } else {
                System.out.println("Ingrese una transición épsilon válida.");
            }
            
        }

        // Ingreso del estado inicial
        System.out.println("Ingrese el estado inicial:");
        String estadoInicial = scanner.nextLine();
        afnd.setEstadoInicial(estadoInicial);

        // Ingreso de estados de aceptación
        System.out.println("Ingrese los estados de aceptación (uno por línea, escriba 'fin' para terminar):");
        String estadoAceptacion;
        
        while (!(estadoAceptacion = scanner.nextLine()).equalsIgnoreCase("fin")) {
            afnd.agregarEstadoAceptacion(estadoAceptacion);
        }

        return afnd;
        
    }

    // Método para convertir un AFND a un AFD
    private static AFD convertirAFNDaAFD(AFND afnd) {
        
        // Conjunto de conjuntos de estados del AFD
        Set<Set<String>> estadosAFD = new HashSet<>();
        
        // Cola para realizar el procesamiento de los estados del AFD
        Queue<Set<String>> cola = new LinkedList<>();
        
        // Cálculo de la cerradura épsilon del estado inicial del AFND
        Set<String> estadoInicial = cerraduraEpsilon(afnd.getEstadoInicial(), afnd);
        estadosAFD.add(estadoInicial);
        cola.add(estadoInicial);
        
        // Creación del AFD
        AFD afd = new AFD();

        // Cálculo de estados y transiciones nuevas del AFD
        while (!cola.isEmpty()) {
            
            Set<String> estadoActual = cola.poll();
            
            // Agregar el estado al AFD
            afd.agregarEstado(convertirConjuntoAString(estadoActual));

            for (char simbolo : afnd.getAlfabeto()) {
                
                // Cálculo de la cerradura épsilon del conjunto de estados destino
                Set<String> estadoDestino = cerraduraEpsilon(mover(estadoActual, simbolo, afnd), afnd);
                
                // Si el conjunto de estados destino no ha sido procesado, se agrega a la cola
                if (!estadosAFD.contains(estadoDestino)) {
                    estadosAFD.add(estadoDestino);
                    cola.add(estadoDestino);
                }
                
                // Agregar la transición al AFD
                afd.agregarTransicion(convertirConjuntoAString(estadoActual), simbolo, convertirConjuntoAString(estadoDestino));
            }
            
        }
        
        // Añadir el alfabeto al AFD
        for (char simbolo : afnd.getAlfabeto()) {
            afd.agregarSimboloAlfabeto(simbolo);
        }
        
        // Añadir el estado inicial al AFD
        afd.setEstadoInicial(convertirConjuntoAString(cerraduraEpsilon(afnd.getEstadoInicial(), afnd)));

        // Añadir los estados de aceptación al AFD
        for (Set<String> estadoAFD : estadosAFD) {
            for (String estadoAceptacion : afnd.getEstadosAceptacion()) {
                if (estadoAFD.contains(estadoAceptacion)) {
                    afd.agregarEstadoAceptacion(convertirConjuntoAString(estadoAFD));
                    break;
                }
            }
        }
        
        return afd;
        
    }

    // Método para calcular la cerradura épsilon de un estado en el AFND
    private static Set<String> cerraduraEpsilon(String estado, AFND afnd) {
        
        Set<String> conjuntoCerradura = new HashSet<>();
        Stack<String> pila = new Stack<>();
        pila.push(estado);

        while (!pila.isEmpty()) {
            
            String estadoActual = pila.pop();
            
            // Agregar el estado a la cerradura épsilon
            conjuntoCerradura.add(estadoActual);
            
            // Obtener los estados destino de las transiciones épsilon
            Set<String> estadosDestino = afnd.getTransicionesEpsilon(estadoActual);
            
            // Agregar los estados destino a la pila si no han sido visitados
            for (String estadoDestino : estadosDestino) {
                if (!conjuntoCerradura.contains(estadoDestino)) {
                    pila.push(estadoDestino);
                }
            }
            
        }

        return conjuntoCerradura;
        
    }

    // Método para calcular la cerradura épsilon de un conjunto de estados en el AFND
    private static Set<String> cerraduraEpsilon(Set<String> estados, AFND afnd) {
        
        Set<String> conjuntoCerradura = new HashSet<>();
        
        for (String estado : estados) {
            conjuntoCerradura.addAll(cerraduraEpsilon(estado, afnd));
        }
        
        return conjuntoCerradura;
        
    }

    // Método para calcular el conjunto de estados destino a partir de un conjunto de estados y un símbolo en el AFND
    private static Set<String> mover(Set<String> estados, char simbolo, AFND afnd) {
        
        Set<String> conjuntoDestino = new HashSet<>();
        
        for (String estado : estados) {
            conjuntoDestino.addAll(afnd.getTransiciones(estado, simbolo));
        }
        
        return conjuntoDestino;
        
    }

    // Método para convertir un conjunto de estados en una cadena de texto
    private static String convertirConjuntoAString(Set<String> conjunto) {
        
        StringBuilder sb = new StringBuilder();
        
        for (String elemento : conjunto) {
            sb.append(elemento).append(",");
        }
        
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        
        return sb.toString();
        
    }

    // Método para imprimir la representación del AFD
    private static void imprimirAFD(AFD afd) {
        
        System.out.println("Estados:");
        for (String estado : afd.getEstados()) {  
            if(!estado.isBlank())
                System.out.println(estado);
        }

        System.out.println("Alfabeto:");
        for (char simbolo : afd.getAlfabeto()) {
            System.out.println(simbolo);
        }

        System.out.println("Transiciones:");
        for (String estadoActual : afd.getTransiciones().keySet()) {
            
            Map<Character, String> transicionesEstado = afd.getTransiciones().get(estadoActual);
            
            for (char simbolo : transicionesEstado.keySet()) {
                
                String estadoDestino = transicionesEstado.get(simbolo);
                if(!estadoDestino.isBlank())
                    System.out.println("(" + estadoActual + ", " + simbolo + ") -> " + estadoDestino);
                
            }
            
        }

        System.out.println("Estado inicial: " + afd.getEstadoInicial());

        System.out.println("Estados de aceptación:");
        for (String estadoAceptacion : afd.getEstadosAceptacion()) {
            System.out.println(estadoAceptacion);
        }
        
    }
    
}