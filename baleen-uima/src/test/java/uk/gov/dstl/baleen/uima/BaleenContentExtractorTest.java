// Dstl (c) Crown Copyright 2017
package uk.gov.dstl.baleen.uima;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.UimaContext;
import org.apache.uima.fit.factory.UimaContextFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.Resource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.impl.CustomResourceSpecifier_impl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.collect.ImmutableMap;

import uk.gov.dstl.baleen.core.pipelines.PipelineBuilder;
import uk.gov.dstl.baleen.types.metadata.Metadata;
import uk.gov.dstl.baleen.uima.testing.JCasSingleton;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BaleenContentExtractorTest {

  private static final String VALUE = "value";

  private static final String KEY = "key";

  private static final String PIPELINE_NAME = "testPipeline";

  @Mock UimaSupport support;

  @Mock UimaMonitor monitor;

  UimaContext context;

  JCas jCas;

  Annotation annotation;

  @Before
  public void setUp() throws UIMAException {
    jCas = JCasSingleton.getJCasInstance();
    annotation = new Annotation(jCas);
    context = UimaContextFactory.createUimaContext(PipelineBuilder.PIPELINE_NAME, PIPELINE_NAME);
  }

  @Test
  public void testDestroy() throws ResourceInitializationException {
    FakeBaleenContentExtractor annotator = new FakeBaleenContentExtractor();
    annotator.initialize(
        new CustomResourceSpecifier_impl(), ImmutableMap.of(Resource.PARAM_UIMA_CONTEXT, context));
    annotator.destroy();
    assertTrue(annotator.destroyed);
  }

  @Test
  public void testDoInitialize() throws ResourceInitializationException {
    FakeBaleenContentExtractor annotator = new FakeBaleenContentExtractor();
    annotator.initialize(
        new CustomResourceSpecifier_impl(), ImmutableMap.of(Resource.PARAM_UIMA_CONTEXT, context));
    assertTrue(annotator.initialised);
  }

  @Test
  public void testProcess() throws Exception {
    FakeBaleenContentExtractor annotator = new FakeBaleenContentExtractor();
    annotator.initialize(
        new CustomResourceSpecifier_impl(), ImmutableMap.of(Resource.PARAM_UIMA_CONTEXT, context));
    annotator.processStream(new ByteArrayInputStream("Hello World".getBytes()), "test", jCas);
    assertTrue(annotator.processed);
  }

  @Test
  public void testGetMonitor() throws ResourceInitializationException {
    FakeBaleenContentExtractor annotator = new FakeBaleenContentExtractor();
    annotator.initialize(
        new CustomResourceSpecifier_impl(), ImmutableMap.of(Resource.PARAM_UIMA_CONTEXT, context));
    assertNotNull(annotator.getMonitor());
    assertEquals(PIPELINE_NAME, annotator.getMonitor().getPipelineName());
  }

  @Test
  public void testGetSupport() throws ResourceInitializationException {
    FakeBaleenContentExtractor annotator = new FakeBaleenContentExtractor();
    annotator.initialize(
        new CustomResourceSpecifier_impl(), ImmutableMap.of(Resource.PARAM_UIMA_CONTEXT, context));
    assertNotNull(annotator.getSupport());
    assertEquals(PIPELINE_NAME, annotator.getSupport().getPipelineName());
  }

  @Test
  public void testSupport() throws ResourceInitializationException {
    FakeBaleenContentExtractor annotator = new MockedBaleenContentExtractor();
    annotator.initialize(
        new CustomResourceSpecifier_impl(), ImmutableMap.of(Resource.PARAM_UIMA_CONTEXT, context));

    List<Annotation> list = Collections.singletonList(annotation);

    annotator.addToJCasIndex(annotation);
    verify(support, only()).add(annotation);
    resetMocked();

    annotator.addToJCasIndex(list);
    verify(support, only()).add(list);
    resetMocked();

    annotator.getDocumentAnnotation(jCas);
    resetMocked();
  }

  @Test
  public void testAddMetadata() throws ResourceInitializationException {
    FakeBaleenContentExtractor annotator = new MockedBaleenContentExtractor();
    annotator.initialize(
        new CustomResourceSpecifier_impl(), ImmutableMap.of(Resource.PARAM_UIMA_CONTEXT, context));

    Metadata md = annotator.addMetadata(jCas, KEY, VALUE);
    verify(support).add(md);

    assertNull(annotator.addMetadata(jCas, "", VALUE));
    assertNull(annotator.addMetadata(jCas, null, VALUE));
    assertNull(annotator.addMetadata(jCas, KEY, ""));
    assertNull(annotator.addMetadata(jCas, KEY, null));

    resetMocked();
  }

  private void resetMocked() {
    reset(support, monitor);
  }

  private class MockedBaleenContentExtractor extends FakeBaleenContentExtractor {

    @Override
    protected UimaMonitor createMonitor(String pipelineName) {
      return monitor;
    }

    @Override
    protected UimaSupport createSupport(String pipelineName, UimaContext context) {
      return support;
    }
  }
}
