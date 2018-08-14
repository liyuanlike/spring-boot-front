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

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.github.model.Photo;
import com.github.mapper.PhotoMapper;


@Service
public class PhotoService {

    @Resource private PhotoMapper photoMapper;

    /** 添加 */
    public void add(Photo photo) {
        this.photoMapper.add(photo);
    }
    /** 删除 */
    @CacheEvict(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#id)")
    public void delete(Integer id) {
        this.photoMapper.delete(id);
    }
    /** 修改 */
    @CachePut(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#photo.id)")
    public void update(Photo photo) {
        this.photoMapper.update(photo);
    }
    /** 查看 - 从Cache中获取对象 */
    @Cacheable(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#id)")
    public Photo get(Integer id) {
        return this.photoMapper.get(id);
    }
    /** 获取列表 */
    public List<Photo> getList() {
        return this.photoMapper.getList();
    }
    /** 获取条件列表 */
    public List<Photo> getPhotoList(Photo photo) {
        return this.photoMapper.getPhotoList(photo);
    }
    /* --------------------------------------------------- */

    @Retryable(maxAttempts = Integer.MAX_VALUE, backoff = @Backoff(delay = 6 * 1000))
    public String retry() {
        System.err.println("retry method...: " + new Date());
        throw new RuntimeException();
    }

    @Async
    public void async() {
        System.err.println("@Async...");
        for (int i = 0; i < 10; i++) {
            this.get(i);
        }
        System.err.println("@Async done.");
    }
}

