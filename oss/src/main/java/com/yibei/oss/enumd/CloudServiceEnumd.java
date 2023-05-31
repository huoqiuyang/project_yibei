package com.yibei.oss.enumd;

import com.yibei.common.utils.StringUtils;
import com.yibei.oss.service.impl.AliyunCloudStorageStrategy;
import com.yibei.oss.service.impl.MinioCloudStorageStrategy;
import com.yibei.oss.service.impl.QcloudCloudStorageStrategy;
import com.yibei.oss.service.impl.QiniuCloudStorageStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 云存储服务商枚举
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum CloudServiceEnumd {

	/**
	 * 七牛云
	 */
	QINIU("qiniu", QiniuCloudStorageStrategy.class),

	/**
	 * 阿里云
	 */
	ALIYUN("aliyun", AliyunCloudStorageStrategy.class),

	/**
	 * 腾讯云
	 */
	QCLOUD("qcloud", QcloudCloudStorageStrategy.class),

	/**
	 * minio
	 */
	MINIO("minio", MinioCloudStorageStrategy.class);

	private final String value;

	private final Class<?> serviceClass;

	public static Class<?> getServiceClass(String value) {
		for (CloudServiceEnumd clazz : values()) {
			if (clazz.getValue().equals(value)) {
				return clazz.getServiceClass();
			}
		}
		return null;
	}

	public static String getServiceName(String value) {
		for (CloudServiceEnumd clazz : values()) {
			if (clazz.getValue().equals(value)) {
				return StringUtils.uncapitalize(clazz.getServiceClass().getSimpleName());
			}
		}
		return null;
	}


}
