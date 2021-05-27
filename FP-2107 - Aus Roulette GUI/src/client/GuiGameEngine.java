package client;

import javax.swing.SwingUtilities;

import ausroulette.model.GameEngineImpl;
import ausroulette.view.LoggerCallback;
import view.GuiCallback;
import view.MainWindow;

public class GuiGameEngine {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				GameEngineImpl ge = new GameEngineImpl();

				MainWindow menu = new MainWindow(ge);

				ge.registerCallback(new GuiCallback(menu));
				ge.registerCallback(new LoggerCallback());

				menu.setVisible(true);
			}
		}); 

	}

}
