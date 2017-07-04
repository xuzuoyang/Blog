from flask import Blueprint

main = Blueprint('main', __name__)

# views, errors require main to be created first.
from . import views
# from ..models import Permission
