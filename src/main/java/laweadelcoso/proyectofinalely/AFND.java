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

    public AFND() {
        
        estados = new HashSet<>();
        alfabeto = new HashSet<>();
        transiciones = new HashMap<>();
        transicionesEpsilon = new HashMap<>();
        estadoInicial = "";
        estadosAceptacion = new HashSet<>();
        
    }

    public void agregarEstado(String estado) {
        estados.add(estado);
    }

    public void agregarSimboloAlfabeto(char simbolo) {
        alfabeto.add(simbolo);
    }

    public void agregarTransicion(String estadoActual, char simbolo, String estadoDestino) {
        
        Map<Character, Set<String>> transicionesEstadoActual = transiciones.getOrDefault(estadoActual, new HashMap<>());
        Set<String> transicionesSimbolo = transicionesEstadoActual.getOrDefault(simbolo, new HashSet<>());
        transicionesSimbolo.add(estadoDestino);
        transicionesEstadoActual.put(simbolo, transicionesSimbolo);
        transiciones.put(estadoActual, transicionesEstadoActual);
        
    }

    public void agregarTransicionEpsilon(String estadoActual, String estadoDestino) {
        
        Set<String> transicionesEpsilonEstadoActual = transicionesEpsilon.getOrDefault(estadoActual, new HashSet<>());
        transicionesEpsilonEstadoActual.add(estadoDestino);
        transicionesEpsilon.put(estadoActual, transicionesEpsilonEstadoActual);
        
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public void agregarEstadoAceptacion(String estadoAceptacion) {
        estadosAceptacion.add(estadoAceptacion);
    }

    public Set<String> getEstados() {
        return estados;
    }

    public Set<Character> getAlfabeto() {
        return alfabeto;
    }

    public Set<String> getTransiciones(String estadoActual, char simbolo) {
        
        Map<Character, Set<String>> transicionesEstadoActual = transiciones.get(estadoActual);
        
        if (transicionesEstadoActual != null) {
            return transicionesEstadoActual.getOrDefault(simbolo, Collections.emptySet());
        }
        return Collections.emptySet();
        
    }

    public Set<String> getTransicionesEpsilon(String estadoActual) {
        return transicionesEpsilon.getOrDefault(estadoActual, Collections.emptySet());
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public Set<String> getEstadosAceptacion() {
        return estadosAceptacion;
    }
}