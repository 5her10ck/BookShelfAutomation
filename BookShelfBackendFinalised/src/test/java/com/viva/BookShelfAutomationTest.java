package com.viva;

//import org.junit.jupiter.api.Test;

//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class BookShelfAutomationTest {
//
//	@Test
//	void contextLoads() {
//	}
//
//}

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viva.entity.Book;
import com.viva.entity.Member;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookShelfAutomationTest {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getBookTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/books").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Book[] books = om.readValue(resultContent, Book[].class);
		assertTrue(books.length > 0);
	}
	
	@Test
	public void getMemberTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/members/5").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Member member = om.readValue(resultContent, Member.class);
		assertTrue(member!=null);
	}
	
//	@Test
//	public void getUssdTest() throws Exception {
//		MvcResult result = mockMvc
//				.perform(get("/findallUssdfiles").content(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(status().isOk()).andReturn();
//		String resultContent = result.getResponse().getContentAsString();
//		//Response response = om.readValue(resultContent, Response.class);
//		  UssdFiles[] ussdlist = om.readValue(resultContent,  UssdFiles[].class); 
//		assertTrue(ussdlist.length > 0);
//
//	}

	/*
	 * @Test public void getUserTest() throws Exception { MvcResult result = mockMvc
	 * .perform(get("/findallUssdfiles").content(MediaType.APPLICATION_JSON_VALUE))
	 * .andExpect(status().isOk()).andReturn(); String resultContent =
	 * result.getResponse().getContentAsString(); //Response response =
	 * om.readValue(resultContent, Response.class); User[] userlist =
	 * om.readValue(resultContent, User[].class); assertTrue(userlist.length > 0);
	 * 
	 * }
	 */
	/*
	 * @Test public void getUserTest() throws Exception { MvcResult result = mockMvc
	 * .perform(get("/findalluser").content(MediaType.APPLICATION_JSON_VALUE))
	 * .andExpect(status().isOk()).andReturn(); String resultContent =
	 * result.getResponse().getContentAsString(); //Response response =
	 * om.readValue(resultContent, Response.class); User[] user =
	 * om.readValue(resultContent, User[].class); assertTrue(user.length > 0);
	 * 
	 * }
	 */

//	@Test
//	public void addloginTest() throws Exception {
//		FileData f= new FileData();
//		
//		
//		f.setFname("temp.txt");
//		
//		FileContent[] data= new FileContent[] {new FileContent("abc")};
//		f.setData(data);
//	    
//	   
//		String jsonRequest = om.writeValueAsString(f);
//		MvcResult result = mockMvc.perform(post("/updatefile").content(jsonRequest)
//				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
//		String resultContent = result.getResponse().getContentAsString();
//		
//		//Response response = om.readValue(resultContent, Response.class);
//       //Assert.assertTrue(response.isStatus() == Boolean.TRUE);
//		assertEquals("",resultContent);
//	}

	/*
	 * @Test public void createProduct() throws Exception { String uri =
	 * "/updatefile"; TextFile textfile= new TextFile();
	 * textfile.setFile_name("doc.txt");
	 * textfile.setFile_path("C:\\Users\\comviva\\Desktop\\files");
	 * 
	 * String inputJson = om.writeValueAsString(textfile); MvcResult mvcResult =
	 * mockMvc.perform(MockMvcRequestBuilders.post(uri)
	 * .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn(
	 * );
	 * 
	 * int status = mvcResult.getResponse().getStatus(); //assertEquals(201,
	 * status); String content = mvcResult.getResponse().getContentAsString();
	 * assertEquals(content, "Product is created successfully"); }
	 */
}
