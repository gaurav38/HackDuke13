/**
 @file
 @author  Nicholas Gillian <ngillian@media.mit.edu>
 @version 1.0
 
 @section LICENSE
 GRT MIT License
 Copyright (c) <2012> <Nicholas Gillian, Media Lab, MIT>
 
 Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 and associated documentation files (the "Software"), to deal in the Software without restriction, 
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all copies or substantial 
 portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 
 @section DESCRIPTION
 */

#ifndef GRT_TIME_DOMAIN_FEATURES_HEADER
#define GRT_TIME_DOMAIN_FEATURES_HEADER

#include "../../GestureRecognitionPipeline/FeatureExtraction.h"
#include "../../Util/Util.h"

namespace GRT{
    
class TimeDomainFeatures : public FeatureExtraction{
public:
    /**
     */
    TimeDomainFeatures(UINT bufferLength=100,UINT numFrames=10,UINT numDimensions = 1,bool offsetInput = false,bool useMean = true,bool useStdDev = true,bool useEuclideanNorm = true,bool useRMS = true);
	
    /**
     Copy constructor, copies the TimeDomainFeatures from the rhs instance to this instance.
     
     @param const TimeDomainFeatures &rhs: another instance of the TimeDomainFeatures class from which the data will be copied to this instance
     */
    TimeDomainFeatures(const TimeDomainFeatures &rhs);
    
    /**
     Default Destructor
     */
    virtual ~TimeDomainFeatures();
    
    /**
     Sets the equals operator, copies the data from the rhs instance to this instance.
     
     @param const TimeDomainFeatures &rhs: another instance of the TimeDomainFeatures class from which the data will be copied to this instance
     @return a reference to this instance of TimeDomainFeatures
     */
    TimeDomainFeatures& operator=(const TimeDomainFeatures &rhs);

    /**
     Sets the FeatureExtraction clone function, overwriting the base FeatureExtraction function.
     This function is used to clone the values from the input pointer to this instance of the FeatureExtraction module.
     This function is called by the GestureRecognitionPipeline when the user adds a new FeatureExtraction module to the pipeline.
     
     @param FeatureExtraction *featureExtraction: a pointer to another instance of a TimeDomainFeatures, the values of that instance will be cloned to this instance
     @return returns true if the clone was successful, false otherwise
     */
    virtual bool clone(const FeatureExtraction *featureExtraction);
    
    /**
     Sets the FeatureExtraction computeFeatures function, overwriting the base FeatureExtraction function.
     This function is called by the GestureRecognitionPipeline when any new input data needs to be processed (during the prediction phase for example).
     This function calls the TimeDomainFeatures's update function.
     
     @param const VectorDouble &inputVector: the inputVector that should be processed.  Must have the same dimensionality as the FeatureExtraction module
     @return returns true if the data was processed, false otherwise
     */
    virtual bool computeFeatures(const VectorDouble &inputVector);
    
    /**
     Sets the FeatureExtraction reset function, overwriting the base FeatureExtraction function.
     This function is called by the GestureRecognitionPipeline when the pipelines main reset() function is called.
     This function resets the feature extraction by re-initiliazing the instance.
     
     @return true if the filter was reset, false otherwise
     */
    virtual bool reset();
    
    /**
     This saves the feature extraction settings to a file.
     This overrides the saveSettingsToFile function in the FeatureExtraction base class.
     
     @param string filename: the name of the file to save the settings to
     @return returns true if the settings were saved successfully, false otherwise (the base class always returns false)
     */
    virtual bool saveSettingsToFile(string filename);
    
    /**
     This loads the feature extraction settings from a file.
     This overrides the loadSettingsFromFile function in the FeatureExtraction base class.
     
     @param string filename: the name of the file to load the settings from
     @return returns true if the settings were loaded successfully, false otherwise (the base class always returns false)
     */
    virtual bool loadSettingsFromFile(string filename);
    
    /**
     This saves the feature extraction settings to a file.
     This overrides the saveSettingsToFile function in the FeatureExtraction base class.
     
     @param fstream &file: a reference to the file to save the settings to
     @return returns true if the settings were saved successfully, false otherwise
     */
    virtual bool saveSettingsToFile(fstream &file);
    
    /**
     This loads the feature extraction settings from a file.
     This overrides the loadSettingsFromFile function in the FeatureExtraction base class.
     
     @param fstream &file: a reference to the file to load the settings from
     @return returns true if the settings were loaded successfully, false otherwise
     */
    virtual bool loadSettingsFromFile(fstream &file);

    /**
     Initializes the MovementTrajectoryFeatures
     */
    bool init(UINT bufferLength,UINT numFrames,UINT numDimensions,bool offsetInput,bool useMean,bool useStdDev,bool useEuclideanNorm,bool useRMS);
    
    /**
     Computes the features from the input, this should only be called if the dimensionality of this instance was set to 1.
     
     @param double x: the value to compute features from, this should only be called if the dimensionality of the filter was set to 1
	 @return a vector containing the features, an empty vector will be returned if the features were not computed
     */
	VectorDouble update(double x);
    
    /**
     Computes the features from the input, the dimensionality of x should match that of this instance.
     
     @param const vector<double> &x: a vector containing the values to be processed, must be the same size as the numInputDimensions
	 @return a vector containing the features, an empty vector will be returned if the features were not computed
     */
    VectorDouble update(const VectorDouble &x);
    
    /**
     */
    CircularBuffer< VectorDouble > getBufferData();

protected:
    UINT bufferLength;
    UINT numFrames;
    bool offsetInput;
    bool useMean;
    bool useStdDev;
    bool useEuclideanNorm;
    bool useRMS;
    CircularBuffer< VectorDouble > dataBuffer;
    
    static RegisterFeatureExtractionModule< TimeDomainFeatures > registerModule;
};

}//End of namespace GRT

#endif //GRT_TIME_DOMAIN_FEATURES_HEADER
