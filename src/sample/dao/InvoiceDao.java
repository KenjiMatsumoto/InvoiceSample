package sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import sample.model.Invoice;
import sample.util.DbUtil;

public class InvoiceDao {

    private Connection connection;

    public InvoiceDao() throws InstantiationException, IllegalAccessException {
        connection = DbUtil.getConnection();
    }

    public void create(Invoice invoice) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into Invoices(title,detail,totalFee,update_date) values (?, ?, ?, ? )");
            // Parameters start with 1
            preparedStatement.setString(1, invoice.getTitle());
            preparedStatement.setString(2, invoice.getDetail());
            preparedStatement.setInt(3, Integer.parseInt(invoice.getTotalFee()));
            preparedStatement.setDate(4, new java.sql.Date(new Date().getTime()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int invoiceId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from Invoices where Invoiceid=?");
            // Parameters start with 1
            preparedStatement.setInt(1, invoiceId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Invoice invoice) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update Invoices set title=?, detail=?, totalFee=?, update_date=?" +
                            "where Invoiceid=?");
            //TODO 文字化け対策
            // Parameters start with 1
            preparedStatement.setString(1, invoice.getTitle());
            preparedStatement.setString(2, invoice.getDetail());
            preparedStatement.setInt(3, Integer.parseInt(invoice.getTotalFee()));
            preparedStatement.setDate(4, new java.sql.Date(new Date().getTime()));
            preparedStatement.setInt(5, Integer.parseInt(invoice.getInvoiceId()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Invoice> selectAll() {
        List<Invoice> invoices = new ArrayList<Invoice>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Invoices");
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getString("InvoiceId"));
                invoice.setTitle(rs.getString("title"));
                invoice.setDetail(rs.getString("detail"));
                invoice.setTotalFee(String.valueOf(rs.getInt("totalFee")));
                invoice.setUpdateDate(rs.getDate("update_date"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    public Invoice selectById(int invoiceId) {
        Invoice invoice = new Invoice();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from Invoices where InvoiceId=?");
            preparedStatement.setInt(1, invoiceId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	invoice.setInvoiceId(rs.getString("InvoiceId"));
            	invoice.setTitle(rs.getString("title"));
            	invoice.setDetail(rs.getString("detail"));
            	invoice.setTotalFee(String.valueOf(rs.getInt("totalFee")));
            	invoice.setUpdateDate(rs.getTimestamp("update_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoice;
    }
}