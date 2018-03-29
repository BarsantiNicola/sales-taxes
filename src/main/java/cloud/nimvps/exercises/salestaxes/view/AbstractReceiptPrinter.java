package cloud.nimvps.exercises.salestaxes.view;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import cloud.nimvps.exercises.salestaxes.core.model.Item;
import cloud.nimvps.exercises.salestaxes.core.model.Receipt;

public abstract class AbstractReceiptPrinter {

	private static final DecimalFormat FORMATTER;

	static {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(',');
		FORMATTER = new DecimalFormat("#0.00", symbols);
	}

	protected abstract void printLine(String str);

	public void printReceipt(Receipt receipt) {
		receipt.getItems().forEach(item -> printItem(item));
		printLine("Sales Taxes: " + FORMATTER.format(receipt.getTotalTaxes()));
		printLine("Total: " + FORMATTER.format(receipt.getTotalPrice()));
	}

	protected void printItem(Item item) {
		StringBuilder sb = new StringBuilder();
		sb.append(item.getAmount());
		sb.append(" ");
		if (item.isImported()) {
			sb.append("imported ");
		}
		sb.append(item.getName());
		sb.append(": ");
		sb.append(FORMATTER.format(item.getTotalPrice()));
		printLine(sb.toString());
	}

}
