 package com.stoldo.m120_rcas_projektarbeit.gui.components.controlls.corner_weight_controll;


import com.stoldo.m120_rcas_projektarbeit.gui.components.controlls.input_fields.DoubleInputField;
import com.stoldo.m120_rcas_projektarbeit.model.VoidCallable;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.DebounceTime;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.FormControll;
import com.stoldo.m120_rcas_projektarbeit.model.validators.DoubleMinMaxValidator;
import com.stoldo.m120_rcas_projektarbeit.model.validators.RequiredValidator;
import com.stoldo.m120_rcas_projektarbeit.shared.util.CommonUtils;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class CornerWeightControllController extends AbstractController implements FormControll<Double> {
	
	@FXML
	private GridPane gridPane;
	
	@FXML
	private Label label;
	
	@FXML
	private Slider slider;
	
	@FXML
	private Text unitText;
	
	@Setter
	@Accessors(fluent = true)
	private VoidCallable onChange;
	
	private DoubleInputField valueField = new DoubleInputField();
	
	private String labelValue;
	private double sliderStartValue;
	private boolean initialized;
	
	
	public CornerWeightControllController(String labelValue, double sliderStartValue) {
		this.labelValue = labelValue;
		this.sliderStartValue = sliderStartValue;
	}

	@Override
	public void initialize() throws Exception {
		label.setText(labelValue);
		PauseTransition debounceTime = DebounceTime.get(500);
		
		slider.setValue(sliderStartValue);
		slider.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (initialized) {
				DebounceTime.withDebounceTime(() -> onSliderChanged(), debounceTime);
			}
		});
		
		valueField.load(getStage());
		valueField
		.addValidator(new RequiredValidator())
		.addValidator(new DoubleMinMaxValidator(100.00, 1300.00))
		.setAlignment(Pos.CENTER_RIGHT)
		.setPromptText("100.25")
		.setValue(sliderStartValue)
		.onChange(() -> {
			DebounceTime.withDebounceTime(() -> onValueFieldChanged(), debounceTime);
		});
		
		gridPane.add(valueField.getPane(), 1, 1);
		GridPane.setHalignment(valueField.getPane(), HPos.CENTER);
		GridPane.setValignment(valueField.getPane(), VPos.CENTER);
		GridPane.setMargin(valueField.getPane(), new Insets(10.00, 0.0, 0.0, 0.0));
		
		initialized = true;
	}
	
	private void onSliderChanged() throws Exception {
		double oldFieldValue = valueField.getValue();
		double value = CommonUtils.roundTwoDecimals(slider.getValue());
		valueField.setValue(value);
		double newFieldValue = valueField.getValue();
		
		if (newFieldValue != oldFieldValue) {
			onChange();		
		}
	}
	
	private void onValueFieldChanged() throws Exception {
		double oldSliderValue = CommonUtils.roundTwoDecimals(slider.getValue());
		
		valueField.validate();
		
		if (valueField.isValid()) {
			slider.setValue(valueField.getValue());	
		}
		
		double newSliderValue = CommonUtils.roundTwoDecimals(slider.getValue());
		
		if (newSliderValue != oldSliderValue) {
			onChange();	
		}
	}
	
	private void onChange() throws Exception {
		if (onChange != null) {
			onChange.call();	
		}
	}
	
	@Override
	public Double getValue() {
		return valueField.getValue();
	}

	@Override
	public boolean isValid() {
		return valueField.isValid();
	}

	@Override
	public void validate() {
		valueField.validate();
	}
}
