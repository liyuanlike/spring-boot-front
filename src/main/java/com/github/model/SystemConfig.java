/*
 * ............................................. 
 * 
 * 				    _ooOoo_ 
 * 		  	       o8888888o 
 * 	  	  	       88" . "88 
 *                 (| -_- |) 
 *                  O\ = /O 
 *              ____/`---*\____ 
 *               . * \\| |// `. 
 *             / \\||| : |||// \ 
 *           / _||||| -:- |||||- \ 
 *             | | \\\ - /// | | 
 *            | \_| **\---/** | | 
 *           \  .-\__ `-` ___/-. / 
 *            ___`. .* /--.--\ `. . __ 
 *        ."" *< `.___\_<|>_/___.* >*"". 
 *      | | : `- \`.;`\ _ /`;.`/ - ` : | | 
 *         \ \ `-. \_ __\ /__ _/ .-` / / 
 *======`-.____`-.___\_____/___.-`____.-*====== 
 * 
 * ............................................. 
 *              佛祖保佑 永无BUG 
 *
 * 佛曰: 
 * 写字楼里写字间，写字间里程序员； 
 * 程序人员写程序，又拿程序换酒钱。 
 * 酒醒只在网上坐，酒醉还来网下眠； 
 * 酒醉酒醒日复日，网上网下年复年。 
 * 但愿老死电脑间，不愿鞠躬老板前； 
 * 奔驰宝马贵者趣，公交自行程序员。 
 * 别人笑我忒疯癫，我笑自己命太贱； 
 * 不见满街漂亮妹，哪个归得程序员？
 *
 * 北纬30.√  154518484@qq.com
 */
package com.github.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SystemConfig {
	
	//alias
	public static final String TABLE_ALIAS = "SystemConfig";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_CODE = "代码";
	public static final String ALIAS_VALUE = "值";
	public static final String ALIAS_DESCRIPTION = "描述";
	
	
	//columns START
	/** id   db_column: id */ 	
	private Integer id;
	/** 名称   db_column: name */ 	
	private String name;
	/** 代码   db_column: code */ 	
	private String code;
	/** 值   db_column: value */ 	
	private String value;
	/** 描述   db_column: description */ 	
	private String description;
	//columns END

	public SystemConfig(){
	}

	public SystemConfig(
		Integer id
	){
		this.id = id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public Integer getId() {
		return this.id;
	}
	public void setName(String value) {
		this.name = value;
	}
	public String getName() {
		return this.name;
	}
	public void setCode(String value) {
		this.code = value;
	}
	public String getCode() {
		return this.code;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
	public void setDescription(String value) {
		this.description = value;
	}
	public String getDescription() {
		return this.description;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SystemConfig == false) return false;
		if(this == obj) return true;
		SystemConfig other = (SystemConfig)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

