package com.dkd.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Random;

//import com.jmj.sc.data.AuthInfo;

public class ConstantData {

	/**
	 * 外部系统调用结算系统使用的认证对象
	 */
//	public static AuthInfo authInfo = new AuthInfo(ChConfig
//			.readString("ch_account_name"), ChConfig
//			.readString("ch_account_pwd"));
	public static final String GUOJIAO_KEY = "B881AC36265CF223E040A8C097977263";
	/**
	 * ICE默认登录账号：sp_jmj
	 */
	public static final String JINMAJIA = ConfigUtils.readConfigString("jmj_org");
	/**
	 * 直接认购：30%
	 */
	public static final double DIRECT_SUBSCRIBE = 0.3;
	/**
	 * 挂单的序列类型:order
	 */
	public static final String ORDER_SEQ_TYPE = "order";
	/**
	 * 摇号的序列类型:yaohao
	 */
	public static final String YAOHAO_SEQ_TYPE = "yaohao";
	/**
	 * 摇号中奖：1
	 */
	public static final String WIN = "1";
	/**
	 * 摇号未中奖：0
	 */
	public static final String NOT_WIN = "0";
	/**
	 * 1573酒坛单位:1坛.
	 */
	public static String UNIT_1573_1 = "1";

	/**
	 * 1573酒坛单位:5坛.
	 */
	public static String UNIT_1573_5 = "5";

	/**
	 * 1573酒坛单位:10坛.
	 */
	public static String UNIT_1573_10 = "10";

	/**
	 * 1573酒坛单位:50坛.
	 */
	public static String UNIT_1573_50 = "50";

	/**
	 * 1573酒坛单位:100坛.
	 */
	public static String UNIT_1573_100 = "100";

	/**
	 * 返回的成功标志：T
	 */
	public static final String FLAG_OK = "T";

	/**
	 * 返回的错误标志：F
	 */
	public static final String FLAG_FALSE = "F";

	/**
	 * 提货卡状态:未使用：0
	 */
	public static final String CARD_STATUS_NOUSER = "0";
	
	/**
	 * 已申请提货卡：9
	 */
	public static final String CARD_STATUS_APPLYED = "9";

	/**
	 * 提货卡状态:未激活：1
	 */
	public static final String CARD_STATUS_UNACTIVITY = "1";

	/**
	 * 提货卡状态:已激活：2
	 */
	public static final String CARD_STATUS_ACTIVITY = "2";

	/**
	 * 提货卡状态:已开卡：3
	 */
	public static final String CARD_STATUS_OPEN = "3";

	/**
	 * 提货卡状态:无效卡.
	 */
	public static final String CARD_STATUS_INVALID = "4";
	
	public static String getCardStatusName(String status){
		String str = "";
		if(ConstantData.CARD_STATUS_NOUSER.equals(status)){
			str = "未使用";
		}else if(ConstantData.CARD_STATUS_APPLYED.equals(status)){
			str = "待发出";
		}else if(ConstantData.CARD_STATUS_UNACTIVITY.equals(status)){
			str = "待激活";
		}else if(ConstantData.CARD_STATUS_ACTIVITY.equals(status)){
			str = "已激活";
		}else if(ConstantData.CARD_STATUS_OPEN.equals(status)){
			str = "已开卡";
		}
		return str;
	}

	/**
	 * 提货卡发放状态：处理中，未发放
	 */
	public static final String CARD_SENT_NO = "0";

	/**
	 * 提货卡发放状态：已发放
	 */
	public static final String CARD_SENT_YES = "1";

	/**
	 * 开卡操作类型:提货.
	 */
	public static final String CARDOPEN_TYPE_PICKUP = "1";

	/**
	 * 开卡操作类型:委托再交易.
	 */
	public static final String CARDOPEN_TYPE_COMMTRADE = "2";
	/**
	 * 开卡操作类型:挂失.
	 */
	public static final String CARDOPEN_TYPE_LOSE = "3";

	/**
	 * 开卡状态:正常.
	 */
	public static final String CARDOPEN_STATUS_NORMAL = "1";

	/**
	 * 开卡状态:已处理.
	 */
	public static final String CARDOPEN_STATUS_DEAL = "2";

	/**
	 * 开卡状态:异常.
	 */
	public static final String CARDOPEN_STATUS_ABNORMAL = "3";

	/**
	 * 入仓:in
	 */
	public static final String CANG_IN = "in";

	/**
	 * 出仓:out
	 */
	public static final String CANG_OUT = "out";

	
	/**
	 * 冲正入仓:in
	 */
	public static final String CZ_IN = "in";

	/**
	 * 冲正出仓:out
	 */
	public static final String CZ_OUT = "out";
	
	/**
	 * T+N入仓
	 */
	public static final int CANG_TN = 1;

	/**
	 * T+N入金
	 */
	public static final int BANK_TN = 1;

	/**
	 * 默认是第1页
	 */
	public static final int OFFSET = 1;

	/**
	 * 默认是10条记录
	 */
	public static final int LIMIIT = 10;

	/**
	 * 价格单位：元
	 */
	public static final String UNIT = "元";

	/**
	 * 请求成功：000000
	 */
	public static final String SUCCESS = "000000";

	/**
	 * 请求失败：111111
	 */
	public static final String ERROR = "111111";

	/*
	 * 交易类型
	 */
	public static class DealType {
		/**
		 * 挂买单:pubBuy
		 */
		public static String PUB_BUY = "pubBuy";
		/**
		 * 挂卖单:pubSell
		 */
		public static String PUB_SELL = "pubSell";
		/**
		 * 挂买单和应单:pubBuyAndResponse
		 */
		public static String PUB_BUY_AND_RESPONSE = "pubBuyAndResponse";
		/**
		 * 挂卖单和应单:pubSellAndResponse
		 */
		public static String PUB_SELL_AND_RESPONSE = "pubSellAndResponse";
		/**
		 * 应买单:responseBuy
		 */
		public static String RESPONSE_BUY = "responseBuy";
		/**
		 * 应卖单:responseSell
		 */
		public static String RESPONSE_SELL = "responseSell";
		/**
		 * 撤买单:cancelBuy
		 */
		public static String CANCEL_BUY = "cancelBuy";
		/**
		 * 撤卖单:cancelSell
		 */
		public static String CANCEL_SELL = "cancelSell";
	}

	/**
	 * 挂单类型
	 * 
	 * @author ysliu
	 * 
	 */
	public static class OrderType {
		/**
		 * 买单：1
		 */
		public static int BUY = 1;
		/**
		 * 卖单：2
		 */
		public static int SELL = 2;
	}

	public static String getOrderType(int type) {
		if (ConstantData.OrderType.BUY == type) {
			return "买单";
		} else if (ConstantData.OrderType.SELL == type) {
			return "卖单";
		} else {
			return "";
		}
	}

	/**
	 * 撤单状态
	 *
	 * @author ysliu
	 *
	 */
	public static class CancelState {
		/**
		 * 已撤单：1
		 */
		public static String CANCEL_YES = "1";
		/**
		 * 未撤单：0
		 */
		public static String CANCEL_NO = "0";
	}

	public static String getCancelState(String state) {
		String ret = "";
		if (ConstantData.CancelState.CANCEL_YES.equals(state)) {
			ret = "已撤单";
		} else if (ConstantData.CancelState.CANCEL_NO.equals(state)) {
			ret = "未撤单";
		}
		return ret;
	}

	/**
	 * 佣金类型
	 * @author ysliu
	 *
	 */
	public static class CommisionType{
		/**
		 * 交易佣金
		 */
		public static String DEAL_COMM = "1";
		/**
		 * 认购佣金
		 */
		public static String RENGOU_COMM = "2";
		/**
		 * 提货佣金
		 */
		public static String TIHUO_COMM = "3";
	}

	/**
	 * 资金类型
	 * @author ysliu
	 *
	 */
	public static class BalanceType{
		/**
		 * 入金
		 */
		public static int TRANSFER_IN = 1;
		/**
		 * 出金
		 */
		public static int TRANSFER_OUT = 2;
		/**
		 * 收入
		 */
		public static int RECEIVE_MONEY = 3;
		/**
		 * 支出
		 */
		public static int PAY_MONEY = 4;
	}

	public static String getCommisionType(String type){
		String ret = "";
		if(ConstantData.CommisionType.DEAL_COMM.equals(ret)){
			ret = "交易佣金";
		}else if(ConstantData.CommisionType.RENGOU_COMM.equals(ret)){
			ret = "认购佣金";
		}else if(ConstantData.CommisionType.TIHUO_COMM.equals(ret)){
			ret = "提货佣金";
		}
		return ret;
	}
	
	/**
	 * 获取一个序列（根据时间戳）
	 * @return
	 */
	public static String getSequence(){
		int intCount=(new Random()).nextInt(999999);
		String val = String.valueOf(intCount);
		if(val.length() < 6){
			int len = val.length();
			int n = 6 - len;
			for(int i = 0;i<n;i++){
				val += "0";
			}
		}
		return String.valueOf(new Date().getTime()+val);
	}
	
	/**
	 * 格式化金额
	 * @param amount
	 * @return
	 */
	public static String transferAmount(long amount){
		double doubleAmount = amount / 100.0;
		String strAmount = String.valueOf(doubleAmount);
		  if (strAmount == null || strAmount.length() < 1) {
			   return "";
		  }
		  NumberFormat formater = null;
		  double num = Double.parseDouble(strAmount);
		  formater = new DecimalFormat("0,000.00");
		  return "￥"+formater.format(num);
	}
	
	/**
	 * 格式化佣金
	 * @param amount
	 * @return
	 */
	public static String transferCommAmount(long amount){
		double doubleAmount = amount / 100.0;
		String strAmount = String.valueOf(doubleAmount);
		  if (strAmount == null || strAmount.length() < 1) {
			   return "";
		  }
		  NumberFormat formater = null;
		  double num = Double.parseDouble(strAmount);
		  formater = new DecimalFormat("0.00");
		  return "￥"+formater.format(num);
	}
	
	
	/**
	 * 申请提货:ChGoodsDetail.status=0.
	 */
	public static final String GOODDETAIL_INIT = "0";
	/**
	 * 已填写详细信息:ChGoodsDetail.status=1.
	 */
	public static final String GOODDETAIL_APPLY = "1";
	/**
	 * ICE确认提货:ChGoodsDetail.status=2
	 */
	public static final String GOODDETAIL_CONFIRM = "2";

	/**
	 * 已发货:ChGoodsDetail.status=3.
	 */
	public static final String GOODDETAIL_DELIVERY = "3";

	/**
	 * 已收货:ChGoodsDetail.status=4.
	 */
	public static final String GOODDETAIL_ACCEPT = "4";
	
	/**
	 * 已撤销:ChGoodsDetail.status=-1.
	 */
	public static final String GOODDETAIL_CANCEL = "-1";

	/**
	 * 未付款:ChGoodsDetail.feeStatus=0
	 */
	public static final String GOODDETAIL_FEESTATUS_NO = "0";

	/**
	 * 已付款:ChGoodsDetail.feeStatus=1
	 */
	public static final String GOODDETAIL_FEESTATUS_YES = "1";

	/**
	 * 持仓表C1:c1
	 */
	public static final String CANG_C1 = "c1";

	/**
	 * 持仓表C2:c2
	 */
	public static final String CANG_C2 = "c2";

	/**
	 * 持仓表C3:c3
	 */
	public static final String CANG_C3 = "c3";

	/**
	 * 持仓表C4:c4
	 */
	public static final String CANG_C4 = "c4";
	
	/**
	 * 持仓表C5:c5
	 */
	public static final String CANG_C5 = "c5";
	
	/**
	 * 持仓表C6:c6
	 */
	public static final String CANG_C6 = "c6";
	
	/**
	 * 用户资金M1:m1
	 */
	public static final String MONEY_M1 = "m1";
	
	/**
	 * 用户资金M2:m2
	 */
	public static final String MONEY_M2 = "m2";
	
	/**
	 * 申购状态
	 * @author ysliu
	 * hjh 2012-9-4修改
	 *
	 */
	public static class RengouStatus{
		/**
		 * 中签：1
		 */
		public static String YES_CONFIRM = "1";
		/**
		 * 未中签：0
		 */
		public static String NOT_CONFIRM = "0";
		
		/**
		 * 撤消抽签状态
		 */
		
		public static String CANCLE_CONFIRM = "3";
		
		/**
		 * 未抽签状态
		 */
		
		public static String UNHANDLED = "9";
		
		/**
		 * 直接申购成功：2
		 */
		public static String ZJ_CONFIRM = "2";
	}
	
	public static String getRengouStatusTxt(String status){
		String str = "";
		if(RengouStatus.YES_CONFIRM.equals(status)){
			str = "中签";
		}else if(RengouStatus.NOT_CONFIRM.equals(status)){
			str = "未中签";
		}else if(RengouStatus.ZJ_CONFIRM.equals(status)){
			str = "直接申购成功";
		}else if(RengouStatus.CANCLE_CONFIRM.equals(status)){
			str = "撤消成功";
		}else if(RengouStatus.UNHANDLED.equals(status)){
			str = "未抽签";
		}
		return str;
	}

	
	public static String YHGL = "用户管理";
	public static String PZSPGL = "产品管理";
	public static String LXGL = "类型管理";
	public static String GGGL = "公告管理";
	public static String XTGL = "系统管理";
	public static String QXGL = "权限管理";
	public static String WPHBGL = "物品划拨管理";
	public static String ZDZYJG = "指定承销商";
	public static String ZJGL = "资金管理";
	public static String ZZSQ ="转账申请";
	public static String THLC ="提货流程";
	public static String YWQS ="业务清算";
	public static String SJTJ ="数据统计";
	public static String ZYJG ="专营机构"; 
	public static String SGGL = "申购管理";
	public static String BJ = "报价";
	public static String CJ = "成交";
	public static String XJ = "询价";
	public static String CSSD = "参数设定";
	public static String LTZTSD= "流通状态设定";
	public static String GZ= "关注";
	
}
