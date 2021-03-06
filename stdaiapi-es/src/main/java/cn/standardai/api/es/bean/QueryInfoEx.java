package cn.standardai.api.es.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.standardai.api.es.bean.AggVerb;
import cn.standardai.api.es.bean.Filter;
import cn.standardai.api.es.bean.StatsAggVerb;
import cn.standardai.api.es.bean.TermFilter;
import cn.standardai.api.es.bean.AggVerb.AggType;
import cn.standardai.api.es.bean.StatsAggVerb.StatsType;
import cn.standardai.api.es.exception.ESException;

public class QueryInfoEx {

	private String trainId;

	private String indicator;
	
	private String sort;

	private List<String> aggrKeys;

	public QueryInfoEx(QueryInfoEx queryInfo) {
		super();
		this.trainId = queryInfo.getTrainId();
		this.indicator = queryInfo.getIndicator();
		this.aggrKeys = queryInfo.getAggrKeys();
	}

	public QueryInfoEx() {
		super();
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<String> getAggrKeys() {
		return aggrKeys;
	}

	public void setAggrKeys(List<String> aggrKeys) {
		this.aggrKeys = aggrKeys;
	}

	public static QueryInfoEx parse(JSONObject jsonObject) throws ESException {

		QueryInfoEx queryInfo = new QueryInfoEx();

		String trainId = jsonObject.getString("trainId");
		if (trainId == null) throw new ESException("Missing parameter(trainId)");
		queryInfo.setTrainId(trainId);

		String indicator = jsonObject.getString("indicator");
		if (indicator != null) {
			queryInfo.setIndicator(indicator);
		}
		
		String sort = jsonObject.getString("sort");
		if (sort != null) {
			queryInfo.setSort(sort);
		}

		return queryInfo;
	}

	public static List<Filter> makeFilters(QueryInfoEx queryInfo) throws ESException {

		// Make filters
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new TermFilter("trainId", queryInfo.getTrainId().toLowerCase()));
		filters.add(new TermFilter("indicator", queryInfo.getIndicator().toLowerCase()));

		return filters;
	}

	public static List<AggVerb> makeAggrVerbs(QueryInfoEx queryInfo) throws ESException {

		//List<String> aggrKeys = new ArrayList<String>();
		List<AggVerb> aggrVerbs = new ArrayList<AggVerb>();
		StatsAggVerb statsverb = new StatsAggVerb(AggType.stats, "value");
		statsverb.getStatsTypes().add(StatsType.sum);
		//aggrVerbs.add(statsverb);
		//queryInfo.setAggrKeys(aggrKeys);

		return aggrVerbs;
	}
	
	public static Map<String, String> makeSorts(QueryInfoEx queryInfo) throws ESException {

		Map<String, String> sortMap = new HashMap<String, String>();
		String COL_SPLITTER = "\\|";
		String ORDER_SPLITTER = ":";
		if (queryInfo.getSort() != null) {
			for (String parse : queryInfo.getSort().split(COL_SPLITTER)) {
				String[] sortCond = parse.split(ORDER_SPLITTER);
				sortMap.put(sortCond[0], sortCond[1]);
			}
		}

		return sortMap;
	}
}
