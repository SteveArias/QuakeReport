package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

import static android.R.attr.data;
import static com.example.android.quakereport.R.id.date;

/**
 * An {@Link EarthquakeAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@Link Earthquake} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView to be displayed
 * to the user.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    /**
     * Constructs a new {@Link Earthquake Adapter}
     * @param context of the app
     * @param earthquakes is the list of earthquakes, which is the data source of the adapter
     */
    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    /**
     * Returns a list item view that displays info about the earthquake at the given position
     * in the list of earthquakes.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there's an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView with view ID magnitude
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Create a DecimalFormat to let the app know how may decimal points you want shown for
        // all of the magnitudes
        DecimalFormat formatter = new DecimalFormat("0.0");
        // Get the magnitude of the current earthquake and format it according to the
        // DecimalFormat we created above
        String magnitude = formatter.format(currentEarthquake.getMagnitude());
        // Display the magnitude of the current earthquake in that TextView
        magnitudeView.setText(magnitude);
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        // Find the TextView with view ID location_offset
        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        // Find the TextView with the view ID primary_location
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);

        String locationString = currentEarthquake.getLocation();

        boolean hasOffset = locationString.contains(" of ");
        // Check to see if the location String of the earthquake has a location Offset
        // (ex. 45 km N of)
        if (hasOffset){
            // Find where the "of " is in the string
            int splitIndex = locationString.indexOf(" of ");
            // Cut the string at the splitIndex and store it in a new String
            String locationOffsetString = locationString.substring(0, splitIndex + 3);
            // Display the locationOffset of the current earthquake in that TextView
            locationOffsetView.setText(locationOffsetString);
            // Get the second half of the locationString and store it in the primaryLocationString String
            String primaryLocationString = locationString.substring(splitIndex + 4, locationString.length());
            // Set the primaryLocationView to have the primaryLocationString String
            primaryLocationView.setText(primaryLocationString);
        }

        else if (!hasOffset){
            // Since it doesn't have a location offset, put "Near the" in the locationOffsetView TextView instead
            String locationOffsetString = getContext().getString(R.string.near_the);
            // Display the location of the current earthquake in that TextView
            locationOffsetView.setText(locationOffsetString);
            // Attach the primary location of the earthquake to the primaryLocationView TextView
            primaryLocationView.setText(locationString);
        }

        // Store the time in milliseconds into a Date object
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());
        // Find the TextView with the view ID date
        TextView dateView = (TextView) listItemView.findViewById(date);
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude){

        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    public void setEarthquake(List<Earthquake> earthquakes) {
        earthquakes.addAll(earthquakes);
        notifyDataSetChanged();
    }
}
