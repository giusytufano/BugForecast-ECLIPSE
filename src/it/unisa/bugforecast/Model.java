package it.unisa.bugforecast;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.Stacking;
import weka.classifiers.meta.Vote;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

/**
 * @author Giusy
 */
public class Model {

	public static Evaluation buildAndEvaluate(String classifierName, String optionsString, Instances pTrainingSet, Instances pTestSet) throws Exception {
		AbstractClassifier classifier = null;

		switch (classifierName) {
		case "Log":
			classifier = new Logistic();
			break;
		case "Voting":
			classifier = new Vote();
			break;
		case "Bagging":
			classifier = new Bagging();
			break;
		case "Boostring":
			classifier = new AdaBoostM1();
			break;
		case "Random Forest":
			classifier = new RandomForest();
			break;
		case "CODEP":
			classifier = new Stacking();
			break;
		case "C45":
			classifier = new J48();
			break;
		case "Decision Table":
			classifier = new DecisionTable();
			break;
		case "MLP":
			classifier = new MultilayerPerceptron();
			break;
		case "RBF":
			classifier = new RBFNetwork();
			break;
		case "NB":
			classifier = new NaiveBayes();
			break;
		case "ASCI":
			classifier = new ASCI();
			break;
		default:
			System.err.println("Unknown classifier.");
		}

		String[] options = weka.core.Utils.splitOptions(optionsString);
		classifier.setOptions(options);

		pTrainingSet.setClassIndex(pTrainingSet.numAttributes() - 1);
		pTestSet.setClassIndex(pTrainingSet.numAttributes() - 1);

		classifier.buildClassifier(pTrainingSet);

		Evaluation eval = new Evaluation(pTrainingSet);
		eval.evaluateModel(classifier, pTestSet);

		return eval;
	}
}
