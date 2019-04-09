package com.reversi.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class MinimaxAlgorithm {
 
    private MinimaxAlgorithm() {}
 
    public static State minimaxDecision(State state) {
        return state.getActions().stream()
                .max(Comparator.comparing(MinimaxAlgorithm::minValue)).get();
    }
 
    private static double maxValue(State state) {
        if(state.isTerminal()){
            return state.getUtility();
        }
        return state.getActions().stream()
                .map(MinimaxAlgorithm::minValue)
                .max(Comparator.comparing(Double::valueOf)).get();
    }
 
    private static double minValue(State state) {
        if(state.isTerminal()){
            return state.getUtility();
        }
        return state.getActions().stream()
                .map(MinimaxAlgorithm::maxValue)
                .min(Comparator.comparing(Double::valueOf)).get();
    }
 
    public static class State {
    	 
    	final int state;
        final boolean firstPlayer;
        final boolean secondPlayer;
            
        public State(int state, boolean firstPlayer){
            this.state = state;
            this.firstPlayer = firstPlayer;
            this.secondPlayer = !firstPlayer;
        }
     
        Collection<State> getActions(){
            List<State> actions = new LinkedList<>();
            //generate actions
            return actions;
        }
     
        boolean isTerminal() {
            //add some logic
            return false;
        }
     
        double getUtility() {
            if(firstPlayer)
                return -1;
            else
                return 1;
        }
    }
}