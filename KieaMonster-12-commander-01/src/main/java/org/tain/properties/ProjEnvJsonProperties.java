package org.tain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "proj-env.json")
@Data
public class ProjEnvJsonProperties {

	private String name;  // default
	
	private String orgInfoFile;
	private String grpInfoFile;
	private String svrInfoFile;
	private String cmdInfoFile;
	
	private String dummy;  // null
}
