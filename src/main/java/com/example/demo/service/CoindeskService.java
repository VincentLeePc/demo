package com.example.demo.service;

import java.util.List;

import com.example.demo.model.req.CoindeskReqModelBean;
import com.example.demo.model.res.CoindeskResModelBean;
import com.example.demo.model.res.MsgResModelBean;

public interface CoindeskService {
	/** 讀取遠端資料寫入DB */
	MsgResModelBean importCoinData() throws Exception;
	
	/** 查詢全部幣別資料 */
	List<CoindeskResModelBean> getCoinDatas() throws Exception;
	
	/** 查詢一筆幣別資料 */
	CoindeskResModelBean getCoinData(CoindeskReqModelBean coindeskReqModelBean) throws Exception;
	
	/** 新增一筆幣別資料 */
	MsgResModelBean addCoinData(CoindeskReqModelBean coindeskReqModelBean) throws Exception;
	
	/** 異動一筆幣別資料 */
	MsgResModelBean updateCoinData(CoindeskReqModelBean coindeskReqModelBean) throws Exception;
	
	/** 刪除一筆幣別資料 */
	MsgResModelBean deleteCoinData(CoindeskReqModelBean coindeskReqModelBean) throws Exception;
}
