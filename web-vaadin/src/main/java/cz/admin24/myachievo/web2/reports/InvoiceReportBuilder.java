package cz.admin24.myachievo.web2.reports;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.base.Objects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.vaadin.ui.UI;

import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web2.service.AchievoConnectorWrapper;

public class InvoiceReportBuilder {
    private static final Logger  LOG          = LoggerFactory.getLogger(InvoiceReportBuilder.class);
    //
    private final FastDateFormat fullFormat   = FastDateFormat.getDateInstance(FastDateFormat.SHORT, UI.getCurrent().getLocale());
    private final FastDateFormat periodFormat = FastDateFormat.getInstance("MMMM yyyy", UI.getCurrent().getLocale());


    public String buildCsv(Date month, Boolean groupByProject, Boolean groupByPhase, Boolean groupByActivty, AchievoConnectorWrapper connector) {
        boolean groupBy = groupByProject || groupByPhase || groupByActivty;
        Date from = DateUtils.truncate(month, Calendar.MONTH);
        Date to = DateUtils.addMilliseconds(DateUtils.addMonths(from, 1), -1);
        List<WorkReport> reportedHours = connector.getHours(from, to);

        Multimap<GroupByKey, WorkReport> groupedValues = ArrayListMultimap.create();

        for (WorkReport r : reportedHours) {
            GroupByKey key = new GroupByKey(r, groupByProject, groupByPhase, groupByActivty);
            groupedValues.put(key, r);
        }

        // TODO Auto-generated method stub
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter, ';');
        try {

            // build header
            List<String> header = Lists.newLinkedList();

            header.add("period");
            if (groupBy) {
                if (groupByProject) {
                    header.add("project");
                }
                if (groupByPhase) {
                    header.add("phase");
                }
                if (groupByActivty) {
                    header.add("activity");
                }
            } else {
                header.add("date");
                header.add("project");
                header.add("phase");
                header.add("activity");
                header.add("remark");
            }
            header.add("hours");
            header.add("minutes");
            header.add("sumHours");
            header.add("mds");

            csvWriter.writeNext(header.toArray(new String[header.size()]));

            // write data
            List<String> data = Lists.newArrayList();
            for (Entry<GroupByKey, Collection<WorkReport>> e : groupedValues.asMap().entrySet()) {
                GroupByKey key = e.getKey();
                Collection<WorkReport> reports = e.getValue();

                if (!groupBy) {
                    for (WorkReport r : reports) {
                        data.clear();
                        data.add(periodFormat.format(r.getDate()));
                        data.add(fullFormat.format(r.getDate()));
                        data.add(r.getProject());
                        data.add(r.getPhase());
                        data.add(r.getActivity());
                        data.add(r.getRemark());
                        data.add("" + r.getHours());
                        data.add("" + r.getMinutes());
                        Double sumHours = (r.getHours() * 60 + r.getMinutes()) / 60.0;
                        data.add(sumHours.toString());
                        Double mds = sumHours / 8;
                        data.add(mds.toString());
                        csvWriter.writeNext(data.toArray(new String[data.size()]));
                    }
                } else {
                    int sumMinutes = 0;
                    data.clear();
                    for (WorkReport r : reports) {
                        sumMinutes += r.getHours() * 60 + r.getMinutes();
                    }
                    data.add(periodFormat.format(month));
                    if (groupByProject) {
                        data.add(key.getProjectName());
                    }
                    if (groupByPhase) {
                        data.add(key.getPhaseName());
                    }
                    if (groupByActivty) {
                        data.add(key.getActivityName());
                    }

                    Integer hours = sumMinutes / 60;
                    Integer minutes = sumMinutes % 60;
                    Double sumHours = sumMinutes / 60.0;
                    Double mds = sumHours / 8;

                    data.add(hours.toString());
                    data.add(minutes.toString());
                    data.add(sumHours.toString());
                    data.add(mds.toString());
                    csvWriter.writeNext(data.toArray(new String[data.size()]));
                }

            }
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                LOG.error("Failed to  close CSV writer", e);
                throw new IllegalStateException("Failed to  close CSV writer", e);
            }
        }
        LOG.debug("Build csv:\n{}", stringWriter);
        return stringWriter.toString();
    }

    private static class GroupByKey {
        private final String projectName;
        private final String phaseName;
        private final String activityName;


        public GroupByKey(WorkReport r, Boolean groupByProject, Boolean groupByPhase, Boolean groupByActivty) {
            if (groupByProject) {
                projectName = r.getProject();
            } else {
                projectName = null;
            }
            if (groupByPhase) {
                phaseName = r.getPhase();
            } else {
                phaseName = null;
            }
            if (groupByActivty) {
                activityName = r.getActivity();
            } else {
                activityName = null;
            }
        }


        public String getProjectName() {
            return projectName;
        }


        public String getPhaseName() {
            return phaseName;
        }


        public String getActivityName() {
            return activityName;
        }


        @Override
        public int hashCode() {
            return Objects.hashCode(projectName, phaseName, activityName);
        }


        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

    }
}