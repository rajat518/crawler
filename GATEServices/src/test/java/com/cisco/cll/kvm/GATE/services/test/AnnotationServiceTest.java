/*package com.cisco.cll.kvm.GATE.services.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.annotation.AnnotationSetImpl;
import gate.util.LanguageAnalyserDocumentProcessor;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.cisco.ccl.kvm.GATE.services.web.AnnotationService;
import com.cisco.ccl.kvm.GATE.services.web.model.Topics;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Factory.class, Document.class })
public class AnnotationServiceTest {

	@InjectMocks
	AnnotationService annotationService;

	@Mock
	LanguageAnalyserDocumentProcessor processor;
	
	@Mock
	AsyncTaskExecutor threadPoolExecutor;
	
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAnnotationsToFile() throws Exception {
		Map<String, Object> expectedJson = new HashMap<String, Object>();
		expectedJson.put("text", "java");
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "java".getBytes());
		File tempFile = File.createTempFile("tmpfile", ".tmp");
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		Mockito.doReturn(tempFile).when(annotationService).createFileOnDisk(file);
		Mockito.doReturn("java").when(annotationService).annotateTheFile(tempFile);
		ResponseEntity<Map<String, String>> response = annotationService.annotations(file);
		verify(annotationService).createFileOnDisk(file);
		verify(annotationService).annotateTheFile(tempFile);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedJson, response.getBody());

	}

	@Test
	public void testAnnotationsToText() throws Exception {
		Map<String, Object> expectedJson = new HashMap<String, Object>();
		expectedJson.put("text", "java");
		File tempFile = File.createTempFile("tmpfile", ".tmp");
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		Mockito.doReturn("java").when(annotationService).annotateTheText(any(String.class));
		ResponseEntity<Map<String, String>> response = annotationService.annotations("java");
		verify(annotationService).annotateTheText("java");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedJson, response.getBody());
	}

	@Test
	public void testTopicsFromFile() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "java".getBytes());
		File tempFile = File.createTempFile("tmpfile", ".tmp");
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		Mockito.doReturn(tempFile).when(annotationService).createFileOnDisk(file);
		Topics expectedJson = new Topics(new String[] { "java" });
		Mockito.doReturn(expectedJson).when(annotationService).getTopicsFromFile(tempFile);
		ResponseEntity<Topics> response = annotationService.topic(file);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedJson, response.getBody());
	}

	
	@Test
	public void testTopicsFromText() throws Exception {
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		Topics expectedJson = new Topics(new String[] { "java" });
		Mockito.doReturn(expectedJson).when(annotationService).getTopicsFromString(anyString());
		annotationService.setTaskExector(new SimpleAsyncTaskExecutor());
		Map<String, String> bodyContent = new HashMap<String, String>();
		bodyContent.put("text", "java");
		WebAsyncTask<ResponseEntity<Topics>> response = annotationService.topic(bodyContent,null,null);
		ResponseEntity<Topics> entity = (ResponseEntity<Topics>) response.getCallable().call();
		Topics actualBody = entity.getBody();
		String[] myArray = actualBody.getTopics();
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		Assert.assertArrayEquals(expectedJson.getTopics(), myArray);
		
		response = annotationService.topic(bodyContent,"1","1");
		entity = (ResponseEntity<Topics>) response.getCallable().call();
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	@Test
	public void testCreateFileOnDisk() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "java".getBytes());
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		Path tempPath = Paths.get("/tmp");
		if (!Files.exists(tempPath)) {
			Files.createDirectory(Paths.get("/tmp"));
		}
		annotationService.setGateFileFolder("/tmp");
		annotationService.createFileOnDisk(file);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmmptyFileOnDisk() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "".getBytes());
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		annotationService.setGateFileFolder("/tmp");
		annotationService.createFileOnDisk(file);
	}

	@Test
	public void testGetTopicsFromString() throws Exception {
		Topics expectedJson = new Topics(new String[] { "java" });
		Document document = Mockito.mock(Document.class);
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		Mockito.doReturn(document).when(annotationService).processString("java");
		Mockito.doReturn(expectedJson).when(annotationService).getTopics(document);

		annotationService.getTopicsFromString("java");

		verify(annotationService).processString("java");
		verify(annotationService).getTopics(document);

	}

	@Test
	public void testGetTopicsFromFile() throws Exception {
		File tempFile = File.createTempFile("tmpfile", ".tmp");
		Topics expectedJson = new Topics(new String[] { "java" });
		Document document = Mockito.mock(Document.class);
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		Mockito.doReturn(document).when(annotationService).processFile(tempFile);
		Mockito.doReturn(expectedJson).when(annotationService).getTopics(document);

		annotationService.getTopicsFromFile(tempFile);

		verify(annotationService).processFile(tempFile);
		verify(annotationService).getTopics(document);

	}

	@Test
	public void testAnnotateTheText() throws Exception {
		Document document = Mockito.mock(Document.class);
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		Mockito.doReturn(document).when(annotationService).processString("java");
		Mockito.doReturn("java").when(annotationService).annotateDocument(document);

		annotationService.annotateTheText("java");

		verify(annotationService).processString("java");
		verify(annotationService).annotateDocument(document);

	}

	@Test
	public void testAnnotateTheFile() throws Exception {
		File tempFile = File.createTempFile("tmpfile", ".tmp");
		Document document = Mockito.mock(Document.class);
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		Mockito.doReturn(document).when(annotationService).processFile(tempFile);
		Mockito.doReturn("java").when(annotationService).annotateDocument(document);

		annotationService.annotateTheFile(tempFile);

		verify(annotationService).processFile(tempFile);
		verify(annotationService).annotateDocument(document);

	}

	@Test
	public void testProcessString() throws Exception {
		Document document = Mockito.mock(Document.class);
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		PowerMockito.mockStatic(Factory.class);
		Mockito.when(Factory.newDocument("java")).thenReturn(document);
		Mockito.doReturn(document).when(annotationService).executeGateApp(any(Document.class));

		annotationService.processString("java");

		verify(annotationService).executeGateApp(document);

	}

	@Test
	public void testProcessFile() throws Exception {
		File tempFile = File.createTempFile("tmpfile", ".tmp");
		Document document = Mockito.mock(Document.class);
		AnnotationService annotationService = Mockito.spy(new AnnotationService());
		PowerMockito.mockStatic(Factory.class);
		Mockito.when(Factory.newDocument(any(URL.class), any(String.class))).thenReturn(document);
		Mockito.doReturn(document).when(annotationService).executeGateApp(any(Document.class));

		annotationService.processFile(tempFile);

		verify(annotationService).executeGateApp(document);

	}

	@Test
	public void testExecuteGateApp() throws Exception {

		Document document = Mockito.mock(Document.class);
		Mockito.doNothing().when(processor).processDocument(any(Document.class));

		annotationService.executeGateApp(document);

		verify(processor).processDocument(document);

	}

	@Test
	public void testAnnotateDocument() throws Exception {
		URL resourceUrl = getClass().getResource("/gate.xml");
		Path filePath = Paths.get(resourceUrl.toURI());
		Path path = filePath.getParent();
		Gate.setSiteConfigFile(filePath.toFile());
		Gate.setPluginsHome(path.toFile());
		Gate.init();

		// Annotate Document
		Document doc = Factory.newDocument("This is an annotated document Lookup on energy and Solar");
		String annotation = annotationService.annotateDocument(doc);

		// Get Topics
		doc = Factory.newDocument("This is a document");
		annotationService.getTopics(doc);
		
		FeatureMap params = Factory.newFeatureMap();
		AnnotationSetImpl annotationSet = new AnnotationSetImpl(doc);
		annotationSet.add(5L,7L,"testAnnot",params);
		
		
	}

} */