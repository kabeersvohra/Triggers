
package com.biovoso.Triggers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private List<Button> buttonList;
    private int height = -1;

    public ButtonAdapter(List<Button> buttonList)
    {
        this.buttonList = buttonList;
    }


    @Override
    public int getItemCount() {
        return buttonList.size();
    }

    @Override
    public void onBindViewHolder(ButtonViewHolder buttonViewHolder, int i) {
        if(height != -1)
            buttonViewHolder.itemView.getLayoutParams().height = height;
        Button button = buttonList.get(i);
        buttonViewHolder.vTitle.setText(button.name);
        buttonViewHolder.vDesc.setText(button.description);
        buttonViewHolder.vIcon.setImageResource(button.iconId);
    }

    @Override
    public ButtonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ButtonViewHolder(itemView);
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vDesc;
        protected ImageView vIcon;

        public ButtonViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.title);
            vDesc = (TextView) v.findViewById(R.id.desc);
            vIcon = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void redoList(List<Button> buttonList)
    {
        this.buttonList = buttonList;
        notifyDataSetChanged();
    }

    public void setHeight(int height)
    {
        this.height = height;
        notifyDataSetChanged();
    }

    public List<Button> getButtonList()
    {
        return buttonList;
    }
}