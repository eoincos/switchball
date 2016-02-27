
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.background.ColorBackground;
import com.golden.gamedev.object.font.BitmapFont;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eoin Costelloe
 */
public class Podium extends GameObject
{
    Team winningTeam;
    boolean menu;

    BitmapFont displayFont;
    PlayField myPlayfield;
    Background background;

    SpriteGroup PLAYER_GROUP;
    
    //used for when a team wins
    Podium(GameEngine parent, Team inputTeam, boolean inputMenu)
    {
       super(parent);
       
       winningTeam = inputTeam;
       menu = inputMenu;
    }
    
    //used for when its a draw
    Podium(GameEngine parent, boolean inputMenu)
    {
       super(parent);

       winningTeam = null;
       menu = inputMenu;
    }

    public void initResources()
    {
        displayFont = new BitmapFont(getImages("Font.jpg", 20, 5));
        background = new ColorBackground(new Color(255, 127, 39));
        myPlayfield = new PlayField(background);
        PLAYER_GROUP = myPlayfield.addGroup(new SpriteGroup("Player Group"));

        if(winningTeam != null)
        {
            AnimatedSprite currentPlayer = new AnimatedSprite(getImages("Characters/" + winningTeam.getLeftPlayer().getName() + ".png", 2, 4), SwitchBall.WIDTH / 2, SwitchBall.HEIGHT / 2);
            currentPlayer.getAnimationTimer().setDelay(200);
            currentPlayer.setAnimate(true);
            currentPlayer.setLoopAnim(true);
            PLAYER_GROUP.add(currentPlayer);

            currentPlayer = new AnimatedSprite(getImages("Characters/" + winningTeam.getCenterPlayer().getName() + ".png", 2, 4), (SwitchBall.WIDTH / 2) + 50, SwitchBall.HEIGHT / 2);
            currentPlayer.getAnimationTimer().setDelay(200);
            currentPlayer.setAnimate(true);
            currentPlayer.setLoopAnim(true);
            PLAYER_GROUP.add(currentPlayer);

            currentPlayer = new AnimatedSprite(getImages("Characters/" + winningTeam.getRightPlayer().getName() + ".png", 2, 4), (SwitchBall.WIDTH / 2) + 100, SwitchBall.HEIGHT / 2);
            currentPlayer.getAnimationTimer().setDelay(200);
            currentPlayer.setAnimate(true);
            currentPlayer.setLoopAnim(true);
            PLAYER_GROUP.add(currentPlayer);

            playSound("Sounds/mission_completed.wav");
        }
        else
        {
            playSound("Sounds/game_over.wav");
        }
    }

    public void update(long elapsedTime)
    {
        //update playfield
        myPlayfield.update(elapsedTime);

        if(keyPressed(KeyEvent.VK_SPACE))
        {
            parent.nextGameID = 0;
            finish();
        }

        if(keyPressed(KeyEvent.VK_ESCAPE))
        {
            finish();

            if(menu)
            {
                new MenuFrame().setVisible(true);
            }
        }
    }

    public void render(Graphics2D g)
    {
        myPlayfield.render(g);

        if(winningTeam != null)
            displayFont.drawString(g, winningTeam.getName() + " Won The Game!", SwitchBall.WIDTH/2, 103);
        else
            displayFont.drawString(g, "It Was A Draw", SwitchBall.WIDTH/2, 103);

        displayFont.drawString(g, "Press Space To Play Again", SwitchBall.WIDTH/2, 503);
        displayFont.drawString(g, "Press Escape To Quit", SwitchBall.WIDTH/2, 553);
    }
}
