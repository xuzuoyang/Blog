from flask_wtf import FlaskForm
from wtforms import StringField, PasswordField, BooleanField, SubmitField, TextAreaField, SelectField
from wtforms.validators import InputRequired, Length, Email, Regexp, EqualTo, NoneOf
from ..models import User


class PostForm(FlaskForm):
    title = StringField('Title', validators=[InputRequired(), Length(1, 200)])
    category = SelectField('Category', validators=[InputRequired()], choices=[('tech', '技术'), ('life', '生活'), ('other', '其他')], coerce=str)
    body = TextAreaField('Content', validators=[InputRequired(), Length(1)], render_kw={'rows': 20})
    submit = SubmitField('Submit')


class CommentForm(FlaskForm):
    body = TextAreaField('Comment', validators=[InputRequired(), Length(1)], render_kw={'rows': 3})
    submit = SubmitField('Submit')


class MessageForm(FlaskForm):
    title = StringField('Title', validators=[InputRequired(), Length(1, 200)])
    body = TextAreaField('Content', validators=[InputRequired(), Length(1)], render_kw={'rows': 3})
    submit = SubmitField('Submit')


class ReplyForm(FlaskForm):
    body = TextAreaField('Comment', validators=[InputRequired(), Length(1)], render_kw={'rows': 3})
    submit = SubmitField('Submit')
