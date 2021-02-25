package com.db.interpretor.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import com.google.cloud.vision.v1.ImageAnnotatorSettings; 
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.SpeechSettings;

@Component("viewTextService")
public class viewTextServiceImpl implements viewTextService  {

	    //@Value("${app.path}")
	    public String extractText(String appmyCredentials,String appprojectId,String appbucketName, String fileName)
	    {
	
		    String myCredentials = appmyCredentials; //"E:\\Study\\fleet-passkey-305804-5e9e544f6ca6.json";
		    // The ID of your GCP project
	        String projectId = appprojectId;//"fleet-passkey-305804";
 
	       // The ID of your GCS bucket
	        String bucketName = appbucketName;// "filebucket-shirish";
	        String responseText =""; 
		    String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		    System.out.println("FileType extracted "+fileType);
	    	switch(fileType)
	    	{
	    	case "txt": case "text":
	    		System.out.println("Input text readable File. getting the text file");
	    		responseText = readFromTxtFiles(fileName,myCredentials,projectId,bucketName);
	    		break;
	    	case "jpeg": case "jpg":
	    		System.out.println("Input Image File. Calling Image to Text");
	    		responseText = readFromImageFiles(fileName,myCredentials,projectId,bucketName);
	    		break;
	    	case "raw": case "mp4": case "wav":
	    		System.out.println("Input Voice File. Calling Voice to Text");
	    		responseText = readFromVoiceFiles(fileName,myCredentials,projectId,bucketName);
	    		break;
	    	}

	    	System.out.println("file"+"-"+fileName+"Type-"+fileType);
	        return responseText; 
	    	
	    }
	    
	    public String readFromTxtFiles(String fileName, String myCredentials, String projectId, String bucketName)
	    {

		    String responseText =""; 
	    	Storage storage = null;
	    	 		
				try {
					storage = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(myCredentials))).build().getService();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					System.out.println("Error in getting storage");
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Blob blob = storage.get(bucketName, fileName);
				responseText = new String(blob.getContent());
   
	        return responseText; 	    	
	    }
	    
	    public String readFromImageFiles (String fileName, String myCredentialsPath, String projectId, String bucketName)
	    {
	    	 String gcsPath = "gs://"+bucketName+"/"+fileName;    
	    	 String resp = "";
	    
	    	 try {
	    	 Credentials myCredentials = ServiceAccountCredentials.fromStream(
	    			    new FileInputStream(myCredentialsPath)); 

	    	 ImageAnnotatorSettings imageAnnotatorSettings =
			    ImageAnnotatorSettings.newBuilder()
			    .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
			    .build();

	    	 ImageAnnotatorClient imageAnnotatorClient =
	    			    ImageAnnotatorClient.create(imageAnnotatorSettings);
			
			 List<AnnotateImageRequest> requests = new ArrayList<>();

			    ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
			    Image img = Image.newBuilder().setSource(imgSource).build();
			    Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
			    AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			    requests.add(request);
			    //ImageAnnotatorClient client = ImageAnnotatorClient.create()) 
			     BatchAnnotateImagesResponse response = imageAnnotatorClient.batchAnnotateImages(requests);
			     List<AnnotateImageResponse> responses = response.getResponsesList();
			     

			      for (AnnotateImageResponse res : responses) {
			        if (res.hasError()) {
			          System.out.format("Error: %s%n", res.getError().getMessage());
			          return "";
			        }

			        // For full list of available annotations, see http://g.co/cloud/vision/docs
			        for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
			          System.out.format("Text: %s%n", annotation.getDescription());		
			          resp = annotation.getDescription();
			          break	; 
			          //System.out.format("Position : %s%n", annotation.getBoundingPoly());			        
			      }
			    }
	    } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error in getting storage");
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
     return resp; 
	    	
	    }

	    public String readFromVoiceFiles(String fileName, String myCredentialsPath, String projectId, String bucketName)
	    {

		    String responseText =""; 
		    String gcsPath = "gs://"+bucketName+"/"+fileName;    
		    try {
		    Credentials myCredentials = ServiceAccountCredentials.fromStream(
    			    new FileInputStream(myCredentialsPath)); 
		   
		    SpeechSettings speechClientSettings =
		    		SpeechSettings.newBuilder()
		    .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
		    .build();
		    
		    ImageAnnotatorSettings imageAnnotatorSettings =
				    ImageAnnotatorSettings.newBuilder()
				    .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
				    .build();
		    
        	SpeechClient speechClient = SpeechClient.create(speechClientSettings);
        		
	          // The path to the audio file to transcribe
	          //String gcsUri = "gs://cloud-samples-data/speech/brooklyn_bridge.raw";

	          // Builds the sync recognize request
	          RecognitionConfig config =
	              RecognitionConfig.newBuilder()
	                  .setEncoding(AudioEncoding.LINEAR16)
	                  //.setSampleRateHertz(16000)
	                  .setLanguageCode("en-US")
	                  .build();
	          RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsPath).build();

	          // Performs speech recognition on the audio file
	          RecognizeResponse response = speechClient.recognize(config, audio);
	          List<SpeechRecognitionResult> results = response.getResultsList();

	          for (SpeechRecognitionResult result : results) {
	            // There can be several alternative transcripts for a given chunk of speech. Just use the
	            // first (most likely) one here.
	            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
	            System.out.printf("Text: %s%n", alternative.getTranscript());
	            responseText = alternative.getTranscript() ;
	          }
	        }
		    catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("Error in getting storage");
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       return responseText;    
	 } 
}
