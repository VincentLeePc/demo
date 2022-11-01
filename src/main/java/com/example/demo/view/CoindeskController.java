package com.example.demo.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.req.CoindeskReqModelBean;
import com.example.demo.model.res.CoindeskResModelBean;
import com.example.demo.model.res.MsgResModelBean;
import com.example.demo.service.CoindeskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "幣別查詢")
@RestController
@RequestMapping("/coin")
public class CoindeskController {
	//private static final Logger log = LoggerFactory.getLogger(CoindeskController.class);
	
	@Autowired
	private CoindeskService coindeskService;
	
	@RequestMapping(value = "/01", method = RequestMethod.POST)
	@Operation(summary = "轉入幣別資料", description = "轉入幣別資料")
	public MsgResModelBean doImport() throws Exception {
		return coindeskService.importCoinData();
	}
	
	@RequestMapping(value = "/02", method = RequestMethod.POST)
	@Operation(summary = "查詢全部幣別資料", description = "查詢全部幣別資料")
	public List<CoindeskResModelBean> doGetAll() throws Exception {
		return coindeskService.getCoinDatas();
	}
	
	@PostMapping("/03")
	@Operation(summary = "新增一筆幣別資料", description = "新增一筆幣別資料")
	public MsgResModelBean doAdd(@RequestBody CoindeskReqModelBean model) throws Exception {
		return coindeskService.addCoinData(model);
	}
	
	@PostMapping("/04")
	@Operation(summary = "異動一筆幣別資料", description = "異動一筆幣別資料")
	public MsgResModelBean doUpdate(@RequestBody CoindeskReqModelBean model) throws Exception {
		return coindeskService.updateCoinData(model);
	}
	
	@PostMapping("/05")
	@Operation(summary = "刪除一筆幣別資料", description = "刪除一筆幣別資料")
	public MsgResModelBean doDelete(@RequestBody CoindeskReqModelBean model) throws Exception {
		return coindeskService.deleteCoinData(model);
	}
	
	@RequestMapping(value = "/06", method = RequestMethod.POST)
	@Operation(summary = "讀取一筆幣別資料", description = "讀取一筆幣別資料")
	@ResponseBody
	public CoindeskResModelBean doGetOne(@RequestBody CoindeskReqModelBean model) throws Exception {
		return coindeskService.getCoinData(model);
	}
}
