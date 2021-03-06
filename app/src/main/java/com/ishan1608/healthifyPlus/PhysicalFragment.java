package com.ishan1608.healthifyPlus;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.fitness.request.OnDataPointListener;

import java.util.Timer;
import java.util.TimerTask;

public class PhysicalFragment extends Fragment {

    private static final int REQUEST_OAUTH = 1;

    /**
     * Track whether an authorization activity is stacking over the current activity, i.e. when
     * a known auth error is being resolved, such as showing the account chooser or presenting a
     * consent dialog. This avoids common duplications as might happen on screen rotations, etc.
     */
//    private static final String AUTH_PENDING = "auth_state_pending";
    private static final String TAG = "PHYSICAL-FRAGMENT";
//    private boolean authInProgress = false;
    private View returnView;
//    private GoogleApiClient physicalFitnessClient;

    // [START mListener_variable_reference]
    // Need to hold a reference to this listener, as it's passed into the "unregister"
    // method in order to stop all sensors from sending data to this listener.
    private OnDataPointListener mListener;
    private TextView stepCountTodayTextView;
    private TextView milesCountTodayTextView;
    private TextView stepsPerSecondTextView;
    private ImageView stepsBannerImageView;
    private TextView caloriesExpendedTodayTextView;
    private TimerTask stepCountNowRequestTask;
    private Timer stepCountNowRequestTimer;
    private TimerTask googleFitTodayRequestTask;
    private Timer googleFitTodayRequestTimer;
    private Intent stepCountNowIntent;
    // [END mListener_variable_reference]

    public PhysicalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Registering broadcast receiver
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(stepCountTodayReceiver, new IntentFilter(GoogleFitService.STEP_COUNT_TODAY));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(stepCountNowReceiver, new IntentFilter(GoogleFitService.STEPS_PER_SECOND_COUNT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(milesCountTodayReceiver, new IntentFilter(GoogleFitService.MILES_COUNT_TODAY));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(caloriesCountTodayReceiver, new IntentFilter(GoogleFitService.CALORIES_EXPENDED_TODAY));

        // Starting services, just to be on the safe side
        // Creates a new Intent to start the step count now IntentService.
        stepCountNowIntent = new Intent(getActivity(), GoogleFitService.class);
        stepCountNowIntent.setAction(GoogleFitService.STEPS_PER_SECOND_COUNT);
        getActivity().startService(stepCountNowIntent);

        // Creates a new Intent to start the step count today IntentService.
        final Intent stepCountTodayIntent;
        stepCountTodayIntent = new Intent(getActivity(), GoogleFitService.class);
        stepCountTodayIntent.setAction(GoogleFitService.STEP_COUNT_TODAY);


        // Creates a new Intent to start the miles count today IntentService
        final Intent milesCountTodayIntent;
        milesCountTodayIntent = new Intent(getActivity(), GoogleFitService.class);
        milesCountTodayIntent.setAction(GoogleFitService.MILES_COUNT_TODAY);

        // Creates a new Intent to start the calories count for today IntentService
        final Intent caloriesExpendedTodayIntent;
        caloriesExpendedTodayIntent = new Intent(getActivity(), GoogleFitService.class);
        caloriesExpendedTodayIntent.setAction(GoogleFitService.CALORIES_EXPENDED_TODAY);

        // Disabling stepCountNowTimer
//        stepCountNowRequestTask = new TimerTask() {
//            @Override
//            public void run() {
//                getActivity().startService(stepCountNowIntent);
//            }
//        };
//        stepCountNowRequestTimer = new Timer("stepCountNowRequestTimer");


        googleFitTodayRequestTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().startService(stepCountTodayIntent);
                getActivity().startService(milesCountTodayIntent);
                getActivity().startService(caloriesExpendedTodayIntent);
            }
        };
        googleFitTodayRequestTimer = new Timer("googleFitTodayRequestTimer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        returnView = inflater.inflate(R.layout.fragment_physical, container, false);

        // Handles for various views
        stepCountTodayTextView = (TextView) returnView.findViewById(R.id.step_count_today);
        milesCountTodayTextView = (TextView) returnView.findViewById(R.id.miles_count_today);
        stepsPerSecondTextView = (TextView) returnView.findViewById(R.id.steps_per_second);
        caloriesExpendedTodayTextView = (TextView) returnView.findViewById(R.id.calories_expended_today);

        // Displaying top image
        stepsBannerImageView = (ImageView) returnView.findViewById(R.id.steps_banner_image);
        stepsBannerImageView.setImageResource(R.drawable.steps_icon);


        // Making and registering a GoogleFit client to get fitness data
//        buildPhysicalFitnessClient();
//        physicalFitnessClient.connect();

//        // Registering broadcast receiver
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(stepCountTodayReceiver, new IntentFilter(GoogleFitService.STEP_COUNT_TODAY));
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(stepCountNowReceiver, new IntentFilter(GoogleFitService.STEPS_PER_SECOND_COUNT));
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(milesCountTodayReceiver, new IntentFilter(GoogleFitService.MILES_COUNT_TODAY));
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(caloriesCountTodayReceiver, new IntentFilter(GoogleFitService.CALORIES_EXPENDED_TODAY));
//
//        // Starting services, just to be on the safe side
//        // Creates a new Intent to start the step count now IntentService.
//        final Intent stepCountNowIntent;
//        stepCountNowIntent = new Intent(getActivity(), GoogleFitService.class);
//        stepCountNowIntent.setAction(GoogleFitService.STEPS_PER_SECOND_COUNT);
//        getActivity().startService(stepCountNowIntent);
//
//        // Creates a new Intent to start the step count today IntentService.
//        final Intent stepCountTodayIntent;
//        stepCountTodayIntent = new Intent(getActivity(), GoogleFitService.class);
//        stepCountTodayIntent.setAction(GoogleFitService.STEP_COUNT_TODAY);
//
//
//        // Creates a new Intent to start the miles count today IntentService
//        final Intent milesCountTodayIntent;
//        milesCountTodayIntent = new Intent(getActivity(), GoogleFitService.class);
//        milesCountTodayIntent.setAction(GoogleFitService.MILES_COUNT_TODAY);
//
//        // Creates a new Intent to start the calories count for today IntentService
//        final Intent caloriesExpendedTodayIntent;
//        caloriesExpendedTodayIntent = new Intent(getActivity(), GoogleFitService.class);
//        caloriesExpendedTodayIntent.setAction(GoogleFitService.CALORIES_EXPENDED_TODAY);
//
//
//        stepCountNowRequestTask = new TimerTask() {
//            @Override
//            public void run() {
//                getActivity().startService(stepCountNowIntent);
//                getActivity().startService(stepCountTodayIntent);
//                getActivity().startService(milesCountTodayIntent);
//                getActivity().startService(caloriesExpendedTodayIntent);
//            }
//        };
//        stepCountNowRequestTimer = new Timer("stepCountNowRequestTimer");

        // Disabling stepCountNow timer as of now
//        stepCountNowRequestTimer.scheduleAtFixedRate(stepCountNowRequestTask, 0, 1000);
        getActivity().startService(stepCountNowIntent);
//        // Calling it twice in the hope that the first count will be erased
//        for(int i = 0; i < 2; i++) {
//            getActivity().startService(stepCountNowIntent);
//        }
        googleFitTodayRequestTimer.scheduleAtFixedRate(googleFitTodayRequestTask, 0, 60000);

        return returnView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Disabled stepCountNowTimer as of now
//        stepCountNowRequestTimer.cancel();
        googleFitTodayRequestTimer.cancel();
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_OAUTH) {
//            authInProgress = false;
//            if (resultCode == Activity.RESULT_OK) {
//                // Make sure the app is not already connected or attempting to connect
//                if (!physicalFitnessClient.isConnecting() && !physicalFitnessClient.isConnected()) {
//                    physicalFitnessClient.connect();
//                }
//            }
//        }
//    }

//    /**
//     * Build a {@link com.google.android.gms.common.api.GoogleApiClient} that will authenticate the user and allow the application
//     * to connect to Fitness APIs. The scopes included should match the scopes your app needs
//     * (see documentation for details). Authentication will occasionally fail intentionally,
//     * and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
//     * can address. Examples of this include the user never having signed in before, or having
//     * multiple accounts on the device and needing to specify which account to use, etc.
//     */
//    private void buildPhysicalFitnessClient() {
//        Log.d(TAG, "buildPhysicalFitnessClient called.");
//
//        // Create the Google API Client
//        physicalFitnessClient = new GoogleApiClient.Builder(getActivity())
//                // Adding Fitness Sensor API
//                .addApi(Fitness.SENSORS_API)
//                        // Adding Plus API
//                .addApi(Plus.API)
//                        // Adding Fitness Scopes
//                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
//                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
//                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
//                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
//                .addScope(new Scope(Scopes.FITNESS_BODY_READ))
//                .addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
//                        // Adding Plus Scopes
//                .addScope(Plus.SCOPE_PLUS_LOGIN)
//                .addScope(Plus.SCOPE_PLUS_PROFILE)
//                .addConnectionCallbacks(
//                        new GoogleApiClient.ConnectionCallbacks() {
//                            @Override
//                            public void onConnected(Bundle bundle) {
//                                Log.i(TAG, "Connected!!!");
//                                // Now you can make calls to the Fitness APIs.
//                                // Put application specific code here.
//                                // Just displaying successful connection message
////                                Toast.makeText(getApplicationContext(), "Connected successfully", Toast.LENGTH_LONG).show();
//
//                                doCoolStuff();
//                            }
//
//                            @Override
//                            public void onConnectionSuspended(int i) {
//                                // If your connection to the sensor gets lost at some point,
//                                // you'll be able to determine the reason and react to it here.
//                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
//                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
//                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
//                                    Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
//                                }
//                            }
//                        }
//                )
//                .addOnConnectionFailedListener(
//                        new GoogleApiClient.OnConnectionFailedListener() {
//                            // Called whenever the API client fails to connect.
//
//                            @Override
//                            public void onConnectionFailed(ConnectionResult result) {
//                                Log.i(TAG, "Connection failed. Cause: " + result.toString());
//                                if (!result.hasResolution()) {
//                                    // Show the localized error dialog
//                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
//                                            getActivity(), 0).show();
//                                    return;
//                                }
//                                // The failure has a resolution. Resolve it.
//                                // Called typically when the app is not yet authorized, and an
//                                // authorization dialog is displayed to the user.
//                                if (!authInProgress) {
//                                    try {
//                                        Log.i(TAG, "Attempting to resolve failed connection");
//                                        authInProgress = true;
//                                        result.startResolutionForResult(getActivity(),
//                                                REQUEST_OAUTH);
//                                    } catch (IntentSender.SendIntentException e) {
//                                        Log.e(TAG, "Exception while starting resolution activity", e);
//                                    }
//                                }
//                            }
//                        }
//                )
//                .build();
//    }

//    private void doCoolStuff() {
//        logStatus("Reday to do some cool stuff");
//        // [START find_data_sources]
//        Fitness.SensorsApi.findDataSources(physicalFitnessClient, new DataSourcesRequest.Builder()
//                // At least one datatype must be specified.
////                .setDataTypes(DataType.TYPE_LOCATION_SAMPLE)
//                        .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
//                        // Can specify whether data type is raw or derived.
////                .setDataSourceTypes(DataSource.TYPE_RAW)
//                .build())
//                .setResultCallback(new ResultCallback<DataSourcesResult>() {
//                    @Override
//                    public void onResult(DataSourcesResult dataSourcesResult) {
//                        Log.i(TAG, "Result: " + dataSourcesResult.getStatus().toString());
//                        logStatus("Result: " + dataSourcesResult.getStatus().toString());
//                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
//                            Log.i(TAG, "Data source found: " + dataSource.toString());
//                            logStatus("Data source found: " + dataSource.toString());
//                            Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());
//                            logStatus("Data Source type: " + dataSource.getDataType().getName());
//
//                            //Let's register a listener to receive Activity data!
////                            if (dataSource.getDataType().equals(DataType.TYPE_LOCATION_SAMPLE)
////                                    && mListener == null) {
////                                Log.i(TAG, "Data source for LOCATION_SAMPLE found!  Registering.");
////                                logStatus("Data source for LOCATION_SAMPLE found!  Registering.");
////                                registerFitnessDataListener(dataSource,
////                                        DataType.TYPE_LOCATION_SAMPLE);
////                            }
//                            if (dataSource.getDataType().equals(DataType.TYPE_STEP_COUNT_DELTA) && mListener == null) {
//                                Log.i(TAG, "Data source for STEP_COUNT_DELTA found!  Registering.");
//                                logStatus("Data source for STEP_COUNT_DELTA found!  Registering.");
//                                registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_DELTA);
//                            }
//                        }
//                    }
//                });
//        // [END find_data_sources]
//    }
//
//    /**
//     * Register a listener with the Sensors API for the provided {@link DataSource} and
//     * {@link DataType} combo.
//     */
//    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
//        logStatus("registerFitnessDataListener called");
//        // [START register_data_listener]
//        mListener = new OnDataPointListener() {
//            @Override
//            public void onDataPoint(DataPoint dataPoint) {
//                for (Field field : dataPoint.getDataType().getFields()) {
//                    Value val = dataPoint.getValue(field);
//                    Log.i(TAG, "Detected DataPoint field: " + field.getName());
//                    logStatus("Detected DataPoint field: " + field.getName());
//                    Log.i(TAG, "Detected DataPoint value: " + val);
//                    logStatus("Detected DataPoint value: " + val);
//                    countSteps(val.asInt());
//                }
//            }
//        };
//
//        Fitness.SensorsApi.add(
//                physicalFitnessClient,
//                new SensorRequest.Builder()
//                        .setDataSource(dataSource) // Optional but recommended for custom data sets.
//                        .setDataType(dataType) // Can't be omitted.
//                        .setSamplingRate(1, TimeUnit.SECONDS)
//                        .build(),
//                mListener)
//                .setResultCallback(new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//                        if (status.isSuccess()) {
//                            Log.i(TAG, "Listener registered!");
//                            logStatus("Listener registered!");
//                        } else {
//                            Log.i(TAG, "Listener not registered.");
//                            logStatus("Listener not registered.");
//                        }
//                    }
//                });
//        // [END register_data_listener]
//    }

//    // Counting steps and displaying it
//    void countSteps(int newSteps) {
//        totalSteps += newSteps;
//        totalStepsTextView.setText("Total Steps : " + totalSteps);
//    }

    // Broadcast receiver for total number of steps today
    private BroadcastReceiver stepCountTodayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent resultIntent) {
//            Toast.makeText(getActivity(), "stepCountTodayReceiver called", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "stepCountTodayReceiver called");
            // Get extra data included in the Intent
            if (resultIntent.hasExtra(GoogleFitService.STEP_COUNT_TODAY_RESULT)) {
                //Recreate the connection result
                int stepCountTodayResult = resultIntent.getIntExtra(GoogleFitService.STEP_COUNT_TODAY_RESULT, 0);
                Log.d(TAG, "StepCountToday result " + stepCountTodayResult);
//                Toast.makeText(getActivity(), "Received result " + stepCountTodayResult, Toast.LENGTH_SHORT).show();
                stepCountTodayTextView.setText("Steps : " + stepCountTodayResult);
            } else {
                // Display the error
                Log.d(TAG, "Didn't received anything.");
//                Toast.makeText(getActivity(), "Didn't received anything.", Toast.LENGTH_SHORT).show();
                stepCountTodayTextView.setText("Didn't received anything.");
            }
        }
    };

    // Broadcast receiver for total number of steps now
    private BroadcastReceiver stepCountNowReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent resultIntent) {
//            Toast.makeText(getActivity(), "stepCountNowReceiver called", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "stepCountNowReceiver called");
            // Get extra data included in the Intent
            if (resultIntent.hasExtra(GoogleFitService.STEPS_PER_SECOND_COUNT_RESULT)) {
                //Recreate the connection result
                int stepCountNowResult = resultIntent.getIntExtra(GoogleFitService.STEPS_PER_SECOND_COUNT_RESULT, 0);
                Log.d(TAG, "StepCountNow result " + stepCountNowResult);
//                Toast.makeText(getActivity(), "Received result " + stepCountNowResult, Toast.LENGTH_SHORT).show();
                stepsPerSecondTextView.setText("" + stepCountNowResult);

//                countSteps(stepCountNowResult);
            } else {
                // Display the error
                Log.d(TAG, "Didn't received anything.");
//                Toast.makeText(getActivity(), "Didn't received anything.", Toast.LENGTH_SHORT).show();
                stepsPerSecondTextView.setText("0");
            }
        }
    };

    private BroadcastReceiver milesCountTodayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent resultIntent) {
            Log.d(TAG, "milesCountTodayReceiver called");
            // Get extra data included in the Intent
            if (resultIntent.hasExtra(GoogleFitService.MILES_TODAY_COUNT_RESULT)) {
                //Recreate the connection result
                float milesCountTodayResult = resultIntent.getFloatExtra(GoogleFitService.MILES_TODAY_COUNT_RESULT, 0);
                Log.d(TAG, "MilesCountToday result " + milesCountTodayResult);

                milesCountTodayTextView.setText("Distance : " + milesCountTodayResult + " kilometer");
            } else {
                milesCountTodayTextView.setText("Distance : " + 0 + " kilometer");
            }
        }
    };

    private BroadcastReceiver caloriesCountTodayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent resultIntent) {
            Log.d(TAG, "caloriesCountTodayReceiver called");
            // Get extra data included in the Intent
            if (resultIntent.hasExtra(GoogleFitService.CALORIES_EXPENDED_TODAY_RESULT)) {
                //Recreate the connection result
                float CaloriesExpendedTodayResult = resultIntent.getFloatExtra(GoogleFitService.CALORIES_EXPENDED_TODAY_RESULT, 0);
                Log.d(TAG, "CaloriesExpendedToday result " + CaloriesExpendedTodayResult);

                caloriesExpendedTodayTextView.setText("Calories : " + CaloriesExpendedTodayResult);
            } else {
                caloriesExpendedTodayTextView.setText("Calories : " + 0 );
            }
        }
    };
}
