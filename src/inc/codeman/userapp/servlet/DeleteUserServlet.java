package inc.codeman.userapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteServlet")
public class DeleteUserServlet extends HttpServlet {
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
			 ServletContext context = config.getServletContext();
			 con = DriverManager.getConnection(context.getInitParameter("dbUrl"),context.getInitParameter("dbUser"),context.getInitParameter("dbPass"));
		} catch (SQLException | ClassNotFoundException e) { 
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");		
		String sql ="delete from user  where email=?";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);		
			stmt.setString(1, email);
			int res = stmt.executeUpdate();
			PrintWriter out= response.getWriter();
			if(res>0)
				out.print("<h1>User Deleted</h1>");
			else
				out.print("<h1>Error, User  not present</h1>");
			out.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}

}
