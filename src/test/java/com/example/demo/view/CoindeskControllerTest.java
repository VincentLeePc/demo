package com.example.demo.view;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolverWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.DemoApplication;
import com.example.demo.model.req.CoindeskReqModelBean;

import lombok.Getter;

@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = DemoApplication.class)
public class CoindeskControllerTest {
	private static final Logger log = LoggerFactory.getLogger(CoindeskController.class);
    /**
     * UTF-8 字串
     */
    private static final String UTF_8 = StandardCharsets.UTF_8.toString();
    /**
     * URI 分隔符號"/"
     */
    private static final String URI_SEPARATOR = "/";
    /** Mock MVC */
    @Getter
    @Autowired
    private MockMvc mockMvc;
    
 
    @Test
    @Order(1)
    @DisplayName(value = "01 轉入幣別資料")
	public void doImport() {
		try {
			String uri = getURI("coin", "01");
			ResultActions resultActions = mockMvc.perform(getMockHttpServletRequestBuilderForPost(uri, null))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(jsonPath("$.length()", greaterThan(0)));
	
	        String resContent = resultActions.andReturn().getResponse().getContentAsString();
	        log.debug(resContent);
		} catch (Exception e) {
			log.error("doImport Exception", e);
		}
	}
    
    @Test
    @Order(2)
    @DisplayName(value = "05 刪除一筆幣別資料")
	public void doDelete() {
    	CoindeskReqModelBean model = new CoindeskReqModelBean();
    	model.setCode("USD");
		try {			
			String uri = getURI("coin", "05");
			ResultActions resultActions = mockMvc.perform(getMockHttpServletRequestBuilderForPost(uri, model))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(jsonPath("$.length()", greaterThan(0)));
			log.info("jsonPath" + jsonPath("$.length()"));
	        String resContent = resultActions.andReturn().getResponse().getContentAsString();
	        log.debug(resContent);
		} catch (Exception e) {
			log.error("doDelete Exception", e);
		}
	}
    
    @Test
    @Order(3)
    @DisplayName(value = "03 新增一筆幣別資料")
	public void doAdd() {
    	CoindeskReqModelBean model = new CoindeskReqModelBean();
    	model.setCode("USD");
    	model.setSymbol("$");
    	model.setCreateTime("20221101015000");
    	model.setUpdateTime("20221101110900");
    	model.setDescription("United States Dollar");
    	model.setRate("20,543.2800");
    	model.setRateFloat(new BigDecimal(20543.28));
		try {			
			String uri = getURI("coin", "03");
			ResultActions resultActions = mockMvc.perform(getMockHttpServletRequestBuilderForPost(uri, model))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(jsonPath("$.length()", greaterThan(0)));
	
	        String resContent = resultActions.andReturn().getResponse().getContentAsString();
	        log.debug(resContent);
		} catch (Exception e) {
			log.error("doImport Exception", e);
		}
	}
    
    @Test
    @Order(2)
    @DisplayName(value = "06 讀取一筆幣別資料")
	public void doGetOne() {
    	CoindeskReqModelBean model = new CoindeskReqModelBean();
    	model.setCode("USD");
		try {			
			String uri = getURI("coin", "06");
			ResultActions resultActions = mockMvc.perform(getMockHttpServletRequestBuilderForPost(uri, model))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(jsonPath("$.length()", greaterThan(0)));
			log.info("jsonPath" + jsonPath("$.length()"));
	        String resContent = resultActions.andReturn().getResponse().getContentAsString();
	        log.debug(resContent);
		} catch (Exception e) {
			log.error("doGetOne Exception", e);
		}
	}
    
    @Test
    @Order(4)
    @DisplayName(value = "04 異動一筆幣別資料")
	public void doUpdate() {
    	CoindeskReqModelBean model = new CoindeskReqModelBean();
    	model.setCode("USD");
    	model.setSymbol("$");
    	model.setCreateTime("20221101015000");
    	model.setUpdateTime("20221101223300");
    	model.setDescription("United States Dollar");
    	model.setRate("20,543.2800");
    	model.setRateFloat(new BigDecimal(20543.28));
		try {			
			String uri = getURI("coin", "04");
			ResultActions resultActions = mockMvc.perform(getMockHttpServletRequestBuilderForPost(uri, model))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(jsonPath("$.length()", greaterThan(0)));
	
	        String resContent = resultActions.andReturn().getResponse().getContentAsString();
	        log.debug(resContent);
		} catch (Exception e) {
			log.error("doUpdate Exception", e);
		}
	}
    
    @Test
    @Order(5)
    @DisplayName(value = "02 查詢幣別資料")
	public void doGetAll() {
		try {
			log.debug("mockMvc: " +mockMvc);
			String uri = getURI("coin", "02");
			ResultActions resultActions = mockMvc.perform(getMockHttpServletRequestBuilderForPost(uri, null))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(jsonPath("$.length()", greaterThan(0)));
	
	        String resContent = resultActions.andReturn().getResponse().getContentAsString();
	        log.debug(resContent);
		} catch (Exception e) {
			log.error("doGetAll Exception", e);
		}
	}
	
    /**
     * 在每個測試方法運行之前執行
     * @remark
     */
    @BeforeEach
    public void beforeEach() {
        try {
            // 主動觸發設定 KeycloakSpringBootProperties
            doKeycloakSpringBootConfigResolverWrapper();
        } catch (Exception e) {
        	log.error("beforeEach Exception", e);
        }
    }
	
    /**
     * @remark 依moduleName & actionCode 取得URI, 格式:/{moduleName}/{actionCode}
     */
    public static String getURI(String moduleName, String actionCode) {
        return URI_SEPARATOR + moduleName + URI_SEPARATOR + actionCode;
    }
    
    /**
     * @remark 依 uri & request object 取得URI, 產生 MockHttpServletRequestBuilder
     */
    public static MockHttpServletRequestBuilder getMockHttpServletRequestBuilderForPost(String uri, Object obj) {
    	JSONObject json;
    	if (obj!=null) {
    		json = new JSONObject(obj);
    	} else {
    		json = new JSONObject();
    	}
        return MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(json.toString());
    }
    
    /**
     * @remark 主動觸發設定 KeycloakSpringBootProperties
     */
    public static void doKeycloakSpringBootConfigResolverWrapper() {
        new KeycloakSpringBootConfigResolverWrapper();
    }
}
