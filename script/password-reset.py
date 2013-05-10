#!/usr/bin/env python
# -*- coding: utf-8 -*-

from sys import argv
from os import path
from mailsnake import MailSnake
from mailsnake.exceptions import MailSnakeException

mapi = MailSnake('XpV56E3xZK_pfIR7Jo0aTA', api='mandrill')

fileName = path.dirname(path.abspath(__file__)) + '/password-reset.txt'
file = open(fileName)
text = file.read()
file.close()

text = text.replace("__TO_EMAIL_ADDRESS__", argv[1])
text = text.replace("__PASSWORD_RESET_URL__", argv[2])

try:
    response = mapi.messages.send(message={'html': text,
                                           'subject': 'webnikki.jp パスワード',
                                           'from_email': 'admin@webnikki.jp',
                                           'from_name': 'webnikki.jp アドミニストレーター',
                                           'to':[{'email': argv[1],
                                                  'name': argv[1]}]})
    # response == 'PONG!'

except MailSnakeException:
    print 'An error occurred. :('
