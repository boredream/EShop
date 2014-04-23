package com.boredream.eshop.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

public class AnnResponse extends JSONObject implements Serializable {
	public String status;
	public int total;// 总共多少条
	public int totalPageNum;// 总共多少页
	public int page;// 当前页数
	public ArrayList<Ann> result;

	public class Ann implements Serializable {
		// {"groupMessageID":131,"myUserID":1886,"messageTime":"2013/9/5 9:25:03","title":"",
		// "msg":" 大家今天来参加软博会","imageID":0,"likeCounts":1,"reviewCounts":2,"readCounts":0,"shareCounts":0}
		public int groupMessageID;
		public int myUserID;
		public int sharedType;
		public String userName;
		public String fromgroupName;
		public int groupID;
		public String groupName;
		public int groupImageId;
		public int groupLogoImageID;
		public String messageType;// 1 - message(聊天留言） 2 - anouncement (资讯） 3 --
									// 活动 4 --member message (成员资讯）5-组织留言
									// 6-朋友圈信息 7- 通知 8 -- 个人发起讨论
		public int usersAvatarPictureID;
		public String messageTime;
		public String title;
		private String msg;
		
		public String getMsg() {
			return msg.replace("##11111##", "\r")
					.replace("##22222##", "\n")
					.replace("##33333##", "\t");
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public int imageID;
		public int likeCounts;
		public int unlikeCounts;
		public int reviewCounts;
		public int readCounts;
		public int shareCounts;
		public int priority;

		@Override
		public boolean equals(Object o) {
			if(o instanceof Ann) {
				return ((Ann)o).groupMessageID == this.groupMessageID;
			}
			return super.equals(o);
		}

		
		
	}

}
