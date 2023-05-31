package com.yibei.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.exception.ServiceException;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.oss.entity.UploadResult;
import com.yibei.oss.factory.OssFactory;
import com.yibei.oss.service.ICloudStorageStrategy;
import com.yibei.system.domain.SysOss;
import com.yibei.system.domain.bo.SysOssBo;
import com.yibei.system.domain.vo.SysOssVo;
import com.yibei.system.mapper.SysOssMapper;
import com.yibei.system.service.ISysOssService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 文件上传 服务层实现
 *
 * @author Lion Li
 */
@Service
public class SysOssServiceImpl extends ServicePlusImpl<SysOssMapper, SysOss, SysOssVo> implements ISysOssService {

	@Override
	public TableDataInfo<SysOssVo> queryPageList(SysOssBo bo) {
		PagePlus<SysOss, SysOssVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
		return PageUtils.buildDataInfo(result);
	}

	private LambdaQueryWrapper<SysOss> buildQueryWrapper(SysOssBo bo) {
		Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<SysOss> lqw = Wrappers.lambdaQuery();
		lqw.like(StringUtils.isNotBlank(bo.getFileName()), SysOss::getFileName, bo.getFileName());
		lqw.like(StringUtils.isNotBlank(bo.getOriginalName()), SysOss::getOriginalName, bo.getOriginalName());
		lqw.eq(StringUtils.isNotBlank(bo.getFileSuffix()), SysOss::getFileSuffix, bo.getFileSuffix());
		lqw.eq(StringUtils.isNotBlank(bo.getUrl()), SysOss::getUrl, bo.getUrl());
		lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
			SysOss::getCreateTime, params.get("beginCreateTime"), params.get("endCreateTime"));
		lqw.eq(StringUtils.isNotBlank(bo.getCreateBy()), SysOss::getCreateBy, bo.getCreateBy());
		lqw.eq(StringUtils.isNotBlank(bo.getService()), SysOss::getService, bo.getService());
		return lqw;
	}

	@Override
	public SysOss upload(MultipartFile file,String cate,Long userId) {
		String originalfileName = file.getOriginalFilename();
		String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
		ICloudStorageStrategy storage = OssFactory.instance();
		UploadResult uploadResult;
		try {
			//uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType(),cate);
			uploadResult = storage.uploadSuffix(file.getBytes(), suffix, getContentType(FilenameUtils.getExtension(file.getOriginalFilename())),cate);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
		// 保存文件信息
		SysOss oss = new SysOss()
			.setUserId(userId)
			.setCate(cate)
			.setUrl(uploadResult.getUrl())
			.setFileSuffix(suffix)
			.setFileName(uploadResult.getFilename())
			.setOriginalName(originalfileName)
			.setService(storage.getServiceType());
		save(oss);
		return oss;
	}
	/**
	 *
	 * @MethodName: getContentType
	 * @Description: 获取文件类型
	 * @param fileType
	 * @return String
	 */
	private static String getContentType(String fileType){
		fileType = fileType.toLowerCase();
		String contentType = "";
		switch (fileType) {
			case "bmp":
			case "gif":
			case "png":
			case "jpeg":
			case "jpg":
				contentType = "image/jpg";
				break;
			case "html":
				contentType = "text/html";
				break;
			case "txt":
				contentType = "text/plain";
				break;
			case "vsd":
				contentType = "application/vnd.visio";
				break;
			case "ppt":
			case "pptx":
				contentType = "application/vnd.ms-powerpoint";
				break;
			case "doc":
			case "docx":
				contentType = "application/msword";
				break;
			case "xml":
				contentType = "text/xml";
				break;
			case "mp4":
				contentType = "video/mp4";
				break;
			case "wav":
				contentType = "audio/wav";
				break;
			case "mp3":
				contentType = "audio/mp3";
				break;
			default:
				contentType = "application/octet-stream";
				break;
		}
		return contentType;
	}

	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			// 做一些业务上的校验,判断是否需要校验
		}
		List<SysOss> list = listByIds(ids);
		for (SysOss sysOss : list) {
			ICloudStorageStrategy storage = OssFactory.instance(sysOss.getService());
			storage.delete(sysOss.getUrl());
		}
		return removeByIds(ids);
	}

}
