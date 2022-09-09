package com.curso.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//de momento como todavia no tenemos base de datos entonces 
//para que arranque excluimos la datasource autoconfiguration
//@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)

@SpringBootApplication
public class SpringEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEcommerceApplication.class, args);
	}

}
