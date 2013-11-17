/*
#include "GRT/GRT.h"
using namespace GRT;

int main (int argc, const char * argv[])
{
    //Create a new DTW instance, using the default parameters
	DTW dtw;

    LabelledTimeSeriesClassificationData testData;
    if( !testData.loadDatasetFromFile(argv[1]) ){
		cout << "Failed to load test data!\n";
		return EXIT_FAILURE;
	}

	//Load the DTW model from a file
	if( !dtw.loadModelFromFile(argv[2]) ){
		cout << "Failed to load the classifier model!\n";
		return EXIT_FAILURE;
	}

	//Use the test dataset to test the DTW model
	double accuracy = 0;
	for(UINT i=0; i<testData.getNumSamples(); i++){
		//Get the i'th test sample - this is a timeseries
		UINT classLabel = testData[i].getClassLabel();
		MatrixDouble timeseries = testData[i].getData();

		//Perform a prediction using the classifier
		if( !dtw.predict( timeseries ) ){
			cout << "Failed to perform prediction for test sampel: " << i <<"\n";
			return EXIT_FAILURE;
		}

		//Get the predicted class label
		UINT predictedClassLabel = dtw.getPredictedClassLabel();
		double maximumLikelihood = dtw.getMaximumLikelihood();
		VectorDouble classLikelihoods = dtw.getClassLikelihoods();
		VectorDouble classDistances = dtw.getClassDistances();

		//Update the accuracy
		if( classLabel == predictedClassLabel ) accuracy++;

        cout << "TestSample: " << i <<  "\tClassLabel: " << classLabel << "\tPredictedClassLabel: " << predictedClassLabel << "\tMaximumLikelihood: " << maximumLikelihood << endl;
	}

	cout << "Test Accuracy: " << accuracy/double(testData.getNumSamples())*100.0 << "%" << endl;
}

*/
