package cn.standardai.api.ml.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.standardai.api.core.base.BaseService;
import cn.standardai.api.ml.agent.MLAgent;

@Controller
@RestController
@EnableAutoConfiguration
@RequestMapping("/decision")
public class DecisionService extends BaseService {

	private Logger logger = LoggerFactory.getLogger(DecisionService.class);

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String receiveData(@RequestBody JSONObject request) {
		logger.info("stdaiapi-ml decision start ...");
		MLAgent agent = null;
		JSONObject result = null;
		try {
			agent = new MLAgent();
			result = agent.makeDecisionTree(request);
			result = successResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
			result = makeResponse(ReturnType.FAILURE, null, e.getMessage());
		} finally {
			if (agent != null) agent.done();
		}
		logger.info("stdaiapi-ml decision finish (response:" + result.toJSONString() + ")");
		return result.toString();
	}
}