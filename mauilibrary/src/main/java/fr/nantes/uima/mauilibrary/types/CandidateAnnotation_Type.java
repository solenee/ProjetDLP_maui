
/* First created by JCasGen Fri Nov 20 16:48:46 CET 2015 */
package fr.nantes.uima.mauilibrary.types;

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
 * Updated by JCasGen Fri Nov 20 16:48:46 CET 2015
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
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("fr.nantes.uima.mauilibrary.types.CandidateAnnotation");
 
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
      jcas.throwFeatMissing("Frequency", "fr.nantes.uima.mauilibrary.types.CandidateAnnotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_Frequency);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFrequency(int addr, double v) {
        if (featOkTst && casFeat_Frequency == null)
      jcas.throwFeatMissing("Frequency", "fr.nantes.uima.mauilibrary.types.CandidateAnnotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_Frequency, v);}
    
  



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

  }
}



    