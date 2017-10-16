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
 * Servlet implementation class DeleteAction
 */
@WebServlet("/DeleteAction")
public class DeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InvoiceDao dao;
       
    /**
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAction() throws InstantiationException, IllegalAccessException {
        super();
        dao = new InvoiceDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得
		HttpSession session = request.getSession(true);
		// トークンをセッションに保存
		session.setAttribute("token", session.getId());
		
		int InvoiceId = Integer.parseInt(request.getParameter("invoiceId"));
		dao.delete(InvoiceId);
		request.setAttribute("invoices", dao.selectAll());
		RequestDispatcher view = request.getRequestDispatcher(EnumJspName.LIST.toString());
		view.forward(request, response);
	}

}
