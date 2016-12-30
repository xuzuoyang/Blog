# -*- coding: utf-8 -*-
from odps import ODPS
import datetime

class PyODPSTool:
	"""open python ODPS portal and get data
	"""
	__access_id = 'HQJlygwky31suH3L'
	__access_key = 'ag8Fi6qvo1RRjBHV565s7wuh3P5mwr'

	def __init__(self, project_name='dkd_data_project', end_point='http://service.odps.aliyun.com/api'):
		self.project_name = project_name
		self.end_point = end_point

	def get_data_by_io(self, table_name, partition, record_num):
		"""partition has to be like dt=20161130 / set record_num 0 to get all"""
		odps_portal = ODPS(self.__access_id, self.__access_key, self.project_name, self.end_point)
		table = odps_portal.get_table(table_name)

		result = []
		with table.open_reader(partition=partition) as reader:
			if record_num == 0:
				record_num = reader.count
			for record in reader[0:record_num]:
				result.append(record)

		return result

	def get_data_by_sql(self, sql):
		odps_portal = ODPS(self.__access_id, self.__access_key, self.project_name, self.end_point)

		result = []
		with odps_portal.execute_sql(sql).open_reader() as reader:
			for record in reader:
				result.append(record)

		return result

if __name__ == '__main__':

	records = PyODPSTool().get_data_by_sql('select channel, register_user_num, open_account_user_num, bindcard_user_num, first_invest_user \
				from user_type_stat_by_channel where substr(stat_date, 1, 10)="' +
				(datetime.date.today() - datetime.timedelta(days=2)).strftime('%Y-%m-%d') +
				'" order by register_user_num desc limit 1000')
	for record in records:
		print(record)

	# print([record['core_enterprise_shortname'] for record in records])