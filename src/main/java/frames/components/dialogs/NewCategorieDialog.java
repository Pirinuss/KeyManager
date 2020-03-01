package frames.components.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frames.MainFrame;
import models.CategorieOption;
import util.IconHandler;
import util.LoggerUtil;

public class NewCategorieDialog extends Dialog {

	private static final long serialVersionUID = 4246979674829196864L;
	private static Logger logger = LoggerUtil.getLogger();

	private final static String TITLE = "Neue Kategorie erstellen";
	private final static int DIALOGHIGH = 180;
	private final static int DIALOGWIDTH = 600;

	private JTextField newCatName;
	private JLabel validationLabel;
	private JComboBox<?> comboBox;

	private CategorieOption categorieOption;

	public NewCategorieDialog() {
		super(TITLE, new Dimension(DIALOGWIDTH, DIALOGHIGH));
	}

	@Override
	protected JPanel createDialogPanel() {

		JPanel dialogPanel = new JPanel();
		dialogPanel.setPreferredSize(new Dimension(DIALOGWIDTH, DIALOGHIGH));
		dialogPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		JPanel contentPanel = new JPanel();
		contentPanel.setPreferredSize(
				new Dimension((DIALOGWIDTH / 3) * 2, (DIALOGHIGH / 6) * 5));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		// Create namePanel
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Name der Kategorie:"));
		newCatName = new JTextField(20);
		namePanel.add(newCatName);
		contentPanel.add(namePanel);

		// Create boxPanel
		JPanel boxPanel = new JPanel();
		boxPanel.add(new JLabel("Bereich:"));
		comboBox = new JComboBox(CategorieOption.getAllCatNames());
		comboBox.setSelectedIndex(0);
		boxPanel.add(comboBox);
		contentPanel.add(boxPanel);

		// Create validationPanel
		JPanel validationPanel = new JPanel();
		validationLabel = new JLabel(" ");
		validationLabel.setForeground(Color.RED);
		validationPanel.add(validationLabel);
		contentPanel.add(validationPanel);

		// Create buttonPanel
		JPanel buttonPanel = new JPanel();
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new createCatButtonListener());
		JButton okayButton = new JButton("Erstellen");
		okayButton.addActionListener(new createCatButtonListener());
		this.getRootPane().setDefaultButton(okayButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(okayButton);
		contentPanel.add(buttonPanel);

		dialogPanel.add(IconHandler.getIconPanel("AddIcon.png", 200, 150));
		dialogPanel.add(contentPanel);

		return dialogPanel;
	}

	public JTextField getNewCatName() {
		return newCatName;
	}

	public CategorieOption getCategorieOption() {
		return categorieOption;
	}

	private class createCatButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JButton pressedButton = (JButton) e.getSource();

			if (pressedButton.getText().equals("Erstellen")) {
				if (newCatName.getText().equals("")) {
					validationLabel.setText(
							"Bitte einen Namen für die Kategorie angeben");
					validationLabel.setVisible(true);
					return;
				} else {
					ArrayList<String> existingCats = MainFrame.getCatTree()
							.getCatNames();
					for (String catName : existingCats) {
						if (catName.equals(newCatName.getText())) {
							logger.warning(
									"Fehler beim Erstellen einer Kategorie: Kategoriename \""
											+ newCatName.getText()
											+ "\" ist bereits vergeben");
							validationLabel.setText(
									"Eine Kategorie mit diesem Namen existiert bereits");
							validationLabel.setVisible(true);
							return;
						}
					}

				}

				String catOptionName = (String) comboBox.getSelectedItem();
				categorieOption = CategorieOption.fromString(catOptionName);
				setAccepted(true);
			}
			closeDialog();
		}
	}

}
