package sample.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sample.dao.InvoiceDao;
import sample.model.Invoice;
import sample.util.EnumJspName;
import sample.validator.InvoiceValidator;

/**
 * Servlet implementation class newAction
 */
@WebServlet("/NewAction")
public class NewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InvoiceDao dao;
       
    /**
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @see HttpServlet#HttpServlet()
     */
    public NewAction() throws InstantiationException, IllegalAccessException {
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
		// 新規追加ページに遷移
		RequestDispatcher view = request.getRequestDispatcher(EnumJspName.NEW.toString());
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		String token = (String) session.getAttribute("token");
		// トークンチェック
		if (token == null || !(token.equals(request.getParameter("token")))) {
			// エラー画面へ
			RequestDispatcher view = request.getRequestDispatcher(EnumJspName.ERROR.toString());
			view.forward(request, response);
		} else {
			request.setCharacterEncoding("UTF-8");
			Invoice invoice = new Invoice();
			invoice.setTitle(request.getParameter("title"));
			invoice.setDetail(request.getParameter("detail"));
			invoice.setTotalFee(request.getParameter("totalFee"));
			String invoiceid = request.getParameter("invoiceId");

			InvoiceValidator invoiceValidator = new InvoiceValidator();
			List<String> errors = invoiceValidator.validate(invoice);
			if (invoiceid == null || invoiceid.isEmpty()) {
				dao.create(invoice);
			}
			RequestDispatcher view = request.getRequestDispatcher(EnumJspName.NEW.toString());
			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
			}
			request.setAttribute("invoice", invoice);
			view.forward(request, response);
		}
	}
}
