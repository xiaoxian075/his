package com.tf.sdk.change;

/**
 * 转换类接口
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月17日
 */
public interface BaseChange<K, V> {

	K change(V in);
}
