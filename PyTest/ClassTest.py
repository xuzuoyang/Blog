class EmailTool:
	
	__from_addr = 'data@daokoudai.com'  # class private var
	password = 'Data20160626'  # class public var

	def __init__(self, smtp_port):
		self.smtp_server = 'smtp.exmail.qq.com'  # instance public var
		self.__smtp_port = smtp_port  # instance private var

	def send(self):  #instance private method
		print(self.__from_addr)


if __name__ == '__main__':
	et = EmailTool(24)
	#et._EmailTool__send()
	print(EmailTool._EmailTool__from_addr)
	print(et._EmailTool__smtp_port)
	print(dir(EmailTool))