package it.unisa.bugforecast;

import weka.classifiers.AbstractClassifier;
import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.ParallelMultipleClassifiersCombiner;
import weka.classifiers.rules.ZeroR;
import weka.core.*;

import java.util.*;

/*
 <!-- globalinfo-start -->
 will be automatically replaced
 <!-- globalinfo-end -->
 <!-- technical-bibtex-start -->
 will be automatically replaced
 <!-- technical-bibtex-end -->
 <!-- options-start -->
 will be automatically replaced
 <!-- options-end -->

 
 @version $Revision: 1 $
 */
public class ASCI extends ParallelMultipleClassifiersCombiner implements TechnicalInformationHandler {

	/**
	 * for serialization
	 */
	static final long serialVersionUID = 5134738557154852252L;

	/**
	 * The meta classifier and its evaluation
	 */
	private Classifier m_MetaClassifier = new ZeroR();

	/**
	 * Main method for testing this class.
	 *
	 * @param argv
	 *            should contain the following arguments: -t training file [-T test
	 *            file] [-c class index]
	 */
	public static void main(String[] argv) {
		runClassifier(new ASCI(), argv);
	}

	/**
	 * Returns a string describing this classifier
	 *
	 * @return a description of the classifier suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String globalInfo() {
		return "This class implements ASCI, an ensemble algorithm for the Adaptive Selection of Classifiers.\n\n"
				+ "For more information on ASCI, see\n\n" + getTechnicalInformation().toString();
	}

	/**
	 * Returns an instance of a TechnicalInformation object, containing detailed
	 * information about the technical background of this class, e.g., paper
	 * reference or book this class is based on.
	 *
	 * @return the technical information about this class
	 */
	@Override
	public TechnicalInformation getTechnicalInformation() {
		TechnicalInformation result;

		result = new TechnicalInformation(TechnicalInformation.Type.ARTICLE);
		result.setValue(TechnicalInformation.Field.AUTHOR,
				"Dario Di Nucci, Fabio Palomba, Rocco Oliveto, Andrea De Lucia");
		result.setValue(TechnicalInformation.Field.TITLE,
				"Dynamic Selection of Classifiers in Bug Prediction: an Adaptive Method");
		result.setValue(TechnicalInformation.Field.YEAR, "2017");
		result.setValue(TechnicalInformation.Field.PUBLISHER, "IEEE");
		result.setValue(TechnicalInformation.Field.JOURNAL,
				"IEEE Transactions on Emerging Topic on Computational Intelligence");
		result.setValue(TechnicalInformation.Field.VOLUME, "1");
		result.setValue(TechnicalInformation.Field.NUMBER, "3");
		result.setValue(TechnicalInformation.Field.PAGES, "202-212");

		return result;
	}

	/**
	 * Returns an enumeration describing the available options.
	 *
	 * @return an enumeration of all the available options.
	 */
	public Enumeration<Option> listOptions() {
		List<Option> options = new ArrayList<>();

		List<Option> parentOptions = Collections.list(super.listOptions());

		options.add(new Option("\tFull name of meta classifier, followed by options.\n"
				+ "\t(default: \"weka.classifiers.rules.ZeroR\")", "M", 0, "-M <scheme specification>"));

		Option baseClassifiersOption = new Option("\tFull class name of classifier to include, followed\n"
				+ "\tby scheme options. May be specified multiple times.\n"
				+ "\t(default: \"weka.classifiers.rules.ZeroR\")", "B", 0, "-B <scheme specification>");

		options.add(baseClassifiersOption);

		options.addAll(parentOptions.subList(1, 4));

		return Collections.enumeration(options);
	}

	/**
	 * Gets the current settings of the Classifier.
	 *
	 * @return an array of strings suitable for passing to setOptions
	 */
	public String[] getOptions() {
		List<String> result = new ArrayList<>();
		String[] options = super.getOptions();

		result.add("-M");

		result.add(getMetaClassifier().getClass().getName() + " "
				+ Utils.joinOptions(((OptionHandler) getMetaClassifier()).getOptions()));

		Collections.addAll(result, options);

		return (String[]) result.toArray(new String[result.size()]);
	}

	/*
	 * <!-- options-start --> will be automatically replaced <!-- options-end -->
	 */
	public void setOptions(String[] options) throws Exception {
		String classifierString = Utils.getOption('M', options);
		String[] classifierSpec = Utils.splitOptions(classifierString);
		String classifierName;
		if (classifierSpec.length == 0) {
			classifierName = "weka.classifiers.rules.ZeroR";
		} else {
			classifierName = classifierSpec[0];
			classifierSpec[0] = "";
		}
		setMetaClassifier(AbstractClassifier.forName(classifierName, classifierSpec));
		super.setOptions(options);
	}

	/**
	 * Returns the tip text for this property
	 *
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String metaClassifierTipText() {
		return "The meta classifiers to be used.";
	}

	/**
	 * Gets the meta classifier.
	 *
	 * @return the meta classifier
	 */
	private Classifier getMetaClassifier() {
		return m_MetaClassifier;
	}

	/**
	 * Adds meta classifier
	 *
	 * @param classifier
	 *            the classifier with all options set.
	 */
	private void setMetaClassifier(Classifier classifier) {
		m_MetaClassifier = classifier;
	}

	/**
	 * Returns default capabilities of the classifier.
	 *
	 * @return the capabilities of this classifier
	 */
	@Override
	public Capabilities getCapabilities() {
		Capabilities capabilities = super.getCapabilities();
		capabilities.disable(Capabilities.Capability.NUMERIC_CLASS);
		capabilities.disable(Capabilities.Capability.DATE_CLASS);
		return capabilities;
	}

	@Override
	public void buildClassifier(Instances data) throws Exception {

		// can classifier handle the data?
		getCapabilities().testWithFail(data);

		Instances newData = new Instances(data);

		// remove instances with missing class
		newData.deleteWithMissingClass();

		if (newData.isEmpty() || newData.classIndex() == -1) {
			return;
		}

		// build the single classifiers
		buildClassifiers(newData);

		// train data for metaClassifier tree
		Instances metaClassifierTraining = new Instances(newData);
		Double AUCs[] = new Double[m_Classifiers.length];
		String classifierType[] = new String[m_Classifiers.length];

		for (int j = 0; j < m_Classifiers.length; j++) {
			Classifier classifier = m_Classifiers[j];
			Evaluation evalutation = new Evaluation(newData);
			evalutation.evaluateModel(classifier, newData);
			AUCs[j] = evalutation.areaUnderROC(1);
			classifierType[j] = classifier.getClass().toString();
		}

		// insert classifier to use attribute
		metaClassifierTraining.insertAttributeAt(new Attribute("bestPredictor", Arrays.asList(classifierType)),
				metaClassifierTraining.numAttributes());
		metaClassifierTraining.setClassIndex(metaClassifierTraining.numAttributes() - 1);

		double[] toPredictValues = metaClassifierTraining
				.attributeToDoubleArray(metaClassifierTraining.numAttributes() - 2);

		// remove "buggy" attribute
		metaClassifierTraining.deleteAttributeAt(metaClassifierTraining.numAttributes() - 2);

		for (int i = 0; i < data.size(); i++) {
			Instance instance = data.get(i);
			double maxAUC = 0;
			int bestClassifierOnInstance = 0;
			for (int j = 0; j < m_Classifiers.length; j++) {
				double prediction = m_Classifiers[j].classifyInstance(instance);
				if (prediction == toPredictValues[i]) {
					if (AUCs[j] > maxAUC) {
						maxAUC = AUCs[j];
						bestClassifierOnInstance = j;
					}
				}
			}
			metaClassifierTraining.get(i).setValue(metaClassifierTraining.numAttributes() - 1,
					m_Classifiers[bestClassifierOnInstance].getClass().toString());
		}

		m_MetaClassifier.buildClassifier(metaClassifierTraining);
	}

	/**
	 * Classifies the given test instance. The instance has to belong to a dataset
	 * when it's being classified.
	 *
	 * @param instance
	 *            the instance to be classified
	 * @return the predicted most likely class for the instance or
	 *         Utils.missingValue() if no prediction is made
	 * @throws Exception
	 *             if instance could not be classified successfully
	 */
	@Override
	public double classifyInstance(Instance instance) throws Exception {

		Instances testSet = instance.dataset();
		Instances metaClassifierTest = new Instances(testSet);
		Instance metaClassifierInstance = (Instance) instance.copy();

		List<String> classifierType = new ArrayList<>();

		for (Classifier m_Classifier : m_Classifiers) {
			classifierType.add(m_Classifier.getClass().toString());
		}

		// insert classifier to use attribute
		metaClassifierTest.insertAttributeAt(new Attribute("bestPredictor", classifierType),
				metaClassifierTest.numAttributes());
		metaClassifierTest.setClassIndex(metaClassifierTest.numAttributes() - 1);

		// remove "buggy" attribute
		metaClassifierTest.deleteAttributeAt(metaClassifierTest.numAttributes() - 2);

		metaClassifierInstance.setDataset(metaClassifierTest);

		int classifierToUse;

		double prediction = 0;

		if (m_Classifiers.length != 1) {
			// Get the classifier to use for predicting the instance
			classifierToUse = (int) m_MetaClassifier.classifyInstance(metaClassifierInstance);
			prediction = m_Classifiers[classifierToUse].classifyInstance(instance);
		}

		return prediction;
	}

	/**
	 * Output a representation of this classifier
	 *
	 * @return a string representation of the classifier
	 */
	@Override
	public String toString() {

		if (m_Classifiers.length == 0) {
			return "ASCI: No base schemes entered.";
		}

		StringBuilder result = new StringBuilder("ASCI combines");
		result.append(" these base learners:\n");
		for (int i = 0; i < m_Classifiers.length; i++) {
			result.append('\t').append(getClassifierSpec(i)).append('\n');
		}

		for (Classifier c : m_Classifiers) {
			result.append("\t").append(c.getClass().getName())
					.append(Utils.joinOptions(((OptionHandler) c).getOptions())).append("\n");
		}

		result.append("\n\nMeta classifier\n\n").append(m_MetaClassifier.toString());
		return result.toString();
	}

	@Override
	public String getRevision() {
		return RevisionUtils.extract("$Revision: 1.0 $");
	}

}
