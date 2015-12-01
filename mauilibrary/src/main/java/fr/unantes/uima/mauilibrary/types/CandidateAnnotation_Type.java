
/* First created by JCasGen Fri Nov 20 17:47:30 CET 2015 */
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
 * Updated by JCasGen Tue Dec 01 10:56:56 CET 2015
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
    
  
 
  /** @generated */
  final Feature casFeat_Lemme;
  /** @generated */
  final int     casFeatCode_Lemme;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLemme(int addr) {
        if (featOkTst && casFeat_Lemme == null)
      jcas.throwFeatMissing("Lemme", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Lemme);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLemme(int addr, String v) {
        if (featOkTst && casFeat_Lemme == null)
      jcas.throwFeatMissing("Lemme", "fr.unantes.uima.mauilibrary.types.CandidateAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Lemme, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public CandidateAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Score = jcas.getRequiredFeatureDE(casType, "Score", "uima.cas.Double", featOkTst);
    casFeatCode_Score  = (null == casFeat_Score) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Score).getCode();

 
    casFeat_Lemme = jcas.getRequiredFeatureDE(casType, "Lemme", "uima.cas.String", featOkTst);
    casFeatCode_Lemme  = (null == casFeat_Lemme) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Lemme).getCode();

  }
}



    