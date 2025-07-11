package Graphs;

import java.util.ArrayList;
import java.util.List;

public class ChavsAndEmos {

	public static void main(String[] args) {

		List<State> solution = solve(new State(3,3,true,null));
		
		for (State state : solution) {
			System.out.println(state.toString());
		}
	}
	
	public static List<State> solve(State initState){
		
		List<State> visited = new ArrayList<State>();
		visited.add(initState);
		
		List<State> queue = new ArrayList<State>();
		queue.add(initState);
		
		while (!queue.isEmpty()) {
			
			// dequeue a state 
			// if state == goal break
			// run getNextStates on it
			// for each in result if not in visited add to visited and queue
			
			State currentState = queue.remove(0);
			
			if (isStateGoal(currentState)) {
				return recreatePath(visited, currentState);
			}
			
			List<State> newStates = getNextStates(currentState);
			for (State newState : newStates) {
				if (!visited.contains(newState)) {
					visited.add(newState);
					queue.add(newState);
				}
			}
		}
		return null;
	}
	
	
	public static List<State> getNextStates(State state){
		
		List<State> result = new ArrayList<State>(); // list of valid states reachable from passed state
		
		// transitions:  ( M, x ), ( M, M ), ( C, x ), ( C, C ),  ( M, C )
		int[][] transitions = {{1,0},{2,0},{0,1},{0,2},{1,1}};
		
		for (int[] move : transitions) {
			int m = move[0];
			int c = move[1];
			
			State newState;
			if (state.boatL) {
				newState = new State(state.missionariesL - m, state.cannibalsL - c, false, state);
			} else {
				newState = new State(state.missionariesL + m, state.cannibalsL + c, true, state);
			}
			
			if (isStateValid(newState)) {
				result.add(newState);
			}
		}
		return result;
	}
	

	
	public static boolean isStateValid(State state) {
	    int mL = state.missionariesL;
	    int cL = state.cannibalsL;
	    int mR = 3 - mL;
	    int cR = 3 - cL;

	    if (mL < 0 || cL < 0 || mL > 3 || cL > 3) return false;
	    if (mR < 0 || cR < 0 || mR > 3 || cR > 3) return false;

	    if ((mL > 0 && cL > mL) || (mR > 0 && cR > mR)) return false;

	    return true;
	}
	
	public static boolean isStateGoal(State state) {
		return state.missionariesL == 0 && state.cannibalsL == 0;
	}
	
	
	public static List<State> recreatePath(List<State> visited, State finalState){
		
		List<State> path = new ArrayList<>();
		State current = finalState;
		
		while (current.parent != null) {
			path.add(0, current); // prepend to list aka add at the beginning
			current = current.parent;
		}
		path.add(0, current);
		return path;
	}
	
	
	public static class State {
		int missionariesL;
		int cannibalsL;
		boolean boatL;
		State parent;
		
		public State(int missionariesL, int cannibalsL, boolean boatL, State parent) {
			this.missionariesL = missionariesL;
			this.cannibalsL = cannibalsL;
			this.boatL = boatL;
			this.parent = parent;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof State)) return false;
			State other = (State) o;
			return this.missionariesL == other.missionariesL &&
					this.cannibalsL == other.cannibalsL &&
					this.boatL == other.boatL;
		}
		
		@Override
		public int hashCode() {
			return 69 * missionariesL + 420 * cannibalsL + (boatL ? 0 : 1);
		}
		
		@Override
		public String toString() {
		    return "(" + missionariesL + "M, " + cannibalsL + "C, BoatLeft=" + boatL + ")";
		}
	}
}