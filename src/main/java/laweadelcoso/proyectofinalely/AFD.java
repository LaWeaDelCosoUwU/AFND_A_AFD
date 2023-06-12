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

    public AFD() {
        estados = new HashSet<>();
        alfabeto = new HashSet<>();
        transiciones = new HashMap<>();
        estadoInicial = "";
        estadosAceptacion = new HashSet<>();
    }

    public void agregarEstado(String estado) {
        estados.add(estado);
    }

    public void agregarSimboloAlfabeto(char simbolo) {
        alfabeto.add(simbolo);
    }
    
    public Map<String, Map<Character, String>> getTransiciones() {
        return transiciones;
    }

    public void agregarTransicion(String estadoActual, char simbolo, String estadoDestino) {
        Map<Character, String> transicionesEstadoActual = transiciones.getOrDefault(estadoActual, new HashMap<>());
        transicionesEstadoActual.put(simbolo, estadoDestino);
        transiciones.put(estadoActual, transicionesEstadoActual);
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

    public String getTransicion(String estadoActual, char simbolo) {
        Map<Character, String> transicionesEstadoActual = transiciones.get(estadoActual);
        if (transicionesEstadoActual != null) {
            return transicionesEstadoActual.get(simbolo);
        }
        return null;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public Set<String> getEstadosAceptacion() {
        return estadosAceptacion;
    }
}