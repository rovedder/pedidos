package br.com.ufsm.pedidoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PedidoServiceApplication {
	
	private static Logger logger = LoggerFactory.getLogger(PedidoServiceApplication .class);

	public static void main(String[] args) {
		SpringApplication.run(PedidoServiceApplication.class, args);
	}

}
