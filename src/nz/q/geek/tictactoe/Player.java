package nz.q.geek.tictactoe;

public enum Player {
	WHITE, BLUE;

	public Player opposite() {
		return this == WHITE ? BLUE : WHITE;
	}

	public String toString() {
		return this == WHITE ? "O" : "X";
	}
}
