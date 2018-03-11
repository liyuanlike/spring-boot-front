package com.github.util;

import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.sql.SQLUtils;
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
}


