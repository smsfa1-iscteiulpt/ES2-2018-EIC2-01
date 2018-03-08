package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TimeConstraintsPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField idealTime;
    private JTextField maxTime;
    private JButton nextButton;
    private boolean idealOk = false;
    private boolean maxOk = false;

    public TimeConstraintsPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    public void initialize() {
	// TODO Auto-generated method stub
	this.idealTime = new JTextField(5);	
	this.maxTime = new JTextField(5);
	this.nextButton = FrameUtils.cuteButton("Next");
	this.nextButton.setEnabled(false);
    }

    @Override
    public void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel();
	titlePanel.setLayout(new BorderLayout());
	titlePanel.setBackground(Color.WHITE);
	titlePanel.add(new JLabel("Time constraints"), BorderLayout.WEST);
	mainPanel.add(titlePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	JLabel infoIcon = new JLabel();
	infoIcon.setIcon(new ImageIcon("./src/frames/images/info_icon.png"));
	infoPanel.add(infoIcon, BorderLayout.WEST);
	JLabel infoLabel = new JLabel("<html>In this section you can set constraints for the algorithm's runtime. To do so, fill in"
		+ "<br> the fields below with optimal and maximum time frames (in minutes) for"
		+ "<br> calculating an optimal solution.</html>");
	infoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	infoLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
	infoPanel.add(infoLabel, BorderLayout.CENTER);
	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel idealPanel = new JPanel();
	idealPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	idealPanel.setBackground(Color.white);
	idealPanel.add(new JLabel("Ideal time frame:           "));
	idealTime.setBorder(FrameUtils.cuteBorder());
	idealPanel.add(idealTime);

	idealTime.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent e) {
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		try {
		    Integer.parseInt(idealTime.getText());
		    idealOk = true;
		    if(idealOk==true && maxOk==true) {
			nextButton.setEnabled(true);
		    }
		} catch(NumberFormatException e1) {
		    nextButton.setEnabled(false);
		}

		try {
		    Double.parseDouble(idealTime.getText());
		    idealOk = true;
		    if(idealOk==true && maxOk==true) {
			nextButton.setEnabled(true);
		    }
		} catch (NumberFormatException e2) {
		    nextButton.setEnabled(false);
		}
	    }


	    @Override
	    public void keyPressed(KeyEvent e) {
	    }
	});

	idealPanel.add(new JLabel("min"));
	mainPanel.add(idealPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel maxPanel = new JPanel();
	maxPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	maxPanel.setBackground(Color.white);
	maxPanel.add(new JLabel("Maximum time frame: "));
	maxTime.setBorder(FrameUtils.cuteBorder());
	maxPanel.add(maxTime);

	maxTime.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent e) {
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		try {
		    Integer.parseInt(maxTime.getText());
		    maxOk = true;
		    if(idealOk==true && maxOk==true) {
			nextButton.setEnabled(true);
		    }
		} catch(NumberFormatException e1) {
		    nextButton.setEnabled(false);
		}

		try {
		    Double.parseDouble(maxTime.getText());
		    maxOk = true;
		    if(idealOk==true && maxOk==true) {
			nextButton.setEnabled(true);
		    }
		} catch (NumberFormatException e2) {
		    nextButton.setEnabled(false);
		}
	    }


	    @Override
	    public void keyPressed(KeyEvent e) {
	    }
	});

	maxPanel.add(new JLabel("min"));
	mainPanel.add(maxPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

    }

    @Override
    public void createButtonsPanel() {
	JButton backButton = FrameUtils.cuteButton("Back");
	backButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.goToPreviousPage();
	    }
	});
	buttonsPanel.add(backButton);

	buttonsPanel.add(new JLabel()); // to add space between the two buttons

	JButton cancelButton = FrameUtils.cuteButton("Cancel");
	cancelButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});
	buttonsPanel.add(cancelButton);

	buttonsPanel.add(new JLabel()); // to add space between the two buttons

	nextButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.goToNextPage();
	    }
	});
	buttonsPanel.add(nextButton);
    }

    @Override
    public void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

}