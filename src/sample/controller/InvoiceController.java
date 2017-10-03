package sample.controller;

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
import sample.validator.InvoiceValidator;

/**
 * Servlet implementation class InvoiceController
 */
@WebServlet("/InvoiceController")
public class InvoiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String INSERT = "/new.jsp";
	private static String DETAIL = "/detail.jsp";
	private static String LIST_INVOICE = "/list.jsp";
	private static String EDIT = "/edit.jsp";
	// エラー画面用の定数宣言
	private static String ERROR = "/error.jsp";
	private InvoiceDao dao;

	/**
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @see HttpServlet#HttpServlet()
	 */
	public InvoiceController()
			throws InstantiationException, IllegalAccessException {
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
		String forward = "";
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("list")) {
			// 一覧ページに遷移
			forward = LIST_INVOICE;
			request.setAttribute("invoices", dao.selectAll());
		} else if (action.equalsIgnoreCase("edit")) {
			forward = EDIT;
			int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
			Invoice invoice = dao.selectById(invoiceId);
			request.setAttribute("invoice", invoice);
		} else if (action.equalsIgnoreCase("delete")) {
			int InvoiceId = Integer.parseInt(request.getParameter("invoiceId"));
			dao.delete(InvoiceId);
			forward = LIST_INVOICE;
			request.setAttribute("invoices", dao.selectAll());
		} else if (action.equalsIgnoreCase("detail")) {
			forward = DETAIL;
			int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
			request.setAttribute("invoice", dao.selectById(invoiceId));
		} else {
			// 新規追加ページに遷移
			forward = INSERT;
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		String token = (String) session.getAttribute("token");
		// トークンチェック
		if (token == null || !(token.equals(request.getParameter("token")))) {
			// エラー画面へ
			RequestDispatcher view = request.getRequestDispatcher(ERROR);
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
			
			String action = LIST_INVOICE;
			if (invoiceid == null || invoiceid.isEmpty()) {
				if (!errors.isEmpty()) {
					action = INSERT;
				}
				dao.create(invoice);
			} else {
				if (!errors.isEmpty()) {
					action = EDIT;
				}
				invoice.setInvoiceId(invoiceid);
				dao.update(invoice);
			}
			RequestDispatcher view = request.getRequestDispatcher(action);
			if (action.equals(LIST_INVOICE)) {
				request.setAttribute("invoices", dao.selectAll());
			} else {
				if (!errors.isEmpty()) {
					request.setAttribute("errors", errors);
				}
				request.setAttribute("invoice", invoice);
			}
			view.forward(request, response);
		}
		// if (invoiceid == null || invoiceid.isEmpty()) {
		// if (!errors.isEmpty()) {
		// request.setAttribute("errors", errors);
		// request.setAttribute("invoice", invoice);
		// RequestDispatcher view = request
		// .getRequestDispatcher(INSERT);
		// view.forward(request, response);
		// } else {
		// dao.create(invoice);
		// }
		// } else {
		// if (!errors.isEmpty()) {
		// invoice.setInvoiceId(invoiceid);
		// request.setAttribute("errors", errors);
		// request.setAttribute("invoice", invoice);
		// RequestDispatcher view = request.getRequestDispatcher(EDIT);
		// view.forward(request, response);
		// } else {
		// invoice.setInvoiceId(invoiceid);
		// dao.update(invoice);
		// }
		// }
		// }

	}

}
