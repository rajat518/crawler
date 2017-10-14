package com.cisco.ccl.kvm.GATE.services.web.diagnostics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource(value = { "classpath:kvm.properties" })
@Component
public class GateConfig {

	@Value("${gate.file.folder}")
	private String gateFileFolder;

	@Value("${gate.home}")
	private String gateHome;

	@Value("${rdf.server.url}")
	private String rdfServerUrl;
	
	public String getGateFileFolder() {
		return gateFileFolder;
	}

	public void setGateFileFolder(String gateFileFolder) {
		this.gateFileFolder = gateFileFolder;
	}

	public String getGateHome() {
		return gateHome;
	}

	public void setGateHome(String gateHome) {
		this.gateHome = gateHome;
	}

	public String getRdfServerUrl() {
		return rdfServerUrl;
	}

	public void setRdfServerUrl(String rdfServerUrl) {
		this.rdfServerUrl = rdfServerUrl;
	}
}
