package com.moneymoneybank.servelet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymoney.account.dao.SavingsAccountDAO;
import com.moneymoney.account.dao.SavingsAccountDAOImpl;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.exception.AccountNotFoundException;

/**
 * Servlet implementation class MMBankController
 */
@WebServlet("*.mm")
public class MMBankController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SavingsAccountService savingsAccountService=new SavingsAccountServiceImpl();    /**
     * Default constructor. 
     */
    public MMBankController() {
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String path = request.getServletPath();
		 System.out.println(path);
		 String accountHolderName="";
		 double accountBalance =0;
		 int accountNumber;
		 boolean salary ;
		 switch(path)
		 {
		 case "/addAccount.mm":
			 response.sendRedirect("CreateAccount.html");
			 break;
		 case "/add.mm":
			 accountHolderName= request.getParameter("name");
			 accountBalance = Double.parseDouble( request.getParameter("accountBalance"));
			 salary = request.getParameter("salaryTrue") == "on"?true : false;
			 try {
				 savingsAccountService.createNewAccount(accountHolderName, accountBalance, salary);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		 case "/closeAccount.mm":
			 response.sendRedirect("CloseAccount.html");
			 break;
		 case "/closeAcc.mm":
			 accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
			 try {
				savingsAccountService.deleteAccount(accountNumber);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
 			 break;
		 case "/CurrentBalance.mm":
			 accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
			 accountBalance = savingsAccountService.getAccountBalance(accountNumber);
		 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
