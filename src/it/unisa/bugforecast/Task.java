package it.unisa.bugforecast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.evaluation.Prediction;

public class Task {

	public Evaluation evaluation;
	public Application a;

	public Task(Application application) {

		Job job = new Job("BugForecast-ECLIPSE") {
			protected IStatus run(IProgressMonitor monitor) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						try {
							evaluation = Study.run(application);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

				ArrayList<Prediction> predictions = evaluation.predictions();
				for (Prediction prediction : predictions) {

				}

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

		// mostra la schermata 2
		try {

			a = (Application) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView("bugforecasteclipse.it.unisa.bugforecast.Application");

		}

		catch (PartInitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
