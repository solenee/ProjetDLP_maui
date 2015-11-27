

/* First created by JCasGen Fri Nov 20 17:47:37 CET 2015 */
package fr.unantes.uima.mauilibrary.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Nov 20 19:18:00 CET 2015
 * XML source: /comptes/E15D861C/Cours_ATAL/ProjetDLP_maui/mauilibrary/desc/types/TopicAnnotation.xml
 * @generated */
public class TopicAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TopicAnnotation.class);
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
  protected TopicAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public TopicAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public TopicAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public TopicAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: Text

  /** getter for Text - gets 
   * @generated
   * @return value of the feature 
   */
  public String getText() {
    if (TopicAnnotation_Type.featOkTst && ((TopicAnnotation_Type)jcasType).casFeat_Text == null)
      jcasType.jcas.throwFeatMissing("Text", "fr.unantes.uima.mauilibrary.types.TopicAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TopicAnnotation_Type)jcasType).casFeatCode_Text);}
    
  /** setter for Text - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setText(String v) {
    if (TopicAnnotation_Type.featOkTst && ((TopicAnnotation_Type)jcasType).casFeat_Text == null)
      jcasType.jcas.throwFeatMissing("Text", "fr.unantes.uima.mauilibrary.types.TopicAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TopicAnnotation_Type)jcasType).casFeatCode_Text, v);}    
   
    
  //*--------------*
  //* Feature: Score

  /** getter for Score - gets 
   * @generated
   * @return value of the feature 
   */
  public double getScore() {
    if (TopicAnnotation_Type.featOkTst && ((TopicAnnotation_Type)jcasType).casFeat_Score == null)
      jcasType.jcas.throwFeatMissing("Score", "fr.unantes.uima.mauilibrary.types.TopicAnnotation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((TopicAnnotation_Type)jcasType).casFeatCode_Score);}
    
  /** setter for Score - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setScore(double v) {
    if (TopicAnnotation_Type.featOkTst && ((TopicAnnotation_Type)jcasType).casFeat_Score == null)
      jcasType.jcas.throwFeatMissing("Score", "fr.unantes.uima.mauilibrary.types.TopicAnnotation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((TopicAnnotation_Type)jcasType).casFeatCode_Score, v);}    
  }

    