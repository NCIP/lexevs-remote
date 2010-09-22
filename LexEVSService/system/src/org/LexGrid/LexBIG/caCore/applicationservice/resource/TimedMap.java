package org.LexGrid.LexBIG.caCore.applicationservice.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.InitializingBean;

public class TimedMap<K,V> extends HashMap<K,V> implements InitializingBean{

	private static final long serialVersionUID = 189654533L;
	
	private Timer evictionTimer;
	
	private long checkPeriod = 10000;
	private long timeToLive = 1000000;
	
	private Map<K,Long> timeMap = new HashMap<K,Long>();

	@Override
	public void afterPropertiesSet() throws Exception {
		this.evictionTimer = new Timer();
		this.evictionTimer.schedule(
				new TimerTask(){

					@Override
					public void run() {
						long currentTime = System.currentTimeMillis();
						
						for(K key : keySet()){
							long originateTime = timeMap.get(key);
							if( (currentTime - timeToLive) >= originateTime){
								remove(key);
							}
						}
						
					}
				}
				,checkPeriod, checkPeriod);
	}

	@Override
	public void clear() {
		this.timeMap.clear();
		super.clear();
	}

	@Override
	public V put(K key, V value) {
		this.timeMap.put(key, System.currentTimeMillis());
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for(K key : m.keySet()){
			this.put(key, m.get(key));
		}
	}

	@Override
	public V remove(Object key) {
		this.timeMap.remove(key);
		return super.remove(key);
	}
}
