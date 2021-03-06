/* First created by JCasGen Wed Jan 21 11:21:05 GMT 2015 */
// Dstl (c) Crown Copyright 2017
package uk.gov.dstl.baleen.types.common;

import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;

import uk.gov.dstl.baleen.types.semantic.Entity_Type;

/**
 * Nationality denonym (e.g. French, British, Spanish) Updated by JCasGen Wed Apr 13 13:23:16 BST
 * 2016
 *
 * @generated
 */
public class Nationality_Type extends Entity_Type {
  /** @generated */
  @SuppressWarnings("hiding")
  public static final int typeIndexID = Nationality.typeIndexID;
  /**
   * @generated
   * @modifiable
   */
  @SuppressWarnings("hiding")
  public static final boolean featOkTst =
      JCasRegistry.getFeatOkTst("uk.gov.dstl.baleen.types.common.Nationality");

  /** @generated */
  final Feature casFeat_countryCode;
  /** @generated */
  final int casFeatCode_countryCode;
  /**
   * @generated
   * @param addr low level Feature Structure reference
   * @return the feature value
   */
  public String getCountryCode(int addr) {
    if (featOkTst && casFeat_countryCode == null)
      jcas.throwFeatMissing("countryCode", "uk.gov.dstl.baleen.types.common.Nationality");
    return ll_cas.ll_getStringValue(addr, casFeatCode_countryCode);
  }
  /**
   * @generated
   * @param addr low level Feature Structure reference
   * @param v value to set
   */
  public void setCountryCode(int addr, String v) {
    if (featOkTst && casFeat_countryCode == null)
      jcas.throwFeatMissing("countryCode", "uk.gov.dstl.baleen.types.common.Nationality");
    ll_cas.ll_setStringValue(addr, casFeatCode_countryCode, v);
  }

  /**
   * initialize variables to correspond with Cas Type and Features
   *
   * @generated
   * @param jcas JCas
   * @param casType Type
   */
  public Nationality_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl) this.casType, getFSGenerator());

    casFeat_countryCode =
        jcas.getRequiredFeatureDE(casType, "countryCode", "uima.cas.String", featOkTst);
    casFeatCode_countryCode =
        (null == casFeat_countryCode)
            ? JCas.INVALID_FEATURE_CODE
            : ((FeatureImpl) casFeat_countryCode).getCode();
  }
}
