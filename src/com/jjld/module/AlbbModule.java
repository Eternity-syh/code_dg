package com.jjld.module;

import java.io.File;
import java.io.InputStream;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.filter.CrossOriginFilter;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
@Filters(@By(type=CrossOriginFilter.class))
public  class AlbbModule {
	public static void albbOSS(File file, String name) {
		String endpoint ="oss-cn-beijing.aliyuncs.com";
		String accessKeyId = "LTAI4Fdh2CKqnFja5qbQUxc5";
		String accessKeySecret = "soyGEKtVcWWg5p3wyupyc0suz8aFZt";
		String bucketName = "ghwgyx";
		String yourObjectNam = name;
	
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, yourObjectNam, file);

		// 上传字符串。
		ossClient.putObject(putObjectRequest);

		// 关闭OSSClient。
		ossClient.shutdown();   
	}
	public static void albbOSS(InputStream is, String name) {
		String endpoint ="oss-cn-beijing.aliyuncs.com";
		String accessKeyId = "LTAI4Fdh2CKqnFja5qbQUxc5";
		String accessKeySecret = "soyGEKtVcWWg5p3wyupyc0suz8aFZt";
		String bucketName = "ghwgyx";
		String yourObjectNam = name;
	
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, yourObjectNam, is);

		// 上传字符串。
		ossClient.putObject(putObjectRequest);

		// 关闭OSSClient。
		ossClient.shutdown();   
	}
	

		
		

	
}
