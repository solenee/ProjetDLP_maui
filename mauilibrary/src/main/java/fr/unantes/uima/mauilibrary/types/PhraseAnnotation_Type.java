
/* First created by JCasGen Thu Dec 03 17:04:09 CET 2015 */
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
 * Updated by JCasGen Thu Dec 03 17:31:29 CET 2015
 * @generated */
public class PhraseAnnotation_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PhraseAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PhraseAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PhraseAnnotation(addr, PhraseAnnotation_Type.this);
  			   PhraseAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PhraseAnnotation(addr, PhraseAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = PhraseAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("fr.unantes.uima.mauilibrary.types.PhraseAnnotation");
 
  /** @generated */
  final Feature casFeat_Stem;
  /** @generated */
  final int     casFeatCode_Stem;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getStem(int addr) {
        if (featOkTst && casFeat_Stem == null)
      jcas.throwFeatMissing("Stem", "fr.unantes.uima.mauilibrary.types.PhraseAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Stem);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setStem(int addr, String v) {
        if (featOkTst && casFeat_Stem == null)
      jcas.throwFeatMissing("Stem", "fr.unantes.uima.mauilibrary.types.PhraseAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Stem, v);}
    
  
 
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
      jcas.throwFeatMissing("FirstOccurence", "fr.unantes.uima.mauilibrary.types.PhraseAnnotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_FirstOccurence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFirstOccurence(int addr, double v) {
        if (featOkTst && casFeat_FirstOccurence == null)
      jcas.throwFeatMissing("FirstOccurence", "fr.unantes.uima.mauilibrary.types.PhraseAnnotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_FirstOccurence, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public PhraseAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Stem = jcas.getRequiredFeatureDE(casType, "Stem", "uima.cas.String", featOkTst);
    casFeatCode_Stem  = (null == casFeat_Stem) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Stem).getCode();

 
    casFeat_FirstOccurence = jcas.getRequiredFeatureDE(casType, "FirstOccurence", "uima.cas.Double", featOkTst);
    casFeatCode_FirstOccurence  = (null == casFeat_FirstOccurence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_FirstOccurence).getCode();

  }
}



    