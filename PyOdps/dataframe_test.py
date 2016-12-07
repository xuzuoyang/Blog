# -*- coding: utf-8 -*-
from odps import ODPS 
from odps.df import DataFrame

# Data Frame Usage
odps = ODPS('HQJlygwky31suH3L', 'ag8Fi6qvo1RRjBHV565s7wuh3P5mwr', 'dkd_data_project', endpoint='http://service.odps.aliyun.com/api')
table = odps.get_table('project_stat_by_core_enterprise_and_amount')

stat = DataFrame(table)
# print(stat.dtypes)
# print(stat.head(10))
 
# dt = stat.groupby('loan_amount_range').agg(count=stat['loan_amount_range'].count())
# dt.sort('count', ascending=False)[:]
print(stat['loan_amount_range'].value_counts().head(5))