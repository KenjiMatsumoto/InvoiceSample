package sample.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sample.dao.InvoiceDao;
import sample.util.EnumJspName;

/**
 * Servlet implementation class DetailAction
 */
@WebServlet("/DetailAction")
public class DetailAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InvoiceDao dao;
	
	/**
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailAction() throws InstantiationException, IllegalAccessException {
		super();
		// TODO Auto-generated constructor stub
		dao = new InvoiceDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得
		HttpSession session = request.getSession(true);
		// トークンをセッションに保存
		session.setAttribute("token", session.getId());
		
		int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
		request.setAttribute("invoice", dao.selectById(invoiceId));
		RequestDispatcher view = request.getRequestDispatcher(EnumJspName.DETAIL.toString());
		view.forward(request, response);
	}

}
