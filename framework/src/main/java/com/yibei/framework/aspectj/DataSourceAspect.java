package com.yibei.framework.aspectj;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.yibei.common.annotation.DataSource;
import com.yibei.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 多数据源处理
 *
 * @author
 */
@Aspect
@Order(-500)
@Component
public class DataSourceAspect {

	@Pointcut("@annotation(com.yibei.common.annotation.DataSource)"
		+ "|| @within(com.yibei.common.annotation.DataSource)")
	public void dsPointCut() {
	}

	@Around("dsPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		DataSource dataSource = getDataSource(point);

		if (StringUtils.isNotNull(dataSource)) {
			DynamicDataSourceContextHolder.poll();
			String source = dataSource.value().getSource();
			DynamicDataSourceContextHolder.push(source);
		}

		try {
			return point.proceed();
		} finally {
			// 销毁数据源 在执行方法之后
			DynamicDataSourceContextHolder.clear();
		}
	}

	/**
	 * 获取需要切换的数据源
	 */
	public DataSource getDataSource(ProceedingJoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
		if (Objects.nonNull(dataSource)) {
			return dataSource;
		}

		return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
	}
}
