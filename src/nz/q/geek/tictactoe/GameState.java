package nz.q.geek.tictactoe;

import java.util.Arrays;

public class GameState {
	public final Player nextPlayer;

	/**
	 * Which player has a piece in each position, or null for empty.
	 */
	public final Player[][][] board;

	public GameState(Player nextPlayer, Player[][][] board) {
		this.nextPlayer = nextPlayer;
		this.board = board;
	}

	/**
	 * Check whether a player has won.
	 * @return The winner, or null if nobody has won yet.
	 */
	public Player winner() {
		int size = board.length;
		// Straight lines
		for (int x = 0; x < size; ++x) {
			lines: for (int y = 0; y < size; ++y) {
				Player first = board[x][y][0];
				if (first != null) {
					// Check that the rest of the line matches the player in the first position
					for (int z = 1; z < size; ++z) {
						if (board[x][y][z] != first) { 
							continue lines;
						}
					}
					// Line matches, we have a winner
					return first;
				}
			}
		}
		for (int z = 0; z < size; ++z) {
			lines: for (int x = 0; x < size; ++x) {
				Player first = board[x][0][z];
				if (first != null) {
					// Check that the rest of the line matches the player in the first position
					for (int y = 1; y < size; ++y) {
						if (board[x][y][z] != first) { 
							continue lines;
						}
					}
					// Line matches, we have a winner
					return first;
				}
			}
		}
		for (int z = 0; z < size; ++z) {
			lines: for (int y = 0; y < size; ++y) {
				Player first = board[0][y][z];
				if (first != null) {
					// Check that the rest of the line matches the player in the first position
					for (int x = 1; x < size; ++x) {
						if (board[x][y][z] != first) { 
							continue lines;
						}
					}
					// Line matches, we have a winner
					return first;
				}
			}
		}
		// Diagonals on a straight plane
		// Diagonals on a diagonal plane

		return null;
	}

	/**
	 * Return whether the board is full, i.e. there are no empty spaces remaining.
	 * This means either it is a stalemate or someone has won.
	 */
	public boolean full() {
		int size = board.length;
		for (int x = 0; x < size; ++x) {
			for (int y = 0; y < size; ++y) {
				for (int z = 0; z < size; ++z) {
					if (board[x][y][z] == null) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Have the next player place a piece in the given position.
	 * @return the resulting board (with the other player next to move).
	 */
	public GameState makeMove(int moveX, int moveY, int moveZ) {
		if (board[moveX][moveY][moveZ] != null) {
			throw new IllegalArgumentException("Cannot make move, space already occupied.");
		}

		// Make a deep copy of the board
		int size = board.length;
		Player[][][] newBoard = new Player[size][size][size];
		for (int x = 0; x < size; ++x) {
			for (int y = 0; y < size; ++y) {
				for (int z = 0; z < size; ++z) {
					newBoard[x][y][z] = board[x][y][z];
				}
			}
		}

		// Place new piece
		newBoard[moveX][moveY][moveZ] = nextPlayer;

		return new GameState(nextPlayer.opposite(), newBoard);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof GameState) {
			GameState o = (GameState) other;
			return o.nextPlayer == nextPlayer && Arrays.deepEquals(o.board, board);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return nextPlayer.ordinal() * 31 + Arrays.deepHashCode(board);
	}

	@Override
	public String toString() {
		String s = nextPlayer + " to move\n";
		int size = board.length;
		for (int x = 0; x < size; ++x) {
			for (int y = 0; y < size; ++y) {
				for (int z = 0; z < size; ++z) {
					if (board[x][y][z] == null) {
						s += ". ";
					} else {
						s += board[x][y][z] + " ";
					}
				}
				s += "\n";
			}
			s += "-\n";
		}
		return s;
	}
}
