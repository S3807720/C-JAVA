package view;

import java.util.Collection;

import ausroulette.model.Player;
import ausroulette.model.bet.Bet;
import ausroulette.model.wheel.Pocket;
import ausroulette.view.GameCallback;

public class GuiCallback implements GameCallback {
	private MainWindow menu;
	
	public GuiCallback(MainWindow menu) {
		this.menu = menu;
	}

	@Override
	public void playerAdded(Player player) {
		menu.getPlayerInfo().addPlayer(player);
		menu.getStatus().updateText(String.format("%s added to the game.", player.getName()));
	}

	@Override
	public void playerRemoved(Player player) {
		menu.getPlayerInfo().removePlayer(player);
		menu.getStatus().updateText(String.format("%s has left the game.", player.getName()));
	}

	@Override
	public void pointsAdded(Player player, int points) {
		menu.getPlayerInfo().updateLabel(player);
		menu.getStatus().updateTwoText(String.format("%d points added to %s.", points, player.getName()));
	}

	@Override
	public void betAccepted(Player player, Bet bet, Bet existingBet) {
		menu.getPlayerInfo().updateLabel(player);
		menu.getStatus().updateTwoText(String.format("%s | %s.", player.getName(), bet.toString()));
	}

	@Override
	public void betCancelled(Bet bet) {
		menu.getPlayerInfo().updateLabel(bet.getPlayer());
		menu.getStatus().updateTwoText(String.format("%s cancelled their bet on %s.", bet.getPlayer().getName(), bet.toString()));
	}

	@Override
	public void spinStart(int spinNumber, Pocket pocket) {
		//this will set the base arrow position
		new Thread(){
			public void run() {
				menu.getWheelPanel().setBaseSpinPosition();			
				//blabla block menu functions somehow
			}
        
        }.start();
		
	}

	@Override
	public void spinTick(int spinNumber, int tick, Pocket pocket) {
		// this will increment
		new Thread(){
			public void run() {
				menu.getWheelPanel().spin();		
			}
        
        }.start();
		
	}

	@Override
	public void spinResult(int spinNumber, Pocket pocket) {
		menu.getStatus().updateText(String.format("Spin #%d landed on %s %d.", 
				spinNumber, pocket.getColor().toString(), pocket.getNumber()));

	}

	@Override
	public void betResult(int spinNumber, Player player, Bet bet) {
		menu.getPlayerInfo().updateLabel(player);
		menu.getStatus().updateTwoText(String.format("%s has won a %s bet.", player.getName(), bet.getBetType()));
	}

	@Override
	public void spinPlayerTotal(int spinNumber, Player player, Collection<Bet> bets, int amountWon, int amountLost) {
		menu.getPlayerInfo().updateLabel(player);
		new ResultsScreen(spinNumber, player, bets, amountWon, amountLost);
	}

	@Override
	public void spinHouseResult(int spinNumber, int allPlayerTotalWin, int allPlayerTotalLoss) {
		menu.getStatus().updateText(String.format("Players have won %d.", allPlayerTotalWin));
		menu.getStatus().updateTwoText(String.format("House has won %d.", allPlayerTotalLoss));

	}

}
