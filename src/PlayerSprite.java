/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.AdvanceSprite;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Eoin Costelloe
 */
class PlayerSprite extends AdvanceSprite
{
    //direction of player
    public static final int EASY = 0, MEDIUM = 1, HARD = 2;
    //direction of player
    public static final int UP = 0, UPRIGHT = 1, RIGHT = 2, DOWNRIGHT = 3, DOWN = 4, DOWNLEFT = 5, LEFT = 6, UPLEFT = 7;
    //action of player
    public static final int DONOTHING = -1, MOVEUP = 0, MOVEUPRIGHT = 1, MOVERIGHT = 2, MOVEDOWNRIGHT = 3, MOVEDOWN = 4, MOVEDOWNLEFT = 5, MOVELEFT = 6, MOVEUPLEFT = 7;

    String name;
    //overall speed of the player
    double speed;
    //power of the pass
    double power;
    //distance can tackle an opposing player
    double tackleRange;
    //distance can intercept ball from
    double interceptRange;
    double interceptStrength;

    //the player may have control over a ball
    BallSprite currentBall;
    //player team
    int currentTeam;
    //player position:
    //0 - left
    //1 - center
    //2 - right
    int currentPosition;

    //allows us to only sort the array of moods once in a while
    int numberOfSteps;
    int maxNumberOfSteps;

    //create moods
    Mood[] moodArray;

    //moods for attacking
    Mood moveTowardsGoal;
    Mood opposingPlayerNearby;

    //mood for defending
    Mood markPlayer;
    Mood markGoal;

    //mood for both
    Mood markPosition;

    PlayerSprite(String inputName, BufferedImage[] image, double x, double y, double inputSpeed, double inputPower, double inputTackleRange, double inputInterceptRange, double inputInterceptStrength, int inputTeam, int inputPosition, int inputNumberSteps)
    {
        super(image,x,y);

        setDirection(DOWN);
        getAnimationTimer().setDelay(200);
        setAnimate(true);
        setLoopAnim(true);

        name = inputName;
        speed = inputSpeed;
        power = inputPower;
        tackleRange = inputTackleRange;
        interceptRange = inputInterceptRange;
        interceptStrength = inputInterceptStrength;

        currentBall = null;
        currentTeam = inputTeam;
        currentPosition = inputPosition;

        //tells us how many steps between sorting the array of moods
        numberOfSteps = inputNumberSteps;
        maxNumberOfSteps = inputNumberSteps;

        //initialize moods
        moveTowardsGoal = new Mood("moveTowardsGoal");
        opposingPlayerNearby = new Mood("opposingPlayerNearby");
        markPlayer = new Mood("markPlayer");
        markGoal = new Mood("markGoal");
        markPosition = new Mood("markPosition");

        //fill up the array with the moods
        moodArray = new Mood[5];
        moodArray[0] = moveTowardsGoal;
        moodArray[1] = opposingPlayerNearby;
        moodArray[2] = markPlayer;
        moodArray[3] = markGoal;
        moodArray[4] = markPosition;
    }

    String getName()
    {
        return name;
    }

    double getPower()
    {
        return power;
    }

    double getTackleRange()
    {
        return tackleRange;
    }

    double getInterceptRange()
    {
        return interceptRange;
    }

    double getInterceptStrength()
    {
        return interceptStrength;
    }

    @Override
    public String toString()
    {
        String currentString = "";

        currentString += "Speed: " + speed + "\n";
        currentString += "Power: " + power + "\n";
        currentString += "Has Ball: " + hasBall() + "\n";
        currentString += "Current Team: " + currentTeam + "\n";
        currentString += "Current Position: " + currentPosition + "\n";

        currentString += "Weights\n";
        currentString += "moveTowardsGoal: " + moveTowardsGoal.getWeight() + "\n";
        currentString += "opposingPlayerNearby: " + opposingPlayerNearby.getWeight() + "\n";
        currentString += "markPlayer: " + markPlayer.getWeight() + "\n";
        currentString += "markGoal: " + markGoal.getWeight() + "\n";
        currentString += "markPosition: " + markPosition.getWeight() + "\n";

        return currentString;
    }

    @Override
    protected void animationChanged(int oldStat, int oldDir, int status, int direction)
    {
        if(direction == UP)
            setAnimationFrame(2, 3);
        else if(direction == UPRIGHT||direction == RIGHT||direction == DOWNRIGHT)
            setAnimationFrame(6, 7);
        else if(direction == DOWN)
            setAnimationFrame(0, 1);
        else if(direction == DOWNLEFT||direction == LEFT||direction == UPLEFT)
            setAnimationFrame(4, 5);
    }

    @Override
    public void move(double x, double y)
    {
        //if player is only moving in y dimension
        if(x == 0)
        {
            super.move(0, speed*y);
        }
        //if player is only moving in s dimension
        else if(y == 0)
        {
            super.move(speed*x, 0);
        }
        //if player is moving diagonally
        else
        {
            //based on the equation for a diagonal adjacent = hypotenuse*sin(45)
            //hardcode for speed, sin(45) = 0.707106781
            double diagonalSpeed = (speed*0.707106781);
            super.move(diagonalSpeed*x, diagonalSpeed*y);
        }
        //move the ball with the player
        if(currentBall != null)
            currentBall.setLocation(getX(), getY());
    }

    @Override
    public void setLocation(double x, double y)
    {
        super.setLocation(x, y);
        //move the ball with the player
        if(currentBall != null)
            currentBall.setLocation(x, y);
    }

    void gotBall(BallSprite inputBall)
    {
        currentBall = inputBall;
        currentBall.setSpeed(0, 0);
        currentBall.setLocation(getX(), getY());
        currentBall.gotPossesion();
    }

    boolean hasBall()
    {
        if(currentBall == null)
            return false;
        return true;
    }

    int getTeam()
    {
        return currentTeam;
    }

    void passBall(Sprite playerToPass)
    {
        //if the player has the ball, they can pass
        if(currentBall != null)
        {
            //find the relative x and y direction of the ball based on origin
            double relativeXDirection = (playerToPass.getX() - getX());
            double relativeYDirection = (playerToPass.getY() - getY());
            //find the slope, (y2 - y1)/(x2 - x1)
            double slopeBetweenPlayers = (relativeYDirection / relativeXDirection);
            //try and move 0.2 in one axis and a smaller amount in the other axis
            //based on the equation y = x*slope and x = y/slope
            //if the slope is greater than 1 or less than -1, we move 0.2 in the y axis (and find x)
            if((slopeBetweenPlayers > 1)||(slopeBetweenPlayers < -1))
            {
                if(relativeYDirection > 0)
                {
                    currentBall.setSpeed((0.2/slopeBetweenPlayers), 0.2);
                }
                else
                {
                    currentBall.setSpeed((-0.2/slopeBetweenPlayers), -0.2);
                }
            }
            //if the slope is between 1 and -1, we move 0.2 in the x axis (and find y)
            else if((slopeBetweenPlayers <= 1)&&(slopeBetweenPlayers >= -1))
            {
                if(relativeXDirection > 0)
                {
                    currentBall.setSpeed(0.2, (0.2*slopeBetweenPlayers));
                }
                else
                {
                    currentBall.setSpeed(-0.2, (-0.2*slopeBetweenPlayers));
                }
            }

            //do not catch the ball if you threw it
            currentBall.lostPossesion(this);

            //lose the ball
            currentBall = null;
        }
    }

    void executeAction(PlayerSprite yourTeamCaptain, ArrayList<PlayerSprite> opposingTeam, GoalSprite yourTeamGoal, GoalSprite opposingTeamGoal, long elapsedTime)
    {
        //find the closest opposing player
        PlayerSprite closestOpposingPlayer = findClosestPlayer(this, opposingTeam);

        //ready the weights of the moods
        readyMoods(yourTeamCaptain, closestOpposingPlayer, yourTeamGoal, opposingTeamGoal);

        //if we need to update the priorities of the moods
        if(numberOfSteps >= maxNumberOfSteps)
        {
            numberOfSteps = 0;
            sortMoodArray();
        }
        numberOfSteps++;

        //go through each mood until we find one which actually can be done
        int action = DONOTHING;
        for(int i = 0; (i < moodArray.length)&&(action == DONOTHING); i++)
        {
            action = getMoodAction(moodArray[i], yourTeamCaptain, closestOpposingPlayer, yourTeamGoal, opposingTeamGoal);
        }

        //execute action chosen
        if(action == PlayerSprite.MOVEUP)
        {
            setDirection(PlayerSprite.UP);
            move(0, -elapsedTime);
        }
        else if(action == PlayerSprite.MOVEUPRIGHT)
        {
            setDirection(PlayerSprite.UPRIGHT);
            move(elapsedTime, -elapsedTime);
        }
        else if(action == PlayerSprite.MOVERIGHT)
        {
            setDirection(PlayerSprite.RIGHT);
            move(elapsedTime, 0);
        }
        else if(action == PlayerSprite.MOVEDOWNRIGHT)
        {
            setDirection(PlayerSprite.DOWNRIGHT);
            move(elapsedTime, elapsedTime);
        }
        else if(action == PlayerSprite.MOVEDOWN)
        {
            setDirection(PlayerSprite.DOWN);
            move(0, elapsedTime);
        }
        else if(action == PlayerSprite.MOVEDOWNLEFT)
        {
            setDirection(PlayerSprite.DOWNLEFT);
            move(-elapsedTime, elapsedTime);
        }
        else if(action == PlayerSprite.MOVELEFT)
        {
            setDirection(PlayerSprite.LEFT);
            move(-elapsedTime, 0);
        }
        else if(action == PlayerSprite.MOVEUPLEFT)
        {
            setDirection(PlayerSprite.UPLEFT);
            move(-elapsedTime, -elapsedTime);
        }
    }

    //distance between two points = square root(squared(x2 - x1) + squared(y2 - y1))
    static double distanceBetweenTwoPoints(double point1X, double point1Y, double point2X, double point2Y)
    {
        return Math.sqrt(Math.pow((point1X - point2X), 2) + Math.pow((point1Y - point2Y), 2));
    }

    private void readyMoods(PlayerSprite yourTeamCaptain, PlayerSprite closestOpposingPlayer, GoalSprite yourTeamGoal, GoalSprite opposingTeamGoal)
    {
        //if your team has the ball, be offensive
        if(yourTeamCaptain.hasBall())
        {
            //set moveTowardsGoal weight
            moveTowardsGoal.setWeight(moveTowardsGoalWeight(opposingTeamGoal));
            //set opposingPlayerNearby weight
            opposingPlayerNearby.setWeight(opposingPlayerNearbyWeight(closestOpposingPlayer));
            markPlayer.setWeight(0);
            markGoal.setWeight(0);
            markPosition.setWeight(markPositionWeight(yourTeamCaptain));
        }
        //else be defensive
        else
        { 
            moveTowardsGoal.setWeight(0);
            opposingPlayerNearby.setWeight(0);
            //set markPlayer weight
            markPlayer.setWeight(markPlayerWeight(closestOpposingPlayer));
            //set markGoal weight
            markGoal.setWeight(markGoalWeight(yourTeamGoal, closestOpposingPlayer));
            markPosition.setWeight(markPositionWeight(yourTeamCaptain));
        }
    }

    private double moveTowardsGoalWeight(GoalSprite opposingTeamGoal)
    {
        double weight = distanceBetweenTwoPoints(opposingTeamGoal.getX(), opposingTeamGoal.getY(), getX(), getY()) / 600;
        if(weight > 1)
            return 1;
        else
            return weight;
    }

    private double opposingPlayerNearbyWeight(PlayerSprite closestOpposingPlayer)
    {
        double weight = (200 - distanceBetweenTwoPoints(closestOpposingPlayer.getX(), closestOpposingPlayer.getY(), getX(), getY())) / 200;
        if(weight < 0)
            return 0;
        else
            return weight;
    }

    private double markPlayerWeight(PlayerSprite closestOpposingPlayer)
    {
        double weight = (400 - distanceBetweenTwoPoints(closestOpposingPlayer.getX(), closestOpposingPlayer.getY(), getX(), getY())) / 400;
        if(weight < 0)
            return 0;
        else
            return weight;
    }

    private double markGoalWeight(GoalSprite yourTeamGoal, PlayerSprite closestOpposingPlayer)
    {
        double midwayX = (yourTeamGoal.getX() + closestOpposingPlayer.getX()) / 2;
        double midwayY = (yourTeamGoal.getY() + closestOpposingPlayer.getY()) / 2;

        double weight = (400 - distanceBetweenTwoPoints(midwayX, midwayY, getX(), getY())) / 400;
        if(weight < 0)
            return 0;
        else
            return weight;
    }

    private double markPositionWeight(PlayerSprite yourTeamCaptain)
    {
        double positionX = getMarkPositionX(yourTeamCaptain.getX(), yourTeamCaptain.getPosition(), currentPosition);
        double positionY = yourTeamCaptain.getY();

        double weight = distanceBetweenTwoPoints(positionX, positionY, getX(), getY()) / 200;
        if(weight > 1)
            return 1;
        else
            return weight;
    }
    
    double getPosition()
    {
        return currentPosition;
    }

    //try and find your position based on the captains position
    //for example if captain is mid fielder and you are left fielder
    //find the location halfway between the captain and the wall to his left side
    double getMarkPositionX(double captainX, double captainPosition, double currentPosition)
    {
        //left fielder
        if(currentPosition == 0)
        {
            //captian is mid fielder
            if(captainPosition == 1)
                return ((captainX - 50) / 2) + 50;
            //captian is right fielder
            else if(captainPosition == 2)
                return ((captainX - 50) / 3) + 50;
            else
                return captainX;
        }
        //mid fielder
        else if(currentPosition == 1)
        {
            //captian is left fielder
            if(captainPosition == 0)
                return (((SwitchBall.WIDTH - 50) - captainX) / 3) + captainX;
            //captian is right fielder
            else if(captainPosition == 2)
                return (((captainX - 50) / 3) * 2) + 50;
            else
                return captainX;
        }
        //right fielder
        else //currentPosition == 2
        {
            //captian is left fielder
            if(captainPosition == 0)
                return ((((SwitchBall.WIDTH - 50) - captainX) / 3) *2) + captainX;
            //captian is mid fielder
            else if(captainPosition == 1)
                return (((SwitchBall.WIDTH - 50) - captainX) / 2) + captainX;
            else
                return captainX;
        }
    }

    public static PlayerSprite findClosestPlayer(Sprite inputPlayer, ArrayList<PlayerSprite> inputTeam)
    {
        //default is the first opposing player
        double closestOpposingPlayerDistance = distanceBetweenTwoPoints(inputTeam.get(0).getX(), inputTeam.get(0).getY(), inputPlayer.getX(), inputPlayer.getY());
        int closestOpposingPlayerIndex = 0;

        double currentOpposingPlayerDistance;

        for(int currentOpposingPlayerIndex = 1; currentOpposingPlayerIndex < inputTeam.size(); currentOpposingPlayerIndex++)
        {
            currentOpposingPlayerDistance = distanceBetweenTwoPoints(inputTeam.get(currentOpposingPlayerIndex).getX(), inputTeam.get(currentOpposingPlayerIndex).getY(), inputPlayer.getX(), inputPlayer.getY());
            if(currentOpposingPlayerDistance < closestOpposingPlayerDistance)
            {
                closestOpposingPlayerDistance = currentOpposingPlayerDistance;
                closestOpposingPlayerIndex = currentOpposingPlayerIndex;
            }
        }

        return inputTeam.get(closestOpposingPlayerIndex);
    }

    private void sortMoodArray()
    {
        //a standard linear sort is fine for a small array
        int currentMax;
        //we are sorting from highest to lowest
        for(int i = 0; i < moodArray.length; i++)
        {
            currentMax = i;
            for(int j = i+1; j < moodArray.length; j++)
            {
                if(moodArray[j].getWeight() > moodArray[i].getWeight())
                    currentMax = j;
            }
            if(i != currentMax)
                swap(i, currentMax);
        }
    }

    private void swap(int current1, int current2)
    {
        Mood temp = moodArray[current1];
        moodArray[current1] = moodArray[current2];
        moodArray[current2] = temp;
    }

    private int getMoodAction(Mood currentMood, PlayerSprite yourTeamCaptain, PlayerSprite closestOpposingPlayer, GoalSprite yourTeamGoal, GoalSprite opposingTeamGoal)
    {
        if(currentMood.getName().equals("moveTowardsGoal"))
        {
            if((currentMood.getWeight() > 0.7)&&(currentMood.getWeight() < 0.9))
            {
                return moveTowards(opposingTeamGoal);
            }

            return DONOTHING;
        }
        else if(currentMood.getName().equals("opposingPlayerNearby"))
        {
            if(currentMood.getWeight() > 0.6)
            {
                return moveAway(closestOpposingPlayer);
            }

            return DONOTHING;
        }
        else if(currentMood.getName().equals("markPlayer"))
        {
            if((currentMood.getWeight() > 0.4)&&(currentMood.getWeight() < 0.9))
            {
                return moveTowards(closestOpposingPlayer);
            }

            return DONOTHING;
        }
        else if(currentMood.getName().equals("markGoal"))
        {
            if((currentMood.getWeight() > 0.5)&&(currentMood.getWeight() < 0.7))
            {
                return moveTowardsMiddle(yourTeamGoal, closestOpposingPlayer);
            }

            return DONOTHING;
        }
        else if(currentMood.getName().equals("markPosition"))
        {
            if(currentMood.getWeight() > 0.3)
            {
                return moveTowardsPosition(yourTeamCaptain);
            }

            return DONOTHING;
        }
        else
        {
            return DONOTHING;
        }
    }

    private int moveTowardsMiddle(Sprite inputSprite1, Sprite inputSprite2)
    {
        double midwayX = (inputSprite1.getX() + inputSprite2.getX()) / 2;
        double midwayY = (inputSprite1.getY() + inputSprite2.getY()) / 2;
        
        //normalise to origin
        double relativeXDirecton = midwayX - getX();
        double relativeYDirecton = midwayY - getY();

        return moveBasedOnAngle(relativeXDirecton, relativeYDirecton);
    }

    private int moveTowards(Sprite inputSprite)
    {
        //normalise to origin
        double relativeXDirecton = inputSprite.getX() - getX();
        double relativeYDirecton = inputSprite.getY() - getY();

        return moveBasedOnAngle(relativeXDirecton, relativeYDirecton);
    }

    private int moveAway(Sprite inputSprite)
    {
        //simply swap which point is getting normalised to origin
        double relativeXDirecton = getX() - inputSprite.getX();
        double relativeYDirecton = getY() - inputSprite.getY();

        return moveBasedOnAngle(relativeXDirecton, relativeYDirecton);
    }

    private int moveTowardsPosition(PlayerSprite yourTeamCaptain)
    {
        //get position coordinates
        double positionX = getMarkPositionX(yourTeamCaptain.getX(), yourTeamCaptain.getPosition(), currentPosition);
        double positionY = yourTeamCaptain.getY();

        double relativeXDirecton = positionX - getX();
        double relativeYDirecton = positionY - getY();

        return moveBasedOnAngle(relativeXDirecton, relativeYDirecton);
    }

    private int moveBasedOnAngle(double relativeXDirection, double relativeYDirection)
    {
        //if x is zero, move based on y only
        double angle = 1;

        //change y direction from + downwards (standard in games) to + upwards (standard in maths)
        relativeYDirection = -relativeYDirection;

        if(relativeXDirection != 0)
        {
            //get angle between sprites
            angle = Math.atan(relativeYDirection / relativeXDirection);

            //normalise angle which is currently in the range pi/2 and -pi/2
            angle = (angle / Math.PI) * 2;
        }

        if(relativeYDirection >= 0)
        {
            if(angle == 0)
            {
                if(relativeXDirection >= 0)
                {
                    return MOVERIGHT;
                }
                else
                {
                    return MOVELEFT;
                }
            }
            if((angle > 0)&&(angle < 0.25))
            {
                return MOVERIGHT;
            }
            else if((angle >= 0.25)&&(angle < 0.75))
            {
                return MOVEUPRIGHT;
            }
            else if((angle < 0)&&(angle >= -0.25))
            {
                return MOVELEFT;
            }
            else if((angle < -0.25)&&(angle >= -0.75))
            {
                return MOVEUPLEFT;
            }
            else //if((angle >= 0.75)||(angle < -0.75))
            {
                return MOVEUP;
            }
        }
        else
        {
            if(angle == 0)
            {
                if(relativeXDirection >= 0)
                {
                    return MOVERIGHT;
                }
                else
                {
                    return MOVELEFT;
                }
            }
            if((angle > 0)&&(angle < 0.25))
            {
                return MOVELEFT;
            }
            else if((angle >= 0.25)&&(angle < 0.75))
            {
                return MOVEDOWNLEFT;
            }
            else if((angle < 0)&&(angle >= -0.25))
            {
                return MOVERIGHT;
            }
            else if((angle < -0.25)&&(angle >= -0.75))
            {
                return MOVEDOWNRIGHT;
            }
            else //if((angle >= 0.75)||(angle < -0.75))
            {
                return MOVEDOWN;
            }
        }
    }

    //same principle as the pass ball function on how to move players after they tackle
    void jumpAway(PlayerSprite inputSprite)
    {
        //find the relative x and y direction of the ball based on origin
        double relativeXDirection = getX() - inputSprite.getX();
        double relativeYDirection = getY() - inputSprite.getY();
        //find the slope, (y2 - y1)/(x2 - x1)
        double slopeBetweenPlayers = (relativeYDirection / relativeXDirection);
        //try and move 0.2 in one axis and a smaller amount in the other axis
        //based on the equation y = x*slope and x = y/slope
        //if the slope is greater than 1 or less than -1, we move 0.2 in the y axis (and find x)
        if((slopeBetweenPlayers > 1)||(slopeBetweenPlayers < -1))
        {
            if(relativeYDirection > 0)
            {
                move((200/slopeBetweenPlayers), 200);
            }
            else
            {
                move((-200/slopeBetweenPlayers), -200);
            }
        }
        //if the slope is between 1 and -1, we move 0.2 in the x axis (and find y)
        else if((slopeBetweenPlayers <= 1)&&(slopeBetweenPlayers >= -1))
        {
            if(relativeXDirection > 0)
            {
                move(200, (200*slopeBetweenPlayers));
            }
            else
            {
                move(-200, (-200*slopeBetweenPlayers));
            }
        }
    }
}