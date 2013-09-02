package cz.admin24.myachievo.android.activity.base;

import java.io.IOException;

import cz.admin24.myachievo.android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ExceptionDialogFragment<Params> extends DialogFragment {

    private final Params[]     params;
    private final BaseActivity activity;
    private final IOException  exception;


    public ExceptionDialogFragment(BaseActivity activity, Params[] params, IOException exception) {
        super();
        this.activity = activity;
        this.params = params;
        this.exception = exception;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.base_activity_error_occured + exception.getLocalizedMessage())
                .setNeutralButton(R.string.base_activity_error_occured_OK, new DialogInterface.OnClickListener() {
                    /*
                     * (non-Javadoc)
                     *
                     * @see android.content.DialogInterface.OnClickListener#onClick(android.content.
                     * DialogInterface, int)
                     */
                    public void onClick(DialogInterface dialog, int id) {
                        // close...
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
