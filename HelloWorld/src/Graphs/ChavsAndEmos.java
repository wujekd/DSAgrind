package Graphs;

public class ChavsAndEmos {

	public static void main(String[] args) {
		System.out.println("hi");
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
	}
}