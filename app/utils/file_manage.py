import oss2
import logging
import datetime
from confs.config import config
from flask import current_app

logger = logging.getLogger('root')


class OssClient:
    """A client tool for the usage of Aliyun OSS.
    """
    def __new__(cls, *args, **kwargs):
        if not hasattr(cls, '_instance'):
            cls._instance = super(OssClient, cls).__new__(cls, *args, **kwargs)
        return cls._instance

    def __init__(self):
        logger.info('Initiating OssClient...')
        config = current_app.config
        acess_id, access_secret = config['ACCESS_KEY_ID'], config['ACCESS_KEY_SECRET']
        end_point, bucket_name = config['OSS_END_POINT'], config['OSS_BUCKET_NAME']
        self.auth = oss2.Auth(acess_id, access_secret)
        self.bucket = oss2.Bucket(self.auth, end_point, bucket_name)
        logger.info('OssClient is ready to use.')

    def upload_img(self, filepath='', file=None, expire_time=3600*24*365*10):
        """Upload image files to OSS.

        This function upload image from either a valid file object or through a file path,
        return an referring url with specific expire time.

        Args:
            filepath: Absolute path of the referring file.
            file: valid file-like object, default None.
            expire_time: Valid time length of the returned url, default 10 years.

        Returns:
            A url through which the referring image could be reached by GET method.
        """
        # make file name
        now = datetime.datetime.now()
        filename = 'img{}.jpg'.format(now.timestamp())
        logger.info('Name the uploading file as {}.'.format(filename))

        url = None
        # if a valid file object
        if file is not None:
            self.bucket.put_object(filename, file)
            url = self.bucket.sign_url('GET', filename, expire_time)
        # if filepath
        elif filepath is not None:
            with open(filepath, 'rb') as f:
                self.bucket.put(filename, f)
                url = self.bucket.sign_url('GET', filename, expire_time)
        else:
            pass
        return url
