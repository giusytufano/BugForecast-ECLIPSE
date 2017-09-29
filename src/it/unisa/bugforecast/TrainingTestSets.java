package it.unisa.bugforecast;

import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * @author giusy
 */
public class TrainingTestSets {

	private final List<Instances> trainingSets;
	private final List<Instances> testSets;
	private Instances datasetExample;

	public TrainingTestSets() {
		this.trainingSets = new ArrayList<>();
		this.testSets = new ArrayList<>();
	}

	public TrainingTestSets(Instances datasetExample) {
		this.datasetExample = datasetExample;
		this.trainingSets = new ArrayList<>();
		this.testSets = new ArrayList<>();
	}

	public TrainingTestSets(Instances datasetExample, int numOfClusters) {
		this.datasetExample = datasetExample;
		this.trainingSets = new ArrayList<>();
		this.testSets = new ArrayList<>();

		for (int i = 0; i < numOfClusters; i++) {
			this.trainingSets.add(new Instances(datasetExample));
			this.testSets.add(new Instances(datasetExample));
		}

	}

	Instances getTrainingSet(int i) {
		return trainingSets.get(i);
	}

	void addTrainingSet(Instances trainingSet) {
		this.trainingSets.add(new Instances(trainingSet));
	}

	void addInstanceToTrainingSet(Instance instance, int trainingSetNumber) {
		trainingSets.get(trainingSetNumber).add(instance);
	}

	Instances getTestSet(int i) {
		return testSets.get(i);
	}

	void addTestSet(Instances testSet) {
		this.testSets.add(new Instances(testSet));
	}

	void addInstanceToTestSet(Instance instance, int testSetNumber) {
		if (testSets.size() < testSetNumber) {
			testSets.add(new Instances(datasetExample));
		}

		testSets.get(testSetNumber).add(instance);
	}

	int getNumberOfSets() {
		return this.trainingSets.size();
	}

}
