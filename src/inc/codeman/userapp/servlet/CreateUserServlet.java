package inc.codeman.userapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns="/addServlet",initParams= {@WebInitParam(name="dbUrl",value="jdbc:mysql://localhost:3306/sales?useSSL=false"),@WebInitParam(name="dbUser",value="webstudent"),@WebInitParam(name="dbPass",value="webstudent")})
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	private Connection con;
	public void destroy() { 
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(ServletConfig config)  {
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 ServletContext context = config.getServletContext();
			 Enumeration<String> paramNames = context.getInitParameterNames();
			 while(paramNames.hasMoreElements()) {
				 String name = paramNames.nextElement();
				 String value = context.getInitParameter(name);
				 System.out.println(name+" "+value);
			 }
			 con = DriverManager.getConnection(config.getInitParameter("dbUrl"),config.getInitParameter("dbUser"),config.getInitParameter("dbPass"));
		} catch (SQLException | ClassNotFoundException e) { 
			e.printStackTrace();
		}
		
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		try {
			Statement stmt = con.createStatement();
			String sql = String.format("insert into user values('%s','%s','%s','%s')",firstName,lastName,email,password);
			System.out.println(sql);
			int res = stmt.executeUpdate(sql);
			stmt.close();
			PrintWriter out = response.getWriter();
			if(res>0) {
			out.print("<h1>User Created</h1>");
			}
			else {
				out.print("<h1>Error Creating the User</h1>");
			}
			out.close();
			} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
