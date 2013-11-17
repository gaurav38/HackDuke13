

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class GRTServlet
 */
@WebServlet("/GRTServlet")
public class GRTServlet extends HttpServlet {
	
	//svm_model model;
	ArrayList<String> gestureWindow = new ArrayList<String>();
	private static final long serialVersionUID = 1L;
	private static final String template = "GRT_LABELLED_TIME_SERIES_CLASSIFICATION_DATA_FILE_V1.0\n" +
"DatasetName: GestureTimeSeriesData\n" +  
"InfoText: This dataset contains 2 mouse timeseries gestures, writing the letters G, R, and T.  There are 15 recording of each gesture. G is class 1, R is class 2, and T is class 3.\n" +
"NumDimensions: 3\n" + 
"TotalNumTrainingExamples: 1\n" + 
"NumberOfClasses: 1\n" + 
"ClassIDsAndCounters:\n" +  
"2	1\n" + 
"UseExternalRanges: 0\n" + 
"LabelledTimeSeriesTrainingData:\n" + 
"************TIME_SERIES************\n" + 
"ClassID: 2\n" + 
"TimeSeriesLength: 1\n" + 
"TimeSeriesData:\n" 
;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GRTServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    public String predict(ArrayList<String> gesture) throws IOException, InterruptedException {
    	File file = new File("C:\\Users\\gaurav\\Documents\\GRT\\GRTExamples\\ClassificationModulesExamples\\DTWExample\\test.log");
    	 
		// if file doesnt exists, then create it
			file.createNewFile();

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(template);
		for (String accel: gesture)
			bw.write(accel + "\n");
		bw.close();
		
		//Invoke C++ program, read predicted class label from stdout
		try {
			Process proc = Runtime.getRuntime().exec(new String[] {
					  "\"C:\\Users\\gaurav\\Documents\\GRT\\HackProject\\bin\\Debug\\HackProject.exe\"", 
					  "\"C:\\Users\\gaurav\\Documents\\GRT\\GRTExamples\\ClassificationModulesExamples\\DTWExample\\test.log\"",
					  "\"C:\\Users\\gaurav\\Documents\\GRT\\Classifier\\bin\\Debug\\DTWModel.txt\""
					  });
			proc.waitFor();
			InputStream in = proc.getInputStream();              
			BufferedReader br=new BufferedReader(new InputStreamReader(in));
			String line =null;

			 line=br.readLine();
			//able to read line only when database name like abc,datastore etc...
			System.out.println("GestureID : " + line);
			return line;
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().println("Hello");
		//model = svm.svm_load_model("C:\\Users\\gaurav\\Documents\\GRT\\Classifier\\bin\\Debug\\SVMModel.txt");
		//Map<String, String[]> req= request.getParameterMap();
		//PrintWriter outputStream = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\gaurav\\Desktop\\sample.log", true)));
		//Map<String, String[]> req= request.getParameterMap();
		
		
		
		
		String test = new String();
		//test = "1";
		String[] pars = request.getQueryString().split("&");
		//ArrayList<Integer> accel = new ArrayList<Integer>();
		
		String label = "100";
		int i=1;
		for(String par:pars) {
			String value = par.split("=")[1];
			//accel.add(Integer.parseInt(value));
			test =test + value;
			if ( i!=3 )
				test = test + "\t";
			i++;
		}
		
		gestureWindow.add(test);
		if (gestureWindow.size()==7) {
			System.out.println("Inside 15");
			//Recognize
			try {
				label = predict(gestureWindow);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gestureWindow = new ArrayList<String>();
			
		}
			
		//test = "1"+"\t"+req.get("x")+"\t"+(String)req.get("y")+"\t"+(String)req.get("z");
		//outputStream.println(test);
		//System.out.println(test);
		//outputStream.close();
		response.getWriter().println(label);	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
