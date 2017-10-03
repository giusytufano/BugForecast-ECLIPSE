/**
 * 
 */
package it.unisa.bugforecast;

/**
 * @author Giusy
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.classifiers.evaluation.AggregateableEvaluation;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

public class Study {

	public static Evaluation run(Application application) throws Exception {
		
		String dataSetFolder = application.trainingFileText.getText();
		String outputFolder = application.outputFolderText.getText();
		String testFile = application.testFileText.getText();

		String validationTechniques;

		if (application.withinButton.getSelection()) {
			validationTechniques = "within";
		} else if (application.crossButton.getSelection()) {
			validationTechniques = "cross";
		} else if (application.localButton.getSelection()) {
			validationTechniques = "local";
		} else {
			throw new Exception("Validation Technique not valid");
		}

		File datasetFile = new File(dataSetFolder);
		System.out.println(datasetFile.getAbsolutePath());
		File resultFolder;

		switch (validationTechniques) {
		case "within":
			resultFolder = new File(outputFolder);
			resultFolder.mkdirs();
			break;
		case "cross":
			resultFolder = new File(outputFolder);
			resultFolder.mkdirs();
			break;
		case "local":
			resultFolder = new File(outputFolder);
			resultFolder.mkdirs();
			break;
		}

		ArrayList<String> datasetFilePaths = new ArrayList<>();
		ArrayList<String> datasetFileNames = new ArrayList<>();

		File[] datasetFileList;

		if (datasetFile.isDirectory()) {
			datasetFileList = datasetFile.listFiles();
			if (datasetFileList != null) {
				for (File datasetFileElem : datasetFileList) {
					datasetFilePaths.add(datasetFileElem.getAbsolutePath());
					datasetFileNames.add(datasetFileElem.getName());
				}
			}
		}

		String classifierName = application.classifierName;
		String optionString = application.classifierDetails;

		// Create training and test sets
		DatasetOperations datasetOperations = new DatasetOperations();

		File resultFile = new File(outputFolder + File.separator + "results.csv");

		try (PrintWriter pw = new PrintWriter(new FileOutputStream(resultFile, false))) {
			pw.write("Project,Model,ACC,PRE,REC,FM,AUC,MCC\n");
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Study.class.getName()).log(Level.SEVERE, null, ex);
		}

		TrainingTestSets data = null;
		int numFolds = 10;

		switch (validationTechniques) {
		case "within":
			System.out.println("Validation technique: within-project (10-folds validation).");
			numFolds = 10;
			data = datasetOperations.kFoldsValidation(datasetFile.getAbsolutePath(), numFolds);
			//data = datasetOperations.normalizeData(data);
			//data = datasetOperations.selectAttributes(data);
			//data = datasetOperations.balanceData(data);
			break;
		case "cross":
			System.out.println("Validation technique: cross-project.");
			data = datasetOperations.leaveOneOut(testFile, datasetFilePaths);
			//data = datasetOperations.normalizeData(data);
			//data = datasetOperations.selectAttributes(data);
			//data = datasetOperations.balanceData(data);
			numFolds = 1;
			break;
		case "local":
			System.out.println("Validation technique: local cross-project.");
			data = datasetOperations.local(testFile, datasetFilePaths);
			//data = datasetOperations.normalizeData(data);
			//data = datasetOperations.selectAttributes(data);
			//data = datasetOperations.balanceData(data);
			numFolds = 1;
			break;
		}

		AggregateableEvaluation evaluation = null;

		System.out.println("Classifier " + classifierName);

		if (data != null) {
			for (int fold = 0; fold < numFolds; fold++) {
				Evaluation singleFoldEvaluation = Model.buildAndEvaluate(classifierName, optionString,
						data.getTrainingSet(fold), data.getTestSet(fold));
				if (evaluation == null) {
					evaluation = new AggregateableEvaluation(singleFoldEvaluation);
					evaluation.aggregate(singleFoldEvaluation);
				} else {
					evaluation.aggregate(singleFoldEvaluation);
				}
			}

			Study.saveAccuracyMeasure(resultFile, testFile, classifierName, evaluation);
		}

		for (int i = 0; i < data.getNumberOfSets(); i++) {
			Instances instances = data.getTestSet(i);

			for (Instance instance : instances) {
				instance.attribute(0);
			}
		}

		return evaluation;
	}

	private static void saveAccuracyMeasure(File csvFile, String projectName, String modelName, Evaluation evaluation) {
		try (final PrintWriter pw = new PrintWriter(new FileOutputStream(csvFile, true))) {
			pw.write(projectName + "," + modelName + "," + evaluation.pctCorrect() / 100 + "," + evaluation.precision(1)
					+ "," + evaluation.recall(1) + "," + evaluation.fMeasure(1) + "," + evaluation.areaUnderROC(1) + ","
					+ evaluation.matthewsCorrelationCoefficient(1) + "\n");
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Study.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
