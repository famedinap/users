package com.init.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceUser1Application implements ApplicationRunner {
	@Value("${Ldap.Url}")
	public static String LdapUrl="default";

	public static void main(String[] args) {
		
		SpringApplication.run(MicroserviceUser1Application.class, args);
	}
	@Override public void run(ApplicationArguments arg)throws Exception {
		
		System.out.println(arg.getNonOptionArgs());
		System.out.println(arg.getOptionNames());
		for (String name : arg.getOptionNames()){
            System.out.println("arg-" + name + "=" + arg.getOptionValues(name));
        }
		if(arg.getNonOptionArgs().size()>0) {
			LdapUrl = arg.getNonOptionArgs().get(0);
		}
		System.out.println(LdapUrl);
		
		
	}

}
