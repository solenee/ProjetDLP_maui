

/* First created by JCasGen Sun Dec 06 15:07:52 CET 2015 */
package fr.unantes.uima.mauilibrary.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Dec 07 16:29:47 CET 2015
 * XML source: C:/Users/Solène/Desktop/UIMAproject/ProjetDLP_maui/mauilibrary/desc/types/CandidateAnnotation.xml
 * @generated */
public class CandidateAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(CandidateAnnotation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected CandidateAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public CandidateAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public CandidateAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public CandidateAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: Frequency

  /** getter for Frequency - gets 
   * @generated
   * @return value of the feature 
   */
  public double getFrequency() {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_Frequency == null)
      jcasType.jcas.throwFeatMissing("Frequency", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_Frequency);}
    
  /** setter for Frequency - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFrequency(double v) {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_Frequency == null)
      jcasType.jcas.throwFeatMissing("Frequency", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_Frequency, v);}    
   
    
  //*--------------*
  //* Feature: FirstOccurence

  /** getter for FirstOccurence - gets 
   * @generated
   * @return value of the feature 
   */
  public double getFirstOccurence() {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_FirstOccurence == null)
      jcasType.jcas.throwFeatMissing("FirstOccurence", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_FirstOccurence);}
    
  /** setter for FirstOccurence - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFirstOccurence(double v) {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_FirstOccurence == null)
      jcasType.jcas.throwFeatMissing("FirstOccurence", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_FirstOccurence, v);}    
   
    
  //*--------------*
  //* Feature: BestFullForm

  /** getter for BestFullForm - gets 
   * @generated
   * @return value of the feature 
   */
  public String getBestFullForm() {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_BestFullForm == null)
      jcasType.jcas.throwFeatMissing("BestFullForm", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_BestFullForm);}
    
  /** setter for BestFullForm - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setBestFullForm(String v) {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_BestFullForm == null)
      jcasType.jcas.throwFeatMissing("BestFullForm", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_BestFullForm, v);}    
   
    
  //*--------------*
  //* Feature: Name

  /** getter for Name - gets 
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_Name == null)
      jcasType.jcas.throwFeatMissing("Name", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_Name);}
    
  /** setter for Name - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_Name == null)
      jcasType.jcas.throwFeatMissing("Name", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_Name, v);}    
   
    
  //*--------------*
  //* Feature: Score

  /** getter for Score - gets 
   * @generated
   * @return value of the feature 
   */
  public double getScore() {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_Score == null)
      jcasType.jcas.throwFeatMissing("Score", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_Score);}
    
  /** setter for Score - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setScore(double v) {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_Score == null)
      jcasType.jcas.throwFeatMissing("Score", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_Score, v);}    
  }

    