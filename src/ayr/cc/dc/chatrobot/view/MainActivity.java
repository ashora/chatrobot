package ayr.cc.dc.chatrobot.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import ayr.cc.dc.chatrobot.R;
import ayr.cc.dc.chatrobot.adapter.ChatAdapter;
import ayr.cc.dc.chatrobot.entity.ChatEntity;
import ayr.cc.dc.chatrobot.entity.ChatEntity.Type;
import ayr.cc.dc.chatrobot.utils.NetUtils;

/***
 * 让ListView自动滚到底部 
 * android:stackFromBottom="true"
 * android:transcriptMode="alwaysScroll"
 * 
 * @author DC
 * 
 */
public class MainActivity extends Activity {
	private ListView mListView = null;
	private List<ChatEntity> mChatEntityList = null;
	private ChatAdapter mChatAdapter = null;
	private EditText dtSendMessage = null;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			ChatEntity chatReceive = (ChatEntity) msg.obj;
			mChatEntityList.add(chatReceive);
			mChatAdapter.notifyDataSetChanged();
			mListView.setSelection(mChatAdapter.getCount()-1);  
		};
	};
	private String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
		initDatas();
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.lv_chat_list);
		dtSendMessage = (EditText) findViewById(R.id.et_send_message);
	}

	private void initDatas() {
		mChatEntityList = new ArrayList<ChatEntity>();
		ChatEntity entity = new ChatEntity();
		entity.setDate(new Date());
		entity.setType(Type.INFORECEIVE);
		entity.setMessage("你好啊!");
		mChatEntityList.add(entity);

		mChatAdapter = new ChatAdapter(MainActivity.this, mChatEntityList);
		mListView.setAdapter(mChatAdapter);
		mListView.setSelection(mChatAdapter.getCount()-1);  
	}

	/***
	 * 点击发送按钮
	 * 
	 * @param view
	 */
	public void sendMessage(View view) {
		message = dtSendMessage.getText().toString().trim();
		if (!TextUtils.isEmpty(message)) {
			ChatEntity entity = new ChatEntity();
			entity.setDate(new Date());
			entity.setType(Type.INFOSEND);
			entity.setMessage(message);
			mChatEntityList.add(entity);
			mChatAdapter.notifyDataSetChanged();
			mListView.setSelection(mChatAdapter.getCount()-1);  
			dtSendMessage.setText("");
			getResult();
		} else {
			// 提示不能为空
			Toast.makeText(MainActivity.this, "不能发送空消息", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void getResult() {
		new Thread() {
			public void run() {
				ChatEntity chatReceive = NetUtils.getChatEntity(message);
				Message msg = new Message();
				msg.obj = chatReceive;
				handler.sendMessage(msg);
			};
		}.start();
	}
}
