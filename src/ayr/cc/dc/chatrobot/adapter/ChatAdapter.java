package ayr.cc.dc.chatrobot.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ayr.cc.dc.chatrobot.R;
import ayr.cc.dc.chatrobot.entity.ChatEntity;
import ayr.cc.dc.chatrobot.entity.ChatEntity.Type;
import ayr.cc.dc.chatrobot.utils.DateUtils;

public class ChatAdapter extends BaseAdapter {
	private List<ChatEntity> mChatEntityList = null;
	
	private LayoutInflater mInflater = null;
	
	public ChatAdapter(Context context,List<ChatEntity> chatEntityList){
		mChatEntityList = chatEntityList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mChatEntityList.size();
	}

	@Override
	public Object getItem(int position) {
		return mChatEntityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		if(mChatEntityList.get(position).getType()==Type.INFORECEIVE){
			return 0;
		}
		return 1;
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder = null;
		if(convertView==null){
			if(getItemViewType(position)==0){
				mViewHolder = new ViewHolder();
				//从服务器收到的消息
				convertView = mInflater.inflate(R.layout.info_receive, null);
				mViewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_receive_date);
				mViewHolder.tvMessage = (TextView) convertView.findViewById(R.id.tv_receive_message);
			}else if(getItemViewType(position)==1){
				mViewHolder = new ViewHolder();
				//发送的消息
				convertView = mInflater.inflate(R.layout.info_send, null);
				mViewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_send_date);
				mViewHolder.tvMessage = (TextView) convertView.findViewById(R.id.tv_send_message);
			}
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		mViewHolder.tvDate.setText(DateUtils.getDateString(mChatEntityList.get(position).getDate()));
		mViewHolder.tvMessage.setText(mChatEntityList.get(position).getMessage());
		return convertView;
	}
	
	
	private class ViewHolder{
		private TextView tvDate;
		private TextView tvMessage;
	}

}
