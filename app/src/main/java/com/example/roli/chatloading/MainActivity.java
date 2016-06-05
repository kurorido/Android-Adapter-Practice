package com.example.roli.chatloading;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;


    //private List<String> data;
    //private Queue<String> messageQueue;
    //private BaseAdapter adapter;

    private List<Message> data;
    private Queue<Message> messageQueue;
    private MessageAdapter adapter;

    private int counter = 0;

    private boolean loading = false;

    private static Handler mHandler = new Handler();

    public static class MessageFactory {
        public static Message getMessage(String message) {
            Message msg = new Message();
            msg.setType(Message.MessageType.TEXT);
            msg.setText(message);
            return msg;
        }

        public static Message getLoadingMessage() {
            Message msg = new Message();
            msg.setType(Message.MessageType.LOADING);
            return msg;
        }
    }

    private Runnable QueueLoadingRunnable = new Runnable() {
        @Override
        public void run() {
            data.remove(data.size() - 1); // remove the last (should be loading message)
            data.add(messageQueue.poll());
            adapter.notifyDataSetChanged();
            if(messageQueue.size() != 0) {
                loadMessage();
            } else {
                loading = false;
            }
        }
    };

    private void loadMessage() {
        data.add(MessageFactory.getLoadingMessage());
        adapter.notifyDataSetChanged(); // show loading message
        mHandler.postDelayed(QueueLoadingRunnable, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.activity_mylist_listview);

        data = new ArrayList<>();

        messageQueue = new LinkedList<>();

//        adapter = new ArrayAdapter<>(
//                this,
//                R.layout.list_row,
//                R.id.list_row_draganddrop_textview,
//                data);

        adapter = new MessageAdapter(this, data);

        mListView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMessageToQueue("Item: " + counter++);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(loading) {
            mHandler.removeCallbacks(QueueLoadingRunnable);
            data.remove(data.size() - 1);
            Message item;
            while ((item = messageQueue.poll()) != null) {
                data.add(item);
            }
            adapter.notifyDataSetChanged();
            mListView.smoothScrollToPosition(data.size() - 1);
            loading = false;
        }
    }

    private void addMessageToQueue(String message) {
        messageQueue.add(MessageFactory.getMessage(message));
        if(!loading) {
            loading = true;
            loadMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
