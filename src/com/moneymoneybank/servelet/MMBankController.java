package com.moneymoneybank.servelet;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymoney.account.SavingsAccount;
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
	SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();
	private RequestDispatcher dispatcher;
	private boolean sort = false;
	private int accountNumber;
	private SavingsAccount account;
	ArrayList<SavingsAccount> accounts;

	/**
	 * Default constructor.
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println(path);
		String accountHolderName = "";
		double accountBalance = 0, amount = 0;
		int senderAccountNumber, recieverAccountNumber;
		boolean salary;
		SavingsAccount savingsAccount = null;
		ArrayList<SavingsAccount> inputAccountList = new ArrayList<SavingsAccount>();

		switch (path) {
		case "/addAccount.mm":
			response.sendRedirect("CreateAccount.jsp");
			break;
		case "/add.mm":
			accountHolderName = request.getParameter("name");
			accountBalance = Double.parseDouble(request
					.getParameter("accountBalance"));
			salary = request.getParameter("rdSalary").equalsIgnoreCase("true") ? true
					: false;
			try {
				savingsAccountService.createNewAccount(accountHolderName,
						accountBalance, salary);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/closeAccount.mm":
			response.sendRedirect("CloseAccount.jsp");
			break;
		case "/closeAcc.mm":
			accountNumber = Integer.parseInt(request
					.getParameter("accountNumber"));
			try {
				savingsAccountService.deleteAccount(accountNumber);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "/currentBalance.mm":
			response.sendRedirect("CheckCurrentBalance.jsp");
			break;
		case "/checkBalance.mm":
			accountNumber = Integer.parseInt(request
					.getParameter("accountNumber"));
			try {
				accountBalance = savingsAccountService
						.getAccountBalance(accountNumber);
				System.out.println(accountBalance);
				out.print("<div> Your Current Balance is " + accountBalance
						+ "</div>");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/withdrawAmount.mm":
			response.sendRedirect("withdraw.jsp");
			break;
		case "/withdraw.mm":
			accountNumber = Integer.parseInt(request
					.getParameter("accountNumber"));
			amount = Integer.parseInt(request.getParameter("amount"));
			try {
				savingsAccount = savingsAccountService
						.getAccountById(accountNumber);
				savingsAccountService.withdraw(savingsAccount, amount);
				DBUtil.commit();
				
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
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
			response.sendRedirect("deposite.jsp");
			break;
		case "/deposite.mm":
			accountNumber = Integer.parseInt(request
					.getParameter("accountNumber"));
			amount = Integer.parseInt(request.getParameter("amount"));
			try {
				savingsAccount = savingsAccountService
						.getAccountById(accountNumber);
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
			response.sendRedirect("fundTransfer.jsp");
			break;
		case "/fundTransf.mm":
			senderAccountNumber = Integer.parseInt(request
					.getParameter("senderAccountNumber"));
			recieverAccountNumber = Integer.parseInt(request
					.getParameter("recieverAccountNumber"));
			amount = Integer.parseInt(request.getParameter("amount"));
			try {
				SavingsAccount senderSavingsAccount = savingsAccountService
						.getAccountById(senderAccountNumber);
				SavingsAccount receiverSavingsAccount = savingsAccountService
						.getAccountById(recieverAccountNumber);
				savingsAccountService.fundTransfer(senderSavingsAccount,
						receiverSavingsAccount, amount);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/searchForm.mm":
			response.sendRedirect("SearchForm.jsp");
			break;
		case "/search.mm":
			accountNumber = Integer.parseInt(request
					.getParameter("txtAccountNumber"));
			System.out.println(accountNumber);
			try {
				SavingsAccount account = savingsAccountService
						.getAccountById(accountNumber);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "/getAll.mm":
			try {
				accounts = (ArrayList<SavingsAccount>) savingsAccountService
						.getAllSavingsAccount();
				request.setAttribute("accounts", accounts);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/sortByName.mm":
			sort = !sort;
			int result = sort ? 1 : -1;
			try {
				inputAccountList = (ArrayList<SavingsAccount>) savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(inputAccountList,
						new Comparator<SavingsAccount>() {
							@Override
							public int compare(SavingsAccount accountOne,
									SavingsAccount accountTwo) {
								return result
										* accountOne
												.getBankAccount()
												.getAccountHolderName()
												.compareToIgnoreCase(
														accountTwo
																.getBankAccount()
																.getAccountHolderName());
							}

						});
				System.out.println(sort);
				request.setAttribute("accounts", inputAccountList);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);

			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}

			break;

		case "/sortByAccNo.mm":
			sort = !sort;
			System.out.println(sort);
			result = sort ? 1 : -1;
			try {
				inputAccountList = (ArrayList<SavingsAccount>) savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(inputAccountList,
						new Comparator<SavingsAccount>() {
							@Override
							public int compare(SavingsAccount accountOne,
									SavingsAccount accountTwo) {
								if (accountOne.getBankAccount()
										.getAccountNumber() < accountTwo
										.getBankAccount().getAccountNumber())
									return -1 * result;
								else if (accountOne.getBankAccount()
										.getAccountNumber() == accountTwo
										.getBankAccount().getAccountNumber())
									return 0;
								else
									return 1 * result;
							}

						});
				request.setAttribute("accounts", inputAccountList);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "/sortBySalary.mm":
			sort = !sort;
			System.out.println(sort);
			result = sort ? 1 : -1;
			try {
				inputAccountList = (ArrayList<SavingsAccount>) savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(inputAccountList,
						new Comparator<SavingsAccount>() {
							@Override
							public int compare(SavingsAccount accountOne,
									SavingsAccount accountTwo) {
								if (accountOne.isSalary())
									return 1 * result;
								else
									return -1 * result;
							}

						});
				request.setAttribute("accounts", inputAccountList);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/sortByAccBalance.mm":
			sort = !sort;
			System.out.println(sort);
			result = sort ? 1 : -1;
			try {
				inputAccountList = (ArrayList<SavingsAccount>) savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(inputAccountList,
						new Comparator<SavingsAccount>() {
							@Override
							public int compare(SavingsAccount accountOne,
									SavingsAccount accountTwo) {
								if (accountOne.getBankAccount()
										.getAccountBalance() < accountTwo
										.getBankAccount().getAccountBalance())
									return -1 * result;
								else if (accountOne.getBankAccount()
										.getAccountBalance() == accountTwo
										.getBankAccount().getAccountBalance())
									return 0;
								else
									return 1 * result;
							}

						});
				request.setAttribute("accounts", inputAccountList);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "/updateAccount.mm":
			response.sendRedirect("UpdateForm.jsp");
			break;
		case "/update.mm":
			accountNumber = Integer.parseInt(request
					.getParameter("txtAccountNumber"));
			System.out.println(accountNumber);
			try {
				account = savingsAccountService.getAccountById(accountNumber);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("updateDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "/updateInDB.mm":
			// accountNumber =
			// Integer.parseInt(request.getParameter("accountNumber"));
			System.out.println(account.getBankAccount().getAccountNumber());
			accountHolderName = request.getParameter("name");
			salary = request.getParameter("salary").equalsIgnoreCase(
					"salaryTrue") ? true : false;
			System.out.println(salary);

			try {
				account.getBankAccount()
						.setAccountHolderName(accountHolderName);
				account.setSalary(salary);
				account = savingsAccountService.updateAccountInfo(account);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				e1.printStackTrace();
			}

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
