package first.intro;


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
	private FormToolkit toolkit;
	 private Form form;
	 private Combo comboBox;
	 private Button within;
	 private Button cross;
	 private Button local;
	 private Button dN;
	 private Button fS;
	 private Button dB;
	 private Button lC;
	 private Button Ac;
	 private Button Pr;
	 private Button Re;
	 private Button Fm;
	 private Button AR;
	 private Button MCC;
	 private Button propeties;
	 private Button bM;
	 private Button reset;
	 private Button buttonSelectDir;
	 private Button buttonSelectFile;
	 private Button buttonSelectFileInp;
	 private Button buttonSelectOut;
	 public String prova;
	 public String selectedDir;
	 public String selectedDirOut;
	 public String fileFilterPath;
	 public String dir;
	 public String firstFile;
	 public String firstFile1;
	 public String out;
	 private Label label;
	 private Label label1;
	 private Label label2;
	 private Label label3;
	 private Label label4;
	 private Label labelsupport1;
	 private Label labelsupport2;
	 private Label labelsupport3;
	 private Label labelsupport4;
	 private Label labelsupport5;
	 private Label labelsupport6;
	 private Label labelsupport7;
	 private Label labelsupport8;
	 private Label labelsupport9;
	 private Label labelsupport10;
	 private Label labelsupport11;
	 private Text text;
	 private Text text1;
	 private Text text2;
	 private StringBuffer sb;
	 private StringBuffer sb1;
	 
	 /**
	  * The constructor.
	  */
	 public Application() {
	 }

	 /**
	  * This is a callback that will allow us to create the viewer and
	  * initialize it.
	  */
	 public void createPartControl(Composite parent) {
		  toolkit = new FormToolkit(parent.getDisplay());
		  form = toolkit.createForm(parent);
		  GridLayout layout1 = new GridLayout();
		  form.getBody().setLayout(layout1);
		  layout1.numColumns = 4;
		  layout1.makeColumnsEqualWidth=true;
		  layout1.marginBottom=13;
		  
		  label = new Label(form.getBody(), SWT.BORDER);
		  label.setText("Validation Strategy");
		  
		  
		  
		  within = toolkit.createButton(form.getBody(), "Within-Project", SWT.RADIO);
		  cross = toolkit.createButton(form.getBody(), "Cross-Project", SWT.RADIO);
		  local = toolkit.createButton(form.getBody(), "Local-Project", SWT.RADIO);
		  within.setSelection(true);
		 
		  
		  label1 = new Label(form.getBody(), SWT.BORDER);
		  text = new Text(form.getBody(), SWT.BORDER);
		  text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  buttonSelectDir = toolkit.createButton(form.getBody(), "Input", SWT.PUSH);
		  buttonSelectDir.addMouseListener(new MouseListener() {@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

		 		@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					DirectoryDialog directoryDialog = new DirectoryDialog(form.getShell());
			        
			        directoryDialog.setFilterPath(selectedDir);
			        directoryDialog.setMessage("Please select a directory and click OK");
			        
			        dir = directoryDialog.open();
			        if(dir != null) {
			          text.setText(dir);
			          selectedDir = dir;
				}}

				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}});
		 
		  buttonSelectFileInp = toolkit.createButton(form.getBody(), "File", SWT.PUSH);
		  buttonSelectFileInp.addMouseListener(new MouseListener() {@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					FileDialog fileDialog = new FileDialog(form.getShell(), SWT.MULTI);

			        fileDialog.setFilterPath(fileFilterPath);
			        
			        fileDialog.setFilterExtensions(new String[]{"*.rtf", "*.html", "*.*"});
			        fileDialog.setFilterNames(new String[]{ "Rich Text Format", "HTML Document", "Any"});
			        
			        firstFile1 = fileDialog.open();

			        if(firstFile1 != null) {
			          fileFilterPath = fileDialog.getFilterPath();
			          String[] selectedFiles = fileDialog.getFileNames();
			          sb1 = new StringBuffer(fileDialog.getFilterPath() +  ": \n");
			          for(int i=0; i<selectedFiles.length; i++) {
			            sb1.append(selectedFiles[i] + "\n");
			          }
			          text.setText(sb1.toString());
			        }
			      }
				

				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}});
		  
		  label2 = new Label(form.getBody(), SWT.BORDER);
		  label2.setText("Test CSV File");
		  text2 = new Text(form.getBody(), SWT.BORDER);
		  text2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  
		  buttonSelectFile = toolkit.createButton(form.getBody(), "File", SWT.PUSH);
		  buttonSelectFile.addMouseListener(new MouseListener() {@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					FileDialog fileDialog = new FileDialog(form.getShell(), SWT.MULTI);

			        fileDialog.setFilterPath(fileFilterPath);
			        
			        fileDialog.setFilterExtensions(new String[]{"*.rtf", "*.html", "*.*"});
			        fileDialog.setFilterNames(new String[]{ "Rich Text Format", "HTML Document", "Any"});
			        
			        firstFile = fileDialog.open();

			        if(firstFile != null) {
			          fileFilterPath = fileDialog.getFilterPath();
			          String[] selectedFiles = fileDialog.getFileNames();
			          sb = new StringBuffer("Selected files under dir " + fileDialog.getFilterPath() +  ": \n");
			          for(int i=0; i<selectedFiles.length; i++) {
			            sb.append(selectedFiles[i] + "\n");
			          }
			          text2.setText(sb.toString());
			        }
			      }
				

				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}});
		  
		  labelsupport2= new Label(form.getBody(), SWT.NULL);
		  labelsupport2.setText(" ");
		  label3= new Label(form.getBody(), SWT.BORDER);
		  label3.setText("Output Folder");
		  text1= new Text(form.getBody(), SWT.BORDER);
		  text1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  
		  buttonSelectOut = toolkit.createButton(form.getBody(), "Output", SWT.PUSH);
		  buttonSelectOut.addMouseListener(new MouseListener() {@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					DirectoryDialog directoryDialog = new DirectoryDialog(form.getShell());
			        
			        directoryDialog.setFilterPath(selectedDirOut);
			        directoryDialog.setMessage("Please select a directory and click OK");
			        
			        out = directoryDialog.open();
			        if(out != null) {
			          text1.setText(out);
			          selectedDir = out;
				}}

				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}});
		  
		  
		   within.addMouseListener(new MouseListener() {@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					 lC.setVisible(false);
					 dN.setVisible(true);
					 fS.setVisible(true);
					 dB.setVisible(true);
					 Ac.setVisible(true);
					 Pr.setVisible(true);
					 Re.setVisible(true);
					 Fm.setVisible(true);
					 AR.setVisible(true);
					 MCC.setVisible(true);
					 label2.setVisible(false);
					 text2.setVisible(false);
					 label1.setText("Input CSV File");
					 buttonSelectFile.setVisible(false);
					 buttonSelectFileInp.setVisible(true);
					 buttonSelectDir.setVisible(false);
				}

				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}});
		  
			cross.addMouseListener(new MouseListener() {@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					@Override
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						lC.setVisible(true);
						dN.setVisible(true);
						fS.setVisible(true);
			 			dB.setVisible(true);
						label2.setVisible(true);
						text2.setVisible(true);
						label1.setText("Training CSV Folder");
						buttonSelectFile.setVisible(true);
						buttonSelectFileInp.setVisible(false);
						buttonSelectDir.setVisible(true);
					}
					
					@Override
					public void mouseUp(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}		
				});
				  
			local.addMouseListener(new MouseListener() {@Override
					public void mouseDoubleClick(MouseEvent e) {
							// TODO Auto-generated method stub
							
					}
						@Override
					public void mouseDown(MouseEvent e) {
							// TODO Auto-generated method stub
							lC.setVisible(true);
							dN.setVisible(true);
							fS.setVisible(true);
							dB.setVisible(true);
							label2.setVisible(true);
							text2.setVisible(true);
							label.setText("Training CSV Folder");
							buttonSelectFile.setVisible(true);
							buttonSelectFileInp.setVisible(false);
							buttonSelectDir.setVisible(true);
					}
						
					@Override
					public void mouseUp(MouseEvent e) {
							// TODO Auto-generated method stub
							
					}		
				});
				 
		
		  
		  
			String items[] = { "Log", "NB", "RBF", "MLP",
					"C45", "DTtable", "Voting", "Bagging", "Boosting", "Random Forest", "CODEP", "ASCI"};
		  
		 
		  
			labelsupport3 = new Label(form.getBody(), SWT.NULL);
			labelsupport3.setText("");
			label4 = new Label(form.getBody(), SWT.BORDER);
			label4.setText("Classifiers");
		 
			comboBox = new Combo(form.getBody(), SWT.CENTER| SWT.READ_ONLY
		            | SWT.DROP_DOWN);
			comboBox.setItems(items);
		  
			propeties = toolkit.createButton(form.getBody(), "Properties", SWT.ICON_INFORMATION);
		  
			labelsupport4 = new Label(form.getBody(), SWT.NULL);
			propeties.addMouseListener(new MouseListener() {@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
			
					
				}
				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					
					 JOptionPane.showMessageDialog(null,prova,"Details",JOptionPane.INFORMATION_MESSAGE);
				     
					 prova = (String) JOptionPane.showInputDialog("Insert details", "Example");
					 JOptionPane.showConfirmDialog(null, "Conferma");
					 JOptionPane.showMessageDialog(null, prova, "Details", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Fist/icons/eclipse128.png"));
					 
					 
					 labelsupport4.setText(prova);
					 
	
				}
				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}		
			});
		
		  
			Section section = toolkit.createSection(form.getBody(), 
				   Section.DESCRIPTION|Section.TITLE_BAR|
				   Section.TWISTIE|Section.EXPANDED);
			section.setText("Pre-Processing Operations");
			Composite sectionClient = toolkit.createComposite(section);
			sectionClient.setLayout(new GridLayout());
			dN = toolkit.createButton(sectionClient, "Data Normalization", SWT.CHECK);
			fS = toolkit.createButton(sectionClient, "Feature Section", SWT.CHECK);
			dB = toolkit.createButton(sectionClient, "Data Balancing", SWT.CHECK);
			lC = toolkit.createButton(sectionClient, "Local-Project", SWT.CHECK);
			section.setClient(sectionClient);
		   
			labelsupport5 = new Label(form.getBody(), SWT.NULL);
			labelsupport5.setText("");
			  
			labelsupport6 = new Label(form.getBody(), SWT.NULL);
			labelsupport6.setText("");
			  
			labelsupport7 = new Label(form.getBody(), SWT.NULL);
			labelsupport7.setText("");
		  
			Section section1 = toolkit.createSection(form.getBody(), 
				   Section.DESCRIPTION|Section.TITLE_BAR|
				   Section.TWISTIE|Section.EXPANDED);
			section1.setText("Accuracy Metrics");
			Composite sectionClient1 = toolkit.createComposite(section1);
			sectionClient1.setLayout(new GridLayout());
			Ac = toolkit.createButton(sectionClient1, "Accuracy", SWT.CHECK);
			Pr = toolkit.createButton(sectionClient1, "Precision", SWT.CHECK);
			Re = toolkit.createButton(sectionClient1, "Recall", SWT.CHECK);
			Fm = toolkit.createButton(sectionClient1, "F-Measure", SWT.CHECK);
			AR = toolkit.createButton(sectionClient1, "Auc-Roc", SWT.CHECK);
			MCC = toolkit.createButton(sectionClient1, "MCC", SWT.CHECK);
			section1.setClient(sectionClient1);
		   
			labelsupport8 = new Label(form.getBody(), SWT.NULL);
			labelsupport8.setText("");
		   
			labelsupport9 = new Label(form.getBody(), SWT.NULL);
			labelsupport9.setText("");
		   
			labelsupport10 = new Label(form.getBody(), SWT.NULL);
			labelsupport10.setText("");
		   
			labelsupport11 = new Label(form.getBody(), SWT.NULL);
			labelsupport11.setText("");
			  
			bM = toolkit.createButton(form.getBody(), "Build Model", SWT.CENTER| SWT.PUSH);
		   
			reset = toolkit.createButton(form.getBody(), "Reset", SWT.CLOSE);
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
	}