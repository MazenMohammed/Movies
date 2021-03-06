package mazen_mohamed.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Card> DataSet;
    private static Context context;

    public CardAdapter(Context cont,List<Card> dataSet)
    {
        context=cont;
        DataSet = dataSet;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.item_image)ImageView poster;
        @BindView(R.id.title)TextView title;
        @BindView(R.id.text)TextView text;
        @BindView(R.id.favorite_button)Button favorite_button;

        public ViewHolder(View v)
        {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                    if (MainActivity.mTwoPane)
                    {
                        Bundle arguments = new Bundle();
                        arguments.putSerializable(DetailsFragment.ARG_ITEM_ID, DataSet.get(getPosition()));

                        DetailsFragment fragment = new DetailsFragment();
                        fragment.setArguments(arguments);
                        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detail_container, fragment)
                                .commit();
                    }
                    else
                    {
                        Context context2 = v.getContext();
                        Intent intent = new Intent(context2, CardDetails.class);
                        intent.putExtra(DetailsFragment.ARG_ITEM_ID,  DataSet.get(getPosition()));
                        context2.startActivity(intent);
                    }
                }
            });
            ButterKnife.bind(this,v);

        }

        public ImageView getPoster() {
            return poster;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getText() {
            return text;
        }

        public void setText(TextView text) {
            this.text = text;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return  new CardAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        if (DataSet.get(position) != null)
        {
            Log.d("", "Element " + position + " set.");
            holder.getTitle().setText(DataSet.get(position).getOriginal_title());
            holder.getText().setText(DataSet.get(position).getOverview());


            // Feed image
            if (DataSet.get(position).getPoster() != null)
            {
                //download image.
                Picasso.with(context).load(DataSet.get(position).getPoster()).into(holder.getPoster());
            }

        }
    }

    @Override
    public int getItemCount() {
        return DataSet.size();
    }
}
