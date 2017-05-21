package cn.standardai.api.ash.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.standardai.api.ash.agent.ArgsHelper;
import cn.standardai.api.ash.base.Action;
import cn.standardai.api.ash.base.AshCommand.HttpMethod;
import cn.standardai.api.ash.bean.AshParam;
import cn.standardai.api.ash.bean.AshReply;
import cn.standardai.api.ash.exception.AshException;
import cn.standardai.api.ash.exception.HttpException;
import cn.standardai.api.core.bean.Context;

public class CallModel extends Action {

	private static final String[][] dialogs = new String[][] {
			new String[] {
					"请输入模型名: ",
					"请输入学习率(0.1, 建议范围0.01 ~ 1.0): ",
					"请输入梯度矩阵秩的阈值(1, 建议范围1 ~ 15): ",
					"请输入训练集，测试集，验证集的比例，用逗号分割(8,1,1): ",
					"请输入batch size: ",
					"请输入中间结果输出间隔(epoch): ",
					"请输入epoch数: ",
					"请输入训练时间(秒): ",
					"请输入测试集Loss增大次数(null): ",
					"是否保留旧模型(N): ",
			},
	};

	String[] s1 = new String[] {
			"请输入模型名：",
			"请输入数据集名(userId/datasetName)：",
			"请输入x过滤器，多个过滤器用｜分割(支持的过滤器请参考文档－－过滤器)：",
			"请输入y过滤器，多个过滤器用｜分割(支持的过滤器请参考文档－－过滤器)：",
			"请输入算法名(CNN, LSTM)：",
			"请输入算法JSON结构(结构说明请参考文档－－建立模型)：",
	};

	private String modelName;

	private String datasetName;

	private String xFilter;

	private String yFilter;

	private String algorithm;

	private String structure;

	@Override
	public AshReply exec() throws AshException {
		if (this.param.has('t')) {
			train();
		} else if (this.param.has('p')) {
			execute();
		} else if (this.param.has('r')) {
			release();
		} else if (this.param.has('s')) {
			setting();
		}
		return this.reply;
	}

	private void train() throws HttpException {

		if (this.param.number() < this.dialogs[0].length) {
			//comm.reply = AshDialog.make(this, this.param);
			return;
		}

		String modelName = this.param.get(0);
		JSONObject body = new JSONObject();
		JSONObject train = new JSONObject();

		train.put("learningRate", "".equals(this.param.get(1)) ? 0.1 : Double.parseDouble(this.param.get(1)));
		train.put("dth", "".equals(this.param.get(2)) ? 1.0 : Double.parseDouble(this.param.get(2)));
		String[] rate3 = "".equals(this.param.get(3)) ? "8,1,1".split(",") : this.param.get(3).split(",");
		JSONArray rate = new JSONArray();
		for (int i = 0; i < 3; i++) {
			rate.add(i >= rate3.length ? 0 : Integer.parseInt(rate3[i]));
		}
		train.put("diverseDataRate", rate);
		Integer batchSize = Integer.parseInt(this.param.get(4));
		Integer watchEpoch = Integer.parseInt(this.param.get(5));
		Integer epoch = Integer.parseInt(this.param.get(6));
		Integer trainSecond = Integer.parseInt(this.param.get(7));
		Integer testLossIncreaseTolerance = Integer.parseInt(this.param.get(8));

		if (batchSize != null) train.put("batchSize", batchSize);
		if (watchEpoch != null) train.put("watchEpoch", watchEpoch);
		if (epoch != null) train.put("epoch", epoch);
		if (trainSecond != null) train.put("trainSecond", trainSecond);
		if (testLossIncreaseTolerance != null) train.put("testLossIncreaseTolerance", testLossIncreaseTolerance);

		body.put("new", !"Y".equals(this.param.get(9)));
		body.put("train", train);

		comm.http(HttpMethod.POST, Context.getProp().getUrl().getMl() + "/" + comm.userId + "/" + modelName, null, body);

		this.reply.display = "训练开始，使用cat命令查看训练状态";
	}

	private void execute() {
		// TODO Auto-generated method stub
		
	}

	private void release() {
		// TODO Auto-generated method stub
		
	}

	private void setting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readParam() throws AshException {
		// TODO Auto-generated method stub
		
	}
}