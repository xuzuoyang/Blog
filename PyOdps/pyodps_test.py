# -*- coding: utf-8 -*-
from odps import ODPS 

# initiate portal to MaxCompute
odps = ODPS('HQJlygwky31suH3L', 'ag8Fi6qvo1RRjBHV565s7wuh3P5mwr', 'dkd_data_project', endpoint='http://service.odps.aliyun.com/api')

# focus on certain project/table
# project = odps.get_project('my_project')
# table = odps.get_table('my_table')
table = odps.get_table('project_stat_by_core_enterprise_and_amount')

# list all tables
# for table in odps.list_tables():

# using head to query data if less than 10000 records
print('using head to query: ')
for record in table.head(5, partition='dt=20161130'): 
	print(record[0])
	print(record[0: 5])
	#print(record['column_name'])
	print(record['core_enterprise_id', 'core_enterprise_name'])

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
with odps.run_sql('select * from project_stat_by_core_enterprise_and_amount').open_reader() as reader:
	for record in reader:
		print(record)


