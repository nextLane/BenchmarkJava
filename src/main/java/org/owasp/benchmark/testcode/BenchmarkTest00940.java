/**
* OWASP Benchmark Project v1.2
*
* This file is part of the Open Web Application Security Project (OWASP)
* Benchmark Project. For details, please see
* <a href="https://www.owasp.org/index.php/Benchmark">https://www.owasp.org/index.php/Benchmark</a>.
*
* The OWASP Benchmark is free software: you can redistribute it and/or modify it under the terms
* of the GNU General Public License as published by the Free Software Foundation, version 2.
*
* The OWASP Benchmark is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
* even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* @author Dave Wichers <a href="https://www.aspectsecurity.com">Aspect Security</a>
* @created 2015
*/

package org.owasp.benchmark.testcode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/sqli-01/BenchmarkTest00940")
public class BenchmarkTest00940 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// some code
		response.setContentType("text/html;charset=UTF-8");
		

		String param = "";
		if (request.getHeader("BenchmarkTest00940") != null) {
			param = request.getHeader("BenchmarkTest00940");
		}
		
		// URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
		java.net.URLDecoder.decode(param, "UTF-8");
		
		String sql = "INSERT INTO users (username, password) VALUES ('foo','"+ param + "')";
				
		try {
			java.sql.Statement statement = org.owasp.benchmark.helpers.DatabaseHelper.getSqlStatement();
			
			// FIX: Use PreparedStatement instead of direct string concatenation
			String preparedSql = "INSERT INTO users (username, password) VALUES ('foo',?)";
			java.sql.PreparedStatement preparedStatement = org.owasp.benchmark.helpers.DatabaseHelper.getConnection().prepareStatement(preparedSql);
			preparedStatement.setString(1, param);
			preparedStatement.execute();
			
			org.owasp.benchmark.helpers.DatabaseHelper.printResults(statement, sql, response);
		} catch (java.sql.SQLException e) {
			if (org.owasp.benchmark.helpers.DatabaseHelper.hideSQLErrors) {
				response.getWriter().println("Error processing request.");
				return;
			}
			else throw new ServletException(e);
		}
	}
}
