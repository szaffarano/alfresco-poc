package afip.tecno.alfresco.constraint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.alfresco.repo.dictionary.constraint.ListOfValuesConstraint;

public class DireccionesConstraint extends ListOfValuesConstraint implements Serializable {

	private static final long serialVersionUID = 6759599988340408958L;

	@Override
	public void setAllowedValues(@SuppressWarnings("rawtypes") List allowedValues) {
	}

	@Override
	public void setCaseSensitive(boolean caseSensitive) {
	}

	public void initialize() {
		super.setCaseSensitive(false);
		loadValues();
	}

	private void loadValues() {
		List<String> values = new ArrayList<String>();
		values.add("Dependencia 1");
		values.add("Dependencia 2");
		values.add("Dependencia 3");

		super.setAllowedValues(values);
	}
}