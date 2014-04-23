package com.boredream.eshop;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class BaseApplication extends Application{
	private List<Activity> activityList=new LinkedList<Activity>();
	   private static BaseApplication instance;
	   
	   private BaseApplication(){
		   
	   }
	   public static BaseApplication getInstance(){
		   if (instance==null) {
			instance=new BaseApplication();
		}
		   return instance;
	   }
	   public void addActivity(Activity activity){
		   activityList.add(activity);
	   }
	   public void exit(){
		   for(Activity activity:activityList){
			     if(!activity.isFinishing()){
			    	  activity.finish();
			     }
		   }
		   int id=android.os.Process.myPid();
		   if(id!=0){
			   android.os.Process
				.killProcess(id);
		   }
	   }
}
