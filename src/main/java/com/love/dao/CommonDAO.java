package com.love.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.hisupplier.commons.util.StringUtil;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.love.entity.UpdateMap;

public class CommonDAO extends SqlMapClientDaoSupport {

	public int update(UpdateMap updateMap) {
		return this.getSqlMapClientTemplate().update("common.update", updateMap);
	}
	
	public int update(final List<UpdateMap> updateMapList) {
		return (Integer) this.getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for (UpdateMap updateMap : updateMapList) {
					executor.update("common.update", updateMap);
				}
				return executor.executeBatch();
			}
		});
	}

	public int delete(UpdateMap updateMap) {
		return this.getSqlMapClientTemplate().update("common.delete", updateMap);
	}
	
	public String find(UpdateMap updateMap) {
		return (String) this.getSqlMapClientTemplate().queryForObject("common.find", updateMap);
	}

	public int findForInt(UpdateMap updateMap) {
		String result = find(updateMap);
		return StringUtil.toInt(result, -1);
	}
	
	@Autowired
	public void setRankSqlMapClient(SqlMapClient sqlMapClient_rank){
		this.getSqlMapClientTemplate().setSqlMapClient(sqlMapClient_rank);
	}
}
