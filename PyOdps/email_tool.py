from email import encoders
from email.header import Header
from email.mime.base import MIMEBase
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.utils import parseaddr, formataddr

import smtplib


class EmailTool:
	"""Email sending function class tool
	"""

	def __init__(self, from_addr, password, smtp_server='smtp.exmail.qq.com', smtp_port=25):
		self.from_addr = from_addr
		self.password = password
		self.smtp_server = smtp_server
		self.smtp_port = smtp_port

	def __format_addr(self, s):
		"""parse and format sending name and address"""
		name, addr = parseaddr(s)
		return formataddr((Header(name, 'utf-8').encode(), addr))

	def send_with_html(self, subject, to_addr, content, cc_addr=[]):
		receivers = ','.join(to_addr)
		ccers = ','.join(cc_addr)

		# message = MIMEText('Hello world', _subtype='plain', _charset='utf-8')  # 文本邮件
		message = MIMEText(content, 'html', 'utf-8')  # HTML邮件
		# message['From'] = _format_addr('发件人 <%s>' % from_addr)  # 有中文前缀的话需要通过Header编码 
		# message['To'] = ','.join([_format_addr('收件人 <%s>' % receiver) for receiver in to_addr])  # 若是多个收件人的话需要format多次
		message['From'] = self.from_addr
		message['To'] = receivers
		if cc_addr:
			# message['Cc'] = ','.join([_format_addr('ccers <%s>' % ccer) for ccer in cc_addr]) 
			message['Cc'] = ccers
		message['Subject'] = subject

		try:
			server = smtplib.SMTP(self.smtp_server, self.smtp_port) # smtp default port no. 25
			server.starttls()
			# server.set_debuglevel(1)  # 可以打印出和SMTP服务器交互的所有信息
			server.login(self.from_addr, self.password)
			server.sendmail(self.from_addr, to_addr+cc_addr, message.as_string())
			server.quit()
		except smtplib.SMTPException:
			print('Error sending email!')

	def send_with_file(self, subject, to_addr, content, file_path, file_name, cc_addr=[]):
		"""send email with one file attachment, need to be optimized for multi attachments
		"""
		receivers = ','.join(to_addr)
		ccers = ','.join(cc_addr)

		message = MIMEMultipart()
		message['From'] = self.from_addr
		message['To'] = receivers
		if cc_addr:
			message['Cc'] = ccers
		message['Subject'] = subject

		message.attach(MIMEText(content, _subtype='plain', _charset='utf-8'))  # attach也可以 message = MIMEText()也可以

		# one way to attach files

		# for file in files: 可循环加入附件
		# att = MIMEText(open('/Users/zuoyangxu/Downloads/eproduct.xlsx', 'rb').read(), 'base64', 'gb2312')
		# att['Content-Type'] = 'application/octet-stream'
		# att["Content-Disposition"] = 'attachment; filename="123.txt'
		# att.add_header('Content-Disposition', 'attachment', filename='eproduct.xlsx')
		# message.attach(att)

		# another way to attach files

		with open(file_path, 'rb') as f:
			mime = MIMEBase('application', 'octet-stream')
			# 把附件的内容读进来:
			mime.set_payload(f.read())
			# 用Base64编码:
			encoders.encode_base64(mime)
			mime.add_header('Content-Disposition', 'attachment', filename=file_name)
			# mime.add_header('Content-ID', '<0>')
			# mime.add_header('X-Attachment-Id', '0')
			# 添加到MIMEMultipart:
			message.attach(mime)

		try:
			server = smtplib.SMTP(self.smtp_server, self.smtp_port)  # smtp default port no. 25
			server.starttls()
			# server.set_debuglevel(1)  # 可以打印出和SMTP服务器交互的所有信息
			server.login(self.from_addr, self.password)
			server.sendmail(self.from_addr, to_addr+cc_addr, message.as_string())  # 此处须加cc member的address 不同邮件服务商效果不一样？
			server.quit()
		except smtplib.SMTPException:
			print('Error sending email!')

	def send_with_image(self, subject, to_addr, content, *files, cc_addr=[]):
		"""send email with one or more images in the content, each as an attached file
		when use the method, should make sure in the content there have enough <img> tag
		to use the image files in the attachment
		"""
		receivers = ','.join(to_addr)
		ccers = ','.join(cc_addr)

		message = MIMEMultipart()
		message['From'] = self.from_addr
		message['To'] = receivers
		if cc_addr:
			message['Cc'] = ccers
		message['Subject'] = subject

		# one way to attach files

		for index, file in enumerate(files):
			att = MIMEText(open(file, 'rb').read(), 'base64', 'gb2312')
			att['Content-Type'] = 'application/octet-stream'
			# att["Content-Disposition"] = 'attachment; filename=123.txt'
			att.add_header('Content-Disposition', 'attachment')
			att.add_header('Content-ID', '<' + str(index) + '>')
			message.attach(att)

		# another way to attach files

		# with open(file_path, 'rb') as f:
		# 	mime = MIMEBase('application', 'octet-stream')
		# 	# 把附件的内容读进来:
		# 	mime.set_payload(f.read())
		# 	# 用Base64编码:
		# 	encoders.encode_base64(mime)
		# 	mime.add_header('Content-Disposition', 'attachment', filename=file_name)
		# 	mime.add_header('Content-ID', '<20161216>')
		# 	# mime.add_header('X-Attachment-Id', '0')
		# 	# 添加到MIMEMultipart:
		# 	message.attach(mime)

		message.attach(MIMEText(content, 'html', 'utf-8'))  # attach也可以 message = MIMEText()也可以

		try:
			server = smtplib.SMTP(self.smtp_server, self.smtp_port)  # smtp default port no. 25
			server.starttls()
			# server.set_debuglevel(1)  # 可以打印出和SMTP服务器交互的所有信息
			server.login(self.from_addr, self.password)
			server.sendmail(self.from_addr, to_addr + cc_addr,
							message.as_string())  # 此处须加cc member的address 不同邮件服务商效果不一样？
			server.quit()
		except smtplib.SMTPException:
			print('Error sending email!')


if __name__ == '__main__':
	et = EmailTool('data@daokoudai.com', 'Data20160626')
	# et.send_with_html('MultipleReceiverTest', ['xzyduoduo@126.com', 'xuzuoyang@daokoudai.com'],
	# 					'<html><body><h1>Hello</h1></body></html>')
	# et.send_with_file('SendWithExcelFileTest', ['xuzuoyang@daokoudai.com'],
	# 					'Hello', '/Users/zuoyangxu/Downloads/eproduct.xlsx', 'eproduct.xlsx')
	et.send_with_image('ImageContentTest', ['xuzuoyang@daokoudai.com', ],
					'<html><body><h1>Hello</h1><p><img src="cid:0"></p></br><p><img src="cid:1"></p></body></html>',
					'/Users/zuoyangxu/stat_plot/user_stat/user_stat_by_channel/20161216.png',
					'/Users/zuoyangxu/stat_plot/user_stat/user_stat_weekly/20161216.png')




