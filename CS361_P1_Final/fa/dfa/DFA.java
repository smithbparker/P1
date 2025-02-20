package fa.dfa;

import fa.FAInterface;
import fa.State;
import java.util.*;

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

    @Override
    public boolean addState(String name) {
        if (name == null || states.contains(name)) return false;
        states.add(name);
        stateMap.put(name, new DFAState(name));
        return true;
    }

    @Override
    public void addSigma(char symbol) {
        alphabet.add(symbol);
    }

    @Override
    public boolean setStart(String name) {
        if (states.contains(name)) {
            startState = name;
            return true;
        }
        return false;
    }

    @Override
    public boolean setFinal(String name) {
        if (states.contains(name)) {
            finalStates.add(name);
            return true;
        }
        return false;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        if (!states.contains(fromState) || !states.contains(toState) || !alphabet.contains(onSymb)) {
            return false;
        }
        stateMap.get(fromState).addTransition(onSymb, stateMap.get(toState));
        return true;
    }

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

    @Override
    public Set<Character> getSigma() {
        return new HashSet<>(alphabet);
    }

    @Override
    public State getState(String name) {
        return stateMap.getOrDefault(name, null);
    }

    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(name);
    }

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
