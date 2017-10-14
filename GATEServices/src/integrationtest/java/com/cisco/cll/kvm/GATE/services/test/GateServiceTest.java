package com.cisco.cll.kvm.GATE.services.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.cisco.ccl.kvm.GATE.services.web.ServicesConfiguration;
//import com.cisco.ccl.kvm.GATE.services.web.WebConfiguration;
import com.cisco.cll.kvm.GATE.services.config.WebConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value="")
@ContextConfiguration(classes = { WebConfiguration.class, ServicesConfiguration.class  })
public final class GateServiceTest {

    @Autowired
    private volatile WebApplicationContext webApplicationContext;

    private volatile MockMvc mockMvc;

    @Before
    public void before() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    
    @Test
    public void testAnnotationTextService() throws Exception {
  
    	MockMultipartFile file = new MockMultipartFile("file", "orig", null, "java".getBytes());
    	String res =  this.mockMvc.perform(fileUpload("/showcase/annotations/to-file").file(file)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    		      
         System.err.println(res);
      
      
    }
    
    @Test
    public void testTopicsService() throws Exception {
        
    	MockMultipartFile file = new MockMultipartFile("file", "orig", null, "java".getBytes());
    	String res =  this.mockMvc.perform(fileUpload("/showcase/topics/from-file").file(file)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
   		      
        System.err.println(res);
     
      }
    
  
    @Test
    public void testAnnotationsToText() throws Exception {
        String expected = "{\"text\":\"data networking\"}";
    	String res =  this.mockMvc.perform(post("/showcase/annotations/to-text").param("text","data networking")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    	assertEquals(expected,res);	      
    }
    
   
    @Test
    public void testTopicsFromText() throws Exception {
        String res =  this.mockMvc.perform(post("/showcase/topics/from-text").param("text","programming")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    	assertNotNull(res);
    }
    
    
    
    
}
