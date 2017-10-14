package com.cisco.ccl.kvm.GATE.services.web.diagnostics;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cisco.ccl.b2b.web.utils.diagnostics.StatusBuilder;

@Controller
@RequestMapping(value = "/diagnostics")
public class GateDiagnotics {

	@Autowired
	private StatusBuilder statusBuilder;

	@Autowired
	private GateConfig gateconfig;

	@RequestMapping("/status.txt")
	@ResponseBody
	public String status(HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		return statusBuilder.build();
	}

	private Properties applicationProperties;

	@RequestMapping(value = "/config.txt")
	public void listProperties(HttpServletResponse response) throws IOException {
		applicationProperties = new Properties();

		applicationProperties.setProperty("gate.file.folder",
				gateconfig.getGateFileFolder());
		applicationProperties
				.setProperty("gate.home", gateconfig.getGateHome());
		applicationProperties.setProperty("rdf.server.url",
				gateconfig.getRdfServerUrl());

		response.setHeader("Content-Type", "text/plain");
		applicationProperties.store(response.getWriter(), null);
	}
}