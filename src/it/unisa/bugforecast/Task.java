package it.unisa.bugforecast;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


public class Task {
	
	public Task() {
		System.out.println("prima del thread thread.....");
		task();
	}

	public void task() {
	
		Job job = new Job("BugForecast-ECLIPSE") {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					Study.run(new Application());
					TimeUnit.MILLISECONDS.sleep(10000);
				} 
					catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Hello World (from a background job)");
				return Status.OK_STATUS;
			}
		};

		
		job.schedule(); // start as soon as possible
		
		//mostra la schermata 2
		try {
						
			Application a= (Application) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("bugforecasteclipse.it.unisa.bugforecast.Application");
			Result w= (Result) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("bugforecasteclipse.it.unisa.bugforecast.Result");
			
			int size= a.control.size();
			
			if(size>0) {
			w.item.setText(0, a.control.get(0));
			w.item.setText(1, "0.01");
			size=size-1;}
			
			if(size>0) {
			w.item2.setText(0, a.control.get(1));
			w.item2.setText(1, "0.02");
			size=size-1;}
			
			if(size>0) {
			w.item3.setText(0, a.control.get(2));
			w.item3.setText(1, "0.03");
			size=size-1;}
			
			if(size>0) {
			w.item4.setText(0, a.control.get(3));
			w.item4.setText(1, "0.04");
			size=size-1;}
			

			if(size>0) {
			w.item5.setText(0, a.control.get(4));
			w.item5.setText(1, "0.04");
			size=size-1;}
			
			if(size>0) {
			w.item6.setText(0, a.control.get(5));
			w.item6.setText(1, "0.05");
			size=size-1;}
		} 
		
		catch (PartInitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		
		
	
			
			 
	
		/*
		 mostra la seconda schermata
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("trio.views.SchermataSecondaria");
		} catch (PartInitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		elimina la pria schermata
		 IWorkbenchPage wp=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(); 
		 //Find desired view : 
		 IViewPart myView=wp.findView("trio.views.SchermataPrincipale"); 
		 //Hide the view : 
		 wp.hideView(myView);
		 */
	}

}
