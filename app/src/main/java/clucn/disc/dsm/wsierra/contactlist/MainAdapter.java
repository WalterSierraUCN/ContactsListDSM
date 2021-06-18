package clucn.disc.dsm.wsierra.contactlist;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    /**
     * Initialize the Activity and the arrayList.
     */
    Activity activity;
    ArrayList<ContactModel> arrayList;

    /**
     * The Constructor
     * @param activity
     * @param arrayList
     */
    public MainAdapter(Activity activity, ArrayList<ContactModel> arrayList){
        this.activity = activity;
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    /**
     *  Launchs The View Holder.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);

        // Return view
        return new ViewHolder(view);
    }

    /**
     * Initialize the model.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Initialize contact model
        ContactModel model = arrayList.get(position);

        //Set name
        holder.tvName.setText(model.getName());

        //Set number
        holder.tvNumber.setText(model.getNumber());


    }

    /**
     * Gets the count of items on arrayList.
     * @return arrayList's size.
     */
    @Override
    public int getItemCount() {

        // return this arraylist's size
        return arrayList.size();
    }

    /**
     * The View Holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Initialize textViews
        TextView tvName, tvNumber;

        /**
         * The View Holder's Constructor.
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variables
            tvName= itemView.findViewById(R.id.tv_name);
            tvNumber= itemView.findViewById(R.id.tv_number);

        }
    }
}
