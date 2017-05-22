package cn.standardai.api.ml.filter;

import cn.standardai.api.ml.exception.FilterException;

public class ExpInteger1D extends DimensionFilter<Integer, Integer[]> {

	@Override
	public Integer[] encode(Integer src) throws FilterException {
		if (src == null) return null;
		Integer[] des = new Integer[1];
		des[0] = src;
		return des;
	}

	@Override
	public Integer decode(Integer[] src) throws FilterException {
		if (src == null || src.length < 1) return null;
		return src[0];
	}
}
