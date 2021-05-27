package ausroulette.model.bet;

import ausroulette.model.Player;
import ausroulette.model.wheel.Pocket;
import ausroulette.model.wheel.Wheel;

public class NumberBetImpl implements NumberBet {
	private Player player; 
	private int amount, number; 
	private BetType betType;
	private Pocket winningPocket;
	
	public NumberBetImpl(Player player, int amount, int number) {
		if (player == null) {
			throw new NullPointerException("Invalid player.");
		}
		if (0 > amount) {
			throw new IllegalArgumentException("Bet amount must be positive.");
		}
		if (number > Wheel.LARGEST_NUMBER || number < 0) {
			throw new IllegalArgumentException("That is an invalid pocket number.");
		}
		if (amount > player.getPoints()) {
			throw new IllegalStateException("You don't have enough points for that bet.");
		}
		this.player = player;

		this.amount = amount;
		this.number = number;
		if (number == 0) {
			this.betType = BetType.ZERO;
		} else {
			this.betType = BetType.NUMBER;
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
		if (winningPocket.getNumber() == this.number) {
			return this.amount;
		}
		return 0;
	}

	@Override
	public boolean isWin() {
		return (winningPocket != null && winningPocket.getNumber() == this.number);
	}

	@Override
	public int getAmountWon() {
		if (isWin()) {
			return this.amount * 35;
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
		return this.amount + this.number + betType.hashCode() + player.hashCode();
	}
	
	@Override
	public String toString() {
		if (betType == BetType.ZERO) {
			return String.format("%s bet %d on Zero", player.getId(), this.amount);
		}
		return String.format("%s bet %d on Number #%d", player.getId(), this.amount, this.getNumber());
	}
	
	public int getNumber() {
		return this.number;
	}

}
