package self.learn.microservices.currency.convertionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> response =  new RestTemplate().getForEntity("http://localhost:8082/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);
		
		CurrencyConversionBean bean = response.getBody();
		CurrencyConversionBean beanoutput =  new CurrencyConversionBean(bean.getId(), from, to, bean.getConversionMultiple(), quantity);
		beanoutput.setPort(bean.getPort());
		beanoutput.setTotalCalculationAmount(beanoutput.getQuantity().multiply(bean.getConversionMultiple()));
		return beanoutput;
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		CurrencyConversionBean bean = proxy.retrieveExchangeValue(from, to);
		CurrencyConversionBean beanoutput =  new CurrencyConversionBean(bean.getId(), from, to, bean.getConversionMultiple(), quantity);
		beanoutput.setPort(bean.getPort());
		beanoutput.setTotalCalculationAmount(beanoutput.getQuantity().multiply(bean.getConversionMultiple()));
		
		log.info("resonse {}", bean);
		return beanoutput;
	}

}
