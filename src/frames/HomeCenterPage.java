package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HomeCenterPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JButton nextButton;
    private JButton emailButton;
    
    public HomeCenterPage(UserInterface userInterface) {
	super(userInterface);
	// TODO Auto-generated constructor stub
    }

    @Override
    public void initialize() {
	nextButton = FrameUtils.cuteButton("Next");
	emailButton = FrameUtils.cuteButton("Send email");
    }

    @Override
    public void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
	
	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	infoPanel.add(new JLabel("Frequently Asked Questions"), BorderLayout.WEST);
	mainPanel.add(infoPanel);
	
	FrameUtils.addEmptyLabels(mainPanel, 1);
	
	JPanel messagePanel = new JPanel();
	messagePanel.setLayout(new BorderLayout());
	messagePanel.setBackground(Color.WHITE);
	JLabel messageLabel = new JLabel("<html>In this section you can get fast answers to most of your questions"
		+ "<br></br><br></br>" 
		+ "<font color=red><b>Question_1</b></font> "
		+ "<br></br>"
		+ "Answer block to question 1"
		+ "<br></br>" 
		+ "<br><font color=blue>Question_2</font> "
		+ "<br></br>" 
		+ "Answer block to question 2"
		+ "<br></br><br></br><br></br>"
		+ "If you have any questions remaining please contact us!"
		+ "</html>");
	messagePanel.add(messageLabel, BorderLayout.WEST);
	mainPanel.add(messagePanel);
	
	FrameUtils.addEmptyLabels(mainPanel, 1);
	
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BorderLayout());
	buttonPanel.setBackground(Color.WHITE);
	buttonPanel.add(emailButton, BorderLayout.WEST);
	
	emailButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.goToEmailPage();
	    }
	});
	
	mainPanel.add(buttonPanel);
	
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

	// to add space between the two buttons
	buttonsPanel.add(new JLabel());

	JButton cancelButton = FrameUtils.cuteButton("Cancel");
	cancelButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});
	buttonsPanel.add(cancelButton);

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
