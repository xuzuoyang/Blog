# -*- coding: utf-8 -*-
from odps import ODPS 
from email_tool import EmailTool


def get_data():
	# initiate portal to MaxCompute
	odps = ODPS('HQJlygwky31suH3L', 'ag8Fi6qvo1RRjBHV565s7wuh3P5mwr', 'dkd_data_project',
					endpoint='http://service.odps.aliyun.com/api')

	# focus on certain project/table
	# project = odps.get_project('my_project')
	# table = odps.get_table('my_table')
	table = odps.get_table('core_enterprise_loan_stat_accum')

	# list all tables
	# for table in odps.list_tables():

	# using head to query data if less than 10000 records
	print('using head to query: ')
	for record in table.head(5, partition='dt=20161130'):
		print(record)
		# print(record['core_enterprise_id', 'core_enterprise_name'])

	print('using i/o reader to query: ')
	# read data as an i/o reader
	with table.open_reader(partition='dt=20161130') as reader:
		count = reader.count
		for record in reader[5: count]:
			print(record)

	print('using sql to query: ')
	# execute sql to query
	# instance = odps.execute_sql('select * from dual') # 同步的方式执行，会阻塞直到SQL执行完成
	# instance = odps.run_sql('select * from dual') # 异步的方式执行
	# instance.wait_for_success() # 阻塞直到完成
	with odps.execute_sql('select * from core_enterprise_loan_stat_accum').open_reader() as reader:
		for record in reader:
			print(record)

if __name__ == '__main__':
	odps = ODPS('HQJlygwky31suH3L', 'ag8Fi6qvo1RRjBHV565s7wuh3P5mwr', 'dkd_data_project',
				endpoint='http://service.odps.aliyun.com/api')
	table = odps.get_table('core_enterprise_loan_stat_accum')

	start = '<html><head>' \
		'<meta charset="utf-8"><title>ODPS table--test</title></head>' \
		'</br><body text="#000000"><center>表1 核心企业统计表<font color="#dd0000"></font></center>' \
		'<table border="1" cellspacing="0" cellpadding="2" bordercolor="#000000"  align="center" ' \
		' style="border-collapse:collapse;">'

	t_head = '<tr><th>核心企业</th><th>月份</th><th>项目数</th>' \
		'<th>融资额(万元)</th><th>已还项目数</th><th>已还金额(万元)</th>' \
		'<th>平均融资额(万元)</th><th>平均融资期限</th><th>平均满标时间</th></tr>'

	t_data = ''
	with table.open_reader(partition='dt=20161130') as reader:
		count = reader.count
		for record in reader:
			if record['core_enterprise_name'] is None:
				continue
			t_data += '<tr>'
			t_data += '<td>' + str(record['core_enterprise_shortname']) + '</td>'
			t_data += '<td>' + str(record['month']) + '</td>'
			if record['total_project_num'] is None:
				t_data += '<td>0</td>'
			else:
				t_data += '<td>' + str(record['total_project_num']) + '</td>'
			if record['total_loan_amount'] is None:
				t_data += '<td>0</td>'
			else:
				t_data += '<td>' + str(round(record['total_loan_amount']/10000, 2)) + '</td>'
			if record['paid_project_num'] is None:
				t_data += '<td>0</td>'
			else:
				t_data += '<td>' + str(record['paid_project_num']) + '</td>'
			if record['paid_amount'] is None:
				t_data += '<td>0</td>'
			else:
				t_data += '<td>' + str(round(record['paid_amount']/10000, 2)) + '</td>'
			if record['loan_amount_avg'] is None:
				t_data += '<td>0</td>'
			else:
				t_data += '<td>' + str(round(record['loan_amount_avg']/10000, 2)) + '</td>'
			t_data += '<td>' + str(record['loan_time_avg']) + '</td>'
			t_data += '<td>' + str(record['sellout_time_avg']) + '</td>'
			t_data += '</tr>'

	end = '</br></br></table></body></html>'

	html = start + t_head + t_data + end

	#print(t_data)
	et = EmailTool('data@daokoudai.com', 'Data20160626')
	et.send_with_html('ODPS table test', ['xuzuoyang@daokoudai.com'], html)
