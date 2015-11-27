
/* First created by JCasGen Fri Nov 20 18:14:06 CET 2015 */
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

/** Manual topic stored in .key file
 * Updated by JCasGen Fri Nov 20 18:14:33 CET 2015
 * @generated */
public class ManualTopic_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ManualTopic_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ManualTopic_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ManualTopic(addr, ManualTopic_Type.this);
  			   ManualTopic_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ManualTopic(addr, ManualTopic_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = ManualTopic.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("fr.unantes.uima.mauilibrary.types.ManualTopic");
 
  /** @generated */
  final Feature casFeat_FileName;
  /** @generated */
  final int     casFeatCode_FileName;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getFileName(int addr) {
        if (featOkTst && casFeat_FileName == null)
      jcas.throwFeatMissing("FileName", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    return ll_cas.ll_getStringValue(addr, casFeatCode_FileName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFileName(int addr, String v) {
        if (featOkTst && casFeat_FileName == null)
      jcas.throwFeatMissing("FileName", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    ll_cas.ll_setStringValue(addr, casFeatCode_FileName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_AbsolutePath;
  /** @generated */
  final int     casFeatCode_AbsolutePath;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAbsolutePath(int addr) {
        if (featOkTst && casFeat_AbsolutePath == null)
      jcas.throwFeatMissing("AbsolutePath", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    return ll_cas.ll_getStringValue(addr, casFeatCode_AbsolutePath);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAbsolutePath(int addr, String v) {
        if (featOkTst && casFeat_AbsolutePath == null)
      jcas.throwFeatMissing("AbsolutePath", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    ll_cas.ll_setStringValue(addr, casFeatCode_AbsolutePath, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Topic;
  /** @generated */
  final int     casFeatCode_Topic;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTopic(int addr) {
        if (featOkTst && casFeat_Topic == null)
      jcas.throwFeatMissing("Topic", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Topic);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTopic(int addr, String v) {
        if (featOkTst && casFeat_Topic == null)
      jcas.throwFeatMissing("Topic", "fr.unantes.uima.mauilibrary.types.ManualTopic");
    ll_cas.ll_setStringValue(addr, casFeatCode_Topic, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public ManualTopic_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_FileName = jcas.getRequiredFeatureDE(casType, "FileName", "uima.cas.String", featOkTst);
    casFeatCode_FileName  = (null == casFeat_FileName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_FileName).getCode();

 
    casFeat_AbsolutePath = jcas.getRequiredFeatureDE(casType, "AbsolutePath", "uima.cas.String", featOkTst);
    casFeatCode_AbsolutePath  = (null == casFeat_AbsolutePath) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_AbsolutePath).getCode();

 
    casFeat_Topic = jcas.getRequiredFeatureDE(casType, "Topic", "uima.cas.String", featOkTst);
    casFeatCode_Topic  = (null == casFeat_Topic) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Topic).getCode();

  }
}



    