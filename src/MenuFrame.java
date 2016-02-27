/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MenuFrame.java
 *
 * Created on 03-Feb-2010, 23:57:49
 */

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Component.Identifier;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Eoin Costelloe
 */
public class MenuFrame extends javax.swing.JFrame
{
    ArrayList<Team> listOfTeams;
    ArrayList<Player> listOfPlayers;
    ArrayList<Controller> listOfGamepads;

    /** Creates new form MenuFrame */
    public MenuFrame()
    {
        listOfTeams = new ArrayList<Team>();
        listOfPlayers = new ArrayList<Player>();
        listOfGamepads = new ArrayList<Controller>();

        try
        {
            setupPlayerList();
            setupTeamList();

            initComponents();

            //add team names to menu lists
            for(int i = 0; i < listOfTeams.size(); i++)
            {
                Team currentTeam = listOfTeams.get(i);
                PlayerMatchTeam1List.addItem(currentTeam.getName());
                PlayerMatchTeam2List.addItem(currentTeam.getName());
                TeamBuilderTeamList.addItem(currentTeam.getName());
            }

            //set the default teams to humans and flying
            PlayerMatchTeam1List.setSelectedIndex(0);
            PlayerMatchTeam2List.setSelectedIndex(2);

            //add player names to menu lists
            for(int i = 0; i < listOfPlayers.size(); i++)
            {
                Player currentPlayer = listOfPlayers.get(i);
                TeamBuilderLeftPlayerName.addItem(currentPlayer.getName());
                TeamBuilderCenterPlayerName.addItem(currentPlayer.getName());
                TeamBuilderRightPlayerName.addItem(currentPlayer.getName());
            }

            //setup default viewing of team 1 in team builder
            Team defaultTeam = listOfTeams.get(0);

            TeamBuilderLeftPlayerImage.setIcon(new ImageIcon(defaultTeam.getLeftPlayer().getProfileImage()));
            TeamBuilderLeftPlayerSpeedValue.setText(Double.toString(defaultTeam.getLeftPlayer().getSpeed()));
            TeamBuilderLeftPlayerPowerValue.setText(Double.toString(defaultTeam.getLeftPlayer().getPower()));
            TeamBuilderLeftPlayerTackleRangeValue.setText(Double.toString(defaultTeam.getLeftPlayer().getTackleRange()));
            TeamBuilderLeftPlayerInterRangeValue.setText(Double.toString(defaultTeam.getLeftPlayer().getInterceptRange()));
            TeamBuilderLeftPlayerInterStrengthValue.setText(Double.toString(defaultTeam.getLeftPlayer().getInterceptStrength()));
            TeamBuilderLeftPlayerName.setSelectedItem(defaultTeam.getLeftPlayer().getName());

            TeamBuilderCenterPlayerImage.setIcon(new ImageIcon(defaultTeam.getCenterPlayer().getProfileImage()));
            TeamBuilderCenterPlayerSpeedValue.setText(Double.toString(defaultTeam.getCenterPlayer().getSpeed()));
            TeamBuilderCenterPlayerPowerValue.setText(Double.toString(defaultTeam.getCenterPlayer().getPower()));
            TeamBuilderCenterPlayerTackleRangeValue.setText(Double.toString(defaultTeam.getCenterPlayer().getTackleRange()));
            TeamBuilderCenterPlayerInterRangeValue.setText(Double.toString(defaultTeam.getCenterPlayer().getInterceptRange()));
            TeamBuilderCenterPlayerInterStrengthValue.setText(Double.toString(defaultTeam.getCenterPlayer().getInterceptStrength()));
            TeamBuilderCenterPlayerName.setSelectedItem(defaultTeam.getCenterPlayer().getName());

            TeamBuilderRightPlayerImage.setIcon(new ImageIcon(defaultTeam.getRightPlayer().getProfileImage()));
            TeamBuilderRightPlayerSpeedValue.setText(Double.toString(defaultTeam.getRightPlayer().getSpeed()));
            TeamBuilderRightPlayerPowerValue.setText(Double.toString(defaultTeam.getRightPlayer().getPower()));
            TeamBuilderRightPlayerTackleRangeValue.setText(Double.toString(defaultTeam.getRightPlayer().getTackleRange()));
            TeamBuilderRightPlayerInterRangeValue.setText(Double.toString(defaultTeam.getRightPlayer().getInterceptRange()));
            TeamBuilderRightPlayerInterStrengthValue.setText(Double.toString(defaultTeam.getRightPlayer().getInterceptStrength()));
            TeamBuilderRightPlayerName.setSelectedItem(defaultTeam.getRightPlayer().getName());

            //get the enviroment for the current computer
            ControllerEnvironment currentEnvironment = ControllerEnvironment.getDefaultEnvironment();
            if(currentEnvironment.isSupported())
            {
                //get all controllers attached
                Controller[] currentControllers = currentEnvironment.getControllers();

                listOfGamepads = new ArrayList<Controller>();

                //get the list of gamepads from this list of controllers
                for(int i = 0; i < currentControllers.length; i++)
                {
                    if((currentControllers[i].getType() == Controller.Type.GAMEPAD)&&(currentControllers[i].getComponent(Identifier.Axis.POV) != null)&&(currentControllers[i].getComponent(Identifier.Button._0) != null))
                    {
                        listOfGamepads.add(currentControllers[i]);
                    }
                }

                //update the list of controllers
                for(int i = 0; i < listOfGamepads.size(); i++)
                {
                    ControlsPlayer1Type.addItem((i + 1) + " " + listOfGamepads.get(i).getName());
                    ControlsPlayer2Type.addItem((i + 1) + " " + listOfGamepads.get(i).getName());
                    PlayerMatchTeam1ControlList.addItem((i + 1) + " " + listOfGamepads.get(i).getName());
                    PlayerMatchTeam2ControlList.addItem((i + 1) + " " + listOfGamepads.get(i).getName());
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Controllers not supported in this Operating System.", "", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Problem accessing program files.", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuTabs = new javax.swing.JTabbedPane();
        MainMenuPanel = new javax.swing.JPanel();
        QuickMatchButton = new javax.swing.JButton();
        PlayerMatchButton = new javax.swing.JButton();
        TeamBuilderButton = new javax.swing.JButton();
        ControlsButton = new javax.swing.JButton();
        PlayerMatchPanel = new javax.swing.JPanel();
        PlayerMatchMapLabel = new javax.swing.JLabel();
        PlayerMatchMapList = new javax.swing.JComboBox();
        PlayerMatchTeam1Label = new javax.swing.JLabel();
        PlayerMatchTeam1List = new javax.swing.JComboBox();
        PlayerMatchTeam2Label = new javax.swing.JLabel();
        PlayerMatchTeam2List = new javax.swing.JComboBox();
        PlayerMatchDiffLabel = new javax.swing.JLabel();
        PlayerMatchDiffList = new javax.swing.JComboBox();
        PlayerMatchStartButton = new javax.swing.JButton();
        PlayerMatchMainMenu = new javax.swing.JButton();
        PlayerMatchTimeLabel = new javax.swing.JLabel();
        PlayerMatchTimeValue = new javax.swing.JComboBox();
        PlayerMatchTeam1ControlLabel = new javax.swing.JLabel();
        PlayerMatchTeam1ControlList = new javax.swing.JComboBox();
        PlayerMatchTeam2ControlLabel = new javax.swing.JLabel();
        PlayerMatchTeam2ControlList = new javax.swing.JComboBox();
        TeamBuilderPanel = new javax.swing.JPanel();
        TeamBuilderNameLabel = new javax.swing.JLabel();
        TeamBuilderTeamList = new javax.swing.JComboBox();
        TeamBuilderLeftPlayerImage = new javax.swing.JLabel();
        TeamBuilderCenterPlayerImage = new javax.swing.JLabel();
        TeamBuilderRightPlayerImage = new javax.swing.JLabel();
        TeamBuilderMainMenu = new javax.swing.JButton();
        TeamBuilderSaveButton = new javax.swing.JButton();
        TeamBuilderLeftPlayerName = new javax.swing.JComboBox();
        TeamBuilderCenterPlayerName = new javax.swing.JComboBox();
        TeamBuilderRightPlayerName = new javax.swing.JComboBox();
        TeamBuilderNewTeamButton = new javax.swing.JButton();
        TeamBuilderNewTeamText = new javax.swing.JTextField();
        TeamBuilderLeftPlayerSpeedLabel = new javax.swing.JLabel();
        TeamBuilderLeftPlayerPowerLabel = new javax.swing.JLabel();
        TeamBuilderLeftPlayerTackleRangeLabel = new javax.swing.JLabel();
        TeamBuilderLeftPlayerInterRangeLabel = new javax.swing.JLabel();
        TeamBuilderLeftPlayerInterStrengthLabel = new javax.swing.JLabel();
        TeamBuilderLeftPlayerSpeedValue = new javax.swing.JLabel();
        TeamBuilderLeftPlayerPowerValue = new javax.swing.JLabel();
        TeamBuilderLeftPlayerTackleRangeValue = new javax.swing.JLabel();
        TeamBuilderLeftPlayerInterRangeValue = new javax.swing.JLabel();
        TeamBuilderLeftPlayerInterStrengthValue = new javax.swing.JLabel();
        TeamBuilderCenterPlayerSpeedLabel = new javax.swing.JLabel();
        TeamBuilderCenterPlayerPowerLabel = new javax.swing.JLabel();
        TeamBuilderCenterPlayerTackleRangeLabel = new javax.swing.JLabel();
        TeamBuilderCenterPlayerInterRangeLabel = new javax.swing.JLabel();
        TeamBuilderCenterPlayerInterStrengthLabel = new javax.swing.JLabel();
        TeamBuilderCenterPlayerSpeedValue = new javax.swing.JLabel();
        TeamBuilderCenterPlayerPowerValue = new javax.swing.JLabel();
        TeamBuilderCenterPlayerTackleRangeValue = new javax.swing.JLabel();
        TeamBuilderCenterPlayerInterRangeValue = new javax.swing.JLabel();
        TeamBuilderCenterPlayerInterStrengthValue = new javax.swing.JLabel();
        TeamBuilderRightPlayerSpeedLabel = new javax.swing.JLabel();
        TeamBuilderRightPlayerPowerLabel = new javax.swing.JLabel();
        TeamBuilderRightPlayerTackleRangeLabel = new javax.swing.JLabel();
        TeamBuilderRightPlayerInterRangeLabel = new javax.swing.JLabel();
        TeamBuilderRightPlayerInterStrengthLabel = new javax.swing.JLabel();
        TeamBuilderRightPlayerSpeedValue = new javax.swing.JLabel();
        TeamBuilderRightPlayerPowerValue = new javax.swing.JLabel();
        TeamBuilderRightPlayerTackleRangeValue = new javax.swing.JLabel();
        TeamBuilderRightPlayerInterRangeValue = new javax.swing.JLabel();
        TeamBuilderRightPlayerInterStrengthValue = new javax.swing.JLabel();
        ControlsPanel = new javax.swing.JPanel();
        ControlsPlayer1Label = new javax.swing.JLabel();
        ControlsPlayer1Type = new javax.swing.JComboBox();
        ControlsPlayer1LeftLabel = new javax.swing.JLabel();
        ControlsPlayer1RightLabel = new javax.swing.JLabel();
        ControlsPlayer1UpLabel = new javax.swing.JLabel();
        ControlsPlayer1DownLabel = new javax.swing.JLabel();
        ControlsPlayer1PassLabel = new javax.swing.JLabel();
        ControlsPlayer1LeftValue = new javax.swing.JLabel();
        ControlsPlayer1RightValue = new javax.swing.JLabel();
        ControlsPlayer1UpValue = new javax.swing.JLabel();
        ControlsPlayer1DownValue = new javax.swing.JLabel();
        ControlsPlayer1PassValue = new javax.swing.JLabel();
        ControlsPlayer2Label = new javax.swing.JLabel();
        ControlsPlayer2Type = new javax.swing.JComboBox();
        ControlsPlayer2LeftLabel = new javax.swing.JLabel();
        ControlsPlayer2UpLabel = new javax.swing.JLabel();
        ControlsPlayer2RightLabel = new javax.swing.JLabel();
        ControlsPlayer2DownLabel = new javax.swing.JLabel();
        ControlsPlayer2LeftValue = new javax.swing.JLabel();
        ControlsPlayer2RightValue = new javax.swing.JLabel();
        ControlsPlayer2UpValue = new javax.swing.JLabel();
        ControlsPlayer2DownValue = new javax.swing.JLabel();
        ControlsPlayer2PassLabel = new javax.swing.JLabel();
        ControlsPlayer2PassValue = new javax.swing.JLabel();
        ControlsMainMenu = new javax.swing.JButton();
        ControlsPauseLabel = new javax.swing.JLabel();
        ControlsPauseValue = new javax.swing.JLabel();
        ControlsExitLabel = new javax.swing.JLabel();
        ControlsExitValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SwitchBall");
        setResizable(false);

        MenuTabs.setEnabled(false);

        QuickMatchButton.setText("Quick Match");
        QuickMatchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuickMatchButtonActionPerformed(evt);
            }
        });

        PlayerMatchButton.setText("Player Match");
        PlayerMatchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayerMatchButtonActionPerformed(evt);
            }
        });

        TeamBuilderButton.setText("Team Builder");
        TeamBuilderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeamBuilderButtonActionPerformed(evt);
            }
        });

        ControlsButton.setText("Controls");
        ControlsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ControlsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MainMenuPanelLayout = new javax.swing.GroupLayout(MainMenuPanel);
        MainMenuPanel.setLayout(MainMenuPanelLayout);
        MainMenuPanelLayout.setHorizontalGroup(
            MainMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(QuickMatchButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(PlayerMatchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(TeamBuilderButton, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(ControlsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
                .addContainerGap())
        );
        MainMenuPanelLayout.setVerticalGroup(
            MainMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(QuickMatchButton)
                .addGap(18, 18, 18)
                .addComponent(PlayerMatchButton)
                .addGap(18, 18, 18)
                .addComponent(TeamBuilderButton)
                .addGap(18, 18, 18)
                .addComponent(ControlsButton)
                .addContainerGap(231, Short.MAX_VALUE))
        );

        MenuTabs.addTab("Main Menu", MainMenuPanel);

        PlayerMatchMapLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        PlayerMatchMapLabel.setText("Map:");

        PlayerMatchMapList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Desert", "Grass", "Cloud" }));

        PlayerMatchTeam1Label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        PlayerMatchTeam1Label.setText("Team 1:");

        PlayerMatchTeam2Label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        PlayerMatchTeam2Label.setText("Team 2:");

        PlayerMatchDiffLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        PlayerMatchDiffLabel.setText("AI Difficulty:");

        PlayerMatchDiffList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Easy", "Medium", "Hard" }));
        PlayerMatchDiffList.setSelectedIndex(1);

        PlayerMatchStartButton.setText("Start Game");
        PlayerMatchStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayerMatchStartButtonActionPerformed(evt);
            }
        });

        PlayerMatchMainMenu.setText("Main Menu");
        PlayerMatchMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayerMatchMainMenuActionPerformed(evt);
            }
        });

        PlayerMatchTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        PlayerMatchTimeLabel.setText("Time Limit:");

        PlayerMatchTimeValue.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30", "40", "50", "60" }));
        PlayerMatchTimeValue.setSelectedIndex(3);

        PlayerMatchTeam1ControlLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        PlayerMatchTeam1ControlLabel.setText("Control Type:");

        PlayerMatchTeam1ControlList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keyboard" }));
        PlayerMatchTeam1ControlList.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                PlayerMatchTeam1ControlListPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        PlayerMatchTeam2ControlLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        PlayerMatchTeam2ControlLabel.setText("Control Type:");

        PlayerMatchTeam2ControlList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keyboard" }));
        PlayerMatchTeam2ControlList.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                PlayerMatchTeam2ControlListPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout PlayerMatchPanelLayout = new javax.swing.GroupLayout(PlayerMatchPanel);
        PlayerMatchPanel.setLayout(PlayerMatchPanelLayout);
        PlayerMatchPanelLayout.setHorizontalGroup(
            PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PlayerMatchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PlayerMatchStartButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(PlayerMatchMainMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PlayerMatchPanelLayout.createSequentialGroup()
                        .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PlayerMatchPanelLayout.createSequentialGroup()
                                .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PlayerMatchTeam2ControlLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(PlayerMatchTeam2Label, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(PlayerMatchTeam1Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(PlayerMatchMapLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                        .addComponent(PlayerMatchTeam1ControlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18))
                            .addGroup(PlayerMatchPanelLayout.createSequentialGroup()
                                .addComponent(PlayerMatchDiffLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(PlayerMatchPanelLayout.createSequentialGroup()
                                .addComponent(PlayerMatchTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                .addGap(18, 18, 18)))
                        .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PlayerMatchTimeValue, javax.swing.GroupLayout.Alignment.TRAILING, 0, 210, Short.MAX_VALUE)
                            .addComponent(PlayerMatchDiffList, javax.swing.GroupLayout.Alignment.TRAILING, 0, 210, Short.MAX_VALUE)
                            .addComponent(PlayerMatchMapList, 0, 210, Short.MAX_VALUE)
                            .addComponent(PlayerMatchTeam1ControlList, javax.swing.GroupLayout.Alignment.TRAILING, 0, 210, Short.MAX_VALUE)
                            .addComponent(PlayerMatchTeam2ControlList, 0, 210, Short.MAX_VALUE)
                            .addComponent(PlayerMatchTeam2List, 0, 210, Short.MAX_VALUE)
                            .addComponent(PlayerMatchTeam1List, javax.swing.GroupLayout.Alignment.TRAILING, 0, 210, Short.MAX_VALUE))))
                .addContainerGap())
        );
        PlayerMatchPanelLayout.setVerticalGroup(
            PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PlayerMatchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlayerMatchMapLabel)
                    .addComponent(PlayerMatchMapList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlayerMatchTeam1Label)
                    .addComponent(PlayerMatchTeam1List, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlayerMatchTeam1ControlLabel)
                    .addComponent(PlayerMatchTeam1ControlList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlayerMatchTeam2List, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PlayerMatchTeam2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlayerMatchTeam2ControlLabel)
                    .addComponent(PlayerMatchTeam2ControlList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlayerMatchDiffLabel)
                    .addComponent(PlayerMatchDiffList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PlayerMatchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlayerMatchTimeLabel)
                    .addComponent(PlayerMatchTimeValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(PlayerMatchStartButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PlayerMatchMainMenu)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        MenuTabs.addTab("Player Match", PlayerMatchPanel);

        TeamBuilderNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderNameLabel.setText("Team Name:");

        TeamBuilderTeamList.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                TeamBuilderTeamListPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        TeamBuilderLeftPlayerImage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TeamBuilderLeftPlayerImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Characters/BeetleProfile.png"))); // NOI18N
        TeamBuilderLeftPlayerImage.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        TeamBuilderLeftPlayerImage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        TeamBuilderCenterPlayerImage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TeamBuilderCenterPlayerImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Characters/SaraProfile.png"))); // NOI18N

        TeamBuilderRightPlayerImage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TeamBuilderRightPlayerImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Characters/WolfProfile.png"))); // NOI18N

        TeamBuilderMainMenu.setText("Main Menu");
        TeamBuilderMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeamBuilderMainMenuActionPerformed(evt);
            }
        });

        TeamBuilderSaveButton.setText("Save");
        TeamBuilderSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeamBuilderSaveButtonActionPerformed(evt);
            }
        });

        TeamBuilderLeftPlayerName.setEnabled(false);
        TeamBuilderLeftPlayerName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                TeamBuilderLeftPlayerNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        TeamBuilderCenterPlayerName.setEnabled(false);
        TeamBuilderCenterPlayerName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                TeamBuilderCenterPlayerNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        TeamBuilderRightPlayerName.setEnabled(false);
        TeamBuilderRightPlayerName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                TeamBuilderRightPlayerNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        TeamBuilderNewTeamButton.setText("New Team");
        TeamBuilderNewTeamButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeamBuilderNewTeamButtonActionPerformed(evt);
            }
        });

        TeamBuilderLeftPlayerSpeedLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderLeftPlayerSpeedLabel.setText("Speed:");

        TeamBuilderLeftPlayerPowerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderLeftPlayerPowerLabel.setText("Power:");

        TeamBuilderLeftPlayerTackleRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderLeftPlayerTackleRangeLabel.setText("Tkl Rng:");

        TeamBuilderLeftPlayerInterRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderLeftPlayerInterRangeLabel.setText("Intr Rng:");

        TeamBuilderLeftPlayerInterStrengthLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderLeftPlayerInterStrengthLabel.setText("Intr Str:");

        TeamBuilderLeftPlayerSpeedValue.setText(" ");

        TeamBuilderLeftPlayerPowerValue.setText(" ");

        TeamBuilderLeftPlayerTackleRangeValue.setText(" ");

        TeamBuilderLeftPlayerInterRangeValue.setText(" ");

        TeamBuilderLeftPlayerInterStrengthValue.setText(" ");

        TeamBuilderCenterPlayerSpeedLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderCenterPlayerSpeedLabel.setText("Speed:");

        TeamBuilderCenterPlayerPowerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderCenterPlayerPowerLabel.setText("Power:");

        TeamBuilderCenterPlayerTackleRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderCenterPlayerTackleRangeLabel.setText("Tkl Rng:");

        TeamBuilderCenterPlayerInterRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderCenterPlayerInterRangeLabel.setText("Intr Rng:");

        TeamBuilderCenterPlayerInterStrengthLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderCenterPlayerInterStrengthLabel.setText("Intr Str:");

        TeamBuilderCenterPlayerSpeedValue.setText(" ");

        TeamBuilderCenterPlayerPowerValue.setText(" ");

        TeamBuilderCenterPlayerTackleRangeValue.setText(" ");

        TeamBuilderCenterPlayerInterRangeValue.setText(" ");

        TeamBuilderCenterPlayerInterStrengthValue.setText(" ");

        TeamBuilderRightPlayerSpeedLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderRightPlayerSpeedLabel.setText("Speed:");

        TeamBuilderRightPlayerPowerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderRightPlayerPowerLabel.setText("Power:");

        TeamBuilderRightPlayerTackleRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderRightPlayerTackleRangeLabel.setText("Tkl Rng:");

        TeamBuilderRightPlayerInterRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderRightPlayerInterRangeLabel.setText("Intr Rng:");

        TeamBuilderRightPlayerInterStrengthLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TeamBuilderRightPlayerInterStrengthLabel.setText("Intr Str:");

        TeamBuilderRightPlayerSpeedValue.setText(" ");

        TeamBuilderRightPlayerPowerValue.setText(" ");

        TeamBuilderRightPlayerTackleRangeValue.setText(" ");

        TeamBuilderRightPlayerInterRangeValue.setText(" ");

        TeamBuilderRightPlayerInterStrengthValue.setText(" ");

        javax.swing.GroupLayout TeamBuilderPanelLayout = new javax.swing.GroupLayout(TeamBuilderPanel);
        TeamBuilderPanel.setLayout(TeamBuilderPanelLayout);
        TeamBuilderPanelLayout.setHorizontalGroup(
            TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TeamBuilderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TeamBuilderPanelLayout.createSequentialGroup()
                        .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(TeamBuilderNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TeamBuilderNewTeamButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TeamBuilderTeamList, 0, 254, Short.MAX_VALUE)
                            .addComponent(TeamBuilderNewTeamText, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)))
                    .addGroup(TeamBuilderPanelLayout.createSequentialGroup()
                        .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TeamBuilderLeftPlayerName, 0, 120, Short.MAX_VALUE)
                            .addComponent(TeamBuilderLeftPlayerImage, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TeamBuilderPanelLayout.createSequentialGroup()
                                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TeamBuilderLeftPlayerInterRangeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderLeftPlayerSpeedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderLeftPlayerPowerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderLeftPlayerTackleRangeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderLeftPlayerInterStrengthLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TeamBuilderLeftPlayerInterStrengthValue, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderLeftPlayerInterRangeValue, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderLeftPlayerTackleRangeValue, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderLeftPlayerPowerValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderLeftPlayerSpeedValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TeamBuilderCenterPlayerName, javax.swing.GroupLayout.Alignment.LEADING, 0, 114, Short.MAX_VALUE)
                            .addComponent(TeamBuilderCenterPlayerImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                            .addGroup(TeamBuilderPanelLayout.createSequentialGroup()
                                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TeamBuilderCenterPlayerInterRangeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderCenterPlayerSpeedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderCenterPlayerPowerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderCenterPlayerTackleRangeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderCenterPlayerInterStrengthLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TeamBuilderCenterPlayerInterStrengthValue, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderCenterPlayerInterRangeValue, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderCenterPlayerTackleRangeValue, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderCenterPlayerPowerValue, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderCenterPlayerSpeedValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TeamBuilderRightPlayerImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addGroup(TeamBuilderPanelLayout.createSequentialGroup()
                                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TeamBuilderRightPlayerInterRangeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderRightPlayerSpeedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderRightPlayerPowerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderRightPlayerTackleRangeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderRightPlayerInterStrengthLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TeamBuilderRightPlayerInterStrengthValue, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderRightPlayerInterRangeValue, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderRightPlayerTackleRangeValue, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderRightPlayerPowerValue, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                    .addComponent(TeamBuilderRightPlayerSpeedValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
                            .addComponent(TeamBuilderRightPlayerName, 0, 109, Short.MAX_VALUE)))
                    .addGroup(TeamBuilderPanelLayout.createSequentialGroup()
                        .addComponent(TeamBuilderMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TeamBuilderSaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
                .addContainerGap())
        );
        TeamBuilderPanelLayout.setVerticalGroup(
            TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TeamBuilderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TeamBuilderNewTeamButton)
                    .addComponent(TeamBuilderNewTeamText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TeamBuilderNameLabel)
                    .addComponent(TeamBuilderTeamList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TeamBuilderRightPlayerImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TeamBuilderCenterPlayerImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TeamBuilderLeftPlayerImage, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TeamBuilderLeftPlayerSpeedLabel)
                    .addComponent(TeamBuilderLeftPlayerSpeedValue)
                    .addComponent(TeamBuilderCenterPlayerSpeedLabel)
                    .addComponent(TeamBuilderCenterPlayerSpeedValue)
                    .addComponent(TeamBuilderRightPlayerSpeedLabel)
                    .addComponent(TeamBuilderRightPlayerSpeedValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TeamBuilderLeftPlayerPowerLabel)
                    .addComponent(TeamBuilderLeftPlayerPowerValue)
                    .addComponent(TeamBuilderCenterPlayerPowerLabel)
                    .addComponent(TeamBuilderCenterPlayerPowerValue)
                    .addComponent(TeamBuilderRightPlayerPowerLabel)
                    .addComponent(TeamBuilderRightPlayerPowerValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TeamBuilderLeftPlayerTackleRangeLabel)
                    .addComponent(TeamBuilderLeftPlayerTackleRangeValue)
                    .addComponent(TeamBuilderCenterPlayerTackleRangeLabel)
                    .addComponent(TeamBuilderCenterPlayerTackleRangeValue)
                    .addComponent(TeamBuilderRightPlayerTackleRangeLabel)
                    .addComponent(TeamBuilderRightPlayerTackleRangeValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TeamBuilderLeftPlayerInterRangeLabel)
                    .addComponent(TeamBuilderLeftPlayerInterRangeValue)
                    .addComponent(TeamBuilderCenterPlayerInterRangeLabel)
                    .addComponent(TeamBuilderCenterPlayerInterRangeValue)
                    .addComponent(TeamBuilderRightPlayerInterRangeLabel)
                    .addComponent(TeamBuilderRightPlayerInterRangeValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TeamBuilderLeftPlayerInterStrengthLabel)
                    .addComponent(TeamBuilderLeftPlayerInterStrengthValue)
                    .addComponent(TeamBuilderCenterPlayerInterStrengthLabel)
                    .addComponent(TeamBuilderCenterPlayerInterStrengthValue)
                    .addComponent(TeamBuilderRightPlayerInterStrengthLabel)
                    .addComponent(TeamBuilderRightPlayerInterStrengthValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TeamBuilderLeftPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TeamBuilderCenterPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TeamBuilderRightPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(TeamBuilderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TeamBuilderMainMenu)
                    .addComponent(TeamBuilderSaveButton))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        MenuTabs.addTab("Team Builder", TeamBuilderPanel);

        ControlsPlayer1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ControlsPlayer1Label.setText("Player 1:");

        ControlsPlayer1Type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keyboard" }));
        ControlsPlayer1Type.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ControlsPlayer1TypePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        ControlsPlayer1LeftLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer1LeftLabel.setText("Move Left:");

        ControlsPlayer1RightLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer1RightLabel.setText("Move Right:");

        ControlsPlayer1UpLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer1UpLabel.setText("Move Up:");

        ControlsPlayer1DownLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer1DownLabel.setText("Move Down:");

        ControlsPlayer1PassLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer1PassLabel.setText("Pass/Shoot:");

        ControlsPlayer1LeftValue.setText("Left Key");

        ControlsPlayer1RightValue.setText("Right Key");

        ControlsPlayer1UpValue.setText("Up Key");

        ControlsPlayer1DownValue.setText("Down Key");

        ControlsPlayer1PassValue.setText("Enter Key");

        ControlsPlayer2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ControlsPlayer2Label.setText("Player 2:");

        ControlsPlayer2Type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keyboard" }));
        ControlsPlayer2Type.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ControlsPlayer2TypePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        ControlsPlayer2LeftLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer2LeftLabel.setText("Move Left:");

        ControlsPlayer2UpLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer2UpLabel.setText("Move Up:");

        ControlsPlayer2RightLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer2RightLabel.setText("Move Right:");

        ControlsPlayer2DownLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer2DownLabel.setText("Move Down:");

        ControlsPlayer2LeftValue.setText("A Key");

        ControlsPlayer2RightValue.setText("D Key");

        ControlsPlayer2UpValue.setText("W Key");

        ControlsPlayer2DownValue.setText("S Key");

        ControlsPlayer2PassLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPlayer2PassLabel.setText("Pass/Shoot:");

        ControlsPlayer2PassValue.setText("Space Key");

        ControlsMainMenu.setText("Main Menu");
        ControlsMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ControlsMainMenuActionPerformed(evt);
            }
        });

        ControlsPauseLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsPauseLabel.setText("Pause Game:");

        ControlsPauseValue.setText("P Key");

        ControlsExitLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ControlsExitLabel.setText("Exit Game:");

        ControlsExitValue.setText("Escape Key");

        javax.swing.GroupLayout ControlsPanelLayout = new javax.swing.GroupLayout(ControlsPanel);
        ControlsPanel.setLayout(ControlsPanelLayout);
        ControlsPanelLayout.setHorizontalGroup(
            ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ControlsPlayer2Label, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addGroup(ControlsPanelLayout.createSequentialGroup()
                        .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ControlsPlayer1PassLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ControlsPlayer1LeftLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ControlsPlayer1RightLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ControlsPanelLayout.createSequentialGroup()
                                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ControlsPlayer1RightValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ControlsPlayer1LeftValue, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ControlsPlayer1UpLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ControlsPlayer1DownLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ControlsPlayer1DownValue, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                    .addComponent(ControlsPlayer1UpValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                            .addComponent(ControlsPlayer1PassValue, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)))
                    .addComponent(ControlsPlayer1Label, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(ControlsPlayer2Type, 0, 355, Short.MAX_VALUE)
                    .addGroup(ControlsPanelLayout.createSequentialGroup()
                        .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ControlsPlayer2PassLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ControlsPlayer2LeftLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ControlsPlayer2RightLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ControlsPanelLayout.createSequentialGroup()
                                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ControlsPlayer2RightValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ControlsPlayer2LeftValue, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ControlsPlayer2UpLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ControlsPlayer2DownLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ControlsPlayer2DownValue, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(ControlsPlayer2UpValue, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)))
                            .addComponent(ControlsPlayer2PassValue, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)))
                    .addGroup(ControlsPanelLayout.createSequentialGroup()
                        .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ControlsExitLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ControlsPauseLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ControlsExitValue, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(ControlsPauseValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)))
                    .addComponent(ControlsMainMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(ControlsPlayer1Type, 0, 355, Short.MAX_VALUE))
                .addContainerGap())
        );
        ControlsPanelLayout.setVerticalGroup(
            ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ControlsPlayer1Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ControlsPlayer1Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ControlsPlayer1LeftLabel)
                    .addComponent(ControlsPlayer1UpLabel)
                    .addComponent(ControlsPlayer1LeftValue)
                    .addComponent(ControlsPlayer1UpValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ControlsPlayer1RightLabel)
                    .addComponent(ControlsPlayer1DownLabel)
                    .addComponent(ControlsPlayer1RightValue)
                    .addComponent(ControlsPlayer1DownValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ControlsPlayer1PassLabel)
                    .addComponent(ControlsPlayer1PassValue))
                .addGap(18, 18, 18)
                .addComponent(ControlsPlayer2Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ControlsPlayer2Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ControlsPlayer2LeftLabel)
                    .addComponent(ControlsPlayer2UpLabel)
                    .addComponent(ControlsPlayer2LeftValue)
                    .addComponent(ControlsPlayer2UpValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ControlsPlayer2RightLabel)
                    .addComponent(ControlsPlayer2DownLabel)
                    .addComponent(ControlsPlayer2RightValue)
                    .addComponent(ControlsPlayer2DownValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ControlsPlayer2PassLabel)
                    .addComponent(ControlsPlayer2PassValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ControlsPauseValue)
                    .addComponent(ControlsPauseLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ControlsExitLabel)
                    .addComponent(ControlsExitValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ControlsMainMenu)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        MenuTabs.addTab("Controls", ControlsPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MenuTabs)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MenuTabs)
                .addContainerGap())
        );

        MenuTabs.getAccessibleContext().setAccessibleName("tab2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PlayerMatchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayerMatchButtonActionPerformed

        MenuTabs.setSelectedIndex(1);

    }//GEN-LAST:event_PlayerMatchButtonActionPerformed

    private void PlayerMatchStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayerMatchStartButtonActionPerformed

        //kill the main frame
        setVisible(false);
        dispose();

        String currentMap = (String)PlayerMatchMapList.getSelectedItem();
        int timeLimit = Integer.parseInt((String)(PlayerMatchTimeValue.getSelectedItem()));

        Controller player1Controller = null;
        if(PlayerMatchTeam1ControlList.getSelectedIndex() != 0)
        {
            player1Controller = listOfGamepads.get(PlayerMatchTeam1ControlList.getSelectedIndex() - 1);
        }

        Controller player2Controller = null;
        if(PlayerMatchTeam2ControlList.getSelectedIndex() != 0)
        {
            player2Controller = listOfGamepads.get(PlayerMatchTeam2ControlList.getSelectedIndex() - 1);
        }
        
        Match singleMatch = new Match(currentMap, listOfTeams.get(PlayerMatchTeam1List.getSelectedIndex()), listOfTeams.get(PlayerMatchTeam2List.getSelectedIndex()), PlayerMatchDiffList.getSelectedIndex(), timeLimit, player1Controller, player2Controller);
        singleMatch.start();

    }//GEN-LAST:event_PlayerMatchStartButtonActionPerformed

    private void QuickMatchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuickMatchButtonActionPerformed

        //kill the main frame
        setVisible(false);
        dispose();
        
        Match quickMatch = new Match();
        quickMatch.start();
        
    }//GEN-LAST:event_QuickMatchButtonActionPerformed

    private void PlayerMatchMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayerMatchMainMenuActionPerformed

        MenuTabs.setSelectedIndex(0);
        
    }//GEN-LAST:event_PlayerMatchMainMenuActionPerformed

    private void TeamBuilderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeamBuilderButtonActionPerformed

        MenuTabs.setSelectedIndex(2);

    }//GEN-LAST:event_TeamBuilderButtonActionPerformed

    private void TeamBuilderMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeamBuilderMainMenuActionPerformed

        MenuTabs.setSelectedIndex(0);
        
    }//GEN-LAST:event_TeamBuilderMainMenuActionPerformed

    private void TeamBuilderSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeamBuilderSaveButtonActionPerformed

        //convert team object into a DOM and save as XML
        try
        {
            //convert current list of teams into DOM
            DocumentBuilderFactory currentDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder currentDocumentBuilder = currentDocumentBuilderFactory.newDocumentBuilder();
            Document currentDocument = currentDocumentBuilder.newDocument();

            //create root element
            Element currentTeamsElement = currentDocument.createElement("teams");
            currentDocument.appendChild(currentTeamsElement);

            //go through each team whcih isnt default and add to DOM tree
            for(int i = 3; i < listOfTeams.size(); i++)
            {
                //define team element
                Element currentTeamElement = currentDocument.createElement("team");
                Team currentTeam = listOfTeams.get(i);
                currentTeamElement.setAttribute("name", currentTeam.getName());

                //define left player element
                Element currentLeftPlayerElement  = currentDocument.createElement("player");
                currentLeftPlayerElement.setAttribute("name", currentTeam.getLeftPlayer().getName());
                currentTeamElement.appendChild(currentLeftPlayerElement);

                //define center player element
                Element currentCenterPlayerElement  = currentDocument.createElement("player");
                currentCenterPlayerElement.setAttribute("name", currentTeam.getCenterPlayer().getName());
                currentTeamElement.appendChild(currentCenterPlayerElement);

                //define right player element
                Element currentRightPlayerElement  = currentDocument.createElement("player");
                currentRightPlayerElement.setAttribute("name", currentTeam.getRightPlayer().getName());
                currentTeamElement.appendChild(currentRightPlayerElement);

                currentTeamsElement.appendChild(currentTeamElement);
            }

            //define the format for the outputting XML and output the file
            OutputFormat XMLFormat = new OutputFormat(currentDocument);
            XMLFormat.setIndenting(true);
            XMLFormat.setLineSeparator("\r\n");
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(new File("teams.xml")), XMLFormat);
            serializer.serialize(currentDocument);


            JOptionPane.showMessageDialog(this, "Team data successfully saved.", "", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Problem saving Team data.", "", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_TeamBuilderSaveButtonActionPerformed

    private void TeamBuilderLeftPlayerNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_TeamBuilderLeftPlayerNamePopupMenuWillBecomeInvisible

        Team currentTeam = listOfTeams.get(TeamBuilderTeamList.getSelectedIndex());

        for(int j = 0; j < listOfPlayers.size(); j++)
        {
            Player currentPlayer = listOfPlayers.get(j);
            if(currentPlayer.getName().equals(TeamBuilderLeftPlayerName.getSelectedItem()))
                currentTeam.setLeftPlayer(currentPlayer);
        }

        TeamBuilderLeftPlayerImage.setIcon(new ImageIcon(currentTeam.getLeftPlayer().getProfileImage()));
        TeamBuilderLeftPlayerSpeedValue.setText(Double.toString(currentTeam.getLeftPlayer().getSpeed()));
        TeamBuilderLeftPlayerPowerValue.setText(Double.toString(currentTeam.getLeftPlayer().getPower()));
        TeamBuilderLeftPlayerTackleRangeValue.setText(Double.toString(currentTeam.getLeftPlayer().getTackleRange()));
        TeamBuilderLeftPlayerInterRangeValue.setText(Double.toString(currentTeam.getLeftPlayer().getInterceptRange()));
        TeamBuilderLeftPlayerInterStrengthValue.setText(Double.toString(currentTeam.getLeftPlayer().getInterceptStrength()));

    }//GEN-LAST:event_TeamBuilderLeftPlayerNamePopupMenuWillBecomeInvisible

    private void TeamBuilderCenterPlayerNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_TeamBuilderCenterPlayerNamePopupMenuWillBecomeInvisible

        Team currentTeam = listOfTeams.get(TeamBuilderTeamList.getSelectedIndex());

        for(int j = 0; j < listOfPlayers.size(); j++)
        {
            Player currentPlayer = listOfPlayers.get(j);
            if(currentPlayer.getName().equals(TeamBuilderCenterPlayerName.getSelectedItem()))
                currentTeam.setCenterPlayer(currentPlayer);
        }

        TeamBuilderCenterPlayerImage.setIcon(new ImageIcon(currentTeam.getCenterPlayer().getProfileImage()));
        TeamBuilderCenterPlayerSpeedValue.setText(Double.toString(currentTeam.getCenterPlayer().getSpeed()));
        TeamBuilderCenterPlayerPowerValue.setText(Double.toString(currentTeam.getCenterPlayer().getPower()));
        TeamBuilderCenterPlayerTackleRangeValue.setText(Double.toString(currentTeam.getCenterPlayer().getTackleRange()));
        TeamBuilderCenterPlayerInterRangeValue.setText(Double.toString(currentTeam.getCenterPlayer().getInterceptRange()));
        TeamBuilderCenterPlayerInterStrengthValue.setText(Double.toString(currentTeam.getCenterPlayer().getInterceptStrength()));

    }//GEN-LAST:event_TeamBuilderCenterPlayerNamePopupMenuWillBecomeInvisible

    private void TeamBuilderRightPlayerNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_TeamBuilderRightPlayerNamePopupMenuWillBecomeInvisible

        Team currentTeam = listOfTeams.get(TeamBuilderTeamList.getSelectedIndex());

        for(int j = 0; j < listOfPlayers.size(); j++)
        {
            Player currentPlayer = listOfPlayers.get(j);
            if(currentPlayer.getName().equals(TeamBuilderRightPlayerName.getSelectedItem()))
                currentTeam.setRightPlayer(currentPlayer);
        }

        TeamBuilderRightPlayerImage.setIcon(new ImageIcon(currentTeam.getRightPlayer().getProfileImage()));
        TeamBuilderRightPlayerSpeedValue.setText(Double.toString(currentTeam.getRightPlayer().getSpeed()));
        TeamBuilderRightPlayerPowerValue.setText(Double.toString(currentTeam.getRightPlayer().getPower()));
        TeamBuilderRightPlayerTackleRangeValue.setText(Double.toString(currentTeam.getRightPlayer().getTackleRange()));
        TeamBuilderRightPlayerInterRangeValue.setText(Double.toString(currentTeam.getRightPlayer().getInterceptRange()));
        TeamBuilderRightPlayerInterStrengthValue.setText(Double.toString(currentTeam.getRightPlayer().getInterceptStrength()));
        
    }//GEN-LAST:event_TeamBuilderRightPlayerNamePopupMenuWillBecomeInvisible

    private void TeamBuilderTeamListPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_TeamBuilderTeamListPopupMenuWillBecomeInvisible

        //get team selected
        Team currentTeam = listOfTeams.get(TeamBuilderTeamList.getSelectedIndex());

        //setup the players profile image and name associated with the team
        TeamBuilderLeftPlayerImage.setIcon(new ImageIcon(currentTeam.getLeftPlayer().getProfileImage()));
        TeamBuilderLeftPlayerSpeedValue.setText(Double.toString(currentTeam.getLeftPlayer().getSpeed()));
        TeamBuilderLeftPlayerPowerValue.setText(Double.toString(currentTeam.getLeftPlayer().getPower()));
        TeamBuilderLeftPlayerTackleRangeValue.setText(Double.toString(currentTeam.getLeftPlayer().getTackleRange()));
        TeamBuilderLeftPlayerInterRangeValue.setText(Double.toString(currentTeam.getLeftPlayer().getInterceptRange()));
        TeamBuilderLeftPlayerInterStrengthValue.setText(Double.toString(currentTeam.getLeftPlayer().getInterceptStrength()));
        TeamBuilderLeftPlayerName.setSelectedItem(currentTeam.getLeftPlayer().getName());

        TeamBuilderCenterPlayerImage.setIcon(new ImageIcon(currentTeam.getCenterPlayer().getProfileImage()));
        TeamBuilderCenterPlayerSpeedValue.setText(Double.toString(currentTeam.getCenterPlayer().getSpeed()));
        TeamBuilderCenterPlayerPowerValue.setText(Double.toString(currentTeam.getCenterPlayer().getPower()));
        TeamBuilderCenterPlayerTackleRangeValue.setText(Double.toString(currentTeam.getCenterPlayer().getTackleRange()));
        TeamBuilderCenterPlayerInterRangeValue.setText(Double.toString(currentTeam.getCenterPlayer().getInterceptRange()));
        TeamBuilderCenterPlayerInterStrengthValue.setText(Double.toString(currentTeam.getCenterPlayer().getInterceptStrength()));
        TeamBuilderCenterPlayerName.setSelectedItem(currentTeam.getCenterPlayer().getName());

        TeamBuilderRightPlayerImage.setIcon(new ImageIcon(currentTeam.getRightPlayer().getProfileImage()));
        TeamBuilderRightPlayerSpeedValue.setText(Double.toString(currentTeam.getRightPlayer().getSpeed()));
        TeamBuilderRightPlayerPowerValue.setText(Double.toString(currentTeam.getRightPlayer().getPower()));
        TeamBuilderRightPlayerTackleRangeValue.setText(Double.toString(currentTeam.getRightPlayer().getTackleRange()));
        TeamBuilderRightPlayerInterRangeValue.setText(Double.toString(currentTeam.getRightPlayer().getInterceptRange()));
        TeamBuilderRightPlayerInterStrengthValue.setText(Double.toString(currentTeam.getRightPlayer().getInterceptStrength()));
        TeamBuilderRightPlayerName.setSelectedItem(currentTeam.getRightPlayer().getName());

        //if the team is not a default team, allow the user to modify the team
        if(TeamBuilderTeamList.getSelectedIndex() >= 3)
        {
            TeamBuilderLeftPlayerName.setEnabled(true);
            TeamBuilderCenterPlayerName.setEnabled(true);
            TeamBuilderRightPlayerName.setEnabled(true);
        }
        else
        {
            TeamBuilderLeftPlayerName.setEnabled(false);
            TeamBuilderCenterPlayerName.setEnabled(false);
            TeamBuilderRightPlayerName.setEnabled(false);
        }
    }//GEN-LAST:event_TeamBuilderTeamListPopupMenuWillBecomeInvisible

    private void TeamBuilderNewTeamButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeamBuilderNewTeamButtonActionPerformed

        //do not create a team if the user hasnt entered a name
        if(!TeamBuilderNewTeamText.getText().equals(""))
        {
            //create new team based on text entered, the first player is the default player
            Team newTeam = new Team(TeamBuilderNewTeamText.getText(), listOfPlayers.get(0), listOfPlayers.get(0), listOfPlayers.get(0));
            //add new team to list
            listOfTeams.add(newTeam);
            //clear text input field
            TeamBuilderNewTeamText.setText("");

            //add team to menu lists
            PlayerMatchTeam1List.addItem(newTeam.getName());
            PlayerMatchTeam2List.addItem(newTeam.getName());
            TeamBuilderTeamList.addItem(newTeam.getName());

            //set the team to the new team created
            TeamBuilderTeamList.setSelectedIndex(TeamBuilderTeamList.getItemCount()-1);
            TeamBuilderTeamListPopupMenuWillBecomeInvisible(null);

            //tell user it was a success
            JOptionPane.showMessageDialog(this, "New team created successfully.", "", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please enter a name for the team.", "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_TeamBuilderNewTeamButtonActionPerformed

    private void ControlsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ControlsButtonActionPerformed

        MenuTabs.setSelectedIndex(3);

    }//GEN-LAST:event_ControlsButtonActionPerformed

    private void ControlsMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ControlsMainMenuActionPerformed

        MenuTabs.setSelectedIndex(0);

    }//GEN-LAST:event_ControlsMainMenuActionPerformed

    private void ControlsPlayer1TypePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ControlsPlayer1TypePopupMenuWillBecomeInvisible

        //this is the keyboard
        if(ControlsPlayer1Type.getSelectedIndex() == 0)
        {
            ControlsPlayer1LeftValue.setText("Left Key");
            ControlsPlayer1RightValue.setText("Right Key");
            ControlsPlayer1UpValue.setText("Up Key");
            ControlsPlayer1DownValue.setText("Down Key");
            ControlsPlayer1PassValue.setText("Enter Key");
        }
        //this is a gamepad
        else
        {
            ControlsPlayer1LeftValue.setText("Left D-Pad");
            ControlsPlayer1RightValue.setText("Right D-Pad");
            ControlsPlayer1UpValue.setText("Up D-Pad");
            ControlsPlayer1DownValue.setText("Down D-Pad");
            ControlsPlayer1PassValue.setText("A Button");
        }

    }//GEN-LAST:event_ControlsPlayer1TypePopupMenuWillBecomeInvisible

    private void ControlsPlayer2TypePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ControlsPlayer2TypePopupMenuWillBecomeInvisible

        //this is the keyboard
        if(ControlsPlayer2Type.getSelectedIndex() == 0)
        {
            ControlsPlayer2LeftValue.setText("A Key");
            ControlsPlayer2RightValue.setText("D Key");
            ControlsPlayer2UpValue.setText("W Key");
            ControlsPlayer2DownValue.setText("S Key");
            ControlsPlayer2PassValue.setText("Space Key");
        }
        //this is a gamepad
        else
        {
            ControlsPlayer2LeftValue.setText("Left D-Pad");
            ControlsPlayer2RightValue.setText("Right D-Pad");
            ControlsPlayer2UpValue.setText("Up D-Pad");
            ControlsPlayer2DownValue.setText("Down D-Pad");
            ControlsPlayer2PassValue.setText("A Button");
        }

    }//GEN-LAST:event_ControlsPlayer2TypePopupMenuWillBecomeInvisible

    private void PlayerMatchTeam1ControlListPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_PlayerMatchTeam1ControlListPopupMenuWillBecomeInvisible

        if((PlayerMatchTeam1ControlList.getSelectedIndex() != 0)&&(PlayerMatchTeam1ControlList.getSelectedIndex() == PlayerMatchTeam2ControlList.getSelectedIndex()))
        {
            PlayerMatchTeam1ControlList.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this, "This controller is already in use.", "", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_PlayerMatchTeam1ControlListPopupMenuWillBecomeInvisible

    private void PlayerMatchTeam2ControlListPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_PlayerMatchTeam2ControlListPopupMenuWillBecomeInvisible

        if((PlayerMatchTeam2ControlList.getSelectedIndex() != 0)&&(PlayerMatchTeam2ControlList.getSelectedIndex() == PlayerMatchTeam1ControlList.getSelectedIndex()))
        {
            PlayerMatchTeam2ControlList.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this, "This controller is already in use.", "", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_PlayerMatchTeam2ControlListPopupMenuWillBecomeInvisible

    /**
    * @param args the command line arguments
    */
    public static void main(String args[])
    {
        //start up menu frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ControlsButton;
    private javax.swing.JLabel ControlsExitLabel;
    private javax.swing.JLabel ControlsExitValue;
    private javax.swing.JButton ControlsMainMenu;
    private javax.swing.JPanel ControlsPanel;
    private javax.swing.JLabel ControlsPauseLabel;
    private javax.swing.JLabel ControlsPauseValue;
    private javax.swing.JLabel ControlsPlayer1DownLabel;
    private javax.swing.JLabel ControlsPlayer1DownValue;
    private javax.swing.JLabel ControlsPlayer1Label;
    private javax.swing.JLabel ControlsPlayer1LeftLabel;
    private javax.swing.JLabel ControlsPlayer1LeftValue;
    private javax.swing.JLabel ControlsPlayer1PassLabel;
    private javax.swing.JLabel ControlsPlayer1PassValue;
    private javax.swing.JLabel ControlsPlayer1RightLabel;
    private javax.swing.JLabel ControlsPlayer1RightValue;
    private javax.swing.JComboBox ControlsPlayer1Type;
    private javax.swing.JLabel ControlsPlayer1UpLabel;
    private javax.swing.JLabel ControlsPlayer1UpValue;
    private javax.swing.JLabel ControlsPlayer2DownLabel;
    private javax.swing.JLabel ControlsPlayer2DownValue;
    private javax.swing.JLabel ControlsPlayer2Label;
    private javax.swing.JLabel ControlsPlayer2LeftLabel;
    private javax.swing.JLabel ControlsPlayer2LeftValue;
    private javax.swing.JLabel ControlsPlayer2PassLabel;
    private javax.swing.JLabel ControlsPlayer2PassValue;
    private javax.swing.JLabel ControlsPlayer2RightLabel;
    private javax.swing.JLabel ControlsPlayer2RightValue;
    private javax.swing.JComboBox ControlsPlayer2Type;
    private javax.swing.JLabel ControlsPlayer2UpLabel;
    private javax.swing.JLabel ControlsPlayer2UpValue;
    private javax.swing.JPanel MainMenuPanel;
    private javax.swing.JTabbedPane MenuTabs;
    private javax.swing.JButton PlayerMatchButton;
    private javax.swing.JLabel PlayerMatchDiffLabel;
    private javax.swing.JComboBox PlayerMatchDiffList;
    private javax.swing.JButton PlayerMatchMainMenu;
    private javax.swing.JLabel PlayerMatchMapLabel;
    private javax.swing.JComboBox PlayerMatchMapList;
    private javax.swing.JPanel PlayerMatchPanel;
    private javax.swing.JButton PlayerMatchStartButton;
    private javax.swing.JLabel PlayerMatchTeam1ControlLabel;
    private javax.swing.JComboBox PlayerMatchTeam1ControlList;
    private javax.swing.JLabel PlayerMatchTeam1Label;
    private javax.swing.JComboBox PlayerMatchTeam1List;
    private javax.swing.JLabel PlayerMatchTeam2ControlLabel;
    private javax.swing.JComboBox PlayerMatchTeam2ControlList;
    private javax.swing.JLabel PlayerMatchTeam2Label;
    private javax.swing.JComboBox PlayerMatchTeam2List;
    private javax.swing.JLabel PlayerMatchTimeLabel;
    private javax.swing.JComboBox PlayerMatchTimeValue;
    private javax.swing.JButton QuickMatchButton;
    private javax.swing.JButton TeamBuilderButton;
    private javax.swing.JLabel TeamBuilderCenterPlayerImage;
    private javax.swing.JLabel TeamBuilderCenterPlayerInterRangeLabel;
    private javax.swing.JLabel TeamBuilderCenterPlayerInterRangeValue;
    private javax.swing.JLabel TeamBuilderCenterPlayerInterStrengthLabel;
    private javax.swing.JLabel TeamBuilderCenterPlayerInterStrengthValue;
    private javax.swing.JComboBox TeamBuilderCenterPlayerName;
    private javax.swing.JLabel TeamBuilderCenterPlayerPowerLabel;
    private javax.swing.JLabel TeamBuilderCenterPlayerPowerValue;
    private javax.swing.JLabel TeamBuilderCenterPlayerSpeedLabel;
    private javax.swing.JLabel TeamBuilderCenterPlayerSpeedValue;
    private javax.swing.JLabel TeamBuilderCenterPlayerTackleRangeLabel;
    private javax.swing.JLabel TeamBuilderCenterPlayerTackleRangeValue;
    private javax.swing.JLabel TeamBuilderLeftPlayerImage;
    private javax.swing.JLabel TeamBuilderLeftPlayerInterRangeLabel;
    private javax.swing.JLabel TeamBuilderLeftPlayerInterRangeValue;
    private javax.swing.JLabel TeamBuilderLeftPlayerInterStrengthLabel;
    private javax.swing.JLabel TeamBuilderLeftPlayerInterStrengthValue;
    private javax.swing.JComboBox TeamBuilderLeftPlayerName;
    private javax.swing.JLabel TeamBuilderLeftPlayerPowerLabel;
    private javax.swing.JLabel TeamBuilderLeftPlayerPowerValue;
    private javax.swing.JLabel TeamBuilderLeftPlayerSpeedLabel;
    private javax.swing.JLabel TeamBuilderLeftPlayerSpeedValue;
    private javax.swing.JLabel TeamBuilderLeftPlayerTackleRangeLabel;
    private javax.swing.JLabel TeamBuilderLeftPlayerTackleRangeValue;
    private javax.swing.JButton TeamBuilderMainMenu;
    private javax.swing.JLabel TeamBuilderNameLabel;
    private javax.swing.JButton TeamBuilderNewTeamButton;
    private javax.swing.JTextField TeamBuilderNewTeamText;
    private javax.swing.JPanel TeamBuilderPanel;
    private javax.swing.JLabel TeamBuilderRightPlayerImage;
    private javax.swing.JLabel TeamBuilderRightPlayerInterRangeLabel;
    private javax.swing.JLabel TeamBuilderRightPlayerInterRangeValue;
    private javax.swing.JLabel TeamBuilderRightPlayerInterStrengthLabel;
    private javax.swing.JLabel TeamBuilderRightPlayerInterStrengthValue;
    private javax.swing.JComboBox TeamBuilderRightPlayerName;
    private javax.swing.JLabel TeamBuilderRightPlayerPowerLabel;
    private javax.swing.JLabel TeamBuilderRightPlayerPowerValue;
    private javax.swing.JLabel TeamBuilderRightPlayerSpeedLabel;
    private javax.swing.JLabel TeamBuilderRightPlayerSpeedValue;
    private javax.swing.JLabel TeamBuilderRightPlayerTackleRangeLabel;
    private javax.swing.JLabel TeamBuilderRightPlayerTackleRangeValue;
    private javax.swing.JButton TeamBuilderSaveButton;
    private javax.swing.JComboBox TeamBuilderTeamList;
    // End of variables declaration//GEN-END:variables

    private void setupPlayerList() throws IOException
    {
        //setup the list of players
        listOfPlayers.add(new Player("BlueImp", 0.14, 80, 80, 100, 0.9));
        listOfPlayers.add(new Player("GreenImp", 0.13, 90, 80, 110, 0.9));
        listOfPlayers.add(new Player("RedImp", 0.12, 100, 80, 110, 1.1));
        listOfPlayers.add(new Player("Sara", 0.1, 120, 80, 100, 1.3));
        listOfPlayers.add(new Player("Beetle", 0.1, 110, 60, 100, 1.4));
        listOfPlayers.add(new Player("Wolf", 0.12, 100, 80, 100, 1.2));
    }

    private void setupTeamList() throws ParserConfigurationException, SAXException, IOException
    {
        //put in default teams and players
        listOfTeams.add(new Team("Sara", listOfPlayers.get(4), listOfPlayers.get(3), listOfPlayers.get(5)));
        listOfTeams.add(new Team("Imps", listOfPlayers.get(1), listOfPlayers.get(0), listOfPlayers.get(2)));

        if(new File("teams.xml").canRead())
        {
            //read in xml of team details
            DocumentBuilderFactory currentDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder currentDocumentBuilder = currentDocumentBuilderFactory.newDocumentBuilder();
            Document currentDocument = currentDocumentBuilder.parse("teams.xml");

            //get list of teams
            Element currentTeams = currentDocument.getDocumentElement();
            NodeList currentTeamNodeList = currentTeams.getElementsByTagName("team");

            //search through each team in xml
            for(int i = 0 ; i < currentTeamNodeList.getLength();i++)
            {
                Element currentTeam = (Element)currentTeamNodeList.item(i);

                //get list of players on team
                NodeList currentPlayerNodeList = currentTeam.getElementsByTagName("player");

                //extract the names from the players
                Element currentPlayerElement = (Element)currentPlayerNodeList.item(0);
                String currentLeftPlayerName = currentPlayerElement.getAttribute("name");
                currentPlayerElement = (Element)currentPlayerNodeList.item(1);
                String currentCenterPlayerName = currentPlayerElement.getAttribute("name");
                currentPlayerElement = (Element)currentPlayerNodeList.item(2);
                String currentRightPlayerName = currentPlayerElement.getAttribute("name");

                Player currentLeftPlayer = null;
                Player currentCenterPlayer = null;
                Player currentRightPlayer = null;

                //find player in player list based on name
                for(int j = 0; j < listOfPlayers.size(); j++)
                {
                    Player currentPlayer = listOfPlayers.get(j);
                    if(currentPlayer.getName().equals(currentLeftPlayerName))
                        currentLeftPlayer = currentPlayer;
                    if(currentPlayer.getName().equals(currentCenterPlayerName))
                        currentCenterPlayer = currentPlayer;
                    if(currentPlayer.getName().equals(currentRightPlayerName))
                        currentRightPlayer = currentPlayer;
                }

                //do not add new team if any player does not exist
                if((currentLeftPlayer != null)&&(currentCenterPlayer != null)&&(currentRightPlayer != null))
                {
                    //create new team based on data
                    Team newTeam = new Team(currentTeam.getAttribute("name"), currentLeftPlayer, currentCenterPlayer, currentRightPlayer);
                    //add new team to list
                    listOfTeams.add(newTeam);
                }
            }
        }
    }
}
