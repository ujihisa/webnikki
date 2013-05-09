#!/usr/bin/env python
# -*- coding: utf-8 -*-

from sys import argv
from mailsnake import MailSnake
from mailsnake.exceptions import *

# ms = MailSnake('0bea4e62f382b67e6cd4e0d601c15178-us4')
mapi = MailSnake('XpV56E3xZK_pfIR7Jo0aTA', api='mandrill')
try:
    # print ms.ping() # returns "Everything's Chimpy!"

    # returns 'PONG!'
    print mapi.messages.send(message={'html': 'email html',
                                      'subject': 'テスト',
                                      'from_email': 'admin@webnikki.jp',
                                      'from_name': 'From Name',
                                      'to':[{'email': argv[1],
                                             'name': argv[1]}]})

except MailSnakeException:
    print 'An error occurred. :('
