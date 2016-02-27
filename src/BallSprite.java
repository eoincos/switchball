/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.golden.gamedev.object.AnimatedSprite;
import java.awt.image.BufferedImage;

/**
 *
 * @author Eoin Costelloe
 */
public class BallSprite extends AnimatedSprite
{
    PlayerSprite playerThrewBall;
    boolean inPossesion;
    double power;

    BallSprite(BufferedImage[] currentImage)
    {
        //setup sprite animation
        super(currentImage);
        getAnimationTimer().setDelay(80);
        setAnimationFrame(0, 1);
        setAnimate(true);
        setLoopAnim(true);
        playerThrewBall = null;
        inPossesion = false;
        power = 0;
    }

    @Override
    public void update(long elapsedTime)
    {
        //no reason to reduce power if it has none
        if(power > 0)
        {
            //reduce power as it travels through the air
            power -= elapsedTime / 10.0;

            //minimum of 0
            if(power < 0)
                power = 0;
        }

        super.update(elapsedTime);
    }

    void reducePower(double strength, double elapsedTime)
    {
        //no reason to reduce power if it has none
        if(power > 0)
        {
            //reduce power by a factor
            power -= (strength * elapsedTime) / 30.0;

            //minimum of 0
            if(power < 0)
                power = 0;
        }
    }

    PlayerSprite getPlayerThrown()
    {
        return playerThrewBall;
    }

    void gotPossesion()
    {
        inPossesion = true;
        playerThrewBall = null;
        power = 0;
    }

    double getPower()
    {
        return power;
    }

    void lostPossesion(PlayerSprite inputPlayer)
    {
        inPossesion = false;
        playerThrewBall = inputPlayer;
        power = inputPlayer.getPower();
    }

    boolean hasPossesion()
    {
        return inPossesion;
    }
}
