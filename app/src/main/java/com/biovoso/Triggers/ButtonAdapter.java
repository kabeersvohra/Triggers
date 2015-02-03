
package com.biovoso.Triggers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ContactViewHolder> {

    private List<Button> buttonList;

    public ButtonAdapter(List<Button> buttonList) {
        this.buttonList = buttonList;
    }


    @Override
    public int getItemCount() {
        return buttonList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Button ci = buttonList.get(i);
        contactViewHolder.vTitle.setText(ci.name);
        contactViewHolder.vDesc.setText(ci.description);
        contactViewHolder.vIcon.setImageDrawable(ci.getIcon());
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vDesc;
        protected ImageView vIcon;

        public ContactViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.title);
            vDesc = (TextView) v.findViewById(R.id.desc);
            vIcon = (ImageView) v.findViewById(R.id.icon);
        }
    }
}