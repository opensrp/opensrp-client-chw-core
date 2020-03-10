package org.smartregister.chw.core.watchers;

import android.text.Editable;
import android.text.TextWatcher;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vijay.jsonwizard.fragments.JsonFormFragment;

import java.util.HashMap;
import java.util.Map;

public class HIA2ReportFormTextWatcher implements TextWatcher {
    private static final Map<String, String[]> aggregateFieldsMap;

    static {
        aggregateFieldsMap = new HashMap<>();
        aggregateFieldsMap.put("EPNT", new String[]{"EPNT_M", "EPNT_F"});
        aggregateFieldsMap.put("BCG", new String[]{"BCG_M", "BCG_F"});
        aggregateFieldsMap.put("BCG_plus12m", new String[]{"BCG_M_plus12m", "BCG_F_plus12m"});
        aggregateFieldsMap.put("HepBnaissance", new String[]{"HepBnaissance_M", "HepBnaissance_F"});
        aggregateFieldsMap.put("VPO_0", new String[]{"VPO_0_M", "VPO_0_F"});
        aggregateFieldsMap.put("VPO_1", new String[]{"VPO_1_M", "VPO_1_F"});
        aggregateFieldsMap.put("VPO_1_plus12m", new String[]{"VPO_1_M_plus12m", "VPO_1_F_plus12m"});
        aggregateFieldsMap.put("VPO_2", new String[]{"VPO_2_M", "VPO_2_F"});
        aggregateFieldsMap.put("VPO_2_plus12m", new String[]{"VPO_2_M_plus12m", "VPO_2_F_plus12m"});
        aggregateFieldsMap.put("VPO_3", new String[]{"VPO_3_M", "VPO_3_F"});
        aggregateFieldsMap.put("VPO_3_plus12m", new String[]{"VPO_3_M_plus12m", "VPO_3_F_plus12m"});
        aggregateFieldsMap.put("VPI", new String[]{"VPI_M", "VPI_F"});
        aggregateFieldsMap.put("VPI_plus12m", new String[]{"VPI_M_plus12m", "VPI_F_plus12m"});
        aggregateFieldsMap.put("Penta_1", new String[]{"Penta_1_M", "Penta_1_F"});
        aggregateFieldsMap.put("Penta_1_plus12m", new String[]{"Penta_1_M_plus12m", "Penta_1_F_plus12m"});
        aggregateFieldsMap.put("Penta_2", new String[]{"Penta_2_M", "Penta_2_F"});
        aggregateFieldsMap.put("Penta_2_plus12m", new String[]{"Penta_2_M_plus12m", "Penta_2_F_plus12m"});
        aggregateFieldsMap.put("Penta_3", new String[]{"Penta_3_M", "Penta_3_F"});
        aggregateFieldsMap.put("Penta_3_plus12m", new String[]{"Penta_3_M_plus12m", "Penta_3_F_plus12m"});
        aggregateFieldsMap.put("Pneumo_1", new String[]{"Pneumo_1_M", "Pneumo_1_F"});
        aggregateFieldsMap.put("Pneumo_1_plus12m", new String[]{"Pneumo_1_M_plus12m", "Pneumo_1_F_plus12m"});
        aggregateFieldsMap.put("Pneumo_2", new String[]{"Pneumo_2_M", "Pneumo_2_F"});
        aggregateFieldsMap.put("Pneumo_2_plus12m", new String[]{"Pneumo_2_M_plus12m", "Pneumo_2_F_plus12m"});
        aggregateFieldsMap.put("Pneumo_3", new String[]{"Pneumo_3_M", "Pneumo_3_F"});
        aggregateFieldsMap.put("Pneumo_3_plus12m", new String[]{"Pneumo_3_M_plus12m", "Pneumo_3_F_plus12m"});
        aggregateFieldsMap.put("Rota_1", new String[]{"Rota_1_M", "Rota_1_F"});
        aggregateFieldsMap.put("Rota_1_plus12m", new String[]{"Rota_1_M_plus12m", "Rota_1_F_plus12m"});
        aggregateFieldsMap.put("Rota_2", new String[]{"Rota_2_M", "Rota_2_F"});
        aggregateFieldsMap.put("Rota_2_plus12m", new String[]{"Rota_2_M_plus12m", "Rota_2_F_plus12m"});
        aggregateFieldsMap.put("RR_1", new String[]{"RR_1_M", "RR_1_F"});
        aggregateFieldsMap.put("RR_1_plus12m", new String[]{"RR_1_M_plus12m", "RR_1_F_plus12m"});
        aggregateFieldsMap.put("RR_2", new String[]{"RR_2_M", "RR_2_F"});
        aggregateFieldsMap.put("RR_2_plus12m", new String[]{"RR_2_M_plus12m", "RR_2_F_plus12m"});
        aggregateFieldsMap.put("MenA", new String[]{"MenA_M", "MenA_F"});
        aggregateFieldsMap.put("MenA_plus12m", new String[]{"MenA_M_plus12m", "MenA_F_plus12m"});
        aggregateFieldsMap.put("FJaune", new String[]{"FJaune_M", "FJaune_F"});
        aggregateFieldsMap.put("FJaune_plus12m", new String[]{"FJaune_M_plus12m", "FJaune_F_plus12m"});
        aggregateFieldsMap.put("VitA", new String[]{"VitA_M", "VitA_F"});
        aggregateFieldsMap.put("VitA_plus12m", new String[]{"VitA_M_plus12m", "VitA_F_plus12m"});
        aggregateFieldsMap.put("ECV", new String[]{"ECV_M", "ECV_F"});
        aggregateFieldsMap.put("ECV_plus12m", new String[]{"ECV_M_plus12m", "ECV_F_plus12m"});
    }

    private static final Map<String, String> indicatorKeyMap;

    static {
        indicatorKeyMap = new HashMap<>();
        indicatorKeyMap.put("EPNT_M", "EPNT");
        indicatorKeyMap.put("EPNT_F", "EPNT");
        indicatorKeyMap.put("BCG_M", "BCG");
        indicatorKeyMap.put("BCG_F", "BCG");
        indicatorKeyMap.put("BCG_M_plus12m", "BCG_plus12m");
        indicatorKeyMap.put("BCG_F_plus12m", "BCG_plus12m");
        indicatorKeyMap.put("HepBnaissance_M", "HepBnaissance");
        indicatorKeyMap.put("HepBnaissance_F", "HepBnaissance");
        indicatorKeyMap.put("VPO_0_M", "VPO_0");
        indicatorKeyMap.put("VPO_0_F", "VPO_0");
        indicatorKeyMap.put("VPO_1_M", "VPO_1");
        indicatorKeyMap.put("VPO_1_F", "VPO_1");
        indicatorKeyMap.put("VPO_1_M_plus12m", "VPO_1_plus12m");
        indicatorKeyMap.put("VPO_1_F_plus12m", "VPO_1_plus12m");
        indicatorKeyMap.put("VPO_2_M", "VPO_2");
        indicatorKeyMap.put("VPO_2_F", "VPO_2");
        indicatorKeyMap.put("VPO_2_M_plus12m", "VPO_2_plus12m");
        indicatorKeyMap.put("VPO_2_F_plus12m", "VPO_2_plus12m");
        indicatorKeyMap.put("VPO_3_M", "VPO_3");
        indicatorKeyMap.put("VPO_3_F", "VPO_3");
        indicatorKeyMap.put("VPO_3_M_plus12m", "VPO_3_plus12m");
        indicatorKeyMap.put("VPO_3_F_plus12m", "VPO_3_plus12m");
        indicatorKeyMap.put("VPI_M", "VPI");
        indicatorKeyMap.put("VPI_F", "VPI");
        indicatorKeyMap.put("VPI_M_plus12m", "VPI_plus12m");
        indicatorKeyMap.put("VPI_F_plus12m", "VPI_plus12m");
        indicatorKeyMap.put("Penta_1_M", "Penta_1");
        indicatorKeyMap.put("Penta_1_F", "Penta_1");
        indicatorKeyMap.put("Penta_1_M_plus12m", "Penta_1_plus12m");
        indicatorKeyMap.put("Penta_1_F_plus12m", "Penta_1_plus12m");
        indicatorKeyMap.put("Penta_2_M", "Penta_2");
        indicatorKeyMap.put("Penta_2_F", "Penta_2");
        indicatorKeyMap.put("Penta_2_M_plus12m", "Penta_2_plus12m");
        indicatorKeyMap.put("Penta_2_F_plus12m", "Penta_2_plus12m");
        indicatorKeyMap.put("Penta_3_M", "Penta_3");
        indicatorKeyMap.put("Penta_3_F", "Penta_3");
        indicatorKeyMap.put("Penta_3_M_plus12m", "Penta_3_plus12m");
        indicatorKeyMap.put("Penta_3_F_plus12m", "Penta_3_plus12m");
        indicatorKeyMap.put("Pneumo_1_M", "Pneumo_1");
        indicatorKeyMap.put("Pneumo_1_F", "Pneumo_1");
        indicatorKeyMap.put("Pneumo_1_M_plus12m", "Pneumo_1_plus12m");
        indicatorKeyMap.put("Pneumo_1_F_plus12m", "Pneumo_1_plus12m");
        indicatorKeyMap.put("Pneumo_2_M", "Pneumo_2");
        indicatorKeyMap.put("Pneumo_2_F", "Pneumo_2");
        indicatorKeyMap.put("Pneumo_2_M_plus12m", "Pneumo_2_plus12m");
        indicatorKeyMap.put("Pneumo_2_F_plus12m", "Pneumo_2_plus12m");
        indicatorKeyMap.put("Pneumo_3_M", "Pneumo_3");
        indicatorKeyMap.put("Pneumo_3_F", "Pneumo_3");
        indicatorKeyMap.put("Pneumo_3_M_plus12m", "Pneumo_3_plus12m");
        indicatorKeyMap.put("Pneumo_3_F_plus12m", "Pneumo_3_plus12m");
        indicatorKeyMap.put("Rota_1_M", "Rota_1");
        indicatorKeyMap.put("Rota_1_F", "Rota_1");
        indicatorKeyMap.put("Rota_1_M_plus12m", "Rota_1_plus12m");
        indicatorKeyMap.put("Rota_1_F_plus12m", "Rota_1_plus12m");
        indicatorKeyMap.put("Rota_2_M", "Rota_2");
        indicatorKeyMap.put("Rota_2_F", "Rota_2");
        indicatorKeyMap.put("Rota_2_M_plus12m", "Rota_2_plus12m");
        indicatorKeyMap.put("Rota_2_F_plus12m", "Rota_2_plus12m");
        indicatorKeyMap.put("RR_1_M", "RR_1");
        indicatorKeyMap.put("RR_1_F", "RR_1");
        indicatorKeyMap.put("RR_1_M_plus12m", "RR_1_plus12m");
        indicatorKeyMap.put("RR_1_F_plus12m", "RR_1_plus12m");
        indicatorKeyMap.put("RR_2_M", "RR_2");
        indicatorKeyMap.put("RR_2_F", "RR_2");
        indicatorKeyMap.put("RR_2_M_plus12m", "RR_2_plus12m");
        indicatorKeyMap.put("RR_2_F_plus12m", "RR_2_plus12m");
        indicatorKeyMap.put("MenA_M", "MenA");
        indicatorKeyMap.put("MenA_F", "MenA");
        indicatorKeyMap.put("MenA_M_plus12m", "MenA_plus12m");
        indicatorKeyMap.put("MenA_F_plus12m", "MenA_plus12m");
        indicatorKeyMap.put("FJaune_M", "FJaune");
        indicatorKeyMap.put("FJaune_F", "FJaune");
        indicatorKeyMap.put("FJaune_M_plus12m", "FJaune_plus12m");
        indicatorKeyMap.put("FJaune_F_plus12m", "FJaune_plus12m");
        indicatorKeyMap.put("VitA_M", "VitA");
        indicatorKeyMap.put("VitA_F", "VitA");
        indicatorKeyMap.put("VitA_M_plus12m", "VitA_plus12m");
        indicatorKeyMap.put("VitA_F_plus12m", "VitA_plus12m");
        indicatorKeyMap.put("ECV_M", "ECV");
        indicatorKeyMap.put("ECV_F", "ECV");
        indicatorKeyMap.put("ECV_M_plus12m", "ECV_plus12m");
        indicatorKeyMap.put("ECV_F_plus12m", "ECV_plus12m");
    }

    private final JsonFormFragment formFragment;
    private final String hia2Indicator;

    public HIA2ReportFormTextWatcher(JsonFormFragment formFragment, String hi2IndicatorCode) {
        this.formFragment = formFragment;
        hia2Indicator = hi2IndicatorCode;

    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //default overridden method from interface
    }

    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        //default overridden method from interface
    }

    public void afterTextChanged(Editable editable) {

        if (indicatorKeyMap.containsKey(hia2Indicator)) {

            Integer aggregateValue = 0;

            String[] operandIndicators = aggregateFieldsMap.get(indicatorKeyMap.get(hia2Indicator));

            for (int i = 0; i < operandIndicators.length; i++) {
                MaterialEditText editTextIndicatorView = formFragment.getMainView().findViewWithTag(operandIndicators[i]);
                aggregateValue += editTextIndicatorView.getText() == null || editTextIndicatorView.getText().toString().isEmpty() ? 0 : Integer.valueOf(editTextIndicatorView.getText().toString());
            }

            MaterialEditText aggregateEditText = formFragment.getMainView().findViewWithTag(indicatorKeyMap.get(hia2Indicator));
            aggregateEditText.setText(Integer.toString(aggregateValue));
        }
    }
}
