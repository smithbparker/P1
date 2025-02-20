package fa.dfa;

import fa.FAInterface;
import fa.State;
import java.util.*;

/**
 * A class representing a Deterministic Finite Automaton (DFA).
 * 
 * This class implements {@link DFAInterface}, which extends {@link FAInterface}.
 * The DFA consists of a set of states, an alphabet (Sigma), a transition function,
 * a start state, and a set of accepting (final) states.
 * 
 * The DFA processes input strings and determines whether they belong
 * to the DFA's language.
 * 
 * @author [Parker and Sebastian]
 */
public class DFA implements DFAInterface {
    /** The set of states in the DFA */
    private Set<String> states;
    
    /** The alphabet (set of valid input symbols) */
    private Set<Character> alphabet;
    
    /** The transition function mapping states and input symbols to new states */
    private Map<String, DFAState> stateMap;
    
    /** The start state of the DFA */
    private String startState;
    
    /** The set of final (accepting) states */
    private Set<String> finalStates;

    /**
     * Constructs an empty DFA with no states or transitions.
     */
    public DFA() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        stateMap = new HashMap<>();
        finalStates = new HashSet<>();
        startState = null;
    }

    /**
     * Adds a new state to the DFA.
     * 
     * @param name The name of the state to be added.
     * @return true if the state is successfully added, false if it already exists.
     */
    @Override
    public boolean addState(String name) {
        if (name == null || states.contains(name)) return false;
        states.add(name);
        stateMap.put(name, new DFAState(name));
        return true;
    }

    /**
     * Adds a symbol to the DFA's alphabet (Sigma).
     * 
     * @param symbol The input symbol to add.
     */
    @Override
    public void addSigma(char symbol) {
        alphabet.add(symbol);
    }

    /**
     * Sets the start state of the DFA.
     * 
     * @param name The name of the start state.
     * @return true if the state exists and is set as the start state, false otherwise.
     */
    @Override
    public boolean setStart(String name) {
        if (states.contains(name)) {
            startState = name;
            return true;
        }
        return false;
    }

    /**
     * Marks an existing state as a final (accepting) state.
     * 
     * @param name The name of the state to mark as final.
     * @return true if the state exists and is marked as final, false otherwise.
     */
    @Override
    public boolean setFinal(String name) {
        if (states.contains(name)) {
            finalStates.add(name);
            return true;
        }
        return false;
    }

    /**
     * Adds a transition to the DFA.
     * 
     * @param fromState The state where the transition starts.
     * @param toState The state where the transition leads.
     * @param onSymb The input symbol triggering the transition.
     * @return true if the transition is successfully added, false otherwise.
     */
    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        if (!states.contains(fromState) || !states.contains(toState) || !alphabet.contains(onSymb)) {
            return false;
        }
        stateMap.get(fromState).addTransition(onSymb, stateMap.get(toState));
        return true;
    }

    /**
     * Creates a deep copy of this DFA with symbols `symb1` and `symb2` swapped in transitions.
     * 
     * @param symb1 The first symbol to swap.
     * @param symb2 The second symbol to swap.
     * @return A new DFA with the symbols swapped.
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

    /**
     * Determines whether the DFA accepts a given input string.
     * 
     * @param s The input string to process.
     * @return true if the DFA reaches a final state, false otherwise.
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

    /**
     * Returns the DFA's alphabet (Sigma).
     * 
     * @return A set containing the DFA's input symbols.
     */
    @Override
    public Set<Character> getSigma() {
        return new HashSet<>(alphabet);
    }

    /**
     * Retrieves the state object for a given state name.
     * 
     * @param name The name of the state.
     * @return The corresponding State object, or null if the state does not exist.
     */
    @Override
    public State getState(String name) {
        return stateMap.getOrDefault(name, null);
    }

    /**
     * Checks if a given state is a final state.
     * 
     * @param name The name of the state to check.
     * @return true if the state is final, false otherwise.
     */
    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(name);
    }

    /**
     * Checks if a given state is the start state.
     * 
     * @param name The name of the state to check.
     * @return true if the state is the start state, false otherwise.
     */
    @Override
    public boolean isStart(String name) {
        return name.equals(startState);
    }

    /**
     * Returns a string representation of the DFA, following the format specified in DFAInterface.
     * 
     * @return A formatted string representing the DFA.
     */
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
