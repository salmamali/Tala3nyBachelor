package eg.edu.guc.tala3nybachelor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import eg.edu.guc.tala3nybachelor.R;

/**
 * Created by salmaali on 11/27/15.
 */
public class LoginSpinnerAdapter extends ArrayAdapter<String>{

    private ArrayList<String> text;

    public LoginSpinnerAdapter( Context context, ArrayList<String> text) {
        super(context, R.layout.spinner_fragment, R.id.text1, text);
        this.text = text;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if(convertView == null) {
            customView = inflater.inflate(R.layout.spinner_fragment, parent, false);
        } else {
            customView = convertView;
        }

        TextView spinnerText = (TextView) customView.findViewById(R.id.text1);
        spinnerText.setText(text.get(position));


        return customView;
    }
}
