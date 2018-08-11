package com.faradice.particle.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.faradice.commands.FaraWebApi;
import com.faradice.faraUtil.FaraFiles;
import com.faradice.faraUtil.FaraUtil;
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
//			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			response.setStatus(HttpServletResponse.SC_OK);
			BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = FaraHttp.readInputLine(input);
			System.out.println(line);
			response.setStatus(HttpServletResponse.SC_OK);
//			setValuesFromInput(line);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			response.getWriter().close();
			System.out.println("Done and out");
		}
	}
	
	
	private void setValuesFromInput(String line) {
		String[] cols = line.split("&");
		for (String s : cols) {
			if (s.contains("MaxI=")) {
				setMaxI(s);
			} else if (s.contains("Endpoint=")) {
				setEndPoint(s);
			} else if (s.contains("ServiceName=")) {
				setServiceName(s);
			}  else if (s.contains("Port=")) {
				setOCPPPort(s);
			}  else if (s.contains("Charge=")) {
				startCharging(s);
			}  else if (s.contains("Disable")) {
				disableCharger();
			}  else if (s.contains("Stop")) {
				stopCharging();
			}  else if (s.contains("Enable")) {
				enableCharger();
			}  else if (s.contains("SoftReset")) {
				restartPilot();
			}  else if (s.contains("HardReset")) {
				restartCharger();
			}  else if (s.contains("Authentication=")) {
				setAuthenticate(s);
			}
		}
	}
	
	public HashMap<String, String> mapInput(String input) {
		if (!input.contains("=")) throw new RuntimeException("Invalid configuration input");
		HashMap<String, String> varMap = new HashMap<>();
		input = input.trim();
		int endOfKey = input.indexOf("=");
		String key = input.substring(0, endOfKey);
		String newVar = input.substring(endOfKey+1);
		newVar = newVar.trim();
		varMap.put(key, newVar);
		return varMap;
		
	}
	
	private void setMaxI(String newValue) {
		String newAmpStr = newValue;
		int endPos = newAmpStr.indexOf("a",4);
		if (endPos < 0) endPos = newAmpStr.length();
		int startPos = newValue.indexOf("=") +1;
		String newAmp = newValue.substring(startPos, endPos);
		Integer newAmper = Integer.parseInt(newAmp);
		FaraWebApi.setMaxAmps(newAmper);
	}
	
	private void setEndPoint(String newValue) {
		HashMap<String, String> map = mapInput(newValue);
		String key = "Endpoint";
		FaraUtil.setConfigValue(key, map.get(key));
		
	}
	
	private void setServiceName(String newValue) {
		HashMap<String, String> map = mapInput(newValue);
		String key = "ServiceName";
		FaraUtil.setConfigValue(key, map.get(key));
	}
	
	private void setOCPPPort(String newValue) {
		HashMap<String, String> map = mapInput(newValue);
		String key = "Port";
		FaraUtil.setConfigValue(key, map.get(key));
	}
	
	private void startCharging(String rfid)  {
		FaraWebApi.scan(rfid);
	}

	private void disableCharger()  {
		FaraWebApi.disableCharger();
	}

	private void enableCharger() {
		FaraWebApi.enableCharger();
	}

	private void restartCharger() {
		FaraWebApi.restartCharger();
	}

	private void stopCharging() {
		FaraWebApi.stopCharger();
	}
	
	private void restartPilot() {
		FaraWebApi.restartPilot();
	}

	private void setAuthenticate(String newAuthValue) {
		FaraFiles.deleteRowFromFile(FaraFiles.CONFIG_FILE, "Authentication =");
		newAuthValue = newAuthValue.replace("=", " = ");
		FaraFiles.appendToFile(FaraFiles.CONFIG_FILE, newAuthValue);
		Boolean on = newAuthValue.contains("yes") || newAuthValue.contains("=on") || newAuthValue.contains("true"); 
		FaraWebApi.setAuthenticate(on);
	}
	
}
