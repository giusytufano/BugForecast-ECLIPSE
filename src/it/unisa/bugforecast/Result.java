package it.unisa.bugforecast;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

public class Result extends ViewPart implements IViewPart {

	protected static final String CLOSED_OPTION = null;
	public Form form;
	public FormToolkit toolkit;
	public Composite composite;
	public TabFolder tabFolder;
	public TabItem tab2;
	public Application view;
	public Table table2;
	public Table table;
	public String stringa;
	public Application a;
	public TableItem item;
	public TableItem item2;
	public TableItem item3;
	public TableItem item4;
	public TableItem item5;
	public TableItem item6;
	
	
	
	public Result() {
	}

	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createForm(parent);
		GridLayout layout = new GridLayout(4, false);
		form.getBody().setLayout(layout);
		createResultForm();

		

	}

	public void createResultForm() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		
		
		Label Result = new Label(form.getBody(), SWT.BORDER);
		Result.setText("BUGFORECAST-RESULT");
		Result.setLayoutData(gridData);		
		
		CreateTableMetrics();
		
		CreateTableClassifier();
		
		
	}
	
	
	public void CreateTableMetrics() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		GridLayout layout = new GridLayout(3, false);
	
		Section accurancyMetrics = toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		accurancyMetrics.setText("Accurancy Metrics");
		accurancyMetrics.setLayoutData(gridData);
		Composite sectionMetrics = toolkit.createComposite(accurancyMetrics);
		sectionMetrics.setLayout(layout);
		
		
		
		Table table=toolkit.createTable(sectionMetrics, SWT.BORDER_SOLID);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		table.setLayoutData(gridData);
		
		String[] titles = {"         ", "        "};
		for (int i=0; i<(titles.length); i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
		}
		
		
		
		item= new TableItem(table, SWT.NONE);
		item2= new TableItem(table, SWT.NONE);
		item3= new TableItem(table, SWT.NONE);
		item4= new TableItem(table, SWT.NONE);
		item5= new TableItem(table, SWT.NONE);
		item6= new TableItem(table, SWT.NONE);
		
		
		
			
		
		
		for (int i=0; i<titles.length; i++) {
			table.getColumn (i).pack ();
		} 
		
		accurancyMetrics.setClient(sectionMetrics);
		table.pack(); 
	}
	
	public void CreateTableClassifier()
	{
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 4;
		GridLayout layout = new GridLayout(3, false);
	
		Section classifierMetrics = toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		classifierMetrics.setText("Classifier");
		classifierMetrics.setLayoutData(gridData);
		Composite sectionMetrics = toolkit.createComposite(classifierMetrics);
		sectionMetrics.setLayout(layout);
		
		
		
		Table table2=toolkit.createTable(sectionMetrics, SWT.BORDER_SOLID);
		table2.setLinesVisible (true);
		table2.setHeaderVisible (true);
		table2.setLayoutData(gridData);
		
		String[] titles = {"Class","IsBuggy", "IsReallyBuggy"};
		for (int i=0; i<(titles.length); i++) {
			TableColumn column = new TableColumn (table2, SWT.NONE);
			column.setText (titles [i]);
		}
		
				
			TableItem item1=new TableItem(table2, SWT.NONE);
			item1.setText(0,"exemple.csv");
			item1.setText(1,"true");
			item1.setText(2,"false");
			
		
		
		for (int i=0; i<titles.length; i++) {
			table2.getColumn (i).pack ();
		} 
		
		
		classifierMetrics.setClient(sectionMetrics);
		table2.pack(); 
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