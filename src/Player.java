/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Eoin Costelloe
 */
public class Player
{
    String name;
    double speed;
    double power;
    double tackleRange;
    double interceptRange;
    double interceptStrength;
    BufferedImage profileImage;

    Player(String inputName, double inputSpeed, double inputPower, double inputTackleRange, double inputInterceptRange, double inputInterceptStrength)
    {
        name = inputName;
        speed = inputSpeed;
        power = inputPower;
        tackleRange = inputTackleRange;
        interceptRange = inputInterceptRange;
        interceptStrength = inputInterceptStrength;
        try
        {
            profileImage = ImageIO.read(Player.class.getResourceAsStream("Characters/" + name + "Profile.png"));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    String getName()
    {
        return name;
    }

    double getSpeed()
    {
        return speed;
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

    BufferedImage getProfileImage()
    {
        return profileImage;
    }
}
