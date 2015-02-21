package foosballTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import game.Ball;
import game.Formation;
import game.Team;
import game.TeamSide;
import mainclasses.Game;
import misc.CoinSide;
import misc.Position;

import org.testng.annotations.Test;

import difficulty.DifficultyContext;
import difficulty.EasyDifficulty;
import exceptions.BallAlreadyCreatedException;

public class ConcedingTeamGKHasTheBallAfterAGoal {
	
	public Game init() {
		DifficultyContext context = new DifficultyContext(new EasyDifficulty());
		Game game=null;
		try {
			game = new Game(context, new Formation(4, 3, 3), TeamSide.LEFT, CoinSide.HEADS);
		} catch (BallAlreadyCreatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;	
	}
	@Test
	public void concedingTeamGKHasTheBallAfterAGoal() {
		Game game = init();
		Ball ball = game.getField().getBall();
		
		int ballPossitionAfterGoal = new Position(ball.getBallPosition()).getX();
		Team concedingTeam = ball.getLastContactTeam().getOpponent();
		int concedingTeamGKPostion = new Position(concedingTeam.getPositionOfGoalKeeper()).getX();

		assertThat(ballPossitionAfterGoal, is(equalTo(concedingTeamGKPostion)));
	}
}
