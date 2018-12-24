package com.his.asyn.accept;

import org.apache.commons.lang3.StringUtils;

import com.his.common.net.user.UserPageAccept;
import com.tf.sdk.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElasticSearchAccept extends UserPageAccept {

	private int type;
	private String word;
	private long extra;
	
	public boolean check() {
		String _word = StringUtil.chargLetter(word);
		if (StringUtils.isBlank(_word)) {
			return false;
		}
		return true;
	}
}

