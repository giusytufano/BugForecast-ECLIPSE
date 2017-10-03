package it.unisa.bugforecast;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class Application extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	protected static final String CLOSED_OPTION = null;
	public Form form;
	public FormToolkit toolkit;
	public FormToolkit toolkit2;
	public Button externalProjectRadio;
	public Button eclipseProjectRadio;
	public Button withinButton;
	public Button crossButton;
	public Button localButton;
	public Text trainingFileText;
	public Label testFileLabel;
	public Text testFileText;
	public Text outputFolderText;
	public Combo classifierComboBox;
	public Button dataNormalizationButton;
	public Button featureSelectionButton;
	public Button dataBalancingButton;
	public Section metricsSection;
	public Button accuracyButton;
	public Button precisionButton;
	public Button recallButton;
	public Button fmeasureButton;
	public Button aucrocButton;
	public Button mccButton;
	public String directory;
	public String classifierName;
	public String classifierDetails;
	public Button buildModelButton;
	public Form form1;
	public Composite composite;
	public TabFolder tabFolder;
	public TabItem tab2;
	public String valoreacc;
	public String valorepr;
	public String valorere;
	public String valorefm;
	public String valoremcc;
	public String valoreau;
	public ArrayList<String> control;
	public Table table;
	public TableItem item;
	public TableItem item2;
	public boolean numeroTab;
	
	
	
	
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
		form1 = toolkit.createForm(parent);
		form1.setVisible(false);
		GridLayout layout = new GridLayout(4, false);
		form.getBody().setLayout(layout);
		form1.getBody().setLayout(layout);	
		numeroTab=false;
		
		createTabFolder();
		

	}

	public void createTabFolder() {
		GridLayout layout = new GridLayout(4, false);
		tabFolder = new TabFolder(form.getBody(), SWT.NULL);
		TabItem tab1 = new TabItem(tabFolder, SWT.NONE);
		tab1.setText("BugForecast");

		composite = new Composite(tabFolder, SWT.NULL);
		composite.setLayout(layout);

		createProjectTypeRadioButtons();

		createValidationStrategyButtons();

		createStrategiesSection();

		createPreprocessingSection();

		createMetricsSection();

		createSubmitButton();

		tab1.setControl(composite);

	}

	public void createProjectTypeRadioButtons() {
		Label projectTypeLabel = new Label(composite, SWT.NULL);
		projectTypeLabel.setText("Project-Type");

		Composite projectTypeRadioButtons = toolkit.createComposite(composite, SWT.NONE);

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
		Label validationStrategyLabel = new Label(composite, SWT.BORDER);
		validationStrategyLabel.setText("Validation Strategy");

		Composite validationStrategyButtons = toolkit.createComposite(composite, SWT.NONE);

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
		Label trainingFileLabel = new Label(composite, SWT.BORDER);
		trainingFileLabel.setText("Training File");
		trainingFileText = new Text(composite, SWT.BORDER);
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

					directory = directoryDialog.open();
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

		testFileLabel = new Label(composite, SWT.BORDER);
		testFileLabel.setText("Test File");
		testFileText = new Text(composite, SWT.BORDER);
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
					StringBuffer stringBufferFile = new StringBuffer(fileDialog.getFilterPath() + ": \n");
					for (int i = 0; i < selectedFiles.length; i++) {
						stringBufferFile.append(selectedFiles[i] + "\n");
					}
					testFileText.setText(stringBufferFile.toString());
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		Label outputFolderLabel = new Label(composite, SWT.BORDER);
		outputFolderLabel.setText("Output Folder");
		outputFolderText = new Text(composite, SWT.BORDER);
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

		String items[] = { "Log", "NB", "RBF", "MLP", "C45", "Decision Table", "Voting", "Bagging", "Boosting",
				"Random Forest", "CODEP", "ASCI" };

		Label classifierLabel = new Label(composite, SWT.BORDER);
		classifierLabel.setText("Classifier");

		classifierComboBox = new Combo(composite, SWT.CENTER | SWT.READ_ONLY | SWT.DROP_DOWN);
		classifierComboBox.setItems(items);
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.horizontalSpan = 2;
		classifierComboBox.setLayoutData(gridData2);

		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		Button classifierProperties = toolkit.createButton(composite, "Properties", SWT.ICON_INFORMATION);
		classifierProperties.setLayoutData(gridData3);

		classifierProperties.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				classifierName = classifierComboBox.getText().toString();
				classifierDetails = JOptionPane.showInputDialog("Insert details");
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

	}

	public void createPreprocessingSection() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		GridLayout layout = new GridLayout(3, false);
		Section preprocessingSection = toolkit.createSection(composite, Section.DESCRIPTION | Section.TITLE_BAR);
		preprocessingSection.setText("Pre-Processing Operations");
		preprocessingSection.setLayoutData(gridData);
		Composite sectionClient = toolkit.createComposite(preprocessingSection);
		sectionClient.setLayout(layout);
		dataNormalizationButton = toolkit.createButton(sectionClient, "Data Normalization", SWT.CHECK);
		featureSelectionButton = toolkit.createButton(sectionClient, "Feature Section", SWT.CHECK);
		dataBalancingButton = toolkit.createButton(sectionClient, "Data Balancing", SWT.CHECK);
		preprocessingSection.setClient(sectionClient);
	}

	public void createMetricsSection() {
		
		control = new ArrayList<String>();
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		GridLayout layout = new GridLayout(6, false);
		metricsSection = toolkit.createSection(composite, Section.DESCRIPTION | Section.TITLE_BAR);
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
		
		accuracyButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
			
				if(control.indexOf("Accuracy")==-1) 
				{
					control.add("Accuracy");
				}
				else control.remove("Accuracy");
			}
		});
		
		precisionButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
			
				if(control.indexOf("Precision")==-1) 
				{
					control.add("Precision");
				}
				else control.remove("Precision");
			}
		});
		
		recallButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
			
				if(control.indexOf("Recall")==-1) 
				{
					control.add("Recall");
				}
				else control.remove("Recall");
			}
		});
		
		fmeasureButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
			
				if(control.indexOf("FMeasure")==-1) 
				{
					control.add("FMeasure");
				}
				else control.remove("FMeasure");
			}
		});
		
		aucrocButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
			
				if(control.indexOf("Auc-Roc")==-1) 
				{
					control.add("Auc-Roc");
				}
				else control.remove("Auc-Roc");
			}
		});
		
		mccButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
			
				if(control.indexOf("MCC")==-1) 
				{
					control.add("MCC");
				}
				else control.remove("MCC");
			}
		});

	}

	
	public void createSubmitButton() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		buildModelButton = toolkit.createButton(composite, "Build Model", SWT.NULL);

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

						boolean errorFlag = false;

						if (!externalProjectRadio.getSelection() && !eclipseProjectRadio.getSelection()) {
							errorFlag = true;
						}

						else if (externalProjectRadio.getSelection() || eclipseProjectRadio.getSelection()) {

							if (!withinButton.getSelection() && !crossButton.getSelection()
									&& !localButton.getSelection()) {
								errorFlag = true;
							}

							else if (withinButton.getSelection()) {
								if (trainingFileText.getText().isEmpty() || outputFolderText.getText().isEmpty()
										|| metricsMissing || classifierComboBox.getText().isEmpty()) {

									errorFlag = true;
								}
							}

							else if (crossButton.getSelection() || localButton.getSelection()) {
								if (trainingFileText.getText().isEmpty() || outputFolderText.getText().isEmpty()
										|| testFileText.getText().isEmpty() || metricsMissing
										|| classifierComboBox.getText().isEmpty()) {

									errorFlag = true;

								}
							}
						}

						if (errorFlag == true) {
							JOptionPane.showMessageDialog(null, "Some input data are missing.", "Attention",
									JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons//cartella.png"));
						} else {
							try {

								new Task(Application.this);
								
								GridLayout layout = new GridLayout(4, false);
								
								if(numeroTab==false)
								{
								tab2 = new TabItem(tabFolder, SWT.CLOSE);
								tab2.setText("BugForecast-Result");

								composite = new Composite(tabFolder, SWT.NULL);

								composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));

								composite.setLayout(layout);
								
								createResultForm();
								
								composite.pack();
								

								tab2.setControl(composite);
								numeroTab=true;
								}
								
								else {
									
									tab2.setText("BugForecast-Result");

									composite = new Composite(tabFolder, SWT.NULL);

									composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));

									composite.setLayout(layout);
									
									createResultForm();
									
									composite.pack();
									

									tab2.setControl(composite);
								}
								
								

							} catch (Exception e1) {
								e1.printStackTrace();

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

	public void createResultForm() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;

		Label Result = new Label(composite, SWT.BORDER);
		Result.setText("BUGFORECAST-RESULT");
		Result.setLayoutData(gridData);
		
		CreateTableMetrics();

		CreateTableClassifier();
		

	

	}

	public void CreateTableMetrics() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		GridLayout layout = new GridLayout(4, false);
		Section accurancyMetrics = toolkit.createSection(composite, Section.DESCRIPTION | Section.TITLE_BAR);
		accurancyMetrics.setText("Accurancy Metrics");
		accurancyMetrics.setLayoutData(gridData);
		Composite sectionMetrics = toolkit.createComposite(accurancyMetrics);
		sectionMetrics.setLayout(layout);

		Table table = toolkit.createTable(sectionMetrics, SWT.BORDER_SOLID);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(gridData);
		String[] titles = { "                ", "             " };
		for (int i = 0; i < (titles.length); i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}

		if (accuracyButton.getSelection()) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "Accuracy");
			item.setText(1, "0.01");
		}

		if (precisionButton.getSelection()) {
			TableItem item1 = new TableItem(table, SWT.NONE);
			item1.setText(0, "Precision");
			item1.setText(1, "0.05");
		}

		if (recallButton.getSelection()) {
			TableItem item2 = new TableItem(table, SWT.NONE);
			item2.setText(0, "Recall");
			item2.setText(1, "0.10");
		}

		if (fmeasureButton.getSelection()) {
			TableItem item3 = new TableItem(table, SWT.NONE);
			item3.setText(0, "FMeasure");
			item3.setText(1, "0.15");
		}

		if (aucrocButton.getSelection()) {
			TableItem item4 = new TableItem(table, SWT.NONE);
			item4.setText(0, "Auc-Roc");
			item4.setText(1, "0.50");
		}

		if (mccButton.getSelection()) {
			TableItem item5 = new TableItem(table, SWT.NONE);
			item5.setText(0, "MCC");
			item5.setText(1, "1.01");
		} 

		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}

		accurancyMetrics.setClient(sectionMetrics);
		table.pack();
	}

	public void CreateTableClassifier() {

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		gridData.verticalSpan = 5;
		GridLayout layout = new GridLayout(4, false);
		Section accurancyMetrics = toolkit.createSection(composite, Section.DESCRIPTION | Section.TITLE_BAR);
		accurancyMetrics.setText("Classifier");
		accurancyMetrics.setLayoutData(gridData);
		Composite sectionMetrics = toolkit.createComposite(accurancyMetrics);
		sectionMetrics.setLayout(layout);

		Table table = toolkit.createTable(sectionMetrics, SWT.BORDER_SOLID);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(gridData);
		String[] titles = { "Class", "IsBuggy", "IsReallyBuggy" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}

		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(0, "exemple.csv");
		item.setText(1, "true");
		item.setText(2, "true");

		TableItem item1 = new TableItem(table, SWT.NONE);
		item1.setText(0, "exemple2.csv");
		item1.setText(1, "true");
		item1.setText(2, "false");

		TableItem item2 = new TableItem(table, SWT.NONE);
		item2.setText(0, "exemple23.csv");
		item2.setText(1, "true");
		item2.setText(2, "false");

		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}

		accurancyMetrics.setClient(sectionMetrics);
		table.pack();
		
		sectionMetrics.pack();
	}

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
}
