package cn.standardai.api.statistic.service;

import cn.standardai.api.statistic.exception.StatisticException;

public class StatisticFactory {

	public static Statistic getInstance(String statisticType) throws StatisticException {

		switch (statisticType) {
		case "health":
			return new HealthStatistic();
		case "growthrate":
			return new GrowthRateStatistic();
		case "ratio":
			return new RatioStatistic();
		case "datapoint":
			return new DatapointStatistic();
		case "predict":
			return new PredictStatistic();
		case "peak":
			return new PeakStatistic();
		case "rotationRate":
			return new RotationRateStatistic();
		case "indicator":
			return new IndicatorStatistic();
		default:
			throw new StatisticException("Unknown statistic type(" + statisticType + ")");
		}
	}
}
