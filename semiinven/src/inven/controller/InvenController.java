package inven.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import inven.command.CommandHandler;

public class InvenController extends HttpServlet{

	
	private Map commandMap;
	
	public InvenController(){
		commandMap=new HashMap<String,Object>();
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		String path=config.getServletContext().getRealPath("/WEB-INF/invenCommand.properties");
		Properties prop=null;
		
		try {
			FileInputStream fis = new FileInputStream(path);
			prop=new Properties();
			prop.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		userProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		userProcess(req, resp);
	}
	
	protected void userProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String type=null;
		String userUrl=req.getRequestURI();
		if(userUrl.indexOf(req.getContextPath())==0){
			type=userUrl.substring(req.getContextPath().length());
		}
		CommandHandler command=(CommandHandler)commandMap.get(type);
		String goPage=null;
		
		goPage=command.process(req, resp);
		
		RequestDispatcher dis=req.getRequestDispatcher(goPage);
		dis.forward(req, resp);
		
	}
}
