package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ResponseModel.petModel;
import com.model.Data;
import com.model.Request;
import com.model.RequestModel;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserController() {
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String req = request.getParameter("request");
			String data = request.getParameter("data");
			String petStatus = request.getParameter("status");
			System.out.println(petStatus);
			
			Data petStatusM = new Data();
			petStatusM.setPetStatus(petStatus);

			Request dataM = new Request();
			dataM.setData(petStatusM);

			RequestModel requestM = new RequestModel();
			requestM.setRequest(dataM);

			URL url = new URL("https://petstore.swagger.io/v2/pet/findByStatus?status=" + petStatus);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			int responseCode = conn.getResponseCode();

			// 200 OK
			if (responseCode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responseCode);
			} else {

				StringBuilder informationString = new StringBuilder();
				Scanner scanner = new Scanner(url.openStream());

				while (scanner.hasNext()) {
					informationString.append(scanner.nextLine());
				}
				// Close the scanner
				scanner.close();

				System.out.println(informationString);
				PrintWriter prt = response.getWriter();
				response.setContentType("application/json");
				prt.print(informationString);
				String str = informationString.toString();
				petModel petmodel = new petModel();
				petmodel.setPet(str);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
