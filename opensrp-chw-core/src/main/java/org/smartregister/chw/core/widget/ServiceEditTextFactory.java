package org.smartregister.chw.core.widget;

import android.content.Context;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vijay.jsonwizard.fragments.JsonFormFragment;

import org.json.JSONObject;
import org.smartregister.child.widgets.ChildEditTextFactory;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.watchers.HIA2ReportFormTextWatcher;

public class ServiceEditTextFactory extends ChildEditTextFactory {

    @Override
    public void attachLayout(String stepName, Context context, JsonFormFragment formFragment, JSONObject jsonObject,
                             MaterialEditText editText, ImageView editable) throws Exception {
        super.attachLayout(stepName, context, formFragment, jsonObject, editText, editable);

        if (jsonObject.has(CoreConstants.KEY_INDICATORS.HIA_2_INDICATOR)) {
            editText.setTag(jsonObject.get(CoreConstants.KEY_INDICATORS.HIA_2_INDICATOR));

            editText.addTextChangedListener(new HIA2ReportFormTextWatcher(formFragment, jsonObject.get(CoreConstants.KEY_INDICATORS.HIA_2_INDICATOR).toString()));
        }
    }
}
