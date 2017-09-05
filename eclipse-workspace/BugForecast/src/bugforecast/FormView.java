package bugforecast;



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
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.part.ViewPart;

public class FormView extends ViewPart {
 private FormToolkit toolkit;
 private org.eclipse.ui.forms.widgets.Form form;
 private Combo comboBox;
 private Button within;
 private Button cross;
 private Button dN;
 private Button fS;
 private Button dB;
 private Button lC;

 
 /**
  * The constructor.
  */
 public FormView() {
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
		}
		@Override
		public void mouseUp(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}		
	});
	  Label label = new Label(form.getBody(), SWT.NULL);
	  label.setText("Input file/ Training folder");
	  Text text = new Text(form.getBody(), SWT.BORDER);
	  text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	  Label label1= new Label(form.getBody(), SWT.NULL);
	  label1.setText("Output folder");
	  Text text1= new Text(form.getBody(), SWT.BORDER);
	  text1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	  
	   
	  gd = new GridData();
	  gd.horizontalSpan = 2;
	  
	  
	  String items[] = { "Item One", "Item Two", "Item Three", "Item Four",
      "Item Five" };
	  
	  Label label2 = new Label(form.getBody(), SWT.NULL);
	  label2.setText("Classificatori");
	     
	   comboBox = new Combo(form.getBody(), SWT.BORDER | SWT.READ_ONLY
	            | SWT.DROP_DOWN);
	   comboBox.setItems(items);
	   
	   Section section = toolkit.createSection(form.getBody(), 
			   Section.DESCRIPTION|Section.TITLE_BAR|
			   Section.TWISTIE|Section.EXPANDED);
	   section.setText("PRE-PROCESSING OPERATION");
	   TableWrapData td = new TableWrapData(TableWrapData.FILL);
	   td.colspan = 2;
	   section.setLayoutData(td);
	   Composite sectionClient = toolkit.createComposite(section);
	   sectionClient.setLayout(new GridLayout());
	   dN = toolkit.createButton(form.getBody(), "Data Normalization", SWT.CHECK);
	   fS = toolkit.createButton(form.getBody(), "Feature Section", SWT.CHECK);
	   dB = toolkit.createButton(form.getBody(), "Data Balancing", SWT.CHECK);
	   lC = toolkit.createButton(form.getBody(), "Local", SWT.CHECK);
	   
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