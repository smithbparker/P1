package fa.dfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fa.State;


/*
 * The logic for a DFA within this project.
 * By extending the DFAInterface, this class defines the methods needed for a DFA's states, alphabet, transitions, and start/final points.
 * Out of the concrete classes given, we chose to use a HashSet and HashMap for the structure of this class.
 * @Author Parker Smith, Sabastian Leeper
 */

/**
 * DFA class implements DFAInterface and FAInterface.
 */
public class DFA implements DFAInterface {
    private Set<String> states;
    private Set<Character> alphabet;
    private Map<String, DFAState> stateMap;
    private String startState;
    private Set<String> finalStates;

    public DFA() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        stateMap = new HashMap<>();
        finalStates = new HashSet<>();
        startState = null;
    }

      /*
     * Adds a state to the DFA
     * @param name of the added state
     * @return true if the state is added, otherwise false
     */  
    @Override
    public boolean addState(String name) {
        if (name == null || states.contains(name)) return false;
        states.add(name);
        stateMap.put(name, new DFAState(name));
        return true;
    }

     /*
     * Adds a symbol to the alphabet
     * @param symbol the symbol added to the alphabet
     */  
    @Override
    public void addSigma(char symbol) {
        alphabet.add(symbol);
    }

    /*
     * Sets a state as the start state
     * @param name of the state to be set as the start
     * @return true if the state is set as start, otherwise false
     */
    @Override
    public boolean setStart(String name) {
        if (states.contains(name)) {
            startState = name;
            return true;
        }
        return false;
    }

    /*
     * Sets a state as final
     * @param name of the state to be set as final
     * @return true if the state is set as final, otherwise false
     */
    @Override
    public boolean setFinal(String name) {
        if (states.contains(name)) {
            finalStates.add(name);
            return true;
        }
        return false;
    }

    /* 
      * Adds a transition between states on a given symbol
      * @param fromState the name of the state to transition from
      * @param toState the name of the state to transition to
      * @param onSymb the symbol on which the transition occurs
      * @return true if the transition was added, otherwise false  
      */  

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        if (!states.contains(fromState) || !states.contains(toState) || !alphabet.contains(onSymb)) {
            return false;
        }
        stateMap.get(fromState).addTransition(onSymb, stateMap.get(toState));
        return true;
    }

     /* 
     * Swaps two transition symbols in the DFA
     * @param symb1 the first symbol to swap
     * @param symb2 the second symbol to swap
     * @return a new DFA with the swapped symbols
     */
    @Override
    public DFA swap(char symb1, char symb2) {
        if (!alphabet.contains(symb1) || !alphabet.contains(symb2)) {
            return null;
        }
        DFA newDFA = new DFA();
        newDFA.states.addAll(this.states);
        newDFA.alphabet.addAll(this.alphabet);
        newDFA.startState = this.startState;
        newDFA.finalStates.addAll(this.finalStates);

        for (String state : this.states) {
            newDFA.stateMap.put(state, new DFAState(state));
            for (char symbol : this.stateMap.get(state).getTransitions().keySet()) {
                char newSymbol = (symbol == symb1) ? symb2 : (symbol == symb2) ? symb1 : symbol;
                newDFA.stateMap.get(state).addTransition(newSymbol, this.stateMap.get(state).getTransitions().get(symbol));
            }
        }
        return newDFA;
    }

     /*
     * Checks if a string is accepted by the DFA
     * @param s the string to check
     * @return true if the string is accepted, otherwise false
     */  
    @Override
    public boolean accepts(String s) {
        if (startState == null) return false;
        String currentState = startState;

        for (char symbol : s.toCharArray()) {
            if (!stateMap.get(currentState).getTransitions().containsKey(symbol)) {
                return false;
            }
            currentState = stateMap.get(currentState).getTransitions().get(symbol).getName();
        }
        return finalStates.contains(currentState);
    }

    // Return alphabet (Sigma)
    @Override
    public Set<Character> getSigma() {
        return new HashSet<>(alphabet);
    }

    // Return state object if found
    @Override
    public State getState(String name) {
        return stateMap.getOrDefault(name, null);
    }

    
    // Return true if state is final
    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(name);
    }

    // Check if state is the start state
    @Override
    public boolean isStart(String name) {
        return name.equals(startState);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Q = { ").append(String.join(" ", states)).append(" }\n");
        sb.append("Sigma = { ");
        for (char symbol : alphabet) {
            sb.append(symbol).append(" ");
        }
        sb.append("}\n");

        sb.append("delta =\n\t");
        for (char symbol : alphabet) {
            sb.append(symbol).append("\t");
        }
        sb.append("\n");

        for (String state : states) {
            sb.append(state).append("\t");
            for (char symbol : alphabet) {
                sb.append(stateMap.get(state).getTransitions().getOrDefault(symbol, new DFAState("-")).getName()).append("\t");
            }
            sb.append("\n");
        }

        sb.append("q0 = ").append(startState).append("\n");
        sb.append("F = { ").append(String.join(" ", finalStates)).append(" }");

        return sb.toString();
    }
}
 