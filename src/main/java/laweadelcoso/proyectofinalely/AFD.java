package laweadelcoso.proyectofinalely;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AFD {
    
    private Set<String> estados;
    private Set<Character> alfabeto;
    private Map<String, Map<Character, String>> transiciones;
    private String estadoInicial;
    private Set<String> estadosAceptacion;

    //Inicializa los conjuntos de estados, alfabeto y estados de aceptación, así como el mapa de transiciones. 
    //Además, establece el estado inicial del AFD como una cadena vacía.
    public AFD() {
        
        estados = new HashSet<>();
        alfabeto = new HashSet<>();
        transiciones = new HashMap<>();
        estadoInicial = "";
        estadosAceptacion = new HashSet<>();
        
    }

    //Este método recibe como parámetro un estado y lo agrega al conjunto de estados del AFD.
    public void agregarEstado(String estado) {
        estados.add(estado);
    }

    //Este método recibe como parámetro un símbolo y lo agrega al conjunto de caracteres del alfabeto del AFD.
    public void agregarSimboloAlfabeto(char simbolo) {
        alfabeto.add(simbolo);
    }
    
    //Este método devuelve el mapa de transiciones del AFD, el cual representa las transiciones posibles entre los estados del AFD en función de los símbolos del alfabeto.
    public Map<String, Map<Character, String>> getTransiciones() {
        return transiciones;
    }

    //Este método agrega una transición al mapa de transiciones del AFD. Recibe como parámetros el estado actual, el símbolo de entrada y el estado destino.
    //Para hacerlo, obtiene las transiciones asociadas al estado actual y agrega la transición con el símbolo y el estado destino correspondientes.
    public void agregarTransicion(String estadoActual, char simbolo, String estadoDestino) {
        
        Map<Character, String> transicionesEstadoActual = transiciones.getOrDefault(estadoActual, new HashMap<>());
        transicionesEstadoActual.put(simbolo, estadoDestino);
        transiciones.put(estadoActual, transicionesEstadoActual);
        
    }

    //Este método establece el estado inicial del AFD. Recibe como parámetro el estado inicial y lo asigna a la variable correspondiente.
    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    // Este método recibe como parámetro un estado de aceptación y lo agrega al conjunto de estados de aceptación del AFD.
    public void agregarEstadoAceptacion(String estadoAceptacion) {
        estadosAceptacion.add(estadoAceptacion);
    }

    //Este método devuelve el conjunto de estados del AFD.
    public Set<String> getEstados() {
        return estados;
    }

    //Este método devuelve el conjunto de caracteres que conforman el alfabeto del AFD.
    public Set<Character> getAlfabeto() {
        return alfabeto;
    }

    //Este método devuelve el estado destino de una transición a partir del estado actual y el símbolo de entrada.
    //Primero, obtiene las transiciones asociadas al estado actual y luego busca el estado destino correspondiente al símbolo de entrada en el mapa de transiciones.
    public String getTransicion(String estadoActual, char simbolo) {
        
        Map<Character, String> transicionesEstadoActual = transiciones.get(estadoActual);
        if (transicionesEstadoActual != null) {
            return transicionesEstadoActual.get(simbolo);
        }
        
        return null;
        
    }

    //Este método devuelve el estado inicial del AFD.
    public String getEstadoInicial() {
        return estadoInicial;
    }

    //Este método devuelve el conjunto de estados de aceptación del AFD.
    public Set<String> getEstadosAceptacion() {
        return estadosAceptacion;
    }
    
}