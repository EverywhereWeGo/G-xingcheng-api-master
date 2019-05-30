package com.bfd.api.zuul.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="data-manage")
public interface ManageClient {

	@RequestMapping(value = "/data-manage/api/getApiByUrl")
	public String getApiByUrl(@RequestParam(value = "url",required = true)String url);
}
