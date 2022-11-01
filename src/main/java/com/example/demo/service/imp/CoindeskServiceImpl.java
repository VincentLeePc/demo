package com.example.demo.service.imp;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CoindeskDao;
import com.example.demo.model.Coindesk;
import com.example.demo.model.req.CoindeskReqModelBean;
import com.example.demo.model.res.CoindeskMainResModelBean;
import com.example.demo.model.res.CoindeskResModelBean;
import com.example.demo.model.res.MsgResModelBean;
import com.example.demo.service.CoindeskService;
import com.example.demo.service.OuterService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoindeskServiceImpl implements CoindeskService{
	
	private static final Logger log = LoggerFactory.getLogger(CoindeskServiceImpl.class);
	@Autowired
	private OuterService outerService;
	@Autowired
	private CoindeskDao coindeskDao;
	
	/** 讀取遠端資料寫入DB */
	public MsgResModelBean importCoinData() throws Exception{
		MsgResModelBean result = new MsgResModelBean();
		String str = outerService.getData();
		//轉碼 &pound; -> £
		str = StringEscapeUtils.unescapeHtml(str);
		log.info("importCoinData, str: " + str);
		CoindeskMainResModelBean coindeskMain = new ObjectMapper().readValue(str, CoindeskMainResModelBean.class);
		
		if (coindeskMain != null ) {
			coindeskMain.print();
			List<Coindesk> coindesks = new ArrayList<Coindesk>();
			//USD
			Coindesk usd = new Coindesk();
			BeanUtils.copyProperties(coindeskMain.getBpi().getUsd(), usd);
			usd.setCreateTime(coindeskMain.getTime().getDt());
			usd.setUpdateTime(coindeskMain.getTime().getDt());
			coindesks.add(usd);
			//GBP
			Coindesk gbp = new Coindesk();
			BeanUtils.copyProperties(coindeskMain.getBpi().getGbp(), gbp);
			gbp.setCreateTime(coindeskMain.getTime().getDt());
			gbp.setUpdateTime(coindeskMain.getTime().getDt());
			coindesks.add(gbp);
			//EUR
			Coindesk eur = new Coindesk();
			BeanUtils.copyProperties(coindeskMain.getBpi().getEur(), eur);
			eur.setCreateTime(coindeskMain.getTime().getDt());
			eur.setUpdateTime(coindeskMain.getTime().getDt());
			coindesks.add(eur);
			//寫入DB
			coindeskDao.batchUpdate(coindesks);
			log.info("coindeskDao.batchUpdate done!");
			result.setMsg("轉入完成");
			return result;
		}
		result.setMsg("無資料可轉入");
		return result;
	}
	
	/** 查詢全部幣別資料 */
	public List<CoindeskResModelBean> getCoinDatas() throws Exception {
		List<Coindesk> coindesks = coindeskDao.read();
		List<CoindeskResModelBean> result = new ArrayList<CoindeskResModelBean>();
		if (coindesks != null) {
			for (Coindesk coindesk :coindesks) {
				CoindeskResModelBean coindeskResModelBean = new CoindeskResModelBean();
				BeanUtils.copyProperties(coindesk, coindeskResModelBean);
				result.add(coindeskResModelBean);
			}
		}
		return result;
	}
	
	/** 讀取一筆幣別資料 */
	public CoindeskResModelBean getCoinData(CoindeskReqModelBean coindeskReqModelBean) throws Exception {
		log.info("getCoinData code:" + coindeskReqModelBean.getCode());
		Coindesk coindesk = coindeskDao.readByKey(coindeskReqModelBean.getCode());
		CoindeskResModelBean coindeskResModelBean = new CoindeskResModelBean();
		if (coindesk != null) {
			BeanUtils.copyProperties(coindesk, coindeskResModelBean);
		}
		return coindeskResModelBean;
	}
	
	/** 新增一筆幣別資料 */
	public MsgResModelBean addCoinData(CoindeskReqModelBean coindeskReqModelBean) throws Exception {
		MsgResModelBean result = new MsgResModelBean();
		Coindesk oldCoindesk = coindeskDao.readByKey(coindeskReqModelBean.getCode());
		if (oldCoindesk != null) {
			result.setMsg("新增失敗, 已有舊資料");
			return result;
		} else {
			Coindesk coindesk = new Coindesk();
			BeanUtils.copyProperties(coindeskReqModelBean, coindesk);
			coindeskDao.add(coindesk);
		}
		result.setMsg("新增完成");
		return result;
	}
	
	/** 異動一筆幣別資料 */
	public MsgResModelBean updateCoinData(CoindeskReqModelBean coindeskReqModelBean) throws Exception {
		MsgResModelBean result = new MsgResModelBean();
		Coindesk oldCoindesk = coindeskDao.readByKey(coindeskReqModelBean.getCode());
		if (oldCoindesk != null) {
			Coindesk coindesk = new Coindesk();
			BeanUtils.copyProperties(coindeskReqModelBean, coindesk);
			coindeskDao.update(coindesk);
		} else {
			result.setMsg("異動失敗, 無舊資料");
			return result;
		}
		result.setMsg("異動完成");
		return result;
	}
	
	/** 刪除一筆幣別資料 */
	public MsgResModelBean deleteCoinData(CoindeskReqModelBean coindeskReqModelBean) throws Exception {
		MsgResModelBean result = new MsgResModelBean();
		int count = coindeskDao.delete(coindeskReqModelBean.getCode());
		result.setMsg("刪除:" + count + "筆");
		return result;
	}
}
