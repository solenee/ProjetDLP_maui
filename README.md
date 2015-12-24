# ProjetDLP_maui (GPL)

Version 1.0 23/12/2015
  
What is it?
-----------

  This work is an academic project proposing an integration of 
  [Maui library](https://github.com/zelandiya/maui) in Apache UIMA 
  framework. This library automatically identifies keyphrases in 
  documents. It is meant for terminology extraction and semi-automatic
  topic indexing. It supports English, French, German and Spanish.
  As an academic project developed in a short period of time, it is
  not meant to fully integrate Maui Library but to provide a working
  integration of the main functionalities of Maui.


Documentation
-------------

  All classes have a javadoc documentation please check them for 
  detailled information.


Installation
------------

  You'll need Eclipse (Luna preferably) with Maven and UIMA plugins.
  Check out the repository and import the project in Eclipse as Maven
  project.


Integrated functionalities
-----------------------------
  Maui works in two steps : 
  * Extraction model building using a training test of documents (.txt files) 
  with associated manual topics (.key files)
  * Keyphrases extraction for a test set of documents (.txt files)
  using the extraction model trained during the first step
  
Each step has been adapted into a UIMA pipeline (see [MauiPipelines](https://github.com/solenee/ProjetDLP_maui/blob/v00/mauilibrary/src/main/java/fr/unantes/uima/mauilibrary/pipeline/MauiPipelines.java) class) 
with chosen Maui configuration parameters (see [MauiFilterV0](https://github.com/solenee/ProjetDLP_maui/blob/v00/mauilibrary/src/main/java/fr/unantes/uima/mauilibrary/refactoring/MauiFilterV0.java) class).
  
Note that the use of a thesaurus (controlled vocabulary) is not yet supported.
  
Using TestFunctionalCorrectness JUnit test, you can check by
yourself that for the chosen parameters, our encapsulation and Maui
native library yield the same results.
  
In addition, we allow to read the training (resp. test) set of documents 
in a customed way if you don't want to store the documents as txt 
files in the same directory. You can design your own CollectionReader to
perform the reading task as long as it respects the following requirements :
  * set the JCas document language
  * set the JCas document text : for a given document, it should be the 
  text to analyze (we want to process one document per JCas)
  * create a FileDescription annotation per document to analyze, 
  initialize its "fileName" field with the file ID and its "absolutePath"
  field with the file path necessary to find the corresponding .key file
  (don't mention the .key extension; you might leave the field empty
  if you don't provide any manual topics file), index the annotation
  
This will ensure that the rest of each pipeline works correctly.
TestInputDocuments JUnit test is an example for extracting keyphrases 
from a web page. 
  
We provide a method in MauiPipelines to use a customed CollectionReader
in the extraction pipeline. 
  
  
Warnings
--------
Please do not use the field "absolutePath" of FileDescription 
annotation after its processing by the analysis engine 
ManualTopicsReaderV0.
  

Contacts
--------

     o If you want to be informed about new code releases and bug fixes
       please subscribe to the repository

