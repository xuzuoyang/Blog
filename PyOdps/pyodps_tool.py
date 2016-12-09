# -*- coding: utf-8 -*-
from odps import ODPS


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

	def get_data_by_sql(self, table_name, sql):
		odps_portal = ODPS(self.__access_id, self.__access_key, self.project_name, self.end_point)

		result = []
		with odps_portal.execute_sql(sql).open_reader() as reader:
			for record in reader:
				result.append(record)

		return result

if __name__ == '__main__':
	records = PyODPSTool().get_data_by_io('core_enterprise_loan_stat_accum', 'dt=20161130', 0)
	print([record['core_enterprise_shortname'] for record in records])