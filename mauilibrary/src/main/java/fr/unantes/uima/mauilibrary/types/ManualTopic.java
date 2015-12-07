

/* First created by JCasGen Mon Dec 07 16:30:06 CET 2015 */
package fr.unantes.uima.mauilibrary.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Manual topic stored in .key file
 * Updated by JCasGen Mon Dec 07 16:30:06 CET 2015
 * XML source: C:/Users/Solène/Desktop/UIMAproject/ProjetDLP_maui/mauilibrary/desc/types/ManualTopic.xml
 * @generated */
public class ManualTopic extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ManualTopic.class);
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
  protected ManualTopic() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public ManualTopic(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public ManualTopic(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public ManualTopic(JCas jcas, int begin, int end) {
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
  //* Feature: FileName

  /** getter for FileName - gets 
   * @generated
   * @return value of the feature 
   */
  public String getFileName() {
    if (ManualTopic_Type.featOkTst && ((ManualTopic_Type)jcasType).casFeat_FileName == null)
      jcasType.jcas.throwFeatMissing("FileName", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ManualTopic_Type)jcasType).casFeatCode_FileName);}
    
  /** setter for FileName - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFileName(String v) {
    if (ManualTopic_Type.featOkTst && ((ManualTopic_Type)jcasType).casFeat_FileName == null)
      jcasType.jcas.throwFeatMissing("FileName", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    jcasType.ll_cas.ll_setStringValue(addr, ((ManualTopic_Type)jcasType).casFeatCode_FileName, v);}    
   
    
  //*--------------*
  //* Feature: AbsolutePath

  /** getter for AbsolutePath - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAbsolutePath() {
    if (ManualTopic_Type.featOkTst && ((ManualTopic_Type)jcasType).casFeat_AbsolutePath == null)
      jcasType.jcas.throwFeatMissing("AbsolutePath", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ManualTopic_Type)jcasType).casFeatCode_AbsolutePath);}
    
  /** setter for AbsolutePath - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAbsolutePath(String v) {
    if (ManualTopic_Type.featOkTst && ((ManualTopic_Type)jcasType).casFeat_AbsolutePath == null)
      jcasType.jcas.throwFeatMissing("AbsolutePath", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    jcasType.ll_cas.ll_setStringValue(addr, ((ManualTopic_Type)jcasType).casFeatCode_AbsolutePath, v);}    
   
    
  //*--------------*
  //* Feature: Topic

  /** getter for Topic - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTopic() {
    if (ManualTopic_Type.featOkTst && ((ManualTopic_Type)jcasType).casFeat_Topic == null)
      jcasType.jcas.throwFeatMissing("Topic", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ManualTopic_Type)jcasType).casFeatCode_Topic);}
    
  /** setter for Topic - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTopic(String v) {
    if (ManualTopic_Type.featOkTst && ((ManualTopic_Type)jcasType).casFeat_Topic == null)
      jcasType.jcas.throwFeatMissing("Topic", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    jcasType.ll_cas.ll_setStringValue(addr, ((ManualTopic_Type)jcasType).casFeatCode_Topic, v);}    
   
    
  //*--------------*
  //* Feature: Frequency

  /** getter for Frequency - gets 
   * @generated
   * @return value of the feature 
   */
  public int getFrequency() {
    if (ManualTopic_Type.featOkTst && ((ManualTopic_Type)jcasType).casFeat_Frequency == null)
      jcasType.jcas.throwFeatMissing("Frequency", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    return jcasType.ll_cas.ll_getIntValue(addr, ((ManualTopic_Type)jcasType).casFeatCode_Frequency);}
    
  /** setter for Frequency - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFrequency(int v) {
    if (ManualTopic_Type.featOkTst && ((ManualTopic_Type)jcasType).casFeat_Frequency == null)
      jcasType.jcas.throwFeatMissing("Frequency", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    jcasType.ll_cas.ll_setIntValue(addr, ((ManualTopic_Type)jcasType).casFeatCode_Frequency, v);}    
  }

    