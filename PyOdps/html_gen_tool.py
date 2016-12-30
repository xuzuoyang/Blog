# -*- coding: utf-8 -*-
from pyodps_tool import PyODPSTool
from email_tool import EmailTool
import datetime


def daily_user_stat():
	"""function for content of daily user statistics
	"""
	# sql to get data
	sql1 = 'select sum(register_user_num) as register_total, sum(open_account_user_num)+11549 as open_account_total, \
				sum(first_invest_user_num) as invest_total from user_type_stat'
	sql2 = 'select substr(stat_date, 1, 10), register_user_num, open_account_user_num, \
				(bindcard_user_num-open_account_user_num) as bindcard_stock_user_num, \
				first_invest_user_num from user_type_stat order by _c0 desc limit 7'
	sql3 = 'select channel, register_user_num, open_account_user_num, bindcard_user_num, first_invest_user \
				from user_type_stat_by_channel where substr(stat_date, 1, 10)="' + \
				(datetime.date.today() - datetime.timedelta(days=1)).strftime('%Y-%m-%d') + \
				'" order by register_user_num desc limit 1000'

	# for bindcard user total
	sql4 = 'select count(distinct user_id) as bindcard_total from user_bindcard'
	tool = PyODPSTool()
	data1, data2, data3 = tool.get_data_by_sql(sql1), tool.get_data_by_sql(sql2), tool.get_data_by_sql(sql3)
	data4 = tool.get_data_by_sql(sql4)

	# assemble html code
	start = '<html><head>' \
			'<meta charset="utf-8"><title>ODPS table--test</title></head>' \
			'</br><body text="#000000"><center>每日用户统计 ' + datetime.date.today().strftime('%Y-%m-%d') + \
			'<font color="#dd0000"></font></center>'

	t1_title = '</br><p>当前用户累计情况:</p>' \
		'<table border="1" cellspacing="0" cellpadding="2" bordercolor="#000000" width="90%" align="center" ' \
		' style="border-collapse:collapse;">'
	t1_head = '<tr><th>注册用户数</th><th>上行开户数</th><th>绑卡用户数</th><th>投资用户数</th></tr>'
	t1_data = ''
	for data in data1:
		t1_data += '<tr>'
		if data['register_total'] is None:
			t1_data += '<td>0</td>'
		else:
			t1_data += '<td>' + data['register_total'] + '</td>'
		if data['open_account_total'] is None:
			t1_data += '<td>0</td>'
		else:
			t1_data += '<td>' + data['open_account_total'] + '</td>'
		if data4[0]['bindcard_total'] is None:
			t1_data += '<td>0</td>'
		else:
			t1_data += '<td>' + data4[0]['bindcard_total'] + '</td>'
		if data['invest_total'] is None:
			t1_data += '<td>0</td>'
		else:
			t1_data += '<td>' + data['invest_total'] + '</td>'
		t1_data += '</tr>'
	t1_tail = '</br></table><center></center>'
	t1 = t1_title + t1_head + t1_data + t1_tail

	t2_title = '</br><p>近7日新增用户情况:</p>' \
		'<table border="1" cellspacing="0" cellpadding="2" bordercolor="#000000" width="90%" align="center" ' \
		' style="border-collapse:collapse;">'
	t2_head = '<tr><th>日期</th><th>注册人数</th><th>开户人数</th><th>存量用户绑卡人数</th><th>首投人数</th>'
	t2_data = ''
	for data in data2:
		t2_data += '<tr>'
		t2_data += '<td>' + data['_c0'] + '</td>'
		if data['register_user_num'] is None:
			t2_data += '<td>0</td>'
		else:
			t2_data += '<td>' + data['register_user_num'] + '</td>'
		if data['open_account_user_num'] is None:
			t2_data += '<td>0</td>'
		else:
			t2_data += '<td>' + data['open_account_user_num'] + '</td>'
		if data['bindcard_stock_user_num'] is None:
			t2_data += '<td>0</td>'
		else:
			t2_data += '<td>' + data['bindcard_stock_user_num'] + '</td>'
		if data['first_invest_user_num'] is None:
			t2_data += '<td>0</td>'
		else:
			t2_data += '<td>' + data['first_invest_user_num'] + '</td>'
		t2_data += '</tr>'
	t2_tail = '</br></table><center></center>'
	t2 = t2_title + t2_head + t2_data + t2_tail

	t3_title = '</br><p>新增用户渠道分布:</p>' \
		'<table border="1" cellspacing="0" cellpadding="2" bordercolor="#000000" width="90%" align="center" ' \
		' style="border-collapse:collapse;">'
	t3_head = '<tr><th>渠道</th><th>注册人数</th><th>开户人数</th><th>绑卡人数</th><th>首投人数</th>'
	t3_data = ''
	for data in data3:
		t3_data += '<tr>'
		if data['channel'] is None:
			t3_data += '<td>0</td>'
		else:
			t3_data += '<td>' + data['channel'] + '</td>'
		if data['register_user_num'] is None:
			t3_data += '<td>0</td>'
		else:
			t3_data += '<td>' + data['register_user_num'] + '</td>'
		if data['open_account_user_num'] is None:
			t3_data += '<td>0</td>'
		else:
			t3_data += '<td>' + data['open_account_user_num'] + '</td>'
		if data['bindcard_user_num'] is None:
			t3_data += '<td>0</td>'
		else:
			t3_data += '<td>' + data['bindcard_user_num'] + '</td>'
		if data['first_invest_user'] is None:
			t3_data += '<td>0</td>'
		else:
			t3_data += '<td>' + data['first_invest_user'] + '</td>'
		t3_data += '</tr>'
	t3_tail = '</br></table><center></center>'
	t3 = t3_title + t3_head + t3_data + t3_tail

	end = '</body></html>'
	html = start + t1 + t2 + t3 + end

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
	et.send_with_html('用户统计表邮件测试',
					['lidingyu@daokoudai.com', 'xuzuoyang@daokoudai.com', 'wangnan@daokoudai.com'], daily_user_stat())
	# et.send_with_image('ImageContentTest', ['xuzuoyang@daokoudai.com', 'wangnan@daokoudai.com', ],
	# 				   daily_user_stat(),
	# 				   '/Users/zuoyangxu/stat_plot/user_stat/user_stat_weekly/20161216.png',
	# 				   '/Users/zuoyangxu/stat_plot/user_stat/user_stat_by_channel/20161216.png')