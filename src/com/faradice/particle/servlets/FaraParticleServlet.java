package com.faradice.particle.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.faradice.faranet.FaraHttp;

public class FaraParticleServlet extends HttpServlet {

	public FaraParticleServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("GET yes");
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			response.getWriter().close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("Got Post from particle");
			response.setStatus(HttpServletResponse.SC_OK);
			BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = FaraHttp.readInputLine(input);
			System.out.println(line);
			String res = "Cool stuff";
			response.getOutputStream().write(res.getBytes());
			response.setStatus(HttpServletResponse.SC_OK);
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			System.out.println("Done and out");
		}
	}
	
}
