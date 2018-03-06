package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class DecisionVariablesPage extends SuperPage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final int standardNumberOfVariables = 3;
    private ArrayList<DecisionVariablesObject> decisionVariableList;
    private JPanel warningPanel;
    private JPanel warningPanel2;
    private JPanel subSubMainPanel;

    public DecisionVariablesPage(UserInterface userInterface) {
	super(userInterface);
	// TODO Auto-generated constructor stub
    }

    private JButton nextButton;

    @Override
    public void initialize() {
	nextButton = FrameUtils.cuteButton("Next");	
	decisionVariableList = new ArrayList<DecisionVariablesObject>();

	warningPanel = new JPanel();
	warningPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	warningPanel.setBackground(Color.WHITE);
	JLabel warningIcon = new JLabel();
	warningIcon.setIcon(new ImageIcon("./src/frames/images/warning_icon.png"));
	warningPanel.add(warningIcon);
	JLabel warning = new JLabel("Optimization criterias must have unique names");
	warning.setForeground(Color.red);
	warningPanel.add(warning);

	warningPanel2 = new JPanel();
	warningPanel2.setLayout(new FlowLayout(FlowLayout.LEADING));
	warningPanel2.setBackground(Color.WHITE);
	JLabel warningIcon2 = new JLabel();
	warningIcon2.setIcon(new ImageIcon("./src/frames/images/warning_icon.png"));
	warningPanel2.add(warningIcon2);
	JLabel warning2 = new JLabel("Lower bound and upper bound must agree with the data type selected");
	warning2.setForeground(Color.red);
	warningPanel2.add(warning2);
    }

    @Override
    public void createMainPanel() {
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	// XXX change when frame size is set
	mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

	JPanel titlePanel = new JPanel();
	titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	titlePanel.setBackground(Color.WHITE);
	titlePanel.add(new JLabel("Decision Variables"));
	mainPanel.add(titlePanel);

	FrameUtils.addEmptyLabels(mainPanel, 1);

	JPanel subMainPanel = new JPanel();
	subMainPanel.setBackground(Color.WHITE);
	subMainPanel.setLayout(new BoxLayout(subMainPanel, BoxLayout.Y_AXIS));
	subMainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	infoPanel.setBackground(Color.WHITE);
	JLabel name = new JLabel("Name");
	name.setBorder(new EmptyBorder(0, 0, 0, 45)); //to add space between the labels
	infoPanel.add(name);
	JLabel dataType = new JLabel("Data Type");
	dataType.setBorder(new EmptyBorder(0, 0, 0, 15));
	infoPanel.add(dataType);
	infoPanel.add(new JLabel("Lower Bound  "));
	infoPanel.add(new JLabel("Upper Bound  "));
	infoPanel.add(new JLabel("Domain"));

	subMainPanel.add(infoPanel);


	subSubMainPanel = new JPanel();
	subSubMainPanel.setBackground(Color.WHITE);
	subSubMainPanel.setLayout(new BoxLayout(subSubMainPanel, BoxLayout.Y_AXIS));

	for(int i = 0; i != standardNumberOfVariables; i++) {
	    DecisionVariablesObject decisionVariable = new DecisionVariablesObject(this);
	    decisionVariableList.add(decisionVariable);
	    subSubMainPanel.add(decisionVariable.transformIntoAPanel());
	}

	subMainPanel.add(subSubMainPanel);

	JPanel addOptionPanel = new JPanel();
	addOptionPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	addOptionPanel.setBackground(Color.WHITE);
	JLabel addIcon = new JLabel();
	addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));

	DecisionVariablesPage tmp = this;

	addIcon.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable(){
		    public void run(){
			DecisionVariablesObject decisionVariable = new DecisionVariablesObject(tmp);
			decisionVariableList.add(decisionVariable);
			subSubMainPanel.add(decisionVariable.transformIntoAPanel());
			validate(); //to update window
			repaint(); //to update window
		    }
		});
	    }

	    @Override
	    public void mouseEntered(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseExited(MouseEvent arg0) {
	    }

	    @Override
	    public void mousePressed(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseReleased(MouseEvent arg0) {
	    }

	});
	addOptionPanel.add(addIcon, BorderLayout.WEST);
	addOptionPanel.add(new JLabel("Add new variable"));

	subMainPanel.add(addOptionPanel);

	JScrollPane scrollPane = new JScrollPane(subMainPanel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	mainPanel.add(scrollPane);

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
		if(verifyIfNamesAreUnique()==true && validateAllData()==true) {
		    userInterface.goToNextPage();
		    userInterface.setKnownSolutionsList(getKnownSolutionsFromDecisionVariables());
		}
	    }
	});
	buttonsPanel.add(nextButton);
    }

    @Override
    public void onTop() {
	userInterface.getFrame().setTitle("Problem Solving App");
    }

    public void removeDecisionVariableFromList(DecisionVariablesObject dvo) {
	decisionVariableList.remove(dvo);
	subSubMainPanel.remove(dvo.transformIntoAPanel());
	refreshPage();	
    }

    private ArrayList<KnownSolutionsObject> getKnownSolutionsFromDecisionVariables(){
	ArrayList<KnownSolutionsObject> knownSolutions = new ArrayList<KnownSolutionsObject>();
	for(DecisionVariablesObject dvo : decisionVariableList) {
	    if(!dvo.getName().getText().trim().isEmpty()) {
		knownSolutions.add(new KnownSolutionsObject(null, dvo.getName().getText()));
	    }
	}
	return knownSolutions;
    }

    private boolean verifyIfNamesAreUnique() {
	boolean var = true;
	for(DecisionVariablesObject dvo : decisionVariableList) {
	    String tmp = dvo.getName().getText();
	    int count = 0;
	    for(DecisionVariablesObject dvo2 : decisionVariableList) {
		if(tmp.equals(dvo2.getName().getText()) && !dvo2.getName().getText().trim().isEmpty()) {
		    count++;
		    if(count > 1) {
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
				dvo2.getName().setBackground( new Color(219,151,149).brighter());
				mainPanel.add(warningPanel);
				refreshPage();
			    }
			});
			var = false;
		    } else {
			dvo2.getName().setBackground(Color.white);
			mainPanel.remove(warningPanel);
		    }
		}
	    }
	}
	return var;
    }

    private boolean validateAllData(){
	boolean tmp = true;
	for(DecisionVariablesObject dvo : decisionVariableList) {
	    if(dvo.validateData() == false){
		tmp = false;
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			mainPanel.add(warningPanel2);
			refreshPage();
		    }
		});
		break;
	    }
	}
	mainPanel.remove(warningPanel2);
	refreshPage();
	return tmp;
    }

    public void refreshPage() {
	userInterface.getFrame().validate();
	userInterface.getFrame().repaint();
	userInterface.getFrame().pack();
    }

}
