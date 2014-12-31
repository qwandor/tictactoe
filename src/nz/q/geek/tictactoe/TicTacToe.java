package nz.q.geek.tictactoe;

import static nz.q.geek.tictactoe.Player.BLUE;

public class TicTacToe {
	private static final int SIZE = 3;

	public static void main(String[] args) {
		GameState initialState = new GameState(BLUE, new Player[SIZE][SIZE][SIZE]);
		explore(initialState);
	}

	public static void explore(GameState state) {
		Player winner = state.winner();
		if (winner != null) {
			System.out.println(winner + " wins");
			System.out.println(state);
			return;
		} else if (state.full()) {
			System.out.println("Stalemate");
			return;
		}

		// Try all possible moves
		for (int x = 0; x < SIZE; ++x) {
			for (int y = 0; y < SIZE; ++y) {
				for (int z = 0; z < SIZE; ++z) {
					if (state.board[x][y][z] == null) {
						explore(state.makeMove(x, y, z));
					}
				}
			}
		}
	}
}
