
/* First created by JCasGen Fri Nov 20 16:45:56 CET 2015 */
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
 * Updated by JCasGen Fri Nov 20 16:45:56 CET 2015
 * @generated */
public class TopicAnnotation_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TopicAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TopicAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TopicAnnotation(addr, TopicAnnotation_Type.this);
  			   TopicAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TopicAnnotation(addr, TopicAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TopicAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("fr.nantes.uima.mauilibrary.types.TopicAnnotation");
 
  /** @generated */
  final Feature casFeat_Value;
  /** @generated */
  final int     casFeatCode_Value;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getValue(int addr) {
        if (featOkTst && casFeat_Value == null)
      jcas.throwFeatMissing("Value", "fr.nantes.uima.mauilibrary.types.TopicAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Value);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setValue(int addr, String v) {
        if (featOkTst && casFeat_Value == null)
      jcas.throwFeatMissing("Value", "fr.nantes.uima.mauilibrary.types.TopicAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Value, v);}
    
  
 
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
      jcas.throwFeatMissing("Score", "fr.nantes.uima.mauilibrary.types.TopicAnnotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_Score);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setScore(int addr, double v) {
        if (featOkTst && casFeat_Score == null)
      jcas.throwFeatMissing("Score", "fr.nantes.uima.mauilibrary.types.TopicAnnotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_Score, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public TopicAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Value = jcas.getRequiredFeatureDE(casType, "Value", "uima.cas.String", featOkTst);
    casFeatCode_Value  = (null == casFeat_Value) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Value).getCode();

 
    casFeat_Score = jcas.getRequiredFeatureDE(casType, "Score", "uima.cas.Double", featOkTst);
    casFeatCode_Score  = (null == casFeat_Score) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Score).getCode();

  }
}



    