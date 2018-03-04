package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class FitnessFunctionObject {

	private FitnessFunctionPage page;
	private JLabel addIcon;
	private JButton uploadButton;
	private String[] optimizationCriterias = {"No optimization criterias available"};
	private JComboBox<String> optimizationCriteria;
	private ArrayList<JComboBox<String>> comboBoxList;

	public FitnessFunctionObject(FitnessFunctionPage page){

		this.page = page;
		this.comboBoxList = new ArrayList<JComboBox<String>>();
		uploadButton = FrameUtils.cuteButton("Upload JAR file");
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Upload fitness function");
				if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
					uploadButton.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		this.optimizationCriteria = FrameUtils.cuteComboBox(optimizationCriterias);
		refreshOptimizationCriteriasOfGivenComponent(optimizationCriteria);
		comboBoxList.add(optimizationCriteria);
	}

	public JPanel transformIntoAPanel() {
		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		overallPanel.setBackground(Color.WHITE);

		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		fieldsPanel.setBackground(Color.WHITE);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		uploadButton.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		uploadButton.setPreferredSize(new Dimension(135,22));
		fieldsPanel.add(uploadButton);
		fieldsPanel.add(optimizationCriteria);
		overallPanel.add(fieldsPanel);

		JPanel addPanel = new JPanel();
		addPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		addPanel.setBackground(Color.WHITE);

		this.addIcon = new JLabel();
		this.addIcon.setIcon(new ImageIcon("./src/frames/images/add_icon.png"));
		this.addIcon.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						JComboBox<String> clone = FrameUtils.cuteComboBox(optimizationCriterias);
						fieldsPanel.add(clone);
						comboBoxList.add(clone);
						refreshOptimizationCriteriasOfGivenComponent(clone);
						page.refreshPage();
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

		addPanel.add(addIcon);
		addPanel.add(new JLabel("Add another optimization criteria"));
		overallPanel.add(addPanel);

		return overallPanel;
	}
	
	public void refreshAll() {
		for(JComboBox<String> comboBox : comboBoxList) {
			refreshOptimizationCriteriasOfGivenComponent(comboBox);
		}
	}
	
	private void refreshOptimizationCriteriasOfGivenComponent(JComboBox<String> component) {
		if(page.userInterface.getOptimizationCriteriaFromPage().size()>0) {
			int i = 0;
			component.removeAllItems();
			optimizationCriterias = new String[page.userInterface.getOptimizationCriteriaFromPage().size()];
			for(OptimizationCriteriaObject oco : page.userInterface.getOptimizationCriteriaFromPage()) {
				this.optimizationCriterias[i] = oco.getName().getText();
				component.addItem(optimizationCriterias[i]);
			}
		} else {
			component.removeAllItems();
			optimizationCriterias = new String[1];
			component.addItem("No optimization criterias available");
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				page.validate();
				page.repaint();
			}
		});
	}

}