package pupccb.solutionsresource.com.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.AgencyAdapter;
import pupccb.solutionsresource.com.adapter.LocationAdapter;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.model.Agencies;
import pupccb.solutionsresource.com.util.ToastMessage;

/**
 * Created by User on 8/27/2015.
 */
@SuppressLint("ValidFragment")
public class AddAgencyDialogFragment extends DialogFragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private Communicator communicator;
    private GoogleApiClient mGoogleApiClient;
    private LocationAdapter mLocationAdapter;
    private EditText editTextAgencyName;
    private AutoCompleteTextView mAutocompleteTextView;
    private Button negativeButton, positiveButton;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(6.038410, 113.075581), new LatLng(19.131154, 129.049702));

    private LoadToast loadToast;
    private boolean isGoing;
    private Controller onlineController;
    public AddAgencyDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_add_new_agency, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setNegativeButton(getString(R.string.cancel), null)
                .setPositiveButton(getString(R.string.add), null)
                .setTitle(getArguments().getString("title"));
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        findViewById(view, alertDialog);
        return alertDialog;
    }

    private void findViewById(View view, AlertDialog alertDialog) {
        communicator = (Communicator) getActivity();
        loadToast = new LoadToast(getActivity());
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, null)
                .addConnectionCallbacks(this)
                .build();
        mLocationAdapter = new LocationAdapter(getContext(), android.R.layout.simple_list_item_1, LAT_LNG_BOUNDS, null);
        editTextAgencyName = (EditText) view.findViewById(R.id.editTextNewAgencyName);
        mAutocompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextViewLocation);
        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mAutocompleteTextView.setAdapter(mLocationAdapter);
        negativeButton = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(buttonListener);
        positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(buttonListener);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final LocationAdapter.PlaceAutocomplete item = mLocationAdapter.getItem(position);
            final String placeId = String.valueOf(item.getPlaceId());
            //Log.i(LOG_TAG, "Selected: " + item.getDescription());
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            //Log.i(LOG_TAG, "Fetching details for ID: " + item.getPlaceId());
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                //Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                return;
            }
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        //Log.i(LOG_TAG, "Google Places API connected.");
        mLocationAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Log.e(LOG_TAG, "Google Places API connection failed with error code: " + connectionResult.getErrorCode());
        Toast.makeText(getContext(), "Google Places API connection failed with error code:" + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Log.e(LOG_TAG, "Google Places API connection suspended.");
        mLocationAdapter.setGoogleApiClient(null);
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == negativeButton) {
            }
            if (view == positiveButton) { //Pass the values to the service going to the API
                communicator.createdAgency(new Agencies(editTextAgencyName.getText().toString(), mAutocompleteTextView.getText().toString()));
                //createAgency(new Agencies(editTextAgencyName.getText().toString(), mAutocompleteTextView.getText().toString()));
            }
            mGoogleApiClient.stopAutoManage(getActivity());
            getDialog().dismiss();
            communicator.dismissAgencyList();

        }
    };

    public void createAgency(Agencies agency) {
        isGoing = false;
        loadToast("Creating Agency...");
        onlineController.createAgency(getActivity(), agency);
    }

    public void createAgencyResult(boolean value, Agencies agency) {
        //toast.make(2000, "SUCCESS", ToastMessage.MessageType.SUCCESS);
        loadToast.success();
//        this.agencies = agency;
//        Toast.makeText(this, agencies.getId().toString(), Toast.LENGTH_LONG).show();
    }
    private void loadToast(String message) {
        loadToast.setProgressColor(R.color.selected_selected_blue);
        loadToast.setProgressColor(R.color.half_black);
        loadToast.setTranslationY(200);
        loadToast.setText(message);
        loadToast.show();
    }

    public void agencies() {
        isGoing = true;
        loadToast("Loading...");
        onlineController.agencies(getActivity());
    }

    public void agenciesResult(boolean value, List<Agencies> list) {
        if (list != null && list.size() > 0) {
            showAgencyListDialog("Select Agency",
                    new AgencyAdapter(getContext(), R.layout.two_line_list_item,
                            null,
                            (ArrayList<Agencies>) list,
                            AgencyAdapter.MethodTypes.READ));

            loadToast.success();
        } else {
            showAgencyListDialog("Select Agency",
                    new AgencyAdapter(getContext(), R.layout.two_line_list_item,
                            null,
                            (ArrayList<Agencies>) list,
                            AgencyAdapter.MethodTypes.READ));
            loadToast.error();
        }
        isGoing = false;
    }

    public void showAgencyListDialog(String title, AgencyAdapter adapter) {
        DialogFragment dialogFragment = new AgencyDialogFragment(adapter, 0);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "agencyListDialog");
    }
    public interface Communicator {
        void createdAgency(Agencies agency);

        void dismissAgencyList();
    }
}
