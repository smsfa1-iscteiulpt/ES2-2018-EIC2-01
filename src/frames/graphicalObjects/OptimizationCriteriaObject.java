package frames.graphicalObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frames.OptimizationCriteriaPage;
import frames.frameUtils.FrameUtils;
import objects.DataType;

/**
 * This object was created to aid the construction of the Optimization Criteria
 * Page and later to convert to the object Decision Variable
 */

public class OptimizationCriteriaObject {

    private OptimizationCriteriaPage pageAssociated;
    private JTextField name;
    private String[] dataTypes = { "Integer", "Double", "Binary" };
    private JComboBox<String> dataType;
    private JLabel deleteIcon;
    private JPanel variablesPanel;

    /**
     * 
     * @param ocp
     */
    public OptimizationCriteriaObject(OptimizationCriteriaPage ocp) {
	pageAssociated = ocp;
	variablesPanel = new JPanel();
	name = new JTextField(10);
	dataType = FrameUtils.cuteComboBox(dataTypes);
	dataType.setSelectedItem(null);
	deleteIcon = new JLabel();
	deleteIcon.setIcon(new ImageIcon(getClass().getResource("../images/delete_icon2.png")));
    }

    /**
     * Creates an optimization criteria object with the data read from a XML file
     * 
     * @param ocp
     * @param name
     * @param variableDataType
     */
    public OptimizationCriteriaObject(OptimizationCriteriaPage ocp, String name, String variableDataType) {
	pageAssociated = ocp;
	variablesPanel = new JPanel();
	this.name = new JTextField(name, name.length());
	this.dataType = FrameUtils.cuteComboBox(dataTypes);
	if (variableDataType.equals("INTEGER")) {
	    dataType.setSelectedIndex(0);
	} else if (variableDataType.equals("DOUBLE")) {
	    dataType.setSelectedIndex(1);
	} else {
	    dataType.setSelectedIndex(2);
	}
	deleteIcon = new JLabel();
	deleteIcon.setIcon(new ImageIcon(getClass().getResource("../images/delete_icon2.png")));
    }

    /**
     * Transforms the object in a JPanel that will be added to the frame later.
     * 
     * @return JPanel
     */
    public JPanel transformIntoAPanel() {
	variablesPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	variablesPanel.setBackground(Color.WHITE);
	name.setBorder(FrameUtils.cuteBorder());
	name.setPreferredSize(new Dimension(10, 22));
	variablesPanel.add(name);
	FrameUtils.addEmptyLabels(variablesPanel, 3);

	deleteIcon.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
			pageAssociated.removeOptimizationCriteriaFromList(OptimizationCriteriaObject.this);
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

	variablesPanel.add(deleteIcon);
	return variablesPanel;
    }

    /**
     * @return {@code true} if {@linkplain OptimizationCriteriaObject} have a valid
     *         name and the data type is selected, otherwise {@code false}
     * 
     * @see #isValidName()
     * @see #isDataTypeSelected()
     */
    public boolean isWellFilled() {
	// methods separated to run all
	boolean isValidName = isValidName(), isDataTypeSelected = isDataTypeSelected();
	return isValidName && isDataTypeSelected;
    }

    /**
     * @return {@code true} if the name is not empty and is not repeated, otherwise
     *         {@code false} and evidence the error
     * 
     * @see OptimizationCriteriaPage#isNameRepeated(String)
     */
    private boolean isValidName() {
	String text = name.getText();
	String info = (text.equals("")
		? "The optimization criteria's name field is a mandatory entry field and therefore must be filled in."
		: "") + (pageAssociated.isNameRepeated(text) ? "The optimization criteria's name must be unique." : "");
	return !info.equals("") ? FrameUtils.errorFormat(name, info) : FrameUtils.normalFormat(name);
    }

    /**
     * @return {@code true} if the {@link #dataType} has an item selected, otherwise
     *         {@code false} and evidence the error
     * @see FrameUtils#errorFormat(JComponent, String)
     */
    private boolean isDataTypeSelected() {
	return dataType.getSelectedItem() == null ? FrameUtils.errorFormat(dataType,
		"The optimization criteria's data type field is a mandatory entry field and therefore must be filled in.")
		: FrameUtils.normalFormat(dataType);

    }

    public String getVariableName() {
	return name.getText();
    }

    public String getDataType() {
	return dataType.getSelectedItem().toString();
    }

    /**
     * Transforms the dataType selected to an enumerate
     * 
     * @return
     */
    public DataType getDataTypeToProblem() {
	if (dataType.getSelectedItem().toString().equals("Integer")) {
	    return DataType.INTEGER;
	} else if (dataType.getSelectedItem().toString().equals("Double")) {
	    return DataType.DOUBLE;
	} else {
	    return DataType.BINARY;
	}
    }

    public void setDataType(String str) {
	if (str != null) {
	    if (str.equals("Integer")) {
		dataType.setSelectedIndex(0);
	    } else if (str.equals("Double")) {
		dataType.setSelectedIndex(1);
	    } else {
		dataType.setSelectedIndex(2);
	    }
	}
    }

    public OptimizationCriteriaPage getPageAssociated() {
	return pageAssociated;
    }

    public void setPageAssociated(OptimizationCriteriaPage pageAssociated) {
	this.pageAssociated = pageAssociated;
    }

}
