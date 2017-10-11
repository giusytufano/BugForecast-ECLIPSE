package it.unisa.bugforecast;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import weka.classifiers.evaluation.Evaluation;

public class Task {

	public Evaluation evaluation;
	public Application a;
	public Study s;
	public String acc;

	@SuppressWarnings("static-access")
	public Task(Application application) {

		Job job = new Job("BugForecast-ECLIPSE") {
			protected IStatus run(IProgressMonitor monitor) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						try {
							Study.run(application);
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

				try {
					TimeUnit.MILLISECONDS.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Status.OK_STATUS;
			}
		};

		job.schedule(); // start as soon as possible

		
		try {
			
			Application a= (Application) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("bugforecasteclipse.it.unisa.bugforecast.Application");
			
			
			
		} 
		
		catch (PartInitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// mostra la schermata 2
	

		/*
		 * mostra la seconda schermata try {
		 * PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView
		 * ("trio.views.SchermataSecondaria"); } catch (PartInitException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 * 
		 * elimina la pria schermata IWorkbenchPage
		 * wp=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		 * //Find desired view : IViewPart
		 * myView=wp.findView("trio.views.SchermataPrincipale"); //Hide the view :
		 * wp.hideView(myView);
		 */
	}

}
