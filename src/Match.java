/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import net.java.games.input.Controller;
import com.golden.gamedev.GameLoader;
import java.awt.Dimension;

/**
 *
 * @author Eoin Costelloe
 */
public class Match extends Thread
{
    String currentMap;
    Team team1;
    Team team2;
    int difficulty;
    int timeLimit;
    Controller player1Controller;
    Controller player2Controller;

    Match(String inputMap, Team inputTeam1, Team inputTeam2, int inputDifficulty, int inputTimeLimit, Controller inputPlayer1Controller, Controller inputPlayer2Controller)
    {
        currentMap = inputMap;
        team1 = inputTeam1;
        team2 = inputTeam2;
        difficulty = inputDifficulty;
        timeLimit = inputTimeLimit;
        player1Controller = inputPlayer1Controller;
        player2Controller = inputPlayer2Controller;
    }

    Match()
    {
        currentMap = "Grass";
        team1 = new Team("Sara", new Player("Beetle", 0.1, 120, 60, 100, 1), new Player("Sara", 0.1, 110, 80, 100, 1.1), new Player("Wolf", 0.1, 120, 80, 100, 1));
        team2 = new Team("Imps", new Player("GreenImp", 0.15, 80, 80, 100, 0.9), new Player("BlueImp", 0.13, 90, 80, 110, 0.9), new Player("RedImp", 0.15, 80, 80, 100, 0.9));
        difficulty = PlayerSprite.MEDIUM;
        timeLimit = 60;
        player1Controller = null;
        player2Controller = null;
    }

    @Override
    public void run()
    {
        GameLoader game = new GameLoader();
        game.setup(new SwitchBallGame(currentMap, team1, team2, difficulty, timeLimit, true, player1Controller, player2Controller), new Dimension(SwitchBall.WIDTH + 360, SwitchBall.HEIGHT), false);
        game.start();
    }
}
