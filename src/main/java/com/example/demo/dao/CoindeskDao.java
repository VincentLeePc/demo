package com.example.demo.dao;

import java.sql.Types;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Coindesk;




@Repository
public class CoindeskDao {
	/** 限制最大筆數 */
	private static final int C_LIMIT = 500;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CoindeskDao.class);
    
    /** 批次寫入DB */
    public void batchUpdate(List<Coindesk> coindesks) {
    	
    	if (coindesks == null || coindesks.isEmpty()) {
    		return;
    	}
    	for (Coindesk coindesk: coindesks) {
    		Coindesk oldCoindesk = readByKey(coindesk.getCode());
    		if (oldCoindesk == null) {
    			//無舊資料, insert
    			add(coindesk);
    		} else {
    			//有舊資料, update
    			update(coindesk);
    		}
    		
    	}
    	
    }
    
    /**
     * 根據pk讀取一筆Coindesk, 若找不到會回傳null
     * @author Vincent Lee
     * @since 2022-11-01
     */
    public Coindesk readByKey(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;// 先處理錯誤狀況
        }
        StringBuffer sql = new StringBuffer("SELECT * FROM COINDESK where CODE = ? ");

        // 查詢結果為List裏面放Map, Map的key即為DB欄位名稱
        Object[] args = { code };
        int[] argTypes = { Types.VARCHAR };
        // logger.debug("findByKey SQL: "+sql.toString()+", args:"+args);
        Coindesk result = null;
        List<Coindesk> list = null;
        list = jdbcTemplate.query(sql.toString(), args, argTypes, new BeanPropertyRowMapper<>(Coindesk.class));
        if (list != null && !list.isEmpty()) {
        	logger.debug("readByKey size: "+list.size());
            result = list.get(0);// 應該只有一筆
        }
        return result;
    }
    
    /**
     * 讀取全部COINDESK
     * @author Vincent Lee
     * @since 2022-11-01
     */
    public List<Coindesk> read() {
        StringBuffer sql = new StringBuffer("SELECT * FROM COINDESK WHERE ROWNUM <= " + C_LIMIT);
        sql.append(" order by CODE");
        logger.debug("read SQL: " + sql.toString());
        List<Coindesk> list = null;
        list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Coindesk.class));

        return list;
    }
    

    /**
     * 新增一筆COINDESK
     * @author Vincent Lee
     * @since 2022-11-01
     */
    public void add(Coindesk coindesk) {
    	//insert
		 StringBuffer sql = new StringBuffer("insert into COINDESK");
		 sql.append("(CREATE_TIME, UPDATE_TIME, CODE, SYMBOL, RATE, DESCRIPTION, RATE_FLOAT) ");
		 sql.append("values (?,?,?,?,?,?,?)");
		 logger.debug("insert["+coindesk.getCode()+"] SQL: " + sql);
		 // Insert PP_ROLE
		 jdbcTemplate.update(sql.toString(),
				 new Object[] { coindesk.getCreateTime(), coindesk.getUpdateTime(), coindesk.getCode()
						 , coindesk.getSymbol(), coindesk.getRate(), coindesk.getDescription(), coindesk.getRateFloat()});
    }
    
    /**
     * 異動一筆COINDESK
     * @author Vincent Lee
     * @since 2022-11-01
     */
    public void update(Coindesk coindesk) {
    	String sql = "update COINDESK set UPDATE_TIME=?, SYMBOL=?, RATE=?, DESCRIPTION=?, RATE_FLOAT=? where CODE=?";
		logger.debug("update["+coindesk.getCode()+"] SQL: " + sql);
		jdbcTemplate.update(sql, new Object[] { coindesk.getUpdateTime(), coindesk.getSymbol(),
				coindesk.getRate(), coindesk.getDescription(), coindesk.getRateFloat(), coindesk.getCode() });
    }
    
    /**
     * 刪除一筆COINDESK
     * @author Vincent Lee
     * @since 2022-11-01
     */
    public int delete(String code) {
    	String sql = "delete COINDESK where CODE=?";
		logger.debug("delete["+code+"] SQL: " + sql);
		return jdbcTemplate.update(sql, new Object[] { code });
    }
}
