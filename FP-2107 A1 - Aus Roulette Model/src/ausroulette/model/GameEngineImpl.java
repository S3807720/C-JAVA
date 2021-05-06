package ausroulette.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ausroulette.model.bet.Bet;
import ausroulette.model.bet.BetType;
import ausroulette.model.bet.ColorBet;
import ausroulette.model.bet.ColorBetImpl;
import ausroulette.model.bet.NumberBet;
import ausroulette.model.bet.NumberBetImpl;
import ausroulette.model.bet.SplitBet;
import ausroulette.model.bet.SplitBetImpl;
import ausroulette.model.wheel.Pocket;
import ausroulette.model.wheel.PocketColor;
import ausroulette.model.wheel.Wheel;
import ausroulette.model.wheel.WheelImpl;
import ausroulette.view.GameCallback;

public class GameEngineImpl implements GameEngine {
	private ArrayList<GameCallback> loggers = new ArrayList<GameCallback>();
	private int callbackCount = 0;
	private Map<String, Player> players = new HashMap<String, Player>();
	private int playerCount = 0, spinCount = 0, allPlayerTotalWin = 0, 
			allPlayerTotalLoss = 0, playerWin = 0, playerLoss = 0;
	private WheelImpl gameWheel = new WheelImpl();
	
	@Override
	public int registerCallback(GameCallback callback) {
		loggers.add(callback);
		callbackCount++;
		return callbackCount;
	}

	@Override
	public int removeCallback(GameCallback callback) {
		for (GameCallback logger : loggers) {
			if (logger == callback) {
				loggers.remove(logger);
				callbackCount--;
			}
		}
		return callbackCount;
	}

	@Override
	public Player addPlayer(String id, String name, int initialPoints) {
		if (id == null || name == null) {
			throw new NullPointerException();
		}
		if (id == "" || name == "" || 0 > initialPoints || players.get(id) != null) {
			throw new IllegalArgumentException();
		}
		Player player = new PlayerImpl(id, name, initialPoints);
		players.put(id, player);
		for (GameCallback logger : loggers) {
			logger.playerAdded(player);
		}

		return player;
	}

	@Override
	public Player removePlayer(String id) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id == "" || players.get(id) == null || !players.get(id).getBets().isEmpty()) {
			throw new IllegalArgumentException();
		}
		Player player = players.get(id);
		players.remove(id);
		for (GameCallback logger : loggers) {
			logger.playerRemoved(player);
		}
		return player;
	}
	
	//iterate until ID is found
	private String findId(String id) {
		Player player = players.get(id);
		if (player != null) {
			return player.getId();
		} 
		return null;
	}
	
	@Override
	public Collection<Player> getAllPlayers() {
		//add players to ArrayList and return if # > 0
		if (playerCount > 0) {
			final Collection<Player> PLAYER_LIST = new ArrayList<Player>(players.values());
			return PLAYER_LIST;
		}
		return null;
	}

	@Override
	public Player addPoints(String id, int points) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id == "" || 0 > points || players.get(id) == null) {
			throw new IllegalArgumentException();
		}
		if (findId(id) == null) {
			return null;
		}
		Player player = players.get(id);
		players.get(id).addPoints(points);
		for (GameCallback logger : loggers) {
			logger.pointsAdded(players.get(id), points);
		}
		return player;
	}

	@Override
	public void cancelBet(Bet bet) {
		if (bet == null) {
			throw new NullPointerException();
		}
		Collection<Bet> bets = bet.getPlayer().getBets();
		if (bet.isWin() || bet.getPlayer().getBets().isEmpty() == true) {
			throw new IllegalArgumentException();
		}
		bet.getPlayer().cancelBet(bet);
		for (GameCallback logger : loggers) {
			logger.betCancelled(bet);
		}
		
	}

	private Bet getCurrentColorBet(Player player, PocketColor color) {
		Collection<Bet> bets = player.getBets();
		ArrayList<ColorBet> colBets = new ArrayList<>();
		//if instance of colorbet, put into new list
		for (Bet colBet : bets) {
			if (colBet instanceof ColorBet) {
				colBets.add((ColorBet) colBet);
			}
		} // loop through new list and return match if exists
		for (ColorBet tempBet : colBets) {
			if (tempBet.getColor() == color) {
				return tempBet;
			}
		}
		return null;
	}
	
	private Bet getCurrentNumBet(Player player, int number) {
		Collection<Bet> bets = player.getBets();
		ArrayList<NumberBet> numBets = new ArrayList<>();
		//if instance of numberbet, put into new list
		for (Bet numBet : bets) {
			if (numBet instanceof NumberBet) {
				numBets.add((NumberBet) numBet);
			}
		} // loop through new list and return match if exists
		for (NumberBet tempBet : numBets) {
			if (tempBet.getNumber() == number) {
				return tempBet;
			}
		}
		return null;
	}

	@Override
	public Bet placeColorBet(String id, int amount, PocketColor color) {
		if (id == null || color == null) {
			throw new NullPointerException();
		}
		if (id == "" || 0 > amount || players.get(id) == null) {
			throw new IllegalArgumentException();
		}
		
		if (color == PocketColor.GREEN) { 
			placeNumberBet(id, amount, 0);
		} else if (players.get(id) != null) {
			Bet existingBet = getCurrentColorBet(players.get(id), color);
			if (existingBet != null) {
				amount += existingBet.getAmount();
				players.get(id).cancelBet(existingBet);
			}
			ColorBetImpl bet = new ColorBetImpl(players.get(id), amount, color);
			players.get(id).acceptBet(bet);
			for (GameCallback logger : loggers) {
				logger.betAccepted(players.get(id), bet, existingBet);
			}
			return bet;
		}
		return null;
	}
	
	@Override
	public Bet placeNumberBet(String id, int amount, int number) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id == "" || 0 > amount || players.get(id) == null) {
			throw new IllegalArgumentException();
		}
		if (players.get(id) != null) {
			Bet existingBet = getCurrentNumBet(players.get(id), number);
			if (existingBet != null) {
				amount += existingBet.getAmount();
				players.get(id).cancelBet(existingBet);
			}
			Bet bet = new NumberBetImpl(players.get(id), amount, number);
			players.get(id).acceptBet(bet);
			for (GameCallback logger : loggers) {
				logger.betAccepted(players.get(id), bet, existingBet);
			}
			return bet;
		}
		return null;
	}
	
	private Bet getCurrentSplitBet(SplitBet bet) {
		Collection<Bet> bets = bet.getPlayer().getBets();
		ArrayList<SplitBet> splitBets = new ArrayList<>();
		//if instance of splitbet, put into new list
		for (Bet splitBet : bets) {
			if (splitBet instanceof SplitBet) {
				splitBets.add((SplitBet) splitBet);
			}
		} 
		for (SplitBet tempBet : splitBets) {
			//compare each position, null if somethings off (works as numbers are sorted)
			for (SplitBet.SplitPos pos : SplitBet.SplitPos.values()) {
				if (tempBet.getNumber(pos) != bet.getNumber(pos) ) {
					return null;
				}
				return tempBet;
			}
			
		}
		return null;
	}
	
	private int getNextBaseNumber(int num) {
		//if multiple of 3, return number -1 otherwise same
		//eg. 1-2-3  3 will return 2 so the numbers chosen will be 2&3 for 2 split
		//otherwise if say it's 1 or 2, it'll return the same number and that will be handled in the method
		if (num % 3 == 0) {
			return num-1;
		}
		return num;
	}
	
	@Override
	public Bet placeSplitBet(String id, int amount, int[] numbers) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id == "" || 0 > amount || players.get(id) == null) {
			throw new IllegalArgumentException();
		}
		
		if (players.get(id) != null) {
			SplitBet bet = new SplitBetImpl(players.get(id), amount, numbers);
			Bet existingBet = getCurrentSplitBet(bet);
			if (existingBet != null) {
				amount += existingBet.getAmount();
				players.get(id).cancelBet(existingBet);
				//overwrite bet with new amount
				bet = new SplitBetImpl(players.get(id), amount, numbers);
			}
			players.get(id).acceptBet(bet);
			for (GameCallback logger : loggers) {
				logger.betAccepted(players.get(id), bet, existingBet);
			}
			return bet;
		}
		return null;
	}
	
	@Override
	public Bet placeSplitBet2(String id, int amount, int baseNumber) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id == "" || 0 > amount || players.get(id) == null) {
			throw new IllegalArgumentException();
		}
		
		if (players.get(id) != null) {
			int baseNumber2 = getNextBaseNumber(baseNumber);
			if (baseNumber2 == baseNumber) {
				baseNumber2++;
			}
			SplitBet bet = new SplitBetImpl(players.get(id), amount, baseNumber, baseNumber2);
			Bet existingBet = getCurrentSplitBet(bet);
			if (existingBet != null) {
				amount += existingBet.getAmount();
				players.get(id).cancelBet(existingBet);
				//overwrite bet with new amount
				bet = new SplitBetImpl(players.get(id), amount, baseNumber, baseNumber2);
			}
			players.get(id).acceptBet(bet);
			for (GameCallback logger : loggers) {
				logger.betAccepted(players.get(id), bet, existingBet);
			}
			return bet;
		}
		return null;
	}
	
	@Override
	public Bet placeSplitBet2(String id, int amount, int number1, int number2) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id == "" || 0 > amount || players.get(id) == null) {
			throw new IllegalArgumentException();
		}
		
		if (players.get(id) != null) {
			SplitBet bet = new SplitBetImpl(players.get(id), amount, number1, number2);
			Bet existingBet = getCurrentSplitBet(bet);
			if (existingBet != null) {
				amount += existingBet.getAmount();
				players.get(id).cancelBet(existingBet);
				//overwrite bet with new amount
				bet = new SplitBetImpl(players.get(id), amount, number1, number2);
			}
			players.get(id).acceptBet(bet);
			for (GameCallback logger : loggers) {
				logger.betAccepted(players.get(id), bet, existingBet);
			}
			return bet;
		}
		return null;
	}
	
	@Override
	public Bet placeSplitBet4(String id, int amount, int baseNumber) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id == "" || 0 > amount || players.get(id) == null) {
			throw new IllegalArgumentException();
		}
		
		if (players.get(id) != null) {
			int baseNumber2 = getNextBaseNumber(baseNumber);
			int baseNumber3 = baseNumber2+3; //default to below base num2
			int baseNumber4 = baseNumber2+4; 
			if (baseNumber2 == baseNumber) {
				baseNumber2++;	
			}
			
			SplitBet bet = new SplitBetImpl(players.get(id), amount, baseNumber, baseNumber2, baseNumber3, baseNumber4);
			Bet existingBet = getCurrentSplitBet(bet);
			if (existingBet != null) {
				amount += existingBet.getAmount();
				players.get(id).cancelBet(existingBet);
				//overwrite bet with new amount
				bet = new SplitBetImpl(players.get(id), amount, baseNumber, baseNumber2, baseNumber3, baseNumber4);
			}
			players.get(id).acceptBet(bet);
			for (GameCallback logger : loggers) {
				logger.betAccepted(players.get(id), bet, existingBet);
			}
			return bet;
		}
		return null;
	}

	@Override
	public Bet placeSplitBet4(String id, int amount, int number1, int number2, int number3, int number4) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id == "" || 0 > amount || players.get(id) == null) {
			throw new IllegalArgumentException();
		}
		
		if (players.get(id) != null) {
			SplitBet bet = new SplitBetImpl(players.get(id), amount, number1, number2, number3, number4);
			Bet existingBet = getCurrentSplitBet(bet);
			if (existingBet != null) {
				amount += existingBet.getAmount();
				players.get(id).cancelBet(existingBet);
				//overwrite bet with new amount
				bet = new SplitBetImpl(players.get(id), amount, number1, number2, number3, number4);
			}
			players.get(id).acceptBet(bet);
			for (GameCallback logger : loggers) {
				logger.betAccepted(players.get(id), bet, existingBet);
			}
			return bet;
		}
		return null;
	}

	
	private static void wait(int delay)
	{
	    try
	    {
	        Thread.sleep(delay);
	    }
	    catch(InterruptedException e)
	    {
	        Thread.currentThread().interrupt();
	    }
	}

	@Override
	public Pocket spinToWin(int ticks, int delay) {
		if (1 > ticks|| 0 > delay || delay > MAX_SPIN_DELAY) {
			throw new IllegalArgumentException();
		}
		if (players.isEmpty() || !getTotalBets()) {
			throw new IllegalStateException();
		}
		
		
		int randomNum = gameWheel.generateRandomNumber();
		Pocket pocket = spin(ticks, delay, randomNum);
		applyWinLoss(ticks, pocket);
		return pocket;
	}
	
	private boolean getTotalBets() {
		for (Player plays : players.values()) {
			if (plays.getBets() != null) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Pocket spinToWin(int ticks, int delay, int startingNumber) {
		if (1 > ticks|| 0 > delay || delay > MAX_SPIN_DELAY || Wheel.NUMBER_OF_POCKETS > ticks) {
			throw new IllegalArgumentException();
		}
		if (players.isEmpty() || !getTotalBets()) {
			throw new IllegalStateException();
		}
		Pocket pocket = spin(ticks, delay, startingNumber);
		applyWinLoss(spinCount, pocket);
		return pocket;
	}

	@Override
	public Pocket spin(int ticks, int delay, int startingNumber) {
		if (1 > ticks|| 0 > delay || delay > MAX_SPIN_DELAY) {
			throw new IllegalArgumentException();
		}
		
		spinCount++;
		Pocket pocket = gameWheel.moveToNumber(startingNumber);
		for (GameCallback logger : loggers) {
			logger.spinStart(spinCount, pocket);
		}
		for (int i = 0; ticks >= i; i++) {
			pocket = gameWheel.nextPocket();
			wait(delay);
			//when final loop, result otherwise tick
			if (ticks > i) {
				for (GameCallback logger : loggers) {
					logger.spinTick(spinCount, i, pocket);
				}
			} else {
				for (GameCallback logger : loggers) {
					logger.spinResult(spinCount, pocket);
				}
			}
		}
		return pocket;
	}
	
	@Override
	public void applyWinLoss(int spinNumber, Pocket winningPocket) {
		for (Player plays : players.values()) {
			playerWin = 0;
			playerLoss = 0;
			Collection<Bet> bets = plays.getBets();
			int amount = 0;
			for (Bet bet : bets) {
				amount = bet.finaliseBet(winningPocket);
				plays.applyBetOutcome(bet);
				if (amount > 0) {
					playerWin += bet.getAmountWon();
				} else {
					playerLoss += bet.getAmount();
				}
				for (GameCallback logger : loggers) {
					logger.betResult(spinNumber, plays, bet);
				}
			}
			allPlayerTotalLoss += playerLoss;
			allPlayerTotalWin += playerWin;
			plays.resetBets();
			for (GameCallback logger : loggers) {
				logger.spinPlayerTotal(spinNumber, plays, bets, playerWin, playerLoss);
			}
		}
		for (GameCallback logger : loggers) {
			logger.spinHouseResult(spinNumber, allPlayerTotalWin, allPlayerTotalLoss);
		}
		
	}

}
