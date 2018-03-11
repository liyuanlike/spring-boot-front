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

import org.apache.commons.lang3.builder.*;

public class Photo {
	
	//alias
	public static final String TABLE_ALIAS = "Photo";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TYPE_ID = "类型主键";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_AUTHOR = "author";
	public static final String ALIAS_ORDER_NUM = "排序";
	public static final String ALIAS_THUMBNAIL = "缩略图";
	public static final String ALIAS_PATH = "图片地址";
	public static final String ALIAS_CREATE_TIME = "时间";
	public static final String ALIAS_DESCRIPTION = "描述";
	
	
	//columns START
	/** 主键   db_column: id */ 	
	private Integer id;
	/** 类型主键   db_column: type_id */ 	
	private Integer typeId;
	/** 名称   db_column: name */ 	
	private String name;
	/** author   db_column: author */ 	
	private String author;
	/** 排序   db_column: order_num */ 	
	private Integer orderNum;
	/** 缩略图   db_column: thumbnail */ 	
	private String thumbnail;
	/** 图片地址   db_column: path */ 	
	private String path;
	/** 时间   db_column: create_time */ 	
	private java.util.Date createTime;
	/** 描述   db_column: description */ 	
	private String description;
	//columns END

	public Photo(){
	}

	public Photo(
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
	public void setTypeId(Integer value) {
		this.typeId = value;
	}
	public Integer getTypeId() {
		return this.typeId;
	}
	public void setName(String value) {
		this.name = value;
	}
	public String getName() {
		return this.name;
	}
	public void setAuthor(String value) {
		this.author = value;
	}
	public String getAuthor() {
		return this.author;
	}
	public void setOrderNum(Integer value) {
		this.orderNum = value;
	}
	public Integer getOrderNum() {
		return this.orderNum;
	}
	public void setThumbnail(String value) {
		this.thumbnail = value;
	}
	public String getThumbnail() {
		return this.thumbnail;
	}
	public void setPath(String value) {
		this.path = value;
	}
	public String getPath() {
		return this.path;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
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
		if(obj instanceof Photo == false) return false;
		if(this == obj) return true;
		Photo other = (Photo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

