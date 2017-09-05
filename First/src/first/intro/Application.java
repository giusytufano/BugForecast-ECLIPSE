package first.intro;


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
	 private FormToolkit toolkit;
	 private Form form;
	 private Combo comboBox;
	 private Button within;
	 private Button cross;
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
		  form.setText("VALIDATION STRATEGY");
		  GridLayout layout = new GridLayout();
		  form.getBody().setLayout(layout);
		  layout.numColumns = 2;
		  GridData gd = new GridData();
		  gd.horizontalSpan = 2;
		  within = toolkit.createButton(form.getBody(), "WITHIN-PROJECT", SWT.RADIO);
		  cross = toolkit.createButton(form.getBody(), "CROSS-PROJECT", SWT.RADIO);
		  within.setVisible(true);
		  cross.setVisible(true);
		  
		  Label label = new Label(form.getBody(), SWT.NULL);
		  label.setText("Input csv file/Training csv folder");
		  Text text = new Text(form.getBody(), SWT.BORDER);
		  text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  Label label1= new Label(form.getBody(), SWT.NULL);
		  label1.setText("Output folder");
		  Text text1= new Text(form.getBody(), SWT.BORDER);
		  text1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  Label label2 = new Label(form.getBody(), SWT.NULL);
		  label2.setText("Test csv file");
		  Text text2 = new Text(form.getBody(), SWT.BORDER);
		  text2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  
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
				  
				  GridLayout layout1 = new GridLayout();
				  form.getBody().setLayout(layout1);
				  layout1.numColumns = 2;
				  GridData gd1 = new GridData();
				  gd1.horizontalSpan = 7;
				  layout1.verticalSpacing=2;
		
		  
		  
		  String items[] = { "Item One", "Item Two", "Item Three", "Item Four",
	      "Item Five" };
		  
		  
		  Label label3 = new Label(form.getBody(), SWT.NULL);
		  label3.setText("Classificatori");
		  
		  
		  comboBox = new Combo(form.getBody(), SWT.CENTER| SWT.READ_ONLY
		            | SWT.DROP_DOWN);
		  comboBox.setItems(items);
		   
		  propeties = toolkit.createButton(form.getBody(), "Properties", SWT.ICON_INFORMATION);
		
		  
		  Text text11 = toolkit.createText(form.getBody(), "Information", SWT.SINGLE);
		  GridData gd5 = new GridData();
		  gd5.widthHint = 200;
		  gd5.heightHint=150;
		  text11.setLayoutData(gd5);
		  text11.setVisible(false);
		  
		  
		  propeties.addMouseListener(new MouseListener() {@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					text11.setVisible(true);
					
					
				}
				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}		
			});
		  
		  Section section = toolkit.createSection(form.getBody(), 
				   Section.DESCRIPTION|Section.TITLE_BAR|
				   Section.TWISTIE|Section.EXPANDED);
		   section.setText("Pre-processing operation");
		   Composite sectionClient = toolkit.createComposite(section);
		   sectionClient.setLayout(new GridLayout());
		   dN = toolkit.createButton(sectionClient, "Data Normalization", SWT.CHECK);
		   fS = toolkit.createButton(sectionClient, "Feature Section", SWT.CHECK);
		   dB = toolkit.createButton(sectionClient, "Data Balancing", SWT.CHECK);
		   lC = toolkit.createButton(sectionClient, "Local", SWT.CHECK);
		   section.setClient(sectionClient);
		   
		  
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