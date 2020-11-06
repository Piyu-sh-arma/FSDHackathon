package com.iiht.StockMarket.functionalTestCases;

import static com.iiht.StockMarket.utilTestClass.TestUtils.businessTestFile;
import static com.iiht.StockMarket.utilTestClass.TestUtils.currentTest;
import static com.iiht.StockMarket.utilTestClass.TestUtils.yakshaAssert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.iiht.StockMarket.controller.CompanyInfoController;
import com.iiht.StockMarket.controller.StockPriceController;
import com.iiht.StockMarket.dto.CompanyDetailsDTO;
import com.iiht.StockMarket.dto.StockPriceDetailsDTO;
import com.iiht.StockMarket.dto.StockPriceIndexDTO;
import com.iiht.StockMarket.services.CompanyInfoService;
import com.iiht.StockMarket.services.StockMarketService;
import com.iiht.StockMarket.utilTestClass.MasterData;

// @ExtendWith(MockitoExtension.class)
// @MockitoSettings(strictness = Strictness.LENIENT)
@WebMvcTest({CompanyInfoController.class, StockPriceController.class})
@RunWith(SpringRunner.class)
public class TestController {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CompanyInfoService companyService;

	@MockBean
	private StockMarketService stockMarketService;

	/*@InjectMocks
	private CompanyInfoController companyInfoController;

	@InjectMocks
	private StockPriceController stockPriceController;*/

	// ------------------------------------------------------------------------------------------------------
	/*
	 * Description : This test is to perform setup for Mockito initiations
	 */
	@Before public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	//===========================================================================================================================
	//				I - Testing CompanyInfoController Rest End Points
	//===========================================================================================================================
	//				1. Testing Rest End Point - /company/addCompany
	//-- Test 1 : addCompany ----------------------------------------------------------------------------------------------------
	/*
	 * Description : This test is to perform add new company in the Stock Market Application
	 */
	@Test 
	public void testAddCompany() throws Exception 
	{ 
        CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
	
        Mockito.when(companyService.saveCompanyDetails(companyDto)).thenReturn(companyDto);
		
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/addCompany")
				.content(MasterData.asJsonString(companyDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
        System.out.println("Response : " + result.getResponse().getContentAsString());
		System.out.println("Expected : " + MasterData.asJsonString(companyDto));
		yakshaAssert(currentTest(),	result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(companyDto))? true : false, businessTestFile);
	}
	//-- BDD Test : addCompany --------------------------------------------------------------------------------------------------
	@Test
	public void testAddCompanyBDD() throws Exception 
	{
		final int count[] = new int[1];
		
        CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		
		Mockito.when(companyService.saveCompanyDetails(companyDto)).then(new Answer<CompanyDetailsDTO>() {
			@Override
			public CompanyDetailsDTO answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("Called : testAddCompanyBDD");
				count[0]++;
				return companyDto;
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/addCompany")
				.content(MasterData.asJsonString(companyDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);	
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	//---------------------------------------------------------------------------------------------------------------------------
	//				2. Testing Rest End Point - /company/deleteCompany/{id}
	//-- Test 1 : deleteCompany -------------------------------------------------------------------------------------------------
	@Test
	public void testDeleteCompany() throws Exception
	{
        CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		Long companyCode = companyDto.getCompanyCode();
		
		Mockito.when(companyService.deleteCompany(companyCode)).thenReturn(companyDto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/deleteCompany/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);				
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		yakshaAssert(currentTest(),	result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(companyDto))? true : false, businessTestFile);
	}
	//-- BDD Test : deleteCompany -----------------------------------------------------------------------------------------------
	@Test
	public void testDeleteCompanyBDD() throws Exception 
	{
		final int count[] = new int[1];
	
        CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		Long companyCode = companyDto.getCompanyCode();
		
		Mockito.when(companyService.deleteCompany(companyCode)).then(new Answer<CompanyDetailsDTO>() {
			@Override
			public CompanyDetailsDTO answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("Called : testDeleteCompanyBDD");
				count[0]++;
				return MasterData.getCompanyDetailsDTO();
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/deleteCompany/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse().getContentAsString());
		System.out.println(count[0]);	
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	//				3. Testing Rest End Point - /company/getCompanyInfoById/{companyCode}
	//-- Test 1 : getCompanyInfoById --------------------------------------------------------------------------------------------
	@Test
	public void testFindCompanyInfoById() throws Exception
	{
        CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		Long companyCode = companyDto.getCompanyCode();
		
		Mockito.when(companyService.getCompanyInfoById(companyCode)).thenReturn(companyDto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/getCompanyInfoById/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		yakshaAssert(currentTest(),	result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(companyDto))? true : false, businessTestFile);		
	}
	//-- BDD Test : getCompanyInfoById ------------------------------------------------------------------------------------------
	@Test
	public void testFindCompanyInfoByIdBDD() throws Exception 
	{
		final int count[] = new int[1];
		
        CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		Long companyCode = companyDto.getCompanyCode();
		
		Mockito.when(companyService.getCompanyInfoById(companyCode)).then(new Answer<CompanyDetailsDTO>() {
			@Override
			public CompanyDetailsDTO answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("Called : testFindCompanyInfoByIdBDD");
				count[0]++;
				return MasterData.getCompanyDetailsDTO();
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/getCompanyInfoById/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);			
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	//				4. Testing Rest End Point - /company/getAllCompanies
	//-- Test 1 : getAllCompanies -----------------------------------------------------------------------------------------------
	/*
	 * Description : This test is to perform view all the companies from database
	 */
	@Test 
	public void testFindAllCompanies() throws Exception 
	{ 
		List<CompanyDetailsDTO> list = new ArrayList<CompanyDetailsDTO>();
		list.add(MasterData.getCompanyDetailsDTO());
		
		Mockito.when(companyService.getAllCompanies()).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/getAllCompanies")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(MasterData.asJsonString(list));
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(list))? "true" : "false"),	businessTestFile);
	}
	//-- BDD Test : getAllCompaniesBDD ------------------------------------------------------------------------------------------
	@Test
	public void testFindAllCompaniesBDD() throws Exception 
	{
		final int count[] = new int[1];
		
		List<CompanyDetailsDTO> list = new ArrayList<CompanyDetailsDTO>();
		list.add(MasterData.getCompanyDetailsDTO());		
		
		Mockito.when(companyService.getAllCompanies()).then(new Answer<List<CompanyDetailsDTO>>() {
			@Override
			public List<CompanyDetailsDTO> answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("Called : testFindAllCompaniesBDD");
				count[0]++;
				return list;
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/getAllCompanies")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}
	
	//===========================================================================================================================
	//				II - Testing StockPriceController Rest End Points
	//===========================================================================================================================
	//				1. Testing Rest End Point - /stock/addStock
	//-- Test 1 : addStock ------------------------------------------------------------------------------------------------------
	/*
	 * Description : This test is to perform add new StockPriceDetails in the Stock Market Application
	 */
	@Test 
	public void testAddStock() throws Exception 
	{ 
        StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
	
        Mockito.when(stockMarketService.saveStockPriceDetails(stockDto)).thenReturn(stockDto);
		
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/addStock")
				.content(MasterData.asJsonString(stockDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
        System.out.println(result.getResponse().getContentAsString());
		System.out.println(stockDto);
		yakshaAssert(currentTest(),	result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(stockDto))? true : false, businessTestFile);
	}
	//-- BDD Test : addCompany --------------------------------------------------------------------------------------------------
	@Test
	public void testAddStockBDD() throws Exception
	{
		final int count[] = new int[1];
		
        StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
		
		Mockito.when(stockMarketService.saveStockPriceDetails(stockDto)).then(new Answer<StockPriceDetailsDTO>() {
			@Override
			public StockPriceDetailsDTO answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("Called : testAddStockBDD");
				count[0]++;
				return stockDto;
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/addStock")
				.content(MasterData.asJsonString(stockDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);	
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}	
	
	//---------------------------------------------------------------------------------------------------------------------------
	//				2. Testing Rest End Point - /stock/deleteStock/{id}
	//-- Test 1 : deleteStock ---------------------------------------------------------------------------------------------------
	@Test
	public void testDeleteStock() throws Exception
	{
        StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
        Long companyCode = stockDto.getCompanyCode();

		List<StockPriceDetailsDTO> stockList = new ArrayList<StockPriceDetailsDTO>();
        
		Mockito.when(stockMarketService.deleteStock(companyCode)).thenReturn(stockList);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/stock/deleteStock/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);				
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		yakshaAssert(currentTest(),	result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(stockDto))? true : false, businessTestFile);
	}
	//-- BDD Test : deleteStock --------------------------------------------------------------------------------------------------
	@Test
	public void testDeleteStockBDD() throws Exception 
	{
		final int count[] = new int[1];
	
        StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
        Long companyCode = stockDto.getCompanyCode();

		List<StockPriceDetailsDTO> stockList = new ArrayList<StockPriceDetailsDTO>();
		
		Mockito.when(stockMarketService.deleteStock(companyCode)).then(new Answer<List<StockPriceDetailsDTO>>() {
			@Override
			public List<StockPriceDetailsDTO> answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("Called : testDeleteStockBDD");
				count[0]++;
				return stockList;
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/stock/deleteStock/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse().getContentAsString());
		System.out.println(count[0]);	
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	//				3. Testing Rest End Point - /stock/getStockInfoById/{companyCode}
	//-- Test 1 : getStockInfoById ----------------------------------------------------------------------------------------------
	@Test
	public void testFindStockByCompanyCode() throws Exception
	{
        StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
        Long companyCode = stockDto.getCompanyCode();
        
		List<StockPriceDetailsDTO> stockList = new ArrayList<StockPriceDetailsDTO>();

		Mockito.when(stockMarketService.getStockByCode(companyCode)).thenReturn(stockList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/getStockByCompanyCode/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),	result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(stockDto))? true : false, businessTestFile);		
	}
	//-- BDD Test : getStockByCompanyCode ---------------------------------------------------------------------------------------
	@Test
	public void testFindStockByCompanyCodeBDD() throws Exception 
	{
		final int count[] = new int[1];
		
        StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
        Long companyCode = stockDto.getCompanyCode();

		List<StockPriceDetailsDTO> stockList = new ArrayList<StockPriceDetailsDTO>();
        
		Mockito.when(stockMarketService.getStockByCode(companyCode)).then(new Answer<List<StockPriceDetailsDTO>>() {
			@Override
			public List<StockPriceDetailsDTO> answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("Called : testFindStockByCompanyCodeBDD");
				count[0]++;
				return stockList;
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/getStockByCompanyCode/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);			
	}	
	
	//---------------------------------------------------------------------------------------------------------------------------
	//				5. Testing Rest End Point - /stock/getStockPriceIndex
	//-- Test 1 : getStockPriceIndex --------------------------------------------------------------------------------------------
	/*
	 * Description : This test is to perform view the StockPriceIndex from database
	 */
	@Test 
	public void testStockPriceIndex() throws Exception 
	{ 
        StockPriceIndexDTO stockPriceIndexDto = MasterData.getStockPriceIndexDTO();
        
        CompanyDetailsDTO companyDetailDTO = stockPriceIndexDto.getCompanyDto();
        Long companyCode = companyDetailDTO.getCompanyCode();
        
        List<StockPriceDetailsDTO> stockPDDTOList = stockPriceIndexDto.getStockPriceList();
        StockPriceDetailsDTO spDetails1 = stockPDDTOList.get(0);
        StockPriceDetailsDTO spDetails2 = stockPDDTOList.get(1);
        
        LocalDate startDate = spDetails1.getStockPriceDate();
        LocalDate endDate   = spDetails2.getStockPriceDate();

        StockPriceIndexDTO stockPriceIndexDTO = new StockPriceIndexDTO();
        
		Mockito.when(stockMarketService.getStockPriceIndex(companyCode, startDate, endDate)).thenReturn(stockPriceIndexDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/getStockPriceIndex/"+companyCode+"/"+startDate+"/"+endDate)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),	result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(stockPriceIndexDto))? true : false, businessTestFile);		
	}
	//-- BDD Test : getStockPriceIndex ------------------------------------------------------------------------------------------
	@Test
	public void testStockPriceIndexBDD() throws Exception 
	{
		final int count[] = new int[1];
	
        StockPriceIndexDTO stockPriceIndexDTO = new StockPriceIndexDTO();
        
        StockPriceIndexDTO stockPriceDto = MasterData.getStockPriceIndexDTO();
        
        CompanyDetailsDTO companyDetailDTO = stockPriceDto.getCompanyDto();
        Long companyCode = companyDetailDTO.getCompanyCode();
        
        List<StockPriceDetailsDTO> stockPDDTOList = stockPriceDto.getStockPriceList();
        
        StockPriceDetailsDTO spDetails1 = stockPDDTOList.get(0);
        StockPriceDetailsDTO spDetails2 = stockPDDTOList.get(1);
        
        LocalDate startDate = spDetails1.getStockPriceDate();
        LocalDate endDate   = spDetails2.getStockPriceDate();

		Mockito.when(stockMarketService.getStockPriceIndex(companyCode, startDate, endDate)).then(new Answer<StockPriceIndexDTO>() {
			@Override
			public StockPriceIndexDTO answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("Called : testStockPriceIndexBDD");
				count[0]++;
				return stockPriceIndexDTO;
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/stock/getStockPriceIndex/"+companyCode+"/"+startDate+"/"+endDate)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse().getContentAsString());
		System.out.println(count[0]);	
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}	
}