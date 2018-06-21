package com.github.util;

import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

	/**
	 * more configure see {@link LogFilter}  {@link com.alibaba.druid.spring.boot.autoconfigure.stat.DruidFilterConfiguration}
	 * Ethan 154518484@qq.com
	 * */
	@Bean
	public Slf4jLogFilter logFilter() {

		Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
		slf4jLogFilter.setConnectionLogEnabled(false);
		slf4jLogFilter.setResultSetLogEnabled(false);

		slf4jLogFilter.setStatementCreateAfterLogEnabled(false);
		slf4jLogFilter.setStatementPrepareAfterLogEnabled(false);
		slf4jLogFilter.setStatementPrepareCallAfterLogEnabled(false);
		slf4jLogFilter.setStatementParameterSetLogEnabled(false);
		slf4jLogFilter.setStatementExecuteAfterLogEnabled(false);
		slf4jLogFilter.setStatementCloseAfterLogEnabled(false);

		slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
		slf4jLogFilter.setStatementSqlFormatOption(new SQLUtils.FormatOption(true, true));

		return slf4jLogFilter;
	}

	/**
	 * SQL 防火墙配置, 防止SQL注入
	 * */
	@Bean
	public WallFilter wallFilter() {

		WallConfig wallConfig = new WallConfig();
		wallConfig.setMultiStatementAllow(true); //允许一次执行多条语句
		wallConfig.setNoneBaseStatementAllow(true); //允许非基本语句的其他语句
		wallConfig.setDeleteWhereNoneCheck(true);
		wallConfig.setUpdateWhereNoneCheck(true);

		WallFilter wallFilter = new WallFilter();
		wallFilter.setConfig(wallConfig);

		return wallFilter;
	}
}


