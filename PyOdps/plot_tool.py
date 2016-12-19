# -*- coding: utf-8 -*-
import numpy as np
import matplotlib as mlb
import matplotlib.pyplot as plt
import datetime


def user_stat_plot(stat_date, user_register, user_open_account, stock_user_activate, stock_user_bindcard):
	"""plot user statistic of the most recent 7 days
	"""
	fontpath = '/Library/Fonts/华文细黑.ttf'
	properties = mlb.font_manager.FontProperties(fname=fontpath)
	mlb.rcParams['font.family'] = properties.get_name()

	y_pos = np.arange(len(stat_date))

	plt.figure(figsize=(10, 7))
	# plt.suptitle('7日新增用户统计')

	plt.subplot(221)
	plt.plot(user_register, 'b', lw=1.5)
	plt.plot(user_register, 'ro')
	plt.xticks(y_pos, stat_date)
	plt.grid(True)
	plt.ylabel('人数')
	plt.title('注册用户')

	plt.subplot(222)
	plt.plot(user_open_account, 'b', lw=1.5)
	plt.plot(user_open_account, 'ro')
	plt.xticks(y_pos, stat_date)
	plt.grid(True)
	plt.title('上行开户用户')

	plt.subplot(223)
	plt.plot(stock_user_activate, 'b', lw=1.5)
	plt.plot(stock_user_activate, 'ro')
	plt.xticks(y_pos, stat_date)
	plt.grid(True)
	plt.xlabel('日期')
	plt.ylabel('人数')
	plt.title('绑卡用户')

	plt.subplot(224)
	plt.plot(stock_user_bindcard, 'b', lw=1.5)
	plt.plot(stock_user_bindcard, 'ro')
	plt.xticks(y_pos, stat_date)
	plt.grid(True)
	plt.xlabel('日期')
	plt.title('投资用户')

	#plt.show()


	today = datetime.datetime.now().strftime('%Y%m%d')
	plt.savefig('/Users/zuoyangxu/stat_plot/user_stat/user_stat_weekly/' + today + '.png')


def user_stat_by_channel_plot(channel_name, user_register, user_open_account, user_bind_card, user_first_invest):
	"""plot user statistic by different channels
	"""
	fontpath = '/Library/Fonts/华文细黑.ttf'
	properties = mlb.font_manager.FontProperties(fname=fontpath)
	mlb.rcParams['font.family'] = properties.get_name()

	y_pos = range(len(channel_name))

	plt.figure(figsize=(10, 7))
	# plt.suptitle('user stat by channel')

	plt.subplot(221)
	plt.barh(y_pos, user_register, align='center', color='b', lw=1.5)
	plt.yticks(y_pos, channel_name)
	# plt.ylabel('渠道')
	plt.xlim(0, int(np.max(user_register) * 3 / 2))
	plt.title('注册用户')

	plt.subplot(222)
	plt.barh(y_pos, user_open_account, align='center', color='b', lw=1.5)
	plt.yticks(y_pos, channel_name)
	plt.xlim(0, int(np.max(user_open_account) * 3 / 2))
	plt.title('上行开户用户')

	plt.subplot(223)
	plt.barh(y_pos, user_bind_card, align='center', color='b', lw=1.5)
	plt.yticks(y_pos, channel_name)
	plt.xlabel('人数')
	# plt.ylabel('渠道')
	plt.xlim(0, int(np.max(user_bind_card) * 3 / 2))
	plt.title('绑卡用户')

	plt.subplot(224)
	plt.barh(y_pos, user_first_invest, align='center', color='b', lw=1.5)
	plt.yticks(y_pos, channel_name)
	plt.xlabel('人数')
	plt.xlim(0, int(np.max(user_first_invest) * 3 / 2))
	plt.title('投资用户')

	# plt.show()
	today = datetime.datetime.now().strftime('%Y%m%d')
	plt.savefig('/Users/zuoyangxu/stat_plot/user_stat/user_stat_by_channel/' + today + '.png')

if __name__ == '__main__':
	# stat_date = ['11-21', '11-22', '11-23', '11-24', '11-25', '11-26', '11-27']
	# register = [67, 83, 59, 33, 43, 34, 44]
	# sh_open_account = [36, 58, 50, 24, 23, 21, 19]
	# stock_user_activate = [86, 56, 54, 35, 41, 68, 50]
	# stock_user_bindcard = [31, 44, 33, 34, 21, 7, 12]
	# user_stat_plot(stat_date, register, sh_open_account, stock_user_activate, stock_user_bindcard)
	channel = ['苹果', '华为', '应用汇',
			   '小米', '其他']
	register = [14, 8, 4, 3, 2]
	sh_open_account = [4, 3, 3, 4, 3]
	bind_card = [15, 3, 2, 1, 9]
	first_invest = [2, 2, 3, 2, 0]
	user_stat_by_channel_plot(channel, register, sh_open_account, bind_card, first_invest)