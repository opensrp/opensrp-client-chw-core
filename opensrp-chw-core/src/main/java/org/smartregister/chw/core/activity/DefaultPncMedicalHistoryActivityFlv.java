package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.smartregister.chw.anc.domain.GroupedVisit;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.domain.VisitDetail;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.dao.PNCDao;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DefaultPncMedicalHistoryActivityFlv implements CorePncMedicalHistoryActivity.Flavor {

    protected LayoutInflater inflater;
    protected LinearLayout linearLayoutLastVisit;
    protected TextView customFontTextViewLastVisit;
    protected LinearLayout linearLayoutChildVisitDetails;
    protected LinearLayout linearLayoutMotherVisitDetails;
    protected LinearLayout linearLayoutMotherPncHFVisit;
    protected TextView customFontTextViewMotherTitle;
    protected LinearLayout linearLayoutPncHealthFacilityVisit;
    protected LinearLayout linearLayoutHealthFacilityVisitDetails;
    protected LinearLayout linearLayoutPncFamilyPlanning;
    protected LinearLayout linearLayoutPncFamilyPlanningDetails;
    protected View viewHFVisitsRow;
    protected LinearLayout linearLayoutPncChildVisit;
    protected TextView customFontTextViewChildTitle;
    protected LinearLayout linearLayoutPncChildVaccineCard;
    protected LinearLayout linearLayoutPncChildVaccineDetails;
    protected View viewVaccineCardRow;
    protected LinearLayout linearLayoutPncImmunization;
    protected LinearLayout linearLayoutPncImmunizationDetails;
    protected View viewImmunizationRow;
    protected LinearLayout linearLayoutPncExclusiveBreastfeeding;
    protected LinearLayout linearLayoutPncExclusiveBreastfeedingDetails;

    @Override
    public View bindViews(Activity activity) {
        inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.pnc_medical_history_details, null);
        View motherContainerView = inflater.inflate(R.layout.pnc_mother_medical_history_details, null);
        View childContainerView = inflater.inflate(R.layout.pnc_child_medical_history_details, null);

        linearLayoutMotherVisitDetails = view.findViewById(R.id.linearLayoutMotherVisitDetails);
        linearLayoutChildVisitDetails = view.findViewById(R.id.linearLayoutChildVisitDetails);
        linearLayoutLastVisit = view.findViewById(R.id.linearLayoutLastVisit);
        customFontTextViewLastVisit = view.findViewById(R.id.customFontTextViewLastVisit);
        linearLayoutMotherPncHFVisit = motherContainerView.findViewById(R.id.linearLayoutMotherPncHealthFacilityVisit);
        customFontTextViewMotherTitle = motherContainerView.findViewById(R.id.customFontTextViewPncHealthFacilityVisitMotherTitle);
        linearLayoutPncHealthFacilityVisit = motherContainerView.findViewById(R.id.linearLayoutPncHealthFacilityVisit);
        viewHFVisitsRow = motherContainerView.findViewById(R.id.viewHealthFacilityVisitsRow);
        linearLayoutHealthFacilityVisitDetails = motherContainerView.findViewById(R.id.linearLayoutPncHealthFacilityVisitDetails);
        linearLayoutPncFamilyPlanning = motherContainerView.findViewById(R.id.linearLayoutPncFamilyPlanning);
        linearLayoutPncFamilyPlanningDetails = motherContainerView.findViewById(R.id.linearLayoutPncFamilyPlanningDetails);
        linearLayoutPncChildVisit = childContainerView.findViewById(R.id.linearLayoutPncChildVisit);
        customFontTextViewChildTitle = childContainerView.findViewById(R.id.customFontTextViewPncChildVisitTitle);
        linearLayoutPncChildVaccineCard = childContainerView.findViewById(R.id.linearLayoutPncChildVisitVaccineCard);
        linearLayoutPncChildVaccineDetails = childContainerView.findViewById(R.id.linearLayoutPncChildVisitVaccineCardDetails);
        viewVaccineCardRow = childContainerView.findViewById(R.id.viewChildVaccineCardRow);
        linearLayoutPncImmunization = childContainerView.findViewById(R.id.linearLayoutPncChildVisitImmunizations);
        linearLayoutPncImmunizationDetails = childContainerView.findViewById(R.id.linearLayoutPncChildVisitImmunizationsDetails);
        viewImmunizationRow = childContainerView.findViewById(R.id.viewChildImmunizationsRow);
        linearLayoutPncExclusiveBreastfeeding = childContainerView.findViewById(R.id.linearLayoutPncChildVisitExclusiveBreastfeeding);
        linearLayoutPncExclusiveBreastfeedingDetails = childContainerView.findViewById(R.id.linearLayoutPncChildVisitExclusiveBreastfeedingDetails);

        linearLayoutMotherVisitDetails.addView(motherContainerView);
        linearLayoutChildVisitDetails.addView(childContainerView);


        return view;
    }

    @Override
    public void processViewData(List<GroupedVisit> groupedVisits, Context context, MemberObject memberObject) {
        if (groupedVisits.size() > 0) {

            linearLayoutMotherPncHFVisit.setVisibility(View.VISIBLE);
            linearLayoutPncChildVisit.setVisibility(View.VISIBLE);

            for (GroupedVisit groupedVisit : groupedVisits) {
                // Process mother's details
                if (groupedVisit.getBaseEntityId().equals(memberObject.getBaseEntityId())) {
                    customFontTextViewMotherTitle.setText(memberObject.getFullName());
                    processMotherDetails(groupedVisit.getVisitList(), context);
                } else {
                    // Process child's details
                    customFontTextViewChildTitle.setText(groupedVisit.getName());
                    processChildDetails(groupedVisit.getVisitList(), context);
                }
            }
        }
    }

    @NotNull
    protected String getText(@Nullable List<VisitDetail> visitDetails) {
        if (visitDetails == null) {
            return "";
        }
        List<String> vals = new ArrayList<>();
        for (VisitDetail vd : visitDetails) {
            String val = getText(vd);
            if (StringUtils.isNotBlank(val)) {
                vals.add(val);
            }
        }
        return toCSV(vals);
    }

    protected void processMotherDetails(List<Visit> visits, Context context) {
        int days = 0;
        int x = 0;
        Map<String, Map<String, String>> healthFacility_visit = new HashMap<>();
        Map<String, String> family_planning = new HashMap<>();

        while (x < visits.size()) {
            // the first object in this list is the days difference
            if (x == 0) {
                days = Days.daysBetween(new DateTime(visits.get(0).getDate()), new DateTime()).getDays();
            }
            x++;
        }

        for (Visit visit : visits) {
            for (Map.Entry<String, List<VisitDetail>> entry : visit.getVisitDetails().entrySet()) {
                String val = getText(entry.getValue());

                switch (entry.getKey()) {
                    // health facility
                    case "pnc_visit_1":
                    case "pnc_visit_2":
                    case "pnc_visit_3":

                        String date_key = "pnc_hf_visit1_date";
                        if (entry.getKey().equals("pnc_visit_2")) {
                            date_key = "pnc_hf_visit2_date";
                        }
                        if (entry.getKey().equals("pnc_visit_3")) {
                            date_key = "pnc_hf_visit3_date";
                        }

                        if ("Yes".equalsIgnoreCase(val)) {
                            Map<String, String> map = new HashMap<>();
                            // add details
                            map.put("pnc_hf_visit_date", getText(visit.getVisitDetails().get(date_key)));
                            map.put("baby_weight", getText(visit.getVisitDetails().get("baby_weight")));
                            map.put("baby_temp", getText(visit.getVisitDetails().get("baby_temp")));
                            healthFacility_visit.put(entry.getKey(), map);
                        }
                        break;

                    // family planing
                    case "fp_method":
                    case "fp_start_date":
                        family_planning.put(getText(visit.getVisitDetails().get("fp_method")), getText(visit.getVisitDetails().get("fp_start_date")));
                        break;
                }
            }
        }

        processLastVisit(days, context);
        processHealthFacilityVisit(healthFacility_visit, context);
        processFamilyPlanning(family_planning, context);
    }

    protected void processChildDetails(List<Visit> visits, Context context) {
        String vaccineCard = context.getString(R.string.pnc_no);
        String vaccineCardDate = "";
        String earlyBreastFeeding = "";
        Map<String, String> immunization = new HashMap<>();
        Map<String, String> growth_data = new HashMap<>();

        for (Visit visit : visits) {
            for (Map.Entry<String, List<VisitDetail>> entry : visit.getVisitDetails().entrySet()) {
                String val = getText(entry.getValue());

                switch (entry.getKey()) {
                    // vaccine card
                    case "vaccine_card":
                        if ("No".equalsIgnoreCase(vaccineCard) && "Yes".equalsIgnoreCase(val)) {
                            vaccineCard = context.getString(R.string.pnc_yes);
                        }
                        break;
                    // immunization
                    case "opv0":
                    case "bcg":
                        immunization.put(entry.getKey(), val);
                        break;
                    // growth and nutrition
                    case "exclusive_breast_feeding":
                        growth_data.put(entry.getKey(), val);
                        break;
                }
            }

            earlyBreastFeeding = PNCDao.earlyBreastFeeding(visit.getBaseEntityId(), visit.getVisitId());

            if (earlyBreastFeeding != null && earlyBreastFeeding.equalsIgnoreCase("Yes")) {
                earlyBreastFeeding = context.getString(R.string.pnc_yes);
            } else if (earlyBreastFeeding != null && earlyBreastFeeding.equalsIgnoreCase("No")) {
                earlyBreastFeeding = context.getString(R.string.pnc_no);
            }
        }

        processVaccineCard(vaccineCard, vaccineCardDate, context);
        processImmunization(immunization, context);
        processGrowthAndNutrition(growth_data, context, earlyBreastFeeding);
    }

    protected void processLastVisit(int days, Context context) {
        linearLayoutLastVisit.setVisibility(View.VISIBLE);
        customFontTextViewLastVisit.setText(StringUtils.capitalize(MessageFormat.format(context.getString(R.string.days_ago_for_pnc_home_visit), String.valueOf(days))));
    }

    protected void processHealthFacilityVisit(Map<String, Map<String, String>> healthFacility_visit, Context context) {
        if (healthFacility_visit != null && healthFacility_visit.size() > 0) {
            linearLayoutPncHealthFacilityVisit.setVisibility(View.VISIBLE);
            for (Map.Entry<String, Map<String, String>> entry : healthFacility_visit.entrySet()) {
                View visitDetailsView = inflater.inflate(R.layout.medical_history_pnc_hf_visit_details, null);

                TextView tvTitle = visitDetailsView.findViewById(R.id.pncHealthFacilityVisit);
                TextView tvbabyWeight = visitDetailsView.findViewById(R.id.babyWeight);
                TextView tvbabyTemp = visitDetailsView.findViewById(R.id.babyTemp);
                tvTitle.setText(MessageFormat.format(context.getString(R.string.pnc_wcaro_health_facility_visit), entry.getValue().get("pnc_hf_visit_date")));
                if (entry.getValue().get("baby_weight") != null) {
                    tvbabyWeight.setVisibility(View.VISIBLE);
                    tvbabyWeight.setText(context.getString(R.string.pnc_baby_weight, entry.getValue().get("baby_weight")));
                }
                if (entry.getValue().get("baby_temp") != null) {
                    tvbabyTemp.setVisibility(View.VISIBLE);
                    tvbabyTemp.setText(context.getString(R.string.pnc_baby_temp, entry.getValue().get("baby_temp")));
                }
                linearLayoutHealthFacilityVisitDetails.addView(visitDetailsView, 0);
            }
            viewHFVisitsRow.setVisibility(View.VISIBLE);
        }
    }

    protected void processFamilyPlanning(Map<String, String> family_plnning, Context context) {
        if (family_plnning != null && family_plnning.size() > 0) {
            linearLayoutPncFamilyPlanning.setVisibility(View.VISIBLE);

            for (Map.Entry<String, String> entry : family_plnning.entrySet()
            ) {
                View familyPlanningDetailsView = inflater.inflate(R.layout.medical_history_pnc_family_planning_details, null);
                if (entry.getKey() != null) {
                    TextView tvPncFamilyPlanningMethod = familyPlanningDetailsView.findViewById(R.id.pncFamilyPlanningMethod);
                    String method = "";
                    switch (entry.getKey()) {
                        case "None":
                            method = context.getString(R.string.pnc_none);
                            break;
                        case "Abstinence":
                            method = context.getString(R.string.pnc_abstinence);
                            break;
                        case "Condom":
                            method = context.getString(R.string.pnc_condom);
                            break;
                        case "Tablets":
                            method = context.getString(R.string.pnc_tablets);
                            break;
                        case "Injectable":
                            method = context.getString(R.string.pnc_injectable);
                            break;
                        case "IUD":
                            method = context.getString(R.string.pnc_iud);
                            break;
                        case "Implant":
                            method = context.getString(R.string.pnc_implant);
                            break;
                        case "Other":
                            method = context.getString(R.string.pnc_other);
                            break;
                    }
                    tvPncFamilyPlanningMethod.setVisibility(View.VISIBLE);
                    tvPncFamilyPlanningMethod.setText(MessageFormat.format(context.getString(R.string.pnc_family_planning_method), method));

                }
                if (entry.getValue() != null) {
                    TextView tvPncFamilyPlanningDate = familyPlanningDetailsView.findViewById(R.id.pncFamilyPlanningDate);
                    tvPncFamilyPlanningDate.setVisibility(View.VISIBLE);
                    tvPncFamilyPlanningDate.setText(MessageFormat.format(context.getString(R.string.pnc_family_planning_date), StringUtils.isNotBlank(entry.getValue()) ? entry.getValue() : "n/a"));

                }
                linearLayoutPncFamilyPlanningDetails.addView(familyPlanningDetailsView, 0);
            }

        }
    }

    protected void processVaccineCard(String received, String vaccineCardDate, Context context) {
        if (received != null) {
            linearLayoutPncChildVaccineCard.setVisibility(View.VISIBLE); // TODO :: Move this to where list of vaccinations are defined
            View vaccineDetailsView = inflater.inflate(R.layout.medical_history_pnc_child_vaccine_card_details, null);

            TextView tvPncVaccineCardReceived = vaccineDetailsView.findViewById(R.id.pncChildVaccineCardReceived);
            tvPncVaccineCardReceived.setText(MessageFormat.format(context.getString(R.string.pnc_medical_history_child_vaccine_card_received), received));
            TextView tvPncVaccineCardDate = vaccineDetailsView.findViewById(R.id.pncChildVaccineCardDate);
            tvPncVaccineCardDate.setText(MessageFormat.format(context.getString(R.string.pnc_medical_history_child_vaccine_card_date), StringUtils.isNotBlank(vaccineCardDate) ? vaccineCardDate : "n/a"));
            linearLayoutPncChildVaccineDetails.addView(vaccineDetailsView, 0);
            viewVaccineCardRow.setVisibility(View.VISIBLE);
        }
    }

    protected void processImmunization(Map<String, String> immunization, Context context) {
        if (immunization != null && immunization.size() > 0) {
            linearLayoutPncImmunization.setVisibility(View.VISIBLE);
            View immunizationDetailsView = inflater.inflate(R.layout.medical_history_pnc_immunization_details, null);

            for (Map.Entry<String, String> entry : immunization.entrySet()) {
                if (entry.getValue() != null) {
                    String entryValue = entry.getValue().equalsIgnoreCase("Vaccine not given") ? context.getString(R.string.pnc_vaccine_not_given) : entry.getValue();
                    if (entry.getKey().equals("bcg")) {
                        TextView tvBcg = immunizationDetailsView.findViewById(R.id.pncBcg);
                        tvBcg.setVisibility(View.VISIBLE);
                        if (entryValue.equalsIgnoreCase(context.getString(R.string.pnc_vaccine_not_given)))
                            tvBcg.setText(MessageFormat.format(context.getString(R.string.pnc_bcg_not_done), entryValue));
                        else
                            tvBcg.setText(MessageFormat.format(context.getString(R.string.pnc_bcg), entryValue));
                    } else if (entry.getKey().equals("opv0")) {
                        TextView tvOpv0 = immunizationDetailsView.findViewById(R.id.pncOpv0);
                        tvOpv0.setVisibility(View.VISIBLE);
                        if (entryValue.equalsIgnoreCase(context.getString(R.string.pnc_vaccine_not_given)))
                            tvOpv0.setText(MessageFormat.format(context.getString(R.string.pnc_opv0_not_done), entryValue));
                        else
                            tvOpv0.setText(MessageFormat.format(context.getString(R.string.pnc_opv0), entryValue));
                    }
                }
                linearLayoutPncImmunizationDetails.addView(immunizationDetailsView, 0);
            }
            viewImmunizationRow.setVisibility(View.VISIBLE);
        }
    }

    protected void processGrowthAndNutrition(Map<String, String> growth_data, Context context, String earlyBreastFeeding) {
        if (growth_data != null && growth_data.size() > 0) {
            linearLayoutPncExclusiveBreastfeeding.setVisibility(View.VISIBLE);
            View viewExclusiveBreastfeedingDetails = inflater.inflate(R.layout.medical_history_pnc_child_exclusive_breastfeeding_details, null);
            for (Map.Entry<String, String> entry : growth_data.entrySet()) {
                TextView tvpncExcussiveBf = viewExclusiveBreastfeedingDetails.findViewById(R.id.pncChildExclusiveBreastfeeding);
                tvpncExcussiveBf.setVisibility(View.VISIBLE);
                if (entry.getValue().equalsIgnoreCase("YES")) {
                    tvpncExcussiveBf.setText(MessageFormat.format(context.getString(R.string.pnc_exclusive_bf_0_months), context.getString(R.string.pnc_no)));
                } else {
                    tvpncExcussiveBf.setText(MessageFormat.format(context.getString(R.string.pnc_exclusive_bf_0_months), context.getString(R.string.pnc_yes)));
                }
            }
            linearLayoutPncExclusiveBreastfeedingDetails.addView(viewExclusiveBreastfeedingDetails, 0);
        }
    }

    /**
     * Extract value from VisitDetail
     *
     * @return
     */
    @NotNull
    protected String getText(@Nullable VisitDetail visitDetail) {
        if (visitDetail == null) {
            return "";
        }

        String val = visitDetail.getHumanReadable();
        if (StringUtils.isNotBlank(val)) {
            return val.trim();
        }

        return (StringUtils.isNotBlank(visitDetail.getDetails())) ? visitDetail.getDetails().trim() : "";
    }

    protected static String toCSV(List<String> list) {
        String result = "";
        if (list.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String s : list) {
                sb.append(s).append(",");
            }
            result = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return result;
    }


}