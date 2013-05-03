from mailsnake import MailSnake
from mailsnake.exceptions import *

# ms = MailSnake('0bea4e62f382b67e6cd4e0d601c15178-us4')
mapi = MailSnake('XpV56E3xZK_pfIR7Jo0aTA', api='mandrill')
try:
    # print ms.ping() # returns "Everything's Chimpy!"
    print mapi.messages.send(message={'html': 'email html',
                                      'subject':'email subject',
                                      'from_email':'from@example.com',
                                      'from_name':'From Name',
                                      'to':[{'email': 'nori@mahata.net',
                                             'name':'To Name'}]}) # returns 'PONG!'

except MailSnakeException:
    print 'An error occurred. :('
