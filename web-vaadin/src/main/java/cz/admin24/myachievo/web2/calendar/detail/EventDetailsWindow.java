package cz.admin24.myachievo.web2.calendar.detail;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.admin24.myachievo.connector.http.dto.PhaseActivity;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;
import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web2.SpringUtils;
import cz.admin24.myachievo.web2.calendar.WorkReportEvent;
import cz.admin24.myachievo.web2.service.ProjectsCache;

public class EventDetailsWindow extends Window {
    private static final long    serialVersionUID   = 1L;
    private static final Object  PROPERTY_ID        = "ID";
    private final VerticalLayout content            = new VerticalLayout();
    // private final EventDetailFieldGroup form = new EventDetailFieldGroup();
    private final FormLayout     formLayout         = new FormLayout();
    private final ComboBox       projectCombo       = new ComboBox("Project");
    private final ComboBox       phaseCombo         = new ComboBox("Phase");
    private final ComboBox       activityCombo      = new ComboBox("Activity");
    private final ComboBox       remarkAutoComplete = new ComboBox("Remark");
    private final DateField      dayDateField       = new DateField("Date");
    private final ComboBox       hoursCombo         = new ComboBox("Hours");
    private final ComboBox       minutesCombo       = new ComboBox("Minutes");
    //
    private final Button         submitBtn          = new Button("Save");

    private final ProjectsCache  projectsCache      = SpringUtils.getBean(ProjectsCache.class);


    public EventDetailsWindow(WorkReportEvent calendarEvent) {
        super("Event detail");
        setContent(content);
        buildLayout();
        buildForm();
        configureForm();
        refreshForm(calendarEvent.getWorkReport());
        css();

    }


    private void buildLayout() {
        content.addComponent(formLayout);
        content.addComponent(submitBtn);

    }


    private void css() {
        center();
        content.setMargin(true);
        content.setSpacing(true);

        content.setComponentAlignment(submitBtn, Alignment.MIDDLE_RIGHT);
    }


    private void refreshForm(WorkReport r) {
        projectCombo.setValue(r.getProjectClean());
        phaseCombo.setValue(r.getPhase());
        activityCombo.setValue(r.getActivity());
        remarkAutoComplete.setValue(r.getRemark());
        dayDateField.setValue(r.getDate());
        hoursCombo.setValue(r.getHours());
        minutesCombo.setValue(r.getMinutes());
    }


    private void configureForm() {
        // projectCombo.setInvalidAllowed(true);
        // projectCombo.setNewItemsAllowed(true);
        projectCombo.setImmediate(true);
        phaseCombo.setImmediate(true);
        activityCombo.setImmediate(true);

        projectCombo.addContainerProperty(PROPERTY_ID, String.class, null);
        phaseCombo.addContainerProperty(PROPERTY_ID, String.class, null);
        activityCombo.addContainerProperty(PROPERTY_ID, String.class, null);

        projectCombo.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                configurePhaseCombo();
            }
        });
        phaseCombo.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                configureActivityCombo();
            }

        });

        projectCombo.removeAllItems();
        for (Project p : projectsCache.getProjects()) {
            Item item = projectCombo.addItem(p.getName());
            item.getItemProperty(PROPERTY_ID).setValue(p.getId());
        }

    }


    private void configurePhaseCombo() {
        phaseCombo.removeAllItems();
        String projectId = getSelectedId(projectCombo);
        if (projectId == null) {
            return;
        }

        for (ProjectPhase p : projectsCache.getPhases(projectId)) {
            Item item = phaseCombo.addItem(p.getName());
            item.getItemProperty(PROPERTY_ID).setValue(p.getId());
        }
    }


    private void configureActivityCombo() {
        activityCombo.removeAllItems();
        String projectId = getSelectedId(projectCombo);
        String phaseId = getSelectedId(phaseCombo);
        if (projectId == null || phaseId == null) {
            return;
        }

        for (PhaseActivity a : projectsCache.getActivities(projectId, phaseId)) {
            Item item = activityCombo.addItem(a.getName());
            item.getItemProperty(PROPERTY_ID).setValue(a.getId());
        }
    }


    private String getSelectedId(ComboBox cb) {
        Object value = cb.getValue();
        if (value == null) {
            return null;
        }
        return (String) cb.getItem(value).getItemProperty(PROPERTY_ID).getValue();
    }


    private void buildForm() {
        formLayout.addComponent(projectCombo);
        formLayout.addComponent(phaseCombo);
        formLayout.addComponent(activityCombo);
        formLayout.addComponent(remarkAutoComplete);
        formLayout.addComponent(dayDateField);
        formLayout.addComponent(hoursCombo);
        formLayout.addComponent(minutesCombo);
    }

}
