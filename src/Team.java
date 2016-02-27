/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eoin Costelloe
 */
public class Team
{
    String name;
    Player leftPlayer;
    Player centerPlayer;
    Player rightPlayer;

    Team(String inputName, Player inputLeftPlayer, Player inputCenterPlayer, Player inputRightPlayer)
    {
        name = inputName;
        leftPlayer = inputLeftPlayer;
        centerPlayer = inputCenterPlayer;
        rightPlayer = inputRightPlayer;
    }

    String getName()
    {
        return name;
    }

    Player getLeftPlayer()
    {
        return leftPlayer;
    }

    Player getCenterPlayer()
    {
        return centerPlayer;
    }

    Player getRightPlayer()
    {
        return rightPlayer;
    }

    void setLeftPlayer(Player inputLeftPlayer)
    {
        leftPlayer = inputLeftPlayer;
    }

    void setCenterPlayer(Player inputCenterPlayer)
    {
        centerPlayer = inputCenterPlayer;
    }

    void setRightPlayer(Player inputRightPlayer)
    {
        rightPlayer = inputRightPlayer;
    }
}
