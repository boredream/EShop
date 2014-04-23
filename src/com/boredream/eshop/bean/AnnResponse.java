package com.boredream.eshop.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

public class AnnResponse extends JSONObject implements Serializable {
	public String status;
	public int total;// �ܹ�������
	public int totalPageNum;// �ܹ�����ҳ
	public int page;// ��ǰҳ��
	public ArrayList<Ann> result;

	public class Ann implements Serializable {
		// {"groupMessageID":131,"myUserID":1886,"messageTime":"2013/9/5 9:25:03","title":"",
		// "msg":" ��ҽ������μ�����","imageID":0,"likeCounts":1,"reviewCounts":2,"readCounts":0,"shareCounts":0}
		public int groupMessageID;
		public int myUserID;
		public int sharedType;
		public String userName;
		public String fromgroupName;
		public int groupID;
		public String groupName;
		public int groupImageId;
		public int groupLogoImageID;
		public String messageType;// 1 - message(�������ԣ� 2 - anouncement (��Ѷ�� 3 --
									// � 4 --member message (��Ա��Ѷ��5-��֯����
									// 6-����Ȧ��Ϣ 7- ֪ͨ 8 -- ���˷�������
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
