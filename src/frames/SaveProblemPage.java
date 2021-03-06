package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import frames.frameUtils.Email;
import frames.frameUtils.FrameUtils;
import utils.UserFileUtils;

/**
 * This class represents the Save Problem Page
 */

public class SaveProblemPage extends SuperPage {

    private static final long serialVersionUID = 1L;
    private JPanel savePanel;
    private JTextField fileName;
    private JTextField filePath;
    private JButton saveButton;
    private JButton finishButton;
    private String messageText = "Muito obrigado por usar esta plataforma de otimiza��o. Ser� informado por email sobre o "
	    + "progresso do processo de otimiza��o, quando o processo de otimiza��o tiver atingido 25%, 50%, 75% do"
	    + " total do tempo estimado, e tamb�m quando o processo tiver terminado, com sucesso ou devido � "
	    + "ocorr�ncia de erros.";

    /**
     * 
     * @param userInterface
     */
    public SaveProblemPage(UserInterface userInterface) {
	super(userInterface);
    }

    @Override
    protected void initialize() {
	this.fileName = new JTextField(30);
	this.filePath = new JTextField(30);
	this.saveButton = FrameUtils.cuteButton("Save");
	this.saveButton.setEnabled(false);
	this.finishButton = FrameUtils.cuteButton("Finish");
    }

    @Override
    protected void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(50, 0, 0, 0));
	mainPanel.setBackground(Color.white);

	JPanel infoPanel = new JPanel(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	JLabel infoLabel = new JLabel(" Save this problem's configuration before submitting it to evaluation");
	infoLabel.setFont(FrameUtils.cuteFont(14));
	JLabel infoIcon = new JLabel();
	infoIcon.setIcon(new ImageIcon(getClass().getResource("images/save_icon.png")));
	infoPanel.add(infoIcon, BorderLayout.WEST);
	infoPanel.add(infoLabel, BorderLayout.CENTER);
	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel namePanel = new JPanel(new BorderLayout());
	namePanel.setBackground(Color.WHITE);
	JLabel nameLabel = new JLabel("File name:   ");
	nameLabel.setFont(FrameUtils.cuteFont(12));
	namePanel.add(nameLabel, BorderLayout.WEST);
	fileName.setBorder(FrameUtils.cuteBorder());

	namePanel.add(fileName, BorderLayout.CENTER);
	mainPanel.add(namePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	continueCreateMainPanel();
    }

    /**
     * Method to create the main part of the page to be viewed
     */
    private void continueCreateMainPanel() {
	JPanel pathPanel = new JPanel(new BorderLayout());
	pathPanel.setBackground(Color.WHITE);
	JLabel pathLabel = new JLabel("Path:            ");
	pathLabel.setFont(FrameUtils.cuteFont(12));
	pathPanel.add(pathLabel, BorderLayout.WEST);
	filePath.setBorder(FrameUtils.cuteBorder());

	filePath.addKeyListener(new KeyListener() {
	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
		if (!filePath.getText().trim().isEmpty()) {
		    // TODO: Rever se isto funciona, acho que n�o
		    try {
			Paths.get(filePath.getText());
			saveButton.setEnabled(true);
		    } catch (InvalidPathException | NullPointerException ex1) {
			saveButton.setEnabled(false);
		    }
		}

	    }

	    @Override
	    public void keyTyped(KeyEvent arg0) {
	    }
	});

	pathPanel.add(filePath, BorderLayout.CENTER);
	mainPanel.add(pathPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel infoPanel = new JPanel(new BorderLayout());
	infoPanel.setBackground(Color.WHITE);
	JPanel auxPanel = new JPanel(new BorderLayout());
	auxPanel.setBackground(Color.WHITE);
	JLabel infoIcon = new JLabel();
	infoIcon.setIcon(new ImageIcon(getClass().getResource("images/info_icon.png")));
	auxPanel.add(infoIcon, BorderLayout.NORTH);
	infoPanel.add(auxPanel, BorderLayout.WEST);
	JLabel infoLabel = new JLabel(
		"<html>Please note that, when indicating a path, you <b>must use ABSOLUTE <br> names</b>. "
			+ "For instance, if you wish to save the document in your Desktop <br>"
			+ "it <font color=red><b>isn't sufficient to write 'Desktop'</b></font>. Instead, you should opt for <u>one</u> of<br> "
			+ "the variantions listed below: <br> "
			+ "<font color=green><b>     C://Users//Username//Desktop/</b></font><br>"
			+ "<font color=green><b>     C://Users//Username//Desktop</b></font><br>"
			+ "<font color=green><b>     C:/Users/Username/Desktop</b></font><br><br>"
			+ "In addition, the file name field can only contain letters and/or numbers<br>"
			+ "and it must end with the extension .xml.</html>");
	infoLabel.setFont(FrameUtils.cuteFont(12));
	infoLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
	infoPanel.add(infoLabel, BorderLayout.CENTER);
	mainPanel.add(infoPanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	savePanel = new JPanel(new BorderLayout());
	savePanel.setBackground(Color.WHITE);
	savePanel.add(saveButton, BorderLayout.WEST);
	mainPanel.add(savePanel);

    }

    @Override
    protected void createButtonsPanel() {

	saveButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		try {
		    userInterface.setFinalProblem();
		    // TODO O fileName n�o pode conter caracteres especiais e
		    // tem de terminar em .xml
		    UserFileUtils.writeToXML(userInterface.getProblem(), filePath.getText(), "/" + fileName.getText());
		    saveButton.setBackground(new Color(155, 226, 155).brighter());
		    Email email = new Email(userInterface.getUserEmail());
		    email.setToCC(userInterface.getAdmin().getEmail());
		    email.sendEmailWithAttachment(("Otimiza��o em curso: " + fileName.getText() + " " + new Date()),
			    messageText, filePath.getText(), fileName.getText());
		} catch (Exception e) {
		    saveButton.setBackground(Color.RED.brighter());
		}
		savePanel.validate();
		savePanel.repaint();
		System.out.println(userInterface.getProblem().toString());
		System.out.println(filePath.getText());
	    }
	});

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

	finishButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.runProblem();
	    }
	});
	buttonsPanel.add(finishButton);
    }

    @Override
    protected void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

    @Override
    protected boolean areAllDataWellFilled() {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    protected void saveToProblem() {
	// TODO Auto-generated method stub
    }

    @Override
    protected void clearDataFromPage() {
	fileName.setText(null);
	filePath.setText(null);
    }
}
