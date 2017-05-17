package cn.standardai.api.ash.command;

import cn.standardai.api.ash.command.base.AshCommonCommand;
import cn.standardai.api.ash.resource.base.AshResource;

public class AshHelp extends AshCommonCommand {

	protected final String help = "help命令格式：help [名词]";

	protected final String man = "help命令用于显示帮助信息\n"
				+ "用法\n"
				+ "\thelp [-c 名词]\n"
				+ "参数\n"
				+ "\t-c:\t显示某个名词解释";

	@Override
	public void invoke() {

		if (params.number() == 0) {
			this.reply.display = "Ash是Athvillar平台专用的命令行系统，用来管理和操作Athvillar平台的资源。\n"
					+ "其中全局命令\n"
					+ "\tash(未完成)\t\t\tcd(未完成)\t\t\tcurl(未完成)\n"
					+ "\techo(未完成)\t\t\thelp\t\t\t\t\thistory(未完成)\n"
					+ "\tman\t\t\t\t\tmsg\t\t\t\t\tlogin\n"
					+ "\tlogout(未完成)\t\t\tversion(未完成)\n"
					+ "针对某资源的命令\n"
					+ "\tcat(未完成)\t\t\tcp(未完成)\t\t\tdownload(未完成)\n"
					+ "\tfind(未完成)\t\t\tls\t\t\t\t\tmk\n"
					+ "\trm\t\t\t\t\tupload(未完成)\t\n"
					+ "资源\n"
					+ "\tdata(未完成)\t\t\tdataset(未完成)\t\tdic(未完成)\n"
					+ "\tdoc(未完成)\t\t\tmodel\t\t\t\tnode(未完成)\n"
					+ "\ttemplate(未完成)\t\tuser(未完成)\n"
					+ "查看某个命令的具体使用方式请输入“man 命令名”，查看某项资源介绍请输入“help 资源名”";
		}

		AshResource resource = AshResource.getInstance(params.get(1));
		if (resource == null) {
			this.reply.display = "没有找到" + params.get(1);
		}

		this.reply.display = resource.help().display;
	}
}
