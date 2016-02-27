/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.golden.gamedev.object.Sprite;
import java.awt.image.BufferedImage;

/**
 *
 * @author Eoin Costelloe
 */
public class GoalSprite extends Sprite
{
    int score;
    //when someone scores the ball, the goal gains control of the ball
    BallSprite currentBall;

    GoalSprite(BufferedImage image, double x, double y)
    {
        super(image,x,y);

        //keep the score for the current team
        score = 0;
        currentBall = null;
    }

    void score(BallSprite inputBall)
    {
        score++;
        currentBall = inputBall;
        currentBall.setSpeed(0, 0);
        currentBall.gotPossesion();
    }

    void lostBall()
    {
        //no point in calling lostposession as it has no effect on goal
        currentBall = null;
    }

    boolean hasBall()
    {
        if(currentBall == null)
            return false;
        return true;
    }

    int getScore()
    {
        return score;
    }
}
