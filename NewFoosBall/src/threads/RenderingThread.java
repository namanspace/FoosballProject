package threads;

import exceptions.UnsupportedMethodCalledException;
import game.Field;
import game.Team;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mainclasses.Game;
import notifications.Notification;
import basicUI.EndGameScreen;
import basicUI.GraphicData;
import basicUI.UI;

public class RenderingThread implements Runnable{

	Game game;
	Field field;
	UI ui;

	public RenderingThread(	final Game game) {
		this.field = game.getField();
		this.game = game;
		
		ui = new UI(game);
		ui.initUI();
		
		ui.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e)
			{
				/* TODO No implementation required. */
			}
			@Override
			public void keyReleased(KeyEvent e)
			{
				/* TODO No implementation required. */
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				try
				{
					if ((e.getKeyCode() == KeyEvent.VK_W) || (e.getKeyCode() == KeyEvent.VK_S))
					{
						field.getTeamB().move((e.getKeyCode() ==  KeyEvent.VK_W));
					}
					if ((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
					{
						field.getTeamA().move((e.getKeyCode() ==  KeyEvent.VK_UP));
					}
				} catch (UnsupportedMethodCalledException e1)
				{
					e1.printStackTrace();
				}
			}
		});
	}

	private void handleNotification(Notification notification)
	{
		notification.handleByRenderingThread();
	}
	  private void endGame(Team team, UI ui) {
	    	ui.setVisible(false);
		  	EndGameScreen endScreen = new EndGameScreen(this.game, team);
		  	endScreen.setVisible(true);
		  	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  	endScreen.setVisible(false);
		  	System.exit(0);
	   }
	
	public void run()
	{
		while (true)
		{
			while(true)
			{
				if (game.notification == null) break;
				if (!game.notification.isHandlingLeft())
				{
					System.out.println("breaking");
					break;
				}
				this.handleNotification(game.notification);
			}
//			System.out.println("Setting null");
			this.game.notification = null;
//			System.out.println("Rendering thread running");
			this.ui.repaint();
			
			if((this.game.getTimer().getClock() > 5*60) || (this.game.getScoreBoard().getScoreTeamA() == 5) || (this.game.getScoreBoard().getScoreTeamB() == 5)) {
				if (this.game.getScoreBoard().getScoreTeamA() > this.game.getScoreBoard().getScoreTeamB())
					endGame(game.getField().getTeamA(),this.ui);
				else 		endGame(game.getField().getTeamB(),this.ui);	
				break;
			}
			
			try
			{
				Thread.sleep(1000/GraphicData.FPS);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
