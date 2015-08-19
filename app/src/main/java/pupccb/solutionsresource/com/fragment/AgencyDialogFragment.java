package pupccb.solutionsresource.com.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.AgencyAdapter;
import pupccb.solutionsresource.com.model.Agency;

/**
 * Created by User on 8/27/2015.
 */
@SuppressLint("ValidFragment")
public class AgencyDialogFragment extends DialogFragment {

    private Communicator communicator;
    private int requestCode;
    private Button negativeButton;
    private AgencyAdapter agencyAdapter;
    public AgencyDialogFragment(){

    }

    public AgencyDialogFragment(AgencyAdapter agencyAdapter, int requestCode) {
        this.agencyAdapter = agencyAdapter;
        this.requestCode = requestCode;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_agency_list, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .setTitle(getArguments().getString("title"));
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        findViewById(view, alertDialog);
        return alertDialog;
    }

    private void findViewById(View view, AlertDialog alertDialog) {
        communicator = (Communicator) getActivity();
        negativeButton = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(buttonListener);

        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String string) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String string) {
                if (agencyAdapter != null) {
                    agencyAdapter.getFilter().filter(string);
                }
                return false;
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setEmptyView(view.findViewById(R.id.textViewEmpty));

        if(agencyAdapter != null && agencyAdapter.getCount() > 0){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    communicator.dialogSelectedAgency(agencyAdapter.getItem((int) id), requestCode);
                    getDialog().dismiss();
                }
            });
            listView.setAdapter(agencyAdapter);
        }
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == negativeButton){
                getDialog().dismiss();
            }
        }
    };

    public interface Communicator{
        void dialogSelectedAgency(Agency agency, int requestCode);
    }
}
