package cloud.nimvps.exercises.salestaxes.view;

public class SysoReceiptPrinter extends AbstractReceiptPrinter {

	@Override
	protected void printLine(String str) {
		System.out.println(str);
	}

}
