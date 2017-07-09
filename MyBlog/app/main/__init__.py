from flask import Blueprint

main = Blueprint('main', __name__)

# views, errors require main to be created first.
from . import views, errors
# from ..models import Permission
