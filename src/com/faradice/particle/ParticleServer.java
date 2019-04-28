package com.faradice.particle;

import com.faradice.faranet.FaraJettyServer;
import com.faradice.particle.servlets.FaraParticleServlet;

public class ParticleServer {
	public static void main(String[] args) throws Exception {
		FaraJettyServer server = new FaraJettyServer(8020, args);
		server.addServlet("/", "/particle/*", new FaraParticleServlet());
		server.run();
	}
}
