package it.unisa.bugforecast;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

public class Application extends ViewPart {
	protected static final String CLOSED_OPTION = null;
	private Form form;
	private FormToolkit toolkit;
	private Button externalProjectRadio;
	private Button eclipseProjectRadio;
	private Button withinButton;
	private Button crossButton;
	private Button localButton;
	private Text trainingFileText;
	private Label testFileLabel;
	private Text testFileText;
	private Text outputFolderText;
	private Combo classifierComboBox;
	private Button dataNormalizationButton;
	private Button featureSelectionButton;
	private Button dataBalancingButton;
	private Section metricsSection;
	private Button accuracyButton;
	private Button precisionButton;
	private Button recallButton;
	private Button fmeasureButton;
	private Button aucrocButton;
	private Button mccButton;

	/**
	 * The constructor.
	 */
	public Application() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());

		form = toolkit.createForm(parent);
		GridLayout layout = new GridLayout(4, false);
		form.getBody().setLayout(layout);

		createProjectTypeRadioButtons();

		createValidationStrategyButtons();

		createStrategiesSection();

		createPreprocessingSection();

		createMetricsSection();

		createSubmitButton();

	}

	/**
	 * Passing the focus request to the form.
	 */
	public void setFocus() {
		form.setFocus();
	}

	/**
	 * Disposes the toolkit
	 */
	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

	public void createProjectTypeRadioButtons() {
		Label projectTypeLabel = new Label(form.getBody(), SWT.NULL);
		projectTypeLabel.setText("Project-Type");
		
		Composite projectTypeRadioButtons = toolkit.createComposite(form.getBody(), SWT.NONE);
		
		externalProjectRadio = toolkit.createButton(projectTypeRadioButtons, "External-Project", SWT.RADIO);
		eclipseProjectRadio = toolkit.createButton(projectTypeRadioButtons, "Eclipse-Project", SWT.RADIO);
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		projectTypeRadioButtons.setLayout(new GridLayout(3, false));
		projectTypeRadioButtons.setLayoutData(gridData);
		
		externalProjectRadio.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				withinButton.setVisible(true);
				testFileLabel.setVisible(true);
				testFileText.setVisible(true);
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
		eclipseProjectRadio.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				withinButton.setVisible(false);
				testFileLabel.setVisible(false);
				testFileText.setVisible(false);
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
	}

	public void createValidationStrategyButtons() {
		Label validationStrategyLabel = new Label(form.getBody(), SWT.BORDER);
		validationStrategyLabel.setText("Validation Strategy");
		
		Composite validationStrategyButtons = toolkit.createComposite(form.getBody(), SWT.NONE);
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		validationStrategyButtons.setLayout(new GridLayout(3, false));
		validationStrategyButtons.setLayoutData(gridData);
		
		withinButton = toolkit.createButton(validationStrategyButtons, "Within-Project", SWT.RADIO);
		crossButton = toolkit.createButton(validationStrategyButtons, "Cross-Project", SWT.RADIO);
		localButton = toolkit.createButton(validationStrategyButtons, "Local-Project", SWT.RADIO);
	}

	public void createStrategiesSection() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		Label trainingFileLabel = new Label(form.getBody(), SWT.BORDER);
		trainingFileLabel.setText("Training File");
		trainingFileText = new Text(form.getBody(), SWT.BORDER);
		trainingFileText.setEditable(false);
		trainingFileText.setLayoutData(gridData);
		trainingFileText.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				if (!withinButton.getSelection()) {
					DirectoryDialog directoryDialog = new DirectoryDialog(form.getShell());

					directoryDialog.setFilterPath("");
					directoryDialog.setMessage("Please select a directory and click OK.");

					String directory = directoryDialog.open();
					if (directory != null) {
						trainingFileText.setText(directory);
					}
				}

				else {
					FileDialog fileDialog = new FileDialog(form.getShell(), SWT.MULTI);
					fileDialog.setFilterPath("");
					fileDialog.setFilterExtensions(new String[] { "*.csv" });
					fileDialog.setFilterNames(new String[] { "Comma Separated Values" });

					String dataFile = fileDialog.open();

					if (dataFile != null) {
						String[] selectedFiles = fileDialog.getFileNames();
						StringBuffer stringBuffer = new StringBuffer(fileDialog.getFilterPath() + ": \n");
						for (int i = 0; i < selectedFiles.length; i++) {
							stringBuffer.append(selectedFiles[i] + "\n");
						}
						trainingFileText.setText(stringBuffer.toString());
					}
				}

			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		testFileLabel = new Label(form.getBody(), SWT.BORDER);
		testFileLabel.setText("Test File");
		testFileText = new Text(form.getBody(), SWT.BORDER);
		testFileText.setLayoutData(gridData);
		testFileText.setEditable(false);
		testFileText.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				FileDialog fileDialog = new FileDialog(form.getShell(), SWT.MULTI);

				fileDialog.setFilterPath("");

				fileDialog.setFilterExtensions(new String[] { "*.csv" });
				fileDialog.setFilterNames(new String[] { "Comma Separated Values" });

				String testFile = fileDialog.open();

				if (testFile != null) {
					String[] selectedFiles = fileDialog.getFileNames();
					StringBuffer stringBuffer = new StringBuffer(fileDialog.getFilterPath() + ": \n");
					for (int i = 0; i < selectedFiles.length; i++) {
						stringBuffer.append(selectedFiles[i] + "\n");
					}
					testFileText.setText(stringBuffer.toString());
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		Label outputFolderLabel = new Label(form.getBody(), SWT.BORDER);
		outputFolderLabel.setText("Output Folder");
		outputFolderText = new Text(form.getBody(), SWT.BORDER);
		outputFolderText.setLayoutData(gridData);
		outputFolderText.setEditable(false);
		outputFolderText.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				DirectoryDialog directoryDialog = new DirectoryDialog(form.getShell());

				directoryDialog.setFilterPath("");
				directoryDialog.setMessage("Please select a directory and click OK.");

				String outputFile = directoryDialog.open();
				if (outputFile != null) {
					outputFolderText.setText(outputFile);
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		withinButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				testFileLabel.setVisible(false);
				testFileText.setVisible(false);
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		crossButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				testFileLabel.setVisible(true);
				testFileText.setVisible(true);
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		localButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				testFileLabel.setVisible(true);
				testFileText.setVisible(true);
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		String items[] = { "Log", "NB", "RBF", "MLP", "C45", "DTtable", "Voting", "Bagging", "Boosting",
				"Random Forest", "CODEP", "ASCI" };

		Label classifierLabel = new Label(form.getBody(), SWT.BORDER);
		classifierLabel.setText("Classifier");

		classifierComboBox = new Combo(form.getBody(), SWT.CENTER | SWT.READ_ONLY | SWT.DROP_DOWN);
		classifierComboBox.setItems(items);
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.horizontalSpan = 2;
		classifierComboBox.setLayoutData(gridData2);

		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		Button classifierProperties = toolkit.createButton(form.getBody(), "Properties", SWT.ICON_INFORMATION);
		classifierProperties.setLayoutData(gridData3);

		classifierProperties.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				Object selected = classifierComboBox.getText();
				String select = selected.toString();

				JOptionPane.showInputDialog("Insert details", select);
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
	}

	private void createPreprocessingSection() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		GridLayout layout = new GridLayout(3, false);
		Section preprocessingSection = toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		preprocessingSection.setText("Pre-Processing Operations");
		preprocessingSection.setLayoutData(gridData);
		Composite sectionClient = toolkit.createComposite(preprocessingSection);
		sectionClient.setLayout(layout);
		dataNormalizationButton = toolkit.createButton(sectionClient, "Data Normalization", SWT.CHECK);
		featureSelectionButton = toolkit.createButton(sectionClient, "Feature Section", SWT.CHECK);
		dataBalancingButton = toolkit.createButton(sectionClient, "Data Balancing", SWT.CHECK);
		preprocessingSection.setClient(sectionClient);
	}

	private void createMetricsSection() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		GridLayout layout = new GridLayout(6, false);
		metricsSection = toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		metricsSection.setText("Accuracy Metrics");
		metricsSection.setLayoutData(gridData);
		Composite sectionClient = toolkit.createComposite(metricsSection);
		sectionClient.setLayout(layout);
		accuracyButton = toolkit.createButton(sectionClient, "Accuracy", SWT.CHECK);
		precisionButton = toolkit.createButton(sectionClient, "Precision", SWT.CHECK);
		recallButton = toolkit.createButton(sectionClient, "Recall", SWT.CHECK);
		fmeasureButton = toolkit.createButton(sectionClient, "F-Measure", SWT.CHECK);
		aucrocButton = toolkit.createButton(sectionClient, "Auc-Roc", SWT.CHECK);
		mccButton = toolkit.createButton(sectionClient, "MCC", SWT.CHECK);
		metricsSection.setClient(sectionClient);
	}

	private void createSubmitButton() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		Button buildModelButton = toolkit.createButton(form.getBody(), "Build Model", SWT.NULL);
		buildModelButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {

				buildModelButton.addMouseListener(new MouseListener() {
					@Override
					public void mouseDoubleClick(MouseEvent e) {
					}

					@Override
					public void mouseDown(MouseEvent e) {

						boolean metricsMissing = true;
						if (accuracyButton.getSelection() || precisionButton.getSelection()
								|| recallButton.getSelection() || fmeasureButton.getSelection()
								|| aucrocButton.getSelection() || mccButton.getSelection()) {
							metricsMissing = false;
						}

						if (externalProjectRadio.getSelection()) {
							if (withinButton.getSelection()) {
								if (trainingFileText.getText().isEmpty() || testFileText.getText().isEmpty()
										|| outputFolderText.getText().isEmpty() || metricsMissing
										|| classifierComboBox.getText().isEmpty()) {
									JOptionPane.showMessageDialog(null, "Some input data are missing.", "Attention",
											JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons//cartella.png"));
								} else {
									// Build model
								}
							} else {
								if (trainingFileText.getText().isEmpty() || outputFolderText.getText().isEmpty()
										|| metricsMissing || classifierComboBox.getText().isEmpty()) {
									JOptionPane.showMessageDialog(null, "Some input data are missing.", "Attention",
											JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons//cartella.png"));
								} else {
									// Build model
								}
							}
						} else if (eclipseProjectRadio.getSelection()) {
							if (trainingFileText.getText().isEmpty() || outputFolderText.getText().isEmpty()
									|| classifierComboBox.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Some input data are missing.", "Attention",
										JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons//cartella.png"));
							} else {
								// Build model
							}
						}

					}

					@Override
					public void mouseUp(MouseEvent e) {
					}
				});

			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		buildModelButton.setLayoutData(gridData);
	}
}