package iband.grademojo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by dell on 22-11-2017.
 */

public class StudentListAdapter  extends RecyclerView.Adapter<StudentListAdapter.StudentListViewHolder>{

    private List< StudentListProvider> suggestedProviders;
    private Context context;

    public StudentListAdapter(Context context, List< StudentListProvider> suggestedProviders) {
        this.suggestedProviders = suggestedProviders;
        this.context = context;
    }


    @Override
    public StudentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student, parent, false);
        return new StudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentListViewHolder holder, int position) {

        final  StudentListProvider SuggestedProvider = suggestedProviders.get(position);

        holder.roll.setText(SuggestedProvider.getRoll());
        holder.name.setText(SuggestedProvider.getName());
        holder.gender.setText(SuggestedProvider.getGender());

    }

    @Override
    public int getItemCount() {
        return suggestedProviders.size();
    }

    public class StudentListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView name,roll,gender;


        public StudentListViewHolder(View itemView) {
            super(itemView);
          //  ButterKnife.bind(this, itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            gender=(TextView)itemView.findViewById(R.id.gender);
            roll=(TextView)itemView.findViewById(R.id.roll);
            name.setOnClickListener(this);
           gender.setOnClickListener(this);
            roll.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
            switch (view.getId()){

                case R.id.name:
                    Intent intent =new Intent(context,StudentDetail.class);
                    intent.putExtra("id",suggestedProviders.get(getAdapterPosition()).getId());
                    intent.putExtra("name",suggestedProviders.get(getAdapterPosition()).getName());
                    context.startActivity(intent);
                    break;
                case R.id.gender:
                    Intent intent1 =new Intent(context,StudentDetail.class);
                    intent1.putExtra("id",suggestedProviders.get(getAdapterPosition()).getId());
                    intent1.putExtra("name",suggestedProviders.get(getAdapterPosition()).getName());
                    context.startActivity(intent1);
                    break;
                case R.id.roll:
                    Intent intent2 =new Intent(context,StudentDetail.class);
                    intent2.putExtra("id",suggestedProviders.get(getAdapterPosition()).getId());
                    intent2.putExtra("name",suggestedProviders.get(getAdapterPosition()).getName());
                    context.startActivity(intent2);
                    break;

            }

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
