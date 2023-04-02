package inc.codeman.userapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ReadUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	public void destroy() { 
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void init(ServletConfig config)  {
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection(config.getInitParameter("dbUrl"),config.getInitParameter("dbUser"),config.getInitParameter("dbPass"));
		} catch (SQLException | ClassNotFoundException e) { 
			e.printStackTrace();
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql ="select * from user";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			PrintWriter out= response.getWriter();
			out.println("<h1>User Details</h1>");
			out.println("<table border=1px>");
			out.println("<tr>");
			out.println("<th>");
			out.println("First Name");
			out.println("</th>");
			out.println("<th>");
			out.println("Last Name");
			out.println("</th>");
			out.println("<th>");
			out.println("Email");
			out.println("</th>");
			out.println("</tr>");
			while(rs.next())
			{
				out.println("<tr>");
				out.println("<td>");
				out.println(rs.getString("firstName"));
				out.println("</td>");
				out.println("<td>");
				out.println(rs.getString("lastName"));
				out.println("</td>");
				out.println("<td>");
				out.println(rs.getString("email"));
				out.println("</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}

}
