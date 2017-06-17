package com.programs.lala.myschool;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.programs.lala.myschool.Adapter.ChatAdapter;
import com.programs.lala.myschool.Adapter.MessageAdapter;
import com.programs.lala.myschool.data.MessageContract;
import com.programs.lala.myschool.interfaces.GetData;
import com.programs.lala.myschool.modeld.ChatModel;
import com.programs.lala.myschool.modeld.MessageModel;
import com.programs.lala.myschool.modeld.PostModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatRoom extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    @InjectView(R.id.messageText)
    EditText messageText;
    @InjectView(R.id.messages)
    RecyclerView recyclerView;
    @InjectView(R.id.sendButton)
    ImageButton sendButton;

    ChatAdapter adapter;
    MessageModel model;
    ChatModel chatModel;
    List<ChatModel> chatModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ButterKnife.inject(this);

        model = (MessageModel) getIntent().getSerializableExtra("messageModel");
        this.setTitle(model.getName());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoaderCallbacks<Cursor> callback = ChatRoom.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(0, bundleForLoader, callback);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!messageText.getText().toString().isEmpty()) {
                    sndMessage();
                }

            }
        });


    }

    public void sndMessage() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BAS_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Call<ResponseBody> connection;
        GetData getData = retrofit.create(GetData.class);
        connection = getData.sendMessage(model.getReceiver_id(), model.getSender_id()
                , messageText.getText().toString());
        connection.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String result = response.body().string();

                    JSONObject jso = new JSONObject(result);
                    int success = jso.getInt("success");
                    if (success == 1) {
                        storeMessage();
                    } else
                        Toast.makeText(getBaseContext(), getText(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), getText(R.string.something_wrong), Toast.LENGTH_SHORT).show();


            }
        });


    }

    public void storeMessage() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TABLE_NAME", MessageContract.MessageEntry.FIRST_TABLE_NAME);
        contentValues.put(MessageContract.MessageEntry.COLUMN_sender_id, model.getReceiver_id());
        contentValues.put(MessageContract.MessageEntry.COLUMN_receive_id, model.getSender_id());
        contentValues.put(MessageContract.MessageEntry.COLUMN_name, model.getName());
        contentValues.put(MessageContract.MessageEntry.COLUMN_message, messageText.getText().toString());
        contentValues.put(MessageContract.MessageEntry.COLUMN_type, "0");
        contentValues.put(MessageContract.MessageEntry.COLUMN_sender_image, model.getImage() + "");

        Uri uri = getContentResolver().insert(MessageContract.MessageEntry.CONTENT_URI, contentValues);
        if (uri != null) {
            Log.v("ff", "good");
            messageText.setText("");
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        Log.v("ddd",model.getName());


        return new CursorLoader(this,
                MessageContract.MessageEntry.CONTENT_URI
                , new String[]{MessageContract.MessageEntry.COLUMN_message,
                MessageContract.MessageEntry.COLUMN_type,
        }
                , MessageContract.MessageEntry.COLUMN_name + "=?", new String[]{model.getName()}, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        chatModelList.clear();
        while (data.moveToNext()) {
            chatModel = new ChatModel();
            chatModel.setMessage(data.getString(0));
            chatModel.setType(data.getInt(1));

            chatModelList.add(chatModel);
        }

        adapter = new ChatAdapter(getBaseContext(), chatModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(chatModelList.size() - 1);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
