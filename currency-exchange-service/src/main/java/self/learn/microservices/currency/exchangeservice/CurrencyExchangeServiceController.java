package self.learn.microservices.currency.exchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeServiceController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private org.springframework.core.env.Environment env;
	
	@Autowired
	ExchangeValueRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue ev = repository.findByFromAndTo(from, to);
		ev.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		
		log.info("Exchange value {}", ev);
		return ev;
	}

}
