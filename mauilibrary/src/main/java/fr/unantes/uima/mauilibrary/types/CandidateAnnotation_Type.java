
/* First created by JCasGen Sun Dec 06 15:07:52 CET 2015 */
package fr.unantes.uima.mauilibrary.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sun Dec 06 15:07:52 CET 2015
 * @generated */
public class CandidateAnnotation_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (CandidateAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = CandidateAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new CandidateAnnotation(addr, CandidateAnnotation_Type.this);
  			   CandidateAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new CandidateAnnotation(addr, CandidateAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = CandidateAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
 
  /** @generated */
  final Feature casFeat_Frequency;
  /** @generated */
  final int     casFeatCode_Frequency;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getFrequency(int addr) {
        if (featOkTst && casFeat_Frequency == null)
      jcas.throwFeatMissing("Frequency", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_Frequency);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFrequency(int addr, double v) {
        if (featOkTst && casFeat_Frequency == null)
      jcas.throwFeatMissing("Frequency", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_Frequency, v);}
    
  
 
  /** @generated */
  final Feature casFeat_FirstOccurence;
  /** @generated */
  final int     casFeatCode_FirstOccurence;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getFirstOccurence(int addr) {
        if (featOkTst && casFeat_FirstOccurence == null)
      jcas.throwFeatMissing("FirstOccurence", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_FirstOccurence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFirstOccurence(int addr, double v) {
        if (featOkTst && casFeat_FirstOccurence == null)
      jcas.throwFeatMissing("FirstOccurence", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_FirstOccurence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_BestFullForm;
  /** @generated */
  final int     casFeatCode_BestFullForm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getBestFullForm(int addr) {
        if (featOkTst && casFeat_BestFullForm == null)
      jcas.throwFeatMissing("BestFullForm", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_BestFullForm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setBestFullForm(int addr, String v) {
        if (featOkTst && casFeat_BestFullForm == null)
      jcas.throwFeatMissing("BestFullForm", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_BestFullForm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Name;
  /** @generated */
  final int     casFeatCode_Name;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getName(int addr) {
        if (featOkTst && casFeat_Name == null)
      jcas.throwFeatMissing("Name", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Name);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setName(int addr, String v) {
        if (featOkTst && casFeat_Name == null)
      jcas.throwFeatMissing("Name", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Name, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Score;
  /** @generated */
  final int     casFeatCode_Score;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getScore(int addr) {
        if (featOkTst && casFeat_Score == null)
      jcas.throwFeatMissing("Score", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_Score);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setScore(int addr, double v) {
        if (featOkTst && casFeat_Score == null)
      jcas.throwFeatMissing("Score", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_Score, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public CandidateAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Frequency = jcas.getRequiredFeatureDE(casType, "Frequency", "uima.cas.Double", featOkTst);
    casFeatCode_Frequency  = (null == casFeat_Frequency) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Frequency).getCode();

 
    casFeat_FirstOccurence = jcas.getRequiredFeatureDE(casType, "FirstOccurence", "uima.cas.Double", featOkTst);
    casFeatCode_FirstOccurence  = (null == casFeat_FirstOccurence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_FirstOccurence).getCode();

 
    casFeat_BestFullForm = jcas.getRequiredFeatureDE(casType, "BestFullForm", "uima.cas.String", featOkTst);
    casFeatCode_BestFullForm  = (null == casFeat_BestFullForm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_BestFullForm).getCode();

 
    casFeat_Name = jcas.getRequiredFeatureDE(casType, "Name", "uima.cas.String", featOkTst);
    casFeatCode_Name  = (null == casFeat_Name) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Name).getCode();

 
    casFeat_Score = jcas.getRequiredFeatureDE(casType, "Score", "uima.cas.Double", featOkTst);
    casFeatCode_Score  = (null == casFeat_Score) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Score).getCode();

  }
}



    