package com.yibei.oss.service.abstractd;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.yibei.common.utils.DateUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.oss.properties.CloudStorageProperties;
import com.yibei.oss.service.ICloudStorageStrategy;
import com.yibei.oss.entity.UploadResult;

import java.io.InputStream;

/**
 * 云存储策略(支持七牛、阿里云、腾讯云、minio)
 *
 * @author Lion Li
 */
public abstract class AbstractCloudStorageStrategy implements ICloudStorageStrategy {

	protected CloudStorageProperties properties;

	public abstract void init(CloudStorageProperties properties);

	@Override
	public abstract void createBucket();

	@Override
	public abstract String getServiceType();

	@Override
	public String getPath(String prefix, String suffix,String cate) {
		// 生成uuid
		String uuid = IdUtil.fastSimpleUUID();
		// 文件路径
		String path = DateUtils.datePath() + "/" + uuid;
		if (StringUtils.isNotBlank(cate)) {
			path = cate + "/" + path;
		}
		if (StringUtils.isNotBlank(prefix)) {
			path = prefix + "/" + path;
		}
		return path + suffix;
	}

	public String getPath(String prefix, String suffix) {
		return getPath(prefix,suffix,"");
	}

	@Override
	public abstract UploadResult upload(byte[] data, String path, String contentType);

	@Override
	public abstract void delete(String path);

	@Override
	public UploadResult upload(InputStream inputStream, String path, String contentType) {
		byte[] data = IoUtil.readBytes(inputStream);
		return this.upload(data, path, contentType);
	}

	@Override
	public abstract UploadResult uploadSuffix(byte[] data, String suffix, String contentType,String cate);

	@Override
	public abstract UploadResult uploadSuffix(InputStream inputStream, String suffix, String contentType,String cate);

	@Override
	public abstract String getEndpointLink();
}
