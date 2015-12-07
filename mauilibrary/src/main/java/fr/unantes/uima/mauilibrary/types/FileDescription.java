

/* First created by JCasGen Mon Dec 07 16:29:27 CET 2015 */
package fr.unantes.uima.mauilibrary.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Dec 07 16:40:49 CET 2015
 * XML source: C:/Users/Solène/Desktop/UIMAproject/ProjetDLP_maui/mauilibrary/desc/types/FileDescription.xml
 * @generated */
public class FileDescription extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(FileDescription.class);
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
  protected FileDescription() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public FileDescription(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public FileDescription(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public FileDescription(JCas jcas, int begin, int end) {
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
    if (FileDescription_Type.featOkTst && ((FileDescription_Type)jcasType).casFeat_FileName == null)
      jcasType.jcas.throwFeatMissing("FileName", "fr.unantes.uima.mauilibrary.types.FileDescription");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FileDescription_Type)jcasType).casFeatCode_FileName);}
    
  /** setter for FileName - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFileName(String v) {
    if (FileDescription_Type.featOkTst && ((FileDescription_Type)jcasType).casFeat_FileName == null)
      jcasType.jcas.throwFeatMissing("FileName", "fr.unantes.uima.mauilibrary.types.FileDescription");
    jcasType.ll_cas.ll_setStringValue(addr, ((FileDescription_Type)jcasType).casFeatCode_FileName, v);}    
   
    
  //*--------------*
  //* Feature: AbsolutePath

  /** getter for AbsolutePath - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAbsolutePath() {
    if (FileDescription_Type.featOkTst && ((FileDescription_Type)jcasType).casFeat_AbsolutePath == null)
      jcasType.jcas.throwFeatMissing("AbsolutePath", "fr.unantes.uima.mauilibrary.types.FileDescription");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FileDescription_Type)jcasType).casFeatCode_AbsolutePath);}
    
  /** setter for AbsolutePath - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAbsolutePath(String v) {
    if (FileDescription_Type.featOkTst && ((FileDescription_Type)jcasType).casFeat_AbsolutePath == null)
      jcasType.jcas.throwFeatMissing("AbsolutePath", "fr.unantes.uima.mauilibrary.types.FileDescription");
    jcasType.ll_cas.ll_setStringValue(addr, ((FileDescription_Type)jcasType).casFeatCode_AbsolutePath, v);}    
   
    
  //*--------------*
  //* Feature: Id

  /** getter for Id - gets 
   * @generated
   * @return value of the feature 
   */
  public int getId() {
    if (FileDescription_Type.featOkTst && ((FileDescription_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "fr.unantes.uima.mauilibrary.types.FileDescription");
    return jcasType.ll_cas.ll_getIntValue(addr, ((FileDescription_Type)jcasType).casFeatCode_Id);}
    
  /** setter for Id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(int v) {
    if (FileDescription_Type.featOkTst && ((FileDescription_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "fr.unantes.uima.mauilibrary.types.FileDescription");
    jcasType.ll_cas.ll_setIntValue(addr, ((FileDescription_Type)jcasType).casFeatCode_Id, v);}    
   
    
  //*--------------*
  //* Feature: ManualTopics

  /** getter for ManualTopics - gets 
   * @generated
   * @return value of the feature 
   */
  public String getManualTopics() {
    if (FileDescription_Type.featOkTst && ((FileDescription_Type)jcasType).casFeat_ManualTopics == null)
      jcasType.jcas.throwFeatMissing("ManualTopics", "fr.unantes.uima.mauilibrary.types.FileDescription");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FileDescription_Type)jcasType).casFeatCode_ManualTopics);}
    
  /** setter for ManualTopics - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setManualTopics(String v) {
    if (FileDescription_Type.featOkTst && ((FileDescription_Type)jcasType).casFeat_ManualTopics == null)
      jcasType.jcas.throwFeatMissing("ManualTopics", "fr.unantes.uima.mauilibrary.types.FileDescription");
    jcasType.ll_cas.ll_setStringValue(addr, ((FileDescription_Type)jcasType).casFeatCode_ManualTopics, v);}    
  }

    