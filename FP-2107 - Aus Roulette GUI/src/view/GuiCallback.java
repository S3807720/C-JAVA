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
	}

	@Override
	public void playerRemoved(Player player) {
		menu.getPlayerInfo().removePlayer(player);
		
	}

	@Override
	public void pointsAdded(Player player, int points) {
		menu.getPlayerInfo().updateLabel(player);
	}

	@Override
	public void betAccepted(Player player, Bet bet, Bet existingBet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void betCancelled(Bet bet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spinStart(int spinNumber, Pocket pocket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spinTick(int spinNumber, int tick, Pocket pocket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spinResult(int spinNumber, Pocket pocket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void betResult(int spinNumber, Player player, Bet bet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spinPlayerTotal(int spinNumber, Player player, Collection<Bet> bets, int amountWon, int amountLost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spinHouseResult(int spinNumber, int allPlayerTotalWin, int allPlayerTotalLoss) {
		// TODO Auto-generated method stub
		
	}

}
