package com.example.roli.chatloading;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by roli on 6/5/16.
 */
public class MessageAdapter extends BaseAdapter {

    private List<Message> messages;
    private Context context;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // reference
        // http://stackoverflow.com/questions/19469073/how-do-you-efficiently-load-bitmaps-from-drawable-folder-into-a-listview/19469076#19469076

        ViewHolder holder;
        Message msg = messages.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_with_image, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_image);
            holder.text = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(msg.getType() == Message.MessageType.LOADING) {
            holder.text.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
            AlphaAnimation anim = new AlphaAnimation(0.2f, 1.0f);
            anim.setDuration(1000);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            holder.image.startAnimation(anim);
        } else {
            holder.text.setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.GONE);
            holder.text.setText(msg.getText());
            holder.image.setAnimation(null);
        }

        return convertView;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView text;
    }
}
