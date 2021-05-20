package client;

import ausroulette.model.GameEngineImpl;
import view.GuiCallback;
import view.MainWindow;
import ausroulette.view.LoggerCallback;

public class GuiGameEngine {

	public static void main(String[] args) {
		GameEngineImpl ge = new GameEngineImpl();
		
		MainWindow menu = new MainWindow(ge);
		GuiCallback gui = new GuiCallback(menu);

		ge.registerCallback(gui);
		ge.registerCallback(new LoggerCallback());
		
		menu.setVisible(true);
	}

}
