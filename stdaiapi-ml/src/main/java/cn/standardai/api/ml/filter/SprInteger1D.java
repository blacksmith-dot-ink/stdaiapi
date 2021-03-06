package cn.standardai.api.ml.filter;

import cn.standardai.api.ml.exception.FilterException;
import cn.standardai.lib.base.function.Statistic;

public class SprInteger1D extends DimensionFilter<Integer, Integer[]> {

	@Override
	public Integer[] encode(Integer src) throws FilterException {
		if (src == null) return null;
		Integer[] des = new Integer[Integer.parseInt(this.getParam(0).toString())];
		for (int i = 0; i < des.length; i++) {
			if (i == src) {
				des[i] = 1;
			} else {
				des[i] = 0;
			}
		}
		return des;
	}

	@Override
	public Integer decode(Integer[] src) throws FilterException {
		if (src == null) return null;
		return Statistic.maxIndex(src);
	}

	@Override
	public String getDescription() {
		return "将Integer类型的数字扩展为1维Integer one hot数组。";
	}
}
