package com.app.ptkp.system;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import helolib.ClassSayHello;
////import heloLib.ClassSayHello;
//import net.sf.jni4net.Bridge;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class PtkpMain {
	
	public static void main(String[] args) {	
        //masuk  -Djava.library.path=D:\Code\csharp\sampah\lib
		SpringApplication.run(PtkpMain.class, args);
	}

}
