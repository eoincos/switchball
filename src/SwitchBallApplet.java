/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;

/**
 *
 * @author Eoin Costelloe
 */
public class SwitchBallApplet extends GameLoader
{
    @Override
    protected Game createAppletGame()
    {
        return new SwitchBallGame();
    }

    /*
        if you want to embed this game onto a webiste you can use the following code
        this applet however requires special permissions as it reads from files in the jar
        
        <applet code="SwitchBallApplet.class" archive="Game/SwitchBall.jar,Game/lib/golden_0_2_3.jar" width="960px" height="600px">
        <p>My game requires java, sorry</p>
        </applet>
     */
}