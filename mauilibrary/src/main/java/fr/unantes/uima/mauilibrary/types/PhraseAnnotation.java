

/* First created by JCasGen Thu Dec 03 17:04:09 CET 2015 */
package fr.unantes.uima.mauilibrary.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Dec 03 17:31:29 CET 2015
 * XML source: C:/Users/Solène/Desktop/UIMAproject/ProjetDLP_maui/mauilibrary/src/main/resources/desc/types/PhraseAnnotation.xml
 * @generated */
public class PhraseAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PhraseAnnotation.class);
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
  protected PhraseAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public PhraseAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public PhraseAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public PhraseAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: Stem

  /** getter for Stem - gets 
   * @generated
   * @return value of the feature 
   */
  public String getStem() {
    if (PhraseAnnotation_Type.featOkTst && ((PhraseAnnotation_Type)jcasType).casFeat_Stem == null)
      jcasType.jcas.throwFeatMissing("Stem", "fr.unantes.uima.mauilibrary.types.PhraseAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PhraseAnnotation_Type)jcasType).casFeatCode_Stem);}
    
  /** setter for Stem - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setStem(String v) {
    if (PhraseAnnotation_Type.featOkTst && ((PhraseAnnotation_Type)jcasType).casFeat_Stem == null)
      jcasType.jcas.throwFeatMissing("Stem", "fr.unantes.uima.mauilibrary.types.PhraseAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((PhraseAnnotation_Type)jcasType).casFeatCode_Stem, v);}    
   
    
  //*--------------*
  //* Feature: FirstOccurence

  /** getter for FirstOccurence - gets 
   * @generated
   * @return value of the feature 
   */
  public double getFirstOccurence() {
    if (PhraseAnnotation_Type.featOkTst && ((PhraseAnnotation_Type)jcasType).casFeat_FirstOccurence == null)
      jcasType.jcas.throwFeatMissing("FirstOccurence", "fr.unantes.uima.mauilibrary.types.PhraseAnnotation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((PhraseAnnotation_Type)jcasType).casFeatCode_FirstOccurence);}
    
  /** setter for FirstOccurence - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFirstOccurence(double v) {
    if (PhraseAnnotation_Type.featOkTst && ((PhraseAnnotation_Type)jcasType).casFeat_FirstOccurence == null)
      jcasType.jcas.throwFeatMissing("FirstOccurence", "fr.unantes.uima.mauilibrary.types.PhraseAnnotation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((PhraseAnnotation_Type)jcasType).casFeatCode_FirstOccurence, v);}    
  }

    