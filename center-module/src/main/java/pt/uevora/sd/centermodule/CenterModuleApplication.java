package pt.uevora.sd.centermodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import pt.uevora.sd.centermodule.Components.ReadJSONProperties;
import pt.uevora.sd.centermodule.Configuration.registerCenter;

@SpringBootApplication
@ComponentScan(basePackageClasses = {ReadJSONProperties.class})
@ComponentScan(basePackageClasses = {registerCenter.class})
public class CenterModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CenterModuleApplication.class, args);
	}

}
