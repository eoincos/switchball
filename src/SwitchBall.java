 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.CollisionManager;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.collision.PreciseCollisionGroup;
import com.golden.gamedev.object.font.BitmapFont;
import com.golden.gamedev.object.sprite.AdvanceSprite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

/**
 *
 * @author Eoin Costelloe
 */
public class SwitchBall extends GameObject
{
    public static final int WIDTH = 600, HEIGHT = 600;
    //attributes of game
    String currentMap;
    Team team1;
    Team team2;
    int difficulty;
    int timeLimit;
    boolean menu;

    Controller player1Controller;
    Controller player2Controller;

    boolean AReleaseP1;
    boolean UpReleaseP1;
    boolean DownReleaseP1;

    boolean AReleaseP2;
    boolean UpReleaseP2;
    boolean DownReleaseP2;

    int currentTime;
    boolean paused;
    //this allows us to play a sound to notify the game si close to an end
    int countdown;
    //state of the game:
    //0: players moving
    //1: player 1 is choosing player to pass it to
    //2: player 2 is choosing player to pass it to
    //3: ball is being passed
    int currentGameState;
    //time to wait in millis
    long waitTime;
    String waitAction;
    //list of people intercepting the ball
    ArrayList<PlayerSprite> playersIntercepting;
    //current font
    BitmapFont displayFont;
    PlayField myPlayfield;
    Background background;
    //setup goals
    GoalSprite team1Goal;
    GoalSprite team2Goal;
    //setup players
    ArrayList<PlayerSprite> team1Players;
    ArrayList<PlayerSprite> team2Players;
    PlayerSprite player1;
    AdvanceSprite player1Location;
    PlayerSprite player2;
    AdvanceSprite player2Location;
    //setup ball
    BallSprite currentBall;
    //for when a player passes the ball
    Sprite spriteToPassTeam1;
    Sprite spriteToPassTeam2;
    Sprite currentTarget;
    //groups and collisions
    SpriteGroup GOAL_GROUP, WALL_GROUP, PLAYER_GROUP, BALL_GROUP, OVERLAY_GROUP;
    CollisionManager  collisionPlayerPlayer, collisionBallPlayer, collisionPlayerWall, collisionPlayerGoal, collisionBallGoal;

    SwitchBall(GameEngine parent, String inputMap, Team inputTeam1, Team inputTeam2, int inputDifficulty, int inputTimeLimit, boolean inputMenu, Controller inputPlayer1Controller, Controller inputPlayer2Controller)
    {
        super(parent);
        currentMap = inputMap;
        team1 = inputTeam1;
        team2 = inputTeam2;
        difficulty = inputDifficulty;
        timeLimit = inputTimeLimit;
        menu = inputMenu;

        player1Controller = inputPlayer1Controller;
        player2Controller = inputPlayer2Controller;

        AReleaseP1 = true;
        UpReleaseP1 = true;
        DownReleaseP1 = true;

        AReleaseP2 = true;
        UpReleaseP2 = true;
        DownReleaseP2 = true;
    }

    public void initResources()
    {
        //default player state
        currentTime = timeLimit*1000;
        paused = false;
        countdown = 10;
        currentGameState = 0;
        waitTime = 0;
        waitAction = "";
        playersIntercepting = new ArrayList<PlayerSprite>();
        displayFont = new BitmapFont(getImages("Font.jpg", 20, 5));

        //setup the play field
        if(currentMap != null && !currentMap.equals(""))
            background = new ImageBackground(getImage("Maps/" + currentMap + ".jpg"));
        else
            background = new ImageBackground(getImage("Maps/Grass.jpg"));
        myPlayfield = new PlayField(background);
        GOAL_GROUP = myPlayfield.addGroup(new SpriteGroup("Goal Group"));
        WALL_GROUP = myPlayfield.addGroup(new SpriteGroup("Wall Group"));
        PLAYER_GROUP = myPlayfield.addGroup(new SpriteGroup("Player Group"));
        BALL_GROUP = myPlayfield.addGroup(new SpriteGroup("Ball Group"));
        OVERLAY_GROUP = myPlayfield.addGroup(new SpriteGroup("Target Group"));

        //create walls
        //left wall
        WALL_GROUP.add(new Sprite(getImage("Wall1.jpg"), 30, 50));
        //right wall
        WALL_GROUP.add(new Sprite(getImage("Wall1.jpg"), (WIDTH - 50), 50));
        //top wall
        WALL_GROUP.add(new Sprite(getImage("Wall2.jpg"), 50, 30));
        //bottom wall
        WALL_GROUP.add(new Sprite(getImage("Wall2.jpg"), 50, (HEIGHT - 50)));

        //create Goals
        team1Goal = new GoalSprite(getImage("Pillar.png"), ((WIDTH / 2) - 15), (HEIGHT - 88));
        GOAL_GROUP.add(team1Goal);
        team2Goal = new GoalSprite(getImage("Pillar.png"), ((WIDTH / 2) - 15), 48);
        GOAL_GROUP.add(team2Goal);

        //create players
        team1Players = new ArrayList<PlayerSprite>();
        team2Players = new ArrayList<PlayerSprite>();

        Player currentPlayer;

        //the more steps an AI player goes before changing its mood
        //means its easier to predict
        int numberSteps;
        if(difficulty == PlayerSprite.EASY)
        {
            numberSteps = 100;
        }
        else if(difficulty == PlayerSprite.MEDIUM)
        {
            numberSteps = 30;
        }
        else if(difficulty == PlayerSprite.HARD)
        {
            numberSteps = 5;
        }
        else
        {
            numberSteps = 30;
        }

        //create team 1
        currentPlayer = team1.getLeftPlayer();
        PlayerSprite leftPlayerTeam1 = new PlayerSprite(currentPlayer.getName(), getImages("Characters/" + currentPlayer.getName() + ".png", 2, 4), (WIDTH / 3), ((HEIGHT / 3) * 2), currentPlayer.getSpeed(), currentPlayer.getPower(), currentPlayer.getTackleRange(), currentPlayer.getInterceptRange(), currentPlayer.getInterceptStrength(), 1, 0, numberSteps);
        PLAYER_GROUP.add(leftPlayerTeam1);
        team1Players.add(leftPlayerTeam1);

        currentPlayer = team1.getCenterPlayer();
        PlayerSprite centerPlayerTeam1 = new PlayerSprite(currentPlayer.getName(), getImages("Characters/" + currentPlayer.getName() + ".png", 2, 4), (WIDTH / 2), ((HEIGHT / 3) * 2), currentPlayer.getSpeed(), currentPlayer.getPower(), currentPlayer.getTackleRange(), currentPlayer.getInterceptRange(), currentPlayer.getInterceptStrength(), 1, 1, numberSteps);
        PLAYER_GROUP.add(centerPlayerTeam1);
        team1Players.add(centerPlayerTeam1);

        currentPlayer = team1.getRightPlayer();
        PlayerSprite rightPlayerTeam1 = new PlayerSprite(currentPlayer.getName(), getImages("Characters/" + currentPlayer.getName() + ".png", 2, 4), ((WIDTH / 3) * 2), ((HEIGHT / 3) * 2), currentPlayer.getSpeed(), currentPlayer.getPower(), currentPlayer.getTackleRange(), currentPlayer.getInterceptRange(), currentPlayer.getInterceptStrength(), 1, 2, numberSteps);
        PLAYER_GROUP.add(rightPlayerTeam1);
        team1Players.add(rightPlayerTeam1);

        //create team 2
        currentPlayer = team2.getLeftPlayer();
        PlayerSprite leftPlayerTeam2 = new PlayerSprite(currentPlayer.getName(), getImages("Characters/" + currentPlayer.getName() + ".png", 2, 4), (WIDTH / 3), (HEIGHT / 3), currentPlayer.getSpeed(), currentPlayer.getPower(), currentPlayer.getTackleRange(), currentPlayer.getInterceptRange(), currentPlayer.getInterceptStrength(), 2, 0, numberSteps);
        PLAYER_GROUP.add(leftPlayerTeam2);
        team2Players.add(leftPlayerTeam2);

        currentPlayer = team2.getCenterPlayer();
        PlayerSprite centerPlayerTeam2 = new PlayerSprite(currentPlayer.getName(), getImages("Characters/" + currentPlayer.getName() + ".png", 2, 4), (WIDTH / 2), (HEIGHT / 3), currentPlayer.getSpeed(), currentPlayer.getPower(), currentPlayer.getTackleRange(), currentPlayer.getInterceptRange(), currentPlayer.getInterceptStrength(), 2, 1, numberSteps);
        PLAYER_GROUP.add(centerPlayerTeam2);
        team2Players.add(centerPlayerTeam2);

        currentPlayer = team2.getRightPlayer();
        PlayerSprite rightPlayerTeam2 = new PlayerSprite(currentPlayer.getName(), getImages("Characters/" + currentPlayer.getName() + ".png", 2, 4), ((WIDTH / 3) * 2), (HEIGHT / 3), currentPlayer.getSpeed(), currentPlayer.getPower(), currentPlayer.getTackleRange(), currentPlayer.getInterceptRange(), currentPlayer.getInterceptStrength(), 2, 2, numberSteps);
        PLAYER_GROUP.add(rightPlayerTeam2);
        team2Players.add(rightPlayerTeam2);

        //setup player 1 and player 2
        player1 = centerPlayerTeam1;
        player2 = centerPlayerTeam2;

        //give ball to player
        currentBall = new BallSprite(getImages("Ball.png", 2, 1));
        player1.gotBall(currentBall);
        BALL_GROUP.add(currentBall);
        
        //setup the sprites showing the players location
        player1Location = new AdvanceSprite(getImages("Player1Location.png", 2, 1), player1.getX(), player1.getY() - 27);
        player1Location.getAnimationTimer().setDelay(400);
        player1Location.setAnimationFrame(0, 1);
        player1Location.setAnimate(true);
        player1Location.setLoopAnim(true);
        OVERLAY_GROUP.add(player1Location);
        player2Location = new AdvanceSprite(getImages("Player2Location.png", 2, 1), player1.getX(), player1.getY() - 27);
        player2Location.getAnimationTimer().setDelay(400);
        player2Location.setAnimationFrame(0, 1);
        player2Location.setAnimate(true);
        player2Location.setLoopAnim(true);
        OVERLAY_GROUP.add(player2Location);

        //for passing the ball
        spriteToPassTeam1 = team2Goal;
        spriteToPassTeam2 = team1Goal;
        currentTarget = null;

        //add collision events
        collisionPlayerPlayer = new PlayerPlayerCollision();
        myPlayfield.addCollisionGroup(PLAYER_GROUP, PLAYER_GROUP, collisionPlayerPlayer);
        collisionBallPlayer = new BallPlayerCollision();
        myPlayfield.addCollisionGroup(BALL_GROUP, PLAYER_GROUP, collisionBallPlayer);
        collisionPlayerWall = new PlayerWallCollision();
        myPlayfield.addCollisionGroup(PLAYER_GROUP, WALL_GROUP, collisionPlayerWall);
        collisionPlayerGoal = new PlayerGoalCollision();
        myPlayfield.addCollisionGroup(PLAYER_GROUP, GOAL_GROUP, collisionPlayerGoal);
        collisionBallGoal = new BallGoalCollision();
        myPlayfield.addCollisionGroup(BALL_GROUP, GOAL_GROUP, collisionBallGoal);
    }

    public void update(long elapsedTime)
    {
        //update playfield
        myPlayfield.update(elapsedTime);

        if((currentGameState == 0)&&(!paused))
        {
            //move AI players
            moveAIPlayers(elapsedTime);

            //take user input and move player 1
            movePlayer1(elapsedTime);

            //take user input and move player 2
            movePlayer2(elapsedTime);

            checkPlayersTackling();

            //update the time
            currentTime -= elapsedTime;

            if((currentTime < ((countdown + 1)*1000))&&(countdown > 0))
            {
                playSound("Sounds/" + countdown + ".wav");

                //move to the next second interva;
                countdown--;
            }

            //finish up the game if the time limit is reached
            if(currentTime <= 0)
            {
                //if team 1 won
                if(team1Goal.getScore() < team2Goal.getScore())
                {
                    parent.nextGameID = 1;
                    finish();
                }
                //if team 2 won
                else if(team1Goal.getScore() > team2Goal.getScore())
                {
                    parent.nextGameID = 2;
                    finish();
                }
                //if it was a draw
                else
                {
                    parent.nextGameID = 3;
                    finish();
                }
            }
        }
        //new state where players cannot move and player 1 chooses the sprite to pass it to
        else if(currentGameState == 1)
        {
            if(currentTarget == null)
            {
                //put a target over the default sprite to pass it to
                currentTarget = new Sprite(getImage("Target.png"), spriteToPassTeam1.getX(), spriteToPassTeam1.getY());
                OVERLAY_GROUP.add(currentTarget);
            }

            player1PassBall();
        }
        //new state where players cannot move and player 2 chooses who to pass it to
        else if(currentGameState == 2)
        {
            if(currentTarget == null)
            {
                //put a target over the default sprite to pass it to
                currentTarget = new Sprite(getImage("Target.png"), spriteToPassTeam2.getX(), spriteToPassTeam2.getY());
                OVERLAY_GROUP.add(currentTarget);
            }

            player2PassBall();
        }
        else if(currentGameState == 3)
        {
            //as the ball is travelling
            if(!currentBall.hasPossesion())
            {
                //reduces the power of the ball by their strength if they are intercepting
                checkPlayersInterceptingBall(elapsedTime);

                //if the ball was fumbled
                if(currentBall.getPower() == 0)
                {
                    ballFumbled();
                }
            }
            //find out who caught the ball
            else
            {
                //find the player who caught the ball
                checkPlayerCaughtBall();

                //check if goals have the sprite
                checkGoalHasBall();
            }
        }
        //wait while the user reacts to the action
        else if((currentGameState == 4)&&(System.currentTimeMillis() > waitTime))
        {
            //reset to start state
            playersIntercepting = new ArrayList<PlayerSprite>();
            currentGameState = 0;
            waitAction = "";
        }

        if((keyPressed(KeyEvent.VK_P))&&((currentGameState == 0)||(currentGameState == 4)))
        {
            if(paused)
            {
                paused = false;
                playSound("Sounds/go.wav");
            }
            else
            {
                paused = true;
                playSound("Sounds/hold.wav");
            }
        }

        //update the location sprites to show where the players are
        player1Location.setLocation(player1.getX(), player1.getY() - 27);
        player2Location.setLocation(player2.getX(), player2.getY() - 27);

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

        //display the score for team 1 and team 2
        String team1Name;
        if(team1.getName().length() > 6)
            team1Name = "Team 1:" + team1.getName().substring(0, 6);
        else
            team1Name = "Team 1:" + team1.getName();
        
        String team1Score = Integer.toString(team1Goal.getScore());
        int locationOfTeam1Name = 10;

        String team2Name;
        if(team2.getName().length() > 6)
            team2Name = "Team 2:" + team2.getName().substring(0, 6);
        else
            team2Name = "Team 2:" + team2.getName();

        String team2Score = Integer.toString(team2Goal.getScore());
        int locationOfTeam2Name = (int)Math.floor(590 - (team2Name.length() * 16));

        int locationOfScore = (int)Math.floor(292 - (team2Score.length() * 16));

        displayFont.drawString(g, team1Name, locationOfTeam1Name, 3);
        displayFont.drawString(g, team2Score + "-" + team1Score, locationOfScore, 3);
        displayFont.drawString(g, team2Name, locationOfTeam2Name, 3);

        displayFont.drawString(g, "Time: " + (currentTime / 1000), 244, 573);

        if(currentGameState == 0)
        {
            if(player1.hasBall())
            {
                displayFont.drawString(g, player1.getName() + " has ball", WIDTH + 33, 3);
            }
            else if(player2.hasBall())
            {
                displayFont.drawString(g, player2.getName() + " has ball", WIDTH + 33, 3);
            }
        }
        else if(currentGameState == 1)
        {
            displayFont.drawString(g, player1.getName() + " is passing", WIDTH + 33, 3);
        }
        else if(currentGameState == 2)
        {
            displayFont.drawString(g, player2.getName() + " is passing", WIDTH + 33, 3);
        }
        else if(currentGameState == 3)
        {
            displayFont.drawString(g, "power: " + String.format("%.0f", currentBall.getPower()), WIDTH + 33, 3);
            for(int i = 0; i < playersIntercepting.size(); i++)
            {
                PlayerSprite currentPlayer = playersIntercepting.get(i);
                displayFont.drawString(g, currentPlayer.getName() + " Intercepting", WIDTH + 33, 33 + (i * 30));
            }
        }
        else if(currentGameState == 4)
        {
            displayFont.drawString(g, waitAction, WIDTH + 33, 3);
            for(int i = 0; i < playersIntercepting.size(); i++)
            {
                PlayerSprite currentPlayer = playersIntercepting.get(i);
                displayFont.drawString(g, currentPlayer.getName() + " Intercepted", WIDTH + 33, 33 + (i * 30));
            }
        }

        if(paused)
        {
            displayFont.drawString(g, "Game Paused", (WIDTH/2) - 88, HEIGHT/2);
        }
    }

    private Sprite findNextAvailablePlayerBelow(ArrayList<PlayerSprite> currentTeamPlayers, Sprite currentSpriteToPassOrShoot, GoalSprite currentGoal)
    {
        boolean foundNextPlayer = false;
        int indexOfSprite;

        //if the current sprite is the goal, then search for the next valid sprite at the end of the list
        if(currentSpriteToPassOrShoot == currentGoal)
            indexOfSprite = currentTeamPlayers.size() - 1;
        else
            indexOfSprite = currentTeamPlayers.indexOf(currentSpriteToPassOrShoot);

        //start at the index of the current sprite to pass and move down the list
        for(int i = indexOfSprite; (i >= 0)&&(!foundNextPlayer); i--)
        {
            PlayerSprite currentPlayer = currentTeamPlayers.get(i);
            if((!currentPlayer.hasBall())&&(currentPlayer != currentSpriteToPassOrShoot))
            {
                //we have found the next valid sprite to pass it to
                foundNextPlayer = true;
                return currentPlayer;
            }
        }

        //default to goal as this will always be available to shoot at
        return currentGoal;
    }

    private Sprite findNextAvailablePlayerAbove(ArrayList<PlayerSprite> currentPlayers, Sprite currentSpriteToPassOrShoot, GoalSprite currentGoal)
    {
        boolean foundNextPlayer = false;
        int indexOfSprite;

        //if the current sprite is the goal, then search for the next valid sprite at the start of the list
        if(currentSpriteToPassOrShoot == currentGoal)
            indexOfSprite = 0;
        else
            indexOfSprite = currentPlayers.indexOf(currentSpriteToPassOrShoot);

        //start at the index of the current player to pass and move up the list
        for(int i = indexOfSprite; (i < currentPlayers.size())&&(!foundNextPlayer); i++)
        {
            PlayerSprite currentPlayer = currentPlayers.get(i);
            if((!currentPlayer.hasBall())&&(currentPlayer != currentSpriteToPassOrShoot))
            {
                //we have found the next valid sprite to pass it to
                foundNextPlayer = true;
                return currentPlayer;
            }
        }

        //default to goal as this will always be available to shoot at
        return currentGoal;
    }

    private void movePlayer1(long elapsedTime)
    {
        boolean up;
        boolean upRight;
        boolean right;
        boolean downRight;
        boolean down;
        boolean downLeft;
        boolean left;
        boolean upLeft;
        boolean pass;

        if((player1Controller != null)&&(player1Controller.poll()))
        {
            float pollData = player1Controller.getComponent(Identifier.Axis.POV).getPollData();

            up = (pollData == Component.POV.UP);
            right = (pollData == Component.POV.RIGHT);
            down = (pollData == Component.POV.DOWN);
            left = (pollData == Component.POV.LEFT);

            upRight = (pollData == Component.POV.UP_RIGHT);
            upLeft = (pollData == Component.POV.UP_LEFT);
            downRight = (pollData == Component.POV.DOWN_RIGHT);
            downLeft = (pollData == Component.POV.DOWN_LEFT);

            if(player1Controller.getComponent(Identifier.Button._0).getPollData() == 1.0)
            {
                if(AReleaseP1)
                {
                    pass = true;
                    AReleaseP1 = false;
                }
                else
                {
                    pass = false;
                }
            }
            else
            {
                pass = false;
                AReleaseP1 = true;
            }
        }
        else
        {
            up = keyDown(KeyEvent.VK_UP);
            right = keyDown(KeyEvent.VK_RIGHT);
            down = keyDown(KeyEvent.VK_DOWN);
            left = keyDown(KeyEvent.VK_LEFT);

            upRight = up&&right;
            upLeft = up&&left;
            downRight = down&&right;
            downLeft = down&&left;

            pass = keyPressed(KeyEvent.VK_ENTER);
        }

        if(upRight)
        {
            player1.setDirection(PlayerSprite.UPRIGHT);
            player1.move(elapsedTime, -elapsedTime);
        }
        else if(upLeft)
        {
            player1.setDirection(PlayerSprite.UPLEFT);
            player1.move(-elapsedTime, -elapsedTime);
        }
        else if(downRight)
        {
            player1.setDirection(PlayerSprite.DOWNRIGHT);
            player1.move(elapsedTime, elapsedTime);
        }
        else if(downLeft)
        {
            player1.setDirection(PlayerSprite.DOWNLEFT);
            player1.move(-elapsedTime, elapsedTime);
        }
        else if(up)
        {
            player1.setDirection(PlayerSprite.UP);
            player1.move(0, -elapsedTime);
        }
        else if(right)
        {
            player1.setDirection(PlayerSprite.RIGHT);
            player1.move(elapsedTime, 0);
        }
        else if(down)
        {
            player1.setDirection(PlayerSprite.DOWN);
            player1.move(0, elapsedTime);
        }
        else if(left)
        {
            player1.setDirection(PlayerSprite.LEFT);
            player1.move(-elapsedTime, 0);
        }

        //if player 1 has the ball, he is trying to pass the ball
        if((pass)&&(player1.hasBall()))
        {
            //move to next player state
            currentGameState = 1;
        }
    }

    private void movePlayer2(long elapsedTime)
    {
        boolean up;
        boolean upRight;
        boolean right;
        boolean downRight;
        boolean down;
        boolean downLeft;
        boolean left;
        boolean upLeft;
        boolean pass;

        if((player2Controller != null)&&(player2Controller.poll()))
        {
            float pollData = player2Controller.getComponent(Identifier.Axis.POV).getPollData();

            up = (pollData == Component.POV.UP);
            right = (pollData == Component.POV.RIGHT);
            down = (pollData == Component.POV.DOWN);
            left = (pollData == Component.POV.LEFT);
            
            upRight = (pollData == Component.POV.UP_RIGHT);
            upLeft = (pollData == Component.POV.UP_LEFT);
            downRight = (pollData == Component.POV.DOWN_RIGHT);
            downLeft = (pollData == Component.POV.DOWN_LEFT);

            if(player2Controller.getComponent(Identifier.Button._0).getPollData() == 1.0)
            {
                if(AReleaseP2)
                {
                    pass = true;
                    AReleaseP2 = false;
                }
                else
                {
                    pass = false;
                }
            }
            else
            {
                pass = false;
                AReleaseP2 = true;
            }
        }
        else
        {
            up = keyDown(KeyEvent.VK_W);
            right = keyDown(KeyEvent.VK_D);
            down = keyDown(KeyEvent.VK_S);
            left = keyDown(KeyEvent.VK_A);

            upRight = up&&right;
            upLeft = up&&left;
            downRight = down&&right;
            downLeft = down&&left;

            pass = keyPressed(KeyEvent.VK_SPACE);
        }

        if(upRight)
        {
            player2.setDirection(PlayerSprite.UPRIGHT);
            player2.move(elapsedTime, -elapsedTime);
        }
        else if(upLeft)
        {
            player2.setDirection(PlayerSprite.UPLEFT);
            player2.move(-elapsedTime, -elapsedTime);
        }
        else if(downRight)
        {
            player2.setDirection(PlayerSprite.DOWNRIGHT);
            player2.move(elapsedTime, elapsedTime);
        }
        else if(downLeft)
        {
            player2.setDirection(PlayerSprite.DOWNLEFT);
            player2.move(-elapsedTime, elapsedTime);
        }
        else if(up)
        {
            player2.setDirection(PlayerSprite.UP);
            player2.move(0, -elapsedTime);
        }
        else if(right)
        {
            player2.setDirection(PlayerSprite.RIGHT);
            player2.move(elapsedTime, 0);
        }
        else if(down)
        {
            player2.setDirection(PlayerSprite.DOWN);
            player2.move(0, elapsedTime);
        }
        else if(left)
        {
            player2.setDirection(PlayerSprite.LEFT);
            player2.move(-elapsedTime, 0);
        }

        //if player 2 has the ball, he is trying to pass the ball
        if((pass)&&(player2.hasBall()))
        {
            //move to next player state
            currentGameState = 2;
        }
    }

    private void ballFumbled()
    {
        //random opposing player acquires the ball
        if(currentBall.getPlayerThrown() == player1)
        {
            PlayerSprite currentPlayer = PlayerSprite.findClosestPlayer(currentBall, team2Players);
            currentPlayer.gotBall(currentBall);
            player2 = currentPlayer;
        }
        else if(currentBall.getPlayerThrown() == player2)
        {
            PlayerSprite currentPlayer = PlayerSprite.findClosestPlayer(currentBall, team1Players);
            currentPlayer.gotBall(currentBall);
            player1 = currentPlayer;
        }

        waitTime = System.currentTimeMillis() + 2000;
        waitAction = "ball fumbled";
        currentGameState = 4;
    }

    private void checkPlayerCaughtBall()
    {
        for(int i = 0; i < team1Players.size(); i++)
        {
            PlayerSprite currentPlayer = team1Players.get(i);
            //switch user input to the player with the ball
            if(currentPlayer.hasBall())
            {
                player1 = currentPlayer;

                waitTime = System.currentTimeMillis() + 1000;
                waitAction = player1.getName() + " caught ball";
                currentGameState = 4;
            }
        }
        for(int i = 0; i < team2Players.size(); i++)
        {
            PlayerSprite currentPlayer = team2Players.get(i);
            //switch user input to the player with the ball
            if(currentPlayer.hasBall())
            {
                player2 = currentPlayer;

                waitTime = System.currentTimeMillis() + 1000;
                waitAction = player2.getName() + " caught ball";
                currentGameState = 4;
            }
        }
    }

    private void checkGoalHasBall()
    {
        if(team1Goal.hasBall())
        {
            waitTime = System.currentTimeMillis() + 3000;
            waitAction = player2.getName() + " Scored!!!";
            
            //take ball off goal
            team1Goal.lostBall();

            //reset human players to mid fielders
            player1 = team1Players.get(1);
            player2 = team2Players.get(1);

            //put every player in their default locations
            team1Players.get(0).setLocation((WIDTH / 3), ((HEIGHT / 3) * 2));
            team1Players.get(1).setLocation((WIDTH / 2), ((HEIGHT / 3) * 2));
            team1Players.get(2).setLocation(((WIDTH / 3) * 2), ((HEIGHT / 3) * 2));
            team2Players.get(0).setLocation((WIDTH / 3), (HEIGHT / 3));
            team2Players.get(1).setLocation((WIDTH / 2), (HEIGHT / 3));
            team2Players.get(2).setLocation(((WIDTH / 3) * 2), (HEIGHT / 3));

            //give player ball
            player1.gotBall(currentBall);

            currentGameState = 4;
            playSound("Sounds/congratulations.wav");
        }
        else if(team2Goal.hasBall())
        {
            waitTime = System.currentTimeMillis() + 3000;
            waitAction = player1.getName() + " Scored!!!";

            //take ball off goal
            team2Goal.lostBall();

            //reset human players to mid fielders
            player1 = team1Players.get(1);
            player2 = team2Players.get(1);

            //put every player in their default locations
            team1Players.get(0).setLocation((WIDTH / 3), ((HEIGHT / 3) * 2));
            team1Players.get(1).setLocation((WIDTH / 2), ((HEIGHT / 3) * 2));
            team1Players.get(2).setLocation(((WIDTH / 3) * 2), ((HEIGHT / 3) * 2));
            team2Players.get(0).setLocation((WIDTH / 3), (HEIGHT / 3));
            team2Players.get(1).setLocation((WIDTH / 2), (HEIGHT / 3));
            team2Players.get(2).setLocation(((WIDTH / 3) * 2), (HEIGHT / 3));

            //give player ball
            player2.gotBall(currentBall);

            currentGameState = 4;
            playSound("Sounds/congratulations.wav");
        }
    }

    private void moveAIPlayers(long elapsedTime)
    {
        //move the computer controlled players on team 1
        for(int i = 0; i < team1Players.size(); i++)
        {
            PlayerSprite currentPlayer = team1Players.get(i);
            if(currentPlayer != player1)
            {
                //get the action for a player based on the state of the game
                currentPlayer.executeAction(player1, team2Players, team1Goal, team2Goal, elapsedTime);
            }
        }

        //move the computer controlled players on team 2
        for(int i = 0; i < team2Players.size(); i++)
        {
            PlayerSprite currentPlayer = team2Players.get(i);
            if(currentPlayer != player2)
            {
                //get the action for a player based on the state of the game
                currentPlayer.executeAction(player2, team1Players, team2Goal, team1Goal, elapsedTime);
            }
        }
    }

    private void player1PassBall()
    {
        boolean up;
        boolean down;
        boolean pass;

        if((player1Controller != null)&&(player1Controller.poll()))
        {
            float pollData = player1Controller.getComponent(Identifier.Axis.POV).getPollData();

            if(pollData == Component.POV.UP)
            {
                if(UpReleaseP1)
                {
                    up = true;
                    UpReleaseP1 = false;
                }
                else
                {
                    up = false;
                }
            }
            else
            {
                up = false;
                UpReleaseP1 = true;
            }

            if(pollData == Component.POV.DOWN)
            {
                if(DownReleaseP1)
                {
                    down = true;
                    DownReleaseP1 = false;
                }
                else
                {
                    down = false;
                }
            }
            else
            {
                down = false;
                DownReleaseP1 = true;
            }

            if(player1Controller.getComponent(Identifier.Button._0).getPollData() == 1.0)
            {
                if(AReleaseP1)
                {
                    pass = true;
                    AReleaseP1 = false;
                }
                else
                {
                    pass = false;
                }
            }
            else
            {
                pass = false;
                AReleaseP1 = true;
            }
        }
        else
        {
            up = keyPressed(KeyEvent.VK_UP);
            down = keyPressed(KeyEvent.VK_DOWN);
            pass = keyPressed(KeyEvent.VK_ENTER);
        }

        //choose the next available sprite to pass it to above the current one
        if(up)
        {
            spriteToPassTeam1 = findNextAvailablePlayerAbove(team1Players, spriteToPassTeam1, team2Goal);

            //update the target sprite to show where the player is targetting
            currentTarget.setLocation(spriteToPassTeam1.getX(), spriteToPassTeam1.getY());
        }
        //choose the next available player to pass it to below the current one
        else if(down)
        {
            spriteToPassTeam1 = findNextAvailablePlayerBelow(team1Players, spriteToPassTeam1, team2Goal);

            //update the target sprite to show where the player is targetting
            currentTarget.setLocation(spriteToPassTeam1.getX(), spriteToPassTeam1.getY());
        }

        //pass the ball to the player
        if(pass)
        {
            currentTarget.setActive(false);
            currentTarget = null;

            //pass the ball
            player1.passBall(spriteToPassTeam1);
            //reset the pass sprite to default
            spriteToPassTeam1 = team2Goal;

            //move to next player state
            currentGameState = 3;
        }
    }

    private void player2PassBall()
    {
        boolean up;
        boolean down;
        boolean pass;

        if((player2Controller != null)&&(player2Controller.poll()))
        {
            float pollData = player2Controller.getComponent(Identifier.Axis.POV).getPollData();

            if(pollData == Component.POV.UP)
            {
                if(UpReleaseP2)
                {
                    up = true;
                    UpReleaseP2 = false;
                }
                else
                {
                    up = false;
                }
            }
            else
            {
                up = false;
                UpReleaseP2 = true;
            }

            if(pollData == Component.POV.DOWN)
            {
                if(DownReleaseP2)
                {
                    down = true;
                    DownReleaseP2 = false;
                }
                else
                {
                    down = false;
                }
            }
            else
            {
                down = false;
                DownReleaseP2 = true;
            }

            if(player2Controller.getComponent(Identifier.Button._0).getPollData() == 1.0)
            {
                if(AReleaseP2)
                {
                    pass = true;
                    AReleaseP2 = false;
                }
                else
                {
                    pass = false;
                }
            }
            else
            {
                pass = false;
                AReleaseP2 = true;
            }
        }
        else
        {
            up = keyPressed(KeyEvent.VK_W);
            down = keyPressed(KeyEvent.VK_S);
            pass = keyPressed(KeyEvent.VK_SPACE);
        }

        //choose the next available player to pass it to above the current one
        if(up)
        {
            spriteToPassTeam2 = findNextAvailablePlayerAbove(team2Players, spriteToPassTeam2, team1Goal);

            //update the target sprite to show where the player is targetting
            currentTarget.setLocation(spriteToPassTeam2.getX(), spriteToPassTeam2.getY());
        }
        //choose the next available player to pass it to below the current one
        else if(down)
        {
            spriteToPassTeam2 = findNextAvailablePlayerBelow(team2Players, spriteToPassTeam2, team1Goal);

            //update the target sprite to show where the player is targetting
            currentTarget.setLocation(spriteToPassTeam2.getX(), spriteToPassTeam2.getY());
        }

        //pass the ball to the player
        if(pass)
        {
            currentTarget.setActive(false);
            currentTarget = null;

            //pass the ball
            player2.passBall(spriteToPassTeam2);
            //reset the pass sprite to default
            spriteToPassTeam2 = team1Goal;

            //move to next player state
            currentGameState = 3;
        }
    }

    private void checkPlayersTackling()
    {
        if(player1.hasBall())
        {
            for(int i = 0; i < team2Players.size(); i++)
            {
                PlayerSprite currentPlayer = team2Players.get(i);
                double distanceToBall = PlayerSprite.distanceBetweenTwoPoints(player1.getX(), player1.getY(), currentPlayer.getX(), currentPlayer.getY());
                if(distanceToBall <= currentPlayer.getTackleRange())
                {
                    //move the tackling player away so he doesnt constantly tackle
                    currentPlayer.jumpAway(player1);

                    //move to next player state
                    currentGameState = 1;
                }
            }
        }
        else if(player2.hasBall())
        {
            for(int i = 0; i < team1Players.size(); i++)
            {
                PlayerSprite currentPlayer = team1Players.get(i);
                double distanceToBall = PlayerSprite.distanceBetweenTwoPoints(player2.getX(), player2.getY(), currentPlayer.getX(), currentPlayer.getY());
                if(distanceToBall <= currentPlayer.getTackleRange())
                {
                    //move the tackling player away so he doesnt constantly tackle
                    currentPlayer.jumpAway(player2);

                    //move to next player state
                    currentGameState = 2;
                }
            }
        }
    }

    private void checkPlayersInterceptingBall(double elapsedTime)
    {
        if(currentBall.getPlayerThrown().getTeam() == 1)
        {
            for(int i = 0; i < team2Players.size(); i++)
            {
                PlayerSprite currentPlayer = team2Players.get(i);
                double distanceToBall = PlayerSprite.distanceBetweenTwoPoints(currentBall.getX(), currentBall.getY(), currentPlayer.getX(), currentPlayer.getY());
                if(distanceToBall <= currentPlayer.getInterceptRange())
                {
                    //reduce power of ball by intercepting
                    currentBall.reducePower(currentPlayer.getInterceptStrength(), elapsedTime);
                    
                    //add to list if not already on it
                    if(!playersIntercepting.contains(currentPlayer))
                    {
                        playersIntercepting.add(currentPlayer);
                    }
                }
            }
        }
        else if(currentBall.getPlayerThrown().getTeam() == 2)
        {
            for(int i = 0; i < team1Players.size(); i++)
            {
                PlayerSprite currentPlayer = team1Players.get(i);
                double distanceToBall = PlayerSprite.distanceBetweenTwoPoints(currentBall.getX(), currentBall.getY(), currentPlayer.getX(), currentPlayer.getY());
                if(distanceToBall <= currentPlayer.getInterceptRange())
                {
                    //reduce power of ball by intercepting
                    currentBall.reducePower(currentPlayer.getInterceptStrength(), elapsedTime);
                    
                    //add to list if not already on it
                    if(!playersIntercepting.contains(currentPlayer))
                    {
                        playersIntercepting.add(currentPlayer);
                    }
                }
            }
        }
    }
}

class PlayerPlayerCollision extends PreciseCollisionGroup
{
    public void collided(Sprite currentSprite1, Sprite currentSprite2)
    {
        revertPosition1();
    }
}

class BallPlayerCollision extends PreciseCollisionGroup
{
    public void collided(Sprite currentSprite1, Sprite currentSprite2)
    {
        BallSprite currentBall = ((BallSprite)currentSprite1);
        PlayerSprite currentPlayer = ((PlayerSprite)currentSprite2);

        //make sure the person who has thrown the ball, does not catch the ball
        //if the ball is in possesion by a player, disregard this collision
        //do not allow opposing players to directly intercept the ball
        if((currentBall.getPlayerThrown() != currentPlayer)&&(!currentBall.hasPossesion())&&(currentBall.getPlayerThrown().getTeam() == currentPlayer.getTeam()))
        {
            //player catches the ball
            currentPlayer.gotBall(currentBall);
        }
    }
}

class PlayerWallCollision extends PreciseCollisionGroup
{
    public void collided(Sprite currentSprite1, Sprite currentSprite2)
    {
        revertPosition1();
    }
}

class PlayerGoalCollision extends PreciseCollisionGroup
{
    public void collided(Sprite currentSprite1, Sprite currentSprite2)
    {
        revertPosition1();
    }
}

class BallGoalCollision extends PreciseCollisionGroup
{
    public void collided(Sprite currentSprite1, Sprite currentSprite2)
    {
        BallSprite currentBall = ((BallSprite)currentSprite1);
        GoalSprite currentGoal = ((GoalSprite)currentSprite2);

        //if the ball is being shot
        //prevents players who are holding the ball to score, they must first shoot at the goal
        if(!currentBall.hasPossesion())
        {
            //score the goal
            currentGoal.score(currentBall);
        }
    }
}