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
	 public String prova;
	 
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
		  
		  Label label6 = new Label(form.getBody(), SWT.NULL);
		  label6.setText("Validation Strategy");
		  
		  
		  within = toolkit.createButton(form.getBody(), "Within-Project", SWT.RADIO);
		  cross = toolkit.createButton(form.getBody(), "Cross-Project", SWT.RADIO);
		  local = toolkit.createButton(form.getBody(), "Local-Project", SWT.RADIO);
		  within.setVisible(true);
		  cross.setVisible(true);
		 
				     
		  
		  Label label = new Label(form.getBody(), SWT.NULL);
		  label.setText("Input csv file/Training csv folder");
		  Text text = new Text(form.getBody(), SWT.BORDER);
		  text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  Label labelsupport1= new Label(form.getBody(), SWT.NULL);
		  labelsupport1.setText(" ");
		  Label labelsupport4= new Label(form.getBody(), SWT.NULL);
		  labelsupport4.setText(" ");
		  Label label2 = new Label(form.getBody(), SWT.NULL);
		  label2.setText("Test csv file");
		  Text text2 = new Text(form.getBody(), SWT.BORDER);
		  text2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  Label labelsupport2= new Label(form.getBody(), SWT.NULL);
		  labelsupport2.setText(" ");
		  Label labelsupport5= new Label(form.getBody(), SWT.NULL);
		  labelsupport5.setText(" ");
		  Label label1= new Label(form.getBody(), SWT.NULL);
		  label1.setText("Output folder");
		  Text text1= new Text(form.getBody(), SWT.BORDER);
		  text1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  
		  
		  
		  

		 
		  
		  
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
							
							
						}
						@Override
						public void mouseUp(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}		
					});
				 
		
		  
		  
		  String items[] = { "Log", "NB", "RBF", "MLP",
	      "C45", "DTtable", "Voting", "Bagging", "Boosting", "Random Forest", "CODEP", "ASCI"};
		  
		  Label labelsupport6 = new Label(form.getBody(), SWT.NULL);
		  labelsupport6.setText("");
		  
		  Label labelsupport3 = new Label(form.getBody(), SWT.NULL);
		  labelsupport3.setText("");
		  Label label3 = new Label(form.getBody(), SWT.NULL);
		  label3.setText("Classifiers");
		 
		  comboBox = new Combo(form.getBody(), SWT.CENTER| SWT.READ_ONLY
		            | SWT.DROP_DOWN);
		  comboBox.setItems(items);
		   
		  propeties = toolkit.createButton(form.getBody(), "Properties", SWT.ICON_INFORMATION);
		  
		  
		  propeties.addMouseListener(new MouseListener() {@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
			
					
				}
				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					
					 JOptionPane.showMessageDialog(null,"example","Details",JOptionPane.INFORMATION_MESSAGE);
				     
					 prova = (String) JOptionPane.showInputDialog("Insert details", "Example");
					 JOptionPane.showConfirmDialog(null, "Conferma");
					 JOptionPane.showMessageDialog(null, prova, "Details", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Fist/icons/eclipse128.png"));
					 
					 Label labelsupport7 = new Label(form.getBody(), SWT.NULL);
					 labelsupport7.setText(prova);
					 
	
				}
				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}		
			});
		  
		  Label labelsupport13 = new Label(form.getBody(), SWT.NULL);
		  labelsupport13.setText("");
		  
		  Section section = toolkit.createSection(form.getBody(), 
				   Section.DESCRIPTION|Section.TITLE_BAR|
				   Section.TWISTIE|Section.EXPANDED);
		   section.setText("Pre-processing operations");
		   Composite sectionClient = toolkit.createComposite(section);
		   sectionClient.setLayout(new GridLayout());
		   dN = toolkit.createButton(sectionClient, "Data Normalization", SWT.CHECK);
		   fS = toolkit.createButton(sectionClient, "Feature Section", SWT.CHECK);
		   dB = toolkit.createButton(sectionClient, "Data Balancing", SWT.CHECK);
		   lC = toolkit.createButton(sectionClient, "Local-Project", SWT.CHECK);
		   section.setClient(sectionClient);
		   
		   Label labelsupport10 = new Label(form.getBody(), SWT.NULL);
			  labelsupport10.setText("");
			  
			Label labelsupport8 = new Label(form.getBody(), SWT.NULL);
			  labelsupport8.setText("");
			  
			Label labelsupport9 = new Label(form.getBody(), SWT.NULL);
			  labelsupport9.setText("");
		  
		   Section section1 = toolkit.createSection(form.getBody(), 
				   Section.DESCRIPTION|Section.TITLE_BAR|
				   Section.TWISTIE|Section.EXPANDED);
		   section1.setText("Accuracy metrics");
		   Composite sectionClient1 = toolkit.createComposite(section1);
		   sectionClient1.setLayout(new GridLayout());
		   Ac = toolkit.createButton(sectionClient1, "Accuracy", SWT.CHECK);
		   Pr = toolkit.createButton(sectionClient1, "Precision", SWT.CHECK);
		   Re = toolkit.createButton(sectionClient1, "Recall", SWT.CHECK);
		   Fm = toolkit.createButton(sectionClient1, "F-measure", SWT.CHECK);
		   AR = toolkit.createButton(sectionClient1, "Auc-Roc", SWT.CHECK);
		   MCC = toolkit.createButton(sectionClient1, "MCC", SWT.CHECK);
		   section1.setClient(sectionClient1);
		   
		   Label labelsupport11 = new Label(form.getBody(), SWT.NULL);
			  labelsupport11.setText("");
			  Label labelsupport12 = new Label(form.getBody(), SWT.NULL);
			  labelsupport12.setText("");
			  Label labelsupport15 = new Label(form.getBody(), SWT.NULL);
			  labelsupport15.setText("");
			  Label labelsupport14 = new Label(form.getBody(), SWT.NULL);
			  labelsupport14.setText("");
			  
		   bM = toolkit.createButton(form.getBody(), "Build Model", SWT.COLOR_BLUE);
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