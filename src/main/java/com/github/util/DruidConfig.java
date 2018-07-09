package com.github.util;

import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.alibaba.druid.filter.config.ConfigTools.decrypt;
import static com.alibaba.druid.filter.config.ConfigTools.encrypt;
import static com.alibaba.druid.filter.config.ConfigTools.genKeyPair;

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

	/**
	 * 加解密
	 * */
	public static void main(String[] args) throws Exception {
		String password = "iMiracle";
		String[] arr = genKeyPair(512);
		System.out.println("privateKey:" + arr[0]);
		System.out.println("publicKey:" + arr[1]);
		System.out.println("password:" + encrypt(arr[0], password));
		System.out.println("password:" + decrypt(arr[1], encrypt(arr[0], password)));


		String DEFAULT_PRIVATE_KEY_STRING = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAocbCrurZGbC5GArEHKlAfDSZi7gFBnd4yxOt0rwTqKBFzGyhtQLu5PRKjEiOXVa95aeIIBJ6OhC2f8FjqFUpawIDAQABAkAPejKaBYHrwUqUEEOe8lpnB6lBAsQIUFnQI/vXU4MV+MhIzW0BLVZCiarIQqUXeOhThVWXKFt8GxCykrrUsQ6BAiEA4vMVxEHBovz1di3aozzFvSMdsjTcYRRo82hS5Ru2/OECIQC2fAPoXixVTVY7bNMeuxCP4954ZkXp7fEPDINCjcQDywIgcc8XLkkPcs3Jxk7uYofaXaPbg39wuJpEmzPIxi3k0OECIGubmdpOnin3HuCP/bbjbJLNNoUdGiEmFL5hDI4UdwAdAiEAtcAwbm08bKN7pwwvyqaCBC//VnEWaq39DCzxr+Z2EIk=";
		String DEFAULT_PUBLIC_KEY_STRING = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKHGwq7q2RmwuRgKxBypQHw0mYu4BQZ3eMsTrdK8E6igRcxsobUC7uT0SoxIjl1WveWniCASejoQtn/BY6hVKWsCAwEAAQ==";
		arr[0] = DEFAULT_PRIVATE_KEY_STRING;
		arr[1] = DEFAULT_PUBLIC_KEY_STRING;
		System.out.println("privateKey:" + arr[0]);
		System.out.println("publicKey:" + arr[1]);
		System.out.println("password:" + encrypt(arr[0], password));
		System.out.println("password:" + decrypt(arr[1], encrypt(arr[0], password)));
	}
}


