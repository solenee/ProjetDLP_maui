

/* First created by JCasGen Fri Nov 20 17:47:30 CET 2015 */
package fr.unantes.uima.mauilibrary.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Dec 01 10:56:56 CET 2015
 * XML source: C:/Users/Solène/Desktop/UIMAproject/ProjetDLP_maui/mauilibrary/src/main/resources/desc/types/CandidateAnnotation.xml
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
   
    
  //*--------------*
  //* Feature: Lemme

  /** getter for Lemme - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLemme() {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_Lemme == null)
      jcasType.jcas.throwFeatMissing("Lemme", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_Lemme);}
    
  /** setter for Lemme - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLemme(String v) {
    if (CandidateAnnotation_Type.featOkTst && ((CandidateAnnotation_Type)jcasType).casFeat_Lemme == null)
      jcasType.jcas.throwFeatMissing("Lemme", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((CandidateAnnotation_Type)jcasType).casFeatCode_Lemme, v);}    
  }

    