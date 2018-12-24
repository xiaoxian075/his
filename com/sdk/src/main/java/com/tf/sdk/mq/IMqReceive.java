package com.tf.sdk.mq;

public interface IMqReceive {
	void onMessage(String data);
}
