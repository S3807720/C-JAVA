package ausroulette.model.bet;

import ausroulette.model.Player;
import ausroulette.model.wheel.Pocket;
import ausroulette.model.wheel.PocketColor;

public class ColorBetImpl implements ColorBet {
	private Player player; 
	private int amount; 
	private PocketColor color;
	private BetType betType;
	private Pocket winningPocket;
	
	public ColorBetImpl(Player player, int amount, PocketColor color) {
		if (player == null || color == null) {
			throw new NullPointerException("This can't be reached but something is empty!");
		} else if (amount < 0 || (color != PocketColor.BLACK && color != PocketColor.RED)) { 
			throw new IllegalArgumentException("Bet amount can't be negative!"); //no green bets accepted
		} else if (player.getAvailablePoints() < amount) {
			throw new IllegalStateException("You don't have enough points for that bet.");
		}
		this.player = player;
		this.amount = amount;
		this.color = color;
		if (color == PocketColor.BLACK) {
			this.betType = BetType.BLACK;
		} else {
			this.betType = BetType.RED;
		}
		
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public int getAmount() {
		return this.amount;
	}

	@Override
	public BetType getBetType() {
		return this.betType;
	}

	@Override
	public Pocket getWinningPocket() {
		if (this.winningPocket != null) {
			return this.winningPocket;
		}
		return null;
	}

	@Override
	public int finaliseBet(Pocket winningPocket) {
		this.winningPocket = winningPocket;
		if (winningPocket.getColor() == this.color) {
			return this.amount;
		}
		return 0;
	}

	@Override
	public boolean isWin() {
		return (winningPocket != null && winningPocket.getColor() == this.color);
	}

	@Override
	public int getAmountWon() {
		if (isWin()) {
			return this.amount;
		}
		return 0;
	}

	@Override
	public boolean equals(Bet bet) {
		return (bet.getAmount() == this.amount && bet.getBetType() == this.betType 
				&& bet.getClass() == this.getClass());
	}
	@Override
	public boolean equals(Object obj) {
		return ( obj.hashCode() == this.hashCode() ); // ?
	}
	
	public int hashCode() {
		return this.amount + color.hashCode() + betType.hashCode() + player.hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("%s bet %d on %s", player.getId(), this.amount, this.betType);
		
	}
	
	public PocketColor getColor() {
		return this.color;
	}

}
