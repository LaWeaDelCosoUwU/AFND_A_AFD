package laweadelcoso.proyectofinalely;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class AFND {
    
    private Set<String> estados;
    private Set<Character> alfabeto;
    private Map<String, Map<Character, Set<String>>> transiciones;
    private Map<String, Set<String>> transicionesEpsilon;
    private String estadoInicial;
    private Set<String> estadosAceptacion;

    //Inicializa los conjuntos de estados, alfabeto y estados de aceptación, así como los mapas de transiciones y transiciones épsilon. 
    //Además, establece el estado inicial del AFND como una cadena vacía.
    public AFND() {
        
        estados = new HashSet<>();
        alfabeto = new HashSet<>();
        transiciones = new HashMap<>();
        transicionesEpsilon = new HashMap<>();
        estadoInicial = "";
        estadosAceptacion = new HashSet<>();
        
    }
    
    //Este método recibe como parámetro un estado y lo agrega al conjunto de estados del AFND.
    public void agregarEstado(String estado) {
        estados.add(estado);
    }

    //Este método recibe como parámetro un símbolo y lo agrega al conjunto de caracteres del alfabeto del AFND.
    public void agregarSimboloAlfabeto(char simbolo) {
        alfabeto.add(simbolo);
    }

    //Este método agrega una transición al mapa de transiciones del AFND. Recibe como parámetros el estado actual, el símbolo de entrada y el estado destino. 
    //Para hacerlo, obtiene las transiciones asociadas al estado actual y agrega el estado destino al conjunto de estados alcanzables con el símbolo de entrada correspondiente.
    public void agregarTransicion(String estadoActual, char simbolo, String estadoDestino) {
        
        Map<Character, Set<String>> transicionesEstadoActual = transiciones.getOrDefault(estadoActual, new HashMap<>());
        Set<String> transicionesSimbolo = transicionesEstadoActual.getOrDefault(simbolo, new HashSet<>());
        transicionesSimbolo.add(estadoDestino);
        transicionesEstadoActual.put(simbolo, transicionesSimbolo);
        transiciones.put(estadoActual, transicionesEstadoActual);
        
    }

    //Este método agrega una transición épsilon al mapa de transiciones épsilon del AFND. Recibe como parámetros el estado actual y el estado destino. 
    //Para hacerlo, obtiene las transiciones épsilon asociadas al estado actual y agrega el estado destino al conjunto de estados alcanzables mediante una transición épsilon.
    public void agregarTransicionEpsilon(String estadoActual, String estadoDestino) {
        
        Set<String> transicionesEpsilonEstadoActual = transicionesEpsilon.getOrDefault(estadoActual, new HashSet<>());
        transicionesEpsilonEstadoActual.add(estadoDestino);
        transicionesEpsilon.put(estadoActual, transicionesEpsilonEstadoActual);
        
    }

    //Este método establece el estado inicial del AFND. Recibe como parámetro el estado inicial y lo asigna a la variable correspondiente.
    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    // Este método recibe como parámetro un estado de aceptación y lo agrega al conjunto de estados de aceptación del AFND.
    public void agregarEstadoAceptacion(String estadoAceptacion) {
        estadosAceptacion.add(estadoAceptacion);
    }

    //Este método devuelve el conjunto de estados del AFND.
    public Set<String> getEstados() {
        return estados;
    }

    // Este método devuelve el conjunto de caracteres que conforman el alfabeto del AFND.
    public Set<Character> getAlfabeto() {
        return alfabeto;
    }

    //Este método devuelve el conjunto de estados alcanzables a partir de un estado actual y un símbolo de entrada. 
    //Primero, obtiene las transiciones asociadas al estado actual y luego busca los estados alcanzables correspondientes al símbolo de entrada en el mapa de transiciones.
    public Set<String> getTransiciones(String estadoActual, char simbolo) {
        
        Map<Character, Set<String>> transicionesEstadoActual = transiciones.get(estadoActual);
        
        if (transicionesEstadoActual != null) {
            return transicionesEstadoActual.getOrDefault(simbolo, Collections.emptySet());
        }
        return Collections.emptySet();
        
    }

    //Este método devuelve el conjunto de estados alcanzables mediante transiciones épsilon a partir de un estado actual.
    //Obtiene las transiciones épsilon asociadas al estado actual en el mapa de transiciones épsilon.
    public Set<String> getTransicionesEpsilon(String estadoActual) {
        return transicionesEpsilon.getOrDefault(estadoActual, Collections.emptySet());
    }

    //Este método devuelve el estado inicial del AFND.
    public String getEstadoInicial() {
        return estadoInicial;
    }

    //Este método devuelve el conjunto de estados de aceptación del AFND.
    public Set<String> getEstadosAceptacion() {
        return estadosAceptacion;
    }
    
}