package cn.standardai.api.ml.filter;

import java.util.HashMap;

import cn.standardai.api.dao.base.DaoHandler;
import cn.standardai.api.ml.daohandler.DicHandler;

public class SequenceIntegerFilter extends DicFilter<String, Integer, Integer> {

	@Override
	public Integer encode(String s) {
		if (dic.containsKey(s)) return dic.get(s);
		int idx = dic.size();
		dic.put(s, idx);
		arcDic.put(idx, s);
		return idx;
	}

	@Override
	public String decode(Integer t) {
		if (arcDic.containsKey(t)) return arcDic.get(t);
		return "?";
	}

	@Override
	public void init(String userId, DaoHandler dh) {
		String dicName = params == null ? null : params.get(0);
		if (dicName == null || "".equals(dicName)) {
			this.dic = new HashMap<String, Integer>();
			this.arcDic = new HashMap<Integer, String>();
		} else {
			setDic(new DicHandler(dh).get(userId, dicName));
		}
	}

	@Override
	public String getDescription() {
		return "根据数据中字符串出现的顺序，依次将其转换为从0开始的Integer数字。";
	}
}
