package sample.validator;

import java.util.ArrayList;
import java.util.List;

import sample.model.Invoice;

public class InvoiceValidator {

    public List<String> validate(Invoice invoiceModel) {
        List<String> errors = new ArrayList<String>();
        String title = invoiceModel.getTitle();
        if (title == null || title.trim().isEmpty()) {
            errors.add("タイトルは必ず入力してください");
        }
        String detail = invoiceModel.getDetail();
        if (detail == null || detail.trim().isEmpty()) {
            errors.add("詳細は必ず入力してください");
        }
        
        String totalFee = invoiceModel.getTotalFee().toString();
        if (totalFee == null || totalFee.trim().isEmpty()) {
            errors.add("金額を入れてください");
        } else {
            try {
                Integer.parseInt(totalFee);
            } catch (NumberFormatException e) {
                errors.add("金額は数値を入力してください");
            }
        }

        return errors;
    }
}
