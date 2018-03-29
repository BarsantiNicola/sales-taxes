package cloud.nimvps.exercises.salestaxes.view;

public class SBReceiptPrinter extends AbstractReceiptPrinter {

	private final StringBuilder sb;

	public SBReceiptPrinter(StringBuilder sb) {
		this.sb = sb;
	}

	@Override
	protected void printLine(String str) {
		sb.append(str);
		sb.append("\r\n");
	}

}
