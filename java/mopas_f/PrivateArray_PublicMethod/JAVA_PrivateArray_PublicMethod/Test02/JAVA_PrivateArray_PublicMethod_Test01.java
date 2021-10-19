package kr.go.dfms.engine.framework;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.go.dfms.engine.EnApp;
import kr.go.dfms.engine.utils.EnLog;
import kr.go.dfms.engine.utils.EnLog;


public class EnDocument {
    
    /**
     * log tag
     */
    public static final String TAG = EnDocument.class.getSimpleName();
    
	protected EnApp mContext;
	protected List<EnDocListener> mListener = new ArrayList<EnDocListener>();
	protected Map<String, Object> mDataList = new HashMap<String, Object>();

	public EnDocument(EnApp ctx){
		mContext = ctx;
	}

	public EnApp getApp() {
		return mContext;
	}
 
	public void registerListener(EnDocListener listener){
		if(mListener.contains(listener)){
			return;
		}
		mListener.add(listener);
		EnLog.d(TAG, "listener is added : " + mListener.size());
	}

	public void unRegisterListener(EnDocListener listener){
		if(!mListener.contains(listener)){
			return ;
		}
		mListener.remove(listener);
		EnLog.d(TAG, "listener is removed remained : " + mListener.size());
	}

	public void onSaveInstanceState(Bundle outState) {
		Set<String> keySet = mDataList.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = mDataList.get(key);
			if(value instanceof String)
				outState.putString(key, (String)value);
			else if(value instanceof Integer)
				outState.putInt(key, (Integer)value);
			else if(value instanceof Boolean)
				outState.putBoolean(key, (Boolean)value);
			else 
				EnLog.e(TAG, "not support value type");
		}		
	}	

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		Set<String> keySet = savedInstanceState.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = savedInstanceState.get(key);
			put(key, value);
		}		
	}

	public String getString(String id){
		return (String)get(id);
	}

	public void putString(String id, String value){
		put(id, value);
	}

	public void putInt(String id, Integer value){
		put(id, value);
	}

	public int getInt(String id){
		return (Integer)get(id);
	}

	public void putBoolean(String id, Boolean value){
		put(id, value);
	}

	public Boolean getBoolean(String id){
		return (Boolean)get(id);
	}

	public void put(String id, Object arg){
		mDataList.put(id, arg);
		notifyChanged(id, arg);
	}

	public Object get(String id){
		return mDataList.get(id);
	}

	protected void notifyChanged(String id, Object arg){
		for(int i=0; i < mListener.size() ; i++){
			mListener.get(i).notify(id, arg);
		}
	}

}
