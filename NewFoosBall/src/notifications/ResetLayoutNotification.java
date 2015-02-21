package notifications;

import game.Field;
import game.Team;
import mainclasses.Game;
import basicUI.UI;

public class ResetLayoutNotification extends Notification
{
	Field field;
	Team winner;
	
	public ResetLayoutNotification(Game game, Team winner)
	{
		super(game);
		this.winner = winner;
	}
	
	@Override
	public void handleByTeamLogicThread()
	{
		// each team threads handles its own team
		if (this.game.getField().getTeamA().threadID == Thread.currentThread().getId())
		{
			if (this.handledByTeamLogicThreadA)
			{
				try
				{
					Thread.sleep(10);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				this.game.getField().resetPositions();
				
				this.game.getField().getBall().setLastContactTeam(this.winner.getOpponent());
				this.handledByTeamLogicThreadA = true;
				//		System.out.println("Handled by teamA thread");
			}
		}
		else
		{
			if (this.handledByTeamLogicThreadB)
			{
				try
				{
					Thread.sleep(100);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				this.game.getField().resetPositions();
				this.handledByTeamLogicThreadB = true;
			}
//			System.out.println("Handled by teamB thread");
		}
	}

	@Override
	public void handleByRenderingThread()
	{
		if (this.handledByRenderingThread)
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
		{
			UI.updateScore(this.winner.getGame().getScoreBoard().getScoreTeamA(),
					this.winner.getGame().getScoreBoard().getScoreTeamB());
			this.handledByRenderingThread = true;
//			System.out.println("Handled by rendering thread");
		}
		
	}

	@Override
	public void handleByMainThread()
	{
//		if (hand)
		
		// TODO will not do anything. or maybe it will restore the initial 
		this.handledByMainThread = true;
		System.out.println("Handled by main thread");
	}
	
	@Override
	public void handleByBallLogicThread()
	{
		while((!this.handledByTeamLogicThreadA) || (!this.handledByTeamLogicThreadB))
		{
			try
			{
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (this.handledByBallLogicThread)
		{
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			this.game.getField().getBall().resetBallPosition(winner);
			this.handledByBallLogicThread = true;
		}
//		System.out.println("Handled by ball logic thread");
	}

}
