package ausroulette.model.bet;

import java.util.Arrays;

import ausroulette.model.Player;
import ausroulette.model.wheel.Pocket;
import ausroulette.model.wheel.Wheel;

public class SplitBetImpl implements SplitBet {
	private Player player; 
	private int amount; 
	private BetType betType;
	private Pocket winningPocket;
	private int[] numbers;
	
	public SplitBetImpl(Player player, int amount, int[] numbers) {
		//sort for easier bet checking
		Arrays.sort(numbers);
		if (player == null || numbers == null) {
			throw new NullPointerException("Player and number cannot be empty.");
		} 
		if (amount < 0) { 
			throw new IllegalArgumentException("You can't bet a negative number!");
		}
		if (numbers.length > 4 || numbers.length < 2) { 
			throw new IllegalArgumentException("There are an invalid number of numbers for the bet. You must bet 2 or 4 numbers");
		}
		if (!checkNums(numbers)) { 
			throw new IllegalArgumentException("Bets must be between 1 and 36.");
		}
		if ( (numbers.length == 2 ? !checkTwoSplit(numbers[0], numbers[1]) : !checkFourSplit(numbers[0],numbers[1], numbers[2], numbers[3]))) { 
			throw new IllegalArgumentException("That is an invalid bet combination. "
					+ "Split-four bets must be an adjacent square on the displayed board. "
					+ "Split-two bets must be two adjacent tiles.");
		}
		if (player.getAvailablePoints() < amount) {
			throw new IllegalStateException("You do not have enough points for that.");
		}
		this.player = player;
		this.amount = amount;
		this.numbers = numbers;
		if (numbers.length == 2) {
			this.betType = BetType.SPLIT_2;
		} else {
			this.betType = BetType.SPLIT_4;
		}
	}
	
	public SplitBetImpl(Player player, int amount, int number1, int number2) {
		int[] temp = {number1, number2};
		new SplitBetImpl(player, amount, temp);
	}
	
	public SplitBetImpl(Player player, int amount, int number1, int number2, int number3, int number4) {
		int[] temp = {number1, number2, number3, number4};
		new SplitBetImpl(player, amount, temp);
	}
	
	private boolean checkNextAdjacent(int num1, int num2) {
		return (num1 + 1 == num2);
	}
	
	private boolean checkVertAdjacent(int num1, int num2) {
		return (num1 + 3 == num2 || num1 - 3 == num2);
	}
	
	private boolean checkAboveAdjacent(int num1, int num2) {
		return (num1 - 3 == num2);
	}
	private boolean checkBelowAdjacent(int num1, int num2) {
		return (num1 + 3 == num2);
	}
	
	private boolean checkTwoSplit(int number1, int number2) {

		//nums sorted, so if num1 is ever a multiple of 3, assume invalid bet(unless vertical is true)
		if (number1 % 3 != 0) {
			if (checkNextAdjacent(number1, number2)) {
				return true;
			}
		} 
		if ( checkVertAdjacent(number1, number2) ) {
			return true;
		}
		
		return false;
	}
	
	private boolean checkFourSplit(int number1, int number2, int number3, int number4) {
		// check first 2 nums are valid, then check vertical values before returning true
		// note: even if 1 & 2 are vertically adjacent, none of the other conditions will ever be true
		if ( checkTwoSplit(number1, number2) ) {
			if (checkAboveAdjacent(number1, number3)) {
				if (checkAboveAdjacent(number2, number4)) {
					return true;
				}
			}else if (checkBelowAdjacent(number1, number3)) {
				if (checkBelowAdjacent(number2, number4)) {
					return true;
				}
			}
		}
		return false;
	}
	
	//check numbers exists in pockets
	public boolean checkNums(int[] betNum) {
		int foundCounter = 0;
		//loop until all numbers are found or all comparisons are checked
		for (int i = 0; Wheel.NUMBER_OF_POCKETS > i && betNum.length > foundCounter; i++) {
			for (int b = 0; betNum.length > b; b++) {
				//increment counter
				if (betNum[b] == Wheel.POCKET_NUMBERS[i]) {
					foundCounter++;
				}
			}
		}
		// if all numbers are found return true, else false
		if (foundCounter == betNum.length) {
			return true;
		}
		return false;
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
		for (int number : numbers) { 
			if (winningPocket.getNumber() == number) {
				return this.amount;
			}
		}
		return 0;
	}

	@Override
	public boolean isWin() {
		if (winningPocket == null) {
			return false;
		}
		for (int number : numbers) { 
			if (winningPocket.getNumber() == number) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getAmountWon() {
		if (isWin()) {
			return this.amount * 17;
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
	
	@Override
	public int hashCode() {
		return this.amount + numbers.hashCode() + betType.hashCode() + player.hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("%s bet %d on %s %s", player.getId(), this.amount, this.betType, Arrays.toString(numbers));
	}
	
	@Override
	public int getFirstNumber() {
		return numbers[0];
	}

	@Override
	public int getLastNumber() {
		return numbers[numbers.length-1];
	}

	@Override
	public int getNumber(SplitPos pos) {
		if (betType == BetType.SPLIT_2 && (pos == SplitPos.THIRD || pos == SplitPos.FORTH)) {
			throw new IllegalArgumentException();
		} 
		if (pos == SplitPos.FIRST) {
			return numbers[0];
		} else if (pos == SplitPos.SECOND) {
			return numbers[1];
		} else if (pos == SplitPos.THIRD) {
			return numbers[2];
		} else if (pos == SplitPos.FORTH) {
			return numbers[3];
		} else {
			return numbers[numbers.length-1];
		}
	}

}
