package it.unisa.bugforecast;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import it.unisa.bugforecast.TrainingTestSets;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.clusterers.XMeans;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.attribute.Normalize;

public class DatasetOperations {

	TrainingTestSets leaveOneOut(String testFileName, Collection<String> fileNames) throws IOException {
		Instances trainingSet = null;
		CSVLoader loader = new CSVLoader();

		for (String fileName : fileNames) {
			loader.setSource(new File(fileName));
			Instances instances = loader.getDataSet();
			convertDefectsToBoolean(instances);

			if (!fileName.equals(testFileName)) {
				if (trainingSet == null) {
					trainingSet = new Instances(instances);
				} else {
					trainingSet.addAll(instances);
				}
			}
		}

		loader.setSource(new File(testFileName));
		Instances testSet = loader.getDataSet();
		convertDefectsToBoolean(testSet);

		TrainingTestSets tts = new TrainingTestSets(trainingSet);
		tts.addTrainingSet(trainingSet);
		tts.addTestSet(testSet);

		return tts;
	}

	TrainingTestSets kFoldsValidation(String fileName, int k) throws IOException {
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(fileName));

		Instances data = loader.getDataSet();
		convertDefectsToBoolean(data);

		Instances randData = new Instances(data);
		randData.stratify(10);

		TrainingTestSets tts = new TrainingTestSets(data);
		for (int i = 0; i < k; i++) {
			Instances trainingSet = randData.trainCV(10, i);
			Instances testSet = randData.testCV(10, i);
			tts.addTrainingSet(trainingSet);
			tts.addTestSet(testSet);

		}
		return tts;
	}

	TrainingTestSets local(String testFileName, Collection<String> fileNames) throws Exception {
		TrainingTestSets tts = null;
		Instances trainingSet = null;
		CSVLoader loader = new CSVLoader();

		for (String fileName : fileNames) {
			loader.setSource(new File(fileName));
			Instances instances = loader.getDataSet();
			convertDefectsToBoolean(instances);

			if (!fileName.equals(testFileName)) {
				if (trainingSet == null) {
					trainingSet = new Instances(instances);
				} else {
					trainingSet.addAll(instances);
				}
			}
		}

		loader.setSource(new File(testFileName));
		Instances testSet = loader.getDataSet();
		convertDefectsToBoolean(testSet);

		if (trainingSet != null) {
			Instances allInstances = new Instances(trainingSet);
			allInstances.addAll(testSet);

			XMeans clusterer = new XMeans();

			Instances allInstancesWithoutDefects = new Instances(allInstances);
			allInstancesWithoutDefects.setClassIndex(-1);
			allInstancesWithoutDefects.deleteAttributeAt(allInstances.classIndex());

			clusterer.buildClusterer(allInstancesWithoutDefects);

			tts = new TrainingTestSets(trainingSet, clusterer.numberOfClusters());

			for (int i = 0; i < allInstancesWithoutDefects.numInstances(); i++) {
				Instance instance = allInstancesWithoutDefects.get(i);
				int cluster = clusterer.clusterInstance(instance);

				if (trainingSet.contains(instance)) {
					tts.addInstanceToTrainingSet(instance, cluster);
				}
				if (testSet.contains(instance)) {
					tts.addInstanceToTestSet(instance, cluster);
				}
			}
		}

		return tts;
	}

	TrainingTestSets normalizeData(TrainingTestSets tts) throws Exception {
		Normalize filter = new Normalize();

		TrainingTestSets filteredTrainingTestSet = new TrainingTestSets();

		for (int i = 0; i < tts.getNumberOfSets(); i++) {
			filter.setInputFormat(tts.getTrainingSet(i));
			filteredTrainingTestSet.addTrainingSet(Filter.useFilter(tts.getTrainingSet(i), filter));
			filteredTrainingTestSet.addTestSet(Filter.useFilter(tts.getTestSet(i), filter));
		}

		return filteredTrainingTestSet;
	}

	TrainingTestSets selectAttributes(TrainingTestSets tts) throws Exception {
		AttributeSelection filter = new AttributeSelection();
		filter.setEvaluator(new CfsSubsetEval());
		filter.setSearch(new BestFirst());

		TrainingTestSets filteredTrainingTestSet = new TrainingTestSets();

		for (int i = 0; i < tts.getNumberOfSets(); i++) {
			filter.setInputFormat(tts.getTrainingSet(i));
			filteredTrainingTestSet.addTrainingSet(Filter.useFilter(tts.getTrainingSet(i), filter));
			filteredTrainingTestSet.addTestSet(Filter.useFilter(tts.getTestSet(i), filter));
		}

		return filteredTrainingTestSet;
	}

	TrainingTestSets balanceData(TrainingTestSets tts) throws Exception {
		SMOTE filter = new SMOTE();

		TrainingTestSets filteredTrainingTestSet = new TrainingTestSets();

		for (int i = 0; i < tts.getNumberOfSets(); i++) {
			filter.setInputFormat(tts.getTrainingSet(i));
			filteredTrainingTestSet.addTrainingSet(Filter.useFilter(tts.getTrainingSet(i), filter));
			filteredTrainingTestSet.addTestSet(Filter.useFilter(tts.getTestSet(i), filter));
		}

		return filteredTrainingTestSet;
	}

	private void convertDefectsToBoolean(Instances instances) {
		List<String> binaryBugType = new ArrayList<>();
		binaryBugType.add("FALSE");
		binaryBugType.add("TRUE");

		int lastElementIndex = instances.numAttributes() - 1;

		instances.insertAttributeAt(new Attribute("defective", binaryBugType), lastElementIndex + 1);

		for (Instance instance : instances) {
			if (instance.attribute(lastElementIndex).isNumeric()) {
				if (instance.value(lastElementIndex) == 0) {
					instance.setValue(lastElementIndex + 1, "FALSE");
				} else {
					instance.setValue(lastElementIndex + 1, "TRUE");
				}
			}
		}

		instances.setClassIndex(lastElementIndex + 1);

		instances.deleteAttributeAt(lastElementIndex);
	}
}
