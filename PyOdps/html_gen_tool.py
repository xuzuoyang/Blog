# -*- coding: utf-8 -*-
from pyodps_tool import PyODPSTool
from email_tool import EmailTool


def daily_user_stat():
	start = '<html><head>' \
			'<meta charset="utf-8"><title>ODPS table--test</title></head>' \
			'</br><body text="#000000"><center>每日用户统计<font color="#dd0000"></font></center>' \
			'</br><p>当前用户累计情况:</p>' \
			'<table border="1" cellspacing="0" cellpadding="2" bordercolor="#000000" width="90%" align="center" ' \
			' style="border-collapse:collapse;">'

	t_head = '<tr><th>注册用户数</th><th>新上行用户数</th><th>绑卡用户数</th><th>投资用户数</th></tr>'

	t_data = '<tr><td>99211</td><td>12842</td><td>6301</td><td>12120</td></tr>'

	t_end = '</br></table><center><b>说明:</b></center>'

	p_data = '</br><p>近7日新增用户情况:</p></br><p><img src="cid:0"></p></br>' \
			 '</br><p>新增用户渠道分布:</p></br><p><img src="cid:1"></p></br>'

	p_end = '</body></html>'

	html = start + t_head + t_data + t_end + p_data + p_end

	return html


def core_enterprise_loan_stat_accum():
	records = PyODPSTool().get_data_by_io('core_enterprise_loan_stat_accum', 'dt=20161130', 0)

	start = '<html><head>' \
		'<meta charset="utf-8"><title>ODPS table--test</title></head>' \
		'</br><body text="#000000"><center>核心企业累计情况统计表<font color="#dd0000"></font></center>' \
		'<table border="1" cellspacing="0" cellpadding="2" bordercolor="#000000" width="90%" align="center" ' \
		' style="border-collapse:collapse;">'

	t_head = '<tr><th>核心企业</th><th>月份</th><th>项目数</th>' \
			 '<th>融资额</th><th>已还项目数</th><th>已还金额</th>' \
			 '<th>平均融资额</th><th>平均融资期限</th><th>平均满标时间</th></tr>'

	t_data = ''

	for record in records:
		if record['core_enterprise_name'] is None:
			continue
		t_data += '<tr>'
		t_data += '<td>' + str(record['core_enterprise_shortname']) + '</td>'
		# t_data += '<td>' + str(record['month']) + '</td>'
		t_data += '<td>11</td>'
		if record['total_project_num'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(record['total_project_num']) + '</td>'
		if record['total_loan_amount'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['total_loan_amount'] / 10000, 2)) + '</td>'
		if record['paid_project_num'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(record['paid_project_num']) + '</td>'
		if record['paid_amount'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['paid_amount'] / 10000, 2)) + '</td>'
		if record['loan_amount_avg'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['loan_amount_avg'] / 10000, 2)) + '</td>'
		t_data += '<td>' + str(record['loan_time_avg']) + '</td>'
		t_data += '<td>' + str(round(record['sellout_time_avg']/60)) + '</td>'
		t_data += '</tr>'

	end = '</br></br></table><center><b>说明: 表中金额单位为万元, 项目期限单位为月, 满标时间单位为分钟.</b></center></br></body></html>'

	html = start + t_head + t_data + end

	return html


def finance_stat():
	records = []
	for i in range(1, 8):
		partition = 'dt=2016120' + str(i)
		record = PyODPSTool().get_data_by_io('finance_stat', partition, 0)
		records += record

	start = '<html><head>' \
		'<meta charset="utf-8"><title>ODPS table--test</title></head>' \
		'</br><body text="#000000"><center>资金统计表<font color="#dd0000"></font></center>' \
		'<table border="1" cellspacing="0" cellpadding="2" bordercolor="#000000" width="90%" align="center" ' \
		' style="border-collapse:collapse;">'

	t_head = '<tr><th>统计日期</th><th>充值总额</th><th>提现总额</th>' \
		'<th>投资总额</th><th>回款总额</th><th>账户总额</th>' \
		'<th>平台资金净流入</th><th>笔均投资额</th><th>人均投资额</th></tr>'

	t_data = ''

	for record in records:
		t_data += '<tr>'
		t_data += '<td>' + str(record['stat_date'].strftime('%Y-%m-%d')) + '</td>'
		if record['charge_amt'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['charge_amt'] / 10000, 2)) + '</td>'
		if record['withdraw_amt'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['withdraw_amt'] / 10000, 2)) + '</td>'
		if record['invest_amt'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(record['invest_amt'] / 10000) + '</td>'
		if record['invest_return_total'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['invest_return_total'] / 10000, 2)) + '</td>'
		if record['user_account_total'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['user_account_total'] / 10000, 2)) + '</td>'
		if record['net_inflow'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['net_inflow'] / 10000, 2)) + '</td>'
		if record['invest_avg_per_count'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['invest_avg_per_count'] / 10000, 2)) + '</td>'
		if record['invest_avg_per_user'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['invest_avg_per_user'] / 10000, 2)) + '</td>'
		t_data += '</tr>'

	end = '</br></br></table><center><b>说明: 表中金额单位均为万元</b></center></br></body></html>'

	html = start + t_head + t_data + end

	return html


def project_stat_by_core_enterprise_and_amount():
	records = PyODPSTool().get_data_by_io('project_stat_by_core_enterprise_and_amount', 'dt=20161130', 0)

	start = '<html><head>' \
		'<meta charset="utf-8"><title>ODPS table--test</title></head>' \
		'</br><body text="#000000"><center>核心企业项目融资额分布统计表<font color="#dd0000"></font></center>' \
		'<table border="1" cellspacing="0" cellpadding="2" bordercolor="#000000" width="90%" align="center" ' \
		' style="border-collapse:collapse;">'

	t_head = '<tr><th>核心企业id</th><th>核心企业名称</th><th>月份</th><th>项目数</th>' \
		'<th>融资总额范围</th><th>融资总额(万元)</th></tr>'

	t_data = ''

	for record in records:
		if record['core_enterprise_name'] is None:
			continue
		t_data += '<tr>'
		t_data += '<td>' + str(record['core_enterprise_id']) + '</td>'
		t_data += '<td>' + str(record['core_enterprise_shortname']) + '</td>'
		# t_data += '<td>' + str(record['month']) + '</td>'
		t_data += '<td>11</td>'
		if record['project_num'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(record['project_num']) + '</td>'
		t_data += '<td>' + str(record['loan_amount_range']) + '</td>'
		if record['total_amount'] is None:
			t_data += '<td>0</td>'
		else:
			t_data += '<td>' + str(round(record['total_amount'] / 10000, 2)) + '</td>'
		t_data += '</tr>'

	end = '</br></br></table></br></body></html>'

	html = start + t_head + t_data + end

	return html


if __name__ == '__main__':
	et = EmailTool('data@daokoudai.com', 'Data20160626')
	# et.send_with_html('ODPS统计表邮件测试', ['lidingyu@daokoudai.com', 'xuzuoyang@daokoudai.com', 'wangnan@daokoudai.com'],
	# 				core_enterprise_loan_stat_accum()+finance_stat()+project_stat_by_core_enterprise_and_amount())
	et.send_with_image('ImageContentTest', ['xuzuoyang@daokoudai.com', 'wangnan@daokoudai.com', ],
					   daily_user_stat(),
					   '/Users/zuoyangxu/stat_plot/user_stat/user_stat_weekly/20161216.png',
					   '/Users/zuoyangxu/stat_plot/user_stat/user_stat_by_channel/20161216.png')