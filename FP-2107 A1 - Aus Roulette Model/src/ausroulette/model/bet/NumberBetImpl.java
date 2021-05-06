package ausroulette.model.bet;

import ausroulette.model.Player;
import ausroulette.model.wheel.Pocket;

public class NumberBetImpl implements NumberBet {
	private Player player; 
	private int amount, number; 
	private BetType betType;
	private Pocket winningPocket;
	
	public NumberBetImpl(Player player, int amount, int number) {
		try {
			this.player = player;
		}	catch (NullPointerException n) {
			
		}
		if (0 > amount || number > 30) {
			throw new IllegalArgumentException();
		}
		if (amount > player.getPoints()) {
			throw new IllegalStateException();
		}
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
		return ( obj.equals(this) && obj.hashCode() == this.hashCode() ); // ?
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
