

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogServlet
 */
@WebServlet("/LogServlet")
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter outputStream = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\gaurav\\Desktop\\sample.log", true)));
		//Map<String, String[]> req= request.getParameterMap();
		ArrayList<ArrayList<Integer>> gestureWindow = new ArrayList<ArrayList<Integer>>();
		
		
		
		String test = new String();
		//test = "1";
		String[] pars = request.getQueryString().split("&");
		ArrayList<Integer> accel = new ArrayList<Integer>();
		
		
		for(String par:pars) {
			String value = par.split("=")[1];
			accel.add(Integer.parseInt(value));
			test = test+"\t"+value;
		}
		
		if (accel.size()==5) {
			//Recognize
			
			gestureWindow = new ArrayList<ArrayList<Integer>>();
			
		}
		
		gestureWindow.add(accel);
		
		//test = "1"+"\t"+req.get("x")+"\t"+(String)req.get("y")+"\t"+(String)req.get("z");
		outputStream.println(test);
		System.out.println(test);
		outputStream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
