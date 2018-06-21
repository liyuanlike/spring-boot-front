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
package com.github.service;

import com.github.mapper.SystemConfigMapper;
import com.github.model.SystemConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SystemConfigService {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    /** 添加 */
    public void add(SystemConfig systemConfig) {
        this.systemConfigMapper.add(systemConfig);
    }
    /** 删除 */
    @CacheEvict(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#id)")
    public void delete(Integer id) {
        this.systemConfigMapper.delete(id);
    }
    /** 修改 */
    @CachePut(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#systemConfig.id)")
    public SystemConfig update(SystemConfig systemConfig) {
        this.systemConfigMapper.update(systemConfig);
        return systemConfig;
    }
    /** 查看 - 从Cache中获取对象 */
    @Cacheable(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#id)", unless="#result eq null")
    public SystemConfig get(Integer id) {
        return this.systemConfigMapper.get(id);
    }
    /** 获取列表 */
    public List<SystemConfig> getList() {
        return this.systemConfigMapper.getList();
    }
    /** 获取条件列表 */
    public List<SystemConfig> getSystemConfigList(SystemConfig systemConfig) {
        return this.systemConfigMapper.getSystemConfigList(systemConfig);
    }
    /* --------------------------------------------------- */
}

