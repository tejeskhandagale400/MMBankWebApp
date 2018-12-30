package com.moneymoneybank.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.dao.SavingsAccountDAO;
import com.moneymoney.account.dao.SavingsAccountDAOImpl;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
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
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 System.out.println(path);
		 String accountHolderName="";
		 double accountBalance =0 , amount=0;
		 int accountNumber , senderAccountNumber , recieverAccountNumber ;
		 boolean salary ;
		 SavingsAccount savingsAccount=null;
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
			 break;
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
		 case "/currentBalance.mm":
			 response.sendRedirect("CheckCurrentBalance.html");
			 break;
		 case "/checkBalance.mm":
			 accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
			 try {
				accountBalance = savingsAccountService.getAccountBalance(accountNumber);
				System.out.println(accountBalance);
				out.print("<div> Your Current Balance is "+accountBalance+"</div>");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 case "/withdrawAmount.mm":
			 response.sendRedirect("withdraw.html");
			 break;
		 case "/withdraw.mm":
			 accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
			 amount = Integer.parseInt(request.getParameter("amount"));
			 try {
					savingsAccount = savingsAccountService.getAccountById(accountNumber);
					savingsAccountService.withdraw(savingsAccount, amount);
					DBUtil.commit();
				} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
					try {
						DBUtil.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				} catch (Exception e) {
					try {
						DBUtil.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
		 case "/depositeAmount.mm":
			 response.sendRedirect("deposite.html");
			 break;
		 case "/deposite.mm":
			 accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
			 amount = Integer.parseInt(request.getParameter("amount"));
			 try {
					savingsAccount = savingsAccountService.getAccountById(accountNumber);
					savingsAccountService.deposit(savingsAccount, amount);
					DBUtil.commit();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					try {
						DBUtil.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} catch (Exception e) {
					try {
						DBUtil.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			 break;
		 case "/fundTransfer.mm":
			 response.sendRedirect("fundTransfer.html");
			 break;
		 case "/fundTransf.mm":
			 senderAccountNumber = Integer.parseInt(request.getParameter("senderAccountNumber"));
			 recieverAccountNumber = Integer.parseInt(request.getParameter("recieverAccountNumber"));
			 amount = Integer.parseInt(request.getParameter("amount"));
			 try {
					SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
					SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(recieverAccountNumber);
					savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
		 }
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
