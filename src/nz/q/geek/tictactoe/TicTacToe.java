package nz.q.geek.tictactoe;

import static nz.q.geek.tictactoe.Player.BLUE;

import java.util.HashMap;
import java.util.Map;

public class TicTacToe {
	private static final int SIZE = 3;
	private final Map<GameState, Player> knownWinners = new HashMap<>();

	public static void main(String[] args) {
		new TicTacToe().start();
	}

	public void start() {
		GameState initialState = new GameState(BLUE, new Player[SIZE][SIZE][SIZE]);
		//explore(initialState);
		System.out.println("From blank board of size " + SIZE + ", " + BLUE + " starting, " + cachedWinner(initialState, 0) + " wins.");
	}

	public void explore(GameState state) {
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

	/**
	 * Check whether either player has a guaranteed win from the given state.
	 * @return the player who can win no matter what the other player does, or null if neither is guaranteed.
	 */
	public Player winner(GameState state, int depth) {
		Player alreadyWon = state.winner();
		if (alreadyWon != null) {
			return alreadyWon;
		} else if (state.full()) {
			return null;
		}

		// Try all possible moves for the current player
		boolean stalematePossible = false;
		for (int x = 0; x < SIZE; ++x) {
			for (int y = 0; y < SIZE; ++y) {
				for (int z = 0; z < SIZE; ++z) {
					if (state.board[x][y][z] == null) {
						GameState newState = state.makeMove(x, y, z);
						Player newWinner = cachedWinner(newState, depth + 1);
						if (newWinner == state.nextPlayer) {
							// If the current player has a guaranteed win from the new state, they have a guaranteed win from this state.
							return newWinner;
						} else if (newWinner == null) {
							// We would rather a stalemate than the opposing player winning.
							stalematePossible = true;
						}
					}
				}
			}
		}
		// No way for current player to win. Either stalemate, or other player wins.
		if (stalematePossible) {
			return null;
		} else {
			return state.nextPlayer.opposite();
		}
	}

	public Player cachedWinner(GameState state, int depth) {
		if (knownWinners.containsKey(state)) {
			return knownWinners.get(state);
		}
		Player winner = winner(state, depth);
		knownWinners.put(state, winner);
		if (depth < 14) {
			System.out.println(winner + " wins");
			System.out.println(knownWinners.size() + " entries in cache");
		}
		return winner;
	}
}
