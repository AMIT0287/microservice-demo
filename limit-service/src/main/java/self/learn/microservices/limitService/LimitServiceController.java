package self.learn.microservices.limitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import self.learn.microservices.limitService.bean.LimitConfigurationBean;

@RestController
public class LimitServiceController {
	
	@Autowired
	private ConfigurationProp prop;
	
	
	@GetMapping("/limits")
	public LimitConfigurationBean getConfigurationBean() {
		return new LimitConfigurationBean(prop.getMinimum(), prop.getMaximum());
	}

}
