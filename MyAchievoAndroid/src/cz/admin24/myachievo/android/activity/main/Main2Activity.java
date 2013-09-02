package cz.admin24.myachievo.android.activity.main;

import java.util.Collections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import cz.admin24.myachievo.android.R;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.EditWorkActivity;
import cz.admin24.myachievo.android.activity.main.adapter.WorkReportsAdapter;
import cz.admin24.myachievo.android.activity.main.task.FetchSaveShowWorkReportsTask;
import cz.admin24.myachievo.android.activity.main.task.GetLocalWorkReportsTask;

public class Main2Activity extends BaseActivity {
    public static final Integer RESULT_REGISTRED_WORK = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button registerWork = (Button) findViewById(R.id.main2_registerWorkBtn);
        ListView reportView = (ListView) findViewById(R.id.main2_reportListView);

        reportView.setAdapter(WorkReportsAdapter.getInstance(this, Collections.EMPTY_LIST));

        reportView.setOnItemClickListener(new ItemClickListener());

        new GetLocalWorkReportsTask(this, reportView).execute();
        new FetchSaveShowWorkReportsTask(this, reportView).execute();
        registerWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerWorkIntent = new Intent(Main2Activity.this, EditWorkActivity.class);
                startActivityForResult(registerWorkIntent, RESULT_REGISTRED_WORK);
            }
        });
    }

    public class ItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object itm = parent.getItemAtPosition(position);
            if (itm == WorkReportsAdapter.REFRESH_ENTRY_PLACEHOLDER_TOP) {
                ListView reportView = (ListView) findViewById(R.id.main2_reportListView);
                new FetchSaveShowWorkReportsTask(Main2Activity.this, reportView).execute();
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_REGISTRED_WORK && resultCode == RESULT_REGISTRED_WORK) {
            ListView reportView = (ListView) findViewById(R.id.main2_reportListView);
            new FetchSaveShowWorkReportsTask(Main2Activity.this, reportView).execute();
        }

    }

}
