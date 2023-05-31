package com.yibei.system.service;

import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.SysOss;
import com.yibei.system.domain.bo.SysOssBo;
import com.yibei.system.domain.vo.SysOssVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * 文件上传 服务层
 *
 * @author Lion Li
 */
public interface ISysOssService extends IServicePlus<SysOss, SysOssVo> {

	TableDataInfo<SysOssVo> queryPageList(SysOssBo sysOss);

	SysOss upload(MultipartFile file,String cate,Long userId);

	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
