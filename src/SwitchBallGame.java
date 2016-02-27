
import com.golden.gamedev.Game;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.funbox.ErrorNotificationDialog;
import com.golden.gamedev.util.ImageUtil;
import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import net.java.games.input.Controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eoin Costelloe
 */
public class SwitchBallGame extends GameEngine
{
    { distribute = true; }
    
    //attributes of game
    String currentMap;
    Team team1;
    Team team2;
    int difficulty;
    int timeLimit;
    boolean menu;
    Controller player1Controller;
    Controller player2Controller;

    SwitchBallGame(String inputMap, Team inputTeam1, Team inputTeam2, int inputDifficulty, int inputTimeLimit, boolean inputMenu, Controller inputPlayer1Controller, Controller inputPlayer2Controller)
    {
        currentMap = inputMap;
        team1 = inputTeam1;
        team2 = inputTeam2;
        difficulty = inputDifficulty;
        timeLimit = inputTimeLimit;
        menu = inputMenu;
        player1Controller = inputPlayer1Controller;
        player2Controller = inputPlayer2Controller;
    }

    SwitchBallGame()
    {
        currentMap = "Grass";
        team1 = new Team("Sara", new Player("Beetle", 0.1, 120, 60, 100, 1), new Player("Sara", 0.1, 110, 80, 100, 1.1), new Player("Wolf", 0.1, 120, 80, 100, 1));
        team2 = new Team("Imps", new Player("GreenImp", 0.15, 80, 80, 100, 0.9), new Player("BlueImp", 0.13, 90, 80, 110, 0.9), new Player("RedImp", 0.15, 80, 80, 100, 0.9));
        difficulty = PlayerSprite.MEDIUM;
        timeLimit = 60;
        menu = false;
        player1Controller = null;
        player2Controller = null;
    }

    @Override
    protected void notifyError(Throwable error)
    {
        new ErrorNotificationDialog(error, bsGraphics, "Switch Ball", "support@fluffymachappy.com");
    }

    @Override
    protected void initEngine()
    {
        super.initEngine();
    }

    public GameObject getGame(int GameID)
    {
        if(GameID == 0)
        {
            return new SwitchBall(this, currentMap, team1, team2, difficulty, timeLimit, menu, player1Controller, player2Controller);
        }
        if(GameID == 1)
        {
            return new Podium(this, team1, menu);
        }
        if(GameID == 2)
        {
            return new Podium(this, team2, menu);
        }
        if(GameID == 3)
        {
            return new Podium(this, menu);
        }

        return null;
    }

    public static void main(String [] args)
    {
        GameLoader game = new GameLoader();
        game.setup(new SwitchBallGame(), new Dimension(SwitchBall.WIDTH + 360,SwitchBall.HEIGHT), false);
        game.start();
    }

    //this method overrides the games one
    //because we do not want to call System.exit(0) when the game ends
    //we still want our main menu to run
    @Override
    protected void notifyExit()
    {
        if(this.bsGraphics instanceof Applet)
        {
            // applet game should display to the user
            // that the game has been ended
            final Applet applet = (Applet) this.bsGraphics;
            BufferedImage src = ImageUtil.createImage(this.getWidth(), this.getHeight());
            Graphics2D g = src.createGraphics();

            try
            {
                // fill background
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                // play with transparency a bit
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));

                // draw in a circle only
                Shape shape = new java.awt.geom.Ellipse2D.Float(
                        this.getWidth() / 10, this.getHeight() / 10, this
                                .getWidth()
                                - (this.getWidth() / 10 * 2), this.getHeight()
                                - (this.getHeight() / 10 * 2));
                g.setClip(shape);

                // draw the game unto this image
                if (this instanceof GameEngine)
                {
                    ((GameEngine) this).getCurrentGame().render(g);
                }
                this.render(g);

                g.dispose();
            }
            catch (Exception e) {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, this.getWidth(), this.getHeight());
                    g.dispose();
            }

            // make it as gray
            BufferedImage converted = null;
            try
            {
                // technique #1
                // ColorSpace gray = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                // converted = new ColorConvertOp(gray, null).filter(src, null);

                // technique #2
                BufferedImage image = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                Graphics gfx = image.getGraphics();
                gfx.drawImage(src, 0, 0, null);
                gfx.dispose();
                converted = image;

                // technique #3
                // ImageFilter filter = new GrayFilter(true, 75);
                // ImageProducer producer = new
                // FilteredImageSource(colorImage.getSource(), filter);
                // Image mage = this.createImage(producer);

            } catch(Throwable e) {}
            final BufferedImage image = (converted != null) ? converted : src;

            applet.removeAll();
            applet.setIgnoreRepaint(false);

            Canvas canvas = new Canvas()
            {

                /**
                 *
                 */
                private static final long serialVersionUID = 8493852179266447783L;

                @Override
                public void paint(Graphics g1)
                {
                    Graphics2D g = (Graphics2D) g1;

                    // draw game image
                    g.drawImage(image, 0, 0, null);

                    // draw text
                    g.setColor(Color.YELLOW);
                    g.setFont(new Font("Verdana", Font.BOLD, 12));
                    g.drawString("Game has been ended", 10, 25);
                    g.drawString("Thank you for playing!", 10, 45);
                    g.drawString("Visit http://www.goldenstudios.or.id/", 10,
                            75);
                    g.drawString("For free game engine!", 10, 95);
                    g.drawString("This game is developed with GTGE v"
                            + Game.GTGE_VERSION, 10, 115);
                }
            };
            canvas.setSize(applet.getSize());
            canvas.addMouseListener(new MouseAdapter()
            {
                @Override
                    public void mouseClicked(MouseEvent e) {
                            try {
                                    applet.getAppletContext().showDocument(new URL("http://goldenstudios.or.id/"));
                            }
                            catch (Exception excp) {
                            }
                    }
            });

            applet.add(canvas);
            applet.repaint();
            canvas.repaint();
        }
    }
}
