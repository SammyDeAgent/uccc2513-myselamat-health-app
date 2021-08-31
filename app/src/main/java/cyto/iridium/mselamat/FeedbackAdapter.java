package cyto.iridium.mselamat;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FeedbackAdapter extends BaseAdapter {
    // Adapter to be used for displaying multiple items in the JSONArray for views
    Context context;
    ArrayList<FeedbackModel> arrayList;

    public FeedbackAdapter(Context context, ArrayList<FeedbackModel> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fd_listing, parent, false);
        }

        TextView name, fb, date;
        Button btnDelete;

        name = (TextView) convertView.findViewById(R.id.feedback_name);
        fb = (TextView)  convertView.findViewById(R.id.feedback_text);
        date = (TextView) convertView.findViewById(R.id.feedback_date);
        btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

        fb.setMovementMethod(new ScrollingMovementMethod());

        name.setText(arrayList.get(position).getDisplayname());
        fb.setText(arrayList.get(position).getFeedbackText());
        date.setText(arrayList.get(position).getSubmitDate());
        btnDelete.setTag(arrayList.get(position).getId());

        return convertView;
    }
}

