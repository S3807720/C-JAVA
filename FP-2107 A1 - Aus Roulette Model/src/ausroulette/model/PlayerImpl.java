package ausroulette.model;

import java.util.ArrayList;
import java.util.Collection;

import ausroulette.model.bet.Bet;
import ausroulette.model.bet.BetType;

public class PlayerImpl implements Player {
	private String id, name;
	private int points, availablePoints;
	private ArrayList<Bet> bets = new ArrayList<Bet>();
	private final int initialPoints; //should not be changed??
	private int totalBet = 0, currentBets = 0;
	
	public PlayerImpl(String id, String name, int initialPoints) {
		if (id == "" || name == "" || initialPoints < 0) {
			throw new IllegalArgumentException();
		} else if (id == null || name == null) {
			throw new NullPointerException();
		}
		this.id = id;
		this.name = name;
		this.initialPoints = initialPoints;
		this.points = availablePoints = initialPoints;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getInitialPoints() {
		return this.initialPoints;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	@Override
	public int getAvailablePoints() {
		return this.availablePoints;
	}

	@Override
	public void addPoints(int points) {
		this.points += points;
		this.availablePoints += points;
	}

	@Override
	public int getCurrentBetTotal() {
		int betAmount = 0;
		for(int i = 0; bets.size() > i; i++) {
			betAmount += bets.get(i).getAmount();
		}
		
		
		return betAmount;
	}

	@Override
	public void acceptBet(Bet bet) {
		if (bet == null) {
			throw new NullPointerException();
		}
		if (this.equals(bet.getPlayer()) == false ) {
			throw new IllegalArgumentException();
		}
		bets.add(bet);
		totalBet += bet.getAmount();
		availablePoints -= bet.getAmount();
		currentBets++;
	}

	
	@Override
	public void cancelBet(Bet bet) {
		if (bet == null) {
			throw new NullPointerException();
		}
		if (bet.isWin() || this.equals(bet.getPlayer()) == false || !findBet(bet)) {
			throw new IllegalStateException();
		}
		int i; 
		for (i = 0; bets.size() > i; i++) {
			if (bets.get(i).equals(bet)) {
				this.availablePoints += bet.getAmount();
				this.totalBet -= bet.getAmount();
				bets.remove(i);
				currentBets--;
				break;
			}
		}
	}
	
	private boolean findBet(Bet bet) {
		for (int i = 0; bets.size() > i; i++) {
			if (bets.get(i).equals(bet)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void applyBetOutcome(Bet bet) {
		if (bet == null) {
			throw new NullPointerException();
		}
		if (bet.getWinningPocket() == null || this.equals(bet.getPlayer()) == false || !findBet(bet)) {
			throw new IllegalStateException();
		}
		
		if (bet.isWin()) {
			int amount = bet.getAmount();
			BetType type = bet.getBetType();
			switch(type) {
			case ZERO :
			case NUMBER :
				amount *= 35;
				break;
			case SPLIT_2 :
			case SPLIT_4 :
				amount *= 17;
				break;
			default :
				break;
			}
			this.points += amount;
			this.availablePoints += bet.getAmount() + amount;
		} else {
			this.points -= bet.getAmount();
		}

	}

	@Override
	public void resetBets() {
		this.availablePoints = this.points;
		this.totalBet = 0;
		this.currentBets = 0;
		bets.clear();
	}

	@Override
	public Collection<Bet> getBets() {
		final ArrayList<Bet> LIST_OF_BETS = bets;
		return LIST_OF_BETS;
	}

	@Override
	public int compareTo(Player player) {
		return (player.getName().compareTo(this.name));
	}

	@Override
	public boolean equals(Player player) {
		if(this.id.equals(player.getId()) ) {
			return true;
		} 
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ( obj.equals(this) && obj.hashCode() == this.hashCode() ); // ?
	}
	
	@Override
	public int hashCode() {
		return id.hashCode() + name.hashCode() + points + availablePoints + initialPoints + totalBet;
	}

	@Override
	public String toString() {
		return String.format("Player ID: " + id + ", Name: " + name + 
				"\nPoints: " + points + ", Available Points: " + availablePoints
				+ "\nCurrent Bets: " + currentBets + ", Total Bet: " + totalBet);
	}

}
